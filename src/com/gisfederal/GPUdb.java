package com.gisfederal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;
import avro.java.gpudb.add_object_response;
import avro.java.gpudb.add_symbol_response;
import avro.java.gpudb.bounding_box_response;
import avro.java.gpudb.bulk_add_response;
import avro.java.gpudb.clear_response;
import avro.java.gpudb.cluster_response;
import avro.java.gpudb.convex_hull_response;
import avro.java.gpudb.copy_set_response;
import avro.java.gpudb.delete_object_response;
import avro.java.gpudb.filter_by_bounds_response;
import avro.java.gpudb.filter_by_list_response;
import avro.java.gpudb.filter_by_nai_response;
import avro.java.gpudb.filter_by_radius_response;
import avro.java.gpudb.filter_by_set_response;
import avro.java.gpudb.filter_by_string_response;
import avro.java.gpudb.filter_by_value_response;
import avro.java.gpudb.filter_then_histogram_response;
import avro.java.gpudb.generate_heatmap_video_response;
import avro.java.gpudb.generate_video_response;
import avro.java.gpudb.get_objects_response;
import avro.java.gpudb.get_orphans_response;
import avro.java.gpudb.get_set_objects_response;
import avro.java.gpudb.get_set_response;
import avro.java.gpudb.get_sets_by_type_info_response;
import avro.java.gpudb.get_sorted_set_response;
import avro.java.gpudb.get_sorted_sets_response;
import avro.java.gpudb.get_tracks2_response;
import avro.java.gpudb.get_type_info_response;
import avro.java.gpudb.group_by_map_page_response;
import avro.java.gpudb.group_by_response;
import avro.java.gpudb.group_by_value_response;
import avro.java.gpudb.histogram_response;
import avro.java.gpudb.initialize_group_by_map_response;
import avro.java.gpudb.join_incremental_response;
import avro.java.gpudb.join_response;
import avro.java.gpudb.join_setup_response;
import avro.java.gpudb.make_bloom_response;
import avro.java.gpudb.max_min_response;
import avro.java.gpudb.merge_sets_response;
import avro.java.gpudb.new_set_response;
import avro.java.gpudb.plot2d_multiple_response;
import avro.java.gpudb.populate_full_tracks_response;
import avro.java.gpudb.predicate_join_response;
import avro.java.gpudb.register_parent_set_response;
import avro.java.gpudb.register_trigger_nai_response;
import avro.java.gpudb.register_trigger_range_response;
import avro.java.gpudb.register_type_response;
import avro.java.gpudb.register_type_transform_response;
import avro.java.gpudb.register_type_with_annotations_response;
import avro.java.gpudb.road_intersection_response;
import avro.java.gpudb.select_delete_response;
import avro.java.gpudb.select_response;
import avro.java.gpudb.select_update_response;
import avro.java.gpudb.server_status_response;
import avro.java.gpudb.set_info_response;
import avro.java.gpudb.shape_intersection_response;
import avro.java.gpudb.shape_literal_intersection_response;
import avro.java.gpudb.spatial_query_response;
import avro.java.gpudb.spatial_set_query_response;
import avro.java.gpudb.statistics_response;
import avro.java.gpudb.stats_response;
import avro.java.gpudb.status_response;
import avro.java.gpudb.store_group_by_response;
import avro.java.gpudb.trigger_notification;
import avro.java.gpudb.turn_off_response;
import avro.java.gpudb.unique_response;
import avro.java.gpudb.update_object_response;
import avro.java.gpudb.update_set_ttl_response;

import com.gisfederal.request.AddSymbolRequest;
import com.gisfederal.request.ClearRequest;
import com.gisfederal.request.CreateTypeWithAnnotationsRequest;
import com.gisfederal.request.DeleteObjectRequest;
import com.gisfederal.request.GenerateHeatMapVideoRequest;
import com.gisfederal.request.GenerateVideoRequest;
import com.gisfederal.request.GetObjectsRequest;
import com.gisfederal.request.GetOrphansRequest;
import com.gisfederal.request.GetSortedSetRequest;
import com.gisfederal.request.HistogramRequest;
import com.gisfederal.request.ListBasedOnTypeRequest;
import com.gisfederal.request.ListRequest;
import com.gisfederal.request.MergeSetsRequest;
import com.gisfederal.request.NewSetRequest;
import com.gisfederal.request.PopulateFullTracksRequest;
import com.gisfederal.request.PredicateJoinRequest;
import com.gisfederal.request.RegisterParentSetRequest;
import com.gisfederal.request.Request;
import com.gisfederal.request.RequestConnection;
import com.gisfederal.request.RequestFactory;
import com.gisfederal.request.SelectDeleteRequest;
import com.gisfederal.request.SelectUpdateRequest;
import com.gisfederal.request.SpatialQueryRequest;
import com.gisfederal.request.SpatialSetQueryRequest;
import com.gisfederal.request.TurnOffRequest;
import com.gisfederal.semantic.types.SemanticTypeEnum;
import com.gisfederal.utils.SpatialOperationEnum;
import com.gisfederal.utils.StatisticsOptionsEnum;
import com.google.gson.Gson;

/**
 * The main class of the API through which most commands are run.
 */
public class GPUdb {
	
	// this encapsulates the (HTTP/HTTPS) connection to gpudb 
	protected RequestConnection requestConnection;

	// builds the requests; logger
	private RequestFactory request_factory;
	private Gson gson;
	private Logger log;

	// a client can use a namespace for their set ids
	private boolean hasNamespace = false;
	private String namespace = "";

	// stores the named sets 
	private ClientNSStore ns_store;
	protected Ctx context;
	public SocketBase subscriber;

	// the user authorization string; defaults to empty
	private String user_auth = "";
	private String user_name = "";
	
	// call to generate a replay file
	private boolean collectForReplay = false;

	/**
	 * Get the user authorization.
	 */
	public String getUserAuth() {return this.user_auth;	}

	/**
	 * Set the user authorization.
	 */
	public void setUserAuth(String _user_auth) {this.user_auth = _user_auth;}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setInStore(SetId setId, NamedSet ns) {
		ns_store.put(setId, ns);
	}

	public boolean isCollectForReplay() {
		return collectForReplay;
	}

	public void setCollectForReplay(boolean replay) {
		this.collectForReplay = replay;
	}

	/**
	 * Set the socket and context given the address.  This is used by the triggering system. Ex: "tcp://192.168.1.101:9001"
	 */
	public void setTriggerIP(String triggerIP) {
		log.debug("setTriggerIP:"+triggerIP);
		log.debug("build context...");
		this.context   = ZMQ.zmq_init(1); //TODO - Investigate what the value of io_threads should be
		log.debug("context built");
		
		this.subscriber = ZMQ.zmq_socket(context, ZMQ.ZMQ_SUB);
		this.log.debug("subscriber socket built");
		this.subscriber.setsockopt(ZMQ.ZMQ_SUBSCRIBE, "");

		this.log.debug("connecting");
		ZMQ.zmq_connect(this.subscriber, triggerIP);
		
		this.log.debug("finished subscribe");
	}

	/**
	 * Provide the connection class to gpudb.  Used by Request
	 */
	public RequestConnection getRequestConnection() {
		return this.requestConnection;
	}

	public static GPUdb newGpudbTrigger(String ip, int port, String triggerIP, String namespace){
		GPUdb gPUdb = new GPUdb(ip, port, namespace);		
		gPUdb.setTriggerIP(triggerIP);

		return gPUdb;
	}

	public static GPUdb newGpudbTrigger(String ip, String triggerIP, String namespace){
		GPUdb gPUdb = new GPUdb(ip, namespace);		
		gPUdb.setTriggerIP(triggerIP);

		return gPUdb;
	}

