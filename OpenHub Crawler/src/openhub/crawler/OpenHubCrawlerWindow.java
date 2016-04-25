/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

/**
 *
 * @author mateusz
 */
public class OpenHubCrawlerWindow extends javax.swing.JFrame {

    private OpenHubCrawler crawler;
    private Integer orgMinID;
    private Integer orgMaxID;

    /**
     * Creates new form OpenHubCrawlerWindow
     */
    public OpenHubCrawlerWindow() {
        initComponents();
        UILogger.getInstance().initialize(this.logTextArea);
        crawler = new OpenHubCrawler();
        crawler.initialize(this.scheduledJobsList);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jobName = new javax.swing.JTextField();
        jobTypeComboBox = new javax.swing.JComboBox<>();
        addJobButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        scheduledJobsList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        removeJobButton = new javax.swing.JButton();
        clearAllJobsButton = new javax.swing.JButton();
        minIDSpinner = new javax.swing.JSpinner();
        maxIDSpinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        clearLogButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        downloadOrgsList = new javax.swing.JMenuItem();
        downloadAllProjects = new javax.swing.JMenuItem();
        downloadCommits = new javax.swing.JMenuItem();
        downloadProjectInfo = new javax.swing.JMenuItem();
        addApiKey = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jobName.setText("Name");

        jobTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Organization", "Project" }));

        addJobButton.setText("Add");
        addJobButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJobButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(scheduledJobsList);

        jLabel2.setText("Scheduled jobs:");

        removeJobButton.setText("Remove");
        removeJobButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeJobButtonActionPerformed(evt);
            }
        });

        clearAllJobsButton.setText("Clear all");
        clearAllJobsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllJobsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jobName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addJobButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jobTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(removeJobButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(minIDSpinner))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(clearAllJobsButton)
                                    .addComponent(maxIDSpinner))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jobTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addJobButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearAllJobsButton)
                    .addComponent(removeJobButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maxIDSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minIDSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addContainerGap())
        );

        logTextArea.setEditable(false);
        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        jScrollPane1.setViewportView(logTextArea);

        jLabel1.setText("Log:");

        clearLogButton.setText("Clear log");
        clearLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearLogButtonActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        downloadOrgsList.setText("Download organization list");
        downloadOrgsList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadOrgsListActionPerformed(evt);
            }
        });
        jMenu1.add(downloadOrgsList);

        downloadAllProjects.setText("Download projects for all orgs");
        downloadAllProjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadAllProjectsActionPerformed(evt);
            }
        });
        jMenu1.add(downloadAllProjects);

        downloadCommits.setText("Download commits");
        downloadCommits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadCommitsActionPerformed(evt);
            }
        });
        jMenu1.add(downloadCommits);

        downloadProjectInfo.setText("Download project info");
        downloadProjectInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadProjectInfoActionPerformed(evt);
            }
        });
        jMenu1.add(downloadProjectInfo);

        addApiKey.setText("Add API key");
        addApiKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addApiKeyActionPerformed(evt);
            }
        });
        jMenu1.add(addApiKey);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearLogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(clearLogButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        crawler.start();
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        crawler.stop();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void clearLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearLogButtonActionPerformed
        this.logTextArea.setText(null);
    }//GEN-LAST:event_clearLogButtonActionPerformed

    private void removeJobButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeJobButtonActionPerformed
        if (scheduledJobsList.getSelectedIndex() > -1) {
            crawler.removeJob(scheduledJobsList.getSelectedIndex());
        }
    }//GEN-LAST:event_removeJobButtonActionPerformed

    private void addJobButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJobButtonActionPerformed
        String type = String.valueOf(this.jobTypeComboBox.getSelectedItem());
        String name = this.jobName.getText();
        crawler.scheduleJob(name, type);
    }//GEN-LAST:event_addJobButtonActionPerformed

    private void clearAllJobsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllJobsButtonActionPerformed
        crawler.clearJobs();
    }//GEN-LAST:event_clearAllJobsButtonActionPerformed

    private void downloadOrgsListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadOrgsListActionPerformed
        crawler.downloadOrganizations();
    }//GEN-LAST:event_downloadOrgsListActionPerformed

    private void addApiKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addApiKeyActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_addApiKeyActionPerformed

    private void downloadAllProjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadAllProjectsActionPerformed
//        crawler.downloadProjectsForAllOrganizations();
        crawler.downloadProjectsForOrganizations((int) minIDSpinner.getValue(), (int) maxIDSpinner.getValue());
    }//GEN-LAST:event_downloadAllProjectsActionPerformed

    private void downloadCommitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadCommitsActionPerformed
//         crawler.downloadCommits();
int val = (int)maxIDSpinner.getValue();
System.out.println(val);
        crawler.downloadCommitsMultithread((int) minIDSpinner.getValue(), (int) maxIDSpinner.getValue(), 50);
    }//GEN-LAST:event_downloadCommitsActionPerformed

    private void downloadProjectInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadProjectInfoActionPerformed
        crawler.downloadProjectInfo();
    }//GEN-LAST:event_downloadProjectInfoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OpenHubCrawlerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpenHubCrawlerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpenHubCrawlerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpenHubCrawlerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        System.out.println(args[0]);
        if (args.length >= 1 && args[0].equals("fileConfig")) {
            OpenHubCrawler crawler = new OpenHubCrawler();
            crawler.initialize();
        } else {
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new OpenHubCrawlerWindow().setVisible(true);
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addApiKey;
    private javax.swing.JButton addJobButton;
    private javax.swing.JButton clearAllJobsButton;
    private javax.swing.JButton clearLogButton;
    private javax.swing.JMenuItem downloadAllProjects;
    private javax.swing.JMenuItem downloadCommits;
    private javax.swing.JMenuItem downloadOrgsList;
    private javax.swing.JMenuItem downloadProjectInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jobName;
    private javax.swing.JComboBox<String> jobTypeComboBox;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JSpinner maxIDSpinner;
    private javax.swing.JSpinner minIDSpinner;
    private javax.swing.JButton removeJobButton;
    private javax.swing.JList<String> scheduledJobsList;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
