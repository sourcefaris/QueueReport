/*
 * Created on Nov 2, 2006
 *
 */
package com.permata.queue.report.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author dthobi
 * 
 */
public class BeanCustomHelper {

	private static final String ID_FIELD = "id";

	private static final String CLASS_FIELD = "class";

	private static List listKeys = new ArrayList();

	public static Map getValuesMap(Object o) throws Exception {
		Map values = new LinkedHashMap();
		Map vals = BeanUtils.describe(o);
		listKeys.clear();
		vals.remove(CLASS_FIELD);
		if (vals.containsKey(ID_FIELD)) {
			Object idObj = PropertyUtils.getProperty(o, ID_FIELD);
			Map valsid = BeanUtils.describe(idObj);
			if (valsid.remove(CLASS_FIELD) != null) {
				boolean isRemoveID = false;
				StringBuffer sb = null;
				for (Iterator iter = valsid.keySet().iterator(); iter.hasNext();) {
					String key = (String) iter.next();
					listKeys.add(key);
					Object value = PropertyUtils.getProperty(idObj, key);
					values.put(key, value);
					isRemoveID = true;
				}
				if (isRemoveID)
					vals.remove(ID_FIELD);
			}
		}
		for (Iterator iter = vals.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			listKeys.add(key);
			values.put(key, PropertyUtils.getProperty(o, key));
		}
		return values;
	}

	public static List getObjectKey() {
		return listKeys;
	}
}
