package com.permata.queue.report.engine;

import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.view.JRSaveContributor;

public class JRPlainTextSaveContributor extends JRSaveContributor {

	private static final String EXTENSION_XLS = ".txt";

	public JRPlainTextSaveContributor(Locale locale,
			ResourceBundle resourceBundle) {
		super(locale, resourceBundle);
	}

	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		return file.getName().toLowerCase().endsWith(EXTENSION_XLS);
	}

	public String getDescription() {
		return "TEXT (*.txt)";
	}

	public void save(JasperPrint jasperPrint, File file) throws JRException {
		if (!file.getName().endsWith(EXTENSION_XLS)) {
			file = new File(file.getAbsolutePath() + EXTENSION_XLS);
		}

		if (!file.exists()
				|| JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
						MessageFormat.format(java.util.ResourceBundle
								.getBundle("net/sf/jasperreports/view/viewer")
								.getString("file.exists"), new Object[] { file
								.getName() }), java.util.ResourceBundle
								.getBundle("net/sf/jasperreports/view/viewer")
								.getString("save"),
						JOptionPane.OK_CANCEL_OPTION)) {
			JRTextExporter exporter = new JRTextExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
			exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
					new Integer(10));
			exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
					new Integer(10));
			exporter.exportReport();
		}
	}

}
