package com.permata.queue.forms;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.jeta.forms.components.panel.FormPanel;
import com.mysql.jdbc.Connection;
import com.permata.queue.common.Connector;
import com.permata.queue.common.CustomDialog;
import com.permata.queue.report.engine.NamedParameterStatement;
import com.permata.queue.report.engine.ReportBuilder;
import com.permata.queue.report.engine.ReportException;
import com.permata.queue.report.engine.ReportResultSetInt;

public class QueueReport extends JPanel implements ReportResultSetInt {
	private FormPanel panel = null;
	private JLabel lblMsg = null;
	private JButton btnPrintWaitTime = null;
	private JButton btnPrintSummary = null;
	private JButton btnPrintWaitTimeCS = null ;
    private Connection connection = null;
	private Connector connector = new Connector();
	// private SQLExecutor se = new SQLExecutor();
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final DateFormat dateFormat2 = new SimpleDateFormat("dd MMM yyyy");
	private final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private final String QUERY_SUMMARY = "SELECT * FROM vw_rpt_summary WHERE time_daily_name BETWEEN '08:00' AND curtime()";
	private int refreshTime=Integer.parseInt(System.getProperty("refresh.time"));
	private String timeTeller=System.getProperty("time.teller");
	private String timeCS=System.getProperty("time.cs");
	
	private JButton btnSave = null;
	private JButton btnEdit = null;
	private JLabel lblStatus = null;
	private JTextField txtServer = null;
	private JTextField txtTanggal = null;
	
	private String QUERY_WAIT_TIME = null;
	private String status = null;

	public QueueReport() throws SQLException {
		init();
		initConnection();

	}

	public void initConnection() {
		lblMsg.setText("");
		boolean b = false;
		try {

			connection = connector.getConnection();
			
			((JLabel) panel.getComponentByName("lbl1")).setText("<html>Laporan Nasabah <br/>Teller Menunggu > "+timeTeller+" menit</html>");
			((JLabel) panel.getComponentByName("lbl2")).setText("<html>Laporan Nasabah <br/>CS Menunggu > "+timeCS+" menit</html>");
			b = true;
			
		} catch (SQLException e) {
			//
			// e.printStackTrace();
			lblMsg.setText(e.getMessage());

		} finally {
			if (connection == null) {

			} else {
				try {
					connector.closeConnection(connection);
				} catch (SQLException e) {
				}
			}
			txtServer.setText(connector.getServer());
		}
		setConnection(b);
	}

	private void setConnection(boolean b) {
		if (b) {
			lblStatus.setText("Connected");
			lblStatus.setForeground(Color.BLUE);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(true);
			txtServer.setEditable(false);
			txtTanggal.setEnabled(true);
			btnPrintSummary.setEnabled(true);
			btnPrintWaitTime.setEnabled(true);
			btnPrintWaitTimeCS.setEnabled(true);
		} else {
			lblStatus.setText("Not Connected");
			lblStatus.setForeground(Color.RED);
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			txtServer.setEditable(true);
			txtTanggal.setEnabled(false);
			btnPrintSummary.setEnabled(false);
			btnPrintWaitTime.setEnabled(false);
			btnPrintWaitTimeCS.setEnabled(false);
		}
	}

