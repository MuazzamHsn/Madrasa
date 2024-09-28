package com.madrasa;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;

import net.proteanit.sql.DbUtils;

public class ShowFinance extends JFrame implements ActionListener {
    JLabel stdinfo;
    JTextField srchfield;
    JButton srchbtn, loadbtn, backbtn;
    JTable table;

    private JLabel idlbl, yearlbl, monthlbl, Fundaddlbl, Fundspntlbl, Fundlftlbl, timelbl;

    public ShowFinance() {
        super("Finance information");
        setLayout(null);
        setBounds(90, 30, 1200, 700);

        srchfield = new JTextField();
        srchfield.setBounds(500, 20, 300, 30);

        srchbtn = new JButton("Search");
        srchbtn.setBounds(801, 22, 90, 26);
        srchbtn.setForeground(new Color(0xFFFFFF));
        srchbtn.setBackground(new Color(0x097D97));

        stdinfo = new JLabel("Finance Information");
        stdinfo.setFont(new Font("Typograph pro", Font.BOLD, 15));
        stdinfo.setBounds(120, 60, 300, 40);

        backbtn = new JButton("Close");
        backbtn.setForeground(new Color(0xFFFFFF));
        backbtn.setBackground(new Color(0x850900));
        backbtn.setBounds(750, 630, 90, 26);

        loadbtn = new JButton("Load");
        loadbtn.setBounds(950, 625, 90, 30);
        loadbtn.setForeground(new Color(0xFFFFFF));
        loadbtn.setBackground(new Color(0x005DAF));

        // Table Column Head names
        idlbl = new JLabel("Serial No.");
        idlbl.setBounds(35, 90, 200, 30);
        add(idlbl);

        yearlbl = new JLabel("Year");
        yearlbl.setBounds(190, 90, 200, 30);
        add(yearlbl);

        monthlbl = new JLabel("Month");
        monthlbl.setBounds(354, 90, 200, 30);
        add(monthlbl);

        Fundaddlbl = new JLabel("Fund Added");
        Fundaddlbl.setBounds(510, 90, 200, 30);
        add(Fundaddlbl);

        Fundspntlbl = new JLabel("Fund Spent");
        Fundspntlbl.setBounds(670, 90, 200, 30);
        add(Fundspntlbl);

        Fundlftlbl = new JLabel("Fund Left");
        Fundlftlbl.setBounds(830, 90, 200, 30);
        add(Fundlftlbl);

        timelbl = new JLabel("Date & Time");
        timelbl.setBounds(980, 90, 200, 30);
        add(timelbl);

        table = new JTable();
        table.setBounds(30, 120, 1100, 500);

        add(srchfield);
        add(srchbtn);
        add(stdinfo);
        add(backbtn);
        add(loadbtn);
        add(table);

        srchbtn.addActionListener(this);
        loadbtn.addActionListener(this);
        backbtn.addActionListener(this);

        setVisible(true);
    }

    private void addColumnRenderers() {
        // Renderer for 4th column ("Fund Added")
        TableCellRenderer greenRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
                if (!isSelected) {
                    c.setForeground(new Color(0x0e8d00)); // Green Foreground for "Fund Added" column
                    c.setFont(new Font("Alata", Font.PLAIN, 12));
                }
    
                // Format the value to include a '+' sign for positive numbers
                if (value instanceof Number) {
                    double amount = ((Number) value).doubleValue();
                    String formattedAmount = (amount >= 0) ? "+ " + amount : String.valueOf(amount);
                    setText(formattedAmount); // Update the cell text
                }
    
                return c;
            }
        };
    
        // Renderer for 5th column ("Fund Spent")
        TableCellRenderer redRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
                if (!isSelected) {
                    c.setForeground(Color.RED); // Red Foreground for "Fund Spent" column
                    c.setFont(new Font("Alata", Font.PLAIN, 12));
                }
    
                // Format the value to include a '-' sign (if not already negative)
                if (value instanceof Number) {
                    double amount = ((Number) value).doubleValue();
                    String formattedAmount = (amount < 0) ? String.valueOf(amount) : "- " + amount;
                    setText(formattedAmount); // Update the cell text
                }
    
                return c;
            }
        };
    
        // Apply renderers to the respective columns
        TableColumn fundAddedColumn = table.getColumnModel().getColumn(3); // 4th column (index 3)
        fundAddedColumn.setCellRenderer(greenRenderer);
    
        TableColumn fundSpentColumn = table.getColumnModel().getColumn(4); // 5th column (index 4)
        fundSpentColumn.setCellRenderer(redRenderer);
    }
    

    @Override
    public void actionPerformed(ActionEvent eee) {
        if (eee.getSource() == loadbtn) {
            try {
                DBcon con = new DBcon();
                String displaystd = "select * from finance_history";
                ResultSet rs = con.stmnt.executeQuery(displaystd);
                table.setModel(DbUtils.resultSetToTableModel(rs));

                // Apply custom renderers after data is loaded
                addColumnRenderers();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (eee.getSource() == srchbtn) {
            try {
                DBcon conn = new DBcon();
                String srchnmstr = srchfield.getText();
                String q = "select * from finance_history where month = '" + srchnmstr + "'";
                ResultSet res = conn.scrollstmnt.executeQuery(q);
                if (res.next()) {
                    res.beforeFirst(); // Move the cursor back to the beginning
                    table.setModel(DbUtils.resultSetToTableModel(res));

                    // Apply custom renderers after data is loaded
                    addColumnRenderers();
                } else {
                    JOptionPane.showMessageDialog(this, "No such Month Found! Please check Again");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (eee.getSource() == backbtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new ShowFinance();
    }
}