	/**
	 * Allows a user to register a trigger.
	 */
	public register_trigger_range_response do_register_trigger(SetId set_id, String attribute, double lowest, double highest, String grouping_attribute) throws GPUdbException{
		List<SetId> set_ids = new ArrayList<SetId>();
		set_ids.add(set_id);		
		return this.do_register_trigger(set_ids, attribute, lowest, highest, grouping_attribute);
	}

	
	/**
	 * Allows a parent set to be marked protected
	 */
	public register_parent_set_response do_register_parent_set(SetId set_id, boolean allowDuplicateChildren) throws GPUdbException{
		this.log.debug("Do register trigger");

		// get the request
		Request request = new RegisterParentSetRequest(this, "/registerparentset", set_id, allowDuplicateChildren);

		/// decode 		
		register_parent_set_response response = (register_parent_set_response)AvroUtils.convert_to_object_from_gpudb_response(register_parent_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}


	/**
	 * Allows a user to register a range trigger.
	 */
	public register_trigger_range_response do_register_trigger(List<SetId> set_ids, String attribute, double lowest, double highest, String grouping_attribute) throws GPUdbException{
		this.log.debug("Do register trigger");

		// get the request
		Request request = this.request_factory.create_request("/registertriggerrange", set_ids, attribute, lowest, highest, grouping_attribute);

		/// decode 		
		register_trigger_range_response response = (register_trigger_range_response)AvroUtils.convert_to_object_from_gpudb_response(register_trigger_range_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Allows a user to register a NAI trigger on one setid.
	 */
	public register_trigger_nai_response do_register_trigger(SetId set_id, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector, String grouping_attribute) throws GPUdbException{
		List<SetId> set_ids = new ArrayList<SetId>();
		set_ids.add(set_id);
		return this.do_register_trigger(set_ids, x_attribute, x_vector, y_attribute, y_vector, grouping_attribute);
	}

	/**
	 * Allows a user to register a NAI trigger across several setids.
	 */
	public register_trigger_nai_response do_register_trigger(List<SetId> set_ids, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector, String grouping_attribute) throws GPUdbException{
		this.log.debug("Do register trigger");

		// get the request
		Request request = this.request_factory.create_request("/registertriggernai", set_ids, x_attribute, x_vector, y_attribute, y_vector, grouping_attribute);

		/// decode 		
		register_trigger_nai_response response = (register_trigger_nai_response)AvroUtils.convert_to_object_from_gpudb_response(register_trigger_nai_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Protected helper, recvs bytes on sock and then returns and logs the string
	 * @param sock The zmq socket to recv on
	 * @return String The string that we received
	 */
	protected String recv_string(SocketBase sock) {
		Msg reply = sock.recv(0);
		ByteBuffer bb = reply.buf();
		try {
			return new String(bb.array(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new GPUdbException(e.getMessage());
		}
	}

	/**
	 * This is blocking call that will return objects built using "type" who matched the trigger with the given "trigger_id"
	 * @param trigger_id The trigger id that this object matched/activated.
	 * @param type The type that this object is.
	 * @return The object.
	 */
	public Object do_listen_for_this_trigger(String trigger_id, Type type) throws GPUdbException{
		Schema object_schema = type.getAvroSchema();

		while(true) {
			log.debug("listening for trigger:"+trigger_id);

			String obj_string = this.recv_string(this.subscriber);

			log.debug("got something");

			log.debug("Got back encoded object of size "+obj_string.length());
			Schema type_schema = trigger_notification.SCHEMA$;
			SpecificDatumReader<trigger_notification> reader = new SpecificDatumReader<trigger_notification>(type_schema);
			DecoderFactory decoder_factory = new DecoderFactory();
			log.debug("After building decoder");
			// build the object		
			trigger_notification response = null;

			//Avro encodes bytes in JSON as \U but Java (and Python) expect \\u 
			obj_string = obj_string.replace("\\U","\\u");

			try {			
				Decoder decoder = decoder_factory.jsonDecoder(type_schema, obj_string);
				response = (trigger_notification)reader.read(null, decoder);			
			} catch(Exception e) {
				System.err.println(e.toString());
			}
			///
			log.debug("response.object_data.size():"+response.getObjectData().toString()+" response.trigger_id:"+response.getTriggerId());			

			if(response.getTriggerId().toString().equals(trigger_id)){
				log.debug("This trigger id matches");
				// decode object
				String buffer_string = response.getObjectData().toString();

				// set up the reader for this object [NOTE: this code is similar to the stuff in NS, proably pull out into some utils]
				GenericDatumReader<GenericData.Record> generic_reader = new GenericDatumReader<GenericData.Record>(object_schema);							
				GenericData.Record record = null;
				try {			
					Decoder decoder = decoder_factory.jsonDecoder(object_schema, buffer_string);
					record = generic_reader.read(null, decoder);			
					Class class_of_obj = type.getTypeClass();
					Object instance = class_of_obj.newInstance(); // we will be transfering record into this object
					if(class_of_obj == GenericObject.class) {
						log.debug("List of a generic object");
						GenericObject go = new GenericObject();
						// get the fields from the schema
						List<Schema.Field> list_of_fields = record.getSchema().getFields();
						for(Schema.Field field : list_of_fields) {
							String field_name = field.name();
							log.debug("field name:"+field_name+" value:"+record.get(field_name));
							go.addField(field_name, record.get(field_name).toString());						
						}
						instance = go;
					} else {
						// now use the record to build an object of class "class_of_obj"
						// loop through the fields of the object these correspond to keys in the genericdata record				
						Field[] fields = class_of_obj.getFields();
						for(Field field : fields) {
							String fieldType = field.getType().getSimpleName().toLowerCase(); // need to know how to set the fields
							log.debug("record.get(field.getName()):"+record.get(field.getName()).toString()+" fieldType:"+fieldType); 
							if(fieldType.equals("int")){
								Integer value = (Integer)record.get(field.getName());
								field.setInt(instance, value.intValue());
							} else if(fieldType.equals("long")){
								Long value = (Long)record.get(field.getName());
								field.setLong(instance, value.longValue());
							} else if(fieldType.equals("double")){
								Double value = (Double)record.get(field.getName());
								field.setDouble(instance, value.doubleValue());
							} else if(fieldType.equals("float")){
								Float value = (Float)record.get(field.getName());
								field.setFloat(instance, value.floatValue());
							} else if(fieldType.equals("string")){
								org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)record.get(field.getName());						
								field.set(instance, value.toString());
							} else if(fieldType.equals("bytebuffer")){
								ByteBuffer value = (ByteBuffer)record.get(field.getName());
								field.set(instance, value);
							} 									
						}					
					}
					return instance;
				} catch(Exception e) {
					System.err.println(e.toString());
					return null;
				}				
			}else {
				log.debug("This trigger id NOT matching");
			}
		}
	}	

	/**
	 * This is blocking call that will return objects who matched the trigger with the given "trigger_id".  Dynamically determine the type.  
	 * @param trigger_id The trigger id that this object matched/activated.
	 * @return The object.
	 */
	public Object do_listen_for_this_trigger(String trigger_id) throws GPUdbException{				
		while(true) {
			log.debug("listening for trigger:"+trigger_id);

			String obj_string = this.recv_string(this.subscriber);

			log.debug("got something");

			log.debug("Got back encoded object of size "+obj_string.length());
			Schema type_schema = trigger_notification.SCHEMA$;
			SpecificDatumReader<trigger_notification> reader = new SpecificDatumReader<trigger_notification>(type_schema);
			DecoderFactory decoder_factory = new DecoderFactory();
			log.debug("After building decoder");
			// build the object		
			trigger_notification response = null;

			//Avro encodes bytes in JSON as \U but Java (and Python) expect \\u 
			obj_string = obj_string.replace("\\U","\\u");

			try {			
				Decoder decoder = decoder_factory.jsonDecoder(type_schema, obj_string);
				response = (trigger_notification)reader.read(null, decoder);			
			} catch(Exception e) {
				System.err.println(e.toString());
			}
			///
			log.debug("response.object_data.size():"+ response.getObjectData().array().length   +" response.trigger_id:"+response.getTriggerId());			
						
			if(response.getTriggerId().toString().equals(trigger_id)){
				log.debug("This trigger id matches");
				
				// use the set id to get determine the type of the object
				Type type = this.getNamedSet(new SetId(response.getSetId().toString())).getType();
				Schema object_schema = type.getAvroSchema();
				
				/* THIS IS CRAZY CODE !!!
				if( response.getObjectData().array().length == 0 ) {
					return type.decodeJson(response.getObjectData(), object_schema);
				} else {
					return type.decode(response.getObjectData());
				}
				*/
				return type.decode(response.getObjectData());
			}else {
				log.debug("This trigger id NOT matching");
			}
		}
	}	
	
	/**
	 * Run a the spatial query using the passed in operation on the sets passed in. 
	 * @param set_ids The sets to perform the operation on. 
	 * @param wkt_attr_name The name of the wkt attribute that will be looked for in the sets.
	 * @param wkt_string The wkt string. If this is empty then we are doing all shapes vs. all. If it has a wkt string then we are doing all the
	 * shapes in the set vs. the wkt string passed in. 
	 * @param operation The operation to perform.
	 * @return response object that contains the result.
	 */
	public spatial_set_query_response do_spatial_set_query(List<SetId> set_ids, CharSequence wkt_attr_name, CharSequence wkt_string, SpatialOperationEnum operation) {
		this.log.debug("Do spatial query");

		// get the request
		Request request = new SpatialSetQueryRequest(this, "/spatialsetquery", set_ids, wkt_attr_name, wkt_string, operation.toString());

		/// decode 		
		spatial_set_query_response response = (spatial_set_query_response)AvroUtils.convert_to_object_from_gpudb_response(spatial_set_query_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());
		
		return response;
	}
	
	/**
	 * Private constructor. Sets common info for the other constructors. NOTE: request connection needs to be set first
	 */
	private void initialize() {
		this.request_factory = new RequestFactory(this);
		this.gson = new Gson();
		this.log = Logger.getLogger(GPUdb.class);
		this.ns_store = ClientNSStore.getInstance(this.requestConnection);
	}

	/**
	 * Constructor, builds a Gpudb object with the given IP address and port.
	 * @param ip The String IP address.
	 * @param port The port value.
	 */
	public GPUdb(String ip, int port) {
		this(ip, port, "");
	}

	/**
	 * Constructor, builds a Gpudb object with the given IP address, port, and user namespace.
	 * @param ip The String IP address.
	 * @param port The port value.
	 * @param namespace The user namespace.
	 */
	public GPUdb(String ip, int port, String namespace) throws GPUdbException{		
		this("http", ip, port, namespace);
	}
	
	public GPUdb(String protocol, String ip, int port, String namespace) throws GPUdbException{		

		// build the connection to gpudb			
		this.requestConnection = new RequestConnection(protocol, ip,port);

		// set the namespace if it's been set
		if(!namespace.equals("")){
			this.namespace = namespace;
			this.hasNamespace = true;
		}
		
		initialize();
		this.log.debug(String.format("Starting up gpudb with namespace:%s and connection:%s", this.namespace, this.requestConnection.toString()));		
	}

	//if given "http://192.168.1.101:9191" split into 192.168.1.101 and 9191
	/**
	 * Constructor, builds a Gpudb object from the given full address (IP and port). ex: Gpudb("http://192.168.1.101:9191")
	 * @param full_address The protocol://ip:port address.
	 */
	public GPUdb(String full_address) {
		this(full_address, "");
	}
	

	/**
	 * Constructor, builds a Gpudb object from the given full address (IP and port). ex: Gpudb("http://192.168.1.101:9191")
	 * @param full_address The protocol://ip:port address.
	 * @param namespace The user namespace to use.
	 */
	public GPUdb(String full_address, String namespace) throws GPUdbException{		
		try {
			this.log = Logger.getLogger(GPUdb.class); // need the logger
			
			full_address.trim();
			URL myURL = new URL(full_address);
			String protocol = myURL.getProtocol();
			String host = myURL.getHost();
			int port = myURL.getPort();
			String path = myURL.getPath();
			
			System.out.println("URL parts (protocol, host, port, path) are :" + protocol + " " + host + " " + port + " " + path);
			if( port == -1 ) { // We have no port but may have a path
				if( StringUtils.isEmpty(path) ) {
					// No path and no port - let request connection decide based on protocol
					this.requestConnection = new RequestConnection(protocol, host);
				} else {
					this.requestConnection =  new RequestConnection(protocol, host, path);
				}
				
			} else { // We may still have a port and a path
				if( StringUtils.isEmpty(path) ) {
					this.requestConnection = new RequestConnection(protocol, host, port);
				} else {
					this.requestConnection = new RequestConnection(protocol, host, path);
				}
			}
			// set the namespace if it's been set
			if(!namespace.equals("")){
				this.namespace = namespace;
				this.hasNamespace = true;
			}
			initialize();
			this.log.debug(String.format("Starting up gpudb with namespace:%s and connection:%s", this.namespace, this.requestConnection.toString()));
		} catch(Exception e) {
			System.err.println(e.toString());
			throw new GPUdbException("Error building gpudb object; "+e.toString());			
		}
	}

	/**
	 * Constructor, builds a Gpudb object given the host, baseFile, and some namespace.  Example Gpudb("gpudb.gisfederal.com", "gpudb2", "") or
	 * Gpudb("gpudb.gisfederal.com", "gpudb2", "MyNameSpace") 
	 * @param host The host.
	 * @param baseFile The base file.
	 * @param namespace The user namespace to use.
	 */
	public GPUdb(String host, String baseFile, String namespace) {
		try {
			this.log = Logger.getLogger(GPUdb.class);
			// Old code - protocol is http
			this.requestConnection =  new RequestConnection("http", host, baseFile);

			// set the namespace if it's been set
			if(!namespace.equals("")){
				this.namespace = namespace;
				this.hasNamespace = true;
			}
			initialize();
			this.log.debug(String.format("Starting up gpudb with namespace:%s and connection:%s", this.namespace, this.requestConnection.toString()));
		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * Return some type info for this set.
	 * @param set_id Set id of the set we want to check against.  
	 * @return A set info response object.
	 */
	public set_info_response do_set_info(SetId set_id) throws GPUdbException{	
		
		Request request = this.request_factory.create_request("/setinfo",set_id.get_id());
		log.debug("send info request for set id:"+set_id.get_id());

		/// decode 		
		set_info_response response = (set_info_response)AvroUtils.convert_to_object_from_gpudb_response(set_info_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());		

		return response;
	}

	/**
	 * Create a type in gpudb based on a java class.
	 * @param c The java class to create the type around
	 * @return A type object that represents this type
	 */
	public Type create_type(Class c) throws GPUdbException{
		return this.create_type(c,"", "", SemanticTypeEnum.EMPTY);
	}
	
	/**
	 * Create a type in gpudb based on a java class and an annotation 
	 * @param c
	 * @param annotation_attributes
	 * @return A type
	 * @throws GPUdbException
	 */
	public Type create_type_with_annotations(Class c, Map<CharSequence, List<CharSequence>> annotation_attributes) throws GPUdbException{
		return this.create_type_with_annotations(c, "", SemanticTypeEnum.EMPTY, annotation_attributes);
	}

	/**
	 * Create a type in gpudb based on a json type definition string.
	 * @param definition The json string that represents the type (ex: """{"type":"record","name":"point","fields":[{"name":"x","type":"double"},{"name":"y","type":"double"}]}""")
	 * @return A type object that represents this type
	 */
	public Type create_type(String definition) throws GPUdbException{
		return this.create_type(definition,"", "", SemanticTypeEnum.EMPTY);
	}

	/**
	 * Create a type in gpudb based on a json type definition string.
	 * @param definition The json string that represents the type (ex: """{"type":"record","name":"point","fields":[{"name":"x","type":"double"},{"name":"y","type":"double"}]}""")
	 * @param annotation_attr The attribute name for the security annotation (ex: "msg_id")
	 * @param label The type label.
	 * @param semanticType The semantic type (basically an enumeration).
	 * @return A type object that represents this type
	 */
	public Type create_type(String definition, String annotation_attr, String label, SemanticTypeEnum semanticType) throws GPUdbException{
		this.log.debug("Create a type");
		Request request = this.request_factory.create_request("/registertype", definition, 
					annotation_attr, label, semanticType.toString());

		/// decode 		
		register_type_response response = (register_type_response)AvroUtils.convert_to_object_from_gpudb_response(register_type_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	

		// build the avro schema
		Schema.Parser parser = new Schema.Parser();

		// create the type and return it
		return new Type(response.getTypeId().toString(), GenericObject.class, parser.parse(response.getTypeDefinition().toString()), 
				label, semanticType.toString());
	}
	

	/**
	 * Create a type in GPUDB based on a json string schema. Contains annotation attributes.
	 * @param definition
	 * @param label
	 * @param semanticType
	 * @param annotation_attributes
	 * @return A type
	 * @throws GPUdbException
	 */
	public Type create_type_with_annotations(String definition, String label, SemanticTypeEnum semanticType, 
			Map<CharSequence, List<CharSequence>> annotation_attributes) throws GPUdbException{
		this.log.debug("Create a type with annotations");
		
		Request request = new CreateTypeWithAnnotationsRequest(this, "/registertypewithannotations", definition, label, 
				semanticType.toString(), annotation_attributes);

		/// decode 		
		register_type_with_annotations_response response = (register_type_with_annotations_response)AvroUtils.convert_to_object_from_gpudb_response(
				register_type_with_annotations_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	

		// build the avro schema
		Schema.Parser parser = new Schema.Parser();

		// create the type and return it
		return new Type(response.getTypeId().toString(), GenericObject.class, parser.parse(response.getTypeDefinition().toString()), 
				label, semanticType.toString());
	}


	/**
	 * Create a type in gpudb based on a java class and given the security annotation.
	 * @param c The java class to create the type around
	 * @param annotation_attr The attribute name for the security annotation (ex: "msg_id")
	 * @param label The type label.
	 * @param semanticType The semantic type (basically an enumeration). 
	 * @return A type object that represents this type
	 */
	public Type create_type(Class c, String annotation_attr, String label, SemanticTypeEnum semanticType) throws GPUdbException{
		this.log.debug("Create a type");
		Request request = this.request_factory.create_request("/registertype", c, annotation_attr, label, semanticType.toString());

		/// decode 		
		register_type_response response = (register_type_response)AvroUtils.convert_to_object_from_gpudb_response(register_type_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	
		
		// build the avro schema
		Schema.Parser parser = new Schema.Parser();

		// create the type and return it
		return new Type(response.getTypeId().toString(), c, parser.parse(response.getTypeDefinition().toString()), label, semanticType.toString());		
	}
	
	public Type create_type_with_annotations(Class c, String label, SemanticTypeEnum semanticType, 
			Map<CharSequence, List<CharSequence>> annotation_attributes) throws GPUdbException{
		this.log.debug("Create a type with annotations");
		
		Request request = new CreateTypeWithAnnotationsRequest(this, "/registertypewithannotations", c, label, 
				semanticType.toString(), annotation_attributes);


		/// decode 		
		register_type_with_annotations_response response = (register_type_with_annotations_response)AvroUtils.convert_to_object_from_gpudb_response(
				register_type_with_annotations_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	
		
		// build the avro schema
		Schema.Parser parser = new Schema.Parser();

		// create the type and return it
		return new Type(response.getTypeId().toString(), c, parser.parse(response.getTypeDefinition().toString()), label, semanticType.toString());		
	}


	/**
	 * Build an image from the points in multiple sets 
	 * @param in_sets The sets to generate the image from
	 * @param colors The color for each set
	 * @param x_attribute The name of the "x" attribute.
	 * @param y_attribute The name of the "y" attribute.
	 * @param min_x The min value of the the "x" attribute.
	 * @param max_x The max value of the the "x" attribute.
	 * @param min_y The min value of the the "y" attribute.
	 * @param max_y The max value of the the "y" attribute.
	 * @param width The width.
	 * @param height The height.
	 * @param projection The map projection (mercator, etc.)
	 * @param bg_color The background color.
	 * @return A plot2d_multiple_response object. Contains a PNG-encoded image as a byte string.
	 */
	public plot2d_multiple_response do_plot2d_multiple(List<NamedSet> in_sets, List<Long> colors, List<Integer> sizes, String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y, double width, double height, String projection, long bg_color) throws GPUdbException{		
		this.log.debug("Do Plot2DMultiple");
		
		List<CharSequence> in_set_names = new ArrayList<CharSequence>();
		for (int i=0;i<in_sets.size();i++)
		{
			in_set_names.add(in_sets.get(i).get_setid().get_id());
		}

		// get the request
		Request request = this.request_factory.create_request("/plot2dmultiple", in_set_names, colors, sizes, x_attribute, y_attribute, min_x, max_x, min_y, max_y, width, height, projection, 
				bg_color);

		/// decode 		
		plot2d_multiple_response response = (plot2d_multiple_response)AvroUtils.convert_to_object_from_gpudb_response(plot2d_multiple_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	


		return response;		
	}

	/**
	 * Calculate what objects are within a bounding box.  Objects who are within bounds min_x <= x <= max_x AND min_y <= y <= max_y. 
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects -- i.e. those that are within the box.
	 * @param x_attribute The name of the "x" attribute.
	 * @param y_attribute The name of the "y" attribute.
	 * @param min_x The min value of the the "x" attribute.
	 * @param max_x The max value of the the "x" attribute.
	 * @param min_y The min value of the the "y" attribute.
	 * @param max_y The max value of the the "y" attribute.
	 * @return A bounding box response object. Contains the count of the resulting set.
	 */
	public bounding_box_response do_bounding_box(NamedSet in_set, SetId result_set_id, String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y) throws GPUdbException{		
		this.log.debug("Do bounding box");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());		
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object


		// get the request
		Request request = this.request_factory.create_request("/boundingbox", in_set.get_setid(), result_set_id, x_attribute, y_attribute, min_x, max_x, min_y, max_y);

		/// decode 		
		bounding_box_response response = (bounding_box_response)AvroUtils.convert_to_object_from_gpudb_response(bounding_box_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	
		
		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}
		
		return response;		
	}

	/**
	 * Calculate what objects match the select expression. 
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects -- i.e. those that match the expression.
	 * @param expression The expression string.
	 * @return A select response object. Contains the count of the resulting set.
	 */
	public select_response do_select(NamedSet in_set, SetId result_set_id, String expression) throws GPUdbException{		
		this.log.debug("Do select");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		Request request = this.request_factory.create_request("/select", in_set.get_setid(), result_set_id, expression);

		/// decode 		
		select_response response = (select_response)AvroUtils.convert_to_object_from_gpudb_response(select_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;		
	}

	/**
	 * Objects that matched the select expression will be deleted 
	 * @param in_set The set to perform the calculation on.
	 * @param expression The expression string.
	 * @return The count of affected objects.
	 */
	public long do_select_delete(NamedSet in_set, String expression) throws GPUdbException{		
		this.log.debug("Do select delete");

		// get the request
		Request request = new SelectDeleteRequest(this, "/selectdelete", in_set, expression);

		/// decode 		
		select_delete_response response = (select_delete_response)AvroUtils.convert_to_object_from_gpudb_response(select_delete_response.SCHEMA$, request.post_to_gpudb(true));		
		log.debug("response:"+response.toString());

		return response.getCount();		
	}

	/**
	 * Given a set and an attribute, find the min and max values for that attribute for the objects in that set.  
	 * @param in_set The set to perform the calculation on.
	 * @param attribute The attribute whose min and max values we are looking for.
	 * @return A max min response object. Contains the max and min values.
	 */
	public max_min_response do_max_min(NamedSet in_set, String attribute) throws GPUdbException{
		this.log.debug("Do max min");
		Request request = this.request_factory.create_request("/maxmin", in_set.get_setid(), attribute);

		/// decode 		
		max_min_response response = (max_min_response)AvroUtils.convert_to_object_from_gpudb_response(max_min_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}
	
	/**
	 * Find the unique values of a specified attribute
	 * @param attribute - attribute of the set for which unique values are sought
	 */
	public unique_response do_unique(NamedSet in_set, String attribute) {
		this.log.debug("Do Unique");
		Request request = this.request_factory.create_request("/unique", in_set.get_setid(), attribute);
		unique_response response = (unique_response)AvroUtils.convert_to_object_from_gpudb_response(unique_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());
		return response;	
	}
	
	/**
	 * Given a set, x and y attribute, this function calculates the convex set for the points in the set.  
	 * @param in_set The set to perform the convex hull on.
	 * @param x_attribute The x_attribute that we are going to be using to make the hull.
	 * @param y_attribute The y_attribute that we are going to be using to make the hull.
	 * @return A convex_hull response object.  Contains the points on the hull.
	 */	
	public convex_hull_response do_convex_hull(NamedSet in_set, String x_attribute, String y_attribute) throws GPUdbException{
		this.log.debug("Do convex hull");

		// create request
		Request request = this.request_factory.create_request("/convexhull", in_set.get_setid(), x_attribute, y_attribute);

		/// decode 		
		convex_hull_response response = (convex_hull_response)AvroUtils.convert_to_object_from_gpudb_response(convex_hull_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}



	/**
	 * Given a set, an attribute, and an interval perform a histogram calculation.  The interval is used to produce bins of that size and 
	 * we return a count of the number of objects within those bins.  The first bin is the multiplicity of the set min for that attribute.  
	 * @param in_set The set to perform the histogram calculation on.
	 * @param attribute The attribute that we are going to being using to make the bins.
	 * @param interval The size of the bins.
	 * @return A histogram response object.  Contains the counts for each bin.
	 */	
	public histogram_response do_histogram(NamedSet in_set, String attribute, long interval, Map<CharSequence, 
			CharSequence> params) throws GPUdbException{
		this.log.debug("Do histogram");
		// NOTE: the histogram requires a "start" and "end" which are really the min and max; so we find that first
		max_min_response maxmin_response = do_max_min(in_set, attribute);

		double start = maxmin_response.getMin();
		double end = maxmin_response.getMax();
		log.debug("Build the histogram request; min (i.e. start):"+start+" max (i.e. end):"+end);		
		if((start+interval) > end) {
			// in this situation we wouldn't be forming any bins, so adjust the end to be large enough to form just one bin
			end = start+interval;
			log.debug("New end constructed:"+end);
		}

		return do_histogram(in_set, attribute, params, interval, start, end);
	}

	/**
	 * Given a set, an attribute, and an interval perform a histogram calculation.  The interval is used to produce bins of that size and 
	 * we return a count of the number of objects within those bins.  The first bin is the multiplicity of the set min for that attribute.  
	 * @param in_set The set to perform the histogram calculation on.
	 * @param attribute The attribute that we are going to being using to make the bins.
	 * @param interval The size of the bins.
	 * @param start The start index.
	 * @param end The end index. 
	 * @return A histogram response object.  Contains the counts for each bin.
	 */	
	public histogram_response do_histogram(NamedSet in_set, String attribute, Map<CharSequence, CharSequence> params,
			double interval, double start, double end) throws GPUdbException{
		this.log.debug("Do histogram");
		
		// create request
		Request request = new HistogramRequest(this, "/histogram", in_set.get_setid(), attribute, interval, start, end, params);
		
		/// decode 		
		histogram_response response = (histogram_response)AvroUtils.convert_to_object_from_gpudb_response(histogram_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}
	
	/**
	 * Perform a filter on the given set.  This filter will result in a separate temporary set for each element in the filter.  For each of these
	 * sets perform a histogram. The interval is used to produce bins of that size and we return a count of the number of objects within those bins.  
	 * The first bin is the multiplicity of the set min for that attribute.  
	 * @param in_set The set to perform the histogram calculation on.
	 * @param filter_attribute The attribute that we are going to being using to make the bins.
	 * @param filter The list of values to filter on.
	 * @param histogram_attribute The attribute that we are going to being using to make the bins.
	 * @param interval The size of the bins.
	 * @param start The start index.
	 * @param end The end index. 
	 * @return A filter then histogram response object.  A list of histograms.
	 * @throws GpudbException.
	 */	
	public filter_then_histogram_response do_filter_then_histogram(NamedSet in_set, String filter_attribute, List<CharSequence> filter, String histogram_attribute, long interval, double start, double end) throws GPUdbException{
		this.log.debug("Do filter then histogram");
		// NOTE: the histogram requires a "start" and "end" which are really the min and max; so we find that first
		max_min_response maxmin_response = do_max_min(in_set, histogram_attribute);

		// create request
		Request request = this.request_factory.create_request("/filterthenhistogram", in_set.get_setid(), filter_attribute, filter, histogram_attribute, interval, start,end);

		/// decode 		
		filter_then_histogram_response response = (filter_then_histogram_response)AvroUtils.convert_to_object_from_gpudb_response(filter_then_histogram_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());
		
		return response;
	}
	
	/**
	 * Perform a filter on the given set.  This filter will result in a separate temporary set for each element in the filter.  For each of these
	 * sets perform a histogram. The interval is used to produce bins of that size and we return a count of the number of objects within those bins.  
	 * The first bin is the multiplicity of the set min for that attribute.  
	 * @param in_set The set to perform the histogram calculation on.
	 * @param filter_attribute The attribute that we are going to being using to make the bins.
	 * @param filter The list of values to filter on.
	 * @param histogram_attribute The attribute that we are going to being using to make the bins.
	 * @param interval The size of the bins.
	 * @return A filter then histogram response object.  A list of histograms.
	 * @throws GpudbException.
	 */	
	public filter_then_histogram_response do_filter_then_histogram(NamedSet in_set, String filter_attribute, List<CharSequence> filter, String histogram_attribute, long interval) throws GPUdbException{
		this.log.debug("Do filter then histogram");
		// NOTE: the histogram requires a "start" and "end" which are really the min and max; so we find that first
		max_min_response maxmin_response = do_max_min(in_set, histogram_attribute);

		double start = maxmin_response.getMin();
		double end = maxmin_response.getMax();
		log.debug("Build the histogram request; min (i.e. start):"+start+" max (i.e. end):"+end);		
		if((start+interval) > end) {
			// in this situation we wouldn't be forming any bins, so adjust the end to be large enough to form just one bin
			end = start+interval;
			log.debug("New end constructed:"+end);
		}

		// create request
		Request request = this.request_factory.create_request("/filterthenhistogram", in_set.get_setid(), filter_attribute, filter, histogram_attribute, interval, start,end);

		/// decode 		
		filter_then_histogram_response response = (filter_then_histogram_response)AvroUtils.convert_to_object_from_gpudb_response(filter_then_histogram_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());
		
		return response;
	}

	/**
	 * Calculate which objects from a set have an attribute that is within the given bounds.  An object from the in_set ends up in the 
	 * result_set (set with SetId of result_set_id) if its attribute is within [lower_bounds, upper_bounds]  
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param attribute The name of the attribute that we going to test the bounds on.
	 * @param lower_bounds The lower bound for the given attribute.
	 * @param upper_bounds The upper bound for the given attribute.
	 * @return A filter by bounds response object. Contains the count of the resulting set.
	 */
	public filter_by_bounds_response do_filter_by_bounds(NamedSet in_set, SetId result_set_id, String attribute, double lower_bounds, double upper_bounds) throws GPUdbException{
		this.log.debug("Do filter by bounds");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbybounds", in_set.get_setid(), result_set_id,attribute, lower_bounds, upper_bounds);

		/// decode 		
		filter_by_bounds_response response = (filter_by_bounds_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_bounds_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;
	}

	/**
	 * Find each unique combination of the given attributes for the given in_set and return the count for each combination.  
	 * @param in_set The set to perform the calculation on.
	 * @param attributes A list of attribute names.
	 * @return A group by response object.  Contains a map where each key is a unique combination of the attributes and the value is a list of the 
	 * count of the number of objects who had that combination and the second value is a result set id.
	 */
	public group_by_response do_group_by(NamedSet in_set, List<String> attributes) throws GPUdbException{
		this.log.debug("Do group by");

		Request request = this.request_factory.create_request("/groupby", in_set.get_setid(), attributes);

		/// decode 		
		group_by_response response = (group_by_response)AvroUtils.convert_to_object_from_gpudb_response(group_by_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	// Accepts a value_attribute field in computing the sum of the value_attribute field. 
	// If the value_attribute is "" then it behaves like do_group_by and returns counts of group_by fields
	public group_by_value_response do_group_by_value(NamedSet namedSet, List<String> attributes, String value_attribute) throws GPUdbException{
		this.log.debug("Do group by");

		Request request = this.request_factory.create_request("/groupbyvalue", namedSet.get_setid(), attributes, value_attribute);

		/// decode 		
		group_by_value_response response = (group_by_value_response)AvroUtils.convert_to_object_from_gpudb_response(group_by_value_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Build a bloom filter on the given attribute for the given set.  Used in sets that have joins performed on them.  
	 * @param in_set The set to perform the bloom build on.
	 * @param attribute The attribute to build the bloom on
	 * @return A make bloom response object.
	 */
	public make_bloom_response do_make_bloom(NamedSet in_set, String attribute) throws GPUdbException{
		this.log.debug("Do make bloom");

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/makebloom", in_set.get_setid(), attribute);

		/// decode 		
		make_bloom_response response = (make_bloom_response)AvroUtils.convert_to_object_from_gpudb_response(make_bloom_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Calculate which objects from a set are within a NAI (polygon).  
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param x_attribute The name of the x-attribute
	 * @param x_vector The list of x values.
	 * @param y_attribute The name of the y-attribute
	 * @param y_vector The list of y values.
	 * @return A filter by nai response object. Contains the count of the resulting set.
	 */
	public filter_by_nai_response do_filter_by_nai(NamedSet in_set, SetId result_set_id, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector) throws GPUdbException{
		this.log.debug("Do filter by NAI");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbynai", in_set.get_setid(), result_set_id, x_attribute, x_vector, y_attribute, y_vector);

		/// decode 		
		filter_by_nai_response response = (filter_by_nai_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_nai_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;
	}

	/**
	 * Calculate which objects from a set are within a radius of a given center point (i.e. circular NAI)  
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param x_attribute The name of the x-attribute
	 * @param y_attribute The name of the y-attribute
	 * @param x_center The center point x coordinate (i.e. longitude)
	 * @param y_center The center point y coordinate (i.e. latitude)
	 * @param radius The search radius (in meters)
	 * @return A filter by nai response object. Contains the count of the resulting set.
	 */
	public filter_by_radius_response do_filter_by_radius(NamedSet in_set, SetId result_set_id, String x_attribute, String y_attribute, Double x_center, Double y_center, Double radius) throws GPUdbException{
		this.log.debug("Do filter by Radius");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbyradius", in_set.get_setid(), result_set_id, x_attribute, y_attribute, x_center, y_center, radius);

		/// decode 		
		filter_by_radius_response response = (filter_by_radius_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_radius_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;
	}
	

	/**
	 * @param in_set The set to perform the filter on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param expression filtering condition
	 * @param mode Filter by: [contains, starts, equals or regex]
	 * @param options Optional case sensitivity for filtering
	 * @param attributes Filter on which attributes
	 * @return A filter by string response object. Contains the count of the resulting set.
	 */
	public filter_by_string_response do_filter_by_string(NamedSet in_set, SetId result_set_id, String expression, String mode, List<CharSequence> options, List<CharSequence> attributes) {
		this.log.debug("Do Filter By String");
		
		// create a named set object and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs);

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbystring", in_set.get_setid(), result_set_id, expression, mode, options, attributes);
		filter_by_string_response response = (filter_by_string_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_string_response.SCHEMA$, request.post_to_gpudb());
		log.debug("response:"+response.toString());
		
		return response;		
	}
	
	//String file, SetId in_set, SetId result_set_id, boolean isString, Double value, String value_string, String attribute
	public filter_by_value_response do_filter_by_value(NamedSet in_set, SetId result_set_id, boolean isString, Double value, 
			String value_string, String attribute) throws GPUdbException{
		this.log.debug("Do filter by Radius");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbyvalue", in_set.get_setid(), result_set_id, isString, 
				value, value_string, attribute);

		/// decode 		
		filter_by_value_response response = (filter_by_value_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_value_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;
	}

	


	/**
	 * After performing a group by we can store the objects in separate sets based upon their value for the given attribute.  
	 * @param in_set The set that we performed the group by on.
	 * @param attribute The attribute that we grouped on.
	 * @param group_map The map returned by the group by.
	 * @return A store group by response object.
	 */	
	public store_group_by_response do_store_group_by(NamedSet in_set, String attribute, Map<CharSequence, List<CharSequence>> group_map) throws GPUdbException{
		this.log.debug("Do store group by");

		// need to construct and store a named set for each of the keys/values in the map
		Iterator<CharSequence> iter = group_map.keySet().iterator();
		while(iter.hasNext()) {
			try {
				// grab the result set id for this key; convert to string; and build a new named set around it
				org.apache.avro.util.Utf8 utf_rs_id = (org.apache.avro.util.Utf8)group_map.get(iter.next()).get(1);
				SetId rs_id = new SetId(utf_rs_id.toString());
				NamedSet rs = new NamedSet(rs_id, this, in_set.getType());
				this.ns_store.put(rs_id, rs); // need to be able to retrieve this named set object
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}

		Request request = this.request_factory.create_request("/storegroupby", in_set.get_setid(), attribute, group_map, false, "");

		//decode
		store_group_by_response response = (store_group_by_response)AvroUtils.convert_to_object_from_gpudb_response(store_group_by_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}
	
	/**
	 * This method will return the remaining TTL of a set. For a master set, it will return the minimum of the children set TTL
	 * @param set - The setid for which the TTL is needed
	 * @return - TTL as an integer
	 */
	public int getSetTTL(NamedSet set) {
		int resultTTL = Integer.MAX_VALUE;
		set_info_response response = this.do_set_info(set.get_setid());
		List<Integer> ttls = response.getTtls();
		for(Integer ttl : ttls) {
			resultTTL = Math.min(resultTTL, ttl);
		}
		return resultTTL;
	}

	/**
	 * After performing a group by we can store the objects in separate sets based upon their value for the given attribute.  This calls also sorts
	 * the sets as it's storing them.  
	 * @param in_set The set that we performed the group by on.
	 * @param attribute The attribute that we grouped on.
	 * @param group_map The map returned by the group by.
	 * @param sort_attribute The attribute that we are going to sort on.
	 * @return A store group by response object.
	 */	
	public store_group_by_response do_store_group_by(NamedSet in_set, String attribute, Map<CharSequence, List<CharSequence>> group_map, String sort_attribute) throws GPUdbException{
		this.log.debug("Do store group by");

		// need to construct and store a named set for each of the keys/values in the map
		Iterator<CharSequence> iter = group_map.keySet().iterator();
		while(iter.hasNext()) {
			try {
				// grab the result set id for this key; convert to string; and build a new named set around it
				org.apache.avro.util.Utf8 utf_rs_id = (org.apache.avro.util.Utf8)group_map.get(iter.next()).get(1);
				SetId rs_id = new SetId(utf_rs_id.toString());
				NamedSet rs = new NamedSet(rs_id, this, in_set.getType());
				// since they are going to be sorted sets
				this.ns_store.put(rs_id, rs); // need to be able to retrieve this named set object
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		}

		Request request = this.request_factory.create_request("/storegroupby", in_set.get_setid(), attribute, group_map, true, sort_attribute);

		//decode
		store_group_by_response response = (store_group_by_response)AvroUtils.convert_to_object_from_gpudb_response(store_group_by_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Calculate which objects from a set have attributes whose values are in a given list. This is a wrapper around the other filter by
	 * list call.  
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param attribute The attribute to compare.
	 * @return A filter by list response object. Contains the count of the resulting set.
	 */
	public filter_by_list_response do_filter_by_list(NamedSet in_set, SetId result_set_id, String attribute, CharSequence value) throws GPUdbException{
		return this.do_filter_by_list(in_set, result_set_id, attribute, Arrays.asList(value));
	}

	/**
	 * Calculate which objects from a set have attributes whose values are in a given list. This is a wrapper around the other filter by
	 * list call.  
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param attribute The attribute to compare.
	 * @param filter The filter values for this attribute
	 * @return A filter by list response object. Contains the count of the resulting set.
	 */
	public filter_by_list_response do_filter_by_list(NamedSet in_set, SetId result_set_id, String attribute, List<CharSequence> values) throws GPUdbException{
		Map<CharSequence, List<CharSequence>> attribute_map = new HashMap<CharSequence, List<CharSequence>>();
		attribute_map.put(attribute, values);		

		return this.do_filter_by_list(in_set, result_set_id, attribute_map);
	}
	
	/**
	 * Calculate which objects from a set have attributes whose values are in a given list.  
	 * @param in_set The set to perform the calculation on.
	 * @param result_set_id The set that will contain the resulting objects.  A subset of the in_set.
	 * @param attribute_map The map of attributes and values the attributes must be.
	 * @return A filter by list response object. Contains the count of the resulting set.
	 */
	public filter_by_list_response do_filter_by_list(NamedSet in_set, SetId result_set_id, Map<CharSequence, List<CharSequence>> attribute_map) throws GPUdbException{
		this.log.debug("Do filter by list");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbylist", in_set.get_setid(), result_set_id, attribute_map);

		//decode
		filter_by_list_response response = (filter_by_list_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_list_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;
	}

	/**
	 * Calculate which objects from a set have attributes whose values are in a particular attribute in another set.
	 * @param in_set - The set on which we are doing the ops - could be parent or child
	 * @param result_set_id - resultant set id
	 * @param attribute - attribute name in the set on which the filter is being done
	 * @param source_setId - the setid of the set where the data items to filter by resides
	 * @param source_attribute - the name of the attribute in the source set which contains the attributes
	 * @return - response
	 * @throws GPUdbException
	 */
	public filter_by_set_response do_filter_by_set(NamedSet in_set, SetId result_set_id, 
			String attribute, SetId source_setId, String source_attribute) throws GPUdbException{
		this.log.debug("Do filter by set");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(result_set_id, this, in_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/filterbyset", in_set.get_setid(), result_set_id, attribute, source_setId, source_attribute);

		//decode
		filter_by_set_response response = (filter_by_set_response)AvroUtils.convert_to_object_from_gpudb_response(filter_by_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}

		return response;
	}

	
	public predicate_join_response do_dynamic_duo(NamedSet ns, String x_attribute, String y_attribute, String t_attribute, String track_attribute, double distance, double time_threshold, SetId result_set_id) throws GPUdbException{		
		if(ns.getType().isParent()) {
			log.debug("This is a parent");// finding an appropriate child of track type
			Collection<NamedSet> children = ns.getChildren();
			Iterator iter = children.iterator();
			while(iter.hasNext()) {
				NamedSet child = (NamedSet)iter.next();				
				if(child.getType().getSemanticType().equals(SemanticTypeEnum.TRACK.toString())) {
					log.debug("got the child of type track; child id:"+child.get_setid().get_id());
					ns = child;
					break; // just find one
				}
			}
		} else if(!ns.getType().getSemanticType().equals(SemanticTypeEnum.TRACK.toString())) {
			log.error("Not of type track");
			// IS THIS REQUIRED?
			//throw new GpudbException("This set's type isn't of type track");
		}
		
		return this.do_predicate_join(ns, ns, ns.getType().getID(), UUID.randomUUID().toString(), result_set_id, 
					"((dist(LEFT."+x_attribute+",LEFT."+y_attribute+",RIGHT."+x_attribute+",RIGHT."+y_attribute+") < "+distance+")"+
					" and (LEFT."+track_attribute+" < RIGHT."+track_attribute+")"+
					" and (abs(LEFT."+t_attribute+" - RIGHT."+t_attribute+") < "+time_threshold+") )");
	}
	
	/**
	 * Join based upon a predicate, not just equality.  Takes a left and right side.  The common type to put these sides into.  The resulting type and ID.
	 * @param left_set Left Set
	 * @param right_set Right Set
	 * @param commonType The common type.
	 * @param resultType The resulting type.
	 * @param resultID The ID of the resulting joined set.
	 * @param predicate The predicate expression. 
	 * @return A predicate_join_response object.
	 */
	public predicate_join_response do_predicate_join(NamedSet left_set, NamedSet right_set, String commonType, String resultType, SetId resultID, String predicate){
		this.log.debug("Do predicate join");

		// get the request
		log.debug("Build the request");

		Request request = new PredicateJoinRequest(this, "/predicatejoin", left_set.get_setid(), right_set.get_setid(), commonType, resultType, resultID, predicate);

		//decode
		predicate_join_response response = (predicate_join_response)AvroUtils.convert_to_object_from_gpudb_response(predicate_join_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// build the result set over here; get the schema for it from gpudb
		set_info_response si_response = this.do_set_info(resultID);

		// build the avro schema
		Schema.Parser parser = new Schema.Parser();
		log.debug("Before Join type built; type schema:"+si_response.getTypeSchemas().get(0).toString());
		try {
			Schema schema = parser.parse(si_response.getTypeSchemas().get(0).toString());
			log.debug("Built schema");
			//Type joined_type = new Type(si_response.getTypeId().toString(), GenericObject.class, parser.parse(si_response.getTypechema().toString()));
			Type joined_type = new Type(si_response.getTypeIds().get(0).toString(), GenericObject.class, schema);
			log.debug("Join type built");

			// build a named set object
			NamedSet rs = new NamedSet(resultID, this, joined_type);
			this.ns_store.put(resultID, rs); // need to be able to retrieve this named set object		
		} catch(Exception e){
			log.error(e.toString());
		}

		log.debug("Joined finished; returning");


		return response;
	}	
	
	
	/**
	 * Populate full tracks will get the track points for a track which falls outside of the passed in set (all the way to world set)
	 * @param inSet
	 * @param worldSet
	 * @param resultID
	 * @return response - count available 
	 */
	public populate_full_tracks_response do_populate_full_tracks(NamedSet inSet, NamedSet worldSet, SetId resultID){
		this.log.debug("Do populate full tracks");

		// get the request
		log.debug("Build the request");

		Request request = new PopulateFullTracksRequest(this, "/populatefulltracks", inSet.get_setid(), worldSet.get_setid(), resultID);

		//decode
		populate_full_tracks_response response = (populate_full_tracks_response)AvroUtils.convert_to_object_from_gpudb_response(
				populate_full_tracks_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());
		
		return response;

	}
	/**
	 * Calculate the join of two sets on two given attributes.  
	 * @param left_set The left set.
	 * @param left_attribute The left attribute.
	 * @param right_set The right set.
	 * @param right_attribute The right attribute.
	 * @param result_type Name of the resulting join type.
	 * @param result_set_id The set id of the resulting set.
	 * @return A join response object. Contains the count of the resulting set.
	 */
	public join_response do_join(NamedSet left_set, String left_attribute, NamedSet right_set, String right_attribute, String result_type, SetId result_set_id) throws GPUdbException{
		this.log.debug("Do join");

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/join", left_set.get_setid(), left_attribute, right_set.get_setid(), right_attribute, result_type, result_set_id);

		//decode
		join_response response = (join_response)AvroUtils.convert_to_object_from_gpudb_response(join_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// build the result set over here; get the schema for it from gpudb
		set_info_response si_response = this.do_set_info(result_set_id);

		// build the avro schema
		Schema.Parser parser = new Schema.Parser();
		log.debug("Before Join type built; type schema:"+si_response.getTypeSchemas().get(0).toString());
		try {
			Schema schema = parser.parse(si_response.getTypeSchemas().get(0).toString());
			log.debug("Built schema");
			//Type joined_type = new Type(si_response.getTypeId().toString(), GenericObject.class, parser.parse(si_response.getTypechema().toString()));
			Type joined_type = new Type(si_response.getTypeIds().get(0).toString(), GenericObject.class, schema);
			log.debug("Join type built");

			// build a named set object
			NamedSet rs = new NamedSet(result_set_id, this, joined_type);
			this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object		
		} catch(Exception e){
			log.error(e.toString());
		}

		log.debug("Joined finished; returning");

		return response;
	}

	/**
	 * This is the first step in an incremental join.  It will figure out how many objects from the left set have a matching object in the 
	 * right set.  All objects like that are stored in the subset.  Later, one can join an index from this subset (one object) with all the
	 * objects in the right set.    
	 * @param left_set The left set.
	 * @param left_attr The left attribute.
	 * @param right_set The right set.
	 * @param right_attr The right attribute.
	 * @param subset The set id of the subset of the left set that has matches in the right set.
	 * @return A join setup response object. Contains the count of the resulting set.
	 */
	public join_setup_response do_join_setup(NamedSet left_set, String left_attr, NamedSet right_set, String right_attr, SetId subset) throws GPUdbException{
		this.log.debug("Do join setup");

		// create a named set object over here and store it
		NamedSet rs = new NamedSet(subset, this, left_set.getType());
		this.ns_store.put(subset, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/joinsetup", left_set.get_setid(), left_attr, right_set.get_setid(), right_attr, subset);

		//decode
		join_setup_response response = (join_setup_response)AvroUtils.convert_to_object_from_gpudb_response(join_setup_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * The next step in the incremental join. Join together the object in the left subset at the given index with all the objects in the 
	 * right side that match. Run after the join setup.    
	 * @param left_subset The subset of the left side that have matches on the right side
	 * @param left_attr The left attribute.
	 * @param left_index The left index, indicates what object to use in the left subset.
	 * @param right_set The right set.
	 * @param right_attr The right attribute.
	 * @param result_type The name for the resulting join type.
	 * @param result_set The setid of the resulting joined set.
	 * @return A join incremental response object. Contains the count of the resulting set.
	 */
	public join_incremental_response do_join_incremental(NamedSet left_subset, String left_attr, int left_index, NamedSet right_set, String right_attr, String result_type, SetId result_set) throws GPUdbException{
		this.log.debug("Do join incremental");

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/joinincremental", left_subset.get_setid(), left_attr, left_index, right_set.get_setid(), right_attr, result_type, result_set);

		//decode
		join_incremental_response response = (join_incremental_response)AvroUtils.convert_to_object_from_gpudb_response(join_incremental_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString()+" now get the schema information for the joined set");

		// build the result set over here; get the schema for it from gpudb
		if(!this.ns_store.contains(result_set)) {
			set_info_response si_response = this.do_set_info(result_set);
			// build the avro schema
			Schema.Parser parser = new Schema.Parser();
			Type joined_type = new Type(si_response.getTypeIds().get(0).toString(), GenericObject.class, parser.parse(si_response.getTypeSchemas().get(0).toString()));
			log.debug("Built the joined_type");

			// build a named set object
			NamedSet rs = new NamedSet(result_set, this, joined_type);
			this.ns_store.put(result_set, rs); // need to be able to retrieve this named set object
		}

		log.debug("Returning from join incremental");
		return response;
	}
	
	/**
     * Merge 2 or more sets into a single resultant set.
     * @param in_sets Collection of sets to merge
     * @param commonType The common type.
     * @param resultID The ID of the resulting joined set.
     * @return A merged_sets_response object.
     */
    public merge_sets_response do_merge_sets(List<SetId> in_sets, Type commonType, SetId result_set_id){
        this.log.debug("Do merge sets");
        
        // create a named set object over here and store it
        NamedSet rs = new NamedSet(result_set_id, this, commonType);        
        this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

        // get the request
        log.debug("Build the request");

        Request request = new MergeSetsRequest(this, "/mergesets", in_sets, commonType.getID(), result_set_id);

        //decode
        merge_sets_response response = (merge_sets_response)AvroUtils.convert_to_object_from_gpudb_response(merge_sets_response.SCHEMA$, request.post_to_gpudb());        
        log.debug("response:"+response.toString());
        return response;
    }


	/**
	 * Copy one set into another.  Mostly used to move a joined set into another one.  
	 * @param original_set The set that are we are going to make a copy of.
	 * @param new_set_id The set id of the new resulting set.
	 * @param new_set_type The type of the new set.
	 * @param selector Either empty if not copying from a joined set or "RIGHT" or "LEFT" if its a joined set.
	 * @return A copy set response object.
	 */
	@Deprecated
	public copy_set_response do_copy_set(NamedSet original_set, SetId new_set_id, Type new_set_type, String selector) throws GPUdbException{
		this.log.debug("Do copy set; original_set:"+original_set.get_setid().get_id()+" new_set_id:"+new_set_id.get_id());

		// create a named set object over here
		NamedSet rs = new NamedSet(new_set_id, this, new_set_type);		
		this.ns_store.put(new_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/copyset", original_set.get_setid(), new_set_id, new_set_type.getID(), selector);

		//decode
		copy_set_response response = (copy_set_response)AvroUtils.convert_to_object_from_gpudb_response(copy_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Figure out the clustering.  Basically, we unique on the shared_attribute of the subworld set, then do a filter by list using that 
	 * unique list across the world set. And then do a group by on the cluster attribute on the world set.  
	 * @param world_set The set of the world.
	 * @param subworld_set A set that is a subset of the world.
	 * @param shared_attribute The attribute that is shared between the world and subworld that we want to unique and filter with.
	 * @param cluster_attribute The attribute in the world set that we are going to cluster on.
	 * @return A cluster response object.
	 */
	public cluster_response do_cluster(NamedSet world_set, NamedSet subworld_set, SetId result_set_id, String shared_attribute, String cluster_attribute) throws GPUdbException{
		this.log.debug("Do cluster set; world_set:"+world_set.get_setid().get_id()+" subworld_id:"+subworld_set.get_setid().get_id()+" rs:"+result_set_id.get_id()+" shared_attribute:"+shared_attribute+" cluster_attribute:"+cluster_attribute);

		// create a named set object over here
		NamedSet rs = new NamedSet(result_set_id, this, world_set.getType());
		this.ns_store.put(result_set_id, rs); // need to be able to retrieve this named set object

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/cluster", world_set.get_setid(), subworld_set.get_setid(), result_set_id, shared_attribute, cluster_attribute);

		//decode
		cluster_response response = (cluster_response)AvroUtils.convert_to_object_from_gpudb_response(cluster_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		// get children of the result set if it's a parent
		if(rs.getType().isParent()) {
			rs.getChildrenFromServer();
			log.debug("got children from rs:"+rs.getNumberOfChildren());
		}


		return response;
	}


	/**
	 * Clear out all the sets in gpudb.  
	 * @return A clear response object.
	 */
	public clear_response do_clear() throws GPUdbException {
		// need to clear all sets on this side; empty out store
		this.ns_store.clear();
		Request request = new ClearRequest(this, "/clear", null, null);

		//decode
		clear_response response = (clear_response)AvroUtils.convert_to_object_from_gpudb_response(clear_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}

	/**
	 * Clear out a given set. Accepts an admin pwd
	 * @param ns The set to clear. 
	 * @param pwd Admin password 
	 * @return A clear response object.
	 */
	public clear_response do_clear_set(NamedSet ns, String pwd) throws GPUdbException{
		this.ns_store.clear(ns.get_setid());
		Request request = new ClearRequest(this, "/clear", ns, pwd);
		
		//decode
		clear_response response = (clear_response)AvroUtils.convert_to_object_from_gpudb_response(clear_response.SCHEMA$, request.post_to_gpudb(true));		
		log.debug("response:"+response.toString());

		return response;	
	}

	
	/**
	 * Clear out a given set.
	 * @param ns The set to clear.  
	 * @return A clear response object.
	 */
	public clear_response do_clear_set(NamedSet ns) throws GPUdbException{
		return do_clear_set(ns, "");	
	}
	
	/**
	 * Mainly used by Admin to get data. Pass in null for the parameter if no specific setid status is required.
	 * @return A status response object.
	 */
	public status_response do_status(NamedSet ns) throws GPUdbException{
		Request request = null;
		
		if( ns == null ) {
			request = this.request_factory.create_request("/status", "");
		} else {
			request = this.request_factory.create_request("/status", ns.get_setid().get_id());
		}
		//decode
		status_response response = (status_response)AvroUtils.convert_to_object_from_gpudb_response(status_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());		

		return response;
	}
	
	/**
	 * Return the server status  
	 * @return A status response object with maps of attribute/values.
	 */
	public server_status_response do_server_status() throws GPUdbException{
		Request request = this.request_factory.create_request("/serverstatus","");

		//decode
		server_status_response response = (server_status_response)AvroUtils.convert_to_object_from_gpudb_response(server_status_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());		

		return response;
	}


	/**
	 * Figure out how many sets there are in gpudb and how many points are in each set.  
	 * @return A stats response object.
	 */
	public stats_response do_stats() throws GPUdbException{
		Request request = this.request_factory.create_request("/stats","");

		//decode
		stats_response response = (stats_response)AvroUtils.convert_to_object_from_gpudb_response(stats_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());		

		return response;
	}


	/**
	 * Return how many points are in this specific set.
	 * @param ns Set we want to find the number of points for.  
	 * @return A clear response object.
	 */
	public stats_response do_stats(NamedSet ns) throws GPUdbException{				
		Request request = this.request_factory.create_request("/stats",ns.get_setid().get_id());

		//decode
		stats_response response = (stats_response)AvroUtils.convert_to_object_from_gpudb_response(stats_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());					


		return response;
	}

	/**
	 * Return how many points are in this specific set.
	 * @param set_id Set id of the set we want to check against.  
	 * @return A clear response object.
	 */
	public stats_response do_stats(SetId set_id) throws GPUdbException{				
		Request request = this.request_factory.create_request("/stats",set_id.get_id());

		//decode
		stats_response response = (stats_response)AvroUtils.convert_to_object_from_gpudb_response(stats_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());					

		return response;
	}

	/**
	 * Calls the get set on the given set with the given start and end retrieving objects from that set between index start and end.
	 * NOTE: use the list() functions in named set instead of this.
	 * @param ns The namedset we are retrieving objects from  
	 * @param start The start index.
	 * @param end The end index.
	 * @param semantic_type The semantic type of the set, or of the desired children of the set.
	 * @return A get set response object.
	 */
	public get_set_response do_list(NamedSet ns, long start, long end, String semantic_type) throws GPUdbException{
		Request request = new ListBasedOnTypeRequest(this, ns.get_setid(), start, end, semantic_type);

		//decode
		get_set_response response = (get_set_response)AvroUtils.convert_to_object_from_gpudb_response(get_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());				

		return response;
	}

	public get_set_objects_response do_list_fast(NamedSet ns, long start, long end) throws GPUdbException{
		Request request = new ListRequest(this, ns.get_setid(), start, end);

		//decode
		get_set_objects_response response = (get_set_objects_response)AvroUtils.convert_to_object_from_gpudb_response(get_set_objects_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());				

		return response;
	}
	
	/**
	 * Get objects whose attributes match the passed the matching values.  Conceptually, similar to doing a filter by list followed by listing 
	 * the result 
	 * @param id The SetId of the set.
	 * @param attribute The attribute to look for matches on.
	 * @param attrValues The values to match
	 * @return get_objects_response
	 * @throws GPUdbException
	 */
	public get_objects_response do_get_objects(SetId id, String attribute, List<CharSequence> attrValues) throws GPUdbException {
		log.debug("Get objects with SetId:"+id+" attribute:"+attribute+" attrValues.size():"+attrValues.size());
		Request request = new GetObjectsRequest(this, id, attribute, new ArrayList<Double>(), attrValues);

		//decode
		get_objects_response response = (get_objects_response)AvroUtils.convert_to_object_from_gpudb_response(get_objects_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());				

		return response;
	}
	// NOTE: add the double one too
	
	/**
	 * Add a java object to a gpudb set.  NOTE: should do it from the NamedSet.
	 * @param obj The object we are going to add.  
	 * @param ns The named set of the set we are going to add to.
	 * @return An add object response. Tells the user the object id of the added object.
	 */
	public add_object_response do_add_object(NamedSet ns, Object obj) throws GPUdbException{		
		Request request = this.request_factory.create_request("/add", obj, ns);		

		//decode
		add_object_response response = (add_object_response)AvroUtils.convert_to_object_from_gpudb_response(add_object_response.SCHEMA$, request.post_to_gpudb(true));		
		log.debug("response:"+response.toString());			

		return response;
	}
	
	/**
	 * Add symbol.
	 * @param symbol_id
	 * @param symbol_format
	 * @param obj - this is svg data 
	 * @return response from add symbol call
	 * @throws GPUdbException
	 */
	public add_symbol_response do_add_symbol(String symbol_id, String symbol_format, Object obj) throws GPUdbException{		
		this.log.debug("Do add symbol");

		// get the request
		log.debug("Build the request");

		// Assume object is string for now....
		Request request = new AddSymbolRequest(this, "/addsymbol", symbol_id, symbol_format, (String)obj);

		//decode
		add_symbol_response response = (add_symbol_response)AvroUtils.convert_to_object_from_gpudb_response(add_symbol_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());
		
		return response;
	}

	/**
	 * Update an object present gpudb set.  NOTE: should do it from the NamedSet.
	 * @param objId The object id for the object we are going to update.  
	 * @param obj The object we are going to add.  
	 * @param ns The named set of the set we are going to update.
	 * @return An update object response. Tells the user the object id of the added object.
	 */
	public update_object_response do_update_object(NamedSet ns, Object obj, String objectId) throws GPUdbException{		
		Request request = this.request_factory.create_request("/updateobject", obj, ns, objectId);		

		//decode
		update_object_response response = (update_object_response)AvroUtils.convert_to_object_from_gpudb_response(update_object_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());			

		return response;
	}

	/**
	 * Update an object based on a select statement
	 * @param obj The object we are going to add.  
	 * @param ns The named set of the set we are going to update.
	 * @return An update object response. Tells the user the object id of the added object.
	 */
	public long do_select_update(NamedSet ns, Map<CharSequence, CharSequence> data, String expression) throws GPUdbException{		
		
		Request request = new SelectUpdateRequest(this, "/selectupdate", ns, expression, data);		

		//decode
		select_update_response response = (select_update_response)AvroUtils.convert_to_object_from_gpudb_response(select_update_response.SCHEMA$, request.post_to_gpudb(true));		
		log.debug("response:"+response.toString());			

		return response.getCount();
	}


	
	/**
	 * Delete an object from a gpudb set.  NOTE: should do it from the NamedSet.
	 * @param objId The object we are going to delete.  
	 * @param ns The named set of the set we are going to delete it from.
	 * @return A delete object response. Tells the user the status of the delete request.
	 */
	public delete_object_response do_delete_object(NamedSet ns, String objId) throws GPUdbException{		
		Request request = new DeleteObjectRequest(this, "/deleteobject", ns.get_setid(), objId);		

		//decode
		delete_object_response response = (delete_object_response)AvroUtils.convert_to_object_from_gpudb_response(delete_object_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());			

		return response;
	}


	/**
	 * Add a bulk of java objects (a list) into a set all at once.  
	 * @param list_obj The list of java objects we are going to add to the set.
	 * @param ns The named set of the set we are going to add to.
	 * @return A bulk add response. Tells the user the object ids of the added objects.
	 */
	protected bulk_add_response do_add_object_list(List<Object> list_obj, NamedSet ns) throws GPUdbException{
		log.debug("bulk add object; list size:"+list_obj.size());
		Request request = this.request_factory.create_request("/bulkadd", list_obj, ns);		

		//decode
		bulk_add_response response = (bulk_add_response)AvroUtils.convert_to_object_from_gpudb_response(bulk_add_response.SCHEMA$, request.post_to_gpudb(true));		
		log.debug("response:"+response.toString());						

		return response;
	}

	/**
	 * Update the time-to-live for a set. All sets that are created initially use the default TTL.  If we want a different TTL use this. The TTL is in minutes
	 * @param set_id The set_id who we are setting the TTL for.
	 * @param ttl The time in minutes before this set is expired and cleared out. 
	 */
	public update_set_ttl_response do_update_ttl(SetId set_id, int ttl) throws GPUdbException{
		log.debug("update the ttl, set id:"+set_id.get_id()+" ttl:"+ttl);
		Request request = this.request_factory.create_request("/updatesetttl", set_id, ttl);

		// decode
		update_set_ttl_response response = (update_set_ttl_response)AvroUtils.convert_to_object_from_gpudb_response(update_set_ttl_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;
	}
	
	/**
	 * Update the time-to-live for a list of sets. All sets that are created initially use the default TTL.  If we want a different TTL use 
	 * this. The TTL is in minutes
	 * @param Map of set_id, ttl - The map of set_ids vs their ttls.
	 */
	public void do_update_ttl(Map<SetId, Integer> setIds) throws GPUdbException{
		log.debug("update set ids, ttls count : " + setIds.size());
		
		Set<Entry<SetId,Integer>> entries = setIds.entrySet();
		for( Entry<SetId, Integer> entry : entries ) {
			
			SetId setId = entry.getKey();
			Integer ttl = entry.getValue();
			do_update_ttl(setId, ttl);
		}
		return;
	}
	
	/**
	 * Update the time-to-live for a list of sets. All sets that are created initially use the default TTL.  If we want a different TTL use 
	 * this. The TTL is in minutes.
	 * @param List of set_ids - The list of set_ids vs. their ttls.
	 * @param ttl - ttl to set for these sets
	 */
	public void do_update_ttl(List<SetId> setIds, Integer ttl) throws GPUdbException{
		log.debug("update set ids count : " + setIds.size() + " with ttl : " + ttl);
		
		for( SetId setId : setIds ) {
			do_update_ttl(setId, ttl);
		}
		return;
	}

	

	/**
	 * Do an initialize group by map.  Used in get tracks.  Do before paging. 
	 * @param set_id The set_id we want to group across.
	 * @param map_id The map id. Use a UUID, something unique.
	 * @param attribute The attribute we group on. 
	 * @param page_size The size of each page. The number of keys in each page submap.
	 */
	public initialize_group_by_map_response do_initialize_group_by_map(SetId set_id, String map_id, String attribute, int page_size) throws GPUdbException{
		log.debug("initialize group by map; set_id:"+set_id.get_id()+" attribute:"+attribute);		
		Request request = this.request_factory.create_request("/initializegroupbymap", set_id, map_id, attribute, page_size);

		// decode
		initialize_group_by_map_response response = (initialize_group_by_map_response)AvroUtils.convert_to_object_from_gpudb_response(initialize_group_by_map_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						

		return response;
	}

	/**
	 * Grab a group by map page.  Used in get tracks.  Do after initialize group by map. 	
	 * @param map_id The map id. Use a UUID, something unique. 
	 * @param page_number The page to return.  Starts at 0.
	 */
	public group_by_map_page_response do_group_by_map_page(String map_id, int page_number) throws GPUdbException{
		log.debug("group by map paging; map_id:"+map_id+" page_number:"+page_number);		
		Request request = this.request_factory.create_request("/groupbymappage", map_id, page_number);

		// decode
		group_by_map_page_response response = (group_by_map_page_response)AvroUtils.convert_to_object_from_gpudb_response(group_by_map_page_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						

		return response;
	}

	/**
	 * Do a get sorted sets request.  	
	 * @param ns the Named set
	 * @param sort_attribute The attribute to do the sorting on.
	 * @param start the number of starting object
	 * @param end the number of ending object
	 */
	public get_sorted_set_response do_get_sorted_set(NamedSet ns, String sort_attribute, long start, long end) throws GPUdbException{
		
		log.debug("initialize do_get_sorted for set_id: " + ns.get_setid());		
		Request request = new GetSortedSetRequest(this, "/getsortedset", ns, sort_attribute, start, end);

		// decode
		get_sorted_set_response response = (get_sorted_set_response)AvroUtils.convert_to_object_from_gpudb_response(get_sorted_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						

		return response;
	}
	
	/**
	 * Decoding is callers responsibility
	 * @param set_ids
	 * @param sort_attribute
	 * @return
	 * @throws GPUdbException
	 */
	public get_sorted_sets_response do_get_sorted_sets(List<SetId> set_ids, String sort_attribute) throws GPUdbException{
		throw new GPUdbException("Discontinued function...use do_get_sorted_set() instead");
	}

	/**
	 * This is used to figure out the type information.  Find all types that match the given parameters.  An empty string will match anything.
	 * @param typeID The type's ID.
	 * @param label The label of the type.
	 * @param semanticType The semantic type of the type.
	 * @return the avro get_type_info_response.
	 */
	public get_type_info_response do_get_type_info(String typeID, String label, String semanticType) {
		log.debug("get type info typeID"+typeID+" label:"+label+" semanticType:"+semanticType);
		Request request = this.request_factory.create_request("/gettypeinfo", typeID, label, semanticType);

		// decode
		get_type_info_response response = (get_type_info_response)AvroUtils.convert_to_object_from_gpudb_response(get_type_info_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;
	}

	/**
	 * This is used to identify sets based on type information.  Find all sets that match the given parameters.  An empty string will match anything.
	 * @param label The type label.
	 * @param semanticType The semantic type.
	 * @return get_sets_by_type_info_response the matching set ids.
	 */
	public get_sets_by_type_info_response do_get_sets_by_type_info(String label, String semanticType) {
		return this.do_get_sets_by_type_info("", label, semanticType);
	}

	/**
	 * This is used to identify sets based on type information.  Find all sets that match the given parameters.  An empty string will match anything.	 
	 * @param typeID The type id.
	 * @return get_sets_by_type_info_response the matching set ids.
	 */
	public get_sets_by_type_info_response do_get_sets_by_type_id(String typeID) {
		return this.do_get_sets_by_type_info(typeID, "", "");
	}
	
	/**
	 * This is used to identify sets based on type information.  Find all sets that match the given parameters.  An empty string will match anything.
	 * @param typeID The type's ID.
	 * @param label The label of the type.
	 * @param semanticType The semantic type of the type.
	 * @return the avro get_sets_by_type_info_response.
	 */
	private get_sets_by_type_info_response do_get_sets_by_type_info(String typeID, String label, String semanticType) {
		log.debug("get sets by type info typeID"+typeID+" label:"+label+" semanticType:"+semanticType);
		Request request = this.request_factory.create_request("/getsetsbytypeinfo", typeID, label, semanticType);

		// decode
		get_sets_by_type_info_response response = (get_sets_by_type_info_response)AvroUtils.convert_to_object_from_gpudb_response(get_sets_by_type_info_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						

		return response;
	}

	/**
	 * Find all tracks from a set that intersect the given road  
	 * @param x_attribute The x-attribute of the set (probably "x")
	 * @param y_attribute The y-attribute of the set (probably "y")
	 * @param road_x_vector X coordinates of the given road
	 * @param road_y_vector Y coordinates of the given road
	 * @param output_attribute The track id attribute (probably "TRACKID")
	 * @return road_intersection_response
	 */
	public road_intersection_response do_road_intersection(SetId in_set, String x_attribute, String y_attribute, java.util.List<java.lang.Double> road_x_vector, java.util.List<java.lang.Double> road_y_vector, String output_attribute) {
		log.debug("road intersection; set_id:"+in_set.get_id());		
		Request request = this.request_factory.create_request("/roadintersection", in_set, x_attribute, y_attribute, road_x_vector, road_y_vector, output_attribute);

		// decode
		road_intersection_response response = (road_intersection_response)AvroUtils.convert_to_object_from_gpudb_response(road_intersection_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						

		return response;
	}
	
	/**
	 * Return true if the two given shapes intersect, false if not.  
	 * @param x_vector_1 x-coordinates of the first shape
	 * @param y_vector_1 y-coordinates of the first shape
	 * @param geometry_type_1 geometry type of the first shape (POLYGON and LINESTRING supported).
	 * @param x_vector_2 x-coordinates of the first shape
	 * @param y_vector_2 y-coordinates of the first shape
	 * @param geometry_type_2 geometry type of the first shape (POLYGON and LINESTRING supported).
	 * @return shape_literal_intersection_response
	 */
	public shape_literal_intersection_response do_shape_literal_intersection(List<Double> x_vector_1, List<Double> y_vector_1, CharSequence geometry_type_1, CharSequence wkt_string_1, List<Double> x_vector_2, List<Double> y_vector_2, CharSequence geometry_type_2, CharSequence wkt_string_2) {
		log.debug("shape literal intersection");		
		Request request = this.request_factory.create_request("/shapeliteralintersection", x_vector_1, y_vector_1, geometry_type_1, wkt_string_1, x_vector_2, y_vector_2, geometry_type_2, wkt_string_2);

		// decode
		shape_literal_intersection_response response = (shape_literal_intersection_response)AvroUtils.convert_to_object_from_gpudb_response(shape_literal_intersection_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;
	}

	/**
	 * Return true if the two given shapes intersect, false if not.  
	 * @param wkt_string_1 The WKT representation of the first shape (POLYGON and LINESTRING supported).
	 * @param wkt_string_2 The WKT representation of the second shape (POLYGON and LINESTRING supported).
	 * @return shape_literal_intersection_response
	 */
	public shape_literal_intersection_response do_shape_literal_intersection(String wkt_string_1, String wkt_string_2) {
		log.debug("shape literal intersection: " + wkt_string_1 + " , " + wkt_string_2);
		List<Double> null_list = new ArrayList<Double>();

		return this.do_shape_literal_intersection(null_list, null_list, "", wkt_string_1, null_list, null_list, "", wkt_string_2);
	}
	
	/**
	 * Run a shape intersection query on the sets passed in. 
	 * @param set_ids The sets to perform the operation on. 
	 * @param wkt_attr_name The name of the wkt attribute that will be looked for in the sets.
	 * @param x_vector x-coordinates of the target shape (alternative to the wkt_string below)
	 * @param y_vector y-coordinates of the target shape (alternative to the wkt_string below)
	 * @param geometry_type either "polygon" or "linestring" (alternative to the wkt_string below)
	 * @param wkt_string The wkt string. If this is empty then we are doing all shapes vs. all. If it has a wkt string then we are doing all the
	 * shapes in the set vs. the wkt string passed in. 
	 * @param operation The operation to perform.
	 * @return response object that contains the result.
	 */
	public shape_intersection_response do_shape_intersection(List<SetId> set_ids, CharSequence wkt_attr_name, List<Double> x_vector, List<Double> y_vector, CharSequence geometry_type, CharSequence wkt_string) {
		log.debug("shape intersection");		
		Request request = this.request_factory.create_request("/shapeintersection", set_ids, wkt_attr_name, x_vector, y_vector, geometry_type, wkt_string);

		// decode
		shape_intersection_response response = (shape_intersection_response)AvroUtils.convert_to_object_from_gpudb_response(shape_intersection_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;
	}
	
	
	
	/**
	 * Return all the set ids which don't have parents and are of ANY namespace.  Also, tells you which one of these set ids are themselves parents.
	 * @return the get orphans response object. Includes the set ids and booleans for whether they are parents.
	 */
	public get_orphans_response do_get_orphans() {
		return this.do_get_orphans("*");
	}
	
	/**
	 * Return all the set ids of the given namespace which don't have parents.  Also, tells you which one of these set ids are themselves parents.
	 * @param namespace The namespace that all the sets should be of.
	 * @return the get orphans response object. Includes the set ids and booleans for whether they are parents.
	 */
	public get_orphans_response do_get_orphans(String namespace) {
		log.debug("get orphans");		
		Request request = new GetOrphansRequest(this, "/getorphans", namespace); 

		// decode
		get_orphans_response response = (get_orphans_response)AvroUtils.convert_to_object_from_gpudb_response(get_orphans_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						

		return response;
	}
	
	/**
	 * Turn off analytic.  This analytic is designed to be perform on a set with tracks/groups in them.  These tracks are specified by the group
	 * attribute.  The tracks have an ordering to them, usually time, specified by the sort_attribute.  We are looking for consecutive points in
	 * each track who differ by "threshold" or more in the sort attribute.  
	 * @param set_id The set to perform the analytic on.
	 * @param group_attribute The attribute that defines if two objects are apart of the same group/track.
	 * @param sort_attribute The attribute we sort by, that we use to apply the threshold.
	 * @param x_attribute One of the attributes returned.
	 * @param y_attribute One of the attributes returned.
	 * @param threshold This is applied to the sorted attribute.  
	 * @return a turn_off_response object.
	 */
	public turn_off_response do_turn_off(SetId set_id, String group_attribute, String sort_attribute, String x_attribute, String y_attribute, double threshold){
		log.debug("turn off");
		Request request = new TurnOffRequest(this, "/turnoff", set_id.get_id(), group_attribute, sort_attribute, x_attribute, y_attribute, threshold);
		
		// decode
		turn_off_response response = (turn_off_response)AvroUtils.convert_to_object_from_gpudb_response(turn_off_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;		
	}
	
	/**
	 * Register a type transformation between two different types.  
	 * @param type_id The type for which a transform is being defined.
	 * @param new_type_id The type for which the transform map transforms type_id into (i.e. the target type id)
	 * @param transform_map The map of attributes transformations from type_id to new_type_id
	 * @return A filter by list response object. Contains the count of the resulting set.
	 */
	public register_type_transform_response do_register_type_transform(CharSequence type_id, CharSequence new_type_id, Map<CharSequence, CharSequence> transform_map) throws GPUdbException{
		this.log.debug("Do register type transform");

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/registertypetransform", type_id, new_type_id, transform_map);

		//decode
		register_type_transform_response response = (register_type_transform_response)AvroUtils.convert_to_object_from_gpudb_response(register_type_transform_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}
	
	/**
	 * Generate video endpoint.
	 * @param gvgr - the request object filled in by user.
	 * @return - gen_video_response object
	 */
	public generate_video_response do_generate_video(GPUdbVideoGenRequest gvgr){
		log.debug("generate video request");
		Request request = new GenerateVideoRequest(this, "/generatevideo", gvgr);
		
		// decode
		generate_video_response response = (generate_video_response)AvroUtils.convert_to_object_from_gpudb_response(generate_video_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;		
	}
	
	/**
	 * Generate heatmap video endpoint.
	 * @param gvgr - the request object filled in by user.
	 * @return - gen_video_response object
	 */
	public generate_heatmap_video_response do_generate_heatmap_video(GpudbHeatMapVideoGenRequest gvgr){
		log.debug("generate heatmap video request");
		Request request = new GenerateHeatMapVideoRequest(this, "/generateheatmapvideo", gvgr);
		
		// decode
		generate_heatmap_video_response response = (generate_heatmap_video_response)AvroUtils.convert_to_object_from_gpudb_response(generate_heatmap_video_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());						
		
		return response;		
	}


	/**
	 * Takes in two WKT strings, performs an operation, and returns true or false.
	 * @param wkt1 The first WKT.
	 * @param wkt2 The second WKT.
	 * @param operation The operation to perform.
	 * @return The result of the operation.
	 */
	public boolean do_spatial_query(CharSequence wkt1, CharSequence wkt2, SpatialOperationEnum operation) {
		Request request = new SpatialQueryRequest(this, "/spatialquery", wkt1, wkt2, operation.toString());

		//decode
		spatial_query_response response = (spatial_query_response)AvroUtils.convert_to_object_from_gpudb_response(spatial_query_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response.getResult().booleanValue();
	}
	
	/**
	 * Get the named set object for the given set id, if it doesn't exist we create it and thus need the type. The fact that we
	 * create it if it doesn't exist is the difference between this and the getNamedSet(SetId id) method. 
	 * @param id The set id of this named set.
	 * @param type The type of this named set.
	 * @return The named set object.
	 */
	@Deprecated
	public NamedSet getNamedSet(SetId id, Type type) throws GPUdbException{
		// Returns a named set/collection with this guid/name attached to "this" instance of gpudb
		if(this.ns_store.contains(id)){
			// there is a named set object with this set id already; don't need to recreate; also we want to avoid hitting the server (HTTP) as much as possible
			this.log.debug("get namedset set already exists, set id:"+id.get_id());
			return this.ns_store.get(id);
		}else {			
			NamedSet ns = new NamedSet(id, this, type);

			// send this set definition to gpudb
			this.log.debug("New set, get namedset, set id:"+ns.get_setid().get_id());

			// it only needs to send something if the set is not just a parent by itself
			if(!type.isParent()) {
				Request request = this.request_factory.create_request("/newset", ns);			

				//decode
				new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());		
				log.debug("response:"+response.toString());	

			} else {
				// if its just a parent set then we don't need to inform the server; but we do need the children
				// get the children
				ns.getChildrenFromServer();
				log.debug("parent set; number of children:"+ns.getNumberOfChildren());
			}

			// put into store
			this.ns_store.put(id, ns);

			return ns;
		}		
	}

	/**
	 * Get the named set object for the given set id, if it doesn't exist then it will throw an exception  
	 * @param id The set id of this named set.
	 * @return The named set object.
	 */
	public NamedSet getNamedSet(SetId id) throws GPUdbException {
		this.log.debug("Calling getNamedSet with set id:"+id.get_id());
		
		if(!this.ns_store.contains(id)) {
			// check if this set exists in the server			
			set_info_response response = this.do_set_info(id); // NOTE: this will throw if the set doesn't exist
			
			// at this point we know the set exists; check if it's a parent (if there are multiple set ids in the returned response
			// or if there is just one but that set id doesn't match the one we did the info on [i.e. it's a child id]).
			NamedSet ns = null;
			
			if(response.getSetIds().size() == 0 ) {
				throw new GPUdbException("Strange response back from the set info:"+response.toString());
			}
			
			if(response.getSetIds().size() > 1 || (!response.getSetIds().get(0).toString().equals(id.get_id()))) {
				log.debug("This namedset:"+id.get_id()+" is a parent");
				ns = new NamedSet(id, this, Type.buildParentType());
				ns.buildChildren(response);
				this.ns_store.put(id, ns);
			} else if(response.getSetIds().size() == 1 && response.getSetIds().get(0).toString().equals(id.get_id())){
				// otherwise; build the type from type schema				
				Schema.Parser parser = new Schema.Parser();
				String typeSchema = response.getTypeSchemas().get(0).toString();
				log.debug("Building the type; type schema:"+typeSchema);
				try {
					Schema schema = parser.parse(typeSchema);
					log.debug("Built schema");
					Type type = new Type(response.getTypeIds().get(0).toString(), GenericObject.class, schema,
							response.getLabels().get(0).toString(), response.getSemanticTypes().get(0).toString());
					log.debug("Type built");

					// build a named set object
					ns = new NamedSet(id, this, type);						
				} catch(Exception e){
					log.error(" Error:", e);
					throw new GPUdbException("Error building the type object; schema:"+typeSchema);
				}
				this.ns_store.put(id, ns); // need to be able to retrieve this named set object				
			} else {
				throw new GPUdbException("Strange response back from the set info:"+response.toString());
			}
			return ns;
		} else {
			this.log.debug("There is a NamedSet set id:"+id.get_id());
			return this.ns_store.get(id);
		}
	}

	/**
	 * Return a new named set of the given type with a random id.  This set will have no parent.  
	 * @param type The type of this named set.
	 * @return The named set object.
	 */
	@Deprecated
	public NamedSet newNamedSet(Type type) throws GPUdbException {	
		// create an ID, build the set over here
		SetId si = this.new_setid();		
		NamedSet ns = new NamedSet(si, this, type);
		this.ns_store.put(si, ns);

		// send this set definition to gpudb
		Request request = this.request_factory.create_request("/newset", ns);	
		new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());

		return ns;
	}		

	/**
	 * Return a new named set of the given type with a random id.  
	 * @param type The type of this named set.
	 * @param parent The set id of the parent.
	 * @return The named set object.
	 */
	@Deprecated
	public NamedSet newNamedSet(SetId parent, Type type) throws GPUdbException {	
		// create an ID, build the set over here
		SetId si = this.new_setid();		
		NamedSet ns = new NamedSet(si, this, type);
		this.ns_store.put(si, ns);
		log.debug("Built the named set object; semantic type of the ns:"+type.getSemanticType());

		// if the parent set doesn't exist then create it
		NamedSet parentNS;
		if(this.ns_store.contains(parent)){
			// there is a named set object with this set id already;
			this.log.debug("Parent set id already exists:"+parent.get_id());
			parentNS = this.ns_store.get(parent);
		}else {
			//create a parent NS
			parentNS = new NamedSet(parent, this, Type.buildParentType());
			this.ns_store.put(parent, parentNS);
		}

		// send this set definition to gpudb
		Request request = this.request_factory.create_request("/newset", ns, parent);	
		new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());
		
		log.debug("Got back from posting the set:"+ns.get_setid().get_id());

		// add it to the list of children to the parent set
		parentNS.addChild(ns.getType(), ns);
		log.debug("number of children:"+parentNS.getNumberOfChildren());

		return ns;
	}
	
	/**
	 * Check if there is a set with this set id.  
	 * @param id The set id of the set to look for.
	 * @return true if the set exists, false otherwise.
	 */
	public boolean setExists(SetId id) {
		// first check your map
		if(this.ns_store.contains(id))
			return true;
		
		// do a set info; throws if the set doesn't exist
		try {
			set_info_response response = this.do_set_info(id);
		} catch(GPUdbException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Build a new empty parent set. Throws if a set with this ID already exists.
	 * @param id The parent's ID.
	 * @return The NamedSet.
	 * @throws GPUdbException if there is a set with this ID already.
	 */
	public NamedSet newParentNamedSet(SetId id) throws GPUdbException {
		if(setExists(id)) {
			this.log.error("A set exists with this ID already"+id.get_id());
			throw new GPUdbException("A set exists with this ID already"+id.get_id());
		} 
				
		// don't need send anything to the server; NOTE: we should probably make sure this set doesn't exist already
		NamedSet ns = new NamedSet(id, this, Type.buildParentType());
		this.ns_store.put(id, ns);
		
		return ns;
	}
	
	/**
	 * Build a new empty parent set. Assigns it a random ID.
	 * @return The NamedSet.
	 */
	public NamedSet newParentNamedSet() throws GPUdbException {
		// since this is a UUID not going to check to make sure it already exists.
		SetId id = this.new_setid();
		NamedSet ns = new NamedSet(id, this, Type.buildParentType());
		this.ns_store.put(id, ns);
		
		return ns;
	}
	
	/**
	 * Build a standalone single type (i.e. non-parent) set.
	 * @param id The set id of this set.
	 * @param type The type of this set.
	 * @return The NamedSet.
	 * @throws GPUdbException if there is a set with this ID already.
	 */
	public NamedSet newSingleNamedSet(SetId id, Type type) throws GPUdbException {
		if(setExists(id)) {
			this.log.error("A set exists with this ID already"+id.get_id());
			throw new GPUdbException("A set exists with this ID already"+id.get_id());
		} 		

		NamedSet ns = new NamedSet(id, this, type);
				
		// build the new set on the server
		Request request = new NewSetRequest(this, "/newset", ns);			

		//decode
		new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	

		// NOTE: only add to the store if we didn't throw
		this.ns_store.put(id, ns);
		
		return ns;
	}
	
	/**
	 * Build a standalone single type (i.e. non-parent) set. Assigns it a random ID.
	 * @param type The type of this set.
	 * @return The NamedSet.
	 * @throws GPUdbException
	 */
	public NamedSet newSingleNamedSet(Type type) throws GPUdbException {
		// since this is a UUID not going to check to make sure it already exists.
		SetId id = this.new_setid();
		
		// build the named set
		NamedSet ns = new NamedSet(id, this, type);
				
		// build the new set on the server
		Request request = new NewSetRequest(this, "/newset", ns);			

		//decode
		new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());	
		
		// NOTE: only add to the store if we didn't throw
		this.ns_store.put(id, ns);
		
		return ns;
	}
		
	/**
	 * Create a new child set for the passed in parent. 
	 * @param parent The parent NamedSet.
	 * @param id The id of the child.
	 * @param type The type of the child.
	 * @return The NamedSet.
	 * @throws GPUdbException if there is already a child of this type.
	 */
	public NamedSet newChildNamedSet(NamedSet parent, SetId id, Type type) throws GPUdbException {
		if(setExists(id)) {
			this.log.error("A set exists with this ID already"+id.get_id());
			throw new GPUdbException("A set exists with this ID already"+id.get_id());
		} 
		
		NamedSet ns = new NamedSet(id, this, type);
				
		// send this set definition to gpudb
		Request request = new NewSetRequest(this, "/newset", ns, parent.get_setid());
		new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());
		log.debug("Got back from posting the set:"+ns.get_setid().get_id());

		// add it to the list of children to the parent set
		parent.addChild(ns.getType(), ns); // throws if there is a child of this type already
		log.debug("number of children:"+parent.getNumberOfChildren());

		// NOTE: only add to the store if we didn't throw
		this.ns_store.put(id, ns);
		
		return ns;

	}
	
	/**
	 * Create a new child set for the passed in parent. Assigns a random ID. 
	 * @param parent The parent NamedSet.
	 * @param type The type of the child.
	 * @return The NamedSet.
	 * @throws GPUdbException if there is already a child of this type.
	 */
	public NamedSet newChildNamedSet(NamedSet parent, Type type) throws GPUdbException {
		log.debug("Building a child set from parent:"+parent.get_setid());
		// since this is a UUID not going to check to make sure it already exists.
		SetId id = this.new_setid();
				
		NamedSet ns = new NamedSet(id, this, type);
		log.debug("Building the child with id:"+id);
				
		if(parent.childExists(type))
			throw new GPUdbException("There is already a child of this type:"+type);
		
		// send this set definition to gpudb 
		Request request = new NewSetRequest(this, "/newset", ns, parent.get_setid()); // child then parent	
		new_set_response response = (new_set_response)AvroUtils.convert_to_object_from_gpudb_response(new_set_response.SCHEMA$, request.post_to_gpudb());
		log.debug("Got back from posting the set:"+ns.get_setid().get_id());

		// add it to the list of children to the parent set
		parent.addChild(ns.getType(), ns); // throws if there is a child of this type already
		log.debug("number of children:"+parent.getNumberOfChildren());

		// NOTE: only add to the store if we didn't throw
		this.ns_store.put(id, ns);
		
		return ns;

	}

	/**
	 * Return a new set id with a random id which will be prepended with namespace if there is one.  
	 * @return The named set object.
	 */
	public SetId new_setid() {
		String uuid = UUID.randomUUID().toString();
		if(this.hasNamespace){
			return new SetId(this.namespace+"_"+uuid);
		} else {
			return new SetId(uuid);
		}
	}

	/**
	 * Return a new set id of with the given id which will be prepended with namespace if there is one.  
	 * @param id The id to use
	 * @return The named set object.
	 */
	public SetId new_setid(String id) {		
		if(this.hasNamespace){
			return new SetId(this.namespace+"_"+id);
		} else {
			return new SetId(id);
		}
	}
		
	public get_tracks2_response do_get_tracks(NamedSet namedSet, NamedSet world, int start, int end, 
			double min_x, double min_y, double max_x, double max_y, boolean doExtent) {
		
		this.log.debug("Do get tracks2");

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/gettracks2", namedSet.get_setid(), world.get_setid(), 
				start, end, min_x, min_y, max_x, max_y, doExtent);

		/// decode 		
		get_tracks2_response response = (get_tracks2_response)AvroUtils.convert_to_object_from_gpudb_response(get_tracks2_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}
	
	public statistics_response do_statistics (NamedSet namedSet, List<StatisticsOptionsEnum> stats, String attribute) {
		
		this.log.debug("Do get do_statistics");
		
		// Parameters for requested stats. Currently not available to user
		Map<String, String> params = new HashMap<String, String>();
		
		// Convert stats to a comma separated string
		StringBuffer comma_separated_stats = new StringBuffer();
		for (int i=0; i< stats.size(); i++) {
			if (i < stats.size() - 1) {
				comma_separated_stats.append(stats.get(i).value() + ",");
			} else {
				comma_separated_stats.append(stats.get(i).value());
			}
		}

		// get the request
		log.debug("Build the request");
		Request request = this.request_factory.create_request("/statistics", comma_separated_stats.toString(), 
				params, attribute, namedSet.get_setid());

		/// decode 		
		statistics_response response = (statistics_response)AvroUtils.convert_to_object_from_gpudb_response(statistics_response.SCHEMA$, 
				request.post_to_gpudb());		
		log.debug("response:"+response.toString());

		return response;
	}
	
	public get_set_objects_response do_list_fast(NamedSet ns, int start, int end, String semantic_type) throws GPUdbException{
		Request request = new ListRequest(this, ns.get_setid(), start, end);
		get_set_objects_response response = (get_set_objects_response)AvroUtils.convert_to_object_from_gpudb_response(get_set_objects_response.SCHEMA$, request.post_to_gpudb());		
		log.debug("response:"+response.toString());				
		return response;
	}

}
