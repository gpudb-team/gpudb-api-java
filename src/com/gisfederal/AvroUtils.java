package com.gisfederal;

import java.io.ByteArrayOutputStream;

import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import avro.java.gpudb.*;


public class AvroUtils {
	// this is really just used for debug
	public static String convert_to_string(SpecificRecord request) {
		Schema type_schema = request.getSchema();
		SpecificDatumWriter<SpecificRecord> writer = new SpecificDatumWriter<SpecificRecord>(type_schema);
		EncoderFactory encoder_factory = new EncoderFactory();

		// write out the object using a json encoder
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Encoder encoder = encoder_factory.jsonEncoder(type_schema, baos);
			writer.write(request, encoder);
			encoder.flush();
		} catch(Exception e) {
			System.err.println(e.toString());
		}

		return baos.toString();
	}

	public static byte[] convert_to_bytes(SpecificRecord request) {
		Schema type_schema = request.getSchema();
		SpecificDatumWriter<SpecificRecord> writer = new SpecificDatumWriter<SpecificRecord>(type_schema);
		EncoderFactory encoder_factory = new EncoderFactory();

		// write out the object using a json encoder
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Encoder encoder = encoder_factory.binaryEncoder(baos, null);
			writer.write(request, encoder);
			encoder.flush();
		} catch(Exception e) {
			System.err.println(e.toString());
		}

		return baos.toByteArray();
	}

	public static Object convert_to_object(Schema type_schema, byte[] ba) throws GPUdbException{
		SpecificDatumReader reader = null;
		DecoderFactory decoder_factory = new DecoderFactory();
		if(type_schema == gaia_response.SCHEMA$) {
			reader = new SpecificDatumReader<gaia_response>(type_schema);
		} else if(type_schema == register_type_response.SCHEMA$) {
			reader = new SpecificDatumReader<register_type_response>(type_schema);
		} else if(type_schema == plot2d_multiple_response.SCHEMA$) {
			reader = new SpecificDatumReader<plot2d_multiple_response>(type_schema);
		} else if(type_schema == bounding_box_response.SCHEMA$) {
			reader = new SpecificDatumReader<bounding_box_response>(type_schema);
		} else if(type_schema == select_response.SCHEMA$) {
			reader = new SpecificDatumReader<select_response>(type_schema);
		} else if(type_schema == max_min_response.SCHEMA$) {
			reader = new SpecificDatumReader<max_min_response>(type_schema);
		} else if(type_schema == sort_response.SCHEMA$) {
			reader = new SpecificDatumReader<sort_response>(type_schema);
		} else if(type_schema == histogram_response.SCHEMA$) {
			reader = new SpecificDatumReader<histogram_response>(type_schema);
		} else if(type_schema == filter_then_histogram_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_then_histogram_response>(type_schema);
		} else if(type_schema == filter_by_bounds_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_bounds_response>(type_schema);
		} else if(type_schema == group_by_response.SCHEMA$) {
			reader = new SpecificDatumReader<group_by_response>(type_schema);
		} else if(type_schema == make_bloom_response.SCHEMA$) {
			reader = new SpecificDatumReader<make_bloom_response>(type_schema);
		} else if(type_schema == filter_by_nai_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_nai_response>(type_schema);
		} else if(type_schema == filter_by_radius_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_radius_response>(type_schema);
		} else if(type_schema == store_group_by_response.SCHEMA$) {
			reader = new SpecificDatumReader<store_group_by_response>(type_schema);
		} else if(type_schema == filter_by_list_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_list_response>(type_schema);
		} else if(type_schema == join_response.SCHEMA$) {
			reader = new SpecificDatumReader<join_response>(type_schema);
		} else if(type_schema == join_setup_response.SCHEMA$) {
			reader = new SpecificDatumReader<join_setup_response>(type_schema);
		} else if(type_schema == join_incremental_response.SCHEMA$) {
			reader = new SpecificDatumReader<join_incremental_response>(type_schema);
		} else if(type_schema == copy_set_response.SCHEMA$) {
			reader = new SpecificDatumReader<copy_set_response>(type_schema);
		} else if(type_schema == cluster_response.SCHEMA$) {
			reader = new SpecificDatumReader<cluster_response>(type_schema);
		} else if(type_schema == clear_response.SCHEMA$) {
			reader = new SpecificDatumReader<clear_response>(type_schema);
		} else if(type_schema == stats_response.SCHEMA$) {
			reader = new SpecificDatumReader<stats_response>(type_schema);
		} else if(type_schema == get_set_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_set_response>(type_schema);
		} else if(type_schema == add_object_response.SCHEMA$) {
			reader = new SpecificDatumReader<add_object_response>(type_schema);
		} else if(type_schema == bulk_add_response.SCHEMA$) {
			reader = new SpecificDatumReader<bulk_add_response>(type_schema);
		} else if(type_schema == new_set_response.SCHEMA$) {
			reader = new SpecificDatumReader<new_set_response>(type_schema);
		} else if(type_schema == register_trigger_range_response.SCHEMA$) {
			reader = new SpecificDatumReader<register_trigger_range_response>(type_schema);
		} else if(type_schema == register_trigger_nai_response.SCHEMA$) {
			reader = new SpecificDatumReader<register_trigger_nai_response>(type_schema);
		} else if(type_schema == trigger_notification.SCHEMA$) {
			reader = new SpecificDatumReader<trigger_notification>(type_schema);
		} else if(type_schema == set_info_response.SCHEMA$) {
			reader = new SpecificDatumReader<set_info_response>(type_schema);
		} else if(type_schema == update_set_ttl_response.SCHEMA$) {
			reader = new SpecificDatumReader<update_set_ttl_response>(type_schema);
		} else if(type_schema == authenticate_users_response.SCHEMA$) {
			reader = new SpecificDatumReader<authenticate_users_response>(type_schema);
		} else if(type_schema == initialize_group_by_map_response.SCHEMA$) {
			reader = new SpecificDatumReader<initialize_group_by_map_response>(type_schema);
		} else if(type_schema == group_by_map_page_response.SCHEMA$) {
			reader = new SpecificDatumReader<group_by_map_page_response>(type_schema);
		} else if(type_schema == road_intersection_response.SCHEMA$) {
			reader = new SpecificDatumReader<road_intersection_response>(type_schema);
		} else if(type_schema == get_sorted_sets_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_sorted_sets_response>(type_schema);
		} else if(type_schema == get_type_info_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_type_info_response>(type_schema);
		} else if(type_schema == get_sets_by_type_info_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_sets_by_type_info_response>(type_schema);
		} else if(type_schema == shape_intersection_response.SCHEMA$) {
			reader = new SpecificDatumReader<shape_intersection_response>(type_schema);
		} else if(type_schema == get_objects_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_objects_response>(type_schema);
		} else if(type_schema == shape_literal_intersection_response.SCHEMA$) {
			reader = new SpecificDatumReader<shape_literal_intersection_response>(type_schema);
		} else if(type_schema == get_orphans_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_orphans_response>(type_schema);
		} else if(type_schema == register_type_transform_response.SCHEMA$) {
			reader = new SpecificDatumReader<register_type_transform_response>(type_schema);
		} else if(type_schema == predicate_join_response.SCHEMA$) {
			reader = new SpecificDatumReader<predicate_join_response>(type_schema);
		} else if(type_schema == turn_off_response.SCHEMA$) {
			reader = new SpecificDatumReader<turn_off_response>(type_schema);
		} else if(type_schema == spatial_query_response.SCHEMA$) {
			reader = new SpecificDatumReader<spatial_query_response>(type_schema);
		} else if(type_schema == spatial_set_query_response.SCHEMA$) {
			reader = new SpecificDatumReader<spatial_set_query_response>(type_schema);
		} else if(type_schema == filter_by_value_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_value_response>(type_schema);
		} else if(type_schema == get_tracks_response.SCHEMA$) {
			reader = new SpecificDatumReader<get_tracks_response>(type_schema);
		} else if(type_schema == status_response.SCHEMA$) {
			reader = new SpecificDatumReader<status_response>(type_schema);
		} else if(type_schema == delete_object_response.SCHEMA$) {
			reader = new SpecificDatumReader<delete_object_response>(type_schema);
		} else if(type_schema == update_object_response.SCHEMA$) {
			reader = new SpecificDatumReader<update_object_response>(type_schema);
		} else if(type_schema == generate_video_response.SCHEMA$) {
			reader = new SpecificDatumReader<generate_video_response>(type_schema);
		} else if(type_schema == generate_heatmap_video_response.SCHEMA$) {
			reader = new SpecificDatumReader<generate_heatmap_video_response>(type_schema);
		} else if(type_schema == unique_response.SCHEMA$) {
			reader = new SpecificDatumReader<unique_response>(type_schema);
		} else if(type_schema == server_status_response.SCHEMA$) {
			reader = new SpecificDatumReader<server_status_response>(type_schema);
		} else if(type_schema == merge_sets_response.SCHEMA$) {
			reader = new SpecificDatumReader<merge_sets_response>(type_schema);
		} else if(type_schema == convex_hull_response.SCHEMA$) {
			reader = new SpecificDatumReader<convex_hull_response>(type_schema);
		} else if(type_schema == filter_by_set_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_set_response>(type_schema);
		} else if(type_schema == filter_by_string_response.SCHEMA$) {
			reader = new SpecificDatumReader<filter_by_string_response>(type_schema);
		} else if(type_schema == populate_full_tracks_response.SCHEMA$) {
			reader = new SpecificDatumReader<populate_full_tracks_response>(type_schema);
		} else if(type_schema == add_symbol_response.SCHEMA$) {
			reader = new SpecificDatumReader<add_symbol_response>(type_schema);
		} else if (type_schema == register_type_with_annotations_response.SCHEMA$) {
			reader = new SpecificDatumReader<register_type_with_annotations_response>(type_schema);
		}
		else {
			System.err.println("Unsupported type schema:"+type_schema);
			throw new GPUdbException("Unsupported type schema:"+type_schema);
		}

		//System.out.println("After building decoder");

		// build the object
		Object response = null;
		try {
			Decoder decoder = decoder_factory.binaryDecoder(ba, null);
			response = reader.read(null, decoder);
		} catch(Exception e) {
			System.err.println(e.toString());
		}

		return response;
	}

	// the initial byte[] is assumed to be a gpudb_response object
	public static Object convert_to_object_from_gpudb_response(Schema type_schema, byte[] ba) throws GPUdbException {
		// decode the gpudb response first
		gaia_response g_response = (gaia_response)AvroUtils.convert_to_object(gaia_response.SCHEMA$, ba);

		// error check; if an error then throw the exception
		if(g_response.getStatus().toString().equals("ERROR")){
			// don't print just throwing; this way it can be handled further up; potentially an error might not be "wrong"
			//System.err.println("ERROR returned from server:"+g_response.getMessage().toString());
			throw new GPUdbException(g_response.getMessage().toString());
		}

		return AvroUtils.convert_to_object(type_schema, g_response.getData().array());
	}
}
