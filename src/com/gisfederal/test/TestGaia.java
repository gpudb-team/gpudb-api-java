package com.gisfederal.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;







import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import avro.java.gaia.add_object_response;
import avro.java.gaia.bounding_box_response;
import avro.java.gaia.clear_response;
import avro.java.gaia.cluster_response;
import avro.java.gaia.convex_hull_response;
import avro.java.gaia.copy_set_response;
import avro.java.gaia.filter_by_bounds_response;
import avro.java.gaia.filter_by_list_response;
import avro.java.gaia.filter_by_nai_response;
import avro.java.gaia.filter_by_radius_response;
import avro.java.gaia.filter_by_string_request;
import avro.java.gaia.filter_by_string_response;
import avro.java.gaia.filter_by_value_response;
import avro.java.gaia.filter_then_histogram_response;
import avro.java.gaia.get_orphans_response;
import avro.java.gaia.get_sets_by_type_info_response;
import avro.java.gaia.get_type_info_response;
import avro.java.gaia.group_by_map_page_response;
import avro.java.gaia.group_by_response;
import avro.java.gaia.histogram_response;
import avro.java.gaia.initialize_group_by_map_response;
import avro.java.gaia.join_incremental_response;
import avro.java.gaia.join_response;
import avro.java.gaia.join_setup_response;
import avro.java.gaia.make_bloom_response;
import avro.java.gaia.max_min_response;
import avro.java.gaia.merge_sets_response;
import avro.java.gaia.plot2d_multiple_response;
import avro.java.gaia.predicate_join_response;
import avro.java.gaia.register_trigger_range_response;
import avro.java.gaia.register_type_transform_response;
import avro.java.gaia.road_intersection_response;
import avro.java.gaia.select_response;
import avro.java.gaia.server_status_response;
import avro.java.gaia.set_info_response;
import avro.java.gaia.sort_response;
import avro.java.gaia.stats_response;
import avro.java.gaia.status_response;
import avro.java.gaia.store_group_by_response;
import avro.java.gaia.turn_off_response;
import avro.java.gaia.unique_response;
import avro.java.gaia.update_object_response;

import com.gisfederal.Gaia;
import com.gisfederal.GaiaException;
import com.gisfederal.GenericObject;
import com.gisfederal.NamedSet;
import com.gisfederal.PointPair;
import com.gisfederal.SetId;
import com.gisfederal.Type;
import com.gisfederal.semantic.types.Polygon;
import com.gisfederal.semantic.types.SemanticTypeEnum;
import com.gisfederal.semantic.types.Track;
import com.gisfederal.utils.SpatialOperationEnum;

public class TestGaia {
	private static Gaia gaia;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
		System.out.println("Build gaia...");
		//String gaiaURL = System.getProperty("GAIA_URL", "http://172.30.20.101:9191");

		String gaiaURL = System.getProperty("GAIA_URL", "http://192.168.202.129:9191");

		
		String disableTrigger = System.getProperty("GAIA_DISABLE_TRIGGER", "FALSE");

