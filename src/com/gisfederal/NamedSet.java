package com.gisfederal;


import java.awt.geom.Rectangle2D;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.avro.Schema;
import org.apache.log4j.Logger;

import avro.java.gpudb.add_object_response;
import avro.java.gpudb.bounding_box_response;
import avro.java.gpudb.bulk_add_response;
import avro.java.gpudb.delete_object_response;
import avro.java.gpudb.filter_by_bounds_response;
import avro.java.gpudb.filter_by_list_response;
import avro.java.gpudb.filter_by_nai_response;
import avro.java.gpudb.filter_by_radius_response;
import avro.java.gpudb.filter_by_set_response;
import avro.java.gpudb.filter_by_value_response;
import avro.java.gpudb.get_objects_response;
import avro.java.gpudb.get_set_objects_response;
import avro.java.gpudb.get_set_response;
import avro.java.gpudb.get_sorted_set_response;
import avro.java.gpudb.get_tracks2_response;
import avro.java.gpudb.group_by_response;
import avro.java.gpudb.group_by_value_response;
import avro.java.gpudb.histogram_response;
import avro.java.gpudb.max_min_response;
import avro.java.gpudb.predicate_join_response;
import avro.java.gpudb.register_trigger_range_response;
import avro.java.gpudb.select_response;
import avro.java.gpudb.set_info_response;
import avro.java.gpudb.spatial_set_query_response;
import avro.java.gpudb.statistics_response;
import avro.java.gpudb.status_response;
import avro.java.gpudb.turn_off_response;
import avro.java.gpudb.unique_response;
import avro.java.gpudb.update_object_response;

import com.gisfederal.semantic.types.GenericSemanticType;
import com.gisfederal.semantic.types.Line;
import com.gisfederal.semantic.types.Point2D;
import com.gisfederal.semantic.types.Polygon;
import com.gisfederal.semantic.types.SemanticType;
import com.gisfederal.semantic.types.SemanticTypeEnum;
import com.gisfederal.semantic.types.Time;
import com.gisfederal.semantic.types.Track;
import com.gisfederal.utils.SpatialOperationEnum;
import com.gisfederal.utils.StatisticsOptionsEnum;

/**
 * The object that encapsulates a GPUDB Set.
 *
 */
public class NamedSet{
		
	/**
	 * This constant is used to indicate the end of a gpudb set. Used when getting data out of a set.
	 */
	public static final int END_OF_SET = -9999;

	// This is not very pleasant but for now we will match strings sent by the server
	private static final String EMPTY_SET_ERROR_MSG = "At least 1 child set must match the given semantic type"; 

	// Keeps it's name (i.e. guid) and the instance of gpudb to hit up
	private SetId id;
	private GPUdb gPUdb;
	private Logger log;

	// type and children	
	private Type type;
	private ConcurrentMap<Type,NamedSet> typeToChildren;

	// this is used to limit the number of encoded objects to add per bulk add POST
	private int bulkAddLimit = 1000;
	private int pageSize = 1000; // for the iterator
	
	
	private boolean mutable = false; // If this set is mutable, we need this to decide parallel work on the HA side.

	public boolean isMutable() {
		return mutable;
	}

	public void setMutable(boolean mutable) {
		this.mutable = mutable;
	}

	/**
	 * Provide basically a wrapper that returns a child set of this type from this parent set; creates a new one if it doesn't exist.
	 * Coordinates with the server.
	 * @param type The type of the child set.
	 * @return The child NamedSet
	 * @throws GPUdbException
	 */
	public NamedSet getChild(Type childType) throws GPUdbException{
		if(typeToChildren.containsKey(childType)) {
			return typeToChildren.get(childType);
		} else {
			// only check with the server if we don't already have the right child; make sure we have the most recent children
			this.getChildrenFromServer();
			if(typeToChildren.containsKey(childType)) {
				return typeToChildren.get(childType);
			} else {
				// now create the child; NOTE: in the future it would be nice if we could avoid doing this getChildrenFromServer()  (GPUDBDB-474)				
				// we don't know that this Type actually exists in gpudb so create it
				// OLD NOTE: security annotation of ARTIFACTID? is this right? very UCD specific, it would be better to add that annotation attribute to the type object				
				// NEW NOTE: security annotation is OBJECTAUTH now
				childType = gPUdb.create_type(childType.getAvroSchema().toString(), "OBJECTAUTH", childType.getTypeLabel(), childType.getSemanticTypeEnum());
				
				try {
					NamedSet child = gPUdb.newNamedSet(this.id, childType);
					typeToChildren.put(childType, child);
					return child;
				} catch (GPUdbException ge) {
					if( ge.getMessage().contains("Identical child type in MASTER detected")) {
						this.getChildrenFromServer();
						return typeToChildren.get(childType);
					}
					throw ge;
				}
			}
		}
	}

	/**
	 * Return back the children sets.
	 * @return collection of named set children
	 */
	public Collection<NamedSet> getChildren() {
		return this.typeToChildren.values();
	}

	/**
	 * This gets the list of Type objects, one for each child.  Or, if this set doesn't have children, return the type object
	 * for this set.  Doesn't go to the server.
	 * @return List of Types.
	 */
	public List<Type> listAllTypes() {
		List<Type> types = new ArrayList<Type>();

		if(type.isParent()) {
			
			Map<String, Long> setId2Size = new HashMap<String, Long>();
			status_response response = gPUdb.do_status(this);
			List<CharSequence> setids = response.getSetIds();
			List<Long> setsizes = response.getFullSizes();
			
			int idx = 0;
			while(idx<setids.size() && idx<setsizes.size()){  
				String setid = setids.get(idx).toString();
				Long size = setsizes.get(idx);
				setId2Size.put(setid, size);
				idx++;
			}
			// only return types for children that have a size > 0 (GPUDBDB-524).
			Iterator iter = this.typeToChildren.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry<Type, NamedSet> entry = (Map.Entry<Type, NamedSet>)iter.next();
				NamedSet child = entry.getValue();
				Type childType = entry.getKey();

				if( setId2Size.get(child.get_setid().get_id()) != 0 ) {
					types.add(childType);
				} else {
					log.debug("The child with this type has a zero size:"+childType.toString());
				}
			}
		} else {
			if(this.size() > 0) {
				types.add(this.type);
			} else {
				log.debug("This set has a zero size; don't add");
			}
		}
		
