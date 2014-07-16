/**
 * 
 */
package com.gisfederal.test;

import java.lang.reflect.Method;

/**
 * @author pjacobs
 *
 */
public class TestNonJunit {

	/**
	 * 
	 */
	public TestNonJunit() {
		// TODO Auto-generated constructor stub
	}
	
	// have the user specify the name of the method (or "" for all) and then use reflection to find the methods and run them
	public static void main(String args[]) {		
		System.out.println("Setup class");
		try {
			TestGpudb.setUpClass();
		} catch (Exception e) {
			System.err.println("Class set up error "+e.toString());
		}
		TestGpudb test = new TestGpudb();
		
		String methodName = "";
		if(args.length > 0) {
			methodName = args[0];
		}
		System.out.println("Method to run: |"+methodName+"|");
		
		Method[] methods = test.getClass().getMethods();
		boolean success = true;
		for(Method method : methods) {
			if(!method.getName().contains("test")) {
				// want to avoid some object related messages like "wait", "notify" etc.
				continue;
			}
			// if its an empty string then do all of them
			if(methodName.equals(method.getName()) || methodName.equals("")){											
				try {
					method.invoke(test, null); //run the method
				} catch(Exception e){
					System.err.println("The method "+method.getName()+" FAILED with exception "+e.toString());
					success = false;
				}
				if(success) {
					System.out.println("The method "+method.getName()+" SUCCEEDED");
				}
				if(!methodName.equals("")) {
					break;
				}
			}
		}		
	}
}
