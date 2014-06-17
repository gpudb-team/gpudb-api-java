package com.gisfederal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class NamedSetIterator implements Iterator {	
	private NamedSet ns;	   // set we are iterating over
	private int size;          // size of set
	private int pageSize;      // page size (i.e. length; start to end for list requests)
	
	private List<Object> list; // internal list of objects; the objects we've gotten from the server so far [NOTE: use arraylist for O(1) get]
	private Iterator listIter; // iterator for the current internal list
	
	// if this is Master, then this has all the childrenSet otherwise it just has this set
	private List<NamedSet> allNamedSets = new ArrayList<NamedSet>(); 
	
	// sent to the server to get the list of objects; exclusive [start, end] with a zero start index
	private int startIndex;    
	private int endIndex;
	
	private void updateInternalList() {	
		
		//System.out.println(" %%% UpdateInternalList entering - start and end index are :" + startIndex + " $$ " + endIndex);
		
		// check if this is a valid subset; if not just build an empty list
		if(startIndex >= size) {
			// We may have data in other sets....
			ns = pickAndRemoveFirst(allNamedSets);
			if( ns != null ) {
				size = ns.size();
				//System.out.println(" %%% UpdateInternalList entering size is " + size);
				startIndex = 0;
				endIndex = pageSize >= size ? size-1 : pageSize;
				getDataFromGaia(list, startIndex, endIndex);				
			} else {
				list = new ArrayList();
			}
		} else {
			// valid; get the next list
			getDataFromGaia(list, startIndex, endIndex);
		}		
		listIter = list.iterator();
		
		// adjust the indices for the next request
		startIndex = endIndex+1; // so if we got to the end already this pushes us over
		endIndex = startIndex + pageSize - 1;		
		
		// If we are going beyond size of the set, don't
		if( endIndex > (size-1)) {
			endIndex = size-1;
		}
		//System.out.println(" %%% UpdateInternalList exiting - start and end index are :" + startIndex + " $$ " + endIndex);

	}
	
	private void getDataFromGaia(List<Object> list2, int startIndex,
			int endIndex) {
		
		//System.out.println(" ## Fetch data from GAIA for set " + ns.get_setid() + "  SI and EI are " + startIndex + " $$ " + endIndex);
		
		list = (List<Object>)ns.list(startIndex, endIndex);		
	}

	public NamedSetIterator(NamedSet _ns, int _pageSize) {		

		ns = _ns;
		if( ns.getType().isParent() ) {			
			Collection<NamedSet> nses = ns.getChildren();
			for( NamedSet ns : nses ) {
				allNamedSets.add(ns);
			}
		} else {
			allNamedSets.add(ns);
		}
		
		//System.out.println(" @@@@@ Total sets we have = " + allNamedSets.size());
		
		pageSize = _pageSize-1;
		//System.out.println(" @@@@@ Pagesize is = " + pageSize);
			
		// find the total size of the first or our only set in our collection of sets
		ns = pickAndRemoveFirst(allNamedSets);
		
		size = ns.size();
		//System.out.println(" @@@@@ Set size in init for first set is = " + size);
			
		// get the initial list; set the initial start and end
		this.startIndex = 0;
		this.endIndex = pageSize >= size ? size-1 : pageSize;
		updateInternalList();
		
	}

	@Override
	public boolean hasNext() {
		// if there are elements in our internal list then true
		if(listIter.hasNext()) {
			return true;
		}
		
		// if there are more elements to grab from the server then true
		if(startIndex < size || nextChildHasData(allNamedSets) ) {
			// recall (size-1) would be the last element in the set; so it's a valid start index
			return true;
		}
		
		// otherwise...
		return false;
	}

	@Override
	public Object next() throws NoSuchElementException  {
		// get the next element in our internal list if there is one
		if(listIter.hasNext()){
			return listIter.next();
		}
		
		// otherwise update internal list; this updates the listIter too
		updateInternalList();
		
		// if there are no more elements to grab then this will throw
		return listIter.next(); 						
	}

	@Override
	public void remove() {
		listIter.remove();		
	}	

	private boolean nextChildHasData(List<NamedSet> allNamedSets) {
		boolean hasData = false;
		if( allNamedSets.size() > 0 ) {
			hasData = allNamedSets.get(0).size() > 0;
		}
		//System.out.println(" nextChildHasData returning " + hasData);
		return hasData;
	}

	private NamedSet pickAndRemoveFirst(List<NamedSet> allNamedSets) {
		NamedSet ns = null;
		Iterator<NamedSet> it = allNamedSets.iterator();
		while( it.hasNext() ) {
			ns = it.next();
			it.remove();
			break; // Just get the first one....
		}
		return ns;
	}
}
