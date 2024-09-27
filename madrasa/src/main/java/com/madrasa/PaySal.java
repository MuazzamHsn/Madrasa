package com.madrasa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class PaySal extends JFrame implements ActionListener{
        
        
    JTextField salamntfield,statusField,monthField;
    String[] teachers = {};
    JComboBox<String>teachnmfield = new JComboBox<String>(teachers);
    JLabel infolbl,teachnamelbl,salamntlbl,statuslbl,monthlbl;
    JButton clsbtn,paybtn;

    public PaySal(){
        
        super("Pay Salary");
        setBounds(400,100,580,600);
        setLayout(null);

        infolbl = new JLabel("Pay Salary ");
        infolbl.setFont(new Font("Typpograph Pro", Font.BOLD, 20));
        infolbl.setBounds(150,30,300,40);

        teachnamelbl = new JLabel("Teacher's Name: ");
        teachnamelbl.setBounds(100,100,200,30);

        teachnmfield = new JComboBox<String>(teachers);
        teachnmfield.setBounds(240,100,220,30);

        salamntlbl = new JLabel("Salary Amount: ");
        salamntlbl.setBounds(100,150,200,30);

        salamntfield = new JTextField();
        salamntfield.setBounds(240,150,100,30);   

        statuslbl = new JLabel("Status: ");
        statuslbl.setBounds(100,200,200,30);

        statusField = new JTextField();
        statusField.setBounds(240,200,100,30);

        monthlbl = new JLabel("Month: ");
        monthlbl.setBounds(100,250,200,30);

        monthField = new JTextField();
        monthField.setBounds(240,250,100,30);    
        
        clsbtn = new JButton("Close");
        clsbtn.setForeground(new Color(0xFFFFFF));
        clsbtn.setBackground(new Color(0x850900));
        clsbtn.setBounds(300,400, 80, 29);
        
        paybtn = new JButton("Pay");
        paybtn.setBounds(400,400, 80, 29);
        paybtn.setForeground(new Color(0xFFFFFF));
        paybtn.setBackground(new Color(0x296D14));



                //IMAGE BACKGROUND

        // Load the image into an ImageIcon
        ImageIcon imageIcon = new ImageIcon("madrasa\\src\\main\\resources\\images\\paysal.png");

        // Resize the image to 100x100 pixels
        Image smallImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        // Set the resized image to a new ImageIcon
        ImageIcon smallIcon = new ImageIcon(smallImage);

        // Create a new JLabel and set the icon to the resized image
        JLabel label = new JLabel(smallIcon);

        label.setBounds(250,30,400,450);

        // Add the label to the JFrame
        add(label);




        add(infolbl);
        add(teachnamelbl);
        add(teachnmfield);
        add(salamntlbl);
        add(salamntfield);
        add(statuslbl);
        add(statusField);
        add(monthlbl);
        add(monthField);
        add(clsbtn);
        add(paybtn);




        teachnmfield.addFocusListener((FocusListener)new FocusAdapter() {
            @Override public void focusGained(FocusEvent e){
            
                 try {
                     DBcon conn = new DBcon();

                        // Use a Set to store distinct names
                        Set<String> namesSet = new HashSet<String>();

                        ResultSet rs = conn.stmnt.executeQuery("select name from teacher");

                        while (rs.next()) {
                            String name = rs.getString("name");
                            namesSet.add(name); // Add to set, duplicates will be ignored
                        }
                    
                        // Clear existing items in JComboBox<String>
                        teachnmfield.removeAllItems();
                    
                     // Add distinct names from the set to JComboBox<String>
                     for (String name : namesSet) {
                          teachnmfield.addItem(name);
                        }

                    } catch (Exception ee) {
                      ee.printStackTrace();
                 }
                }

            }
        );


        salamntfield.addFocusListener((FocusListener) new FocusAdapter() {
            public void focusGained(FocusEvent ee){
                try{
                    DBcon con = new DBcon();

                    String teachnam =(String) teachnmfield.getSelectedItem();

                    ResultSet salrs =con.stmnt.executeQuery("select salary from teacher where name = '"+teachnam+"'");

                    while (salrs.next()) {
                        salamntfield.setText(salrs.getString("salary"));
                    }                   
                }

                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


        statusField.addFocusListener((FocusListener) new FocusAdapter(){
            public void focusGained(FocusEvent ee){
                try {
                    DBcon con = new DBcon();

                    String teachnam =(String) teachnmfield.getSelectedItem();

                    ResultSet salstts = con.stmnt.executeQuery("select sal_status,month from salary where teachername ='"+teachnam+"'");

                    while (salstts.next()) {
                        statusField.setText(salstts.getString("sal_status"));       
                        monthField.setText(salstts.getString("month"));
                    }
                }


                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        paybtn.addActionListener(this);
        clsbtn.addActionListener(this);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == paybtn){
            String stats = statusField.getText();
            if (stats.equals("PAID")) {
                JOptionPane.showMessageDialog(this, "Salary is already Paid");
            }

            else{
                try{
                    DBcon conn = new DBcon();
    
                    String tchnm = (String) teachnmfield.getSelectedItem();
                    // String amont = salamntfield.getText();
                    String  month = monthField.getText();
                    String status = "PAID";
    
                    int res = conn.stmnt.executeUpdate("update salary set  sal_status ='"+status+"' where teachername = '"+tchnm+"' and month = '"+month+"'" );
    
                    if(res>0){
                        JOptionPane.showMessageDialog(this, "Salary Paid to "+tchnm);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }         
        }


        else if(ae.getSource() == clsbtn){
            this.dispose();
        }
    }



    public static void main(String[] args) {
        new PaySal();
    }
}
