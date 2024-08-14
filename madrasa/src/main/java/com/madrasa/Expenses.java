package com.madrasa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;

public class Expenses extends JFrame implements ActionListener {
    JLabel infolbl, monthlbl, electriclbl, constrctionlbl, salrylbl, total;
    JTextField monthfield, electricField, constrctionField, salryField;
    JButton calculatebtn, cancelbtn, retrivbtn, savebtn;

    String ebill, constrctcost, salrycost;
    Double ebil, conscost, salcost, totalexp;

    public Expenses() {
        super("Expenses");
        setBounds(300, 50, 900, 700);
        setLayout(null);

        infolbl = new JLabel("Expenses");
        infolbl.setFont(new Font("Typograph pro", Font.BOLD, 15));
        infolbl.setBounds(200, 30, 200, 40);

        monthlbl = new JLabel("Month: ");
        monthlbl.setBounds(120, 100, 150, 30);

        monthfield = new JTextField();
        monthfield.setBounds(250, 100, 200, 30);

        electriclbl = new JLabel("Electricity Bill: ");
        electriclbl.setBounds(120, 150, 150, 30);

        electricField = new JTextField();
        electricField.setBounds(250, 150, 200, 30);

        constrctionlbl = new JLabel("Construction Cost: ");
        constrctionlbl.setBounds(120, 200, 150, 30);

        constrctionField = new JTextField();
        constrctionField.setBounds(250, 200, 200, 30);

        salrylbl = new JLabel("Salaries Cost: ");
        salrylbl.setBounds(120, 250, 150, 30);

        salryField = new JTextField();
        salryField.setBounds(250, 250, 200, 30);

        retrivbtn = new JButton("Retrieve");
        retrivbtn.setBounds(455, 252, 85, 25);

        total = new JLabel("Total Expense: ");
        total.setBounds(150, 320, 300, 30);

        cancelbtn = new JButton("Cancel");
        cancelbtn.setBounds(300, 500, 100, 30);

        calculatebtn = new JButton("Calculate Total");
        calculatebtn.setBounds(410, 500, 150, 30);

        savebtn = new JButton("Save Record");
        savebtn.setBounds(580, 500, 120, 30);

        add(infolbl);
        add(monthlbl);
        add(monthfield);
        add(electriclbl);
        add(electricField);
        add(constrctionlbl);
        add(constrctionField);
        add(salrylbl);
        add(salryField);
        add(retrivbtn);
        add(total);
        add(cancelbtn);
        add(calculatebtn);
        add(savebtn);

        cancelbtn.addActionListener(this);
        calculatebtn.addActionListener(this);
        retrivbtn.addActionListener(this);
        savebtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent eeee) {
        if (eeee.getSource() == savebtn) {
            String monstr = monthfield.getText();
            String ebilstr = electricField.getText();
            String conscoststr = constrctionField.getText();
            String salstr = salryField.getText();
            Double total = Double.parseDouble(ebilstr) + Double.parseDouble(conscoststr) + Double.parseDouble(salstr);

            transactions.deductAmount(total, monstr);
            if (transactions.insuffblnc != 0) {
                JOptionPane.showMessageDialog(this, "Insufficient Funds");
            }

            try {
                DBcon conn = new DBcon();

                String monst = monthfield.getText();
                String ebilst = electricField.getText();
                String conscostst = constrctionField.getText();
                String salst = salryField.getText();
                Double totalstr = Double.parseDouble(ebilst) + Double.parseDouble(conscostst) + Double.parseDouble(salst);

                // Insert expenses
                int rs = conn.stmnt.executeUpdate("Insert into expenses values('" + monst + "','" + ebilstr + "','" + conscoststr + "','" + salstr + "','" + totalstr + "')");

                // Retrieve and calculate the sum of amounts from funds
                String query1 = "Select current_balance as total_amount from funds where trans_id = (select max(trans_id) from funds)";
                ResultSet rs1 = conn.stmnt.executeQuery(query1);
                String prevfnd = "0"; // Initialize to 0 in case there are no results
                if (rs1.next()) {
                    prevfnd = rs1.getString("total_amount");
                }

                // Calculate total expense
                int totalexp = Integer.parseInt(ebilstr) + Integer.parseInt(conscoststr) + Integer.parseInt(salstr);

                // Calculate the new fund left after expenses
                Double newfnd = Double.parseDouble(prevfnd) - totalexp;

                // Insert the calculated fund left into finance_history
                String insertQuery = "insert into finance_history (month, fund_spent, fund_left) values('" + monstr + "','" + totalstr + "','" + newfnd + "')";
                int ress = conn.stmnt.executeUpdate(insertQuery);

                if (rs > 0 && ress > 0) {
                    JOptionPane.showMessageDialog(this, "Data inserted successfully");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }





        } else if (eeee.getSource() == retrivbtn) {
            try {
                DBcon conn = new DBcon();

                String montstr = monthfield.getText();
                ResultSet rs = conn.stmnt.executeQuery("select sum(sal_amount) as total_sal from salary where month= '" + montstr + "' and sal_status = 'PAID'");

                while (rs.next()) {
                    if (rs.getString("total_sal") == "") {
                        salryField.setText("0");
                    } else {
                        salryField.setText(rs.getString("total_sal"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




        } else if (eeee.getSource() == calculatebtn) {
            ebill = electricField.getText();
            constrctcost = constrctionField.getText();
            salrycost = salryField.getText();

            ebil = Double.parseDouble(ebill);
            conscost = Double.parseDouble(constrctcost);
            salcost = Double.parseDouble(salrycost);

            if (ebill.isEmpty() || constrctcost.isEmpty() || salrycost.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter values");
            } else {
                totalexp = ebil + conscost + salcost;

                total.setText(("TOTAL EXPENDITURE: " + totalexp));
                total.setFont(new Font("Century Gothic", Font.BOLD, 18));
            }
        } else if (eeee.getSource() == cancelbtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Expenses();
    }
}