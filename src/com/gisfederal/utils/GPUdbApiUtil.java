package com.gisfederal.utils;

import java.util.HashMap;
import java.util.Map;

public class GPUdbApiUtil {
	
	public static final String UPSERT_MODE_PARAM = "update_on_existing_pk";
	public static final Map<CharSequence, CharSequence> updateOnExistingPk;
	public static final Map<CharSequence, CharSequence> silentOnExistingPk;
	
	static {
		updateOnExistingPk = new HashMap<CharSequence, CharSequence>();
		updateOnExistingPk.put(UPSERT_MODE_PARAM, "TRUE");
		silentOnExistingPk = new HashMap<CharSequence, CharSequence>();
		silentOnExistingPk.put(UPSERT_MODE_PARAM, "FALSE");
	}

	public static Map<CharSequence, CharSequence> getUpdateonexistingpk() {
		return updateOnExistingPk;
	}

	public static Map<CharSequence, CharSequence> getSilentonexistingpk() {
		return silentOnExistingPk;
	}
	
	public static Map<CharSequence, CharSequence> getSetPropertiesMap(boolean allowDuplicateChildren, boolean protectedSet) {
		Map<CharSequence, CharSequence> setPropetiesMap = new HashMap<CharSequence, CharSequence>();
		
		setPropetiesMap.put("allow_duplicate_children", Boolean.toString(allowDuplicateChildren));
		setPropetiesMap.put("protected", Boolean.toString(protectedSet));
		
		return setPropetiesMap;
	}
	
	public static Map<CharSequence, CharSequence> getEmptyParams() {
		return new HashMap<CharSequence, CharSequence>();
	}
}
