package com.gisfederal.request;

import java.util.List;
import java.util.Map;

import com.gisfederal.Gaia;
import com.gisfederal.NamedSet;
import com.gisfederal.SetId;

// construct and return the correct Request object
public class RequestFactory {
	private Gaia gaia;

	public RequestFactory(Gaia gaia) {
		this.gaia = gaia;
	}
	
	public Request create_request(String file, Object obj, NamedSet ns, String objectId) {
		if(file.equals("/updateobject"))
			return new UpdateObjectRequest(this.gaia, file, obj, objectId, ns);
		else
			return null;
	}	

	public Request create_request(String file, Object obj, NamedSet ns) {
		if(file.equals("/add"))
			return new AddObjectRequest(this.gaia, file, obj, ns);
		else
			return null;
	}	

	/* LAME DUCK
	public Request create_request(String file, List<String> user_authorizations, List<SetId> set_ids) {
		if(file.equals("/authenticateusers"))
			return new AuthenticateUsersRequest(this.gaia, file, user_authorizations, set_ids);
		else
			return null;
	}
	*/
	
	public Request create_request(String file, SetId set_id, int ttl) {
		if(file.equals("/updatesetttl"))
			return new UpdateSetTTLRequest(this.gaia, file, set_id, ttl);
		else
			return null;
	}
	
	public Request create_request(String file, List<Object> list_obj, NamedSet ns) {
		if(file.equals("/bulkadd"))
			return new BulkAddRequest(this.gaia, file, list_obj, ns);
		else
			return null;
	}
	
	public Request create_request(String file, List<SetId> set_ids, String attribute, double lowest, double highest, String grouping_attribute) {
		if(file.equals("/registertriggerrange"))
			return new RegisterTriggerRange(this.gaia, file, set_ids, attribute, lowest, highest, grouping_attribute);
		else
			return null;
	}
	
	public Request create_request(String file, List<SetId> set_ids, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector, String grouping_attribute) {
		if(file.equals("/registertriggernai"))
			return new RegisterTriggerNAI(this.gaia, file, set_ids, x_attribute, x_vector, y_attribute, y_vector, grouping_attribute);
		else
			return null;
	}

	public Request create_request(String file, String definition, String annotation_attr, String label, String semanticType) { 
		if(file.equals("/registertype"))
			return new CreateTypeRequest(this.gaia, file, definition, annotation_attr, label, semanticType);
		else
			return null;
	}
	
	public Request create_request(String file, Object obj, String annotation_attr, String label, String semanticType) { 
		if(file.equals("/registertype"))
			return new CreateTypeRequest(this.gaia, file, (Class)obj, annotation_attr, label, semanticType);
		else
			return null;
	}
	
	public Request create_request(String file, Object obj) { 
		if(file.equals("/newset"))
			return new NewSetRequest(this.gaia, file, (NamedSet)obj);		
		else
			return null;
	}
	
	public Request create_request(String file, Object obj, Object param2) { 
		if(file.equals("/clear"))
			return new ClearRequest(this.gaia, file, (String)obj, (String)param2);
		if(file.equals("/newset"))
			return new NewSetRequest(this.gaia, file, (NamedSet)obj, (SetId)param2);
		else
			return null;
	}

	public Request create_request(String file, SetId world_id, SetId subworld_id, SetId result_set_id, String shared_attribute, String cluster_attribute) {
		if(file.equals("/cluster"))
			return new ClusterRequest(this.gaia, file, world_id, subworld_id, result_set_id, shared_attribute, cluster_attribute);
		else
			return null;
	}
	
	public Request create_request(String file, SetId original_id, SetId new_id, String type_id, String selector) {
		if(file.equals("/copyset"))
			return new CopySetRequest(this.gaia, file, original_id, new_id, type_id, selector);
		else
			return null;
	}

	public Request create_request(String file, String typeID, String label, String semanticType) {
		if(file.equals("/gettypeinfo"))
			return new GetTypeInfoRequest(this.gaia, file, typeID, label, semanticType);
		if(file.equals("/getsetsbytypeinfo"))
			return new GetSetsByTypeInfoRequest(this.gaia, file, typeID, label, semanticType);
		else
			return null;
	}
	
	public Request create_request(String file, SetId src_guid, SetId dst_guid, String calc_str) {
		if(file.equals("/select"))
			return new SelectRequest(this.gaia, file, src_guid, dst_guid, calc_str);		
		else
			return null;
	}
	
