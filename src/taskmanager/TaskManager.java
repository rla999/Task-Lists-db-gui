/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.*;
import org.apache.derby.jdbc.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author tduffy
 */
public class TaskManager extends javax.swing.JFrame implements WindowListener {

    private Connection con;
    private ResultSet rs;
    private ResultSet rs_tablenames;
    private Statement stmt;
    ArrayList<String> tablenames = new ArrayList<>();
//    private String addList = JOptionPane.showInputDialog("New Task List Name (type default if not known): ");
    private String addList = "default_table";
    private String removeList = "";
    DatabaseMetaData dbmd;
//    private String addList;
//    Tasklistadder tasklistadder;
//    Tasklistadder tasklistadder = new Tasklistadder();
//    tasklistadder.setVisible(true);
//    private String addList = tasklistadder.txtAddTaskList.getText();

    private final String dbURI = "jdbc:derby:ListDB;create=true";

    /**
     * Creates new form TaskManager
     */
    TaskAdder tskAdd;

    public TaskManager() {
//        this.addList = tasklistadder.txtAddTaskList.getText();
//        this.addList = txtAddList.getText();
        initComponents();
        setDBSystemDir();
        createDBTable();
        getResultSet();
        displayResults();
        // tskAdd.setVisible(true);
        pnlInsert.setVisible(false);
        pack();
    }

    private void setDBSystemDir() {
        // Decide on the db system directory: <userhome>/contact/
        String userHomeDir = System.getProperty("user.home", ".");
        String systemDir = userHomeDir + "/" + "listDB";

        // Set the db system directory.
        System.setProperty("derby.system.home", systemDir);
    }

