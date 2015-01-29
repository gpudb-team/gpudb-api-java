package com.gisfederal.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import avro.java.gpudb.add_object_response;
import avro.java.gpudb.bounding_box_response;
import avro.java.gpudb.bulk_add_response;
import avro.java.gpudb.bulk_delete_response;
import avro.java.gpudb.bulk_select_response;
import avro.java.gpudb.bulk_update_response;
import avro.java.gpudb.clear_response;
import avro.java.gpudb.convex_hull_response;
import avro.java.gpudb.copy_set_response;
import avro.java.gpudb.filter_by_bounds_response;
import avro.java.gpudb.filter_by_list_response;
import avro.java.gpudb.filter_by_nai_response;
import avro.java.gpudb.filter_by_radius_response;
import avro.java.gpudb.filter_by_string_response;
import avro.java.gpudb.filter_by_value_response;
import avro.java.gpudb.filter_then_histogram_response;
import avro.java.gpudb.get_orphans_response;
import avro.java.gpudb.get_sets_by_type_info_response;
import avro.java.gpudb.get_type_info_response;
import avro.java.gpudb.group_by_response;
import avro.java.gpudb.group_by_value_response;
import avro.java.gpudb.histogram_response;
import avro.java.gpudb.join_incremental_response;
import avro.java.gpudb.join_response;
import avro.java.gpudb.join_setup_response;
import avro.java.gpudb.make_bloom_response;
import avro.java.gpudb.max_min_response;
import avro.java.gpudb.merge_sets_response;
import avro.java.gpudb.predicate_join_response;
import avro.java.gpudb.register_parent_set_response;
import avro.java.gpudb.register_trigger_nai_response;
import avro.java.gpudb.register_trigger_range_response;
import avro.java.gpudb.register_type_transform_response;
import avro.java.gpudb.road_intersection_response;
import avro.java.gpudb.select_response;
import avro.java.gpudb.server_status_response;
import avro.java.gpudb.set_info_response;
import avro.java.gpudb.stats_response;
import avro.java.gpudb.status_response;
import avro.java.gpudb.store_group_by_response;
import avro.java.gpudb.unique_response;
import avro.java.gpudb.update_object_response;

import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.GenericObject;
import com.gisfederal.NamedSet;
import com.gisfederal.SetId;
import com.gisfederal.Type;
import com.gisfederal.semantic.types.AnnotationAttributeEnum;
import com.gisfederal.semantic.types.Polygon;
import com.gisfederal.semantic.types.SemanticTypeEnum;
import com.gisfederal.semantic.types.Track;
import com.gisfederal.utils.GPUdbApiUtil;
import com.gisfederal.utils.NullObject;
import com.gisfederal.utils.SpatialOperationEnum;
import com.gisfederal.utils.StatisticsOptionsEnum;

public class TestGpudb {
	private static GPUdb gPUdb;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
		System.out.println("Build gpudb...");
		String gpudbURL = System.getProperty("GPUDB_URL", "http://172.30.20.177:9191");
		
		//String gpudbURL = System.getProperty("GPUDB_URL", "https://172.30.20.27:9191");

		
		//String gpudbURL = System.getProperty("GPUDB_URL", "http://192.168.56.101:9191");

		String disableTrigger = System.getProperty("GPUDB_DISABLE_TRIGGER", "TRUE");

