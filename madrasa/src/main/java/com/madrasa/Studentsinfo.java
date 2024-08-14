package com.madrasa;

import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

public class Studentsinfo extends JFrame implements ActionListener {
    JLabel stdinfo;
    JTextField srchfield;
    JButton srchbtn,loadbtn,backbtn;
    JTable table;

    private JLabel Admissionlbl, Namelbl, FatherNamelbl, FatherCNIClbl, Contactlbl, Addresslbl, Departmentlbl, boardinglbl,doblbl;

    public Studentsinfo(){
        super("Students information");
        setLayout(null);
        setBounds(90,30,1200, 700);

        srchfield = new JTextField();
        srchfield.setBounds(500,20,300,30);

        srchbtn = new JButton("Search");
        srchbtn.setBounds(801, 22, 90, 26);

        stdinfo = new JLabel("Students Information");
        stdinfo.setFont(new Font("Typograph pro",Font.BOLD,15));
        stdinfo.setBounds(120,60,300,40);

        backbtn = new JButton("Close");
        backbtn.setBounds(750,630,90,26);

        loadbtn = new JButton("Load");
        loadbtn.setBounds(950,625,90,30);



        //Table Column Head names 
        Admissionlbl = new JLabel("Admission Number");
        Admissionlbl.setBounds(35, 90, 200, 30);
        add(Admissionlbl);

        Namelbl = new JLabel("Name");
        Namelbl.setBounds(165,90,200,30);
        add(Namelbl);
        
        FatherNamelbl = new JLabel("Father's Name");
        FatherNamelbl.setBounds(285, 90, 200, 30);
        add(FatherNamelbl);
        
        FatherCNIClbl = new JLabel("Father's CNIC");
        FatherCNIClbl.setBounds(400, 90, 200, 30);
        add(FatherCNIClbl);
        
        doblbl = new JLabel("Date of Birth");
        doblbl.setBounds(530, 90, 200, 30);
        add(doblbl);
        
        Contactlbl = new JLabel("Contact");
        Contactlbl.setBounds(653, 90, 200, 30);
        add(Contactlbl);
        
        Addresslbl = new JLabel("Address");
        Addresslbl.setBounds(775, 90, 200, 30);
        add(Addresslbl);
        
        Departmentlbl = new JLabel("Department");
        Departmentlbl.setBounds(895, 90, 200, 30);
        add(Departmentlbl);

        boardinglbl = new JLabel("Boarding");
        boardinglbl.setBounds(1010,90,200,30);
        add(boardinglbl);



        table = new JTable();
        table.setBounds(30,120,1100,500);

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

    @Override
    public void actionPerformed(ActionEvent eee){
        if(eee.getSource() == loadbtn){
            try{
                DBcon con = new DBcon();

                String displaystd ="select * from student";
                ResultSet rs = con.stmnt.executeQuery(displaystd);

                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        else if (eee.getSource() == srchbtn) {
            try {
                DBcon conn = new DBcon();
        
                String srchnmstr = srchfield.getText();
                String q = "select * from student where name = '" + srchnmstr + "'";
        
                ResultSet res = conn.scrollstmnt.executeQuery(q);
                if (res.next()) {
                    // Consume the entire ResultSet
                    res.beforeFirst(); // Move the cursor back to the beginning
                    table.setModel(DbUtils.resultSetToTableModel(res));
                } else {
                    JOptionPane.showMessageDialog(this, "No Student Found! Please check Again");
                }
        
            } catch (Exception e) {

                e.printStackTrace();
            }
        }


        else if(eee.getSource() == backbtn){
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Studentsinfo();
    }
    
}
