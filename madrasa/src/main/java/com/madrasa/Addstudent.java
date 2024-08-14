package com.madrasa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class Addstudent extends JFrame implements ActionListener{
    JLabel infolbl,namelbl,fnamlbl,fathercniclbl,contactlbl,addresslbl,deptlbl,bordinglbl,doblbl;
    JTextField namField,fnamField,fathercnicfield,contactField,addressField,bordingfield;
    JDateChooser dobField;
    JComboBox<String> deptfield ;
    String departments[] = {"ILM","Hifz","Nazira","Qaaida" };
    String namestr,fnamstr,fcnicstr,contactstr,addressstr,deptstr,boardstr;
    Date dobstr;
    JButton cnclbtn,addbtn;

    public Addstudent(){
        super("Add Student");
        setLayout(null);
        setBounds(230,100,900,600);

        infolbl = new JLabel("Add new Student");
        infolbl.setFont(new Font("Typograph Pro", Font.PLAIN, 20));
        infolbl.setBounds(170, 30, 500, 40); 

        namelbl = new JLabel("Name: ");
        namelbl.setBounds(100,100,90,30);

        namField = new JTextField();
        namField.setBounds(220,100,200,26);

        fnamlbl = new JLabel("Father's Name: ");
        fnamlbl.setBounds(100,140,90,30);

        fnamField = new JTextField();
        fnamField.setBounds(220, 140, 200, 26);

        fathercniclbl = new JLabel("Father CNIC No. : ");
        fathercniclbl.setBounds(100, 180, 100, 30);

        fathercnicfield = new JTextField();
        fathercnicfield.setBounds(220,180,220,26);

        doblbl = new JLabel("Date of Birth: ");
        doblbl.setBounds(100,220,200,30);

        dobField = new JDateChooser();
        dobField.setDateFormatString("yyyy-MM-dd");
        dobField.setBounds(220, 220, 200, 26);

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

        bordinglbl = new JLabel("Boarding: ");
        bordinglbl.setBounds(100,380,100,30);

        bordingfield = new JTextField();
        bordingfield.setBounds(220,380,200,26);



        cnclbtn = new JButton("Cancel");
        cnclbtn.setBounds(169,509,90,30);

        addbtn = new JButton("Add Student");
        addbtn.setBounds(469,500,130,40);
        


        add(infolbl);
        add(namelbl);
        add(namField);
        add(fnamlbl);
        add(fnamField);
        add(fathercniclbl);
        add(fathercnicfield);
        add(doblbl);
        add(dobField);
        add(contactlbl);
        add(contactField);
        add(addresslbl);
        add(addressField);
        add(deptlbl);
        add(deptfield);
        add(bordinglbl);
        add(bordingfield);

        add(cnclbtn);
        add(addbtn);


        addbtn.addActionListener(this);
        cnclbtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == addbtn){
            try {
                DBcon con = new DBcon();
            
                String namestr = namField.getText();
                String fnamstr = fnamField.getText();
                String fcnicstr = fathercnicfield.getText();
                String contactstr = contactField.getText();
                String addressstr = addressField.getText();
                String deptstr = (String) deptfield.getSelectedItem();
                String boardstr = bordingfield.getText();
                
                // Get the date from the dobField (JDateChooser)
                Date dobstr = dobField.getDate();
            
                // Format the date for MySQL
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(dobstr);
            
                // Correct the SQL query by removing the extra quote before formattedDate
                String insertstd = "insert into student(name,Father_name,father_cnic,contact,address,department,boarding,date_of_birth) values ('"+namestr+"','"+fnamstr+"','"+fcnicstr+"','"+contactstr+"','"+addressstr+"','"+deptstr+"','"+boardstr+"','"+formattedDate+"')";
            
                int rs = con.stmnt.executeUpdate(insertstd);
            
                if(rs > 0) {
                    JOptionPane.showMessageDialog(this, "Student Added Successfully");
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
            
        }

        else if (e.getSource() == cnclbtn){
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Addstudent();
    }
}