		if( Boolean.parseBoolean(disableTrigger) ) {
			gaia = new Gaia(gaiaURL);
		} else {
			URL myURL = new URL(gaiaURL);
			String host = myURL.getHost();
			int port = myURL.getPort();
			System.out.println(" Trigger host is " + host);
			gaia = Gaia.newGaiaTrigger(host, port, "tcp://"+ host +":9001", "");
		}
		gaia.setCollectForReplay(true);
		System.out.println("Built gaia");
	}
		
	@Test
	public void testTypeCreation() {
		
		//System.setProperty("javax.net.ssl.trustStore","C:/software_downloads/JKSTRUST/systemCA.jks");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		Type type = gaia.create_type(BigPoint.class);
		System.out.println("Type id:"+type.getID());		
	}
	
	@Test
	public void testBadGaiaIP() {
		try {
			Gaia bad_gaia = new Gaia("GARBAGE", 9191);
			Type type = gaia.create_type(BigPoint.class);
		} catch (GaiaException e) {			
			assertTrue(e.toString().contains("net connection"));
		}
	}

	@Test
	public void testBadTypeCreation() {
		try {
			Type type = gaia.create_type(BadObject.class);
			System.out.println("Type id:"+type.getID());
		} catch(GaiaException e) {
			assertTrue(e.toString().equals("com.gisfederal.GaiaException: Unsuported java data type:long"));
		}
	}

	@Test
	public void testTypeByDefinitionCreation() {
		// test of using the string definition; create a type; then a new set; add objects 
		Type type = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		System.out.println("Type id:"+type.getID());			
	}
	
	@Test
	public void testGetTypeInfo() {
		// register two types 
		String label1 = "type_label1";
		SemanticTypeEnum semanticType1 = SemanticTypeEnum.GENERICOBJECT;
		Type type1 = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", 
					"", label1, semanticType1);
		
		String label2 = "type_label2";
		SemanticTypeEnum semanticType2 = SemanticTypeEnum.GENERICOBJECT;
		Type type2 = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", 
				label2, semanticType2);
		
		// now get out the type info
		get_type_info_response response = gaia.do_get_type_info("", label1, "");
		List<CharSequence> semanticTypes1 = response.getSemanticTypes();
		assertTrue(semanticTypes1.size() == 1);
		assertTrue(semanticTypes1.get(0).toString().equals(semanticType1.name()));
	}
	
	/// NOTE: this isn't really a normal junit test; if the TEST hasn't been created then it should throw but if it has been
	// (for instance through python) then it should return the proper set
	@Test
	public void testGetNamedSet() {
		// test getting a named set	
		try {			
			gaia.getNamedSet(new SetId("TEST"));
			assertTrue(false);
		} catch(GaiaException e) {
			System.out.println("Error:"+e.toString());
			assertTrue(true);
		}
	}
	
	// NOTE: you need two servers up for this! fails otherwise
	@Test
	public void testTwoGaias() {
		gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		SetId si = gaia.new_setid();
		gaia.newSingleNamedSet(si, type);
		stats_response response = gaia.do_stats();
		System.out.println(response.toString());
		
		// do the actual 2 server test
		if(false) {
			// build a new connection to separate gaia instances
			Gaia gaia2 = new Gaia("127.0.0.1", 9192);
			gaia2.do_clear();
			response = gaia2.do_stats();
			System.out.println(response.toString());
			
			boolean check = false;
			try {
				NamedSet ns = gaia2.getNamedSet(si);			
			} catch (GaiaException e) {
				// set doesn't exist error
				System.out.println(e.toString());
				check = true;
			}
			assertTrue(check);		
			
				
			type = gaia2.create_type(BigPoint.class);
			NamedSet ns = gaia2.newSingleNamedSet(si, type);
			ns.do_filter_by_bounds("x", 0, 10);
		}
	}
	
	@Test
	public void testCreationMethods() {
		Type type = gaia.create_type(BigPoint.class);
		SetId si = gaia.new_setid();
		gaia.newSingleNamedSet(si, type);
		
		assertTrue(gaia.setExists(si));
		
		assertFalse(gaia.setExists(gaia.new_setid()));
		
		boolean check = false;
		try { 
			// can't recreate the same set
			gaia.newSingleNamedSet(si, type);			
		} catch(GaiaException e) {
			check = true;
			e.printStackTrace();
		}
		assertTrue(check);
		
		NamedSet parent = gaia.newParentNamedSet();
		check = false;
		try { 
			// can't recreate the same set
			gaia.newParentNamedSet(parent.get_setid());			
		} catch(GaiaException e) {
			check = true;			
		}
		assertTrue(check);
		
		// add a child
		
		gaia.newChildNamedSet(parent, type);
		check = false;
		try { 
			// can't recreate a child with the same type
			gaia.newChildNamedSet(parent, type);			
		} catch(GaiaException e) {
			check = true;			
		}
		assertTrue(check);
			
	}
	
	
	@Test
	public void testGetOrphans() {
		gaia.do_clear();
		
		Type bgType = gaia.create_type(BigPoint.class);
		Type kvType = gaia.create_type(KeyValueType.class);
		SetId parent = gaia.new_setid();
		System.out.println("parent:"+parent.get_id());
		
		NamedSet kvChild = gaia.newNamedSet(parent, kvType);
		System.out.println("built first child");
		NamedSet bgChild = gaia.newNamedSet(parent, bgType);
		System.out.println("built second child");
		
		// orphan 
		NamedSet orphan_nonparent = gaia.newNamedSet(bgType);
		get_orphans_response response = gaia.do_get_orphans();
		System.out.println(response.toString());
		List<CharSequence> set_ids = response.getSetIds();
		List<Boolean> parents = response.getIsParent();
		assertTrue(set_ids.size() == 2);
		assertTrue(parents.size() == 2);
		for(int i=0; i<set_ids.size(); i++) {
			if(set_ids.get(i).toString().equals(parent.get_id())){
				assertTrue(parents.get(i) == true);
			} else if(set_ids.get(i).toString().equals(orphan_nonparent.get_setid().get_id())) {
				assertTrue(parents.get(i) == false);
			} else {
				assertTrue(false);
			}
		}		
	}
	
	@Test
	public void testGetServerStatus() {
		server_status_response ssr = gaia.do_server_status();
		System.out.println(ssr.getStatusMap());

	}
	
	@Test
	public void testFetchShapes() {
		testCreateShapes();
		NamedSet containerSet = gaia.getNamedSet(new SetId("CONTAINER"), Type.buildParentType());
		
		List<Polygon> polygons = containerSet.getPolygons(0, 100);
		for( Polygon poly : polygons ) {
			System.out.println(poly.wkt + " $ " + poly.features);
		}
		
		List<Track> tracks = containerSet.getTracks(containerSet, 0, 100, -180, -90, 180, 90);
		for( Track track : tracks ) {
			List<Track.TrackPoint> points = track.getTrackPoints();
			for( Track.TrackPoint tp : points ) {
				System.out.println(tp.getX() + " $ " + tp.getY() + " $ " + tp.getFeatures());
			}
		}

	
	}
	
	@Test
	public void testCreateShapes() {
		gaia.do_clear();
		NamedSet parent = gaia.newParentNamedSet(new SetId("CONTAINER"));

		// Create some Polygons;
		String label = "type_label_Polygon";
		SemanticTypeEnum semanticType = SemanticTypeEnum.POLYGON2D;
		Type polyType = gaia.create_type("{\"type\":\"record\",\"name\":\"Poly2D\",\"fields\":" +
				"[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"WKT\",\"type\":\"string\"}," +
				"{\"name\":\"Attribute1\",\"type\":\"string\"},{\"name\":\"Attribute2\",\"type\":\"int\"}]}", "", label, semanticType);
		
		NamedSet child = gaia.newChildNamedSet(parent, polyType);
		
		GenericObject go = new GenericObject();
		go.addField("Attribute1", "Alice");
		go.addField("Attribute2", "21");
		go.addField("OBJECT_ID", "A21");
		go.addField("WKT", "POLYGON ((30 10, 10 20, 20 40, 40 40, 30 10))");
		child.add(go);
		
		GenericObject go2 = new GenericObject();
		go2.addField("Attribute1", "Bob");
		go2.addField("Attribute2", "20");
		go2.addField("OBJECT_ID", "B20");
		go2.addField("WKT", "POLYGON ((30 10, 10 12, 12 42, 42 50, 30 10))");
		child.add(go2);

		// Create some Tracks;
		String label2 = "type_label_Track";
		SemanticTypeEnum semanticType2 = SemanticTypeEnum.TRACK;
		Type trackType = gaia.create_type("{\"type\":\"record\",\"name\":\"Track\",\"fields\":" +
				"[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"x\",\"type\":\"double\"},{\"name\":\"y\",\"type\":\"double\"}," +
				"{\"name\":\"TIMESTAMP\",\"type\":\"double\"}," +
				"{\"name\":\"TRACKID\",\"type\":\"string\"},{\"name\":\"Attribute2\",\"type\":\"int\"}]}", "", label2, semanticType2);
		
		NamedSet child2 = gaia.newChildNamedSet(parent, trackType);
		
		// Tracks consists of points (on the track). All points on a track has the same trackid
		for( int ii = 0; ii < 3; ii++ ) {
			GenericObject go3 = new GenericObject();
			go3.addField("TRACKID", "track1");
			go3.addField("Attribute2", ""+ii);
			go3.addField("OBJECT_ID", "T1_"+ii);
			go3.addField("TIMESTAMP", "12345");
			go3.addField("x", "20" + ii );
			go3.addField("y", "30" + ii );
			child2.add(go3);
		}
		
		// Tracks consists of points (on the track). All points on a track has the same trackid
		for( int ii = 10; ii < 13; ii++ ) {
			GenericObject go3 = new GenericObject();
			go3.addField("TRACKID", "track2");
			go3.addField("Attribute2", ""+ii);
			go3.addField("OBJECT_ID", "T2_"+ii);
			go3.addField("TIMESTAMP", "12345");
			go3.addField("x", "20" + ii );
			go3.addField("y", "30" + ii );
			child2.add(go3);
		}
		
	}
	
	@Test
	public void testGetSetsByTypeInfo() {
		gaia.do_clear();
		
		// register two types 
		String label1 = "type_label1"+UUID.randomUUID().toString();;
		SemanticTypeEnum semanticType1 = SemanticTypeEnum.GENERICOBJECT;
		Type type1 = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label1, semanticType1);
		
		String label2 = "type_label2"+UUID.randomUUID().toString();;
		SemanticTypeEnum semanticType2 = SemanticTypeEnum.GENERICOBJECT;
		Type type2 = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label2, semanticType2);
				
		// different semantic type but same label as type2
		SemanticTypeEnum semanticType3 = SemanticTypeEnum.POLYGON2D;
		Type type3 = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"WKT\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label2, semanticType3);
		
		
		NamedSet ns1 = gaia.newSingleNamedSet(type1);
		NamedSet ns2 = gaia.newSingleNamedSet(type1);
		NamedSet ns3 = gaia.newSingleNamedSet(type2);
		
		// now get out the type info
		get_sets_by_type_info_response response = gaia.do_get_sets_by_type_info(label1, "");
		List<CharSequence> set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 2);
		
		response = gaia.do_get_sets_by_type_info(label2, "");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 1);
		
		response = gaia.do_get_sets_by_type_info("", "GENERICOBJECT");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 3);
		
		response = gaia.do_get_sets_by_type_info("", "");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 3);
		
		response = gaia.do_get_sets_by_type_info("", "BLAH");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 0);
		
		response = gaia.do_get_sets_by_type_id(type2.getID());
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 1);
		System.out.println(ns3.get_setid().get_id());
		assertTrue(set_ids.get(0).toString().equals(ns3.get_setid().get_id()));
		
		// try the named set functions; especially on parents
		NamedSet parent = gaia.newParentNamedSet();
		NamedSet child1 = gaia.newChildNamedSet(parent, type1);
		NamedSet child2 = gaia.newChildNamedSet(parent, type2);
		NamedSet child3 = gaia.newChildNamedSet(parent, type3);
		
		// get by type id
		assertTrue(parent.do_get_sets_by_type_id(type1.getID()).get_setid().equals(child1.get_setid()));
		
		// get by semantic type 
		List<NamedSet> sets = parent.do_get_sets_by_semantic_type(semanticType3.name());
		assertTrue(sets.size() == 1);
		assertTrue(sets.get(0).get_setid().equals(child3.get_setid()));
		
		// by label
		sets = parent.do_get_sets_by_type_label(label2); // this label is shared by type2 and type3
		assertTrue(sets.size() == 2);
		sets.remove(child2);
		sets.remove(child3);
		assertTrue(sets.size() == 0);		
		
		// get by semantic type and label
		sets = parent.do_get_sets_by_type_info(label2, semanticType2.name());
		assertTrue(sets.size() == 1);
		assertTrue(sets.get(0).get_setid().equals(child2.get_setid()));
		
		// use empty string
		sets = parent.do_get_sets_by_type_info(label2, "");
		assertTrue(sets.size() == 2);
		sets.remove(child2);
		sets.remove(child3);
		assertTrue(sets.size() == 0);				
	}

	@Test
	public void tetsListAllTypes() {
		gaia.do_clear();
		
		// register types 
		String label1 = "type_label1"+UUID.randomUUID().toString();;
		SemanticTypeEnum semanticType = SemanticTypeEnum.GENERICOBJECT;
		Type type1 = gaia.create_type(BigPoint.class, "", label1, semanticType);
		
		// different label
		String label2 = "type_label2"+UUID.randomUUID().toString();;
		Type type2 = gaia.create_type(BigPoint.class, "", label2, semanticType);
		
		// same label but different class (schema)
		Type type3 = gaia.create_type(SimplePoint.class, "", label2, semanticType);				
		
		// try the named set functions; especially on parents
		NamedSet parent = gaia.newParentNamedSet();
		NamedSet child1 = gaia.newChildNamedSet(parent, type1);
		NamedSet child2 = gaia.newChildNamedSet(parent, type2);
		NamedSet child3 = gaia.newChildNamedSet(parent, type3);
		
		// list all types; should say "no" types because no objects added
		try {
			List<Type> types = parent.listAllTypes();
			assertTrue(types.size() == 0);
			
			// now add something; one type
			child3.add(new SimplePoint(UUID.randomUUID().toString(), 0,1));
			types = parent.listAllTypes();
			assertTrue(types.size() == 1);
			types.get(0).equals(type3);
						
			child3.add(new SimplePoint(UUID.randomUUID().toString(), 0,1));
			types = parent.listAllTypes();
			assertTrue(types.size() == 1);
			types.get(0).equals(type3);
			
			types = child3.listAllTypes();
			assertTrue(types.size() == 1);
			types.get(0).equals(type3);
			
			// add more
			child2.add(new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2"));
			types = parent.listAllTypes();
			System.out.println("types.size():"+types.size());
			assertTrue(types.size() == 2);
			types.remove(type2);
			types.remove(type3);			
			assertTrue(types.size() == 0);
			
		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	@Test
	public void testBytesTypeCreation() {
		Type type = gaia.create_type(BytesPoint.class);
		System.out.println("Type id:"+type.getID());		
	}

	@Test
	public void testBytesAddObject() {
		Type type = gaia.create_type(BytesPoint.class);
		System.out.println("Type id:"+type.getID());
		NamedSet ns = gaia.newNamedSet(type);
		BytesPoint point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",1.01, 2.01,1,"SRC1", "GROUP1", ByteBuffer.wrap("Binary Test Data1".getBytes()));
		ns.add(point);
		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",1.01, 3.01,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary Test Data2".getBytes()));
		ns.add(point);
	}

	@Test
	public void testBytesListObject() {
		Type type = gaia.create_type(BytesPoint.class);
		System.out.println("Type id:"+type.getID());
		NamedSet ns = gaia.newNamedSet(type);

		ArrayList<BytesPoint> local_points = new ArrayList<BytesPoint>();				
		BytesPoint point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",1.01, 2.01,1,"SRC1", "GROUP1",ByteBuffer.wrap("b1".getBytes()));
		ns.add(point);
		local_points.add(point);
		//point = new BytesPoint("MSGID2",1.01, 3.01,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary Test Data2".getBytes()));
		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",1.01, 3.01,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary".getBytes()));
		ns.add(point);
		local_points.add(point);
		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID3",1.01, 4.01,1,"SRC3", "GROUP3", ByteBuffer.wrap("Binary Test Data3".getBytes()));
		ns.add(point);
		local_points.add(point);

		// list
		ArrayList<BytesPoint> points = (ArrayList<BytesPoint>)ns.list(0);
		assertTrue(points.size() == 3);		

		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));
		}


		// true test; make sure the points return equal those in the local list
		for(BytesPoint p : local_points) {
			points.remove(p); 
		}
		assertTrue(points.size() == 0); 

	}

	@Test
	public void testNamedSetCreation() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);
	}	
	
	@Test
	public void testParentNamedSetCreation() {
		Type bgType = gaia.create_type(BigPoint.class);
		Type kvType = gaia.create_type(KeyValueType.class);
		SetId parent = gaia.new_setid();
		System.out.println("parent:"+parent.get_id());
		
		NamedSet kvChild = gaia.newNamedSet(parent, kvType);
		System.out.println("built first child");
		NamedSet bgChild = gaia.newNamedSet(parent, bgType);
		System.out.println("built second child");
	}

	@Test
	public void testAddObject() {		
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		add_object_response response = A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		response = A.add(p);
		
		assertTrue(response.getOBJECTID().length() > 0);
		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		response = A.add(p);

		System.out.println(" ObjectId " + response.getOBJECTID().toString());
		
		assertTrue(response.getOBJECTID().length() > 0);
	}
	
	@Test
	public void testDeleteObject() {		
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		add_object_response response = A.add(p);
		System.out.println(" Object id is " + response.getOBJECTID());
		assertTrue(response.getOBJECTID().length() > 0);
		
		assertTrue(A.size() == 1);
		
		gaia.do_delete_object(A, response.getOBJECTID().toString());
		
		assertTrue(A.size() == 0);
		
	}
	
	@Test
	public void testUpdateObject() {	
		
		gaia.do_clear();
		
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		add_object_response response = A.add(p);
		System.out.println(" Object id is " + response.getOBJECTID());
		assertTrue(response.getOBJECTID().length() > 0);
		
		System.out.println(" I am going to sleep for 3 secs now !!");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p = new BigPoint(response.getOBJECTID().toString(),  "MSGID2", 11.01, 12.01, 0, "SRC2", "GROUP2");
		update_object_response ur = A.update(p, response.getOBJECTID().toString());
		
		// Test GaiaUCD will do more attribute level checking of updates.
		assertTrue(ur.getStatus().toString().equals("OK"));
		
	}


	@Test
	public void testBadAddObject() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some incorrect points and add them
		try {
			A.add(new KeyValueType(UUID.randomUUID().toString(), "KEY", "VALUE"));
			System.out.println("added key value");
		} catch(GaiaException e) {
			assertTrue(e.toString().contains("com.gisfederal.GaiaException"));
		}		
	}

	@Test
	public void testClear() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		A.add(p);

		// clear [NOTE: how else to test?]
		clear_response response = gaia.do_clear();
		System.out.println(response.toString());
	}

	@Test
	public void testFilterThenHistogram() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newSingleNamedSet(type);
		
		// add the points; we are going to do binning on "TIMESTAMP"; 
	    // GROUP 1 
		BigPoint p = null;
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 2, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 3, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 4, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 5, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 6, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 7, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 8, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 9, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 2.01, 3.01, 10, "SRC1", "GROUP1");
		ns.add(p);
		
	    // GROUP 2 
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 2, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 4, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 5, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 5, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 5, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 6, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 6, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 6, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 7, "SRC1", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 9, "SRC1", "GROUP2");
		ns.add(p);
		
	    // GROUP 3 
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 1, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 1, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 1, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 1, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 1, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 1, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 2, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 2, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 3, "SRC1", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 3.01, 4, "SRC1", "GROUP3");
		ns.add(p);
		
		// Nore attribute TIMESTAMP, group_id are part of BigPoint class variables...

		max_min_response response = gaia.do_max_min(ns, "timestamp");	
		Double minVal = response.getMin();
		Double maxVal = response.getMax();
		
		assertTrue(minVal == 1);
		assertTrue(maxVal == 10);
		
		System.out.println("Min val is " + minVal);
		System.out.println("Min val is " + maxVal);
		
		List<CharSequence> filter = new ArrayList<CharSequence>();
		filter.add("GROUP1");
		filter.add("GROUP2");
		filter.add("GROUP3");
		filter_then_histogram_response fthr =  gaia.do_filter_then_histogram(ns,"group_id",filter,"timestamp", 1,minVal,maxVal);

		List<List<Integer>> counts = fthr.getCounts();
		for( List<Integer> ci : counts ) {
			assertTrue(ci.size() == 9);
		}
		System.out.println("XXX " + fthr.getCounts());
	}
	
	@Test
	public void testBoundingBoxForSet() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newSingleNamedSet(type);

		// Create some points and add them
		ArrayList<BigPoint> local_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1.1", 2.01, 3.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		local_points.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 3.01, 4.01, 1, "SRC2", "GROUP2");
		ns.add(p);
		local_points.add(p);
		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 4.01, 5.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 5.01, 6.01, 2, "SRC3", "GROUP4");
		ns.add(p);		

		//
		Rectangle2D myRect = ns.getBoundingBox();
		
		System.out.println(" Rectangle is " + myRect.getMaxY() + " " + myRect.getMinY());
		
		assertTrue(myRect.getMinX() == 1.01); 
		assertTrue(myRect.getMaxX() == 5.01); 
		assertTrue(myRect.getMinY() == 2.01); 
		assertTrue(myRect.getMaxY() == 6.01); 
	}

	@Test
	public void testGetObjects() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newSingleNamedSet(type);

		// Create some points and add them
		ArrayList<BigPoint> local_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1.1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		local_points.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);
		local_points.add(p);
		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 3.01, 3.01, 2, "SRC3", "GROUP4");
		ns.add(p);		


		// get out the objects of group1 and group2
		List<CharSequence> values = new ArrayList<CharSequence>();
		values.addAll(Arrays.asList("GROUP1", "GROUP2"));
		List<BigPoint> points  = ns.get_objects("group_id", values);
		System.out.println("ns.getType().getTypeClass().getName():"+ns.getType().getTypeClass().getName());
		assertTrue(ns.getType().getTypeClass().getName() == "com.gisfederal.test.BigPoint");
		
		// list
		//assertTrue(points.size() == 3); // NOTE: it is only returning one object per filter value
		
		// debugging
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));			
		}		

		// true test; make sure the points return equal those in the local list
		for(BigPoint point : local_points) {
			points.remove(point); 
		}
		assertTrue(points.size() == 0); 
	}

	@Test
	public void testListObject() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		ArrayList<BigPoint> local_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		local_points.add(p);

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0,2);
		assertTrue(points.size() == 3);
		// debugging
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));			
		}

		// list
		points = (ArrayList<BigPoint>)ns.list(0);
		assertTrue(points.size() == 3);

		// true test; make sure the points return equal those in the local list
		for(BigPoint point : local_points) {
			points.remove(point); 
		}
		assertTrue(points.size() == 0); 
	}
	
	@Test
	public void testIterator() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		ArrayList<BigPoint> local_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		local_points.add(p);

		// to force it to switch pages; i.e. do multiple request
		ns.setPageSize(2);

		// iterate through the set 
		Iterator<BigPoint> iter = ns.iterator();		
		while(iter.hasNext()){
			p = iter.next();
			System.out.println("ITER:"+p);
			local_points.remove(p);
			iter.remove();
		}

		assertTrue(local_points.size() == 0);
	}

	@Test
	public void testTypeByDefinitionCreationAndList() {
		// test of using the string definition; create a type; then a new set; add objects 
		Type type = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		NamedSet ns = gaia.newNamedSet(type);
		System.out.println(type.getTypeClass());
		System.out.println(ns.getType().getTypeClass());
		System.out.println(ns.getClass());

		// Create some objects;
		GenericObject go = new GenericObject();
		go.addField("person", "Alice");
		go.addField("age", "21");
		go.addField("OBJECT_ID", "0.0");
		ns.add(go);
		go = new GenericObject();
		go.addField("person", "Bob");
		go.addField("age", "25");
		go.addField("OBJECT_ID", "0.0");
		ns.add(go); 
		
		// check the size
		System.out.println(ns.size());
		assertTrue(ns.size() == 2);

		// also test adding a list of generic objects
		List<Object> gos = new ArrayList<Object>();
		go = new GenericObject();
		go.addField("person", "Carl");
		go.addField("age", "35");
		go.addField("OBJECT_ID", "0.0");
		gos.add(go);
		go = new GenericObject();
		go.addField("person", "Denise");
		go.addField("age", "31");
		go.addField("OBJECT_ID", "0.0");
		gos.add(go);
		
		// bulk add
		ns.add_list(gos); 
		
		// check the size
		System.out.println(ns.size());
		assertTrue(ns.size() == 4);		
	}
		
	@Test
	public void testNamedSetSize() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		int numPoints = 11;		
		for(int i=0; i<numPoints; i++) {
			ns.add(new BigPoint(UUID.randomUUID().toString(),  "MSGID"+i, i, 2.01, 0, "SRC1", "GROUP1"));
		}

		// check the size
		System.out.println(ns.size());
		assertTrue(ns.size() == numPoints);
	}

	@Test
	public void testSemanticType() {
		// type registering 
		String label1 = "type_label_st";
		SemanticTypeEnum semanticType1 = SemanticTypeEnum.EMPTY;
		Type type = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label1, semanticType1);
		
		// test some semantic type stuff
		NamedSet ns = gaia.newSingleNamedSet(gaia.new_setid(), type);
		//NamedSet ns = gaia.newParentNamedSet(gaia.new_setid());
				
		// Create some objects;
		GenericObject go = new GenericObject();
		go.addField("person", "Alice");
		go.addField("age", "21");
		go.addField(Type.PROP_OBJECT_ID, UUID.randomUUID().toString());
		ns.add(go);
		go = new GenericObject();
		go.addField("person", "Bob");
		go.addField("age", "25");
		go.addField(Type.PROP_OBJECT_ID, UUID.randomUUID().toString());
		ns.add(go);
		
		
		try {
			ns.getPoints(0, NamedSet.END_OF_SET);
			// should throw
		} catch(GaiaException e) {
			assertTrue(true);
			System.out.println("GE:"+e.toString());
		} catch(Exception e) {
			System.out.println("E:"+e.toString());
		}
	}
	
	@Test
	public void testBulkAdd() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		List<Object> list = new ArrayList<Object>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		list.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		list.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		list.add(p);

		A.add_list(list);		

		// list		
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)A.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}

	@Test
	public void testBulkAddWithLimit() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);
		ns.setBulkAddLimit(2);

		// Create some points and add them
		List local_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");		
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 2.01, 2.01, 1, "SRC2", "GROUP2");
		local_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 3.01, 3.01, 2, "SRC3", "GROUP3");
		local_points.add(p);

		ns.add_list(local_points);

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0);
		assertTrue(points.size() == local_points.size());

		// debugging
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));			
		}

		// true test; make sure the points return equal those in the local list
		for(BigPoint point : (ArrayList<BigPoint>)local_points) {
			points.remove(point); 
		}
		assertTrue(points.size() == 0);	
	}	
	
	@Ignore
	public void testMakeBloom() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points		
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);				

		// run make bloom
		make_bloom_response response = gaia.do_make_bloom(ns, "x");
		System.out.println("make bloom status:"+response.getStatus());
		assertTrue(response.getStatus().toString().equals("CREATED"));
	}
		
	@Ignore
	public void testTurnOff() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newSingleNamedSet(type);
		
		List<Object> points = new ArrayList<Object>();
		// do_add_big_point(self, set_id, msg_id, x, y, timestamp, source, group_id)
	    // Track 1, no points over the threshold
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_001", 2.0, 9.0, 1, "SRC_ID_001", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_002", 2.0, 8.0, 2, "SRC_ID_002", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_003", 1.0, 6.0, 3, "SRC_ID_003", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_004", 1.0, 4.0, 4, "SRC_ID_004", "GROUP_001"));		
		
		// Track 2, one pair of points that go over the threshold
		// we expect for this track (x,y,t) = ((8,6,3),(7,7,8))
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_005",10.0, 8.0, 1, "SRC_ID_001", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_006", 9.0, 7.0, 2, "SRC_ID_002", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_007", 8.0, 6.0, 3, "SRC_ID_003", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_008", 7.0, 7.0, 8, "SRC_ID_004", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_009", 6.0, 7.0, 10, "SRC_ID_004", "GROUP_002"));		
		
		// Track 3 two pairs and mixed up order
		// we expect for this track (x,y,t) = ((2,5,1),(4,4,8)),((6,4,16),(7,3,23))
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_010", 2.0, 5.0, 1, "SRC_ID_001", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_011", 3.0, 4.0, 12, "SRC_ID_002", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_012", 4.0, 4.0, 8, "SRC_ID_003", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_013", 5.0, 4.0, 14, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_014", 6.0, 4.0, 16, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_015", 7.0, 3.0, 23, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_016", 8.0, 3.0, 0, "SRC_ID_004", "GROUP_003"));
		        
	    //Track 4: Not in bounding box
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_017", -1, 8.0, 1, "SRC_ID_001", "GROUP_004"));
		points.add(new BigPoint(UUID.randomUUID().toString(),  "MSG_ID_018", 1, 20, 2, "SRC_ID_002", "GROUP_004"));
			
		// add the pints
		ns.add_list(points);
		
		SetId result_set_id = gaia.new_setid();
		bounding_box_response response = gaia.do_bounding_box(ns, result_set_id, "x", "y", 0, 11, 0, 10);
		assertTrue(response.getCount() == 16);
		
		turn_off_response toresp = gaia.do_turn_off(result_set_id, "group_id", "timestamp", "x", "y", 5);
		System.out.println(toresp.getMap());	
		
		try {
			Map<String,List<PointPair>> track_map = ns.do_turn_off(5);
			System.out.println(track_map);
			assertTrue(track_map.get("GROUP_001").size() == 0); // NOTE: might just not add this key						
			assertTrue(track_map.get("GROUP_002").size() == 1);
			
			PointPair pp = track_map.get("GROUP_002").get(0);
			assertTrue(pp.firstPoint.x == 8 && pp.firstPoint.y == 6 && pp.firstPoint.timestamp == 3);
			assertTrue(pp.secondPoint.x == 7 && pp.secondPoint.y == 7 && pp.secondPoint.timestamp == 8);
			
			assertTrue(track_map.get("GROUP_003").size() == 2);
		} catch(GaiaException e) {
			System.err.println("error:"+e.toString());
		}
	}
	
	@Test
	public void testBoundingBox() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points into the box area
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);
		in_points.add(p);

		// outside the box
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// run bounding box
		SetId result_set_id = gaia.new_setid();
		bounding_box_response response = gaia.do_bounding_box(ns, result_set_id, "x", "y", 1, 10, 1, 10);
		assertTrue(response.getCount() == 3);

		// grab the RS; loop through the points
		NamedSet rs = gaia.getNamedSet(result_set_id, type);
		// list 
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}
	
	@Test
	public void testBoundingBoxWithParent() {
		// make types
		Type bgType = gaia.create_type(BigPoint.class);
		Type spType = gaia.create_type(SimplePoint.class);
		
		// parent set id
		SetId parent = gaia.new_setid();				
		NamedSet bgChild = gaia.newNamedSet(parent, bgType);
		NamedSet spChild = gaia.newNamedSet(parent, spType);
		NamedSet parentNS = gaia.getNamedSet(parent);
		assertTrue(parentNS != null);
		
		// add points into the box area
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		bgChild.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		bgChild.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		bgChild.add(p);
		in_points.add(p);
		SimplePoint sp = new SimplePoint(UUID.randomUUID().toString(), 2,2);
		spChild.add(sp);

		// outside the box
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		bgChild.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		bgChild.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		bgChild.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		bgChild.add(p);
		sp = new SimplePoint(UUID.randomUUID().toString(), 15,2);
		spChild.add(sp);

		// run bounding box
		SetId result_set_id = gaia.new_setid();
		bounding_box_response response = gaia.do_bounding_box(parentNS, result_set_id, "x", "y", 1, 10, 1, 10);
		System.out.println(response.getCount());
		assertTrue(response.getCount() == 4);

		// grab the RS; loop through the points
		NamedSet parentRS = gaia.getNamedSet(result_set_id);
				
		// get children from the server
		List<NamedSet> children = new ArrayList<NamedSet>();
		children.addAll(parentRS.getChildren());
		
		// find the child that matches this type
		NamedSet child = null;
		System.out.println("bgType.get_id():"+bgType.getID());
		for(int i=0; i<children.size(); i++) {
			child = children.get(i);
			if(child.getType().getID().equals(bgType.getID())) {
				child.setType(bgType);
				break;
			}
		}
		assertTrue(child != null);
		
		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)child.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}

	@Test
	public void testMaxMin() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points		
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);				
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// run max min
		max_min_response response = gaia.do_max_min(ns, "x");
		assertTrue(response.getMax() == 35.01);
		assertTrue(response.getMin() == 1.01);
	}

	@Test
	public void testBulkAddThenCalc() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		List<Object> list = new ArrayList<Object>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1, 2.01, 0, "SRC1", "GROUP1");
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2, 2.01, 1, "SRC2", "GROUP2");
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3, 3.01, 2, "SRC3", "GROUP3");
		list.add(p);


		ns.add_list(list);		

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}

		max_min_response response = gaia.do_max_min(ns, "x");
		assertTrue(response.getMax() == 3);
		assertTrue(response.getMin() == 1);

	}

	@Test
	public void testHistogram() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points		
		// RECALL: histogram will return the first bin as the min, and the next bin is [min+1,(min+1)+interval]
		// interval/min
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 1, "SRC1", "IN_BOX");
		ns.add(p);		

		// interval
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 5, "SRC2", "IN_BOX");
		ns.add(p);				
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 6, "SRC3", "IN_BOX");
		ns.add(p);				

		// interval
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 9, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 10, "SRC2", "OUT_BOX");
		ns.add(p);		

		// interval
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 20.01, 20.01, 13, "SRC2", "OUT_BOX");
		ns.add(p);		


		// run histogram (set id, attr, interval)
		histogram_response response = gaia.do_histogram(ns, "timestamp", 5);		
		assertTrue(response.getStart() == 1);
		assertTrue(response.getEnd() == 13);

		// check counts
		List<Integer> list = response.getCounts();
		System.out.println("list:"+list.toString());
		assertTrue(list.size() == 3);
		
		assertTrue(list.get(0).intValue() == 2 && list.get(1).intValue() == 3 && list.get(2).intValue() == 1); 
	}	

	@Test
	public void testFilterByBounds() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points in the bounds
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);
		in_points.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// run bounding box
		SetId result_set_id = gaia.new_setid();
		filter_by_bounds_response response = gaia.do_filter_by_bounds(ns, result_set_id, "x", 1, 5);
		assertTrue(response.getCount() == 3);

		// grab the RS; loop through the points
		NamedSet rs = gaia.getNamedSet(result_set_id, type);
		// list 
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}
	
	@Test
	public void testConvexHull() {
		gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);
		
		Random r = new Random();
		int Low = 5;
		int High = 10;
		
		// Create some points and add them. These are all inside and wont be part of the hull....
		for( int ii = 0; ii< 100; ii++ ) {
			int x = r.nextInt(High-Low) + Low;
			int y = r.nextInt(High-Low) + Low;
			
			BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", x, y, 0, "SRC1", "GROUP1");
			ns.add(p);
		}

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 0, 0, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 0, 20, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 20, 20, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 20, 0, 1, "SRC2", "GROUP2");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 10, 30, 2, "SRC3", "GROUP3");
		ns.add(p);
		
		
		convex_hull_response chr = gaia.do_convex_hull(ns, "x", "y");
		assertTrue(chr.getCount() == 6);
		
	}

	@Test
	public void testGroupBy() {
		Type type = gaia.create_type(BigPoint.class);
		gaia.do_clear();
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		// group 1
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);

		// group 2
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);		

		// group 3
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 4.01, 2, "SRC4", "GROUP3");
		ns.add(p);

		// run group by on "group_id"
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("group_id");
		attributes.add("source");

		group_by_response response = gaia.do_group_by(ns, attributes);
		Map<CharSequence, List<CharSequence>> count_map = response.getCountMap();

		// NOTE: so the CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, List<String>> str_count_map = new HashMap<String, List<String>>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();
				String str_key = key.toString();
				List utf8_list = count_map.get(key);
				List<String> str_list = new ArrayList<String>();
				for(int i=0; i<utf8_list.size(); i++) {
					org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)utf8_list.get(i);
					str_list.add(value.toString());
				}
				str_count_map.put(str_key, str_list);
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}		
		System.out.println("G1S1:"+str_count_map.get("GROUP1,SRC1").get(0)+" G3S4:"+str_count_map.get("GROUP3,SRC4").get(0)+ 
				" G3S3:"+str_count_map.get("GROUP3,SRC3").get(0) + " G2S2:"+str_count_map.get("GROUP2,SRC2").get(0));
		assertTrue(str_count_map.get("GROUP1,SRC1").get(0).equals("3") && 
				str_count_map.get("GROUP3,SRC4").get(0).equals("1") && 
				str_count_map.get("GROUP3,SRC3").get(0).equals("1") && 
				str_count_map.get("GROUP2,SRC2").get(0).equals("1"));
	}


	@Test
	public void testFilterByNAI() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points in the bounds
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// run NAI (this is just a bounding box though! we should really test with a more complex polygon)
		SetId result_set_id = gaia.new_setid();
		List<Double> x_vector = new ArrayList<Double>();
		List<Double> y_vector = new ArrayList<Double>();
		// [assuming a box (1,1),(1,10),(10,10),(10,1)] 
		// REWRITE using Arrays.asList()
		x_vector.add(new Double(1));
		x_vector.add(new Double(1));
		x_vector.add(new Double(10));
		x_vector.add(new Double(10));

		y_vector.add(new Double(1));
		y_vector.add(new Double(10));
		y_vector.add(new Double(10));
		y_vector.add(new Double(1));

		filter_by_nai_response response = gaia.do_filter_by_nai(ns, result_set_id, "x", x_vector, "y", y_vector);
		System.out.println("NAI count:"+response.getCount());
		assertTrue(response.getCount() == 3); // THIS IS THE REAL COUNT

		// grab the RS; loop through the points
		NamedSet rs = gaia.getNamedSet(result_set_id, type);
		// list 
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}

		// let's perform a 2nd test too;		
		ns = gaia.newNamedSet(type);

		// add points in the bounds
		ArrayList<Object> all_points = new ArrayList<Object>();
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();

		// points
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1, 1, 0, "SRC1", "IN_BOX");
		all_points.add(p);		
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1, 1, 1, "SRC2", "IN_BOX");
		all_points.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 5, 5, 2, "SRC3", "IN_BOX");
		all_points.add(p);
		in_points.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 11, -11, 0, "SRC1", "OUT_BOX");
		all_points.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", -1, -1, 1, "SRC2", "OUT_BOX");
		all_points.add(p);		

		// add points
		ns.add_list(all_points);

		// nai [(0,0),(0,10),(10,10),(10,0)] NEEDS TO BE A PROPER ORDER
		List<Double> naiX = new ArrayList();
		naiX.add(new Double(0));
		naiX.add(new Double(0));
		naiX.add(new Double(10));
		naiX.add(new Double(10));

		List<Double> naiY = new ArrayList();
		naiY.add(new Double(0));
		naiY.add(new Double(10));
		naiY.add(new Double(10));
		naiY.add(new Double(0));

		// run NAI 
		result_set_id = gaia.new_setid();
		response = gaia.do_filter_by_nai(ns, result_set_id, "x", naiX, "y", naiY);
		System.out.println("NAI count:"+response.getCount());
		assertTrue(response.getCount() == in_points.size()); 

		// grab the RS; loop through the points
		rs = gaia.getNamedSet(result_set_id, type);
		// list 
		points = (ArrayList<BigPoint>)rs.list(0,response.getCount()-1);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}
	
	@Test
	public void testFilterByRadius() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points in the bounds
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", -77.03, 38.89, 0, "SRC1", "IN_RADIUS");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", -77.05, 38.89, 1, "SRC2", "IN_RADIUS");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", -77.13, 38.99, 2, "SRC3", "IN_RADIUS");
		ns.add(p);
		in_points.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", -77.03, 37.89, 0, "SRC1", "OUT_RADIUS");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", -79.03, 38.89, 1, "SRC2", "OUT_RADIUS");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", -77.03, 40.89, 2, "SRC3", "OUT_RADIUS");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", -78.03, 39.89, 2, "SRC3", "OUT_RADIUS");
		ns.add(p);

		// run FilterByRadius
		SetId result_set_id = gaia.new_setid();

		filter_by_radius_response response = gaia.do_filter_by_radius(ns, result_set_id, "x", "y", -77.03, 38.89, 75000.0);
		System.out.println("Radius count:"+response.getCount());
		assertTrue(response.getCount() == 3); // THIS IS THE REAL COUNT

		// grab the RS; loop through the points
		NamedSet rs = gaia.getNamedSet(result_set_id, type);
		// list 
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}

	}
	
	@Ignore
	public void testFilterByString() {
		
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points in the bounds
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "Test Msg ID1", -77.03, 38.89, 0, "test source 1", "IN_RADIUS");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "Production Msg", -77.05, 38.89, 1, "Prod", "IN_RADIUS");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "Staging Msg", -77.13, 38.99, 2, "Stage", "IN_RADIUS");
		ns.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "outside", -77.03, 37.89, 0, "SRC1", "OUT_RADIUS");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(),  "testing", -79.03, 38.89, 1, "outside", "OUT_RADIUS");
		ns.add(p);		
		
		SetId result_set_id = gaia.new_setid();

		ArrayList<CharSequence> options = new ArrayList<CharSequence>();
		ArrayList<CharSequence> attributes = new ArrayList<CharSequence>();
		
		filter_by_string_response response = gaia.do_filter_by_string(ns, result_set_id, "outside", "contains", options, attributes);
		System.out.println("String filter count:" + response.getCount()); // should print 2
	}

	@Test
	public void testStoreGroupBy() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		// group 1
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);

		// group 2
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);		

		// group 3
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 4.01, 2, "SRC3", "GROUP3");
		ns.add(p);

		// run group by on "group_id"
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("group_id");

		group_by_response response = gaia.do_group_by(ns, attributes);
		Map<CharSequence, List<CharSequence>> count_map = response.getCountMap();
		System.out.println(" Count_Map " + count_map.toString());

		// NOTE: so the CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, List<String>> str_count_map = new HashMap<String, List<String>>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();
				String str_key = key.toString();
				List utf8_list = count_map.get(key);
				List<String> str_list = new ArrayList<String>();
				for(int i=0; i<utf8_list.size(); i++) {
					org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)utf8_list.get(i);
					str_list.add(value.toString());
				}
				System.out.println(" Putting " + str_key + " " + str_list);
				str_count_map.put(str_key, str_list);
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}		
		System.out.println("GROUP1:"+str_count_map.get("GROUP1").get(0)+" GROUP2:"+str_count_map.get("GROUP2").get(0)+ " GROUP3:"+str_count_map.get("GROUP3").get(0));
		assertTrue(str_count_map.get("GROUP1").get(0).equals("3") && str_count_map.get("GROUP2").get(0).equals("1") && str_count_map.get("GROUP3").get(0).equals("2"));

		// store the group by (create the result sets)
		store_group_by_response sgb_response = gaia.do_store_group_by(ns, "group_id", count_map);
		System.out.println("finished store group by");
		String group_1_rs_id = str_count_map.get("GROUP1").get(1);
		NamedSet group_1_ns = gaia.getNamedSet(new SetId(group_1_rs_id), type);

		ArrayList<BigPoint> points = (ArrayList<BigPoint>)group_1_ns.list(0,2);
		assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: do fancier test...
			System.out.println(points.get(i));
		}
	}

	
	@Ignore
	public void testTracks() {
		// NOTE: testing the initialize, paging, and get sorted chain of commands
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// group 1
		List<Object> local_group1 = new ArrayList<Object>();		
		int count_group1 = 5;
		for(int i=0; i<count_group1; i++) {
			local_group1.add(new BigPoint(UUID.randomUUID().toString(), "MSGID"+i, i ,i, i, "SRC1", "GROUP1"));			
		}		
		ns.add_list(local_group1);

		// group 2
		List<Object> local_group2 = new ArrayList<Object>();
		int count_group2 = 2;
		for(int i=0; i<count_group2; i++) {
			local_group2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID"+i, i ,i, i, "SRC1", "GROUP2"));
		}		
		ns.add_list(local_group2);

		// group 3
		List<Object> local_group3 = new ArrayList<Object>();
		int count_group3 = 6;
		for(int i=0; i<count_group3; i++) {
			local_group3.add(new BigPoint(UUID.randomUUID().toString(), "MSGID"+i, i ,i, i, "SRC1", "GROUP3"));
		}		
		ns.add_list(local_group3);

		// run initialize "map_id"
		String map_id = UUID.randomUUID().toString();
		int page_size = 2;
		initialize_group_by_map_response response = this.gaia.do_initialize_group_by_map(ns.get_setid(), map_id, "group_id",page_size);
		// page size is 2 and three unique keys
		assertTrue(response.getNumberOfPages() == 2);
		assertTrue(response.getTotalNumberOfKeys() == 3);

		// get page 0 
		group_by_map_page_response page_response = this.gaia.do_group_by_map_page(map_id, 0);
		Map<CharSequence, List<CharSequence>> count_map = page_response.getCountMap();
		System.out.println("count map 0:"+count_map.toString());

		// get out the keys (unique group ids) and do a get sorted set on them all
		Iterator<CharSequence> iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		List<SetId> set_ids = new ArrayList<SetId>();
		List<String> group = new ArrayList<String>(); //groups that match the set id
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();								
				String str_key = key.toString();
				group.add(str_key);

				List utf8_list = count_map.get(key);

				// checking counts in the map are OK
				int count = Integer.parseInt(utf8_list.get(0).toString());
				if(str_key.equals("GROUP1")) {
					assertTrue(count == count_group1);
				}else if(str_key.equals("GROUP2")) {
					assertTrue(count == count_group2);
				}else if(str_key.equals("GROUP3")) {
					assertTrue(count == count_group3);
				}

				// add a set id for this set/key
				String rs = utf8_list.get(1).toString();
				set_ids.add(new SetId(rs));
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}
		System.out.println("about to get sorted sets; set_ids.size()"+set_ids.size());
		// get sorted sets
		List<List> list_of_lists = this.gaia.do_get_sorted_sets(set_ids, "timestamp", type);
		System.out.println("list_of_lists.size():"+list_of_lists.size());

		// the order should match what came in
		for(int i=0; i<group.size(); i++) {
			List<BigPoint> object_list = (List<BigPoint>)list_of_lists.get(i);
			String group_id = group.get(i);
			System.out.println("group_id:"+group_id);

			// loop through verifying the timestamp order [they all have the same timestamp order i.e. 0, 1, 2,...]
			for(int j=0; j<object_list.size(); j++) {
				System.out.println("object_list.get("+j+").timestamp="+object_list.get(j).timestamp);
				assertTrue(object_list.get(j).timestamp == j);
			}

			// verify that the lists match
			if(group_id.equals("GROUP1")) {				
				assertTrue(object_list.size() == local_group1.size());
				local_group1.removeAll(object_list);
				assertTrue(local_group1.size() == 0);
			}else if(group_id.equals("GROUP2")) {				
				assertTrue(object_list.size() == local_group2.size());
				local_group2.removeAll(object_list);
				assertTrue(local_group2.size() == 0);
			}else if(group_id.equals("GROUP3")) {				
				assertTrue(object_list.size() == local_group3.size());
				local_group3.removeAll(object_list);
				assertTrue(local_group3.size() == 0);
			}
		}

		// get page 1 
		page_response = this.gaia.do_group_by_map_page(map_id, 1);
		count_map = page_response.getCountMap();
		System.out.println("count map from the group by map:"+count_map.toString());

		// get out the keys (unique group ids) and do a get sorted set on them all
		iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		set_ids = new ArrayList<SetId>();
		group = new ArrayList<String>(); //groups that match the set id
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();								
				String str_key = key.toString();
				group.add(str_key);

				List utf8_list = count_map.get(key);

				// checking counts in the map are OK
				int count = Integer.parseInt(utf8_list.get(0).toString());
				if(str_key.equals("GROUP1")) {
					assertTrue(count == count_group1);
				}else if(str_key.equals("GROUP2")) {
					assertTrue(count == count_group2);
				}else if(str_key.equals("GROUP3")) {
					assertTrue(count == count_group3);
				}

				// add a set id for this set/key
				String rs = utf8_list.get(1).toString();
				set_ids.add(new SetId(rs));
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}
		System.out.println("finished while loop");

		// get sorted sets
		System.out.println("about to do get set sorted: set_ids.size():"+set_ids.size());
		list_of_lists = this.gaia.do_get_sorted_sets(set_ids, "timestamp", type);
		System.out.println("list_of_lists.size():"+list_of_lists.size());

		// the order should match what came in
		for(int i=0; i<group.size(); i++) {
			List<BigPoint> object_list = (List<BigPoint>)list_of_lists.get(i);
			String group_id = group.get(i);
			System.out.println("group_id:"+group_id);

			// loop through verifying the timestamp order [they all have the same timestamp order i.e. 0, 1, 2,...]
			for(int j=0; j<object_list.size(); j++) {
				System.out.println("object_list.get("+j+").timestamp="+object_list.get(j).timestamp);
				assertTrue(object_list.get(j).timestamp == j);
			}

			// verify that the lists match
			if(group_id.equals("GROUP1")) {				
				assertTrue(object_list.size() == local_group1.size());
				local_group1.removeAll(object_list);
				assertTrue(local_group1.size() == 0);
			}else if(group_id.equals("GROUP2")) {				
				assertTrue(object_list.size() == local_group2.size());
				local_group2.removeAll(object_list);
				assertTrue(local_group2.size() == 0);
			}else if(group_id.equals("GROUP3")) {				
				assertTrue(object_list.size() == local_group3.size());
				local_group3.removeAll(object_list);
				assertTrue(local_group3.size() == 0);
			}
		}

	}



	@Test
	public void testJoin() {
		//gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type);
		NamedSet right_set = gaia.newNamedSet(type);

		// Create some points and add them to the LEFT SET
		// group 1
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 3.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);

		// group 2
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		left_set.add(p);		

		// group 3
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 4.01, 2, "SRC3", "GROUP3");
		left_set.add(p);

		// Create some points and add them to the RIGHT SET
		// group 1
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID1", 4.01, 2.01, 0, "SRC1", "GROUP1");
		right_set.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID1", 5.01, 2.01, 0, "SRC1", "GROUP1");
		right_set.add(p);

		// group 2
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		right_set.add(p);		


		// run the join on "group_id"
		SetId result_set_id = gaia.new_setid();
		String join_type = UUID.randomUUID().toString();
		//join_response response = gaia.do_join(left_set, "group_id", right_set, "group_id", "JOINTYPE", result_set_id);
		join_response response = gaia.do_join(left_set, "group_id", right_set, "group_id", join_type, result_set_id);
		//join_response response = gaia.do_join(left_set, "x", right_set, "x", "JOINTYPE", result_set_id);
		System.out.println("Join count:"+response.getCount());
		assertTrue(response.getCount() == 7); // the first group1 makes two, the next makes two, next two, the last one make one so 7 
	}
	
	@Test
	public void testUnique() {
		//gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type);

		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 3.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		left_set.add(p);		

		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 4.01, 2, "SRC3", "GROUP3");
		left_set.add(p);

		unique_response response = gaia.do_unique(left_set, "msg_id");
		assertTrue(response.getIsString());
		List<CharSequence> values = response.getValuesStr();
		assertTrue(values.size() == 3);
		assertTrue(values.get(0).toString().equals("LMSGID1"));
		assertTrue(values.get(1).toString().equals("LMSGID2"));
		assertTrue(values.get(2).toString().equals("LMSGID3"));
		
		response = gaia.do_unique(left_set, "x");
		assertFalse(response.getIsString());
		List<Double> values2 = response.getValues();
		assertTrue(values2.size() == 3);
		assertTrue(values2.contains(1.01));
		assertTrue(values2.contains(2.01));
		assertTrue(values2.contains(3.01));
	}

	
	@Test
	public void testPredicateJoin() {
		//gaia.do_clear();
		Type type1 = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type1);
		
		Type type2 = gaia.create_type(SimplePoint.class);
		NamedSet right_set = gaia.newNamedSet(type2);

		// Create some points and add them to the LEFT SET
		// group 1

		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 3.01, 100, "SRC1", "GROUP1"));
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 3.01, 100, "SRC1", "GROUP1"));
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 13.01, 12.01, 100, "SRC1", "GROUP1"));
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 12.01, 100, "SRC1", "GROUP3"));
		
		right_set.add(new SimplePoint(UUID.randomUUID().toString(), 11.01, 3.01));
		right_set.add(new SimplePoint(UUID.randomUUID().toString(), 41.01, 3.01));
		right_set.add(new SimplePoint(UUID.randomUUID().toString(), 2.01, 3.01));
		right_set.add(new SimplePoint(UUID.randomUUID().toString(), 4.01, 3.01));
		
		// need a type transform between the two types
		
		Map<CharSequence,CharSequence> transform_map = new HashMap<CharSequence,CharSequence>();
		transform_map.put("x", "x");
		transform_map.put("y", "y");
		transform_map.put("OBJECT_ID", "OBJECT_ID");
		
		try {
			register_type_transform_response response = gaia.do_register_type_transform(type1.getID(), type2.getID(), transform_map);
		} catch(GaiaException e){
			if (!(e.toString().contains("already exists")))
			{
				throw e;
			}
		}
		
		// run predicate join
		
		SetId result_set_id = gaia.new_setid();
		String join_type = UUID.randomUUID().toString();
		
		predicate_join_response pj_response = gaia.do_predicate_join(left_set, right_set, type2.getID(), join_type, result_set_id, "(abs(LEFT.x - RIGHT.x) > 5.0)");
		
		System.out.println("Join count:"+pj_response.getCount());
		assertTrue(pj_response.getCount() == 9);  
	}
	
	@Test
	public void testMergeSets() {
		//gaia.do_clear();
		Type type1 = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type1);
		
		Type type2 = gaia.create_type(BigPoint.class);
		NamedSet right_set = gaia.newNamedSet(type1);

		// Set 1
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 3.01, 100, "SRC1", "GROUP1"));
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 3.01, 100, "SRC1", "GROUP1"));
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 13.01, 12.01, 100, "SRC1", "GROUP1"));
		left_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID4", 3.01, 12.01, 100, "SRC1", "GROUP1"));
		
		// Set 2
		right_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID11", 1.01, 3.01, 100, "SRC1", "GROUP2"));
		right_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID21", 2.01, 3.01, 100, "SRC1", "GROUP2"));
		right_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID31", 13.01, 12.01, 100, "SRC1", "GROUP2"));
		right_set.add(new BigPoint(UUID.randomUUID().toString(), "MSGID41", 3.01, 12.01, 100, "SRC1", "GROUP2"));
				
		SetId result_set_id = gaia.new_setid();
		List<SetId> in_sets = new ArrayList<SetId>();
		in_sets.add(left_set.get_setid());
		in_sets.add(right_set.get_setid());
		merge_sets_response response = gaia.do_merge_sets(in_sets, type1, result_set_id);
		
		NamedSet merged_ns = gaia.getNamedSet(result_set_id);
		assertTrue(merged_ns.list().size() == 8);  
	}

	@Test
	public void testDynamicDuo() {
		//gaia.do_clear();
		Type type1 = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newSingleNamedSet(type1);
		
		// create two tracks that will be a duo
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1, 1, 1, "SRC1", "GROUP1"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 2, 2, 2, "SRC1", "GROUP1"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 3, 3, 3, "SRC1", "GROUP1"));
		
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1, 1, 1, "SRC1", "GROUP2"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", -2, -2, -2, "SRC1", "GROUP2"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", -3, -3, -3, "SRC1", "GROUP2"));

		// not close enough in space
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 5, 1, 1, "SRC1", "GROUP3"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 5, -2, -2, "SRC1", "GROUP3"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 5, -3, -3, "SRC1", "GROUP3"));
		
		// not close enough in time
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 1, 1, 5, "SRC1", "GROUP4"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", -2, -2, 6, "SRC1", "GROUP4"));
		ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", -3, -3, 7, "SRC1", "GROUP4"));
				
		// run dynamic duo
		SetId result_set_id = gaia.new_setid();
		System.out.println("type1.getSemanticType():"+type1.getSemanticType());
		predicate_join_response response = gaia.do_dynamic_duo(ns, "x", "y", "timestamp", "group_id", 1, 1, result_set_id);
		
		System.out.println("response.getCount():"+response.getCount());
		NamedSet rs = gaia.getNamedSet(result_set_id);
		
		// joined set
		List<GenericObject> points = (ArrayList<GenericObject>)rs.list();
		System.out.println("points size:"+points.size());
		
		/// debug
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));
		}
		///
		assertTrue(points.size() == 1); 
						
		GenericObject go = points.get(0);
		assertTrue((go.getField("RIGHT_msg_id").toString().equals("MSGID1") && go.getField("LEFT_msg_id").toString().equals("MSGID2")) || 
				  (go.getField("RIGHT_msg_id").toString().equals("MSGID2") && go.getField("LEFT_msg_id").toString().equals("MSGID1")));
		
		
		// now test with parent sets
		Type bgType = gaia.create_type(BigPoint.class);
		// NOTE: only going to work against semantic type TRACK  
		Type ctType = gaia.create_type("{\"type\":\"record\",\"name\":\"CustomType\",\"fields\":[{\"name\":\"TRACKID\"" +
				",\"type\":\"string\"},{\"name\":\"x\",\"type\":\"double\"},{\"name\":\"y\",\"type\":\"double\"}," +
				"{\"name\":\"TIMESTAMP\",\"type\":\"double\"}, {\"name\":\"OBJECT_ID\",\"type\":\"string\"}]}", 
					"", "", SemanticTypeEnum.TRACK);
				
		// parent set id
		NamedSet parentNS = gaia.newParentNamedSet();	
		NamedSet bgChild = gaia.newChildNamedSet(parentNS, bgType);
		NamedSet ctChild = gaia.newChildNamedSet(parentNS, ctType);		
						
		// add points into the box area
		// create two tracks that will be a duo 
		bgChild.add(new BigPoint(UUID.randomUUID().toString(), "MSGID0", 1, 1, 1, "SRC1", "GROUP1"));
		bgChild.add(new BigPoint(UUID.randomUUID().toString(), "MSGID0", 2, 2, 2, "SRC1", "GROUP1"));
		bgChild.add(new BigPoint(UUID.randomUUID().toString(), "MSGID0", 3, 3, 3, "SRC1", "GROUP1"));
		
		// add the other duo 
		go = new GenericObject(); // this won't match up with the other guy
		go.addField("TRACKID", "GROUP2");
		go.addField("x", "1");
		go.addField("y", "1");
		go.addField("TIMESTAMP", "1");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		go = new GenericObject();
		go.addField("TRACKID", "GROUP2");
		go.addField("x", "2");
		go.addField("y", "2");
		go.addField("TIMESTAMP", "2");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		go = new GenericObject(); 
		go.addField("TRACKID", "GROUP2");
		go.addField("x", "3");
		go.addField("y", "3");
		go.addField("TIMESTAMP", "3");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		// another track 
		go = new GenericObject(); // this won't match up with the other guy
		go.addField("TRACKID", "GROUP3");
		go.addField("x", "1");
		go.addField("y", "1");
		go.addField("TIMESTAMP", "1");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		go = new GenericObject();
		go.addField("TRACKID", "GROUP3");
		go.addField("x", "-2");
		go.addField("y", "-2");
		go.addField("TIMESTAMP", "2");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		go = new GenericObject(); 
		go.addField("TRACKID", "GROUP3");
		go.addField("x", "-3");
		go.addField("y", "-3");
		go.addField("TIMESTAMP", "3");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		// another track
		go = new GenericObject(); 
		go.addField("TRACKID", "GROUP4");
		go.addField("x", "5");
		go.addField("y", "-3");
		go.addField("TIMESTAMP", "3");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		go = new GenericObject(); 
		go.addField("TRACKID", "GROUP4");
		go.addField("x", "5");
		go.addField("y", "-3");
		go.addField("TIMESTAMP", "3");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		go = new GenericObject(); 
		go.addField("TRACKID", "GROUP4");
		go.addField("x", "5");
		go.addField("y", "-3");
		go.addField("TIMESTAMP", "3");
		go.addField("OBJECT_ID", "0.0");
		ctChild.add(go);
		
		// run dynamic duo
		try {
			/*
			result_set_id = gaia.new_setid();
			response = gaia.do_dynamic_duo(parentNS, "x", "y", "TIMESTAMP", "TRACKID", 1, 1, result_set_id);			
			System.out.println("response.getCount():"+response.getCount());
			rs = gaia.getNamedSet(result_set_id);
			*/
			
			rs = parentNS.do_dynamic_duo(1, 1); 
		
			// joined set
			points = (ArrayList<GenericObject>)rs.list();
			System.out.println("points size:"+points.size());
		
			/// debug
			for(int i=0; i<points.size(); i++) {
				System.out.println(points.get(i));
			}
			///
			assertTrue(points.size() == 1); 
			
			go = points.get(0);
			assertTrue((go.getField("RIGHT_TRACKID").toString().equals("GROUP2") && go.getField("LEFT_TRACKID").toString().equals("GROUP3")) || 
					  (go.getField("RIGHT_TRACKID").toString().equals("GROUP3") && go.getField("LEFT_TRACKID").toString().equals("GROUP2")));
			
		} catch(Exception e) {
			System.err.println(e.toString());
		}						
	}
	
	@Test
	public void testCopySet() {
		//gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type);
		NamedSet right_set = gaia.newNamedSet(type);

		// Create some points and add them to the LEFT SET
		// group 1
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 3.01, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);

		// group 2
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		left_set.add(p);		

		// group 3
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 4.01, 2, "SRC3", "GROUP3");
		left_set.add(p);

		// Create some points and add them to the RIGHT SET
		// group 1
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID1", 4.01, 2.01, 0, "SRC1", "GROUP1");
		right_set.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID1", 5.01, 2.01, 0, "SRC1", "GROUP1");
		right_set.add(p);

		// group 2
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		right_set.add(p);		


		// run the join on "group_id"
		SetId join_set_id = gaia.new_setid();
		String join_type = UUID.randomUUID().toString();

		join_response response = gaia.do_join(left_set, "group_id", right_set, "group_id", join_type, join_set_id);
		System.out.println("Join count:"+response.getCount());
		assertTrue(response.getCount() == 7); // the first group1 makes two, the next makes two, next two, the last one make one so 7

		// copy the set
		SetId copied_set_id = gaia.new_setid();
		NamedSet join_ns = gaia.getNamedSet(join_set_id, type); // NOTE: type here, of course, is wrong
		
		try {
			copy_set_response copy_response = gaia.do_copy_set(join_ns, copied_set_id, type, "RIGHT");
			NamedSet copied_ns = gaia.getNamedSet(copied_set_id, type);
			System.out.println(copied_ns.getType().getTypeClass()+" other "+type.getTypeClass());
			assertTrue(copy_response.getCount() == 7);		

			// get set [so the casting is breaking it here...]
			ArrayList<BigPoint> points = (ArrayList<BigPoint>)copied_ns.list(0,(7-1));
			for(BigPoint point : points) {
				System.out.println(point.toString());
			}
		} catch(GaiaException e) {
			// NOTE: copy set has been turned off. Throws now.
			System.out.println(e.toString());
		}
	}


	@Test	
	public void testFilterByList() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points in the bounds
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1, 2.01, 0, "SRC1", "IN_LIST");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2, 2.01, 1, "SRC2", "IN_LIST");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_LIST");
		ns.add(p);
		in_points.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_LIST");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_LIST");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_LIST");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_LIST");
		ns.add(p);

		// run the filter by list
		SetId result_set_id = gaia.new_setid();
		// build the list mapping; let's get the points where x is in [1,2] and group_id in ["IN_LIST"]
		System.out.println("Build map...");
		Map<CharSequence, List<CharSequence>> attribute_map = new HashMap<CharSequence, List<CharSequence>>();
		try {
			attribute_map.put("x",new ArrayList());
			attribute_map.get("x").add(new String("1"));
			attribute_map.get("x").add(new String("2"));
			attribute_map.put("group_id",new ArrayList());
			attribute_map.get("group_id").add(new String("IN_LIST"));
		} catch(Exception e) {
			System.err.println(e.toString());
		}
		System.out.println("Map built;attribute_map:"+attribute_map.toString());

		filter_by_list_response response = gaia.do_filter_by_list(ns, result_set_id, attribute_map);
		NamedSet ns_list = gaia.getNamedSet(result_set_id, type);

		System.out.println("Filter by list:"+response.getCount());

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns_list.list(0,0);
		//assertTrue(points.size() == 3);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}

		assertTrue(response.getCount() == 2);
	}

	@Test
	public void testCluster() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points (for the subworld) [fake mgrs in source field]
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "40R", "IN");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "32S", "IN");
		ns.add(p);

		// not in subworld (box) but matching msgid so in cluster
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 13.01, 3.01, 2, "32S", "IN_WORLD");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 13.01, 3.01, 2, "32S", "IN_WORLD");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 13.01, 3.01, 2, "50P", "IN_WORLD");
		ns.add(p);

		// outside box and without a matching msgid
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 11.01, 20.01, 0, "SRC1", "OUT");
		ns.add(p);				
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID4", 12.01, 20.01, 0, "SRC1", "OUT");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT");
		ns.add(p);

		// run bounding box
		SetId bb_result_set_id = gaia.new_setid();
		bounding_box_response response = gaia.do_bounding_box(ns, bb_result_set_id, "x", "y", 0, 10, 0, 10);
		NamedSet bb_ns = gaia.getNamedSet(bb_result_set_id, type);
		assertTrue(response.getCount() == 2);

		// cluster call
		SetId f_result_set_id = gaia.new_setid(); // intermediate... NOTE: we should probably delete these
		cluster_response c_response = gaia.do_cluster(ns, bb_ns, f_result_set_id, "msg_id", "source");
		System.out.println(c_response.toString());

		Map<CharSequence, List<CharSequence>> count_map = c_response.getCountMap();
		System.out.println(count_map.toString());

		// NOTE: so the CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, List<String>> str_count_map = new HashMap<String, List<String>>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();
				String str_key = key.toString();
				List utf8_list = count_map.get(key);
				List<String> str_list = new ArrayList<String>();
				for(int i=0; i<utf8_list.size(); i++) {
					org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)utf8_list.get(i);
					str_list.add(value.toString());
				}
				str_count_map.put(str_key, str_list);
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}

		//System.out.println("GROUP1:"+str_count_map.get("GROUP1").get(0)+" GROUP2:"+str_count_map.get("GROUP2").get(0)+ " GROUP3:"+str_count_map.get("GROUP3").get(0));
		assertTrue(str_count_map.get("40R").get(0).equals("1") && str_count_map.get("50P").get(0).equals("1") && str_count_map.get("32S").get(0).equals("3"));
	}

	@Test
	public void testStats() {
		gaia.do_clear();

		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		A.add(p);

		NamedSet B = gaia.newNamedSet(type);

		// Create some points and add them
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		B.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		B.add(p);

		stats_response response = gaia.do_stats();
		Map<CharSequence, Integer> count_map = response.getCountMap();		
		System.out.println(count_map.toString());	

		// NOTE: need to convert to UTF8
		org.apache.avro.util.Utf8 key_a = new org.apache.avro.util.Utf8(A.get_setid().get_id());
		org.apache.avro.util.Utf8 key_b = new org.apache.avro.util.Utf8(B.get_setid().get_id());

		assertTrue(count_map.get(key_a).intValue() == 3 && count_map.get(key_b).intValue() == 2);
	}
	
	@Test
	public void testStatus() {
		gaia.do_clear();

		Type type = gaia.create_type(BigPoint.class);
		NamedSet A = gaia.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		A.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		A.add(p);

		NamedSet B = gaia.newNamedSet(type);

		// Create some points and add them
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		B.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		B.add(p);

		status_response response = gaia.do_status(null);

		List<Integer> sizes = response.getSizes();
		int cnt = 0;
		for( Integer size : sizes ) {
			cnt += size;
		}
		assertTrue(cnt == 5);
	}

	@Test
	public void testSetInfo() {
		Type type1 = gaia.create_type(BigPoint.class);
		Type type2 = gaia.create_type(BytesPoint.class);
		NamedSet A = gaia.newNamedSet(type1);		
		NamedSet B = gaia.newNamedSet(type1);

		NamedSet C = gaia.newNamedSet(type2);
		NamedSet D = gaia.newNamedSet(type2);

		set_info_response responseA = gaia.do_set_info(A.get_setid());
		set_info_response responseB = gaia.do_set_info(B.get_setid());
		set_info_response responseC = gaia.do_set_info(C.get_setid());
		set_info_response responseD = gaia.do_set_info(D.get_setid());

		System.out.println(responseA);
		System.out.println(responseB);
		System.out.println(responseC);
		System.out.println(responseD);

		assertTrue(responseA.getTypeIds().get(0).equals(responseB.getTypeIds().get(0)) && responseA.getTypeSchemas().get(0).equals(responseB.getTypeSchemas().get(0)));
		assertFalse(responseA.getTypeIds().get(0).equals(responseC.getTypeIds().get(0)));
		assertFalse(responseA.getTypeSchemas().get(0).equals(responseC.getTypeSchemas().get(0)));
		assertTrue(responseC.getTypeIds().get(0).equals(responseD.getTypeIds().get(0)) && responseC.getSchema().equals(responseD.getSchema()));
	}


	@Test
	public void testSort() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points		
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 7, "SRC1", "IN_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 6, "SRC2", "IN_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 5, "SRC3", "IN_BOX");
		ns.add(p);				
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID4", 10.01, 20.01, 4, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID5", 20.01, 20.01, 3, "SRC2", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 1, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 8, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 9, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 10, "SRC3", "OUT_BOX");
		ns.add(p);

		// run sort
		sort_response response = gaia.do_sort(ns, "timestamp");
		System.out.println("satus "+response.getStatus());
		assertTrue(response.getStatus().toString().equals("DONE")); 

		// NOTE: now the real test is the get set				 
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0,4);
		assertTrue(points.size() == 5);
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));
		}
		assertTrue(points.get(0).timestamp == 1 && points.get(1).timestamp == 2 && points.get(2).timestamp == 3 && 
				points.get(3).timestamp == 4 && points.get(4).timestamp == 5);

		points = (ArrayList<BigPoint>)ns.list(5, 9); // WARNING: going to list(5) will cause an error
		assertTrue(points.size() == 5);
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));
		}
		assertTrue(points.get(0).timestamp == 6 && points.get(1).timestamp == 7 && points.get(2).timestamp == 8 && 
				points.get(3).timestamp == 9 && points.get(4).timestamp == 10);
	}
		
	@Test
	public void testSelect() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// add points into the box area
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 4.01, 1, "SRC2", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 5.01, 2, "SRC3", "IN_BOX");
		ns.add(p);
		in_points.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 7.01, 2, "SRC3", "IN_BOX");
		ns.add(p);
		in_points.add(p);

		// outside the box
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID4", 10.01, 1.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID5", 20.01, 2.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// run select (really a bounding box)
		SetId result_set_id = gaia.new_setid();
		//select_response response = gaia.do_select(ns, result_set_id, "(x > 1) && (x < 10) && (y > 1) && (y < 10)");
		select_response response = gaia.do_select(ns, result_set_id, "(x < y);");
		System.out.println("The count back is:"+response.getCount()+" count expected is 4");
		assertTrue(response.getCount() == 4);

		// grab the RS; loop through the points
		NamedSet rs = gaia.getNamedSet(result_set_id, type);
		// list 
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0);
		assertTrue(points.size() == 4);
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}

	@Test
	public void testPlot2DMultiple() throws IOException {
		Type type = gaia.create_type(BytesPoint.class);

		List<NamedSet> namedSets = new ArrayList<NamedSet>();
		List<Long> colors = new ArrayList<Long>();
		List<Integer> sizes = new ArrayList<Integer>();

		//Set 1
		NamedSet ns1 = gaia.newNamedSet(type);			
		BytesPoint point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",10.0, 10.0,1,"SRC1", "GROUP1",ByteBuffer.wrap("b1".getBytes()));
		ns1.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",15.0, 15.0,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary".getBytes()));		
		ns1.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID3",20.0, 20.0,1,"SRC3", "GROUP3", ByteBuffer.wrap("Binary Test Data3".getBytes()));		
		ns1.add(point);

		namedSets.add(ns1);
		colors.add(new Long(0xFFFF0000));
		sizes.add(new Integer(1));

		//Set 2
		NamedSet ns2 = gaia.newNamedSet(type);			
		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",10.0, 20.0,1,"SRC1", "GROUP1",ByteBuffer.wrap("b1".getBytes()));
		ns2.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",12.0, 18.0,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary".getBytes()));		
		ns2.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID3",14.0, 18.0,1,"SRC3", "GROUP3", ByteBuffer.wrap("Binary Test Data3".getBytes()));		
		ns2.add(point);

		namedSets.add(ns2);
		colors.add(new Long(0xFF00FF00));
		sizes.add(new Integer(1));

		//Set 3
		NamedSet ns3 = gaia.newNamedSet(type);			
		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",20.0, 10.0,1,"SRC1", "GROUP1",ByteBuffer.wrap("b1".getBytes()));
		ns3.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",18.0, 11.0,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary".getBytes()));		
		ns3.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID3",17.0, 13.0,1,"SRC3", "GROUP3", ByteBuffer.wrap("Binary Test Data3".getBytes()));		
		ns3.add(point);

		namedSets.add(ns3);
		colors.add(new Long(0xFF0000FF));
		sizes.add(new Integer(1));

		// run the plot2dmultiple calculation 
		plot2d_multiple_response response = gaia.do_plot2d_multiple(namedSets, colors, sizes, "x", "y", 0.0, 30.0, 0.0, 30.0, 500, 500, "web_mercator", 0);				

		// what to check this against?
		ByteBuffer image_byte_buffer = response.getImageData();

		//write resulting image to a file
		/*
		FileOutputStream fos = new FileOutputStream("/home/eli/test_java_api_multiple.png");
		FileChannel channel = fos.getChannel();

		channel.write(image_byte_buffer);

		fos.close();
		 */

	}

	@Ignore
	public void testJoinSetup() {
		//gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type);
		NamedSet right_set = gaia.newNamedSet(type);

		// Create some points and add them to the LEFT SET
		// matches
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID2", 2, 2.01, 1, "SRC2", "GROUP2");
		left_set.add(p);		

		// not matches
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 3.01, 2, "SRC3", "LNOTMATCH");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 4.01, 2, "SRC3", "LNOTMATCH");
		left_set.add(p);

		// Create some points and add them to the RIGHT SET
		// matches
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID1", 2, 2.01, 0, "SRC1", "GROUP1");
		right_set.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID2", 2, 2.01, 0, "SRC1", "GROUP2");
		right_set.add(p);

		// not matches
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 2.01, 2.01, 1, "SRC2", "RNOTMATCH");
		right_set.add(p);		


		// run the join setup; determine the subset
		SetId subset = gaia.new_setid();	
		join_setup_response response = gaia.do_join_setup(left_set, "x", right_set, "x", subset);

		System.out.println("Subset count:"+response.getCount());
		assertTrue(response.getCount() == 3); 
	}


	@Ignore
	public void testJoinIncremental() {
		//gaia.do_clear();
		Type type = gaia.create_type(BigPoint.class);
		NamedSet left_set = gaia.newNamedSet(type);
		NamedSet right_set = gaia.newNamedSet(type);

		// Create some points and add them to the LEFT SET
		// matches
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID1", 2, 2.01, 0, "SRC1", "GROUP1");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID2", 2, 2.01, 1, "SRC2", "GROUP2");
		left_set.add(p);		

		// not matches
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 3.01, 2, "SRC3", "LNOTMATCH");
		left_set.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "LMSGID3", 3.01, 4.01, 2, "SRC3", "LNOTMATCH");
		left_set.add(p);

		// Create some points and add them to the RIGHT SET
		// matches
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID1", 2, 2.01, 0, "SRC1", "GROUP1");
		right_set.add(p);		
		p = new BigPoint(UUID.randomUUID().toString(), "RMSGID2", 2, 2.01, 0, "SRC1", "GROUP2");
		right_set.add(p);

		// not matches
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 2.01, 2.01, 1, "SRC2", "RNOTMATCH");
		right_set.add(p);		


		// run the join setup; determine the subset
		SetId subset = gaia.new_setid();
		System.out.println("subset id:"+subset.get_id());
		join_setup_response response = gaia.do_join_setup(left_set, "x", right_set, "x", subset);

		System.out.println("Subset count:"+response.getCount());
		assertTrue(response.getCount() == 3);

		// now do the incremental
		SetId result_set = gaia.new_setid();
		String result_type = UUID.randomUUID().toString();
		NamedSet ns_subset = gaia.getNamedSet(subset, type);
		System.out.println("result_set:"+result_set.get_id());
		join_incremental_response ji_response = gaia.do_join_incremental(ns_subset, "x", 0, right_set, "x", result_type, result_set);
		assertTrue(ji_response.getCount() == 2);

		// grab the rs 
		NamedSet rs = gaia.getNamedSet(result_set);
		System.out.println("Got the result set");

		// list 
		ArrayList<GenericObject> points = (ArrayList<GenericObject>)rs.list(0);
		System.out.println("points size:"+points.size());
		assertTrue(points.size() == 2); // NOTE: the set is the result of only one join incremental so far

		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}

		ji_response = gaia.do_join_incremental(ns_subset, "x", 1, right_set, "x", result_type, result_set);
		assertTrue(ji_response.getCount() == 2); 

		// list 
		points = (ArrayList<GenericObject>)rs.list(0);
		System.out.println("points size:"+points.size());
		assertTrue(points.size() == 4); // NOTE: the set is the result of only one join incremental so far

		ArrayList<Map<String,String>> map_list = new ArrayList<Map<String,String>>();
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));

			String[] sa = points.get(i).toString().split(" ");
			Map<String,String> map = new HashMap<String,String>();
			for(int j=0; j<sa.length; j+=1) { // loop through key values
				String[] sa2 = sa[j].split(":");
				map.put(sa2[0], sa2[1]);
				System.out.println("obj:"+i+" key:"+sa2[0]+" value:"+sa2[1]);				
			}
			map_list.add(map);
		}
	}


	@Ignore
	public void testJoinIncrementalKeyValue() {
		//gaia.do_clear();
		Type type = gaia.create_type(KeyValueType.class);
		NamedSet left_set = gaia.newNamedSet(type);
		NamedSet right_set = gaia.newNamedSet(type);

		// Create some points and add them to the LEFT SET
		// matches
		KeyValueType kv = new KeyValueType(UUID.randomUUID().toString(), "lkey1","value1");
		left_set.add(kv);
		kv = new KeyValueType(UUID.randomUUID().toString(), "lkey2","value1");
		left_set.add(kv);
		kv = new KeyValueType(UUID.randomUUID().toString(), "lkey3","value1");
		left_set.add(kv);

		// not matches

		// Create some points and add them to the RIGHT SET
		// matches
		kv = new KeyValueType(UUID.randomUUID().toString(), "rkey1","value1");
		right_set.add(kv);
		kv = new KeyValueType(UUID.randomUUID().toString(), "rkey2","value1");
		right_set.add(kv);

		// not matches
		kv = new KeyValueType(UUID.randomUUID().toString(), "rkey3","not_value2");
		right_set.add(kv);


		// run the join setup; determine the subset
		SetId subset = gaia.new_setid();	
		join_setup_response response = gaia.do_join_setup(left_set, "value", right_set, "value", subset);

		System.out.println("Subset count:"+response.getCount());
		assertTrue(response.getCount() == 3);

		// now do the incremental
		SetId result_set = gaia.new_setid();
		String result_type = UUID.randomUUID().toString();
		NamedSet ns_subset = gaia.getNamedSet(subset, type);
		join_incremental_response ji_response = gaia.do_join_incremental(ns_subset, "value", 0, right_set, "value", result_type, result_set);
		assertTrue(ji_response.getCount() == 2);

		// grab the rs 
		NamedSet rs = gaia.getNamedSet(result_set);
		System.out.println("Got the result set");

		// list 
		ArrayList<GenericObject> points = (ArrayList<GenericObject>)rs.list(0);
		System.out.println("points size:"+points.size());
		assertTrue(points.size() == 2); // NOTE: the set is the result of only one join incremental so far

		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}

		ji_response = gaia.do_join_incremental(ns_subset, "value", 1, right_set, "value", result_type, result_set);
		assertTrue(ji_response.getCount() == 2); 

		// list 
		points = (ArrayList<GenericObject>)rs.list(0);
		System.out.println("points size:"+points.size());
		assertTrue(points.size() == 4); // NOTE: the set is the result of only one join incremental so far

		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality
			System.out.println(points.get(i));
		}
	}


	@Test
	public void testStoreGroupBySort() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		// group 1

		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1");
		ns.add(p);


		// group 2
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);		

		// group 3
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 4.01, 2, "SRC3", "GROUP3");
		ns.add(p);

		// run group by on "group_id"
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("group_id");

		group_by_response response = gaia.do_group_by(ns, attributes);
		Map<CharSequence, List<CharSequence>> count_map = response.getCountMap();
		System.out.println(count_map.toString());

		// NOTE: so the CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, List<String>> str_count_map = new HashMap<String, List<String>>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();
		// iterating through the Utf8 map and building a new string based one
		while(iter.hasNext()) {		
			try {
				org.apache.avro.util.Utf8 key = (org.apache.avro.util.Utf8)iter.next();
				String str_key = key.toString();
				List utf8_list = count_map.get(key);
				List<String> str_list = new ArrayList<String>();
				for(int i=0; i<utf8_list.size(); i++) {
					org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)utf8_list.get(i);
					str_list.add(value.toString());
				}
				str_count_map.put(str_key, str_list);
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}		
		System.out.println("GROUP1:"+str_count_map.get("GROUP1").get(0)+" GROUP2:"+str_count_map.get("GROUP2").get(0)+ " GROUP3:"+str_count_map.get("GROUP3").get(0));
		assertTrue(str_count_map.get("GROUP1").get(0).equals("5") && str_count_map.get("GROUP2").get(0).equals("1") && str_count_map.get("GROUP3").get(0).equals("2"));

		// store the group by (create the result sets)
		store_group_by_response sgb_response = gaia.do_store_group_by(ns, "group_id", count_map, "timestamp");
		String group_1_rs_id = str_count_map.get("GROUP1").get(1);
		NamedSet group_1_ns = gaia.getNamedSet(new SetId(group_1_rs_id), type);

		ArrayList<BigPoint> points = (ArrayList<BigPoint>)group_1_ns.list(0,4);
		assertTrue(points.size() == 5);
		for(int i=0; i<points.size(); i++) {
			System.out.println(points.get(i));
		}
	}

	@Ignore
	public void testTriggering() {
		
		//if( true ) return;
		gaia.do_clear();
		
		Type type = gaia.create_type(BigPoint.class);
		SetId si = gaia.new_setid("TEST_TRIGGERING_SET");
		NamedSet ns = gaia.newSingleNamedSet(si, type);
		
		// grouping attribute? used for determining tracks basically, so we can find when a track USED to trigger something but has stopped
		register_trigger_range_response response = gaia.do_register_trigger(ns.get_setid(), "x", 1, 10, "group_id");
		//register_trigger_range_response response = gaia.do_register_trigger(ns.get_setid(), "x", 1, 10, "x");
		
		// start up a thread to do adds in a second
		Runnable r = new Runnable() {
			public void run() {
				
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					///				
				}
				
				NamedSet ns = gaia.getNamedSet(new SetId("TEST_TRIGGERING_SET"));
				System.out.println("Got this named set");
				
				ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
				System.out.println("added");
				
			}
		};
		
		Thread t = new Thread(r);
		t.start();			
		
		BigPoint p = null;
		try {
			p = (BigPoint)gaia.do_listen_for_this_trigger(response.getTriggerId().toString());
			System.out.print(p.toString());
		} catch(Exception e) {
			System.err.println("ERROR while listening to trigger:"+e.toString());			
		}
		assertTrue(p != null);
		
	}
	
	@Ignore
	public void testRoadIntersection() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);
		
		// x road vector; see python test for more
		List<Double> x_road_vals = Arrays.asList(6.0,5.0,8.0,4.0);
		List<Double> y_road_vals = Arrays.asList(8.0,6.0,5.0,3.0);
		
		// Track 1: just left side
		BigPoint p;
		List<Object> points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_001", 2.0, 9.0, 1, "SRC_ID_001", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_002", 2.0, 8.0, 2, "SRC_ID_002", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_003", 1.0, 6.0, 3, "SRC_ID_003", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_004", 1.0, 4.0, 4, "SRC_ID_004", "GROUP_001"));		
		
		// Track 2: just right side
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_005",10.0, 8.0, 1, "SRC_ID_001", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_006", 9.0, 7.0, 2, "SRC_ID_002", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_007", 8.0, 6.0, 3, "SRC_ID_003", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_008", 7.0, 7.0, 4, "SRC_ID_004", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_009", 6.0, 7.0, 4, "SRC_ID_004", "GROUP_002"));		
		
		// Track 3: on both sides
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_010", 2.0, 5.0, 1, "SRC_ID_001", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_011", 3.0, 4.0, 2, "SRC_ID_002", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_012", 4.0, 4.0, 3, "SRC_ID_003", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_013", 5.0, 4.0, 4, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_014", 6.0, 4.0, 4, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_015", 7.0, 3.0, 4, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_016", 8.0, 3.0, 4, "SRC_ID_004", "GROUP_003"));
		        
	    //Track 4: both sides
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_017", 0.0, 8.0, 1, "SRC_ID_001", "GROUP_004"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_018",10.0, 4.0, 2, "SRC_ID_002", "GROUP_004"));
			
		// add the pints
		ns.add_list(points);
		
		// do road intersections
		try {
			road_intersection_response response = gaia.do_road_intersection(ns.get_setid(), "x", "y", x_road_vals, y_road_vals, "group_id");
			List<CharSequence> tracks_that_crossed = response.getMatchingOutputAttributes();
			assertTrue(tracks_that_crossed.size() == 2);			

			// don't work...
			//tracks_that_crossed.remove(new org.apache.avro.util.Utf8("GROUP_003"));
			//tracks_that_crossed.remove(new org.apache.avro.util.Utf8("GROUP_004"));

			// make sure just 003 and 004 
			for(int i=0; i<tracks_that_crossed.size(); i++) {
				String track = tracks_that_crossed.get(i).toString();				
				assertTrue(track.equals("GROUP_003") || track.equals("GROUP_004"));
			}
		} catch(GaiaException e) {
			System.out.println(e.toString());
		}		
	}
	
	@Test
	public void testSpatialQuery() {			
		assertTrue(gaia.do_spatial_query("POLYGON((1.0 1.0,4.0 1.0,4.0 4.0,1.0 4.0,1.0 1.0))", "POLYGON((3.0 2.0,5.0 2.0,5.0 3.0,3.0 3.0))", SpatialOperationEnum.INTERSECTS));
		assertFalse(gaia.do_spatial_query("POLYGON((1.0 1.0,4.0 1.0,4.0 4.0,1.0 4.0,1.0 1.0))", "POLYGON((3.0 2.0,5.0 2.0,5.0 3.0,3.0 3.0))", SpatialOperationEnum.DISJOINT));		
	}
			
	@Test
	public void testUpdateSetTTL() {
		Type type = gaia.create_type(BigPoint.class);
		NamedSet ns = gaia.newNamedSet(type);

		// Create some points and add them
		List<Object> points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1"));		
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1"));

		// adding the points
		ns.add_list(points);
		
		// do stats; make sure the set is initially populated
		stats_response response = gaia.do_stats();
		assertTrue(gaia.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns.get_setid().get_id())));

		// update the set TTL
		try {
			// NOTE: this means kill in 1 minute
			gaia.do_update_ttl(ns.get_setid(), 1);
		} catch(GaiaException e) {
			System.out.println(e.toString());
		}
		
		// now continually check the size of the set until it has been deleted
		int milliseconds_waited = 0;
		int delta = 5000;
		while(true) {			
			try {
				// wait; 5 seconds
				System.out.println("Sleep for "+delta+" total waited:"+milliseconds_waited);
				Thread.sleep(delta);
				milliseconds_waited += delta;
			} catch (Exception e) {
				System.err.println(e.toString());
			}
			
			if(!gaia.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns.get_setid().get_id()))) {
				// deleted! sucess
				return;
			}
			
			// we have failed if... (2 minutes)
			assertTrue(milliseconds_waited <= 120000);
		} 
		
	}
	
	@Test
	public void testUpdateSetTTLForManySets() {
		
		gaia.do_clear();
		Map<SetId, Integer> ttlSet = new HashMap<SetId, Integer>();
		
		Type type1 = gaia.create_type(BigPoint.class);
		NamedSet ns1 = gaia.newNamedSet(type1);

		// Create some points and add them
		List<Object> points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1"));		
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1"));

		// adding the points
		ns1.add_list(points);
		
		// do stats; make sure the set is initially populated
		stats_response response = gaia.do_stats();
		assertTrue(gaia.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns1.get_setid().get_id())));
		
		Type type2 = gaia.create_type(BigPoint.class);
		NamedSet ns2 = gaia.newNamedSet(type2);

		// Create some points and add them
		List<Object> points2 = new ArrayList<Object>();
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 1, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 3, "SRC1", "GROUP2"));		
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 4, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 5, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 2, "SRC1", "GROUP2"));

		// adding the points
		ns2.add_list(points2);
		
		// do stats; make sure the set is initially populated
		stats_response response2 = gaia.do_stats();
		assertTrue(gaia.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns2.get_setid().get_id())));
		
		ttlSet.put(ns1.get_setid(), 1);
		ttlSet.put(ns2.get_setid(), 2);
		
		// update the set TTL
		try {
			// NOTE: this means kill in 1 minute
			gaia.do_update_ttl(ttlSet);
		} catch(GaiaException e) {
			System.out.println(e.toString());
		}
		
		int set1TTL = gaia.getSetTTL(ns1);
		assertTrue(set1TTL == 1);
		
		int set2TTL = gaia.getSetTTL(ns2);
		assertTrue(set2TTL == 2);
		
		gaia.do_clear();
		List<SetId> setList = new ArrayList<SetId>();
		
		type1 = gaia.create_type(BigPoint.class);
		ns1 = gaia.newNamedSet(type1);

		// Create some points and add them
		points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1"));		
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1"));

		// adding the points
		ns1.add_list(points);
		
		// do stats; make sure the set is initially populated
		response = gaia.do_stats();
		assertTrue(gaia.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns1.get_setid().get_id())));
		
		type2 = gaia.create_type(BigPoint.class);
		ns2 = gaia.newNamedSet(type2);

		// Create some points and add them
		points2 = new ArrayList<Object>();
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 1, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 3, "SRC1", "GROUP2"));		
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 4, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 5, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 2, "SRC1", "GROUP2"));

		// adding the points
		ns2.add_list(points2);
		
		// do stats; make sure the set is initially populated
		response2 = gaia.do_stats();
		assertTrue(gaia.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns2.get_setid().get_id())));

		setList.add(ns1.get_setid());
		setList.add(ns2.get_setid());
		
		// update the set TTL
		try {
			// NOTE: this means kill in 1 minute
			gaia.do_update_ttl(setList, 2);
		} catch(GaiaException e) {
			System.out.println(e.toString());
		}
		
		set1TTL = gaia.getSetTTL(ns1);
		assertTrue(set1TTL == 2);
		
		set2TTL = gaia.getSetTTL(ns2);
		assertTrue(set2TTL == 2);
	}
	
	@Test
	public void testFilterByValue() {
		// Follows python test created by EG.....
		gaia.do_clear();
		
		NamedSet parent = gaia.newParentNamedSet();
		Type type1 = gaia.create_type(BigPoint.class);
		SetId result_set_id = gaia.new_setid();	

		NamedSet ns1 = gaia.newChildNamedSet(parent, type1);

		
		// fbv on empty set
		filter_by_value_response fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "");
		assertTrue(fbvr.getCount() == 0);

		// fbv on an attribute that doesnt exist
		try {
			fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "abcd");
		} catch( GaiaException ge) {
			System.out.println(ge.getMessage());
			assertTrue(ge.getMessage().contains("doesn't have an attribute named: abcd"));
		}
		
		// Create some points and add them
		List<Object> points2 = new ArrayList<Object>();
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 5.1, 6.0, 1, "SRC1", "GROUPA"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 5.2, 6.1, 2, "SRC2", "GROUPB"));		
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 5.3, 5.1, 3, "SRC3", "GROUPC"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 5.4, 6.3, 4, "SRC1", "GROUPC"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 5.5, 6.4, 5, "SRC2", "GROUPC"));

		// adding the points
		ns1.add_list(points2);
		
		// fbv with double value
		result_set_id = gaia.new_setid();
	    fbvr = gaia.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "");
		assertTrue(fbvr.getCount() == 2);
		
		// fbv with a string
		result_set_id = gaia.new_setid();
	    fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "MSGID1", "");
		assertTrue(fbvr.getCount() == 2);
		
		// fbv with a string
		result_set_id = gaia.new_setid();
	    fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "SRC1", "");
		assertTrue(fbvr.getCount() == 2);
		
		// fbv with a string
		result_set_id = gaia.new_setid();
	    fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "GROUPC", "");
		assertTrue(fbvr.getCount() == 3);
		
		// fbv with a string
		result_set_id = gaia.new_setid();
	    fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "abcd", "");
		assertTrue(fbvr.getCount() == 0);
		
		// fbv with a double value
		result_set_id = gaia.new_setid();
		fbvr = gaia.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "");
		assertTrue(fbvr.getCount() == 2);
		
		// fbv with a double value with a given attribute
		result_set_id = gaia.new_setid();
		fbvr = gaia.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "y");
		assertTrue(fbvr.getCount() == 1);

		// fbv with a double value with a given attribute
		result_set_id = gaia.new_setid();
		fbvr = gaia.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "x");
		assertTrue(fbvr.getCount() == 1);

		Type type2 = gaia.create_type(SimplePoint.class);
		NamedSet child2 = gaia.newChildNamedSet(parent, type2);
		
		// fbv with a double value on a parent set
		result_set_id = gaia.new_setid();
		fbvr = gaia.do_filter_by_value(parent, result_set_id, false, 5.1, "", "x");
		assertTrue(fbvr.getCount() == 1);
		
		// Create some points and add them
		List<Object> points = new ArrayList<Object>();
		points.add(new SimplePoint(UUID.randomUUID().toString(), 5.1, 6.1));
		points.add(new SimplePoint(UUID.randomUUID().toString(), 5.1, 6.1));
		child2.add_list(points);
		
		// fbv with a double value on a parent set
		result_set_id = gaia.new_setid();
		fbvr = gaia.do_filter_by_value(parent, result_set_id, false, 5.1, "", "");
		assertTrue(fbvr.getCount() == 4);
		
		NamedSet rs = parent.filterByValue(5.1, "");		
		assertTrue(rs.getChildren().size() == 2);
		int totObjects = 0;
		Collection<NamedSet> sets = rs.getChildren();
		for( NamedSet ns : sets ) {
			totObjects += ns.size();
		}
		assertTrue(totObjects == 4);
	}
	
	@Test
	public void testFilterBySet() {
		// Follows python test created by EG.....
		gaia.do_clear();
		
		NamedSet parent = gaia.newParentNamedSet();
		Type type1 = gaia.create_type(BigPoint.class);
		SetId result_set_id = gaia.new_setid();	

		NamedSet ns1 = gaia.newChildNamedSet(parent, type1);
		
		// fbv on empty set
		filter_by_value_response fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "");
		assertTrue(fbvr.getCount() == 0);

		// fbv on an attribute that doesnt exist
		try {
			fbvr = gaia.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "abcd");
		} catch( GaiaException ge) {
			System.out.println(ge.getMessage());
			assertTrue(ge.getMessage().contains("doesn't have an attribute named: abcd"));
		}
		
		// Create some points and add them
		List<Object> points2 = new ArrayList<Object>();
		points2.add(new BigPoint(UUID.randomUUID().toString(), "Alice", 5.1, 6.0, 1, "SRC1", "GROUPA"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 5.2, 6.1, 2, "SRC2", "GROUPB"));		
		points2.add(new BigPoint(UUID.randomUUID().toString(), "Alice", 5.3, 5.1, 3, "SRC3", "GROUPC"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "Bob", 5.4, 6.3, 4, "SRC1", "GROUPC"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 5.5, 6.4, 5, "SRC2", "GROUPC"));

		// adding the points
		ns1.add_list(points2);
		
		// test of using the string definition; create a type; then a new set; add objects 
		Type type = gaia.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		NamedSet ns = gaia.newSingleNamedSet(type);

		// Create some objects;
		GenericObject go = new GenericObject();
		go.addField("person", "Alice");
		go.addField("age", "21");
		go.addField("OBJECT_ID", "0.0");
		ns.add(go);
		go = new GenericObject();
		go.addField("person", "Bob");
		go.addField("age", "25");
		go.addField("OBJECT_ID", "0.0");
		ns.add(go); 
		
		// Now call the API
		NamedSet result = ns1.do_filter_by_set("msg_id", ns.get_setid(), "person");
		// 2 Alice and 1 bob = total of 3
		System.out.println(" Count is " + result.size());
		assertTrue(result.size() == 3);
		
	}
}