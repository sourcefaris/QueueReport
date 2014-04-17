package com.permata.queue.report.engine;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.JRViewer;

public class ReportCustomViewer extends JRViewer {

	public ReportCustomViewer(JasperPrint jrPrint) {
		super(jrPrint);
	}

	public ReportCustomViewer(String fileName, boolean isXML)
			throws JRException {
		super(fileName, isXML);
	}

	public ReportCustomViewer(InputStream is, boolean isXML) throws JRException {
		super(is, isXML);
	}

	public ReportCustomViewer(JasperPrint jrPrint, Locale locale) {
		super(jrPrint, locale);
	}

	public ReportCustomViewer(String fileName, boolean isXML, Locale locale)
			throws JRException {
		super(fileName, isXML, locale);
	}

	public ReportCustomViewer(InputStream is, boolean isXML, Locale locale)
			throws JRException {
		super(is, isXML, locale);
	}

	public ReportCustomViewer(JasperPrint jrPrint, Locale locale,
			ResourceBundle resBundle) {
		super(jrPrint, locale, resBundle);
	}

	public ReportCustomViewer(String fileName, boolean isXML, Locale locale,
			ResourceBundle resBundle) throws JRException {
		super(fileName, isXML, locale, resBundle);
	}

	public ReportCustomViewer(InputStream is, boolean isXML, Locale locale,
			ResourceBundle resBundle) throws JRException {
		super(is, isXML, locale, resBundle);
	}

	protected void initSaveContributors() {
		final String[] DEFAULT_CONTRIBUTORS = {
				"com.permata.queue.report.engine.JRPlainTextSaveContributor",
				"net.sf.jasperreports.view.save.JRPdfSaveContributor",
				"net.sf.jasperreports.view.save.JRCsvSaveContributor" };

		for (int i = 0; i < DEFAULT_CONTRIBUTORS.length; i++) {
			try {
				Class saveContribClass = JRClassLoader
						.loadClassForName(DEFAULT_CONTRIBUTORS[i]);
				Constructor constructor = saveContribClass
						.getConstructor(new Class[] { java.util.Locale.class,
								java.util.ResourceBundle.class });
				JRSaveContributor saveContrib = (JRSaveContributor) constructor
						.newInstance(new Object[] { getLocale(), null });
				saveContributors.add(saveContrib);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