	public Request create_request(String file, String expr) {
		if(file.equals("/stats"))
			return new StatsRequest(this.gaia, file, expr);
		if(file.equals("/status"))
			return new StatusRequest(this.gaia, file, expr);
		if(file.equals("/setinfo"))
			return new SetInfoRequest(this.gaia, file, expr);
		if(file.equals("/serverstatus"))
			return new ServerStatusRequest(this.gaia, file, expr);
		else
			return null;
	}

	public Request create_request(String file, SetId in_set, SetId rs_set, String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y){
		if(file.equals("/boundingbox"))
			return new BoundingBoxRequest(this.gaia, file, in_set, rs_set, x_attribute, y_attribute, min_x, max_x, min_y, max_y);
		else
			return null;
	}
	
	public Request create_request(String file, List<CharSequence> in_sets, List<Long> colors, List<Integer> sizes, String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y, double width, double height, String projection, long bg_color){
		if(file.equals("/plot2dmultiple"))
			return new Plot2DMultipleRequest(this.gaia, file, in_sets, colors, sizes, x_attribute, y_attribute, min_x, max_x, min_y, max_y, width, height, projection, bg_color);
		else
			return null;
	}
	
	public Request create_request(String file, SetId id, int start, int end) {
		if(file.equals("/getsetsorted"))
			return new GetSetSortedRequest(this.gaia, id, start, end);		
		else
			return null;
	}
	
	public Request create_request(String file, SetId id, int start, int end, String semantic_type) {
		if(file.equals("/getset"))
			return new ListRequest(this.gaia, id, start, end, semantic_type);
		else
			return null;
	}

	public Request create_request(String file, SetId id, String attr_name) {
		if(file.equals("/maxmin"))
			return new MaxMinRequest(this.gaia, file, id, attr_name);		
		if(file.equals("/makebloom"))
			return new MakeBloomRequest(this.gaia, file, id, attr_name);
		if(file.equals("/sort"))
			return new SortRequest(this.gaia, file, id, attr_name);
		if(file.equals("/unique"))
			return new UniqueRequest(this.gaia, file, id, attr_name);
		else
			return null;
	}
	
	public Request create_request(String file, SetId id, String x_attribute, String y_attribute) {
		if(file.equals("/convexhull"))
			return new ConvexHullRequest(this.gaia, file, id, x_attribute, y_attribute);
		else
			return null;
	}

	public Request create_request(String file, SetId id, String map_id, String attr_name, int page_size) {
		if(file.equals("/initializegroupbymap"))
			return new InitializeGroupByMap(this.gaia, file, id, map_id, attr_name, page_size);
		else
			return null;
	}
	
	public Request create_request(String file, String map_id, int page_number) {
		if(file.equals("/groupbymappage"))
			return new GroupByMapPage(this.gaia, file, map_id, page_number);
		else
			return null;
	}
	
	public Request create_request(String file, SetId id, List<String> attributes) {
		if(file.equals("/groupby"))
			return new GroupByRequest(this.gaia, file, id, attributes);		
		else
			return null;
	}
	
	public Request create_request(String file, SetId id, String attribute, long interval, double start, double end) {
		if(file.equals("/histogram"))
			return new HistogramRequest(this.gaia, file, id, attribute, interval, start, end);
		else
			return null;
	}
	
	public Request create_request(String file, SetId id, String filter_attribute, List<CharSequence> filter, String histogram_attribute, long interval, double start, double end) {
		if(file.equals("/filterthenhistogram"))
			return new FilterThenHistogramRequest(this.gaia, file, id, filter_attribute, filter, histogram_attribute, interval, start, end);
		else
			return null;
	}
	
	public Request create_request(String file, SetId in_set, SetId rs, String attribute, double lower_bounds, double upper_bounds) {
		if(file.equals("/filterbybounds"))
			return new FilterByBoundsRequest(this.gaia, file,  in_set, rs, attribute, lower_bounds, upper_bounds);
		else
			return null;
	}
	
	public Request create_request(String file, SetId in_set, SetId rs, Map<CharSequence, List<CharSequence>> attribute_map) {
		if(file.equals("/filterbylist"))
			return new FilterByListRequest(this.gaia, file,  in_set, rs, attribute_map);
		else
			return null;
	}
		
	public Request create_request(String file, SetId in_set, String attribute, Map<CharSequence, List<CharSequence>> group_map, boolean sort, String sort_attribute) {
		if(file.equals("/storegroupby"))
			return new StoreGroupByRequest(this.gaia, file,  in_set, attribute, group_map, sort, sort_attribute);
		else
			return null;
	}
	
	public Request create_request(String file, SetId in_set, SetId result_set_id, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector){
		if(file.equals("/filterbynai"))
			return new FilterByNAIRequest(this.gaia, file, in_set, result_set_id, x_attribute, x_vector, y_attribute, y_vector);
		else
			return null;
	}
	
