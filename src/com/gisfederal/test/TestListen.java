package com.gisfederal.test;

import org.zeromq.ZMQ;

import avro.java.gaia.register_trigger_range_response;

import com.gisfederal.Gaia;
import com.gisfederal.SetId;
import com.gisfederal.Type;

public class TestListen {

	public TestListen() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gaia gaia = Gaia.newGaiaTrigger("127.0.0.1", 9191, "tcp://127.0.0.1:9001", "");
		Type type = gaia.create_type(BigPoint.class);
		
		//Gaia gaia2 = Gaia.newGaiaTrigger("127.0.0.1", 9191, "tcp://127.0.0.1:9001", "");
		// register trigger		
		/*
		register_trigger_range_response response = gaia.do_register_trigger(new SetId("MASTER"), "x", 1, 10, "group_id");
		System.out.print("TI:"+response.getTriggerId()+" termination:"+response.getTerminationTriggerId());
		
		//String trigger_id = response.getTerminationTriggerId().toString();
		String trigger_id = response.getTriggerId().toString();
		BigPoint p = (BigPoint)gaia.do_listen_for_this_trigger(trigger_id, type);
		System.out.print(p.toString());
		*/	
		BigPoint p = (BigPoint)gaia.do_listen_for_this_trigger("xxxx", type);
	}
}
