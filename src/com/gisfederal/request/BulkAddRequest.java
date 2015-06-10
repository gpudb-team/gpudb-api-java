/**
 *
 */
package com.gisfederal.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.log4j.Logger;
import org.xerial.snappy.Snappy;

import avro.java.gpudb.bulk_add_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.GenericObject;
import com.gisfederal.NamedSet;
import com.gisfederal.Type;
import com.gisfederal.utils.IgnoreOnIngest;

public class BulkAddRequest extends Request {

	public BulkAddRequest(GPUdb gPUdb, String file, List<Object> list_obj,
			NamedSet ns,
			Map<java.lang.CharSequence, java.lang.CharSequence> params) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(BulkAddRequest.class);

		long start = System.currentTimeMillis();
		this.setId = ns.get_setid().toString();

		List<ByteBuffer> binaryList = new ArrayList<ByteBuffer>();

		binaryList = fastEncode(list_obj, ns);
		/*
		 * for(Object obj : list_obj) { // Use the static call from
		 * AddObjectRequest ByteBuffer serialized =
		 * AddObjectRequest.encodeObject(gPUdb, file, obj, ns, log); // add to
		 * list binaryList.add(serialized); }
		 */
		this.log.debug("BulkAddRequest new way data in millis "
				+ (System.currentTimeMillis() - start));

		// list of string encodings
		List<java.lang.CharSequence> list_str = new ArrayList<java.lang.CharSequence>();
		for (int i = 0; i < binaryList.size(); i++) {
			list_str.add("");
		}

		// build avro object
		bulk_add_request request = new bulk_add_request(
				ns.get_setid().get_id(), binaryList, list_str, "BINARY", params);

		byte[] dataToSend = null;
		byte[] bulk_data_bytes = AvroUtils.convert_to_bytes(request);
		if (gPUdb.isSnappyCompress()) {
			try {
				dataToSend = Snappy.compress(bulk_data_bytes);
				log.debug(" Compressed from " + bulk_data_bytes.length
						+ " to :" + dataToSend.length);
			} catch (IOException e) {
				log.error(e);
				throw new GPUdbException(e.getMessage());
			}
		} else {
			dataToSend = bulk_data_bytes;
		}
		this.requestData = new RequestData(dataToSend);

		// Create log msg for audit
		createAuditMsg(request);
	}

	@Override
	protected void setContentType(HttpURLConnection connection) {
		if (gPUdb.isSnappyCompress()) {
			connection.setRequestProperty("Content-type",
					"application/x-snappy");
		} else {
			connection.setRequestProperty("Content-type",
					"application/octet-stream");
		}
	}

	private void createAuditMsg(bulk_add_request request) {

		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");

		msg.append("[binary packet size=");
		msg.append(request.getList().size());
		msg.append("]");

		msg.append("[json packet size=");
		msg.append(request.getListStr().size());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

	private List<ByteBuffer> fastEncode(List<Object> list_obj, NamedSet ns) {
		List<ByteBuffer> binaryList = new ArrayList<ByteBuffer>();

		Type type = ns.getType();
		Schema schema = type.getAvroSchema();
		try {
			GenericDatumWriter<GenericRecord> writer =
			    new GenericDatumWriter<GenericRecord>(schema);
			GenericRecord record = new GenericData.Record(schema);

			boolean genericObjectType = type.getTypeClass() == GenericObject.class;

			Field[] fields = null;
			for (Iterator<Object> localIterator = list_obj.iterator(); localIterator
					.hasNext();) {

				Object obj = localIterator.next();
				EncoderFactory encoderFactory = new EncoderFactory();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				if (obj instanceof GenericRecord) {
				    record = (GenericRecord) obj;
				}
				else if( genericObjectType ) {
					GenericObject go = (GenericObject)obj;
					Iterator<Entry<String, String>> iter = go.dataMap.entrySet().iterator();
					while(iter.hasNext()) {
						Map.Entry<String, String> pairs = iter.next();

						// figure out the type of this field; use that to decode the value
						String field_name = pairs.getKey();
						String field_value = pairs.getValue(); // stored as string in the map
						Schema.Field field = schema.getField(field_name);
						Schema.Type field_type =field.schema().getType();

						if(Schema.Type.STRING == field_type) {
							record.put(field_name, field_value);
						} else if(Schema.Type.FLOAT == field_type) {
							record.put(field_name, Float.valueOf(field_value));
						} else if(Schema.Type.DOUBLE == field_type) {
							record.put(field_name, Double.valueOf(field_value));
						} else if(Schema.Type.INT == field_type) {
							record.put(field_name, Integer.valueOf(field_value));
						} else if(Schema.Type.LONG == field_type) {
							record.put(field_name, Long.valueOf(field_value));
						} else {
							log.error("Unsupported type:"+field_type);
						}
					}
				} else {
					if (fields == null) {
						fields = obj.getClass().getFields();
					}

					for (Field field : fields) {
						String fieldName = field.getName();

						Class<?> fieldType = field.getType();

						Annotation annotation = field
								.getAnnotation(IgnoreOnIngest.class);
						if (!(annotation instanceof IgnoreOnIngest)) {
							if (fieldType == Double.TYPE)
								record.put(fieldName,
										Double.valueOf(field.getDouble(obj)));
							else if (fieldType == Float.TYPE)
								record.put(fieldName,
										Float.valueOf(field.getFloat(obj)));
							else if (fieldType == Integer.TYPE)
								record.put(fieldName,
										Integer.valueOf(field.getInt(obj)));
							else if (fieldType == Long.TYPE)
								record.put(fieldName,
										Long.valueOf(field.getLong(obj)));
							else if (fieldType == String.class)
								record.put(fieldName, field.get(obj).toString());
							else if (fieldType == ByteBuffer.class)
								record.put(fieldName, field.get(obj));
							else
								System.out.println("Unhandled field; fieldType:"
										+ fieldType);
						}
					}

				}
				Encoder encoder = encoderFactory.binaryEncoder(baos, null);
				writer.write(record, encoder);
				encoder.flush();

				baos.flush();
				baos.close();

				ByteBuffer serialized = ByteBuffer.wrap(baos.toByteArray());
				binaryList.add(serialized);
			}
		} catch (Exception e) {
			System.out.println(" Exception received " + e);
			throw new GPUdbException("Error writing avro encoding of object:"
					+ e.toString());
		}
		return binaryList;
	}

}
