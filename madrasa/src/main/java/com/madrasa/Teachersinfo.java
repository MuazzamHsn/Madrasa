package com.madrasa;

import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

public class Teachersinfo extends JFrame implements ActionListener {
    JLabel stdinfo;
    JTextField srchfield;
    JButton srchbtn,loadbtn,backbtn;
    JTable table;

    private JLabel idlbl, Namelbl, FatherNamelbl, CNIClbl, Contactlbl, Addresslbl, Departmentlbl, Qualificationlbl,salarylbl;

    public Teachersinfo(){
        super("Teachers information");
        setLayout(null);
        setBounds(90,30,1200, 700);

        srchfield = new JTextField();
        srchfield.setBounds(500,20,300,30);

        srchbtn = new JButton("Search");
        srchbtn.setBounds(801, 22, 90, 26);

        stdinfo = new JLabel("Teachers Information");
        stdinfo.setFont(new Font("Typograph pro",Font.BOLD,15));
        stdinfo.setBounds(120,60,300,40);

        backbtn = new JButton("Close");
        backbtn.setBounds(750,625,90,26);

        loadbtn = new JButton("Load");
        loadbtn.setBounds(950,620,90,30);


          //Column Head names 
           idlbl = new JLabel("ID Number");
           idlbl.setBounds(35, 90, 200, 30);
          add( idlbl);
  
          Namelbl = new JLabel("Name");
          Namelbl.setBounds(165,90,200,30);
          add(Namelbl);
          
          FatherNamelbl = new JLabel("Father's Name");
          FatherNamelbl.setBounds(285, 90, 200, 30);
          add(FatherNamelbl);
          
          CNIClbl = new JLabel("CNIC");
          CNIClbl.setBounds(410, 90, 200, 30);
          add(CNIClbl);
          
          Contactlbl = new JLabel("Contact");
          Contactlbl.setBounds(525, 90, 200, 30);
          add(Contactlbl);
          
          Addresslbl = new JLabel("Address");
          Addresslbl.setBounds(650, 90, 200, 30);
          add(Addresslbl);
          
          Departmentlbl = new JLabel("Department");
          Departmentlbl.setBounds(770, 90, 200, 30);
          add(Departmentlbl);
          
          Qualificationlbl = new JLabel("Qualification");
          Qualificationlbl.setBounds(890, 90, 200, 30);
          add(Qualificationlbl);

          salarylbl = new JLabel("Salary");
          salarylbl.setBounds(1020, 90, 200, 30);
          add(salarylbl);


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
        if (eee.getSource() == loadbtn){
              try{
                DBcon con = new DBcon();

                String displaystd ="select * from teacher";
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
                String q = "select * from teacher where name = '" + srchnmstr + "'";
        
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
        new Teachersinfo();
    }
    
}
