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

import avro.java.gpudb.gaia_response;



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

	public static <T> Object convert_to_object(Schema type_schema, byte[] ba) throws GPUdbException{
		DecoderFactory decoder_factory = new DecoderFactory();
		
		//System.out.println("After building decoder");

		SpecificDatumReader<T> reader = new SpecificDatumReader<T>(type_schema);
		
		// build the object
		Object response = null;
		try {
			Decoder decoder = decoder_factory.binaryDecoder(ba, null);
			response = reader.read(null, decoder);
		} catch(Exception e) {
			e.printStackTrace();
			throw new GPUdbException("Unsupported type schema :" + e.getMessage());
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
