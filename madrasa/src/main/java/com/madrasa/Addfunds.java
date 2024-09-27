package com.madrasa;

import java.awt.event.*;
import java.sql.ResultSet;

import java.awt.*;

import javax.swing.*;

public class Addfunds extends JFrame implements ActionListener {
    JLabel infolbl,prevamntlbl,amountlbl,monthlbl;
    JTextField prevamntfield,amountfield,monthfield;
    JButton canclbtn,addamontbtn;
    String amount,month,totalamount;

    public Addfunds(){
        super("Add Fund");
        setLayout(null);
        setBounds(300,150,700,400);

        infolbl = new JLabel("Add Fund to Account");
        infolbl.setFont(new Font("Typograph Pro", Font.PLAIN, 15));
        infolbl.setBounds(120,50,260,30);

        monthlbl = new JLabel("Month: ");
        monthlbl.setBounds(100,100,150,30);

        monthfield = new JTextField();
        monthfield.setBounds(210,100,200,30);

        prevamntlbl = new JLabel("Current Balance: ");
        prevamntlbl.setBounds(100,150,200,30);

        prevamntfield = new JTextField();
        prevamntfield.setBounds(220,150,180,30);

        amountlbl = new JLabel("Add Amount: ");
        amountlbl.setBounds(100,200,90,30);

        amountfield = new JTextField();
        amountfield.setBounds(210,200,200,30);

        canclbtn = new JButton("Cancel");
        canclbtn.setBounds(150, 300, 90, 28);
        canclbtn.setForeground(new Color(0xFFFFFF));
        canclbtn.setBackground(new Color(0x850900));

        addamontbtn = new JButton("Add Amount");
        addamontbtn.setBounds(300, 300, 120, 30);
        addamontbtn.setForeground(new Color(0xFFFFFF));        
        addamontbtn.setBackground(new Color(0x0D6A21));        



        //IMAGE BACKGROUND

        // Load the image into an ImageIcon
        ImageIcon imageIcon = new ImageIcon("madrasa\\src\\main\\resources\\images\\addfund.png");

        // Resize the image to 100x100 pixels
        Image smallImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        // Set the resized image to a new ImageIcon
        ImageIcon smallIcon = new ImageIcon(smallImage);

        // Create a new JLabel and set the icon to the resized image
        JLabel label = new JLabel(smallIcon);

        label.setBounds(350,5,400,450);

        // Add the label to the JFrame
        add(label);



        


        add(infolbl);
        add(monthlbl);
        add(monthfield);
        add(prevamntlbl);
        add(prevamntfield);
        add(amountlbl);
        add(amountfield);
        add(canclbtn);
        add(addamontbtn);


        prevamntfield.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e){
                try{
                    DBcon con = new DBcon();
                    
                    ResultSet rs = con.stmnt.executeQuery("SELECT current_balance AS availfund FROM funds where trans_id = (select max(trans_id) from funds)");
                    

                    while (rs.next()) {
                         prevamntfield.setText(rs.getString("availfund"));
                    }

                }
                catch(Exception ee){
                    ee.printStackTrace();
                }     
            }
        });



        canclbtn.addActionListener(this);
        addamontbtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ee){

        if(ee.getSource() == addamontbtn){
          
                String amnt = amountfield.getText();
                Double amount = Double.parseDouble(amnt);
                String month = monthfield.getText();
                transactions.addAmount(amount,month);

                if (transactions.addamnt != 0) {
                    JOptionPane.showMessageDialog(this, "Fund Added Successfuly");
                }
            }
           
            //  try {
                //     DBcon con = new DBcon();
            
            //     // Insert fund into funds table
            //     String q = "insert into funds(month,amount) values ('"+month+"','"+amount+"')";
            //     int result = con.stmnt.executeUpdate(q);
            
            //     // Insert into finance_history
            //     int res = con.stmnt.executeUpdate("insert into finance_history (fund_added) values ('"+amount+"')");
            
            //     // Fetch the sum of current_balance
            //     ResultSet rs = con.stmnt.executeQuery("SELECT current_balance AS availfund FROM funds where trans_id = (select max(trans_id) from funds)");
            
            //     // Initialize balance to 0 in case the result is null or no rows are returned
            //     String blnce = "0";
            //     if (rs.next()) {
            //         blnce = rs.getString("availfund");
            //     }
            //     if (blnce == null || blnce.isEmpty()) {
            //         blnce = "0"; // Fallback to "0" if the result is null or empty
            //     }
            
            //     Integer balance = Integer.parseInt(blnce);
            
            
            //     // Update the funds table with the new balance
            //     int ress = con.stmnt.executeUpdate("update funds set current_balance= "+balance+"+"+amount+" where trans_id =  (select max(trans_id) from funds)");
            
            //     // Show success message if all updates are successful
            //     if (result > 0 && res > 0 && ress > 0) {
            //         JOptionPane.showMessageDialog(this, "Fund Added Successfully");
            //     }
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }

            
        // }

        else if(ee.getSource() == canclbtn){
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Addfunds();
    }
}
