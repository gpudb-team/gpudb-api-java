package com.gisfederal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.gisfederal.request.RequestConnection;

// storage mapping setid to named set
public class ClientNSStore {	
	private ConcurrentMap<SetId,NamedSet> store;

	private ClientNSStore(){
		this.store = new ConcurrentHashMap<SetId,NamedSet>();
	}

	public static synchronized ClientNSStore getInstance(RequestConnection rc) {
		return new ClientNSStore();
	}

	public void put(SetId key, NamedSet value) {
		this.store.put(key, value);
	}

	public boolean contains(SetId key){
		return this.store.containsKey(key);
	}
	
	public NamedSet get(SetId key) {		
		return this.store.get(key);
	}

	// empty it all out
	public void clear() {
		this.store.clear();
	}

	// remove this setid -> namedset
	public void clear(SetId si) {		
		this.store.remove(si);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Clone is not allowed.");
	}
}