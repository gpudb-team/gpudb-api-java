package com.gisfederal.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;
import com.gisfederal.SetId;
import com.gisfederal.Type;

public class TestHAProcessor {

	private static int NUM_THREADS = 1;

	public static void main(String[] args) throws Exception {

		System.out.println("Building gpudb for cleaning GPUDB ");
		String gpudbURL = System.getProperty("GPUDB_URL", "http://172.30.20.27:9191");
		GPUdb gPUdb = new GPUdb(gpudbURL, "", "", "");

		try {
			gPUdb.do_clear();
		} catch (Exception e) {
			// Whatever !!
		}

		ExecutorService executor = null;
		executor = Executors.newFixedThreadPool(10);
		for (int ii = 0; ii < NUM_THREADS; ii++) {
            Runnable worker = new TestUpdateDeleteHA(ii);
            executor.execute(worker);
        }
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}
}

class TestUpdateDeleteHA implements Runnable {

	private final GPUdb gPUdb;
	private final Type type;
	private NamedSet ns;
	final int id;

	final int CNT = 500000;

	public static final Log logger = LogFactory.getLog(TestUpdateDeleteHA.class);

	public TestUpdateDeleteHA(int id) {
		this.id = id;
		// Code executed before the first test method
		System.out.println("Building gpudb for thread id " + id);
		String gpudbURL = System.getProperty("GPUDB_URL", "http://172.30.20.27:9191");
		gPUdb = new GPUdb(gpudbURL, "", "admin", "changeme");
		System.out.println("Built gpudb for thread id " + id);
		type = gPUdb.create_type(BigPoint.class);
		try {
			ns = gPUdb.newSingleNamedSet(new SetId("HASETC"+id), type);
		} catch( Exception e) {
			System.out.println(" Set probably already present....");
			ns = gPUdb.getNamedSet(new SetId("HASETC"+id), type);
		}
	}

	@Override
	public void run() {

		addData();

		System.out.println("Data added.....");

		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		updateDeleteData();
		*/
		System.out.println("All shenanigans completed....");
	}

	private void updateDeleteData() {

		for( int ii = 0; ii < CNT; ) {
			String select = "(x == " + ii + ") AND (y == " + ii + ")";
			long count = gPUdb.do_select_delete(ns, select);
			ii += 2;
		}

		for( int ii = 1; ii < CNT; ) {

			Map<CharSequence,CharSequence> data = new HashMap<CharSequence,CharSequence>();
			data.put("x", "222222");
			data.put("y", "333333");
			data.put("msg_id", "HATESTCHANGED"+ii);
			data.put("source", "HATESTCHANGED"+ii);
			data.put("group_id", "HATESTCHANGED"+ii);

			String select = "(x == " + ii + ") AND (y == " + ii + ")";
			long count = gPUdb.do_select_update(ns, data, select);
			ii += 2;
		}
	}

	private void addData() {
		int x = 0;
		int y = 0;
		List<Object> list = new ArrayList<Object>();
		for( int ii=0; ii<CNT; ii++ ) {
			BigPoint p = new BigPoint(UUID.randomUUID().toString(),  "MSGID"+ii, x, y, ii, "SRC"+ii, "GROUP"+ii);
			list.add(p);
			if( list.size() >= 50 ) {
				ns.add_list(list);
				list.clear();
			}
			x++;
			y++;
		}
		if( list.size() > 0 ) {
			ns.add_list(list);
		}
		//assertTrue(ns.size() == CNT);
	}
}