		return types;
	}

	/**
	 * Get the list of all different "sources" in this set. Relies upon the SOURCEKEY (param) being present in the objects.  
	 * @return List of SourceType objects. Each one has a type and a subtype.
	 * @throws GPUdbException
	 */
	public List<SourceType> listAllSources(String sourceKey) throws GPUdbException{
		List<SourceType> sources = new ArrayList<SourceType>();
		unique_response response = gPUdb.do_unique(this, sourceKey);
		List<CharSequence> values = response.getValuesStr();
		for(CharSequence composite : values) {
			// each composite a source type object
			log.debug("build source type on:"+composite);
			sources.add(new SourceType(composite.toString())); //throws if malformed
		}
		return sources;
	}

	/**
	 * Get the number of objects for each SourceType.
	 * @return Map of SourceType to the count (Integer).
	 */
	public Map<SourceType, Integer> getSourceTypesWithCounts(String sourceKey) {
		Map<SourceType, Integer> map2counts = new HashMap<SourceType, Integer>();
		List<String> attributes = new ArrayList<String>();

		attributes.add(sourceKey);
		// do the actual counting
		group_by_response response = gPUdb.do_group_by(this, attributes);			

		// build out the source types
		Iterator iter = response.getCountMap().entrySet().iterator();
		log.debug("got iterator; map size:"+response.getCountMap().size());
		while(iter.hasNext()) {
			Map.Entry<CharSequence, List<CharSequence>> pairs = (Map.Entry<CharSequence, List<CharSequence>>)iter.next();
			// each key becomes a source type object
			log.debug("build source type on:"+pairs.getKey().toString());				
			map2counts.put(new SourceType(pairs.getKey().toString()), Integer.parseInt(pairs.getValue().get(0).toString()));
		}			
		return map2counts;
	}
	
	/*
	private String getGroupingFieldForST(SemanticTypeEnum st) {
		String groupingFieldName = "";					
		switch(st) {
		case LINE:
			groupingFieldName = Line.groupingFieldName;
			break;
		case POLYGON2D:
			groupingFieldName = Polygon.groupingFieldName;
			break;
		case POLYGON3D:
			groupingFieldName = Polygon.groupingFieldName;
			break;
		case POINT:
			groupingFieldName = Point2D.groupingFieldName;
			break;
		case TRACK:
			groupingFieldName = Track.groupingFieldName;
			break;
		case TIME:
			groupingFieldName = Time.groupingFieldName;
			break;
		case SHAPE:	
		case EMPTY:
			groupingFieldName = "";
			break;
		case GENERICOBJECT:
			groupingFieldName = GenericSemanticType.groupingFieldName;
			break;
		default:
			log.warn("Unknown semantic:"+st);
		}
		return groupingFieldName;
	}
	*/
	
	/**
	 * This returns a map of semantic type to count.  So if there are 5 polygon objects and 2 points in this set it will return
	 * a map with the key-value pairs: "POLYGON2D" -> 5, "POINT" -> 2
	 * @return Map of semantic types to counts.
	 */
	public EnumMap<SemanticTypeEnum, Long> getSemanticTypesWithCounts() {
		EnumMap<SemanticTypeEnum, Long> typeToCount = new EnumMap<SemanticTypeEnum, Long>(SemanticTypeEnum.class);		
		
		// if this is NOT a parent; it's just a key-value entry
		if(!this.type.isParent()) {
			typeToCount.put(SemanticTypeEnum.valueOfWithEmpty(this.type.getSemanticType()), this.size());
			return typeToCount;
		}
		
		status_response response = gPUdb.do_status(this);
		List<CharSequence> stypes = response.getSemanticTypes();
		List<Long> setsizes = response.getSizes();
		int idx = 0;
		while( idx < stypes.size() && idx < setsizes.size() ) {
			String stype = stypes.get(idx).toString();
			Long count = setsizes.get(idx);
			SemanticTypeEnum semanticType = SemanticTypeEnum.valueOfWithEmpty(stype.toString());
			if(typeToCount.containsKey(semanticType)) {
				log.debug("this semantic type is already in the map");
				// there could be multiple children with the same type
				typeToCount.put(semanticType, typeToCount.get(semanticType).intValue()+count);
			} else {
				log.debug("this semantic type is NOT already in the map");
				typeToCount.put(semanticType, count);
			}
			idx++;
		}
		
		return typeToCount;
	}

	/**
	 * Retrieve the children from the server.  Throws an error if this set is not a parent set. 
	 * @throws GPUdbException
	 */
	public void getChildrenFromServer() throws GPUdbException{
		if(!type.isParent()) {
			log.error("Getting children from a non-parent set; probably an error.");
			throw new GPUdbException("Getting children from a non-parent set; probably an error.");
		}
		log.debug("get children from server for set:"+this.id.get_id());

		// reset the children
		this.typeToChildren = new ConcurrentHashMap<Type,NamedSet>();

		// we need to grab the children from the server using the set info
		try {
			set_info_response response = gPUdb.do_set_info(id);
			buildChildren(response);
		} catch(GPUdbException e) {
			if( !e.getMessage().equals("set doesn't exist:MASTER") ) { // This can be ignored.....
				e.printStackTrace();
			}
			// set info throws an error if the set doesn't exist; which can be the case if its just a parent with no children; a bit weird
			log.debug("No children found");
		}		
	}

	/**
	 * Get back the number of children.
	 * @return the number of children
	 */
	public int getNumberOfChildren() {		
		return this.typeToChildren.size();
	}

	/**
	 * Helper method, build the children from the set info response.  Sets the children mapping in this NamedSet.
	 * @param response set info response of this set.
	 */
	public void buildChildren(set_info_response response) {
		// go through types; NOTE: not reseting the child mapping
		List<CharSequence> semanticTypes = response.getSemanticTypes();
		List<CharSequence> labels = response.getLabels();
		List<CharSequence> setIDs = response.getSetIds();
		List<CharSequence> typeIDs = response.getTypeIds();
		List<CharSequence> typeSchemas = response.getTypeSchemas();
		for(int i=0; i<setIDs.size(); i++) {
			Type childType = new Type(typeIDs.get(i).toString(), GenericObject.class, 
					Schema.parse(typeSchemas.get(i).toString()), labels.get(i).toString(), 
					semanticTypes.get(i).toString());
			NamedSet child = new NamedSet(new SetId(setIDs.get(i).toString()), gPUdb, childType); 
			gPUdb.setInStore(child.get_setid(), child);
			this.typeToChildren.put(childType, child);
		}
	}

	/**
	 * Add the NamedSet child to the child mapping in this object. NOTE: This is used in Gpudb.java but is NOT a way of adding a child
	 * set to gpudb server system. Throws if there is already a child with this type.
	 * @param child
	 * @throws GPUdbException
	 */
	public void addChild(Type childType, NamedSet child) throws GPUdbException {
		if(this.typeToChildren.containsKey(childType))
			throw new GPUdbException("There is already a child of this type:"+childType);
		this.typeToChildren.put(childType, child);
	}
	
	public boolean childExists(Type childType) throws GPUdbException {
		return typeToChildren.containsKey(childType);
	}

	/**
	 * Get back the list of objects per key. 
	 * @param child_ids The child sets.
	 * @param ids
	 * @return
	 */
	public EnumMap<SemanticTypeEnum, List<SemanticType>> getObjectsByID(List<CharSequence> child_ids, List<CharSequence> ids) {
		// designed to be run after a shape intersection 
		// need to do a separate getObjectsByID for each of the different semantic types;
		// need to call getObjectsById on each child set
		Map<NamedSet, List<String>> ns_to_ids = new HashMap<NamedSet, List<String>>();				
		EnumMap<SemanticTypeEnum, List<SemanticType>> em = new EnumMap<SemanticTypeEnum, List<SemanticType>>(SemanticTypeEnum.class);
		
		// group the ids based upon their set id
		for(int i=0; i<child_ids.size(); i++) {
			NamedSet ns = gPUdb.getNamedSet(new SetId(child_ids.get(i).toString()));
			if(!ns_to_ids.containsKey(ns)){
				ns_to_ids.put(ns, new ArrayList<String>());			
			}
			log.debug("Add to ns's ids:"+ns.get_setid()+" id:"+ids.get(i)+" i:"+i);
			ns_to_ids.get(ns).add(ids.get(i).toString());
		}
		
		// for each named set call get objects with the appropriate semantic type
		Iterator it = ns_to_ids.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<NamedSet, List<String>> pairs = (Map.Entry<NamedSet, List<String>>)it.next();
			NamedSet key = pairs.getKey();
			log.debug("Calling getObjects on set:"+key+" of semantic type:"+key.getType().getSemanticType()+" pairs.getValue().size():"+pairs.getValue().size());
			if(key.getType().getSemanticType().equals(Polygon.type.toString())) {
				em.put(SemanticTypeEnum.POLYGON2D, key.getObjectsByID(Polygon.class, pairs.getValue()));
			} else if(key.getType().getSemanticType().equals(Line.type.toString())) {
				em.put(SemanticTypeEnum.LINE, key.getObjectsByID(Line.class, pairs.getValue()));
			} else {
				log.warn("Unsupported type:"+key.getType().getSemanticType());
			}
		}				
		
		return em;
	}
	

	public List<SemanticType> getObjectsByID(Class<? extends SemanticType> cls, Collection<String> ids) throws GPUdbException{		
		List<CharSequence> list = new ArrayList<CharSequence>();
		list.addAll(ids);		
		List<SemanticType> objectList = new ArrayList<SemanticType>();

		// NOTE: do I need to do this? all I want is the grouping field name		
		if(cls == Line.class) {
			objectList.addAll(convertToLines((List<GenericObject>)this.get_objects(Line.groupingFieldName, list)));			
		} else if(cls == Polygon.class) {
			objectList.addAll(convertToPolygons((List<GenericObject>)this.get_objects(Polygon.groupingFieldName, list)));
		} else {
			log.error("Unsupported class:"+cls);
			throw new GPUdbException("Unsupported class:"+cls);
		}

		return objectList;
	}

	private List<Polygon> convertToPolygons(List<GenericObject> gos) {
		// the polygon list
		List<Polygon> polygons = new ArrayList<Polygon>();

		try {
			// we are assuming that each "GO" maps to one Polygon
			// now convert the GOs to Polygon; assume there is a string "WKT" all other keys just add to the map
			for(com.gisfederal.GenericObject go : gos) {
				if(!go.dataMap.containsKey("WKT")) {
					log.error("The expected WKT key is not in the object even though its a polygon");
					throw new GPUdbException("The expected WKT key is not in the object even though its a polygon");
				}
	
				// populate the other keys NOTE: keys to ignore? i.e. we don't want "x" and "y" -- what was pulled out of wkt
				Polygon polygon = new Polygon(go.getField("WKT"), go.getField(Polygon.groupingFieldName), new HashMap<String,String>());
				Iterator iter = go.dataMap.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry<String, String> pairs = (Map.Entry<String, String>)iter.next();
					if(!pairs.getKey().equals("WKT") && !pairs.getKey().equals(Polygon.groupingFieldName)) {
						log.debug("add feature:"+pairs.getKey()+" value:"+pairs.getValue());
						polygon.features.put(pairs.getKey(), pairs.getValue());
					}
				}
	
				// add the polygon
				polygons.add(polygon);															
			}	
		} catch( GPUdbException ge ) {
			if( !ge.getMessage().contains(EMPTY_SET_ERROR_MSG)) {
				throw ge;
			}
		}

		return polygons;

	}

	private List<Line> convertToLines(List<GenericObject> gos) throws GPUdbException {
		List<Line> semanticObjs = new ArrayList<Line>();

		// we are assuming that each "GO" maps to one semantic object
		// now convert the GOs; assume there is a string "WKT" all other keys just add to the map
		for(com.gisfederal.GenericObject go : gos) {
			if(!go.dataMap.containsKey("WKT")) {
				log.error("The expected WKT key is not in the object even though its a line");
				throw new GPUdbException("The expected WKT key is not in the object even though its a line");
			}

			// populate the other keys
			Line semanticObj = new Line(go.getField("WKT"), go.getField(Line.groupingFieldName), new HashMap<String,String>());
			Iterator iter = go.dataMap.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)iter.next();
				if(!pairs.getKey().equals("WKT") && !pairs.getKey().equals(Line.groupingFieldName)) {
					semanticObj.features.put(pairs.getKey(), pairs.getValue());
				}
			}

			// add the SEMANTIC object
			semanticObjs.add(semanticObj);												
		}				

		return semanticObjs;
	}

	/**
	 * Return a list of Polygon objects, one for each polygon in this gpudb set.
	 * @param start The start index.
	 * @param end The end index.
	 * @return List of Polygons
	 * @throws GPUdbException
	 */
	public List<Polygon> getPolygons(int start, int end) throws GPUdbException{	
		
		try {
			List<com.gisfederal.GenericObject> gos = (List<com.gisfederal.GenericObject>)this.list(start,end,Polygon.type.toString());
			return convertToPolygons(gos);
		} catch( GPUdbException ge ) {
			if( !ge.getMessage().contains(EMPTY_SET_ERROR_MSG)) {
				throw ge;
			}	
		}
		return Collections.EMPTY_LIST;		
	}

	/**
	 * Returns a list of objects which are of semantic type point.  Each point is built into a Point object.
	 * @param start The start index.
	 * @param end The end index.
	 * @return List of Point2Ds.
	 * @throws GPUdbException
	 */
	public List<Point2D> getPoints(long start, long end) throws GPUdbException{
		// the polygon list
		List<Point2D> semanticObjs = new ArrayList<Point2D>();

		try {
			// now check the semantic type; only interested in ploygon
			String semanticType = "POINT";
	
			List<com.gisfederal.GenericObject> goList;
	
			goList = (List<com.gisfederal.GenericObject>)this.list(start,end,semanticType);
	
			// we are assuming that each "GO" maps to one Polygon
			// now convert the GOs to Polygon; assume there is a string "WKT" all other keys just add to the map
			for(com.gisfederal.GenericObject go : goList) {
				if(!go.dataMap.containsKey("x") || !go.dataMap.containsKey("y")) {
					log.error("The expected x and y key are not in the object even though its a "+semanticType);
					throw new GPUdbException("The expected x and y key are not in the object even though its a "+semanticType);
				}
	
				// populate the other keys NOTE: keys to ignore? i.e. we don't want "x" and "y" -- what was pulled out of wkt
				Point2D point = new Point2D(Double.parseDouble(go.dataMap.get("x")), Double.parseDouble(go.dataMap.get("y")), new HashMap<String,String>());
				Iterator iter = go.dataMap.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry<String, String> pairs = (Map.Entry<String, String>)iter.next();
					//if(!go.dataMap.containsKey("x") && !go.dataMap.containsKey("y")) {
					if(!pairs.getKey().equals("x") && !pairs.getKey().equals("y")) {
						log.debug("add feature:"+pairs.getKey()+" value:"+pairs.getValue());
						point.features.put(pairs.getKey(), pairs.getValue());
					}
				}
	
				// add the polygon
				semanticObjs.add(point);					
			}	
		} catch ( GPUdbException ge ) {
			if( !ge.getMessage().contains(EMPTY_SET_ERROR_MSG)) {
				throw ge;
			}
		}
		return semanticObjs;
	}

	/**
	 * Returns a list of objects which are of the generic semantic type. 
	 * @param start The start index.
	 * @param end The end index.
	 * @return List of GenericSemanticType.
	 * @throws GPUdbException
	 */
	public List<GenericSemanticType> getGenericSemanticObjects(long start, long end) throws GPUdbException{
		// the list of genric objects
		List<GenericSemanticType> semanticObjs = new ArrayList<GenericSemanticType>();

		// now check the semantic type;
		String semanticType = SemanticTypeEnum.GENERICOBJECT.toString();

		List<com.gisfederal.GenericObject> goList;

		goList = (List<com.gisfederal.GenericObject>)list(start, end, semanticType);

		// convert them; NOTE: maybe we should just return com.gisfederal.GenericObjects
		for(com.gisfederal.GenericObject go : goList) {
			// NOTE no assumed keys

			// add the object
			semanticObjs.add(new GenericSemanticType(go.dataMap));					
		}

		return semanticObjs;
	}

	/**
	 * Return a list of objects with semantic type Line.
	 * @param start The start index.
	 * @param end The end index.
	 * @return List of Line objects.
	 * @throws GPUdbException
	 */
	public List<Line> getLines(long start, long end) throws GPUdbException{
		
		try {
			List<com.gisfederal.GenericObject> gos = (List<com.gisfederal.GenericObject>)this.list(start,end,Line.type.toString());
			return convertToLines(gos);
		} catch( GPUdbException ge ) {
			if( !ge.getMessage().contains(EMPTY_SET_ERROR_MSG)) {
				throw ge;
			}	
		}
		return Collections.EMPTY_LIST;		
	}

	/**
	 * Return all the 3D Polygons in this set.
	 * @param start The start index.
	 * @param end The end index.
	 * @return List of Polygon.
	 * @throws GPUdbException
	 */
	public List<Polygon> getPolygon3Ds(long start, long end) throws GPUdbException{
		// the polygon list
		List<Polygon> polygons = new ArrayList<Polygon>();

		// now check the semantic type; only interested in polygon
		String semanticType = SemanticTypeEnum.POLYGON3D.toString(); 

		// all these children are of this semantic type
		List<com.gisfederal.GenericObject> goList;

		goList = (List<com.gisfederal.GenericObject>)list(start, end, semanticType);

		// we are assuming that each "GO" maps to one Polygon
		// now convert the GOs to Polygon; assume there is a string "WKT" all other keys just add to the map
		for(com.gisfederal.GenericObject go : goList) {
			if(!go.dataMap.containsKey("WKT")) {
				log.error("The expected WKT key is not in the object even though its a "+semanticType);
				throw new GPUdbException("The expected WKT key is not in the object even though its a "+semanticType);
			}

			// populate the other keys NOTE: keys to ignore? i.e. we don't want "x" and "y" -- what was pulled out of wkt
			Polygon polygon = new Polygon(go.getField("WKT"), go.getField(Polygon.groupingFieldName), new HashMap<String,String>());
			Iterator iter = go.dataMap.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)iter.next();
				if(!pairs.getKey().equals("WKT") && !pairs.getKey().equals(Polygon.groupingFieldName)) {
					log.debug("add feature:"+pairs.getKey()+" value:"+pairs.getValue());
					polygon.features.put(pairs.getKey(), pairs.getValue());
				}
			}

			// add the polygon
			polygons.add(polygon);															
		}							

		return polygons;
	}

	/**
	 * Return a list of the objects who are of semantic type time.
	 * @param start The start index.
	 * @param end The end index.
	 * @return List of Time.
	 * @throws GPUdbException
	 */
	public List<Time> getTimes(int start, int end) throws GPUdbException{
		// the polygon list
		List<Time> semanticObjs = new ArrayList<Time>();

		// now check the semantic type;
		String semanticType = SemanticTypeEnum.TIME.toString();

		List<com.gisfederal.GenericObject> goList;

		goList = (List<com.gisfederal.GenericObject>)list(start, end, semanticType);

		// we are assuming that each "GO" maps to one Polygon
		// now convert the GOs to Polygon; assume there is a string "WKT" all other keys just add to the map
		for(com.gisfederal.GenericObject go : goList) {
			if(!go.dataMap.containsKey("TIMESTAMP")) {
				log.error("The expected TIMESTAMP key are not in the object even though its a "+semanticType);
				throw new GPUdbException("The expected TIMESTAMP key are not in the object even though its a "+semanticType);
			}

			// populate the other keys NOTE: keys to ignore? i.e. we don't want "x" and "y" -- what was pulled out of wkt
			Time time = new Time(Double.parseDouble(go.dataMap.get("TIMESTAMP")), new HashMap<String,String>());
			Iterator iter = go.dataMap.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)iter.next();
				//if(!go.dataMap.containsKey("TIMESTAMP")) {
				if(!pairs.getKey().equals("TIMESTAMP")) {
					log.debug("add feature:"+pairs.getKey()+" value:"+pairs.getValue());
					time.features.put(pairs.getKey(), pairs.getValue());
				}
			}

			// add the polygon
			semanticObjs.add(time);					
		}					
		return semanticObjs;
	}

	// This is new and improved gettracks() API....and the only API...
	public List<Track> getTracks(NamedSet world, int start , int end, double min_x, double min_y, double max_x, double max_y) throws GPUdbException {
		
		List<Track> tracks = new ArrayList<Track>();
		
		boolean doExtent = (min_x <= -180 && max_x >= 180 && min_y <= -90 && max_y >= 90) ? false : true; 

		get_tracks2_response response = gPUdb.do_get_tracks(this, world, start, end, min_x, min_y, max_x, max_y, doExtent);
		
		log.debug("get tracks response - number of tracks: " + response.getSetIds().size());

		// We have a bunch of set ids. They may be from different types. So bunch them all together by type
		
		NamedSet setForType = null;
		List<SetId> setIDs = new ArrayList<SetId>();
		List<CharSequence> setIdStrings = response.getSetIds();
		for( CharSequence cs : setIdStrings ) {
			setIDs.add(new SetId(cs.toString()));
		}

		// convert the encoded objects
		java.util.List<java.util.List<java.nio.ByteBuffer>> list_of_lists_encoded = response.getLists();

		int index = 0;
		List<List<Object>> listOfLists = new ArrayList<List<Object>>();
		for( int ii = 0; ii < list_of_lists_encoded.size(); ii++ ) {
			List<ByteBuffer> list_encoded = list_of_lists_encoded.get(ii);
			CharSequence setId = setIdStrings.get(ii);
			List<Object> list_decoded = new ArrayList<Object>();
			// go through decoding each encoded object and adding it
			for(ByteBuffer bytes : list_encoded) {
				list_decoded.add(gPUdb.getNamedSet(new SetId(setId.toString())).getType().decode(bytes));
			}
			listOfLists.add(list_decoded);			
		}

		// convert into the List<Track> every "list" collapses to one track object			
		for(int i=0; i<listOfLists.size(); i++) {
			List<Object> listGOs = listOfLists.get(i);			
			Track track = new Track();
			track.setSetID(setIDs.get(i));
			log.debug("number of track points in this track:"+listGOs.size());
			for(Object trackGO : listGOs) {
				// iterate through this track point
				Iterator trackIter = ((GenericObject)trackGO).dataMap.entrySet().iterator();
				Track.TrackPoint onePoint = new Track.TrackPoint();
				while(trackIter.hasNext()) {
					Map.Entry<String, String> pairs = (Map.Entry<String, String>)trackIter.next();
					// add to the features if need be
					String key = pairs.getKey();
					String value = pairs.getValue();
					log.debug("key:"+key+" value:"+value);					
					if(key.equals("x")) {
						onePoint.setX(Double.parseDouble(value));
					} else if(key.equals("y")) {
						onePoint.setY(Double.parseDouble(value));
					} else if(key.equals("z")) {
						onePoint.setZ(Double.parseDouble(value));
					} else if(key.equals("TIMESTAMP")) {
						onePoint.setTime(Double.parseDouble(value));
					} else if(key.equals(Track.groupingFieldName)) {
						// only needs to get set once..do it anyway
						track.groupingField = value;
					} else {
						onePoint.getFeatures().put(key, value);
					}						
				}
				track.addTrackPoint(onePoint);
				log.debug("track point:"+track.toString());
			}
			tracks.add(track);
			log.debug("add track with id:"+track.groupingField);
		}

		return tracks;
	}
	
	// Statistics returns a map of statistics for requested STAT fields ("mean,stdv,esimtated_cardinality")
	public Map<String, Double> statistics (List<StatisticsOptionsEnum> stats, String attribute) throws GPUdbException {
		
		Map<String, Double> result = new HashMap<String, Double>();
		
		statistics_response response = gPUdb.do_statistics(this, stats, attribute);
		
		log.debug("statistics response - number of stats: " + response.getStats().size());
		
		Map<CharSequence, Double> map = response.getStats();
		for (CharSequence key : map.keySet()) {
			result.put(key.toString(), map.get(key));
		}
		
		return result;
	}
		
	// Helper method for statistics assumes estimated cardinality as the STAT
	public boolean canFacetAttribute(String attribute, Integer limit) {
	
		List<StatisticsOptionsEnum> stats = new ArrayList<StatisticsOptionsEnum>();
		stats.add(StatisticsOptionsEnum.ESTIMATED_CARDINALITY);
		
		statistics_response response = gPUdb.do_statistics(this, stats, attribute);
		
		Map<CharSequence, Double> map = response.getStats();
		Map<String, Double> result = new HashMap<String, Double>();
		for (CharSequence key : map.keySet()) {
			result.put(key.toString(), map.get(key));
		}
		Double res = result.get(StatisticsOptionsEnum.ESTIMATED_CARDINALITY.value());
		
		return (res < (limit == null? 1000: limit));
	}
	
	/**
	 * Return an iterator for the set.
	 */
	public NamedSetIterator iterator() {
		return new NamedSetIterator(this, pageSize);
	}

	/**
	 * Set the page size for the iterator.
	 */
	public void setPageSize(int size){pageSize=size;	}

	/**
	 * Get the page size for the iterator.
	 */
	public int getPageSize(){return pageSize;	}


	/** 
	 * Set the bulk add limit.
	 * @param bulkAddLimit The new limit.
	 */
	public void setBulkAddLimit(int bulkAddLimit) {
		this.bulkAddLimit = bulkAddLimit;
	}

	/**
	 * Return the size of this set (i.e. the number of objects in it). This will hit the server.
	 * @return size of this set
	 */
	public long size() {
		
		status_response response = gPUdb.do_status(this);
		return response.getTotalSize();
	}
	
	/**
	 * Return the element size of this set i.e., the total number of elements within the set. For polygons ( points, lines, generic objects, events) e.g., 
	 * the number of polygons is equal to the number of objects which is also equal to the number of elements in GPUDB. For tracks, the number of tracks is
	 * equal to the number of objects but the total number of trackpoints making up those tracks is equal to the number of elements for the 
	 * track set. 
	 * This will hit the server.
	 * @return size of this set
	 */
	public long fullSize() {
		status_response response = gPUdb.do_status(this);
		return response.getTotalFullSize();
	}

	/**
	 * Get the type.
	 * @return The type object for this set.
	 */
	public Type getType(){
		return this.type;
	}

	/**
	 * Set the type object.
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * Update an object in this set. 
	 * @param obj The java object to add to the gpudb set.
	 * @param objectId The objectId to update.
	 * @return An add object response which has the object id for this object.
	 * @throws GPUdbException
	 */
	public update_object_response update(Object obj, String objectId) {
		return gPUdb.do_update_object(this, obj, objectId);
	}


	/**
	 * Add an object to this set. Throws if you use on a parent set.
	 * @param obj The java object to add to the gpudb set.
	 * @return An add object response which has the object id for this object.
	 * @throws GPUdbException
	 */
	public add_object_response add(Object obj) {
		if(type.isParent()){
			log.error("Can't add an object directly to a parent set");
			throw new GPUdbException("Can't add an object directly to a parent set");
		}
		return gPUdb.do_add_object(this, obj);
	}

	/**
	 * Add a list of object all at once to this set. If the list is greater than the bulk add limit then the objects will be sent on multiple
	 * bulk adds.
	 * @param list_obj The list of objects to add.
	 * @return A bulk add response object that has the object ids for each of the added objects.
	 */
	// NOTE: throw if a parent
	public bulk_add_response add_list(List<Object> list_obj) {
		if(list_obj.size() > this.bulkAddLimit){
			log.debug("Bulk add list greater than limit; limit:"+this.bulkAddLimit+" size:"+list_obj.size());
			int listSize = list_obj.size();
			
			int startIndex = 0;
			int endIndex = this.bulkAddLimit;
			bulk_add_response response; // warning! get's overriden every time
			List<Object> sublist;			
			do {
				sublist = list_obj.subList(startIndex, endIndex); // a list of length bulk add limit [or less if this is the last loop]
				response = gPUdb.do_add_object_list(sublist, this);

				// update the indices
				startIndex = endIndex;
				endIndex += this.bulkAddLimit;
				if(endIndex > listSize) {
					endIndex = listSize;
				}
			}while (startIndex <= (listSize-1));

			return response; // can't trust this, in the sense that not all obj_ids etc. will be there...
		} else {
			if( list_obj.size() < 1 ) {
				throw new GPUdbException( "List of objects to add is empty !!");
			}

			return gPUdb.do_add_object_list(list_obj, this);
		}
	}


	/**
	 * Get objects whose attribute matches one of the values.
	 * @param attribute
	 * @param values
	 * @return List of objects.
	 * @throws GPUdbException
	 */
	public List get_objects(String attribute, List<CharSequence> values) throws GPUdbException {		
		if(this.type.isParent()) 
			throw new GPUdbException("Can't call this function on a parent set; id:"+this.id.get_id());
		
		get_objects_response response = gPUdb.do_get_objects(this.id, attribute, values);
		List objectList = new ArrayList();

		List<ByteBuffer> bytesList = response.getList();
		for(ByteBuffer bytes : bytesList){
			// binary decode
			objectList.add(this.type.decode(bytes));
			log.debug("Added object to the list");			
		}

		return objectList;
	}

	/**
	 * Return a list of the objects in this named set between start and end.
	 * @param start The start index, starts at 0 and is inclusive.
	 * @param end The end index, inclusive.
	 * @return The list of objects in this named set.
	 */
	public List list(long start, long end) {
		log.debug("in list; start:"+start+" end:"+end);
		get_set_objects_response response = gPUdb.do_list_fast(this, start, end);
		List<ByteBuffer> bytesList = response.getList();
		List objectList = createObjectList(bytesList);
		return objectList;
	}
	
	/**
	 * Return a list of the objects in this named set between start and end with a specified sort attribute.
	 * @param start The start index, starts at 0 and is inclusive.
	 * @param end The end index, inclusive.
	 * @param attribute the attribute to sort on
	 * @return The list of objects in this named set.
	 */
	public List listSorted(long start, long end, String attribute) {
		log.debug("in listsorted; start:"+start+" end:"+end + " attribute:"+attribute);
		get_sorted_set_response response = gPUdb.do_get_sorted_set(this, attribute, start, end);
		List<ByteBuffer> bytesList = response.getList();
		List objectList = createObjectList(bytesList);
		return objectList;
	}

	private List createObjectList(List<ByteBuffer> bytesList) {
		Schema schema = this.type.getAvroSchema();
		log.debug("schema "+schema);

		List objectList = new ArrayList();
		for(ByteBuffer bytes : bytesList){
			// binary decode
			objectList.add(this.type.decode(bytes));
			log.debug("Added object to the list");			
		}
		return objectList;
	}

	/**
	 * Return a list of the objects in this named set of the given semantic type between start and end.
	 * @param start The start index, starts at 0 and is inclusive.
	 * @param end The end index, inclusive.
	 * @param semantic_type The semantic type of the objects of interest
	 * @param semanticChildrenTypes 
	 * @return The list of objects in this named set.
	 */
	public List list(long start, long end, String semantic_type) {
		log.debug("in list; start:"+start+" end:"+end);

		// do a list [NOTE: so assuming we have gotten the children from the server]
		List<NamedSet> children = new ArrayList<NamedSet>();
		if(this.type.isParent()) {
			children.addAll(this.typeToChildren.values());
		} else {
			children = Arrays.asList(this);
		}

		Map<CharSequence,Type> typeMap = new HashMap<CharSequence,Type>();

		for(NamedSet child : children) {
			if(child.getType().getSemanticType().equals(semantic_type)) {
				typeMap.put(child.type.getID(), child.type);
			}
		}				

		get_set_response response;
		response = gPUdb.do_list(this, start, end, semantic_type);

		Schema schema = this.type.getAvroSchema();
		log.debug("schema "+schema);

		List objectList = new ArrayList();

		List<ByteBuffer> bytesList = response.getList();
		List<CharSequence> typeIds = response.getTypeIds();

		//for each encoded object
		//{
		//	lookup the corresponding Type
		//	decode the object using the right Type
		//}

		int i = 0;
		for(ByteBuffer bytes : bytesList){
			// binary decode
			CharSequence seq = typeIds.get(i);
			Type type = typeMap.get(seq.toString());
			objectList.add(type.decode(bytes));
			log.debug("Added object to the list");			
			i++;
		}

		return objectList;
	}

	/**
	 * Run a bounding box calculation on this set.
	 * @param x_attribute The name of the "x" attribute.
	 * @param y_attribute The name of the "y" attribute.
	 * @param min_x The min value of the the "x" attribute.
	 * @param max_x The max value of the the "x" attribute.
	 * @param min_y The min value of the the "y" attribute.
	 * @param max_y The max value of the the "y" attribute.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet bounding_box(String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y) throws GPUdbException {

		SetId rs = gPUdb.new_setid();
		bounding_box_response response = gPUdb.do_bounding_box(this, rs, x_attribute, y_attribute, min_x, max_x, min_y, max_y);
		log.debug("bounding box count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	public NamedSet filterByValue(double value, String attribute) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_value_response response = gPUdb.do_filter_by_value(this, rs, false, value, "", attribute);
		log.debug("filter by value count:"+response.getCount());
		return gPUdb.getNamedSet(rs);
	}

	public NamedSet filterByValue(String value, String attribute) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_value_response response = gPUdb.do_filter_by_value(this, rs, true, 0.0, value, attribute);
		log.debug("filter by value count:"+response.getCount());
		return gPUdb.getNamedSet(rs);
	}

	
	/**
	 * Run a bounding box calculation on this set.
	 * @param min_x The min value of the the "x" attribute.
	 * @param max_x The max value of the the "x" attribute.
	 * @param min_y The min value of the the "y" attribute.
	 * @param max_y The max value of the the "y" attribute.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet bounding_box(double min_x, double max_x, double min_y, double max_y) throws GPUdbException {
		return this.bounding_box("x", "y", min_x, max_x, min_y, max_y);
	}

	/**
	 * Run a select calculation on this set.
	 * @param expression The expression string.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet select(String expression) throws GPUdbException {

		SetId rs = gPUdb.new_setid();
		select_response response = gPUdb.do_select(this, rs, expression);
		log.debug("select count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Run a filter_by_list calculation on this set.
	 * @param attribute_map The map of attributes and values the attributes must be.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_list(Map<CharSequence, List<CharSequence>> attribute_map) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_list_response response = gPUdb.do_filter_by_list(this, rs, attribute_map);
		log.debug("filter by list count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}
	
	/**
	 * Run a filter_by_set calculation on this set.
	 * @param attribute - this set's attribute we are interested in
	 * @param sourceSetId - the source set where the filter data is present
	 * @param source_attribute - the attribute on the source set
	 * @return
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_set(String attribute, SetId sourceSetId, String source_attribute) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_set_response response = gPUdb.do_filter_by_set(this, rs, attribute, sourceSetId, source_attribute);
				
		log.debug("filter by set count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Run a filter_by_list calculation on this set.
	 * @param attribute The attribute to filter on.
	 * @param values The list of values that we are trying to match on.
	 * @return The NamedSet consisting of the objects that matched.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_list(String attribute, List<CharSequence> values) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_list_response response = this.gPUdb.do_filter_by_list(this, rs, attribute, values);
		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Delete an object from this set.
	 * @param objId The objectId to delete.
	 * @return A status for the delete operation.
	 * @throws GPUdbException
	 */
	public String do_delete_object(String objId) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		delete_object_response response = gPUdb.do_delete_object(this, objId);
		log.debug("delete object status:"+response.getStatus());

		return response.getStatus().toString();
	}

	
	/**
	 * Run a filter_by_list calculation on this set.
	 * @param attribute The attribute to compare.
	 * @param value The filter value for this attribute
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_list(String attribute, String value) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_list_response response = gPUdb.do_filter_by_list(this, rs, attribute, value);
		log.debug("filter by list count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Run a filter_by_bounds calculation on this set.
	 * @param attribute The name of the attribute that we going to test the bounds on.
	 * @param lower_bounds The lower bound for the given attribute.
	 * @param upper_bounds The upper bound for the given attribute.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_bounds(String attribute, double lower_bounds, double upper_bounds) throws GPUdbException {
		this.log.debug("Do filter by bounds");

		SetId rs = gPUdb.new_setid();
		filter_by_bounds_response response = gPUdb.do_filter_by_bounds(this, rs, attribute, lower_bounds, upper_bounds);
		log.debug("filter by bounds count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Run a filter_by_time calculation on this set.
	 * @param lower_bounds The lower bound for the time.
	 * @param upper_bounds The upper bound for the time.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_time(double lower_bounds, double upper_bounds) throws GPUdbException{
		return this.do_filter_by_bounds("TIMESTAMP", lower_bounds, upper_bounds);
	}

	/**
	 * Run a filter_by_nai calculation on this set.
	 * @param x_attribute The name of the x-attribute
	 * @param x_vector The list of x values.
	 * @param y_attribute The name of the y-attribute
	 * @param y_vector The list of y values.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_nai(String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector) throws GPUdbException {
		SetId rs = gPUdb.new_setid();
		filter_by_nai_response response = gPUdb.do_filter_by_nai(this, rs, x_attribute, x_vector, y_attribute, y_vector);
		log.debug("filter by nai count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Run a filter_by_nai calculation on this set.
	 * @param x_vector The list of x values.
	 * @param y_vector The list of y values.
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_nai(List<Double> x_vector, List<Double> y_vector) throws GPUdbException {
		return this.do_filter_by_nai("x", x_vector, "y", y_vector);
	}

	/**
	 * Run a filter_by_radius calculation on this set.
	 * @param x_attribute The name of the x-attribute
	 * @param y_attribute The name of the y-attribute
	 * @param x_center The center point x coordinate (i.e. longitude)
	 * @param y_center The center point y coordinate (i.e. latitude)
	 * @param radius The search radius (in meters)
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_radius(String x_attribute, String y_attribute, Double x_center, Double y_center, Double radius) throws GPUdbException {

		SetId rs = gPUdb.new_setid();
		filter_by_radius_response response = gPUdb.do_filter_by_radius(this, rs, x_attribute, y_attribute, x_center, y_center, radius);
		log.debug("filter by radius count:"+response.getCount());

		return gPUdb.getNamedSet(rs);
	}

	/**
	 * Run a filter_by_radius calculation on this set.
	 * @param x_center The center point x coordinate (i.e. longitude)
	 * @param y_center The center point y coordinate (i.e. latitude)
	 * @param radius The search radius (in meters)
	 * @return The NamedSet consisting of the results of this calculation.
	 * @throws GPUdbException
	 */
	public NamedSet do_filter_by_radius(Double x_center, Double y_center, Double radius) throws GPUdbException {
		return this.do_filter_by_radius("x", "y", x_center, y_center, radius);
	}

	/**
	 * Return the unique values and associated count for this attribute in this set.
	 * @param attribute The attribute to group on.
	 * @return Map contains a map of the values to counts.
	 * @throws GPUdbException
	 */
	public Map<String, Integer> do_group_by(String attribute) throws GPUdbException {
		group_by_response response =  gPUdb.do_group_by(this, Arrays.asList(attribute));
		Map<CharSequence, List<CharSequence>> count_map = response.getCountMap();

		// NOTE: so the CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, Integer> group_count_map = new HashMap<String, Integer>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();
				String str_key = key.toString();
				List<CharSequence> utf8_list = count_map.get(key);
				org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)utf8_list.get(0);
				group_count_map.put(str_key, Integer.valueOf(value.toString()));
			} catch(Exception e) {
				log.error(e.toString());
				throw new GPUdbException(e.getMessage());
			}
		}		
		return group_count_map;
	}

	// Accepts a value_attribute field in computing the sum of the value_attribute field. 
	// If the value_attribute is "" then it behaves like do_group_by and returns counts of group_by fields
	public Map<String, Double> do_group_by_value(String attribute, String value_attribute) throws GPUdbException {
		group_by_value_response response =  gPUdb.do_group_by_value(this, Arrays.asList(attribute), value_attribute);
		Map<CharSequence, Double> count_map = response.getCountMap();

		Map<String, Double> group_count_map = new HashMap<String, Double>();
		for (CharSequence key : count_map.keySet()) {
			try {
				group_count_map.put(key.toString(), count_map.get(key));
			} catch (Exception e) {
				log.error(e.toString());
				throw new GPUdbException(e.getMessage());
			}
		}
		return group_count_map;
	}

	/**
	 * Get the NamedSet that is a child of this set whose type id matches the passed in one. If this set has no children then check to see if 
	 * it matches this type and return it if it does.  Doesn't go to the server. If you think there have been new children added you can call 
	 * getChildrenFromServer() first. 
	 * @param typeID The type id.  Empty string is *not* a wild card. 
	 * @return The NamedSet whose type has the given id.
	 * @throws GPUdbException if there is not child set with this type id
	 */	
	public NamedSet do_get_sets_by_type_id(String typeID) throws GPUdbException{
		List<NamedSet> sets = this.do_get_sets_by_type_info(typeID, "", "");
		if(sets.size() == 1) {
			return sets.get(0);
		} else if(sets.size() == 0) {
			throw new GPUdbException("No children with this typeID:"+typeID);
		} else {
			log.warn("Multiple children with the same type id:"+typeID+" children:"+sets.size());
			return sets.get(0);
		}
	}		

	/**
	 * Get the NamedSets that are children of this set whose type label matches the passed in one with "" as a wild card. If this set has no
	 * children then check to see if it matches this type and return it if it does. Doesn't go to the server. If you think there have been 
	 * new children added you can call getChildrenFromServer() first.
	 * @param label The type label or empty string. 
	 * @return A List of NamedSets whose types matched.
	 */	
	public List<NamedSet> do_get_sets_by_type_label(String label) {
		return this.do_get_sets_by_type_info("", label, "");
	}

	/**
	 * Get the NamedSets that are children of this set whose semantic type matches the passed in one with "" as a wild card. If this set has no
	 * children then check to see if it matches this type and return it if it does.  Doesn't go to the server. If you think there have been 
	 * new children added you can call getChildrenFromServer() first.
	 * @param semanticType The semantic type or empty string. 
	 * @return A List of NamedSets whose types matched.
	 */	
	public List<NamedSet> do_get_sets_by_semantic_type(String semanticType) {
		return this.do_get_sets_by_type_info("", "", semanticType);
	}

	/**
	 * Get the NamedSets that are children of this set whose semantic type matches the passed in parameters with "" as a wild card. If this set has no
	 * children then check to see if it matches this type and return it if it does.  Doesn't go to the server. If you think there have been 
	 * new children added you can call getChildrenFromServer() first.
	 * @param semanticType The semantic type or empty string. 
	 * @param label The type label or empty string.
	 * @return A List of NamedSets whose types matched.
	 */
	public List<NamedSet> do_get_sets_by_type_info(String label, String semanticType) {
		return this.do_get_sets_by_type_info("", label, semanticType);
	}

	/**
	 * Get the NamedSets that are children of this set whose type matches the passed in parameters with "" as a wild card. If this set has no
	 * children then check to see if it matches this type and return it if it does. Doesn't go to the server. If you think there have been 
	 * new children added you can call getChildrenFromServer() first.
	 * @param typeID The type id or empty string. 
	 * @param label The type label or empty string.
	 * @param semanticType The semantic type or empty string.
	 * @return A List of NamedSets whose types match the passed in parameters.
	 */
	private List<NamedSet> do_get_sets_by_type_info(String typeID, String label, String semanticType) {		
		// NOTE: it makes no sense to specify both typeID and anything else because the type id includes the label and semantic type
		List<NamedSet> matches = new ArrayList<NamedSet>();
		if(!this.getType().isParent()) {
			// does the type match?
			if((typeID.equals("") || this.type.getID().equals(typeID)) &&
					(label.equals("") || this.type.getTypeLabel().equals(label)) &&
					(semanticType.equals("") || this.type.getSemanticType().equals(semanticType))) {
				matches.add(this);
			}
		} else {
			Collection<NamedSet> children = this.getChildren();			
			for(NamedSet child : children) {
				Type childType = child.getType();
				if((typeID.equals("") || childType.getID().equals(typeID)) &&
						(label.equals("") || childType.getTypeLabel().equals(label)) &&
						(semanticType.equals("") || childType.getSemanticType().equals(semanticType))) {
					matches.add(child);
				}
			}
		}

		return matches;
	}

	/**
	 * Perform a histogram calculation on time for this set with the interval given.  The interval is used to produce bins of that size and 
	 * we return a count of the number of objects within those bins.  The first bin is the multiplicity of the set min time.  	
	 * @param interval The interval (bin length)
	 * @return histogram_response
	 * @throws GPUdbException
	 */
	public histogram_response do_histogram(long interval, Map<CharSequence,	CharSequence> params) throws GPUdbException {
		return gPUdb.do_histogram(this, "TIMESTAMP", interval, params);
	}

	/**
	 * Change the TTL (time to live) for the set.  The TTL is in minutes.  So if you specify 10 on this set the set should be gone some time after
	 * 10 minutes.
	 * @param ttl The time to live for this set in minutes.
	 */
	public void do_update_ttl(int ttl) {
		this.gPUdb.do_update_ttl(this.id, ttl);
	}
	
	/**
	 * Find the unique values of a specified attribute
	 * @param attribute - attribute of the set for which unique values are sought
	 */
	public unique_response do_unique(String attribute) {
		return this.gPUdb.do_unique(this, attribute);
	}
	
	/**
	 * Find the unique values of a specified attribute
	 * @param attribute - attribute of the set for which unique values are sought
	 */
	public List<CharSequence> getUniqueTrackIds(String attribute) {
		unique_response tr = this.gPUdb.do_unique(this, Track.groupingFieldName);
		return tr.getValuesStr();
	}



	/**
	 * Turn off analytic.  This analytic is designed to be perform on a set with tracks/groups in them.  These tracks are specified by the track id.
	 * The tracks are ordered from time.  We are looking for consecutive points in each track who differ by "threshold" or more in time. The first
	 * point in the pair is the "turn off" and the second is the turn back on moment.
	 * @param threshold 
	 * @return Map<String, List<PointPair>>
	 */	
	public Map<String, List<PointPair>> do_turn_off(double threshold) throws GPUdbException {		
		Map<String, List<PointPair>> track_map = new HashMap<String, List<PointPair>>();		

		// NOTE: check that this is of type track!
		turn_off_response response = this.gPUdb.do_turn_off(this.id, "TRACKID", "TIMESTAMP", "x", "y", threshold);
		//turn_off_response response = this.gpudb.do_turn_off(this.id, "group_id", "timestamp", "x", "y", threshold);
		log.debug("Got the turn off response:"+response.toString());

		// convert the map
		Map<CharSequence, List<Double>> encoded_track_map = response.getMap();
		Iterator it = encoded_track_map.entrySet().iterator();
		log.debug("Begin iteration; hasNext():"+it.hasNext());
		while(it.hasNext()) {
			// go through the value vector
			Map.Entry pairs = (Map.Entry<CharSequence,List<Double>>)it.next();
			String track_id = pairs.getKey().toString();
			log.debug("track_id:"+track_id);

			List<Double> values = (List<Double>)pairs.getValue();
			List<PointPair> pointPairs = new ArrayList<PointPair>();

			// the values are encoded in blocks of six (x_1,y_1,t_1,x_2,y_2,t_2)
			// NOTE: probably going to remove any zero length key-value pairs server side
			log.debug("the length of the values:"+values.size()+" for key:"+track_id);
			for(int i=0; i<values.size(); i+=6) {								
				try {
					pointPairs.add(new PointPair
							(new PointWithTime(values.get(i),values.get(i+1), values.get(i+2)), new PointWithTime(values.get(i+3),values.get(i+4), values.get(i+5))));

				} catch (Exception e) {
					throw new GPUdbException("Error parsing the turn off response map");
				}
			}

			track_map.put(track_id, pointPairs);
		}

		return track_map;
	}	

	/**
	 * Return back a set of joined duos of tracks.  Find pairs of tracks that are in the same area (defined by the distance) at the same time 
	 * (defined by the time threshold).
	 * @param distance The distance.
	 * @param time_threshold The time.
	 * @return A NamedSet that is the result of the join.
	 */
	public NamedSet do_dynamic_duo(double distance, double time_threshold) {
		NamedSet childTrack = null; // the set with Track data		
		if(this.type.isParent()) {
			// find the child who is of type track
			Iterator iter = this.typeToChildren.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry<Type, NamedSet> entry = (Map.Entry<Type, NamedSet>)iter.next();
				NamedSet child = entry.getValue();
				Type childType = entry.getKey();

				if(childType.getSemanticType().equals(SemanticTypeEnum.TRACK.toString())) {
					log.debug("got the child of type track; child id:"+child.get_setid().get_id());
					childTrack = child;
					break; // assumes just one
				}
			}
		} else if(this.type.getSemanticType().equals(SemanticTypeEnum.TRACK.toString())){
			// not a parent and the type is track
			childTrack = this;
		}

		SetId rs = this.gPUdb.new_setid();
		log.debug("childTrack:"+childTrack.get_setid());
		predicate_join_response response = this.gPUdb.do_dynamic_duo(childTrack, "x", "y", "TIMESTAMP", "TRACKID", distance, time_threshold, rs);
		log.debug("dynamic duo response count:"+response.getCount());

		return this.gPUdb.getNamedSet(rs);
	}

	public spatial_set_query_response do_spatial_set_query(CharSequence wkt_attr_name, CharSequence wkt_string, SpatialOperationEnum operation) {
		return this.gPUdb.do_spatial_set_query(Arrays.asList(this.id), wkt_attr_name, wkt_string, operation);
	}
	
	/**
	 * 
	 * @param attribute
	 * @param lowest
	 * @param highest
	 * @param grouping_attribute
	 * @return register_trigger_range_response 
	 * @throws GPUdbException
	 */
	public register_trigger_range_response do_register_trigger(String attribute, double lowest, double highest, String grouping_attribute) throws GPUdbException{
		return gPUdb.do_register_trigger(this.id, attribute, lowest, highest, grouping_attribute);
	}
	
	/**
	 * Helper API to get the bounding box of a set. Assumes that set data has x and y attributes.
	 * @return Rectangle2D
	 */
	public Rectangle2D getBoundingBox() {
		max_min_response xresp = gPUdb.do_max_min(this,"x");
		max_min_response yresp = gPUdb.do_max_min(this,"y");
		return new Rectangle2D.Double(xresp.getMin(), yresp.getMin(), xresp.getMax() - xresp.getMin(), 
				yresp.getMax() - yresp.getMin());
	}
	
	/**
	 * Get the set id
	 * @return SetId The id/guid for this named set.
	 */
	public SetId get_setid() {
		return id;
	}

	private void initialize(SetId id, GPUdb gPUdb, Type type) {
		this.id = id;
		this.gPUdb = gPUdb;
		this.log = Logger.getLogger(NamedSet.class);

		// set the type
		this.type = type;
		this.typeToChildren = new ConcurrentHashMap<Type,NamedSet>();
	}

	/**
	 * Build the named set NOTE: don't use this; construct from a gpudb object.
	 */	
	public NamedSet(SetId id, GPUdb gPUdb, Type type) {
		initialize(id, gPUdb, type);
	}		
}