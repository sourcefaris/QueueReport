/*
 * Created on Apr 30, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.permata.queue.report.engine;

import java.util.List;
import java.util.Map;

/**
 * @author dthobi
 *
 */
public interface ReportInt {
//	public InputStream getResource();
	public Map getParameter();
	public List getContent();
	public String getTitle();
	public String getResource();
}
