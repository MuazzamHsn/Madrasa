package com.madrasa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.swing.*;

public class Addteacher extends JFrame implements ActionListener{
    JLabel infolbl,qualfcationlbl,namelbl,fnamlbl,cniclbl,contactlbl,addresslbl,deptlbl,sallbl;
    JTextField namField,fnamField,cnicfield,contactField,addressField,salfield;
    JComboBox<String> deptfield, qualficationfield;
    String departments[] = {"ILM","Hifz","Nazira" }, qualifcations[]={"Maulana","Hafiz","Mufti","Qari"};
    String namstr,fnmstr,cnicstr,contctstr,addrssstr,depttstr,qualfctionstr,salstr;
    JButton cnclbtn,addbtn;

    public Addteacher(){
        super("Add Teacher");
        setLayout(null);
        setBounds(230,100,900,600);

        infolbl = new JLabel("Add new Teacher");
        infolbl.setFont(new Font("Typograph Pro", Font.PLAIN, 20));
        infolbl.setBounds(170, 30, 500, 40); 
        
        qualfcationlbl = new JLabel("Qualificcation : ");
        qualfcationlbl.setBounds(100,100,90,30);

        qualficationfield = new JComboBox<String>(qualifcations);
        qualficationfield.setBounds(220,100,120,26);

        namelbl = new JLabel("Name: ");
        namelbl.setBounds(100,140,90,30);

        namField = new JTextField();
        namField.setBounds(220,140,200,26);

        fnamlbl = new JLabel("Father's Name: ");
        fnamlbl.setBounds(100,180,90,30);

        fnamField = new JTextField();
        fnamField.setBounds(220, 180, 200, 26);

        cniclbl = new JLabel("CNIC No. : ");
        cniclbl.setBounds(100, 220, 90, 30);

        cnicfield = new JTextField();
        cnicfield.setBounds(220,220,220,26);

        contactlbl = new JLabel("Contact: ");
        contactlbl.setBounds(100, 260, 90,30);

        contactField = new JTextField();
        contactField.setBounds(220,260,220,26);

        addresslbl = new JLabel("Address: ");
        addresslbl.setBounds(100,300,90,30);

        addressField = new JTextField();
        addressField.setBounds(220,300,260,26);

        deptlbl = new JLabel("Department: ");
        deptlbl.setBounds(100,340,90,30);

        deptfield = new JComboBox<String>(departments);
        deptfield.setBounds(220,340,200,26);

        sallbl = new JLabel("Salary: ");
        sallbl.setBounds(100,380,90,30);

        salfield = new JTextField();
        salfield.setBounds(220, 380, 100, 26);

        cnclbtn = new JButton("Cancel");
        cnclbtn.setBounds(169,509,90,30);

        addbtn = new JButton("Add Teacher");
        addbtn.setBounds(469,500,130,40);

        add(qualfcationlbl);
        add(qualficationfield);
        add(infolbl);
        add(namelbl);
        add(namField);
        add(fnamlbl);
        add(fnamField);
        add(cniclbl);
        add(cnicfield);
        add(contactlbl);
        add(contactField);
        add(addresslbl);
        add(addressField);
        add(deptlbl);
        add(deptfield);
        add(sallbl);
        add(salfield);

        add(cnclbtn);
        add(addbtn);

        addbtn.addActionListener(this);
        cnclbtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == addbtn){
            try{
                DBcon con = new DBcon();

                namstr = namField.getText();
                fnmstr = fnamField.getText();
                cnicstr = cnicfield.getText();
                contctstr = contactField.getText();
                addrssstr = addressField.getText();
                depttstr = (String) deptfield.getSelectedItem();
                qualfctionstr = (String) qualficationfield.getSelectedItem();
                salstr = salfield.getText();

                

                String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);


                String insertstd ="insert into teacher(name,father_name,CNIC,contact,address,department,qualification,salary) values ('"+namstr+"','"+fnmstr+"','"+cnicstr+"','"+contctstr+"','"+addrssstr+"','"+depttstr+"','"+qualfctionstr+"','"+salstr+"')";

                int rs = con.stmnt.executeUpdate(insertstd);

                int res = con.stmnt.executeUpdate("Insert into salary (teachername,month,sal_amount,sal_status) values ('"+namstr+"','"+currentMonth+"','"+salstr+"','UN-PAID')");

                if(rs>0 && res>0)
                JOptionPane.showMessageDialog(this, "Teacher Added Successfully ");
            }
            catch(Exception ee ){
                ee.printStackTrace();
            }
        }

        else if (e.getSource() == cnclbtn){
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Addteacher();
    }
}