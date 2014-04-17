/*
 * Created on Jun 4, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.permata.queue.report.engine;

import java.io.File;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

/**
 * @author stanis
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class GenerateReportFile {

	private JasperPrint jasperPrint;
	private File file;
	private static final String EXTENSION_CSV = ".csv";
	private static final String EXTENSION_PDF = ".pdf";
	private static final String EXTENSION_TXT = ".txt";
	private static final String EXTENSION_XLS = ".xls";

	public GenerateReportFile(JasperPrint jp, File file) {
		this.jasperPrint = jp;
		this.file = file;
	}

	public void generateFileText() throws JRException {
		/*
		 * Exporting to plain text makes more sense when the report is
		 * completely (or mostly) text. Since tables and graphical elements
		 * don't translate to plain text very well. * === JasperReports for
		 * JavaDeveloper ===
		 */

		File fileTXT = new File(file.getAbsolutePath() + EXTENSION_TXT);
		JRTextExporter exporter = new JRTextExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, fileTXT);
		exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
				new Integer(10));
		exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
				new Integer(10));

		exporter.exportReport();
	}

	public void generateFileTXTfromCSV() throws JRException {
		File fileTXT = new File(file.getAbsolutePath() + EXTENSION_TXT);
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRCsvExporterParameter.OUTPUT_FILE, fileTXT);
		exporter.setParameter(JRCsvExporterParameter.OUTPUT_FILE, fileTXT);
		exporter.exportReport();
	}

	public void generateFileCSV() throws JRException {
		File fileCSV = new File(file.getAbsolutePath() + EXTENSION_CSV);
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRCsvExporterParameter.OUTPUT_FILE, fileCSV);
		exporter.exportReport();
	}

	public void generateFilePDF() throws JRException {
		File filePDF = new File(file.getAbsolutePath() + EXTENSION_PDF);
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE, filePDF);
		exporter.exportReport();
	}

	public void generateFileXLS() throws JRException {
		File fileXLS = new File(file.getAbsolutePath() + EXTENSION_XLS);
		JExcelApiExporter exporter = new JExcelApiExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, fileXLS);
		exporter.exportReport();
	}
}