	public void initTimer() {
		// 300000 : 5000
		Timer timer = new Timer(refreshTime*60*1000, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Date date;
				try {
					date = new Date(getServerTime());
//					int minutes = date.getMinutes();
					System.out.println(date);
					 int minutes = date.getSeconds();
					if (minutes < 5) {
						PrintReportSummary report = new PrintReportSummary();
						report.start();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					lblMsg.setText(e.getMessage());
					
				}//
			
				// System.out.println("check");s
			}
		});
		timer.start();

	}

	public void init() {
		try {
			panel = new FormPanel("com/permata/queue/res/main-form.jfrm");
			btnPrintWaitTime = (JButton) panel.getComponentByName("btnPrint1");
			btnPrintSummary = (JButton) panel.getComponentByName("btnPrint2");
			btnPrintWaitTimeCS = (JButton) panel.getComponentByName("btnPrint3");
			lblMsg = (JLabel) panel.getComponentByName("lblMsg");
			btnSave = (JButton) panel.getComponentByName("btnSave");
			btnEdit = (JButton) panel.getComponentByName("btnEdit");
			lblStatus = (JLabel) panel.getComponentByName("lblStatus");
			txtServer = (JTextField) panel.getComponentByName("txtServer");
			txtTanggal = (JTextField) panel.getComponentByName("txtTanggal");
			btnPrintWaitTime.addActionListener(actionListener);
			btnPrintWaitTimeCS.addActionListener(actionListener);
			btnPrintSummary.addActionListener(actionListener);
			btnEdit.addActionListener(actionListener);
			btnSave.addActionListener(actionListener);
			this.add(panel);
			initTimer();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnPrintWaitTime)) {
				status = "teller";
				PrintReportWaitTime t = new PrintReportWaitTime();
				t.start();
				return;
			}
			
			if (e.getSource().equals(btnPrintWaitTimeCS)) {
				status = "cs";
				PrintReportWaitTime t = new PrintReportWaitTime();
				t.start();
				return;
			}
			
			if (e.getSource().equals(btnPrintSummary)) {
				PrintReportSummary t = new PrintReportSummary();
				t.start();
			}
			if (e.getSource().equals(btnEdit)) {
				btnSave.setEnabled(true);
				btnEdit.setEnabled(false);
				txtServer.setEditable(true);
			}
			if (e.getSource().equals(btnSave)) {
				SaveThread st = new SaveThread();
				st.start();
			}
		}
	};

	private class SaveThread extends Thread {

		public void run() {
			// TODO Auto-generated method stub
			CustomDialog dialog = new CustomDialog(null, false);
			dialog.setVisible(true);

			try {
				lblMsg.setText("");
				connector.setServer(txtServer.getText());

			} catch (Exception e) {
				// System.out.println("a");
				e.printStackTrace();
				lblMsg.setText(e.getMessage());
			} finally {
				initConnection();
				dialog.setVisible(false);
			}

		}
	}

	public class PrintReportWaitTime extends Thread {
		public void run() {
			try {
				lblMsg.setText("");
				if(status.equals("teller"))
					buildQueryWaitTime();
				else buildQueryWaitTimeCS();
			} catch (Exception e) {
				lblMsg.setText(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public class PrintReportSummary extends Thread {
		public void run() {
			try {
				lblMsg.setText("");
				buildQuerySummary();
			} catch (Exception e) {
				lblMsg.setText(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void buildQuerySummary() throws Exception {

		listResult = new ArrayList();
		ResultSet resultSet = null;
		Date currentDate = new Date(System.currentTimeMillis());
		String endTime = timeFormat.format(currentDate);
		try {
			map = new HashMap();
			connection = connector.getConnection();
			map.put("BRANCH", getBranchCode());
			NamedParameterStatement nps = new NamedParameterStatement(
					connection, QUERY_SUMMARY);
	
			resultSet = nps.executeQuery();
			jasperPath = "com/permata/queue/jasper/ReportQueueSummary.jasper";

			if (!resultSet.isBeforeFirst())
				throw new Exception("Data tidak dapat ditemukan");

			listResult.add(resultSet);
			runReport();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		}

	}

	private String getBranchCode() throws SQLException {
		Connector cr = null;
		Connection c = null;
		ResultSet rs=null;
		try {
			cr = new Connector();
			c = cr.getConnection();
			Statement s = c.createStatement();
			String query = "SELECT value FROM parameter WHERE code='branchcode'";
			rs = s.executeQuery(query);

//			System.out.println(rs.getString("value"));
			return rs.next() ? rs.getString("value") : "";

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			rs.close();
			cr.closeConnection(c);
		
		}

	}
	
//	private String getWaitTimeTeller() throws SQLException {
//		Connector cr = null;
//		Connection c = null;
//		ResultSet rs=null;
//		try {
//			cr = new Connector();
//			c = cr.getConnection();
//			Statement s = c.createStatement();
//			String query = "SELECT value FROM parameter WHERE code='timeteller'";
//			rs = s.executeQuery(query);
//
////			System.out.println(rs.getString("value"));
//			return rs.next() ? rs.getString("value") : "";
//
//		} catch (Exception e) {
//			// TODO: handle exception
//
//			e.printStackTrace();
//			throw new SQLException(e);
//		} finally {
//			rs.close();
//			cr.closeConnection(c);
//		
//		}
//
//	}
	private long getServerTime() throws SQLException {
		Connector cr = null;
		Connection c = null;
		ResultSet rs=null;
		try {
			cr = new Connector();
			c = cr.getConnection();
			Statement s = c.createStatement();
			String query = "SELECT now()";
			rs = s.executeQuery(query);

//			System.out.println(rs.getString("value"));
			return rs.next() ? rs.getTimestamp(1).getTime() : 0;

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			if(rs!=null)
			rs.close();
			
			cr.closeConnection(c);
		
		}

	}

	public void buildQueryWaitTime() throws Exception {

		listResult = new ArrayList();
		ResultSet resultSet = null;
		Date currentDate = new Date(System.currentTimeMillis());

		try {
			map = new HashMap();
			map.put("BRANCH", getBranchCode());
			map.put("WAIT_TIME", timeTeller);
			map.put("SERVICE_", "Teller");
			map.put("PERIODE_DATE", txtTanggal.getText());
			connection = connector.getConnection();
			QUERY_WAIT_TIME = "SELECT * FROM vw_rpt_wait_time WHERE TIME_TO_SEC(time_diff)>:limit and date(TIME)=date('"+txtTanggal.getText()+"')";			
			NamedParameterStatement nps = new NamedParameterStatement(
					connection, QUERY_WAIT_TIME);
			nps.setLong("limit",Long.parseLong( timeTeller)*60);
			System.out.println(QUERY_WAIT_TIME);
			resultSet = nps.executeQuery();
			jasperPath = "com/permata/queue/jasper/ReportQueueWaitTime.jasper";
			if (!resultSet.isBeforeFirst())
				throw new Exception("Data tidak dapat ditemukan");

			listResult.add(resultSet);
			runReport();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		}

	}
	
	public void buildQueryWaitTimeCS() throws Exception {

		listResult = new ArrayList();
		ResultSet resultSet = null;
		Date currentDate = new Date(System.currentTimeMillis());

		try {
			map = new HashMap();
			map.put("BRANCH", getBranchCode());
			map.put("WAIT_TIME", timeCS);
			map.put("SERVICE_", "Customer Service");
			map.put("PERIODE_DATE", txtTanggal.getText());
			connection = connector.getConnection();
			QUERY_WAIT_TIME = "SELECT * FROM vw_rpt_wait_time_cs WHERE TIME_TO_SEC(time_diff)>:limit and date(TIME)=date('"+txtTanggal.getText()+"')";
			NamedParameterStatement nps = new NamedParameterStatement(
					connection, QUERY_WAIT_TIME);
			nps.setLong("limit",Long.parseLong( timeCS)*60);
			System.out.println(QUERY_WAIT_TIME);
			resultSet = nps.executeQuery();
			jasperPath = "com/permata/queue/jasper/ReportQueueWaitTime.jasper";
			if (!resultSet.isBeforeFirst())
				throw new Exception("Data tidak dapat ditemukan");

			listResult.add(resultSet);
			runReport();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		}

	}

	public void runReport() {
		try {
			ReportBuilder rb = new ReportBuilder(this);
			rb.showReport();
		} catch (ReportException ex) {
			ex.printStackTrace();
		}
	}


	public Map getParameter() {

		return map;
	}


	public List getContent() {

		return listResult;
	}


	public String getTitle() {

		return null;
	}


	public String getResource() {
		return jasperPath;
	}

	private String jasperPath = null;
	private HashMap map = null;
	private List listResult = null;

}
