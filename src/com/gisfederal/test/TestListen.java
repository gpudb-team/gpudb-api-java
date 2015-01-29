package com.gisfederal.test;

import org.zeromq.ZMQ;

import avro.java.gpudb.register_trigger_range_response;

import com.gisfederal.GPUdb;
import com.gisfederal.SetId;
import com.gisfederal.Type;

public class TestListen {

	public TestListen() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GPUdb gPUdb = GPUdb.newGpudbTrigger("127.0.0.1", 9191, "tcp://127.0.0.1:9001", "", "admin", "changeme");
		Type type = gPUdb.create_type(BigPoint.class);
		
		//Gpudb gpudb2 = Gpudb.newGpudbTrigger("127.0.0.1", 9191, "tcp://127.0.0.1:9001", "");
		// register trigger		
		/*
		register_trigger_range_response response = gpudb.do_register_trigger(new SetId("MASTER"), "x", 1, 10, "group_id");
		System.out.print("TI:"+response.getTriggerId()+" termination:"+response.getTerminationTriggerId());
		
		//String trigger_id = response.getTerminationTriggerId().toString();
		String trigger_id = response.getTriggerId().toString();
		BigPoint p = (BigPoint)gpudb.do_listen_for_this_trigger(trigger_id, type);
		System.out.print(p.toString());
		*/	
		BigPoint p = (BigPoint)gPUdb.do_listen_for_this_trigger("xxxx", type);
	}
}
