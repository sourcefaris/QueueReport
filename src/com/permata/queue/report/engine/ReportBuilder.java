package com.permata.queue.report.engine;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author dthobi
 * 
 */
public class ReportBuilder {

	private ReportInt report;

	private ReportCustomExecuteViewer rcv;

	public ReportBuilder(ReportInt report) throws ReportException {
		this.report = report;
		generateReport();
	}

	private void generateReport() throws ReportException {
		CustomResultDataSource crds = null;
		if (report instanceof ReportFieldInt) {
			ReportFieldInt rfi = (ReportFieldInt) report;
			crds = new CustomResultDataSource(rfi.getContent(),
					rfi.getFieldParam());
			buildJasperPrint(rfi.getResource(), rfi.getParameter(), crds);
		} else if (report instanceof ReportResultSetInt) {
			ResultSet resultSet = null;
			if (report.getContent().size() > 0)
				resultSet = (ResultSet) report.getContent().get(0);
			buildJasperPrint(report.getResource(), report.getParameter(),
					new JRResultSetDataSource(resultSet));
		} else {
			crds = new CustomResultDataSource(report.getContent());
			buildJasperPrint(report.getResource(), report.getParameter(), crds);
		}
	}

	private JasperPrint jasperPrint;

	private void buildJasperPrint(InputStream in, Map param, JRDataSource ds)
			throws ReportException {
		try {
			jasperPrint = JasperFillManager.fillReport(in, param, ds);
		} catch (JRException e) {
			e.printStackTrace();
			throw new ReportException("Failed to show report");
		}
	}

	// parameter jasperreport
	private void buildJasperPrint(String jasperPath, Map param, JRDataSource ds)
			throws ReportException {
		try {
			JasperReport jr = (JasperReport) JRLoader
					.loadObjectFromLocation(jasperPath);
			jasperPrint = JasperFillManager.fillReport(jr, param, ds);
		} catch (JRException e) {
			e.printStackTrace();
			throw new ReportException("Failed to show report");
		}
	}

	public void showReport() {
//		rcv = new ReportCustomExecuteViewer(jasperPrint, false);
//		rcv.setTitle(report.getTitle());
//		rcv.show();
//		JasperViewer.viewReport(jasperPrint, false);

		JasperViewer.viewReport(jasperPrint, false);

	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

}
