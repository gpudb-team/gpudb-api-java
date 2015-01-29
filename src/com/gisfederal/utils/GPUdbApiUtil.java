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
}