    private void createDBTable() {
        if (stmt != null){
            try{
                stmt.close();
                rs.close();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            }
        if(addList.isEmpty() == false){
            try {
                DriverManager.registerDriver(new EmbeddedDriver());
                con = DriverManager.getConnection(dbURI);
                String sql = "create table " + addList + " (name varchar(30), status varchar(30))";
                stmt = con.createStatement();
                stmt.execute(sql);
            } catch (SQLException ex) {
                if (ex.getErrorCode() != 30000) {
                    //Error Code 30000: Table already exists - not an error!
                    JOptionPane.showMessageDialog(this, "createTable: " + ex.getMessage());
                }
            }
        }
    }
    private void deleteDBTable() {
        if (stmt != null){
            try{
                stmt.close();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            }
        if(removeList.isEmpty() == false){
            try {
                String sql = "drop table " + removeList;
                stmt = con.createStatement();
                stmt.execute(sql);
            } catch (SQLException ex) {
                if (ex.getErrorCode() != 30000) {
                    //Error Code 30000: Table already exists - not an error!
                    JOptionPane.showMessageDialog(this, "deleteTable: " + ex.getMessage());
                }
            }
        }
    }

    private void getResultSet() {
        if(addList.isEmpty() == false){
            try {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery("select " + addList + ".* from " + addList);
    //            DatabaseMetaData dbmd = con.getMetaData();
    //            String[] types = {"TABLE"};
    //            ResultSet rs_tablenames = dbmd.getTables(null, null, "%", types);
    //            while(rs_tablenames.next()){
    //                JOptionPane.showMessageDialog(this, rs_tablenames.getString("TABLE_NAME"));
    //            } 

                //ResultSet is scrollable and updatable
                rs.first();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "getResultSet: " + ex.getMessage());
            }
        }
    }

    private void displayResults() {
        if(addList.isEmpty() == false){
            try {
                txtName.setText(rs.getString("name"));
                txtStatus.setText(rs.getString("status"));
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 20000) {
                    //Invalid Cursor State - no records in ResultSet
//                    JOptionPane.showMessageDialog(this, "Click New Task to get started");
                    //In case the only record was deleted...
                    txtName.setText("");
                    txtStatus.setText("");

                } else {
                    JOptionPane.showMessageDialog(this, "displayResults: " + ex.getMessage());
                }
            }
        }
    }
    private void addList(){
        String displaylist = "";
        ArrayList<String> tableList = new ArrayList<String>();
        try {
            if (rs_tablenames != null)
                rs_tablenames.close();
            dbmd = con.getMetaData();
            String[] types = {"TABLE"};
            rs_tablenames = dbmd.getTables(null, null, "%", types);
            while (rs_tablenames.next()) {
                displaylist += rs_tablenames.getString("TABLE_NAME") + "\n";
                tableList.add(rs_tablenames.getString("TABLE_NAME"));
            }
            
            //ResultSet is scrollable and updatable
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "btnAddList: " + ex.getMessage());
        }
            tskAdd.txtTableNames.setText(displaylist);
            tskAdd.tableSelector.setModel(new DefaultComboBoxModel(tableList.toArray()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInput = new javax.swing.JPanel();
        btnAddList = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtStatus = new JTextField("");
        ((AbstractDocument)txtStatus.getDocument()).setDocumentFilter(new LimitedDocFilter(Pattern.compile("^[YNyn]$"), 1));
        pnlButtons = new javax.swing.JPanel();
        pnlNav = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        pnlControls = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        pnlInsert = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Task Manager");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlInput.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        btnAddList.setText("Open List Selector");
        btnAddList.setToolTipText("Add a task list.");
        btnAddList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddListActionPerformed(evt);
            }
        });
        pnlInput.add(btnAddList);
        pnlInput.add(jLabel3);

        jLabel1.setText("Task Name");
        pnlInput.add(jLabel1);

        txtName.setToolTipText("Enter name and description of the task.");
        pnlInput.add(txtName);

        jLabel2.setText("Task completed? (Y/N)");
        pnlInput.add(jLabel2);

        txtStatus.setToolTipText("Status of the task (y for yes, n for no)");
        txtStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusActionPerformed(evt);
            }
        });
        pnlInput.add(txtStatus);

        getContentPane().add(pnlInput, java.awt.BorderLayout.PAGE_START);

        pnlButtons.setLayout(new java.awt.GridLayout(2, 1));

        btnFirst.setText("<<");
        btnFirst.setToolTipText("First record in the task list.");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlNav.add(btnFirst);

        btnPrevious.setText("<");
        btnPrevious.setToolTipText("Previous record in the task list.");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });
        pnlNav.add(btnPrevious);

        btnNext.setText(">");
        btnNext.setToolTipText("Next record in the task list.");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlNav.add(btnNext);

        btnLast.setText(">>");
        btnLast.setToolTipText("Last/final record in the task list.");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlNav.add(btnLast);

        pnlButtons.add(pnlNav);

        btnNew.setText("New Task");
        btnNew.setToolTipText("Create a new  task.");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlControls.add(btnNew);

        btnUpdate.setText("Update Task");
        btnUpdate.setToolTipText("Update information about the task (e.g. whether it's been completed or not)");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlControls.add(btnUpdate);

        btnDelete.setText("Delete Task");
        btnDelete.setToolTipText("Delete the task from the list.");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlControls.add(btnDelete);

        pnlButtons.add(pnlControls);

        getContentPane().add(pnlButtons, java.awt.BorderLayout.CENTER);

        btnInsert.setText("Insert Task");
        btnInsert.setToolTipText("Insert a task into the list after clicking New Task.");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        pnlInsert.add(btnInsert);

        btnCancel.setText("Cancel");
        btnCancel.setToolTipText("Cancel the app?");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlInsert.add(btnCancel);

        getContentPane().add(pnlInsert, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(439, 300));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

        try {
            rs.first();
            displayResults();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnFirst: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No table selected");
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        try {
            if (rs.previous()) {
                displayResults();
            } else {
                rs.first();
                displayResults();
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnPrevious: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No table selected");
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        try {
            if (rs.next()) {
                displayResults();
            } else {
                rs.last();
                displayResults();
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnNext: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No table selected");
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        try {
            rs.last();
            displayResults();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnLast: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No table selected");
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            rs.updateString("name", txtName.getText());
            rs.updateString("status", txtStatus.getText());

            rs.updateRow();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnUpdate: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No task to update!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Delete Record?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == 0) {
                int currentRow = rs.getRow() - 1;
                if (currentRow == 0) {
                    currentRow = 1;
                }
                rs.deleteRow();
                rs.close();
                rs = stmt.executeQuery("select * from " + addList);
                rs.absolute(currentRow);
                displayResults();
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnDelete: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No task to delete!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtStatus.setText("");
        pnlButtons.setVisible(false);
        pnlInsert.setVisible(true);
        pack();
        try {
            rs.moveToInsertRow();
            displayResults();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnNew: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No table selected");
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        try {
            rs.updateString("name", txtName.getText());
            rs.updateString("status", txtStatus.getText());

            rs.insertRow();
            //refresh the ResultSet
            rs = stmt.executeQuery("select * from " + addList);
            rs.last();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnInsert: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No table selected");
        } finally {
            pnlInsert.setVisible(false);
            pnlButtons.setVisible(true);
            pack();
            displayResults();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            rs.moveToCurrentRow();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(this, "btnCancel: " + ex.getMessage());
        } finally {
            pnlInsert.setVisible(false);
            pnlButtons.setVisible(true);
            pack();
            displayResults();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            rs = null;
            stmt = null;
            con.close();
            con = null;
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_formWindowClosing

    private void btnAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddListActionPerformed
        // TODO add your handling code here:
        if (tskAdd == null){
            tskAdd = new TaskAdder();
            tskAdd.addWindowListener(this);
            tskAdd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            tskAdd.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null, "Window already open");
        }
        addList();
//        String displaylist = "";
//        ArrayList<String> tableList = new ArrayList<String>();
//        try {
//            if (rs_tablenames != null)
//                rs_tablenames.close();
//            dbmd = con.getMetaData();
//            String[] types = {"TABLE"};
//            rs_tablenames = dbmd.getTables(null, null, "%", types);
//            while (rs_tablenames.next()) {
//                displaylist += rs_tablenames.getString("TABLE_NAME") + "\n";
//                tableList.add(rs_tablenames.getString("TABLE_NAME"));
//            }
//            
//            //ResultSet is scrollable and updatable
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "btnAddList: " + ex.getMessage());
//        }
//            tskAdd.txtTableNames.setText(displaylist);
//            tskAdd.tableSelector.setModel(new DefaultComboBoxModel(tableList.toArray()));
//        for (int i=0; i < tablenames.size();i++){
//            displaylist += tablenames.get(i) +"\n";       
//        }

    }//GEN-LAST:event_btnAddListActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusActionPerformed

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
            java.util.logging.Logger.getLogger(TaskManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaskManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaskManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaskManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddList;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlControls;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlInsert;
    private javax.swing.JPanel pnlNav;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {

        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        if((tskAdd.getList().isEmpty() == false)){
            //addList function
            addList = tskAdd.getList();
            tablenames.add(addList);
            createDBTable();
            getResultSet();
            displayResults();
            boolean tableAdded = tskAdd.getTableAdded();
            tskAdd = null;
            if(tableAdded == true && tskAdd == null){
                tskAdd = new TaskAdder();
                tskAdd.addWindowListener(this);
                tskAdd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                addList();
                tskAdd.setVisible(true);
            }
        }

        else if(tskAdd.getDeleteList().isEmpty() == false){
            removeList = tskAdd.getDeleteList();
            tablenames.remove(removeList);
            deleteDBTable();
            displayResults();
            tskAdd = null;
                if (tskAdd == null){
                tskAdd = new TaskAdder();
                tskAdd.addWindowListener(this);
                tskAdd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tskAdd.setVisible(true);
        }
            else{
                JOptionPane.showMessageDialog(null, "Window already open");
            }
        addList();
        }
        else
            {JOptionPane.showMessageDialog(null, "No selection made.");
            tskAdd = null;
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
