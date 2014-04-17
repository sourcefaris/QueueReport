package com.permata.queue.report.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CustomResultDataSource implements JRDataSource {
	
	private Object bean;
	
	private Iterator list;
	
	private Object[] param;
	
	public CustomResultDataSource(List list){
		this.list = list.iterator();
	}
	
	public CustomResultDataSource(List list, Object[] param){
		this.list = list.iterator();
		this.param = param;
	}

	public Object getFieldValue(JRField jrField) throws JRException {
		Object value = null;
		String field = jrField.getName();
		try {
			if(bean instanceof Map){
			    Map map = (Map) bean;
				value = map.get(field);
			}
			else if(bean instanceof Object[]){
				int index = getFieldIndex(field);
				if(index > -1){
					Object[] beanArray = (Object[]) bean;
					value = beanArray[index];					
				}
			}
			else{
				Map map = BeanCustomHelper.getValuesMap(bean);
				value = map.get(field);
			}
		} catch (Exception e) {}
		return value;
	}
	
	private int getFieldIndex(String field){
		int index = -1;
		for(int i=0;i<param.length;i++){
			String key = (String) param[i];
			if(key.equals(field)){
				index = i;
				break;
			}
		}
		return index;
	}

	public boolean next() throws JRException {
		bean = list.hasNext() ? list.next() : null;
		return (bean!=null);
	}

}
