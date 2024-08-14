package com.madrasa;

import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

public class ShowFinance extends JFrame implements ActionListener {
    JLabel stdinfo;
    JTextField srchfield;
    JButton srchbtn,loadbtn,backbtn;
    JTable table;

    private JLabel idlbl, yearlbl, monthlbl, Fundaddlbl, Fundspntlbl, Fundlftlbl,timelbl;

    public ShowFinance(){
        super("Finance information");
        setLayout(null);
        setBounds(90,30,1200, 700);

        srchfield = new JTextField();
        srchfield.setBounds(500,20,300,30);

        srchbtn = new JButton("Search");
        srchbtn.setBounds(801, 22, 90, 26);

        stdinfo = new JLabel("Finance Information");
        stdinfo.setFont(new Font("Typograph pro",Font.BOLD,15));
        stdinfo.setBounds(120,60,300,40);

        backbtn = new JButton("Close");
        backbtn.setBounds(750,630,90,26);

        loadbtn = new JButton("Load");
        loadbtn.setBounds(950,625,90,30);



        //Table Column Head names 
        idlbl = new JLabel("Serial No.");
        idlbl.setBounds(35, 90, 200, 30);
        add(idlbl);

        yearlbl = new JLabel("Year");
        yearlbl.setBounds(190,90,200,30);
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
        timelbl.setBounds(980,90,200,30);
        add(timelbl);
        


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

                String displaystd ="select * from finance_history";
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
                String q = "select * from finance_history where month = '" + srchnmstr + "'";
        
                ResultSet res = conn.scrollstmnt.executeQuery(q);
                if (res.next()) {
                    // Consume the entire ResultSet
                    res.beforeFirst(); // Move the cursor back to the beginning
                    table.setModel(DbUtils.resultSetToTableModel(res));
                } else {
                    JOptionPane.showMessageDialog(this, "No such Month Found! Please check Again");
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
        new ShowFinance();
    }
    
}
