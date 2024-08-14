/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.madrasa;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener{
    
    JLabel name,usernmlbl,passlbl;
    JTextField usernmfield;
    JPasswordField passfield;
    JButton loginbtn,closebtn;
    JCheckBox showpassbtn;
    int count=0;

    Login(){

        super("Login");

        setLayout(null);

        name = new JLabel("جامعہ عمرین تعلیم القرآن");
        name.setBounds(200,1,300,60);
        name.setFont((new Font("Urdu Typesetting", Font.PLAIN, 30)));
        name.setForeground(Color.RED);
        add(name);

        usernmlbl = new JLabel("Username: ");
        usernmlbl.setBounds(145,85,100,30);
        add(usernmlbl);
        
        passlbl = new JLabel("Password: ");
        passlbl.setBounds(145,120,100,30);
        add(passlbl);
 
        usernmfield=new JTextField();
        usernmfield.setBounds(220,85,180,25);
        add(usernmfield);

        passfield=new JPasswordField();
        passfield.setBounds(220,120,180,25);
        add(passfield);

        showpassbtn = new JCheckBox("SHOW");
        showpassbtn.setBounds(420,123,75,20);
        add(showpassbtn);
        showpassbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showpassbtn.isSelected()) {
                    // Show the password
                    passfield.setEchoChar((char) 0);
                } else {
                    // Hide the password
                    passfield.setEchoChar('*');
                }
            }
        });

       
        loginbtn = new JButton("Login");

        loginbtn.setBounds(385,200,90,30);
        loginbtn.setFont(new Font("Century Gothic",Font.BOLD,15));
        loginbtn.addActionListener(this);
        add(loginbtn);

        closebtn=new JButton("Cancel");
        closebtn.setBounds(245,205,75,25);
        closebtn.setFont(new Font("Tw cen",Font.BOLD,12));
        add(closebtn);

        closebtn.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);

        
        InputMap inputmap = loginbtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionmap = loginbtn.getActionMap();

        inputmap.put(KeyStroke.getKeyStroke("ENTER"), "loginpress");

        actionmap.put("loginpress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                loginbtn.doClick();
            }
        });

        setVisible(true);
        setSize(600,300);
        setLocation(500,265);

    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==loginbtn){
            try{
            DBcon c1 = new DBcon();
            String unmstr = usernmfield.getText();
            String passtr = new String (passfield.getPassword());
            
            String q = "select * from login where username='"+unmstr+"' and password='"+passtr+"'";
            
            ResultSet rs = c1.stmnt.executeQuery(q); 
            if(rs.next()){ 
                this.dispose();
                new Dashboard().setVisible(true);
            }else{
                count++;
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
            if(count==3){
                setVisible(false);
                JOptionPane.showMessageDialog(this, "Try again later.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        }else if(ae.getSource()==closebtn){
            System.exit(0);
        }
    }
    public static void main(String[] arg){
        new Login();
    }
}
