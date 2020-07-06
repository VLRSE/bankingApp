/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import com.classes.*;
import java.awt.HeadlessException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CSC
 */
public final class Banking extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private Boolean newbtn = false;
    private DecimalFormat formatter = new DecimalFormat("#,000.00");
    private account acc = new account();
    private checking chk = new checking();
    
     
    private String driver  = "com.mysql.jdbc.Driver";
    private String domain = "jdbc:mysql://127.0.0.1:3306/bankaccounts?autoReconnect=true&useSSL=false";
    private String user  = "root";
    private String pass  = "K€nntW0rt6225";
    
    private Connection conn = null;
    private int count = 0;
    private Statement stm;
    private ResultSet rs;

    /** Creates new form NewJFrame */
    public Banking() {
        initComponents();
        connect();
        loadAccounts();
        loadSavings();
        loadLoan();
        loadChecking();
        
    }
    
     private void connect(){
        try{
            Class.forName(driver);            
            conn = (DriverManager.getConnection(domain, user, pass));
        }catch(ClassNotFoundException | SQLException e){
          JOptionPane.showMessageDialog(null, "connection error");
        }
    }
    public void loadSavings() {
            setCount(0);
            String strSQL = "select * from savings inner join accounts where savings.accountno=accounts.accountno";
            try {
                setStm(getConn().createStatement());
                setRs(getStm().executeQuery(strSQL));
                List<String> v;
                DefaultTableModel m = (DefaultTableModel) this.jTable1.getModel();
                m.setRowCount(getCount());
                while (getRs().next()) {
                setCount(getCount() + 1);
                v = new ArrayList<>();
                v.add(getRs().getString(1));
                
                v.add(getRs().getString("DateOpened").substring(0, getRs().getString("DateOpened").indexOf(" ")));
                v.add(getFormatter().format(getRs().getDouble("Balance")));
                v.add(getRs().getString("InterestRate"));
                double in = Double.parseDouble(getRs().getString("InterestRate"));
                double bal = Double.parseDouble(getRs().getString("balance"));
                v.add(getFormatter().format((bal * in) / 100));
                v.add(getFormatter().format(bal + ((bal * in) / 100)));
                m.addRow(v.toArray());
                }
                m.getRowCount();
            } catch (NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
    }
    
    public void loadChecking() {
        setCount(0);
        String strSQL = "select * from checking inner join accounts where checking.accountno=accounts.accountno";
        try {
            setStm(getConn().createStatement());
            setRs(getStm().executeQuery(strSQL));
        
            List<String> v;
            DefaultTableModel m = (DefaultTableModel) this.jTable2.getModel();
            m.setRowCount(getCount());
            while (     getRs().next()) {
                setCount(getCount() + 1);
            v = new ArrayList<>();
            v.add( getRs().getString(1));
            v.add( getRs().getString("DateOpened").substring(0, getRs().getString("DateOpened").indexOf(" ")));
            v.add(getFormatter().format(getRs().getDouble("Balance")));
            v.add(getRs().getString("ServiceCharge"));
            double in = Double.parseDouble(getRs().getString("ServiceCharge"));
            double bal = Double.parseDouble(getRs().getString("balance"));
            v.add( getFormatter().format((bal * in) / 100));
            m.addRow(v.toArray());
        }
        m.getRowCount();
        
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
     
    public void loadLoan() {
        setCount(0);
        String strSQL = "select * from loan inner join accounts where loan.accountno=accounts.accountno";
        try {
            setStm(getConn().createStatement());
            setRs(getStm().executeQuery(strSQL));
            List<String> v ;
            DefaultTableModel m = (DefaultTableModel) this.jTable3.getModel();
            m.setRowCount(getCount());
            while (     getRs().next()) {
                setCount(getCount() + 1);
                v = new ArrayList<>();
                v.add(          getRs().getString(1));
                v.add(          getRs().getString("DateOpened").substring(0, getRs().getString("DateOpened").indexOf(" ")));
                v.add(getFormatter().format(getRs().getDouble("Balance")));
                v.add(          getRs().getString("InterestRate"));
                double in = Double.parseDouble(getRs().getString("InterestRate"));
                double bal = Double.parseDouble(getRs().getString("balance"));
                v.add(          getFormatter().format((bal * in) / 100));
                v.add(getFormatter().format(getRs().getDouble("Payment")));
                double pay = Double.parseDouble(getRs().getString("Payment"));
                v.add(          getFormatter().format(((bal * in) / 100) + pay));
                m.addRow(v.toArray());
            }
            m.getRowCount();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    private void jTabbedPaneCheckingKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        int ind = evt.getKeyCode();
        if (ind == 96 || ind == 48) {
            this.dispose();
        } else if (ind == 97 || ind == 49) {
            this.jTabbedPaneChecking.setSelectedIndex(0);
        } else if (ind == 98 || ind == 50) {
            this.jTabbedPaneChecking.setSelectedIndex(1);
            loadSavings();
        } else if (ind == 99 || ind == 51) {
            this.jTabbedPaneChecking.setSelectedIndex(2);
            loadChecking();
        } else if (ind == 100 || ind == 52) {
            this.jTabbedPaneChecking.setSelectedIndex(3);
            loadLoan();
        } else if (ind == 101 || ind == 53) {
            this.jTabbedPaneChecking.setSelectedIndex(4);
            loadAccounts();
        } else if (ind == 102 || ind == 54) {
            this.jTabbedPaneChecking.setSelectedIndex(5);
        } else if (ind == 103 || ind == 55) {
            this.jTabbedPaneChecking.setSelectedIndex(6);
        } else if (ind == 83) {
            this.jRadioButtonSavings.setSelected(true);
            this.jRadioButtonChecking.setSelected(false);
            this.jRadioButtonLoan.setSelected(false);
            this.jFormattedTextFieldinterestRate.setEditable(true);
            this.jFormattedTextFieldInterestRateLoan.setEditable(false);
            this.jFormattedTextFieldServiceCharge.setEditable(false);
            this.jFormattedTextFieldPayment.setEditable(false);
            this.jRadioButtonChecking.setSelected(false);
            this.jRadioButtonLoan.setSelected(false);
            this.jFormattedTextFieldInterestRateLoan.setText("");
            this.jFormattedTextFieldPayment.setText("");
            this.jFormattedTextFieldServiceCharge.setText("");
        } else if (ind == 76) {
            this.jRadioButtonSavings.setSelected(false);
            this.jRadioButtonChecking.setSelected(false);
            this.jRadioButtonLoan.setSelected(true);
            this.jFormattedTextFieldinterestRate.setEditable(false);
            this.jFormattedTextFieldInterestRateLoan.setEditable(true);
            this.jFormattedTextFieldServiceCharge.setEditable(false);
            this.jFormattedTextFieldPayment.setEditable(true);
            this.jRadioButtonChecking.setSelected(false);
            this.jRadioButtonSavings.setSelected(false);
            this.jFormattedTextFieldServiceCharge.setText("");
            this.jFormattedTextFieldinterestRate.setText("");
        } else if (ind == 67) {
            this.jRadioButtonSavings.setSelected(false);
            this.jRadioButtonChecking.setSelected(true);
            this.jRadioButtonLoan.setSelected(false);
            this.jFormattedTextFieldinterestRate.setEditable(false);
            this.jFormattedTextFieldInterestRateLoan.setEditable(false);
            this.jFormattedTextFieldServiceCharge.setEditable(true);
            this.jFormattedTextFieldPayment.setEditable(false);
            this.jRadioButtonSavings.setSelected(false);
            this.jRadioButtonLoan.setSelected(false);
            this.jFormattedTextFieldInterestRateLoan.setText("");
            this.jFormattedTextFieldPayment.setText("");
            this.jFormattedTextFieldinterestRate.setText("");
        }
        System.out.print("" + ind);
    }
     
     
    public void loadYr(String sql) {
        setCount(0);
        boolean found = false;
        String strSQL = sql;
        try {
            setStm(getConn().createStatement());
            setRs(getStm().executeQuery(strSQL));
            List<String> v;
            DefaultTableModel m = (DefaultTableModel) this.jTable4.getModel();
            m.setRowCount(getCount());
            
            while (getRs().next()) {
                setCount(getCount() + 1);
                found = true;
                v = new ArrayList<>();
                v.add(getRs().getString(1));
                v.add(          getRs().getString("DateOpened").substring(0, getRs().getString("DateOpened").indexOf(" ")));
                v.add(getFormatter().format(getRs().getDouble("Balance")));
                m.addRow(v.toArray());
            }
            m.getRowCount();
            if (found == false) {
                JOptionPane.showMessageDialog(null, "Record not Found");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
     
    public void loadAccounts() {
        setCount(0);
        String strSQL = "select * from Accounts";
        try {
            setStm(getConn().createStatement());
            setRs(getStm().executeQuery(strSQL));
            List<String> v ;
            DefaultTableModel m = (DefaultTableModel) this.jTableAccounts.getModel();
            m.setRowCount(getCount());
            while (getRs().next()) {
                setCount(getCount() + 1);
            v = new ArrayList<>();
            v.add(getRs().getString(1));
//            int accNo = 
//if (accNo == (rs.getInt(1))) {
//JOptionPane.showMessageDialog(null, "Duplicate Account Enter Another AccountNo");
//this.jTextFieldAccountNo.setText(null);
//this.jTextFieldAccountNo.requestFocus();
//}
            v.add(getRs().getString("DateOpened").substring(0, getRs().getString("DateOpened").indexOf(" ")));
            v.add(getFormatter().format(getRs().getDouble("Balance")));
            m.addRow(v.toArray());
            }
             m.getRowCount();
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneChecking = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        btnSearchAcct = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableAccounts = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        btnFilterYr = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jRadioButtonSavings = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextFieldinterestRate = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jRadioButtonChecking = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextFieldServiceCharge = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jRadioButtonLoan = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldInterestRateLoan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextFieldPayment = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPaneChecking.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        btnSearchAcct.setText("Search AccountNo");
        btnSearchAcct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchAcctActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnSearchAcct, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 110, Short.MAX_VALUE)))
                .addGap(76, 76, 76))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchAcct, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPaneChecking.addTab("[1]Search", jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AccountNo", "DateOpened", "Balance", "InterestRate", "Interest", "NetBalance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        jTabbedPaneChecking.addTab("[2]Savings", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account No", "DateOpened", "Balance", "Service Charge", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        jTabbedPaneChecking.addTab("[3]Checking", jPanel3);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account No", "DateOpened", "Balance", "InterestRate", "Interest", "Payment", "Amount Due"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        jTabbedPaneChecking.addTab("[4]Loan", jPanel4);

        jTableAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AccountNo", "DateOpened", "Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableAccounts);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        jTabbedPaneChecking.addTab("[5]Accounts", jPanel5);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        btnFilterYr.setText("Filter");
        btnFilterYr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterYrActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account No", "DateOpened", "Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnFilterYr, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnFilterYr, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
        );

        jTabbedPaneChecking.addTab("[6]Filter by Year", jPanel6);

        jLabel1.setText("Account NO.");

        jLabel2.setText("Date Opened(yyyy-mm-dd)");

        jLabel3.setText("Balance");

        jRadioButtonSavings.setText("[s]SAVINGS");

        jLabel4.setText("Interest Rate");

        jRadioButtonChecking.setText("[c]CHECKING");

        jLabel5.setText("Interest Rate");

        jRadioButtonLoan.setText("[l]LOANS");

        jLabel6.setText("Service Charge");

        jLabel7.setText("Payment");

        jButton3.setText("Save");

        jButton4.setText("New");

        jButton5.setText("Update");

        jButton6.setText("Delete");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButtonSavings, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jFormattedTextFieldinterestRate, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(281, 281, 281))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 147, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jRadioButtonChecking, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextFieldServiceCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jRadioButtonLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextFieldInterestRateLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 7, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonSavings)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldinterestRate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonChecking)
                    .addComponent(jFormattedTextFieldServiceCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonLoan)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldInterestRateLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jTabbedPaneChecking.addTab("[7]Manage Account", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );

        jTabbedPaneChecking.addTab("[0]Exit", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPaneChecking)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneChecking))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void btnFilterYrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterYrActionPerformed
        
        loadYr(jTextField3.getText());
    }//GEN-LAST:event_btnFilterYrActionPerformed

    private void btnSearchAcctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAcctActionPerformed
       
    }//GEN-LAST:event_btnSearchAcctActionPerformed

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
            java.util.logging.Logger.getLogger(Banking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Banking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Banking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Banking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Banking().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilterYr;
    private javax.swing.JButton btnSearchAcct;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JTextField jFormattedTextFieldInterestRateLoan;
    private javax.swing.JTextField jFormattedTextFieldPayment;
    private javax.swing.JTextField jFormattedTextFieldServiceCharge;
    private javax.swing.JTextField jFormattedTextFieldinterestRate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButtonChecking;
    private javax.swing.JRadioButton jRadioButtonLoan;
    private javax.swing.JRadioButton jRadioButtonSavings;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPaneChecking;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTableAccounts;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the formatter
     */
    public DecimalFormat getFormatter() {
        return formatter;
    }

    /**
     * @param formatter the formatter to set
     */
    public void setFormatter(DecimalFormat formatter) {
        this.formatter = formatter;
    }

    /**
     * @return the newbtn
     */
    public Boolean getNewbtn() {
        return newbtn;
    }

    /**
     * @param newbtn the newbtn to set
     */
    public void setNewbtn(Boolean newbtn) {
        this.newbtn = newbtn;
    }

    /**
     * @return the acc
     */
    public account getAcc() {
        return acc;
    }

    /**
     * @param acc the acc to set
     */
    public void setAcc(account acc) {
        this.acc = acc;
    }

    /**
     * @return the chk
     */
    public checking getChk() {
        return chk;
    }

    /**
     * @param chk the chk to set
     */
    public void setChk(checking chk) {
        this.chk = chk;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the stm
     */
    public Statement getStm() {
        return stm;
    }

    /**
     * @param stm the stm to set
     */
    public void setStm(Statement stm) {
        this.stm = stm;
    }

    /**
     * @return the rs
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * @param rs the rs to set
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

}
