package com.permata.queue.common;


public class CustomDialog extends javax.swing.JDialog {

    public CustomDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        if(parent==null){
        	setLocationRelativeTo(null);
        }
    }

    public void setVisible(boolean b) {
    	progressBar.setIndeterminate(b);
        super.setVisible(b);
    }
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        progressBar = new javax.swing.JProgressBar();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Loading...");
        getContentPane().setLayout(new java.awt.GridBagLayout());
        progressBar.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(progressBar, gridBagConstraints);
        pack();
    }
    private javax.swing.JProgressBar progressBar;

}
