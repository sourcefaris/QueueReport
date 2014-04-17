package com.permata.queue.main;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.permata.queue.forms.QueueReport;

public class MainQueueReport {

	public static void main(String[] args) throws Exception {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		final JFrame frame = new JFrame();
		QueueReport report = new QueueReport();

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
				super.windowClosing(e);
				
			}
			
		});
		Dimension dim = new Dimension();
		dim.setSize(580, 250);

		frame.setSize(dim);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Queuing Report");
		// frame.setResizable(false);
		frame.setVisible(true);

		frame.getContentPane().add(report);

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.setVisible(true);
			}
		});

	}

}