		if( Boolean.parseBoolean(disableTrigger) ) {
			System.out.println("XXXXXXXXXXXXXXXXXXXXX");
			gPUdb = new GPUdb(gpudbURL, "", "admin_user", "admin_user_password");
		} else {
			URL myURL = new URL(gpudbURL);
			String host = myURL.getHost();
			int port = myURL.getPort();
			System.out.println(" Trigger host is " + host);
			gPUdb = GPUdb.newGpudbTrigger(host, port, "tcp://"+ host +":9001", "", "admin_user", "admin_user_password");
		}
		gPUdb.setCollectForReplay(true);
		System.out.println("Built gpudb");
	}
	
	@Test
	public void testUniqueHATest() {
		NamedSet left_set = gPUdb.getNamedSet(new SetId("Twitter"));
		
		List<StatisticsOptionsEnum> stats = new ArrayList<StatisticsOptionsEnum>();
		stats.add(StatisticsOptionsEnum.CARDINALITY);		
		Map<String, Double> result = left_set.statistics(stats, "URL");
		System.out.println("URL unique " + result);
		result = left_set.statistics(stats, "y");
		System.out.println("y unique " + result);
		result = left_set.statistics(stats, "TIMESTAMP");
		System.out.println("timestamp unique " + result);
	}


	@Test
	public void testTypeCreation() {

		//System.setProperty("javax.net.ssl.trustStore","C:/software_downloads/JKSTRUST/systemCA.jks");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		Type type = gPUdb.create_type(BigPoint.class);
		System.out.println("Type id:"+type.getID());
		assertNotNull(type.getID());
	}
	
	@Test
	public void testUniqueConstraint() {

		// Clear previous data in gaia
		gPUdb.do_clear();
		
		String definition = "{\"type\":\"record\",\"name\":\"TestType\",\"fields\":" +
				"[{\"name\":\"FL\",\"type\":\"long\"}," +
				"{\"name\":\"FL2\",\"type\":\"long\"}, " +
				"{\"name\":\"FS\",\"type\":\"string\"} " +
				"]}";
		
		String label = "label1";
		SemanticTypeEnum semanticType = SemanticTypeEnum.EMPTY;

		Map<CharSequence, List<CharSequence>> annotation_attributes =
				new HashMap<CharSequence, List<CharSequence>>();

		
		List<CharSequence> store = new ArrayList<CharSequence>();
		store.add(AnnotationAttributeEnum.STORE_ONLY.attribute());
		annotation_attributes.put("FL", store);
		
		
		List<CharSequence> pk_list = new ArrayList<CharSequence>();
		pk_list.add("FL");
		annotation_attributes.put(AnnotationAttributeEnum.PRIMARY_KEY.attribute(), pk_list);
		

		// Test type can be created using create_type_with_annotation()
		{
			try {
				Type type = gPUdb.create_type_with_annotations(definition, label,
						semanticType, annotation_attributes);
			} catch (GPUdbException ge) {
				assertTrue(ge.getMessage().contains("primary key may not include store_only"));
			}
		}
		
		{
			// Mark FL_SO as PK
			annotation_attributes.clear();
			pk_list = new ArrayList<CharSequence>();
			pk_list.add("FL");
			annotation_attributes.put(AnnotationAttributeEnum.PRIMARY_KEY.attribute(), pk_list);
			
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			GenericObject go = new GenericObject();
			go.addField("FL", "1");
			go.addField("FL2", "1");
			go.addField("FS", "AAA");
			ns.add(go);

			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "2");
			go.addField("FS", "BBB");
			ns.add(go);
			
			assertTrue(ns.list(0, 10).size() == 2);

			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "22");
			go.addField("FS", "CCC");
			ns.add(go);
			
			assertTrue(ns.list(0, 10).size() == 2);

			List gos = ns.list(0, 10);
			for( Object obj : gos ) {
				if( obj instanceof GenericObject ) {
					GenericObject go1 = (GenericObject)obj;
					long l1 = Long.parseLong(go1.getField("FL"));
					if( l1 == 1 ) {
						assertTrue(go1.getField("FS").equals("AAA"));
					} else {
						assertTrue(go1.getField("FS").equals("CCC"));
					}
				}
			}
			System.out.println(ns.list(0,10));
		}
		
		{
			// Mark FL and FL2 as PKs
			annotation_attributes.clear();
			pk_list = new ArrayList<CharSequence>();
			pk_list.add("FL");
			pk_list.add("FL2");
			
			annotation_attributes.put(AnnotationAttributeEnum.PRIMARY_KEY.attribute(), pk_list);
			
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			GenericObject go = new GenericObject();
			go.addField("FL", "1");
			go.addField("FL2", "1");
			go.addField("FS", "AAA");
			ns.add(go);

			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "2");
			go.addField("FS", "BBB");
			ns.add(go);
			
			assertTrue(ns.list(0, 10).size() == 2);

			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "2");
			go.addField("FS", "CCC");
			ns.add(go);
			
			assertTrue(ns.list(0, 10).size() == 2);

			List gos = ns.list(0, 10);
			for( Object obj : gos ) {
				if( obj instanceof GenericObject ) {
					GenericObject go1 = (GenericObject)obj;
					long l1 = Long.parseLong(go1.getField("FL"));
					if( l1 == 1 ) {
						assertTrue(go1.getField("FS").equals("AAA"));
					} else {
						assertTrue(go1.getField("FS").equals("CCC"));
					}
				}
			}
			System.out.println(ns.list(0,10));
		}
		
	}

	@Test
	public void testCreateTypeWithAnnotation() {

		// Clear previous data in gaia
		gPUdb.do_clear();
		
		String definition = "{\"type\":\"record\",\"name\":\"TestType\",\"fields\":" +
				"[{\"name\":\"OBJECT_ID\",\"type\":\"string\"}," +
				"{\"name\":\"FD\",\"type\":\"double\"}," +
				"{\"name\":\"FD_SO\",\"type\":\"double\"}, " +
				"{\"name\":\"FF\",\"type\":\"float\"}, " +
				"{\"name\":\"FF_SO\",\"type\":\"float\"}, " +
				"{\"name\":\"FS\",\"type\":\"string\"}, " +
				"{\"name\":\"FS_SO_SEARCH\",\"type\":\"string\"} " +
				"]}";

		String label = "type_label_annotation";
		SemanticTypeEnum semanticType = SemanticTypeEnum.EMPTY;

		Map<CharSequence, List<CharSequence>> annotation_attributes =
				new HashMap<CharSequence, List<CharSequence>>();
		List<CharSequence> store = new ArrayList<CharSequence>();

		store.add(AnnotationAttributeEnum.STORE_ONLY.attribute());
		annotation_attributes.put("FD_SO", store);
		annotation_attributes.put("FF_SO", store);

		List<CharSequence> search = new ArrayList<CharSequence>();
		search.add(AnnotationAttributeEnum.TEXT_SEARCH.attribute());
		annotation_attributes.put("FS_SO_SEARCH", search);

		// Test type can be created using create_type_with_annotation()
		{
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			assertNotNull(type.getID());
		}

		// Test type creation throws an exception for field not in the definition
		{
			// This should cause an error as no field XYZ in definition
			annotation_attributes.put("XYZ", store);

			Type type = null;
			try {
				type = gPUdb.create_type_with_annotations(definition, label,
						semanticType, annotation_attributes);
			} catch (GPUdbException e) {
				assertTrue(e.getMessage().contains("XYZ"));
			}
			assertNull(type);
		}

		// Test type creation throws an exception for an invalid annotation attribute
		{
			// Clear previous incorrect attributes from annotation map
			annotation_attributes.remove("XYZ");
			annotation_attributes.remove("FS_SO_SEARCH");

			// Add a dummy search attribute for this test
			search = new ArrayList<CharSequence>();
			search.add("dummy");
			annotation_attributes.put("FS_SO_SEARCH", search);

			Type type = null;
			try {
				type = gPUdb.create_type_with_annotations(definition, label, 
						semanticType, annotation_attributes);
			} catch (GPUdbException e) {
				assertTrue(e.getMessage().contains("dummy"));
			}
			assertNull(type);
		}

		// Test bounding box returns expected counts for a valid in-memory field
		{
			// Clear previous search attribute, add a valid search attribute
			annotation_attributes.remove("FS_SO_SEARCH");
			search = new ArrayList<CharSequence>();
			search.add(AnnotationAttributeEnum.TEXT_SEARCH.attribute());
			annotation_attributes.put("FS_SO_SEARCH", search);

			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			for (int i = 0; i<10; i++) {
				GenericObject go = new GenericObject();
				go.addField("OBJECT_ID", "OBJID");
				Double fd = 10.0 + i;
				go.addField("FD", fd.toString());
				go.addField("FD_SO", fd.toString());
				Float ff = 20.0f + i;
				go.addField("FF", ff.toString());
				go.addField("FF_SO", ff.toString());
				go.addField("FS", "10.0");
				go.addField("FS_SO_SEARCH", "fs_so_search" + i);
				ns.add(go);
			}

			SetId result_set_id = gPUdb.new_setid();
			bounding_box_response response = gPUdb.do_bounding_box(ns, result_set_id, "FD", "FF", 10.0, 20.0, 12.0, 30.0);
			assertTrue(response.getCount() == 8);
		}

		// Test bounding box throws an exception for an invalid (non-memory) field
		{
			// Create the type with annotations and named set for the type
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			// Create data fields for custom data adding to named set
			for (int i = 0; i<10; i++) {
				GenericObject go = new GenericObject();
				go.addField("OBJECT_ID", "OBJID");
				Double fd = 10.0 + i;
				go.addField("FD", fd.toString());
				go.addField("FD_SO", fd.toString());
				Float ff = 20.0f + i;
				go.addField("FF", ff.toString());
				go.addField("FF_SO", ff.toString());
				go.addField("FS", "10.0");
				go.addField("FS_SO_SEARCH", "fs_so_search" + i);
				ns.add(go);
			}

			// Trying to create a bounding box for disk fields (store only fields) should throw an exception
			bounding_box_response response = null;
			try {
				SetId result_set_id = gPUdb.new_setid();
				response = gPUdb.do_bounding_box(ns, result_set_id, "FD_SO", "FD_SO", 
						10.0, 20.0, 12.0, 22.0);
			} catch (GPUdbException e) {
				assertTrue(e.getMessage().contains("FD_SO"));
			}
			assertNull(response);
		}

		// Test search by string returns the correct count when the search field is correct
		{
			// Clear previous search attribute, add the data attribute to allow search
			annotation_attributes.remove("FS_SO_SEARCH");
			search = new ArrayList<CharSequence>();
			search.add("data");
			annotation_attributes.put("FS_SO_SEARCH", search);
						
			// Create the type with annotations and named set for the type
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			// Create data fields for custom data adding to named set
			for (int i = 0; i<10; i++) {
				GenericObject go = new GenericObject();
				go.addField("OBJECT_ID", "OBJID");
				Double fd = 10.0 + i;
				go.addField("FD", fd.toString());
				go.addField("FD_SO", fd.toString());
				Float ff = 20.0f + i;
				go.addField("FF", ff.toString());
				go.addField("FF_SO", ff.toString());
				go.addField("FS", "10.0");
				go.addField("FS_SO_SEARCH", "fs_so_search" + i);
				ns.add(go);
			}

			// Set a valid search field
			List<CharSequence> options = new ArrayList<CharSequence>();
			List<CharSequence> attributes = new ArrayList<CharSequence>();
			attributes.add("FS_SO_SEARCH");

			// Search on the word "fs_so_search" in the FS_SO_SEARCH field
			SetId result_set_id = gPUdb.new_setid();
			filter_by_string_response response = gPUdb.do_filter_by_string(ns,
					result_set_id, "fs_so_search", "contains", options, attributes);
			assertTrue(response.getCount() == 10);
		}

		// Test search by string returns 0 counts for an invalid (non-search) search field
		{
			// Create the type with annotations and named set for the type
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			// Create data fields for custom data adding to named set
			for (int i = 0; i<10; i++) {
				GenericObject go = new GenericObject();
				go.addField("OBJECT_ID", "OBJID");
				Double fd = 10.0 + i;
				go.addField("FD", fd.toString());
				go.addField("FD_SO", fd.toString());
				Float ff = 20.0f + i;
				go.addField("FF", ff.toString());
				go.addField("FF_SO", ff.toString());
				go.addField("FS", "10.0");
				go.addField("FS_SO_SEARCH", "fs_so_search" + i);
				ns.add(go);
			}

			// Set an invalid search field
			List<CharSequence> options = new ArrayList<CharSequence>();
			List<CharSequence> attributes = new ArrayList<CharSequence>();
			attributes.add("FS");

			// Search on the word "fs_so_search" in the FS field
			SetId result_set_id = gPUdb.new_setid();
			filter_by_string_response response = gPUdb.do_filter_by_string(ns,
					result_set_id, "fs_so_search", "contains", options, attributes);
			assertTrue(response.getCount() == 0);
		}
		
		// Test cases for create type with annotation using class instead of definition
		{
			// Test type creation with no annotation on fields
			annotation_attributes.clear();
			Type type = gPUdb.create_type_with_annotations(BigPoint.class, label, 
					semanticType, annotation_attributes);
			assertNotNull(type);
			
			// Test type creation with annotations on fields
			annotation_attributes.clear();
			store = new ArrayList<CharSequence>();
			store.add(AnnotationAttributeEnum.STORE_ONLY.attribute());
			annotation_attributes.put("group_id", store);

			search = new ArrayList<CharSequence>();
			search.add(AnnotationAttributeEnum.TEXT_SEARCH.attribute());
			annotation_attributes.put("msg_id", search);
			type = gPUdb.create_type_with_annotations(BigPoint.class, label, 
					semanticType, annotation_attributes);
			assertNotNull(type);
			
			// Test type creation with annotation on bogus field in BigPoint class
			annotation_attributes.clear();
			annotation_attributes.put("XYZ", store);
			type = null;
			try {
				type = gPUdb.create_type_with_annotations(BigPoint.class, label, 
						semanticType, annotation_attributes);
			} catch (GPUdbException e) {
				assertTrue(e.getMessage().contains("XYZ"));
			}
			assertNull(type);
			
			// Test type creation with annotation on bogus attribute on BigPoint field
			annotation_attributes.clear();
			search = new ArrayList<CharSequence>();
			search.add("dummy");
			annotation_attributes.put("msg_id", search);
			type = null;
			try {
				type = gPUdb.create_type_with_annotations(BigPoint.class, label, 
						semanticType, annotation_attributes);
			} catch (GPUdbException e) {
				assertTrue(e.getMessage().contains("dummy"));
			}
			assertNull(type);
		}
	}

	@Test
	public void testBadGpudbIP() {
		exception.expect(GPUdbException.class);
		exception.expectMessage("connection");

		// gPUdb throws exception when object not created
		GPUdb bad_gpudb = new GPUdb("GARBAGE", 9191, "xx", "yy");
		Type type = bad_gpudb.create_type(BigPoint.class);
	}

	@Test
	public void testBadTypeCreation() {
		exception.expect(GPUdbException.class);
		exception.expectMessage("Unsuported java data type");

		// gPUdb throws exception when type cannot be created
		Type type = gPUdb.create_type(BadObject.class);
	}

	@Test
	public void testTypeByDefinitionCreation() {
		// test of using the string definition; create a type; then a new set; add objects
		Type type = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		assertNotNull(type.getID());
	}

	@Test
	public void testGetTypeInfo() {
		// register two types
		String label1 = "type_label1";
		SemanticTypeEnum semanticType1 = SemanticTypeEnum.GENERICOBJECT;
		Type type1 = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}",
					"", label1, semanticType1);

		String label2 = "type_label2";
		SemanticTypeEnum semanticType2 = SemanticTypeEnum.GENERICOBJECT;
		Type type2 = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "",
				label2, semanticType2);

		// now get out the type info
		get_type_info_response response = gPUdb.do_get_type_info("", label1, "");
		List<CharSequence> semanticTypes1 = response.getSemanticTypes();
		assertTrue(semanticTypes1.size() == 1);
		assertTrue(semanticTypes1.get(0).toString().equals(semanticType1.name()));
	}

	/// NOTE: this isn't really a normal junit test; if the TEST hasn't been created then it should throw but if it has been
	// (for instance through python) then it should return the proper set
	@Test
	public void testGetNamedSet() {
		exception.expect(GPUdbException.class);
		exception.expectMessage("set doesn't exist");

		// gpudb throws exception when requested set does not exist
		gPUdb.getNamedSet(new SetId("TEST"));
	}

	// NOTE: you need two servers up for this! fails otherwise
	@Test
	public void testTwoGpudbs() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		SetId si = gPUdb.new_setid();
		NamedSet ns = gPUdb.newNamedSet(si, type);
		stats_response response = gPUdb.do_stats();
		System.out.println(response.toString());

		// do the actual 2 server test
		try {
			// build a new connection to separate gpudb instances
			GPUdb gpudb2 = new GPUdb("127.0.0.1", 9192, "xx", "yy");
			gpudb2.do_clear();
			response = gpudb2.do_stats();
			System.out.println(response.toString());

			type = gpudb2.create_type(BigPoint.class);
			ns = gpudb2.newNamedSet(si, type);
			ns.do_filter_by_bounds("x", 0, 10);

			boolean check = false;
			try {
				ns = gpudb2.getNamedSet(si);
			} catch (GPUdbException e) {
				// set doesn't exist error
				System.out.println(e.toString());
				check = true;
			}
			assertTrue(check);
		} catch (Exception e) {
			System.out.println("Did not run server 2 test");
		}
	}

	@Test
	public void testCreationMethods() {
		Type type = gPUdb.create_type(BigPoint.class);
		SetId si = gPUdb.new_setid();
		gPUdb.newSingleNamedSet(si, type);

		assertTrue(gPUdb.setExists(si));

		assertFalse(gPUdb.setExists(gPUdb.new_setid()));

		boolean check = false;
		try {
			// can't recreate the same set
			gPUdb.newSingleNamedSet(si, type);
		} catch(GPUdbException e) {
			check = true;
		}
		assertTrue(check);

		NamedSet parent = gPUdb.newParentNamedSet();
		check = false;
		try {
			// can't recreate the same set
			gPUdb.newParentNamedSet(parent.get_setid());
		} catch(GPUdbException e) {
			check = true;
		}
		assertTrue(check);

		// add a child
		gPUdb.newChildNamedSet(parent, type);
		check = false;
		try {
			// can't recreate a child with the same type
			gPUdb.newChildNamedSet(parent, type);
		} catch(GPUdbException e) {
			check = true;
		}
		assertTrue(check);

	}

	@Test
	public void testGetOrphans() {
		gPUdb.do_clear();

		SetId parent = gPUdb.new_setid();
		System.out.println("parent:"+parent.get_id());

		Type bgType = gPUdb.create_type(BigPoint.class);
		Type kvType = gPUdb.create_type(KeyValueType.class);

		// A parent has children
		NamedSet kvChild = gPUdb.newNamedSet(parent, kvType);
		System.out.println("built first child");
		NamedSet bgChild = gPUdb.newNamedSet(parent, bgType);
		System.out.println("built second child");

		// An orphan has no parent
		NamedSet orphan_nonparent = gPUdb.newSingleNamedSet(bgType);

		// Get the orphans. Orphan has no parent
		get_orphans_response response = gPUdb.do_get_orphans();

		// Get the distinct setIds and whether each a parent
		List<CharSequence> set_ids = response.getSetIds();
		List<Boolean> parents = response.getIsParent();

		// Two setIds generated above, one parent other orphan
		assertTrue(set_ids.size() == 2);
		assertTrue(parents.size() == 2);

		// One setId should be the parent and other the orphan
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
		server_status_response ssr = gPUdb.do_server_status();
		System.out.println(ssr.getStatusMap());
		assertNotNull(ssr.getStatusMap());
	}

	@Test
	public void testFetchShapes() {
		testCreateShapes();

		// Get the CONTAINER named set created in testCreateShapes
		@SuppressWarnings("deprecation")
		NamedSet containerSet = gPUdb.getNamedSet(new SetId("CONTAINER"), Type.buildParentType());

		// Get up to 100 polygons
		List<Polygon> polygons = containerSet.getPolygons(0, 100);

		Map<String, String> expected = new HashMap<String, String>();
		expected.put("Alice", "A21");
		expected.put("Bob", "B20");
		for( Polygon poly : polygons ) {
			String key = poly.features.get("Attribute1");
			String val = poly.features.get("OBJECT_ID");
			assertEquals(expected.get(key), val);
		}

		// Get up to 100 tracks inside the specified binding box left bottom right top
		List<Track> tracks = containerSet.getTracks(containerSet, 0, 100, -180, -90, 180, 90);
		String expect = "T1_0 T1_1 T1_2 T2_10 T2_11 T2_12";
		for( Track track : tracks ) {
			List<Track.TrackPoint> points = track.getTrackPoints();
			for( Track.TrackPoint tp : points ) {
				String objectId = tp.getFeatures().get("OBJECT_ID");
				assertTrue(expect.contains(objectId));
			}
		}
	}
	
	@Test
	public void testFetchShapesUsingTrack2() {
		testCreateShapes();

		// Get the CONTAINER named set created in testCreateShapes
		@SuppressWarnings("deprecation")
		NamedSet containerSet = gPUdb.getNamedSet(new SetId("CONTAINER"), Type.buildParentType());

		// Get up to 100 polygons
		List<Polygon> polygons = containerSet.getPolygons(0, 100);

		Map<String, String> expected = new HashMap<String, String>();
		expected.put("Alice", "A21");
		expected.put("Bob", "B20");
		for( Polygon poly : polygons ) {
			String key = poly.features.get("Attribute1");
			String val = poly.features.get("OBJECT_ID");
			assertEquals(expected.get(key), val);
		}

		// Get up to 100 tracks inside the specified binding box left bottom right top
		List<Track> tracks = containerSet.getTracks(containerSet, 0, 100, -180, -90, 180, 90);
		String expect = "T1_0 T1_1 T1_2 T2_10 T2_11 T2_12";
		for( Track track : tracks ) {
			List<Track.TrackPoint> points = track.getTrackPoints();
			for( Track.TrackPoint tp : points ) {
				String objectId = tp.getFeatures().get("OBJECT_ID");
				assertTrue(expect.contains(objectId));
			}
		}
	}

	@Test
	public void testCreateShapes() {
		gPUdb.do_clear();
		NamedSet parent = gPUdb.newParentNamedSet(new SetId("CONTAINER"));

		// Create some Polygons; Each child polygon has 4 fields
		String label = "type_label_Polygon";
		SemanticTypeEnum semanticType = SemanticTypeEnum.POLYGON2D;
		Type polyType = gPUdb.create_type("{\"type\":\"record\",\"name\":\"Poly2D\",\"fields\":" +
				"[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"WKT\",\"type\":\"string\"}," +
				"{\"name\":\"Attribute1\",\"type\":\"string\"},{\"name\":\"Attribute2\",\"type\":\"int\"}]}", "", label, semanticType);

		NamedSet child = gPUdb.newChildNamedSet(parent, polyType);
		assertEquals(polyType, child.getType());

		// Add 2 polygons to the polygon named set
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

		// Create some Tracks; Each child has 6 fields
		String label2 = "type_label_Track";
		SemanticTypeEnum semanticType2 = SemanticTypeEnum.TRACK;
		Type trackType = gPUdb.create_type("{\"type\":\"record\",\"name\":\"Track\",\"fields\":" +
				"[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"x\",\"type\":\"double\"},{\"name\":\"y\",\"type\":\"double\"}," +
				"{\"name\":\"TIMESTAMP\",\"type\":\"double\"}," +
				"{\"name\":\"TRACKID\",\"type\":\"string\"},{\"name\":\"Attribute2\",\"type\":\"int\"}]}", "", label2, semanticType2);

		NamedSet child2 = gPUdb.newChildNamedSet(parent, trackType);
		assertEquals(trackType, child2.getType());

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
		gPUdb.do_clear();

		// Register two types
		String label1 = "type_label1"+UUID.randomUUID().toString();;
		SemanticTypeEnum semanticType1 = SemanticTypeEnum.GENERICOBJECT;
		Type type1 = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label1, semanticType1);

		String label2 = "type_label2"+UUID.randomUUID().toString();;
		SemanticTypeEnum semanticType2 = SemanticTypeEnum.GENERICOBJECT;
		Type type2 = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label2, semanticType2);

		// Different semantic type but same label as type2
		SemanticTypeEnum semanticType3 = SemanticTypeEnum.POLYGON2D;
		Type type3 = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"WKT\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label2, semanticType3);


		NamedSet ns1 = gPUdb.newNamedSet(type1);
		NamedSet ns2 = gPUdb.newNamedSet(type1);
		NamedSet ns3 = gPUdb.newNamedSet(type2);

		// Now get response by label or type
		get_sets_by_type_info_response response = gPUdb.do_get_sets_by_type_info(label1, "");
		List<CharSequence> set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 2);

		response = gPUdb.do_get_sets_by_type_info(label2, "");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 1);

		response = gPUdb.do_get_sets_by_type_info("", "GENERICOBJECT");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 3);

		response = gPUdb.do_get_sets_by_type_info("", "");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 3);

		response = gPUdb.do_get_sets_by_type_info("", "BLAH");
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 0);

		response = gPUdb.do_get_sets_by_type_id(type2.getID());
		set_ids = response.getSetIds();
		System.out.println("num set_ids:"+set_ids.size());
		assertTrue(set_ids.size() == 1);
		System.out.println(ns3.get_setid().get_id());
		assertTrue(set_ids.get(0).toString().equals(ns3.get_setid().get_id()));

		// try the named set functions; especially on parents
		NamedSet parent = gPUdb.newParentNamedSet();
		NamedSet child1 = gPUdb.newChildNamedSet(parent, type1);
		NamedSet child2 = gPUdb.newChildNamedSet(parent, type2);
		NamedSet child3 = gPUdb.newChildNamedSet(parent, type3);

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
	public void testListAllTypes() {
		gPUdb.do_clear();

		// register types
		String label1 = "type_label1"+UUID.randomUUID().toString();;
		SemanticTypeEnum semanticType = SemanticTypeEnum.GENERICOBJECT;
		Type type1 = gPUdb.create_type(BigPoint.class, "", label1, semanticType);

		// different label
		String label2 = "type_label2"+UUID.randomUUID().toString();;
		Type type2 = gPUdb.create_type(BigPoint.class, "", label2, semanticType);

		// same label but different class (schema)
		Type type3 = gPUdb.create_type(SimplePoint.class, "", label2, semanticType);

		// try the named set functions; especially on parents
		NamedSet parent = gPUdb.newParentNamedSet();
		NamedSet child1 = gPUdb.newChildNamedSet(parent, type1);
		NamedSet child2 = gPUdb.newChildNamedSet(parent, type2);
		NamedSet child3 = gPUdb.newChildNamedSet(parent, type3);

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
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BytesPoint.class);
		assertTrue(type.getTypeClass() == BytesPoint.class);
	}

	@Test
	public void testBytesAddObject() {
		//gPUdb.do_clear();
		Type type = gPUdb.create_type(BytesPoint.class);
		NamedSet ns = gPUdb.newSingleNamedSet(type);

		BytesPoint point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",1.01, 2.01,1,"SRC1", "GROUP1", ByteBuffer.wrap("Binary Test Data1".getBytes()));
		add_object_response response = ns.add(point);
		assertNotNull(response.getCountInserted() == 1);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",1.01, 3.01,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary Test Data2".getBytes()));
		response = ns.add(point);
		assertNotNull(response.getCountInserted() == 1);
	}

	@Test
	public void testBytesListObject() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BytesPoint.class);
		System.out.println("Type id:"+type.getID());
		NamedSet ns = gPUdb.newSingleNamedSet(type);

		ArrayList<BytesPoint> local_points = new ArrayList<BytesPoint>();

		BytesPoint point = new BytesPoint(UUID.randomUUID().toString(), "MSGID1",1.01, 2.01,1,"SRC1", "GROUP1",ByteBuffer.wrap("b1".getBytes()));
		ns.add(point);
		local_points.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID2",1.01, 3.01,1,"SRC2", "GROUP2", ByteBuffer.wrap("Binary".getBytes()));
		ns.add(point);
		local_points.add(point);

		point = new BytesPoint(UUID.randomUUID().toString(), "MSGID3",1.01, 4.01,1,"SRC3", "GROUP3", ByteBuffer.wrap("Binary Test Data3".getBytes()));
		ns.add(point);
		local_points.add(point);

		// List
		ArrayList<BytesPoint> points = (ArrayList<BytesPoint>)ns.list(0,5);
		assertTrue(points.size() == 3);

		// true test; make sure the points return equal those in the local list
		for(BytesPoint p : local_points) {
			assertEquals(points.contains(p), true);
			points.remove(p);
		}
		assertTrue(points.size() == 0);
	}

	@Test
	public void testNamedSetCreation() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);
		clear_response response = gPUdb.do_clear_set(ns);
		assertEquals(response.getStatus().toString().contains("CLEARED"), true);
	}

	@Test
	public void testParentNamedSetCreation() {
		gPUdb.do_clear();
		Type bgType = gPUdb.create_type(BigPoint.class);
		Type kvType = gPUdb.create_type(KeyValueType.class);

		SetId parent = gPUdb.new_setid();

		NamedSet kvChild = gPUdb.newNamedSet(parent, kvType);
		System.out.println("built first child");
		NamedSet bgChild = gPUdb.newNamedSet(parent, bgType);
		System.out.println("built second child");
		get_orphans_response response = gPUdb.do_get_orphans();
		assertEquals(response.getIsParent().get(0), true);
	}

	@Test
	public void testAddObject() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);

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
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		add_object_response response = A.add(p);
		System.out.println(" Object id is " + response.getOBJECTID());
		assertTrue(response.getOBJECTID().length() > 0);

		assertTrue(A.size() == 1);

		gPUdb.do_delete_object(A, response.getOBJECTID().toString());

		assertTrue(A.size() == 0);

	}
	
	
	
	@Test
	public void testSelectDeleteObjectTwitter() {	
				
		NamedSet twitter = gPUdb.getNamedSet(new SetId("Twitter"));
		
		long count = gPUdb.do_select_delete(twitter, "TIMESTAMP < 1393822800000");
		
		System.out.println(" Select Delete Done...." + count);
		
	}

	@Test
	public void testSelectDeleteObject() {	
				
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);
		
		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 10.0, 20.0, 0, "SRC1", "GROUP1");
		add_object_response response = A.add(p);
		System.out.println(" Object id is " + response.getOBJECTID());
		assertTrue(response.getOBJECTID().length() > 0);
		assertTrue(A.size() == 1);
		
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 11.0, 21.0, 0, "SRC1", "GROUP1");
		response = A.add(p);
		System.out.println(" Object id is " + response.getOBJECTID());
		assertTrue(response.getOBJECTID().length() > 0);
		assertTrue(A.size() == 2);
		
		long count = gPUdb.do_select_delete(A, "x == 10.0");
		assertTrue(count == 1);
		
		assertTrue(A.size() == 1);
	}
	
	@Test
	public void testUpdateObject() {

		gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);
		
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

		// Test GpudbUCD will do more attribute level checking of updates.
		assertTrue(ur.getStatus().toString().equals("OK"));

	}

	@Test
	public void testSelectUpdateObject() {	
		
		gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);
		
		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		add_object_response response = A.add(p);
		System.out.println(" Object id is " + response.getOBJECTID());
		assertTrue(response.getOBJECTID().length() > 0);
		
		assertTrue(A.size() == 1);
		
		Map<CharSequence,CharSequence> data = new HashMap<CharSequence,CharSequence>();
		data.put("x", "22.0");
		data.put("y", "23.0");
		data.put("OBJECT_ID", "13AC");
		data.put("msg_id", "13AC");
		data.put("source", "13AC");
		data.put("group_id", "13AC");

		long count = gPUdb.do_select_update(A, data, "source == 'SRC1'");
		
		// Test GaiaUCD will do more attribute level checking of updates.
		assertTrue(count == 1);
		assertTrue(A.size() == 1);
		
	}

	@Test
	public void testUpsertSemantics() {	
		
		gPUdb.do_clear();
		
		String definition = "{\"type\":\"record\",\"name\":\"TestType\",\"fields\":" +
				"[{\"name\":\"FL\",\"type\":\"long\"}," +
				"{\"name\":\"FL2\",\"type\":\"long\"}, " +
				"{\"name\":\"FS\",\"type\":\"string\"} " +
				"]}";
		
		String label = "label1";
		SemanticTypeEnum semanticType = SemanticTypeEnum.EMPTY;

		Map<CharSequence, List<CharSequence>> annotation_attributes =
				new HashMap<CharSequence, List<CharSequence>>();

		
		List<CharSequence> store = new ArrayList<CharSequence>();
		store.add(AnnotationAttributeEnum.STORE_ONLY.attribute());
		annotation_attributes.put("FL", store);
		
		
		List<CharSequence> pk_list = new ArrayList<CharSequence>();
		pk_list.add("FL");
		annotation_attributes.put(AnnotationAttributeEnum.PRIMARY_KEY.attribute(), pk_list);
		
		{
			// Mark FL_SO as PK
			annotation_attributes.clear();
			pk_list = new ArrayList<CharSequence>();
			pk_list.add("FL");
			annotation_attributes.put(AnnotationAttributeEnum.PRIMARY_KEY.attribute(), pk_list);
			
			Type type = gPUdb.create_type_with_annotations(definition, label,
					semanticType, annotation_attributes);
			NamedSet ns = gPUdb.newSingleNamedSet(type);

			GenericObject go = new GenericObject();
			go.addField("FL", "1");
			go.addField("FL2", "1");
			go.addField("FS", "AAA");
			add_object_response aor = ns.add(go);
			assertTrue(aor.getCountInserted() == 1);

			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "2");
			go.addField("FS", "BBB");
			aor = ns.add(go);
			assertTrue(aor.getCountInserted() == 1);
			
			assertTrue(ns.list(0, 10).size() == 2);

			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "22");
			go.addField("FS", "CCC");
			aor = ns.add(go, GPUdbApiUtil.getSilentonexistingpk());
			assertTrue(aor.getCountInserted() == 0);
			
			go = new GenericObject();
			go.addField("FL", "2");
			go.addField("FL2", "22");
			go.addField("FS", "CCC");
			aor = ns.add(go, GPUdbApiUtil.getUpdateonexistingpk());
			assertTrue(aor.getCountInserted() == 0);
			assertTrue(aor.getCountUpdated() == 1);
			
			assertTrue(ns.list(0, 10).size() == 2);
		}
	}
	
	@Test
	public void testBulkUpdate() {	
		
		gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);
		
		// Create some points and add them
		BigPoint p1 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.0, 2.0, 1, "SRC1", "GROUP1");
		BigPoint p2 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.1, 2.1, 2, "SRC1", "GROUP1");
		BigPoint p3 = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 1.2, 2.2, 3, "SRC1", "GROUP1");
		BigPoint p4 = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 1.3, 2.3, 4, "SRC1", "GROUP1");
		BigPoint p5 = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 1.4, 2.4, 5, "SRC1", "GROUP1");
		BigPoint p11 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.0, 2.0, 1, "SRC2", "GROUP1");
		BigPoint p21 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.1, 2.1, 2, "SRC2", "GROUP1");
		BigPoint p31 = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 1.2, 2.2, 3, "SRC2", "GROUP1");
		BigPoint p41 = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 1.3, 2.3, 4, "SRC2", "GROUP1");
		BigPoint p51 = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 1.4, 2.4, 5, "SRC2", "GROUP1");

		List<Object> list = new ArrayList<Object>();
		list.add(p1);list.add(p2);list.add(p3);list.add(p4);list.add(p5);
		list.add(p11);list.add(p21);list.add(p31);list.add(p41);list.add(p51);
		
		bulk_add_response response = A.add_list(list);
		assertTrue(response.getOBJECTIDs().size() == 10);

		String global_expression = "(source == 'SRC1')";
		
		List<CharSequence> expressions = new ArrayList<CharSequence>();
		List<Map<java.lang.CharSequence, java.lang.CharSequence>> updateMaps = 
				new ArrayList<Map<java.lang.CharSequence, java.lang.CharSequence>>();

		List<Object> insertMaps = new ArrayList<Object>();
		
		
		expressions.add("x == 1.1");
		Map<CharSequence,CharSequence> data1 = new HashMap<CharSequence,CharSequence>();
		data1.put("x", "11.1");
		updateMaps.add(data1);
		insertMaps.add(new NullObject() {});
		
		expressions.add("x == 1.2");
		Map<CharSequence,CharSequence> data2 = new HashMap<CharSequence,CharSequence>();
		data2.put("x", "21.1");
		updateMaps.add(data2);
		insertMaps.add(new NullObject() {});
		
		expressions.add("x == 3.2"); // will not match anything
		Map<CharSequence,CharSequence> data3 = new HashMap<CharSequence,CharSequence>();
		data3.put("x", "4.1");
		updateMaps.add(data3);
		BigPoint newPoint = new BigPoint(UUID.randomUUID().toString(),  "MSGIDNEW", 111.4, 2222.4, 5, "SRCNEW", "GROUPNEW");
		insertMaps.add(newPoint);

		
		bulk_update_response bur = gPUdb.do_bulk_updates(A, global_expression, expressions, updateMaps, insertMaps, new HashMap());
		
		
		System.out.println("xxxx " + A.list(0, 100));
		
	}
	
	@Test
	public void testBulkDelete() {	
		
		gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);
		
		// Create some points and add them
		BigPoint p1 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.0, 2.0, 1, "SRC1", "GROUP1");
		BigPoint p2 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.1, 2.1, 2, "SRC1", "GROUP1");
		BigPoint p3 = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 1.2, 2.2, 3, "SRC1", "GROUP1");
		BigPoint p4 = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 1.3, 2.3, 4, "SRC1", "GROUP1");
		BigPoint p5 = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 1.4, 2.4, 5, "SRC1", "GROUP1");
		
		BigPoint p11 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.0, 2.0, 1, "SRC2", "GROUP1");
		BigPoint p21 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.1, 2.1, 2, "SRC2", "GROUP1");
		BigPoint p31 = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 1.2, 2.2, 3, "SRC2", "GROUP1");
		BigPoint p41 = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 1.3, 2.3, 4, "SRC2", "GROUP1");
		BigPoint p51 = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 1.4, 2.4, 5, "SRC2", "GROUP1");

		List<Object> list = new ArrayList<Object>();
		list.add(p1);list.add(p2);list.add(p3);list.add(p4);list.add(p5);
		list.add(p11);list.add(p21);list.add(p31);list.add(p41);list.add(p51);
		
		bulk_add_response response = A.add_list(list);
		assertTrue(response.getOBJECTIDs().size() == 10);

		String global_expression = "(source == 'SRC1')";
		
		List<CharSequence> expressions = new ArrayList<CharSequence>();
		expressions.add("x > 1.2");
		expressions.add("x > 1.0");
		
		bulk_delete_response bdr = gPUdb.do_bulk_deletes(A, global_expression, expressions, new HashMap());
		assertTrue(A.list(0, 100).size() == 6);
	}
	
	@Test
	public void testBulkSelect() {	
		
		gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet A = gPUdb.newNamedSet(type);
		
		// Create some points and add them
		BigPoint p1 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.0, 2.0, 1, "SRC1", "GROUP1");
		BigPoint p2 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.1, 2.1, 2, "SRC1", "GROUP1");
		BigPoint p3 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.2, 2.2, 3, "SRC1", "GROUP1");
		BigPoint p4 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.3, 2.3, 4, "SRC1", "GROUP1");
		BigPoint p5 = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 1.4, 2.4, 5, "SRC1", "GROUP1");
		BigPoint p11 = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.0, 2.0, 1, "SRC2", "GROUP1");
		BigPoint p21 = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 1.1, 2.1, 2, "SRC2", "GROUP1");
		BigPoint p31 = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 1.2, 2.2, 3, "SRC2", "GROUP1");
		BigPoint p41 = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 1.3, 2.3, 4, "SRC2", "GROUP1");
		BigPoint p51 = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 1.4, 2.4, 5, "SRC2", "GROUP1");

		List<Object> list = new ArrayList<Object>();
		list.add(p1);list.add(p2);list.add(p3);list.add(p4);list.add(p5);
		list.add(p11);list.add(p21);list.add(p31);list.add(p41);list.add(p51);
		
		bulk_add_response response = A.add_list(list);
		assertTrue(response.getOBJECTIDs().size() == 10);

		String global_expression = "(source == 'SRC1')";
		
		List<CharSequence> expressions = new ArrayList<CharSequence>();
		
		expressions.add("x == 1.1");
		expressions.add("x == 1.2");
		expressions.add("x == + @# (()(( 3.2"); // will not match anything
		
		bulk_select_response bsr = gPUdb.do_bulk_selects(A, global_expression, expressions, new HashMap());
		System.out.println("xxxx " + bsr.getCountFound());
		System.out.println("xxxx " + bsr.getCountsFound());
		
		List<List> objects = A.bulkSelectObjects(global_expression, expressions, new HashMap());
		
		for( List loj : objects ) {
			System.out.println(" Objects for predicates are : " + loj);
		}
		
		
	}

	@Test
	public void testBadAddObject() {
		exception.expect(GPUdbException.class);
		exception.expectMessage("Not a valid schema");

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		ns.add(new KeyValueType(UUID.randomUUID().toString(), "KEY", "VALUE"));
	}

	@Test
	public void testClear() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);

		clear_response response = gPUdb.do_clear();
		assertEquals(response.toString().contains("ALL CLEARED"), true);
	}

	@Test
	public void testFilterThenHistogram() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newSingleNamedSet(type);

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

		max_min_response response = gPUdb.do_max_min(ns, "timestamp");
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
		filter_then_histogram_response fthr =  gPUdb.do_filter_then_histogram(ns,"group_id",filter,"timestamp", 1,minVal,maxVal);

		List<List<Integer>> counts = fthr.getCounts();
		for( List<Integer> ci : counts ) {
			assertTrue(ci.size() == 9);
		}
		System.out.println("XXX " + fthr.getCounts());
	}

	@Test
	public void testBoundingBoxForSet() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newSingleNamedSet(type);

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
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newSingleNamedSet(type);

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

		// Get out the objects of GROUP1 and GROUP2
		List<CharSequence> values = new ArrayList<CharSequence>();
		values.addAll(Arrays.asList("GROUP1", "GROUP2"));

		// Give me the object having group_id == GROUP1 and GROUP2
		List<BigPoint> points  = ns.get_objects("group_id", values);

		// Returns one object per filter (group_id) value
		assertTrue(points.size() == 3);

		// Loop through the returned object
		List<CharSequence> actual = new ArrayList<CharSequence>();
		for (BigPoint bp : points) {
			assertTrue(local_points.contains(bp));
		}
	}

	@Test
	public void testListObject() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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
		for (BigPoint point : points) {
			assertTrue(local_points.contains(point));
		}

		// list
		points = (ArrayList<BigPoint>)ns.list(0,10);
		assertTrue(points.size() == 3);
		for (BigPoint point : points) {
			assertTrue(local_points.contains(point));
		}
	}

	@Test
	public void testIterator() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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

		// To force it to switch pages; i.e. do multiple request
		ns.setPageSize(2);

		// Iterate through the set
		Iterator<BigPoint> iter = ns.iterator();
		while(iter.hasNext()){
			p = iter.next();
			assertTrue(local_points.contains(p));

			local_points.remove(p);
			iter.remove();
		}

		assertTrue(local_points.size() == 0);
	}

	@Test
	public void testTypeByDefinitionCreationAndList() {
		gPUdb.do_clear();
		// test of using the string definition; create a type; then a new set; add objects
		Type type = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		NamedSet ns = gPUdb.newNamedSet(type);
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
		assertTrue(ns.size() == 4);
		for (Object o : ns.list(0,10)) {
			GenericObject gen = (GenericObject) o;
			String age = gen.getField("age");
			if (age.equals("21")) {
				assertEquals(gen.getField("person"), "Alice");
			} else if (age.equals("25")) {
				assertEquals(gen.getField("person"), "Bob");
			} else if (age.equals("35")) {
				assertEquals(gen.getField("person"), "Carl");
			} else if (age.equals("31")) {
				assertEquals(gen.getField("person"), "Denise");
			}
		}
	}

	@Test
	public void testNamedSetSize() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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
		gPUdb.do_clear();

		// Type registering. Add point semantic type
		String label1 = "type_label_st";
		SemanticTypeEnum semanticType1 = SemanticTypeEnum.EMPTY;
		Type type = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}", "", label1, semanticType1);

		// Test some semantic type stuff
		NamedSet ns = gPUdb.newSingleNamedSet(gPUdb.new_setid(), type);

		// Create some objects;
		GenericObject go = new GenericObject();
		go.addField("person", "Alice");
		go.addField("age", "21");
		ns.add(go);

		go = new GenericObject();
		go.addField("person", "Bob");
		go.addField("age", "25");
		ns.add(go);

		// Requested semantic type is POINT but added was GenericObject so exception
		try {
			ns.getPoints(0, NamedSet.END_OF_SET);
			// should throw
		} catch(GPUdbException e) {
			assertTrue(true);
			System.out.println("GE:"+e.toString());
		} catch(Exception e) {
			System.out.println("E:"+e.toString());
		}
	}

	@Test
	public void testBulkAdd() {
		//gPUdb.do_clear();

		gPUdb.setSnappyCompress(true);
		
		Type type = gPUdb.create_type(BigPoint.class);
		
		NamedSet ns = gPUdb.newNamedSet(type);
		
		ns.setBulkAddLimit(1000000);
		// Create some points and add them
		List<Object> list = new ArrayList<Object>();
		
		for( int ii = 0; ii < 10; ii++ ) {
			BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGIDTeams should be able to access OWF on WL1 now ."+ii, 
					1.01+ii, 2.01+ii, 0, "SRC"+ii, "GROUP1");
			list.add(p);
		}

		System.out.println(" 0000000000000 " + new Date());

		
		ns.add_list(list);

		// list
		//ArrayList<BigPoint> points = (ArrayList<BigPoint>) ns.list(0,2);
		//assertTrue(points.size() == 5000);
		//Assert.assertArrayEquals(list.toArray(), points.toArray());
	}

	@Test
	public void testBulkAddWithLimit() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);
		ns.setBulkAddLimit(2);

		// Create a list of points
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

		// Add the list (simulates bulk)
		ns.add_list(local_points);

		// Fetch the first element and verify this is a list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0,10);
		assertTrue(points.size() == local_points.size());
		for (BigPoint point : points) {
			assertTrue(local_points.contains(point));
		}
	}

	@Ignore
	public void testMakeBloom() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// add points
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);

		// run make bloom
		make_bloom_response response = gPUdb.do_make_bloom(ns, "x");
		System.out.println("make bloom status:"+response.getStatus());
		assertTrue(response.getStatus().toString().equals("CREATED"));
	}

	@Test
	public void testBoundingBox() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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
		SetId result_set_id = gPUdb.new_setid();
		bounding_box_response response = gPUdb.do_bounding_box(ns, result_set_id, "x", "y", 1, 10, 1, 10);
		assertTrue(response.getCount() == 3);

		// grab the RS; loop through the points
		NamedSet rs = gPUdb.getNamedSet(result_set_id, type);

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for (BigPoint point: points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Test
	public void testBoundingBoxWithParent() {
		// Make types
		Type bgType = gPUdb.create_type(BigPoint.class);
		Type spType = gPUdb.create_type(SimplePoint.class);

		// Get the parent named set and create child named sets for set id
		SetId parent = gPUdb.new_setid();
		NamedSet bgChild = gPUdb.newNamedSet(parent, bgType);
		NamedSet spChild = gPUdb.newNamedSet(parent, spType);
		NamedSet parentNS = gPUdb.getNamedSet(parent);
		assertTrue(parentNS != null);

		// Add points into the box area
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

		// Add simple point to sp named set
		SimplePoint sp = new SimplePoint(UUID.randomUUID().toString(), 2,2);
		spChild.add(sp);

		// Add points outside the box to bg named set
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		bgChild.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		bgChild.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		bgChild.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		bgChild.add(p);

		// Add simple point outside the box to sp named set
		sp = new SimplePoint(UUID.randomUUID().toString(), 15,2);
		spChild.add(sp);

		// Run bounding box
		SetId result_set_id = gPUdb.new_setid();
		bounding_box_response response = gPUdb.do_bounding_box(parentNS, result_set_id, "x", "y", 1, 10, 1, 10);
		assertTrue(response.getCount() == 4);

		// Grab the RS named set; loop through the points
		NamedSet parentRS = gPUdb.getNamedSet(result_set_id);

		// Get children from the server
		List<NamedSet> children = new ArrayList<NamedSet>();
		children.addAll(parentRS.getChildren());

		// Find the child that matches this type
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
		for (BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Test
	public void testMaxMin() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Add points
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

		// Run max min on x
		max_min_response response = gPUdb.do_max_min(ns, "x");
		assertTrue(response.getMax() == 35.01);
		assertTrue(response.getMin() == 1.01);
	}

	@Test
	public void testBulkAddThenCalc() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		List<Object> list = new ArrayList<Object>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1, 2.01, 0, "SRC1", "GROUP1");
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2, 2.01, 1, "SRC2", "GROUP2");
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3, 3.01, 2, "SRC3", "GROUP3");
		list.add(p);

		// Bulk add
		ns.add_list(list);

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0,2);
		assertTrue(points.size() == 3);
		for(BigPoint point : points) {
			assertTrue(list.contains(point));
		}

		max_min_response response = gPUdb.do_max_min(ns, "x");
		assertTrue(response.getMax() == 3);
		assertTrue(response.getMin() == 1);
	}
	
	@Test
	public void testHistogram() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Add points
		// Histogram split input named set data into bins of desired intervals with values that fall within each bin
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

		// Interval
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 20.01, 20.01, 13, "SRC2", "OUT_BOX");
		ns.add(p);

		// Run histogram (set id, attribute, interval) - splits into three bins of interval 5
		histogram_response response = gPUdb.do_histogram(ns, "timestamp", 5, new HashMap());
		assertTrue(response.getStart() == 1);
		assertTrue(response.getEnd() == 13);

		// Check counts - bin 1 contains values 1 and 5, bin 2 values 6, 9, 10, bin 3 contains 13
		List<Double> list = response.getCounts();
		System.out.println("list:"+list.toString());
		assertTrue(list.size() == 3);

		assertTrue(list.get(0).intValue() == 2 && list.get(1).intValue() == 3 && list.get(2).intValue() == 1);
	}

	@Test
	public void testFilterByBounds() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Add points in the bounds
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

		// Outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// Run bounding box on the x variable
		SetId result_set_id = gPUdb.new_setid();
		filter_by_bounds_response response = gPUdb.do_filter_by_bounds(ns, result_set_id, "x", 1, 5);
		assertTrue(response.getCount() == 3);

		// Give me the named set for the bounding box
		NamedSet rs = gPUdb.getNamedSet(result_set_id, type);

		// List
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Test
	public void testConvexHull() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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

		convex_hull_response chr = gPUdb.do_convex_hull(ns, "x", "y");
		assertTrue(chr.getCount() == 6);

	}

	@Test
	public void testGroupBy() {
		//gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		// Group 1
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);

		// Group 2
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);

		// Group 3
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 4.01, 2, "SRC4", "GROUP3");
		ns.add(p);

		// Run group by on group_id and source
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("group_id");
		attributes.add("source");

		group_by_response response = gPUdb.do_group_by(ns, attributes);
		Map<CharSequence, List<CharSequence>> count_map = response.getCountMap();

		// CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, List<String>> str_count_map = new HashMap<String, List<String>>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();

		// Iterate through the Utf8 map and building a new string based one
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
	public void testGroupByValue() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		// Group 1
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);

		// Group 2
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);		

		// Group 3
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 4.01, 2, "SRC4", "GROUP3");
		ns.add(p);

		// Run group by on group_id and source
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("group_id");
		attributes.add("source");
		
		// Give me the sum of x grouped by (group_id, source)
		String value_attribute = "timestamp";

		group_by_value_response response = gPUdb.do_group_by_value(ns, attributes, value_attribute);
		Map<CharSequence, Double> count_map = response.getCountMap();

		// CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		Map<String, Double> str_count_map = new HashMap<String, Double>();
		
		for (CharSequence key : count_map.keySet()) {
			str_count_map.put(key.toString(), count_map.get(key));
		}
				
		System.out.println("G1S1:"+str_count_map.get("GROUP1,SRC1")+" G3S4:"+str_count_map.get("GROUP3,SRC4")+ 
				" G3S3:"+str_count_map.get("GROUP3,SRC3") + " G2S2:"+str_count_map.get("GROUP2,SRC2"));
		assertTrue(str_count_map.get("GROUP1,SRC1") == 0.0 && 
				str_count_map.get("GROUP3,SRC4") == 2.0 && 
				str_count_map.get("GROUP3,SRC3") == 2.0 && 
				str_count_map.get("GROUP2,SRC2") == 1.0);
		
		// Test that when value_attribute is null then it behaves like simple group_by
		response = gPUdb.do_group_by_value(ns, attributes, "");
		count_map = response.getCountMap();

		// CharSequences in avro are really org.apache.avro.util.Utf8 let's convert into strings
		str_count_map = new HashMap<String, Double>();
		
		for (CharSequence key : count_map.keySet()) {
			str_count_map.put(key.toString(), count_map.get(key));
		}
		
		assertTrue(str_count_map.get("GROUP1,SRC1") == 3 && 
				str_count_map.get("GROUP3,SRC4") == 1 && 
				str_count_map.get("GROUP3,SRC3") == 1 && 
				str_count_map.get("GROUP2,SRC2") == 1);
	}

	@Test
	public void testFilterByNAI() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// add points in the bounds
		List<BigPoint> list = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID1", 1.01, 2.01, 0, "SRC1", "IN_BOX");
		ns.add(p);
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID2", 2.01, 2.01, 1, "SRC2", "IN_BOX");
		ns.add(p);
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_BOX");
		ns.add(p);
		list.add(p);

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

		SetId result_set_id = gPUdb.new_setid();
		filter_by_nai_response response = gPUdb.do_filter_by_nai(ns, result_set_id, "x", x_vector, "y", y_vector);
		assertTrue(response.getCount() == 3); // THIS IS THE REAL COUNT

		// grab the RS; loop through the points
		NamedSet rs = gPUdb.getNamedSet(result_set_id, type);

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(BigPoint point : points) {
			assertTrue(list.contains(point));
		}

		// let's perform a 2nd test too;
		ns = gPUdb.newNamedSet(type);

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
		result_set_id = gPUdb.new_setid();
		response = gPUdb.do_filter_by_nai(ns, result_set_id, "x", naiX, "y", naiY);
		assertTrue(response.getCount() == in_points.size());

		// grab the RS; loop through the points
		rs = gPUdb.getNamedSet(result_set_id, type);

		// list
		
		// TODO - reckless downcasting here
		points = (ArrayList<BigPoint>)rs.list(0,(int)(response.getCount()-1));
		for(BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Test
	public void testFilterByRadius() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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
		SetId result_set_id = gPUdb.new_setid();

		filter_by_radius_response response = gPUdb.do_filter_by_radius(ns, result_set_id, "x", "y", -77.03, 38.89, 75000.0);
		assertTrue(response.getCount() == 3); // THIS IS THE REAL COUNT

		// grab the RS; loop through the points
		NamedSet rs = gPUdb.getNamedSet(result_set_id, type);
		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)rs.list(0,2);
		assertTrue(points.size() == 3);
		for(BigPoint point : points) {
			in_points.contains(point);
		}

	}

	@Test
	public void testFilterByString() {

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// add points in the bounds
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "Test Msg ID1", -77.03, 38.89, 0, "test source 1", "IN_RADIUS");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "Production Msg", -77.05, 38.89, 1, "Prod", "IN_RADIUS");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "Staging Msg", -77.13, 38.99, 2, "Stage", "IN_RADIUS");
		ns.add(p);

		// outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(),  "outside", -77.03, 37.89, 0, "SRC1", "OUT_RADIUS");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(),  "testing", -79.03, 38.89, 1, "outside", "OUT_RADIUS");
		ns.add(p);
		in_points.add(p);

		SetId result_set_id = gPUdb.new_setid();
		ArrayList<CharSequence> options = new ArrayList<CharSequence>();
		ArrayList<CharSequence> attributes = new ArrayList<CharSequence>();

		// Search for objects that have (contains) the word "outside"
		filter_by_string_response response = gPUdb.do_filter_by_string(ns, result_set_id, "outside", "contains", options, attributes);
		System.out.println("String filter count:" + response.getCount()); // should print 2

		// grab the RS; loop through the points
		NamedSet rs = gPUdb.getNamedSet(result_set_id, type);

		// list
		ArrayList<BigPoint> points = (ArrayList<BigPoint>) rs.list(0,10);
		assertTrue(points.size() == 2);
		for(BigPoint point : points) {
			in_points.contains(point);
		}
	}

	@Test
	public void testStoreGroupBy() {
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		// group 1
		List<BigPoint> list = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		list.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);
		list.add(p);

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

		group_by_response response = gPUdb.do_group_by(ns, attributes);
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
		store_group_by_response sgb_response = gPUdb.do_store_group_by(ns, "group_id", count_map);
		System.out.println("finished store group by");
		String group_1_rs_id = str_count_map.get("GROUP1").get(1);
		NamedSet group_1_ns = gPUdb.getNamedSet(new SetId(group_1_rs_id), type);

		ArrayList<BigPoint> points = (ArrayList<BigPoint>)group_1_ns.list(0,2);
		assertTrue(points.size() == 3);
		for(BigPoint point : points) {
			assertTrue(list.contains(point));
		}
	}

	@Ignore
	public void testTracks() {
		// NOTE: testing the initialize, paging, and get sorted chain of commands
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

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

		throw new GPUdbException(" ......FIX ME.......");

	}

	@Ignore
	public void testJoin() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type);
		NamedSet right_set = gPUdb.newNamedSet(type);

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
		SetId result_set_id = gPUdb.new_setid();
		String join_type = UUID.randomUUID().toString();
		join_response response = gPUdb.do_join(left_set, "group_id", right_set, "group_id", join_type, result_set_id);

		// Test the number of joins
		assertTrue(response.getCount() == 7);
	}

	@Test
	public void testUnique() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type);

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

		unique_response response = gPUdb.do_unique(left_set, "msg_id");
		assertTrue(response.getIsString());

		List<CharSequence> values = response.getValuesStr();
		assertTrue(values.size() == 3);
		assertTrue(values.get(0).toString().equals("LMSGID1"));
		assertTrue(values.get(1).toString().equals("LMSGID2"));
		assertTrue(values.get(2).toString().equals("LMSGID3"));

		response = gPUdb.do_unique(left_set, "x");
		assertFalse(response.getIsString());

		List<Double> values2 = response.getValues();
		assertTrue(values2.size() == 3);
		assertTrue(values2.contains(1.01));
		assertTrue(values2.contains(2.01));
		assertTrue(values2.contains(3.01));
	}

	@Ignore
	public void testPredicateJoin() {
		gPUdb.do_clear();
		Type type1 = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type1);

		Type type2 = gPUdb.create_type(SimplePoint.class);
		NamedSet right_set = gPUdb.newNamedSet(type2);

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
			register_type_transform_response response = gPUdb.do_register_type_transform(type1.getID(), type2.getID(), transform_map);
		} catch(GPUdbException e){
			if (!(e.toString().contains("already exists"))) {
				throw e;
			}
		}

		// run predicate join
		SetId result_set_id = gPUdb.new_setid();
		String join_type = UUID.randomUUID().toString();
		predicate_join_response pj_response = gPUdb.do_predicate_join(left_set, right_set, type2.getID(), join_type, result_set_id, "(abs(LEFT.x - RIGHT.x) > 5.0)");

		assertTrue(pj_response.getCount() == 9);
	}

	@Ignore
	public void testMergeSets() {
		gPUdb.do_clear();
		Type type1 = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type1);
		NamedSet right_set = gPUdb.newNamedSet(type1);

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

		SetId result_set_id = gPUdb.new_setid();
		List<SetId> in_sets = new ArrayList<SetId>();
		in_sets.add(left_set.get_setid());
		in_sets.add(right_set.get_setid());

		merge_sets_response response = gPUdb.do_merge_sets(in_sets, type1, result_set_id);

		NamedSet merged_ns = gPUdb.getNamedSet(result_set_id);
		assertTrue(merged_ns.list(0,10).size() == 8);
	}

	@Ignore
	public void testDynamicDuo() {
		gPUdb.do_clear();
		Type type1 = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newSingleNamedSet(type1);

		// create two tracks that will be a duo - part of the same line
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
		SetId result_set_id = gPUdb.new_setid();
		predicate_join_response response = gPUdb.do_dynamic_duo(ns, "x", "y", "timestamp", "group_id", 1, 1, result_set_id);
		NamedSet rs = gPUdb.getNamedSet(result_set_id);

		// joined set will be ones part of the same line (x, y)
		List<GenericObject> points = (ArrayList<GenericObject>)rs.list(0,10);
		assertTrue(points.size() == 1);

		GenericObject go = points.get(0);
		assertTrue((go.getField("RIGHT_msg_id").toString().equals("MSGID1") &&
				go.getField("LEFT_msg_id").toString().equals("MSGID2")) ||
				  (go.getField("RIGHT_msg_id").toString().equals("MSGID2") &&
						  go.getField("LEFT_msg_id").toString().equals("MSGID1")));

		// now test with parent sets
		Type bgType = gPUdb.create_type(BigPoint.class);
		// NOTE: only going to work against semantic type TRACK
		Type ctType = gPUdb.create_type("{\"type\":\"record\",\"name\":\"CustomType\",\"fields\":[{\"name\":\"TRACKID\"" +
				",\"type\":\"string\"},{\"name\":\"x\",\"type\":\"double\"},{\"name\":\"y\",\"type\":\"double\"}," +
				"{\"name\":\"TIMESTAMP\",\"type\":\"double\"}, {\"name\":\"OBJECT_ID\",\"type\":\"string\"}]}",
					"", "", SemanticTypeEnum.TRACK);

		// parent set id
		NamedSet parentNS = gPUdb.newParentNamedSet();
		NamedSet bgChild = gPUdb.newChildNamedSet(parentNS, bgType);
		NamedSet ctChild = gPUdb.newChildNamedSet(parentNS, ctType);

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

		// another track will not match with the above one
		go = new GenericObject();
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

			//result_set_id = gPUdb.new_setid();
			//response = gPUdb.do_dynamic_duo(parentNS, "x", "y", "TIMESTAMP", "TRACKID", 1, 1, result_set_id);
			//System.out.println("response.getCount():"+response.getCount());
			//rs = gPUdb.getNamedSet(result_set_id);
			rs = parentNS.do_dynamic_duo(1, 1);

			// joined set will create a single track it appears for same child
			points = (ArrayList<GenericObject>)rs.list(0,10);
			assertTrue(points.size() == 1);

			go = points.get(0);
			assertTrue((go.getField("RIGHT_TRACKID").toString().equals("GROUP2") &&
					go.getField("LEFT_TRACKID").toString().equals("GROUP3")) ||
					  (go.getField("RIGHT_TRACKID").toString().equals("GROUP3") &&
							  go.getField("LEFT_TRACKID").toString().equals("GROUP2")));

		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}

	@Ignore
	public void testCopySet() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type);
		NamedSet right_set = gPUdb.newNamedSet(type);

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

		// Create a join name set on "group_id" will result in a named set with 7 data points
		SetId join_set_id = gPUdb.new_setid();
		String join_type = UUID.randomUUID().toString();
		join_response response = gPUdb.do_join(left_set, "group_id", right_set, "group_id", join_type, join_set_id);
		assertTrue(response.getCount() == 7);
		List<BigPoint> in_points = (ArrayList<BigPoint>) gPUdb.getNamedSet(join_set_id).list(0, 6);

		// Copy the joined set
		SetId copied_set_id = gPUdb.new_setid();
		NamedSet join_ns = gPUdb.getNamedSet(join_set_id, type); // NOTE: type here, of course, is wrong
			copy_set_response copy_response = gPUdb.do_copy_set(join_ns, copied_set_id, type, "RIGHT");
			NamedSet copied_ns = gPUdb.getNamedSet(copied_set_id, type);
			assertTrue(copy_response.getCount() == 7);

		// Get set [so the casting is breaking it here...]
		List<BigPoint> points = (ArrayList<BigPoint>) copied_ns.list(0, 6);
			for(BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Test
	public void testFilterByList() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Add points in the bounds
		ArrayList<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1, 2.01, 0, "SRC1", "IN_LIST");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2, 2.01, 1, "SRC2", "IN_LIST");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "IN_LIST");
		ns.add(p);

		// Outside the bounds
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID4", 10.01, 20.01, 0, "SRC1", "OUT_LIST");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID5", 20.01, 20.01, 1, "SRC2", "OUT_LIST");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_LIST");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_LIST");
		ns.add(p);

		// Create a map of the allowed data
		Map<CharSequence, List<CharSequence>> attribute_map = new HashMap<CharSequence, List<CharSequence>>();
			attribute_map.put("x",new ArrayList());
			attribute_map.get("x").add(new String("1"));
			attribute_map.get("x").add(new String("2"));
			attribute_map.put("group_id",new ArrayList());
			attribute_map.get("group_id").add(new String("IN_LIST"));

		// Call the filter method
		SetId result_set_id = gPUdb.new_setid();
		filter_by_list_response response = gPUdb.do_filter_by_list(ns, result_set_id, attribute_map);
		NamedSet ns_list = gPUdb.getNamedSet(result_set_id, type);
		assertTrue(response.getCount() == 2);

		// Test the returned data
		ArrayList<BigPoint> points = (ArrayList<BigPoint>) ns_list.list(0,10);
		assertTrue(points.size() == 2);
		for(BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Test
	public void testStats() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);

		NamedSet ns2 = gPUdb.newNamedSet(type);

		// Create some points and add them
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns2.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns2.add(p);

		// Get the stat of number of sets and points in each set
		stats_response response = gPUdb.do_stats();
		Map<CharSequence, Long> count_map = response.getCountMap();

		// We can also do the reverse conversion from String to org.apache.avro.util.Utf8
		org.apache.avro.util.Utf8 key_ns = new org.apache.avro.util.Utf8(ns.get_setid().get_id());
		org.apache.avro.util.Utf8 key_ns2 = new org.apache.avro.util.Utf8(ns2.get_setid().get_id());

		// Test the counts in the returned map
		assertTrue(count_map.get(key_ns).intValue() == 3 &&
				count_map.get(key_ns2).intValue() == 2);
	}

	@Test
	public void testStatus() {
		
		/*
		gPUdb.do_clear();
	
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID3", 3.01, 3.01, 2, "SRC3", "GROUP3");
		ns.add(p);

		NamedSet ns2 = gPUdb.newNamedSet(type);

		// Create some points and add them
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 0, "SRC1", "GROUP1");
		ns2.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID2", 2.01, 2.01, 1, "SRC2", "GROUP2");
		ns2.add(p);
		*/
		// Get status
		status_response response = gPUdb.do_status(null);
		System.out.println(" XXXX " + response.total_size);
		System.out.println(" XXXX " + response.total_full_size);
		
		/*
		assertTrue(response.getSizes().get(0) == 3);
		response = gPUdb.do_status(ns2);
		assertTrue(response.getSizes().get(0) == 2);
		*/
	}

	@Test
	public void testSetInfo() {
		gPUdb.do_clear();

		Type type1 = gPUdb.create_type(BigPoint.class);
		Type type2 = gPUdb.create_type(BytesPoint.class);
		NamedSet ty1_ns1 = gPUdb.newNamedSet(type1);
		NamedSet ty1_ns2 = gPUdb.newNamedSet(type1);

		NamedSet ty2_ns1 = gPUdb.newNamedSet(type2);
		NamedSet ty2_ns2 = gPUdb.newNamedSet(type2);

		set_info_response response_ty1_ns1 = gPUdb.do_set_info(ty1_ns1.get_setid());
		set_info_response response_ty1_ns2 = gPUdb.do_set_info(ty1_ns2.get_setid());
		set_info_response response_ty2_ns1 = gPUdb.do_set_info(ty2_ns1.get_setid());
		set_info_response response_ty2_ns2 = gPUdb.do_set_info(ty2_ns2.get_setid());

		assertTrue(response_ty1_ns1.getTypeIds().get(0).equals(response_ty1_ns2.getTypeIds().get(0)) &&
				response_ty1_ns1.getTypeSchemas().get(0).equals(response_ty1_ns2.getTypeSchemas().get(0)));

		assertTrue(response_ty2_ns1.getTypeIds().get(0).equals(response_ty2_ns2.getTypeIds().get(0)) &&
				response_ty2_ns1.getSchema().equals(response_ty2_ns2.getSchema()));

		assertFalse(response_ty1_ns1.getTypeIds().get(0).equals(response_ty2_ns1.getTypeIds().get(0)));
		assertFalse(response_ty1_ns1.getTypeSchemas().get(0).equals(response_ty2_ns1.getTypeSchemas().get(0)));
	}

	@Ignore
	public void testSort() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Add points
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

		/*********** JAZZ
		// Sort by timestamp
		sort_response response = gPUdb.do_sort(ns, "timestamp");
		assertTrue(response.getStatus().toString().equals("DONE"));

		// Test
		ArrayList<BigPoint> points = (ArrayList<BigPoint>)ns.list(0,4);
		assertTrue(points.size() == 5);
		assertTrue(points.get(0).timestamp == 1 && points.get(1).timestamp == 2
				&& points.get(2).timestamp == 3 && points.get(3).timestamp == 4 &&
				points.get(4).timestamp == 5);

		points = (ArrayList<BigPoint>) ns.list(5, 9); // WARNING: list(5) will cause an error
		assertTrue(points.size() == 5);
		assertTrue(points.get(0).timestamp == 6 && points.get(1).timestamp == 7 &&
				points.get(2).timestamp == 8 && points.get(3).timestamp == 9 &&
				points.get(4).timestamp == 10);
		*/
	}

	@Test
	public void testSelect() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Add points into the box area
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

		// Outside the box
		p = new BigPoint(UUID.randomUUID().toString(), "MSGID4", 10.01, 1.01, 0, "SRC1", "OUT_BOX");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID5", 20.01, 2.01, 1, "SRC2", "OUT_BOX");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID6", 30.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID7", 35.01, 3.01, 2, "SRC3", "OUT_BOX");
		ns.add(p);

		// Select points where x < y
		SetId result_set_id = gPUdb.new_setid();
		select_response response = gPUdb.do_select(ns, result_set_id, "(x < y);");
		assertTrue(response.getCount() == 4);

		// grab the RS; loop through the points
		NamedSet rs = gPUdb.getNamedSet(result_set_id, type);
		ArrayList<BigPoint> points = (ArrayList<BigPoint>) rs.list(0,10);
		assertTrue(points.size() == 4);
		for(BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Ignore
	public void testJoinSetup() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type);
		NamedSet right_set = gPUdb.newNamedSet(type);

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
		SetId subset = gPUdb.new_setid();
		join_setup_response response = gPUdb.do_join_setup(left_set, "x", right_set, "x", subset);

		assertTrue(response.getCount() == 3);
	}

	@Ignore
	public void testJoinIncremental() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet left_set = gPUdb.newNamedSet(type);
		NamedSet right_set = gPUdb.newNamedSet(type);

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

		// Setup the join on x will find out which objects on the left match those on right
		SetId subset = gPUdb.new_setid();
		join_setup_response response = gPUdb.do_join_setup(left_set, "x", right_set, "x", subset);
		assertTrue(response.getCount() == 3);

		// In above named set join the left item 0 with all matching right items on x
		SetId result_set = gPUdb.new_setid();
		String result_type = UUID.randomUUID().toString();
		NamedSet ns_subset = gPUdb.getNamedSet(subset, type);
		join_incremental_response ji_response = gPUdb.do_join_incremental(ns_subset, "x", 0, right_set, "x", result_type, result_set);
		assertTrue(ji_response.getCount() == 2);

		// Get points in the first joined result set, appears to result in GenericObject
		NamedSet rs = gPUdb.getNamedSet(result_set);
		ArrayList<GenericObject> points = (ArrayList<GenericObject>)rs.list(0,10);
		assertTrue(points.size() == 2);

		for(GenericObject point : points) {
			assertEquals("2", point.getField("x"));
		}

		// In same above named set join the left item 1 with all matching right items on x
		ji_response = gPUdb.do_join_incremental(ns_subset, "x", 1, right_set, "x", result_type, result_set);
		assertTrue(ji_response.getCount() == 2);

		// list
		points = (ArrayList<GenericObject>)rs.list(0,10);
		assertTrue(points.size() == 4); // NOTE: the set is the result of only one join incremental so far

		ArrayList<Map<String,String>> map_list = new ArrayList<Map<String,String>>();
		for(int i=0; i<points.size(); i++) {
			// NOTE: test the two lists for equality

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
		gPUdb.do_clear();

		Type type = gPUdb.create_type(KeyValueType.class);
		NamedSet left_set = gPUdb.newNamedSet(type);
		NamedSet right_set = gPUdb.newNamedSet(type);

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

		// Setup join to match on value
		SetId subset = gPUdb.new_setid();
		join_setup_response response = gPUdb.do_join_setup(left_set, "value", right_set, "value", subset);
		assertTrue(response.getCount() == 3);

		// now do the incremental
		SetId result_set = gPUdb.new_setid();
		String result_type = UUID.randomUUID().toString();
		NamedSet ns_subset = gPUdb.getNamedSet(subset, type);
		join_incremental_response ji_response = gPUdb.do_join_incremental(ns_subset, "value", 0, right_set, "value", result_type, result_set);
		assertTrue(ji_response.getCount() == 2);

		// grab the rs
		NamedSet rs = gPUdb.getNamedSet(result_set);

		// list
		ArrayList<GenericObject> points = (ArrayList<GenericObject>)rs.list(0,10);
		assertTrue(points.size() == 2);

		for(GenericObject point : points) {
			assertEquals("value1", point.getField("value"));
		}

		ji_response = gPUdb.do_join_incremental(ns_subset, "value", 1, right_set, "value", result_type, result_set);
		assertTrue(ji_response.getCount() == 2);

		// list - why 4 and not 2
		points = (ArrayList<GenericObject>)rs.list(0,10);
		System.out.println("points size:"+points.size());
		assertTrue(points.size() == 4); // NOTE: the set is the result of only one join incremental so far

		for(GenericObject point : points) {
			assertEquals("value1", point.getField("value"));
		}
	}

	@Ignore
	public void testStoreGroupBySort() {
		gPUdb.do_clear();
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		// group 1
		List<BigPoint> in_points = new ArrayList<BigPoint>();
		BigPoint p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1");
		ns.add(p);
		in_points.add(p);

		p = new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1");
		ns.add(p);
		in_points.add(p);

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

		group_by_response response = gPUdb.do_group_by(ns, attributes);
		Map<CharSequence, List<CharSequence>> count_map = response.getCountMap();

		// CharSequences in avro are org.apache.avro.util.Utf8 let's convert into strings
		Map<String, List<String>> str_count_map = new HashMap<String, List<String>>();
		Iterator<CharSequence> iter = count_map.keySet().iterator();
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

		// Test the groups counts
		assertTrue(str_count_map.get("GROUP1").get(0).equals("5") &&
				str_count_map.get("GROUP2").get(0).equals("1") &&
				str_count_map.get("GROUP3").get(0).equals("2"));

		// Store the group by map while also sorting on an attribute
		store_group_by_response sgb_response = gPUdb.do_store_group_by(ns, "group_id", count_map, "timestamp");
		String group_1_rs_id = str_count_map.get("GROUP1").get(1);
		NamedSet group_1_ns = gPUdb.getNamedSet(new SetId(group_1_rs_id), type);

		// Give me all the points in the group1
		ArrayList<BigPoint> points = (ArrayList<BigPoint>) group_1_ns.list(0, 4);
		assertTrue(points.size() == 5);
		for(BigPoint point : points) {
			assertTrue(in_points.contains(point));
		}
	}

	@Ignore
	public void testTriggering() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		SetId si = gPUdb.new_setid("TEST_TRIGGERING_SET");
		NamedSet ns = gPUdb.newSingleNamedSet(si, type);

		// Grouping attribute? used for determining tracks basically, so we can find when a track USED to trigger something but has stopped
		register_trigger_range_response response = gPUdb.do_register_trigger(ns.get_setid(), "x", 1, 10, "group_id");

		// Thread will sleep until after listener call
		Runnable r = new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					// Do nothing
				}
				NamedSet ns = gPUdb.getNamedSet(new SetId("TEST_TRIGGERING_SET"));
				System.out.println("Got this named set");

				ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
				System.out.println("added");
			}
		};

		Thread t = new Thread(r);
		t.start();

		BigPoint p = null;
		try {
			// Appears to block wait indefinitely without returning anything
			p = (BigPoint)gPUdb.do_listen_for_this_trigger(response.getTriggerId().toString());
		} catch(Exception e) {
			System.err.println("ERROR while listening to trigger:"+e.toString());
		}
		assertTrue(p != null);
	}

	@Ignore
	public void testRegisterTriggerNAI() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		SetId si = gPUdb.new_setid("TEST_TRIGGER_NAI_SET_t8");
		NamedSet ns = gPUdb.newSingleNamedSet(si, type);

		List<Double> x_vector = new ArrayList<Double>();
		List<Double> y_vector = new ArrayList<Double>();
		x_vector.add(0.0);
		x_vector.add(10.0);
		x_vector.add(15.0);
		y_vector.add(0.0);
		y_vector.add(10.0);
		y_vector.add(15.0);

		// grouping attribute? used for determining tracks basically, so we can find when a track USED to trigger something but has stopped
		register_trigger_nai_response response = gPUdb.do_register_trigger(si, "x", x_vector, "y", y_vector, "group_id");

		// start up a thread to do adds in a second
		Runnable r = new Runnable() {
			public void run() {

				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					// Do nothing
				}
				NamedSet ns = gPUdb.getNamedSet(new SetId("TEST_TRIGGER_NAI_SET_t8"));
				System.out.println("Got this named set");

				ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
				System.out.println("added");
			}
		};

		Thread t = new Thread(r);
		t.start();

		BigPoint p = null;
		try {
			// Appears to block wait indefinitely without returning anything
			p = (BigPoint)gPUdb.do_listen_for_this_trigger(response.getTriggerId().toString());
			System.out.print(p.toString());
		} catch(Exception e) {
			System.err.println("ERROR while listening to trigger NAI:"+e.toString());
		}
		assertTrue(p != null);
	}

	@Ignore
	public void testRegisterTriggerRange() {
		gPUdb.do_clear();

		NamedSet namedDataSet = gPUdb.newSingleNamedSet(gPUdb.new_setid("TEST_RANGE_TRIGGERING_SET"), 
                                gPUdb.create_type(BigPoint.class));

		register_trigger_range_response response = gPUdb.do_register_trigger(namedDataSet.get_setid(), "x", 0, 100, "group_id");

		Runnable r = new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					// Do nothing
	            }
				NamedSet ns = gPUdb.getNamedSet(new SetId("TEST_RANGE_TRIGGERING_SET"));
				System.out.println("Got this named set");

				ns.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
				System.out.println("added");
			}
		};

		Thread t = new Thread(r);
		t.start();

		BigPoint p = null;
		try {
			p = (BigPoint)gPUdb.do_listen_for_this_trigger(response.getTriggerId().toString());
			System.out.print(p.toString());
		} catch(Exception e) {
			System.err.println("ERROR while listening to trigger: " + e.toString());
		}
		assertTrue(p != null);
    }

	@Ignore
	public void testRoadIntersection() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// x road vector; see python test for more
		List<Double> x_road_vals = Arrays.asList(6.0,5.0,8.0,4.0);
		List<Double> y_road_vals = Arrays.asList(8.0,6.0,5.0,3.0);

		// Track 1: just left side of above x and y points
		BigPoint p;
		List<Object> points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_001", 2.0, 9.0, 1, "SRC_ID_001", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_002", 2.0, 8.0, 2, "SRC_ID_002", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_003", 1.0, 6.0, 3, "SRC_ID_003", "GROUP_001"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_004", 1.0, 4.0, 4, "SRC_ID_004", "GROUP_001"));

		// Track 2: just right side of above x and y points
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_005",10.0, 8.0, 1, "SRC_ID_001", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_006", 9.0, 7.0, 2, "SRC_ID_002", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_007", 8.0, 6.0, 3, "SRC_ID_003", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_008", 7.0, 7.0, 4, "SRC_ID_004", "GROUP_002"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_009", 6.0, 7.0, 4, "SRC_ID_004", "GROUP_002"));

		// Track 3: on both sides of above x and y points
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_010", 2.0, 5.0, 1, "SRC_ID_001", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_011", 3.0, 4.0, 2, "SRC_ID_002", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_012", 4.0, 4.0, 3, "SRC_ID_003", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_013", 5.0, 4.0, 4, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_014", 6.0, 4.0, 4, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_015", 7.0, 3.0, 4, "SRC_ID_004", "GROUP_003"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_016", 8.0, 3.0, 4, "SRC_ID_004", "GROUP_003"));

	    //Track 4: both sides of above x and y points
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_017", 0.0, 8.0, 1, "SRC_ID_001", "GROUP_004"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSG_ID_018",10.0, 4.0, 2, "SRC_ID_002", "GROUP_004"));

		// Add the pints to the same named set
		ns.add_list(points);

		// do road intersections
		try {
			road_intersection_response response = gPUdb.do_road_intersection(ns.get_setid(), "x", "y", x_road_vals, y_road_vals, "group_id");
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
		} catch(GPUdbException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void testSpatialQuery() {
		// Do the two polygons intersects and disjoint (nothing common)
		assertTrue(gPUdb.do_spatial_query("POLYGON((1.0 1.0,4.0 1.0,4.0 4.0,1.0 4.0,1.0 1.0))", "POLYGON((3.0 2.0,5.0 2.0,5.0 3.0,3.0 3.0))", SpatialOperationEnum.INTERSECTS));
		assertFalse(gPUdb.do_spatial_query("POLYGON((1.0 1.0,4.0 1.0,4.0 4.0,1.0 4.0,1.0 1.0))", "POLYGON((3.0 2.0,5.0 2.0,5.0 3.0,3.0 3.0))", SpatialOperationEnum.DISJOINT));
	}

	@Test
	public void testUpdateSetTTL() {
		gPUdb.do_clear();

		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet ns = gPUdb.newNamedSet(type);

		// Create some points and add them
		List<Object> points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1"));

		// Add the points to named set
		ns.add_list(points);

		// Do stats; make sure the set is initially populated
		stats_response response = gPUdb.do_stats();
		assertTrue(response.getCountMap().containsKey(new org.apache.avro.util.Utf8(ns.get_setid().get_id())));

		// Update the set TTL
		try {
			// Set named set life time of 1 minute
			gPUdb.do_update_ttl(ns.get_setid(), 1);
		} catch(GPUdbException e) {
			System.out.println(e.toString());
		}

		// Display size of the set until time elapsed is twice the set time limit
		int milliseconds_waited = 0;
		int delta = 5000;
		while(milliseconds_waited < 120 * 1000) {
			try {
				// wait; 5 seconds
				System.out.println("Sleep for "+delta+" total waited:"+milliseconds_waited);
				Thread.sleep(delta);
				milliseconds_waited += delta;
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

		// Run stats again and test. Test failure means deletion is slower than estimated
		assertFalse(gPUdb.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns.get_setid().get_id())));
	}

	@Test
	public void testUpdateSetTTLForManySets() {
		gPUdb.do_clear();

		Type type1 = gPUdb.create_type(BigPoint.class);
		NamedSet ns1 = gPUdb.newNamedSet(type1);

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
		stats_response response = gPUdb.do_stats();
		assertTrue(gPUdb.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns1.get_setid().get_id())));

		Type type2 = gPUdb.create_type(BigPoint.class);
		NamedSet ns2 = gPUdb.newNamedSet(type2);

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
		stats_response response2 = gPUdb.do_stats();
		assertTrue(gPUdb.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns2.get_setid().get_id())));

		// Add the time limits for the named sets
		Map<SetId, Integer> ttlSet = new HashMap<SetId, Integer>();
		ttlSet.put(ns1.get_setid(), 1);
		ttlSet.put(ns2.get_setid(), 2);
		try {
			gPUdb.do_update_ttl(ttlSet);
		} catch(GPUdbException e) {
			System.out.println(e.toString());
		}

		// Verify the named sets timings
		int set1TTL = gPUdb.getSetTTL(ns1);
		assertTrue(set1TTL == 1);

		int set2TTL = gPUdb.getSetTTL(ns2);
		assertTrue(set2TTL == 2);

		// Clear above named sets for test overload method
		gPUdb.do_clear();

		type1 = gPUdb.create_type(BigPoint.class);
		ns1 = gPUdb.newNamedSet(type1);

		// Create some points and add them
		points = new ArrayList<Object>();
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 1, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 3, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 4, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 5, "SRC1", "GROUP1"));
		points.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 1.01, 2.01, 2, "SRC1", "GROUP1"));

		// Add the list of points
		ns1.add_list(points);

		// Do stats; make sure the set is initially populated
		response = gPUdb.do_stats();
		assertTrue(gPUdb.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns1.get_setid().get_id())));

		type2 = gPUdb.create_type(BigPoint.class);
		ns2 = gPUdb.newNamedSet(type2);

		// Create some points and add them
		points2 = new ArrayList<Object>();
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 1, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 3, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 4, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 5, "SRC1", "GROUP2"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 1.01, 2.01, 2, "SRC1", "GROUP2"));

		// Add list of points
		ns2.add_list(points2);

		// Do stats; make sure the set is initially populated
		response2 = gPUdb.do_stats();
		assertTrue(gPUdb.do_stats().getCountMap().containsKey(new org.apache.avro.util.Utf8(ns2.get_setid().get_id())));

		List<SetId> setList = new ArrayList<SetId>();
		setList.add(ns1.get_setid());
		setList.add(ns2.get_setid());

		// Set time limit on the named set
		try {
			gPUdb.do_update_ttl(setList, 2);
		} catch(GPUdbException e) {
			System.out.println(e.toString());
		}

		// Test the named sets have the set time limits
		set1TTL = gPUdb.getSetTTL(ns1);
		assertTrue(set1TTL == 2);

		set2TTL = gPUdb.getSetTTL(ns2);
		assertTrue(set2TTL == 2);
	}

	@Ignore
	public void testFilterByValue() {
		gPUdb.do_clear();

		NamedSet parent = gPUdb.newParentNamedSet();
		Type type1 = gPUdb.create_type(BigPoint.class);
		SetId result_set_id = gPUdb.new_setid();

		NamedSet ns1 = gPUdb.newChildNamedSet(parent, type1);

		// Filter by value on empty set
		filter_by_value_response fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "");
		assertTrue(fbvr.getCount() == 0);

		// Filter by value an attribute that does not exist
		try {
			result_set_id = gPUdb.new_setid();
			fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "abcd");
		} catch (GPUdbException e) {
			assertTrue(e.getMessage().contains("doesn't have an attribute named: abcd"));
		}

		// Create some points
		List<Object> points2 = new ArrayList<Object>();
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 5.1, 6.0, 1, "SRC1", "GROUPA"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID1", 5.2, 6.1, 2, "SRC2", "GROUPB"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 5.3, 5.1, 3, "SRC3", "GROUPC"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID2", 5.4, 6.3, 4, "SRC1", "GROUPC"));
		points2.add(new BigPoint(UUID.randomUUID().toString(), "MSGID3", 5.5, 6.4, 5, "SRC2", "GROUPC"));

		// Add list of points to named set
		ns1.add_list(points2);

		// Filter with a string
		result_set_id = gPUdb.new_setid();
	    fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "MSGID1", "");
		assertTrue(fbvr.getCount() == 2);

		// Filter with a string
		result_set_id = gPUdb.new_setid();
	    fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "SRC1", "");
		assertTrue(fbvr.getCount() == 2);

		// Filter with a string
		result_set_id = gPUdb.new_setid();
	    fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "GROUPC", "");
		assertTrue(fbvr.getCount() == 3);

		// Filter with a string
		result_set_id = gPUdb.new_setid();
	    fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "abcd", "");
		assertTrue(fbvr.getCount() == 0);

		// Filter with a double value with no attribute
		result_set_id = gPUdb.new_setid();
		fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "");
		assertTrue(fbvr.getCount() == 2);

		// Filter with a double value with a given attribute y
		result_set_id = gPUdb.new_setid();
		fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "y");
		assertTrue(fbvr.getCount() == 1);

		// Filter with a double value with a given attribute x
		result_set_id = gPUdb.new_setid();
		fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, false, 5.1, "", "x");
		assertTrue(fbvr.getCount() == 1);

		// Filter with a double value on a parent set
		result_set_id = gPUdb.new_setid();
		fbvr = gPUdb.do_filter_by_value(parent, result_set_id, false, 5.1, "", "x");
		assertTrue(fbvr.getCount() == 1);

		Type type2 = gPUdb.create_type(SimplePoint.class);
		NamedSet child2 = gPUdb.newChildNamedSet(parent, type2);

		// Create some points and add them
		List<Object> points = new ArrayList<Object>();
		points.add(new SimplePoint(UUID.randomUUID().toString(), 5.1, 6.1));
		points.add(new SimplePoint(UUID.randomUUID().toString(), 5.1, 6.1));
		child2.add_list(points);

		// Filter with a double value on a parent set
		result_set_id = gPUdb.new_setid();
		fbvr = gPUdb.do_filter_by_value(parent, result_set_id, false, 5.1, "", "");
		assertTrue(fbvr.getCount() == 4);

		// Test the total points added for the parent
		NamedSet rs = parent.filterByValue(5.1, "");
		assertTrue(rs.getChildren().size() == 2);
		int totObjects = 0;
		Collection<NamedSet> sets = rs.getChildren();
		for( NamedSet ns : sets ) {
			totObjects += ns.size();
		}
		assertTrue(totObjects == 7);
	}

	@Test
	public void testFilterBySet() {
		gPUdb.do_clear();

		NamedSet parent = gPUdb.newParentNamedSet();
		Type type1 = gPUdb.create_type(BigPoint.class);
		SetId result_set_id = gPUdb.new_setid();

		NamedSet ns1 = gPUdb.newChildNamedSet(parent, type1);

		// Filter on empty set
		filter_by_value_response fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "");
		assertTrue(fbvr.getCount() == 0);

		// Filter on an attribute that doesn't exist
		try {
			result_set_id = gPUdb.new_setid();
			fbvr = gPUdb.do_filter_by_value(ns1, result_set_id, true, 0.0, "hello", "abcd");
		} catch( GPUdbException ge) {
			assertTrue(ge.getMessage().contains("doesn't have a string attribute named: abcd"));
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
		Type type = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		NamedSet ns = gPUdb.newSingleNamedSet(type);

		// Create some objects we will use defining our filter
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

		// Create a filtered named set from ns1 on msg_id using persons in ns
		NamedSet result = ns1.do_filter_by_set("msg_id", ns.get_setid(), "person");
		assertTrue(result.size() == 3);
	}
	
	@Test
	public void testCreateManyColumns() {
		gPUdb.do_clear();
		// Generate the many fields
		StringBuffer strBuf = new StringBuffer();
		int num = 20;
		for (int i=0; i<num; i++) {
			if (i == num-1) {
				String str = "{\"name\":\"Field_" + i + "\",\"type\":\"string\"}";
				strBuf.append(str);
			} else {
				String str = "{\"name\":\"Field_" + i + "\",\"type\":\"string\"},";
				strBuf.append(str);
			}
		}
		
		// Field definition
		String definition = "{\"type\":\"record\",\"name\":\"TestType\",\"fields\":" + 
				"[{\"name\":\"OBJECT_ID\",\"type\":\"string\"}," + 
				strBuf.toString() + "]}";
		
		// Create type and named set
		String label = "ManyFieldsTest";
		SemanticTypeEnum semanticType = SemanticTypeEnum.GENERICOBJECT;
		Type type = gPUdb.create_type(definition, "", label, semanticType);
		NamedSet ns = gPUdb.getNamedSet(new SetId("Many Fields Test"), type);
		
		// Add the data to the named set
		int rows = 300;
		Random rand = new Random(Integer.MAX_VALUE);
		for (int i=0; i<rows; i++) {
			GenericObject go = new GenericObject();
			go.addField("OBJECT_ID", "OBJID" + i);
			for (int j=0; j<num; j++) {
				go.addField("Field_" + j, "" + rand.nextInt(num * rows + 1));
			}
			ns.add(go);
		}
		
		// Test
		assertEquals(rows, ns.size());
	}
	
	@Test
	public void testRegisterParentSet() throws Exception {
		gPUdb.do_clear();
		
		// Test creating parent does not create set in GPUDB
		NamedSet ns = gPUdb.newParentNamedSet();
		SetId setId = ns.get_setid();
		try {
			status_response status = gPUdb.do_status(ns);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("set doesn't exist"));
		}
		
		// Test that registering parent creates parent in GPUDB (another way is to add child to parent)
		register_parent_set_response response = gPUdb.do_register_parent_set(setId, false);
		status_response status = gPUdb.do_status(ns);
		assertEquals(setId.toString(), status.getSetId().toString());
		
		// Test that creating child does create a child under the above parent in GPUDB
		Type type = gPUdb.create_type(BigPoint.class);
		NamedSet child = gPUdb.newChildNamedSet(ns, type);
		SetId childSetId = child.get_setid();
		assertEquals(child, gPUdb.getNamedSet(childSetId));
		
		// Test that creating a second child of same type when duplicate child is false above
		try {
			NamedSet childDuplicate = gPUdb.newChildNamedSet(ns, type);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("There is already a child of this type"));
		}
		
		// Test creating a second child of different type is allowed in parent
		Type type2 = gPUdb.create_type("{\"type\":\"record\",\"name\":\"TestType\",\"fields\":[{\"name\":\"OBJECT_ID\",\"type\":\"string\"},{\"name\":\"person\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"}]}");
		NamedSet child2 = gPUdb.newChildNamedSet(ns, type2);
		SetId childSetId2 = child2.get_setid();
		assertEquals(child2, gPUdb.getNamedSet(childSetId2));
		
		// Clear GPUDB and re-create parent in GPUDB that allows duplicate children of same type
		gPUdb.do_clear();
		
		ns = gPUdb.newParentNamedSet();
		setId = ns.get_setid();
		response = gPUdb.do_register_parent_set(setId, true);
		status = gPUdb.do_status(ns);
		assertEquals(setId.toString(), status.getSetId().toString());
		
		child = gPUdb.newChildNamedSet(ns, type);
		childSetId = child.get_setid();
		assertEquals(child, gPUdb.getNamedSet(childSetId));
		
		// Test will fail as Java API does not allow duplicate child sets of same type although register has this option
		//child2 = gPUdb.newChildNamedSet(ns, type);
		//childSetId2 = child2.get_setid();
		//assertEquals(child2, gPUdb.getNamedSet(childSetId2));
	}
	
	@Test
	public void testStatistics() throws Exception {
		//gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		SetId si = gPUdb.new_setid();
		NamedSet ns = gPUdb.newNamedSet(si, type);

		// Create some points and add them
		BigPoint p;
		p = new BigPoint(UUID.randomUUID().toString(), "MSG", 1, -3, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 2, -2, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSG", 3, -1, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 4, 0, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSG", 5, 1, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 6, 2, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 6, 3, 1, "SRC1", "GROUP1");
		ns.add(p);
		
		// Test statistics
		List<StatisticsOptionsEnum> stats = new ArrayList<StatisticsOptionsEnum>();
		stats.add(StatisticsOptionsEnum.MEAN);
		stats.add(StatisticsOptionsEnum.STDV);
		stats.add(StatisticsOptionsEnum.CARDINALITY);
		stats.add(StatisticsOptionsEnum.ESTIMATED_CARDINALITY);
		stats.add(StatisticsOptionsEnum.VARIANCE);
		stats.add(StatisticsOptionsEnum.SKEW);
		stats.add(StatisticsOptionsEnum.KURTOSIS);
		//stats.add(StatisticsOptionsEnum.SUM);
		stats.add(StatisticsOptionsEnum.COUNT);
		
		Map<String, Double> result = ns.statistics(stats, "y");
		assertTrue(result.get("mean") == 0.0);
		double std = result.get(StatisticsOptionsEnum.STDV.value());
		assertTrue(std > 2.1 && std < 2.2);
		assertNotNull(result.get(StatisticsOptionsEnum.ESTIMATED_CARDINALITY.value()));
		
		/*
		System.out.println(result.get(StatisticsOptionsEnum.CARDINALITY.value()));
		System.out.println(result.get(StatisticsOptionsEnum.COUNT.value()));
		System.out.println(result.get(StatisticsOptionsEnum.MEAN.value()));
		System.out.println(result.get(StatisticsOptionsEnum.STDV.value()));
		System.out.println(result.get(StatisticsOptionsEnum.ESTIMATED_CARDINALITY.value()));
		System.out.println(result.get(StatisticsOptionsEnum.VARIANCE.value()));
		System.out.println(result.get(StatisticsOptionsEnum.SKEW.value()));
		System.out.println(result.get(StatisticsOptionsEnum.KURTOSIS.value()));
		//System.out.println(result.get(StatisticsOptionsEnum.SUM.value()));
		*/
	}
	
	@Test
	public void testCanFacetAttribute() throws Exception {
		gPUdb.do_clear();
		
		Type type = gPUdb.create_type(BigPoint.class);
		SetId si = gPUdb.new_setid();
		NamedSet ns = gPUdb.newNamedSet(si, type);

		// Create some points and add them
		BigPoint p;
		p = new BigPoint(UUID.randomUUID().toString(), "MSG", 1, 2, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 2, 2, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSG", 3, 4, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 4, 6, 1, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(), "MSG", 5, 6, 0, "SRC1", "GROUP1");
		ns.add(p);
		p = new BigPoint(UUID.randomUUID().toString(),  "MSG", 6, 7, 1, "SRC1", "GROUP1");
		ns.add(p);
		
		// Test statistics cardinality for this data
		boolean result = ns.canFacetAttribute("y", 2);
		assertEquals(false, result);
		
		result = ns.canFacetAttribute("y", null);
		assertEquals(true, result);
	}
	
}