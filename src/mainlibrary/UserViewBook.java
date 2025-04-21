/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bikash
 */
public class UserViewBook extends javax.swing.JFrame {

    /**
     * Creates new form ViewBook
     *
     * @throws java.sql.SQLException
     */
    public UserViewBook() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        loadAllBooks(0); // Load all books by default
    }

    /**
     * Loads books into the table based on the filter flag.
     * @param flag 0 for all books, 1 for not issued books only.
     */
    private void loadAllBooks(int flag) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        clearTable(model);

        String query = "SELECT Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID " +
                       "FROM Books LEFT OUTER JOIN IssuedBook ON Books.BookID = IssuedBook.BookID";

        try (Connection Con = DB.getConnection();
             PreparedStatement ps = Con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            populateTable(model, rs, flag);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Populates the table with data from the ResultSet.
     * @param model The table model to populate.
     * @param rs The ResultSet containing the data.
     * @param flag 0 for all books, 1 for not issued books only.
     * @throws SQLException If an SQL error occurs.
     */
    private void populateTable(DefaultTableModel model, ResultSet rs, int flag) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int colnum = rsmd.getColumnCount();

        while (rs.next()) {
            String[] row = extractRowData(rs, colnum, flag);
            if (row.length > 0) {
                model.addRow(row);
            }
        }

        if (model.getRowCount() == 0) {
            addNoResultsRow(model);
        }
    }

    /**
     * Extracts a single row of data from the ResultSet.
     * @param rs The ResultSet containing the data.
     * @param colnum The number of columns in the ResultSet.
     * @param flag 0 for all books, 1 for not issued books only.
     * @return A String array representing the row, or an empty array if the row should be skipped.
     * @throws SQLException If an SQL error occurs.
     */
    private String[] extractRowData(ResultSet rs, int colnum, int flag) throws SQLException {
        String[] row = new String[colnum];

        for (int i = 1; i <= colnum; i++) {
            if (i == colnum) {
                if (rs.getString(i) == null) {
                    row[i - 1] = "Not Issued";
                } else {
                    if (flag == 1) {
                        return new String[0]; // Return an empty array instead of null
                    }
                    row[i - 1] = "Issued";
                }
            } else {
                row[i - 1] = rs.getString(i);
            }
        }

        return row;
    }

    /**
     * Clears all rows from the table model.
     * @param model The table model to clear.
     */
    private void clearTable(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    /**
     * Adds a "No Results" row to the table model.
     * @param model The table model to update.
     */
    private void addNoResultsRow(DefaultTableModel model) {
        String[] NoRow = new String[8];
        NoRow[3] = "NO";
        NoRow[4] = "RESULT";
        model.addRow(NoRow);
    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        if (SearchField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Search Field is Empty", "Search Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int flag = ALL.isSelected() ? 0 : 1;
        String search = "%" + SearchField.getText() + "%";
        String query;

        if (NameRadio.isSelected()) {
            query = "SELECT Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID " +
                    "FROM Books LEFT OUTER JOIN IssuedBook ON Books.BookID = IssuedBook.BookID WHERE Books.BookName LIKE ?";
        } else if (AuthorRadio.isSelected()) {
            query = "SELECT Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID " +
                    "FROM Books LEFT OUTER JOIN IssuedBook ON Books.BookID = IssuedBook.BookID WHERE Books.Author LIKE ?";
        } else {
            JOptionPane.showMessageDialog(this, "Select Name or Author", "No Selection!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        clearTable(model);

        try (Connection Con = DB.getConnection();
             PreparedStatement ps = Con.prepareStatement(query)) {

            ps.setString(1, search);
            try (ResultSet rs = ps.executeQuery()) {
                populateTable(model, rs, flag);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void ShowALLActionPerformed(java.awt.event.ActionEvent evt) {
        int flag = ALL.isSelected() ? 0 : 1;
        loadAllBooks(flag);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        NameRadio = new javax.swing.JRadioButton();
        AuthorRadio = new javax.swing.JRadioButton();
        ALL = new javax.swing.JRadioButton();
        NotIssued = new javax.swing.JRadioButton();
        SearchField = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        ShowALL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Book ID", "Name", "Genre", "Author", "Publisher", "Shelf", "Row", "Available"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false,  false,false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Books");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        NameRadio.setText("Name");
        NameRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameRadioActionPerformed(evt);
            }
        });

        AuthorRadio.setText("Author");
        AuthorRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AuthorRadioActionPerformed(evt);
            }
        });

        ALL.setText("ALL");
        ALL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ALLActionPerformed(evt);
            }
        });

        NotIssued.setText("NOT ISSUED");
        NotIssued.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotIssuedActionPerformed(evt);
            }
        });

        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        ShowALL.setText("Show All");
        ShowALL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowALLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(698, 698, 698)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1464, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(452, 452, 452)
                        .addComponent(NameRadio)
                        .addGap(30, 30, 30)
                        .addComponent(AuthorRadio)
                        .addGap(308, 308, 308)
                        .addComponent(ALL)
                        .addGap(40, 40, 40)
                        .addComponent(NotIssued))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(725, 725, 725)
                        .addComponent(jLabel1)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ShowALL)
                .addGap(122, 122, 122)
                .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(Search)
                .addGap(288, 288, 288))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameRadio)
                    .addComponent(AuthorRadio)
                    .addComponent(ALL)
                    .addComponent(NotIssued))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search)
                    .addComponent(ShowALL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code hereset
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AuthorRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AuthorRadioActionPerformed
        // TODO add your handling code here:
        NameRadio.setSelected(false);
    }//GEN-LAST:event_AuthorRadioActionPerformed

    private void NameRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameRadioActionPerformed
        // TODO add your handling code here:
        AuthorRadio.setSelected(false);
    }//GEN-LAST:event_NameRadioActionPerformed

    private void ALLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ALLActionPerformed
        // TODO add your handling code here:
        NotIssued.setSelected(false);

    }//GEN-LAST:event_ALLActionPerformed

    private void NotIssuedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotIssuedActionPerformed
        // TODO add your handling code here:
        ALL.setSelected(false);

    }//GEN-LAST:event_NotIssuedActionPerformed

    private void NotIssuedActionPerformed() {
        // TODO add your handling code here:
        ALL.setSelected(false);
    }

    private void ALLActionPerformed() {
        // TODO add your handling code here:
        NotIssued.setSelected(false);
    }

    private void NameRadioActionPerformed() {
        // TODO add your handling code here:
        AuthorRadio.setSelected(false);
    }

    private void AuthorRadioActionPerformed() {
        // TODO add your handling code here:
        NameRadio.setSelected(false);
    }

    private void jButton1ActionPerformed() {
        // TODO add your handling code here:
        this.dispose();
    }

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
            java.util.logging.Logger.getLogger(UserViewBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserViewBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserViewBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserViewBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UserViewBook().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(UserViewBook.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ALL;
    private javax.swing.JRadioButton AuthorRadio;
    private javax.swing.JRadioButton NameRadio;
    private javax.swing.JRadioButton NotIssued;
    private javax.swing.JButton Search;
    private javax.swing.JTextField SearchField;
    private javax.swing.JButton ShowALL;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
