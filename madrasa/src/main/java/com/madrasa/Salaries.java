package com.madrasa;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

public class Salaries extends JFrame implements ActionListener {

    JLabel stdinfo;
    JTextField srchfield;
    JButton srchbtn,loadbtn,backbtn;
    JTable table;

    private JLabel teachidlbl, tchnmlbl, monthlbl, salamntlbl, salsttslbl;


    public Salaries(){
        super("Teachers Salary information");
        setLayout(null);
        setBounds(90,30,1200, 700);

        srchfield = new JTextField();
        srchfield.setBounds(500,20,300,30);

        srchbtn = new JButton("Search");
        srchbtn.setBounds(801, 22, 90, 26);
        srchbtn.setForeground(new Color(0xFFFFFF));
        srchbtn.setBackground(new Color(0x097D97));

        stdinfo = new JLabel("Teachers Salary Information");
        stdinfo.setFont(new Font("Typograph pro",Font.BOLD,15));
        stdinfo.setBounds(120,60,400,40);

        backbtn = new JButton("Close");
        backbtn.setForeground(new Color(0xFFFFFF));
        backbtn.setBackground(new Color(0x850900));
        backbtn.setBounds(800,623,90,26);

        loadbtn = new JButton("Load");
        loadbtn.setBounds(950,620,90,30);
        loadbtn.setForeground(new Color(0xFFFFFF));
        loadbtn.setBackground(new Color(0x005DAF));


         //Table Column Head names 
         teachidlbl = new JLabel("Teacher Id");
         teachidlbl.setBounds(35, 90, 200, 30);
         add(teachidlbl);
 
         tchnmlbl = new JLabel("Teacher Name");
         tchnmlbl.setBounds(255,90,200,30);
         add(tchnmlbl);
         
         monthlbl = new JLabel("Month");
         monthlbl.setBounds(485, 90, 200, 30);
         add(monthlbl);
         
         salamntlbl = new JLabel("Salary Amount");
         salamntlbl.setBounds(700, 90, 200, 30);
         add(salamntlbl);
         
         salsttslbl = new JLabel("Salary Status");
         salsttslbl.setBounds(910, 90, 200, 30);
         add(salsttslbl);



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

                String displaysal ="select serialno,teachername,month,sal_amount,sal_status from salary";
                ResultSet rs = con.stmnt.executeQuery(displaysal);

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
                String q = "select * from salary where teachername = '" + srchnmstr + "'";
        
                ResultSet res = conn.scrollstmnt.executeQuery(q);
                if (res.next()) {
                    // Consume the entire ResultSet
                    res.beforeFirst(); // Move the cursor back to the beginning
                    table.setModel(DbUtils.resultSetToTableModel(res));
                } else {
                    JOptionPane.showMessageDialog(this, "No Teacher Found! Please check Again");
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
        new Salaries();
    }
    
}