	public Request create_request(String file, SetId in_set, SetId result_set_id, String x_attribute, String y_attribute, Double x_center, Double y_center, Double radius){
		if(file.equals("/filterbyradius"))
			return new FilterByRadiusRequest(this.gaia, file, in_set, result_set_id, x_attribute, y_attribute, x_center, y_center, radius);
		else
			return null;
	}
	
	public Request create_request(String file, SetId in_set, SetId result_set_id, String expression, String mode, List<CharSequence> options, List<CharSequence> attributes){
		if(file.equals("/filterbystring"))
			return new FilterByStringRequest(this.gaia, file, expression, mode, options, in_set, attributes, result_set_id);
		else
			return null;
	}

	public Request create_request(String file, SetId in_set, SetId result_set_id, boolean isString, Double value, String value_string, String attribute){
		if(file.equals("/filterbyvalue"))
			return new FilterByValueRequest(this.gaia, file, in_set, result_set_id, isString, value, value_string, attribute);
		else
			return null;
	}

	
	public Request create_request(String file, SetId left_set, String left_attribute, SetId right_set, String right_attribute, String result_type, SetId result_set_id) {
		if(file.equals("/join"))
			return new JoinRequest(this.gaia, file, left_set, left_attribute, right_set, right_attribute, result_type, result_set_id);
		else
			return null;
	}
	
	public Request create_request(String file, SetId left_setId, SetId rsId, String attribute, SetId sourceSetId, String source_attribute) {
		if(file.equals("/filterbyset"))
			return new FilterBySetRequest(this.gaia, file, left_setId, rsId, attribute, sourceSetId, source_attribute);
		else
			return null;
	}
	
	public Request create_request(String file, SetId left_set, String left_attr, SetId right_set, String right_attr, SetId subset) {
		if(file.equals("/joinsetup"))
			return new JoinSetupRequest(this.gaia, file, left_set, left_attr, right_set, right_attr, subset);
		else
			return null;
	}
	
	public Request create_request(String file, SetId left_subset, String left_attr, int left_index, SetId right_set, String right_attr, String result_type, SetId result_set) {
		if(file.equals("/joinincremental"))
			return new JoinIncrementalRequest(this.gaia, file, left_subset, left_attr, left_index, right_set, right_attr, result_type, result_set);
		else
			return null;
	}
	
	public Request create_request(String file, SetId in_set, String x_attribute, String y_attribute, java.util.List<java.lang.Double> road_x_vector, java.util.List<java.lang.Double> road_y_vector, String output_attribute) {
		if(file.equals("/roadintersection"))
			return new RoadIntersectionRequest(this.gaia, file, in_set, x_attribute, y_attribute, road_x_vector, road_y_vector, output_attribute);
		else
			return null;
	}
	
	public Request create_request(String file, List<Double> x_vector_1, List<Double> y_vector_1, CharSequence geometry_type_1, CharSequence wkt_string_1, List<Double> x_vector_2, List<Double> y_vector_2, CharSequence geometry_type_2, CharSequence wkt_string_2) {
		if(file.equals("/shapeliteralintersection")) {
			return new ShapeLiteralIntersectionRequest(this.gaia, file,  x_vector_1, y_vector_1, geometry_type_1, wkt_string_1, x_vector_2, y_vector_2, geometry_type_2, wkt_string_2);
		} else 
			return null;
	}
	
	public Request create_request(String file, List<SetId> set_ids, CharSequence wkt_attr_name, List<Double> x_vector, List<Double> y_vector, CharSequence geometry_type, CharSequence wkt_string) {
		if(file.equals("/shapeintersection")) {
			return new ShapeIntersectionRequest(this.gaia, file, set_ids, wkt_attr_name, x_vector, y_vector, geometry_type, wkt_string);
		} else 
			return null;
	}
	
	public Request create_request(String file, CharSequence type_id, CharSequence new_type_id, Map<CharSequence,CharSequence> transform_map) {
		if(file.equals("/registertypetransform")) {
			return new RegisterTypeTransformRequest(this.gaia, file, type_id, new_type_id, transform_map);
		} else 
			return null;
	}
	
	public Request create_request(String file, SetId world_id, SetId subworld_id, int start, int end,
			double min_x, double min_y, double max_x, double max_y, boolean doExtent) {
		if(file.equals("/gettracks"))
			return new GetTracksRequest(this.gaia, file, world_id, subworld_id, start, end, min_x, min_y, max_x, max_y, doExtent);
		else
			return null;
	}
}