package com.madrasa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;

import javax.swing.*;

public class MarksEntry extends JFrame implements ActionListener{
    JLabel infolbl,Admnolbl,namelbl,fnamlbl,fathercniclbl,totmrkslbl,obtmrkslbl,deptlbl,percentlbl;
    JTextField admnoField,namField,fnamField,fathercnicfield,totmrksfield,obtmrksfield,percentfield;
    JComboBox<String> deptfield ;
    String departments[] = {"ILM","Hifz","Nazira","Qaaida" };
    JButton findbtn,cnclbtn,addbtn;

    public MarksEntry(){
        super("Enter Marks");
        setLayout(null);
        setBounds(230,100,900,600);

        infolbl = new JLabel("Enter Marks");
        infolbl.setFont(new Font("Typograph Pro", Font.PLAIN, 20));
        infolbl.setBounds(170, 30, 500, 40); 
        
        Admnolbl = new JLabel("Admission No.  : ");
        Admnolbl.setBounds(100,100,100,30);

        admnoField = new JTextField();
        admnoField.setBounds(220,100,120,26);

        findbtn = new JButton("Find");
        findbtn.setBounds(360,100,70,25);

        namelbl = new JLabel("Name : ");
        namelbl.setBounds(100,140,90,30);

        namField = new JTextField();
        namField.setBounds(220,140,200,26);

        fnamlbl = new JLabel("Father's Name : ");
        fnamlbl.setBounds(100,180,100,30);

        fnamField = new JTextField();
        fnamField.setBounds(220, 180, 200, 26);

        deptlbl = new JLabel("Department : ");
        deptlbl.setBounds(100, 220, 100, 30);

        deptfield = new JComboBox<String>(departments);
        deptfield.setBounds(220,220,220,26);

        totmrkslbl = new JLabel("Total Marks : ");
        totmrkslbl.setBounds(100, 260, 90,30);

        totmrksfield = new JTextField();
        totmrksfield.setBounds(220,260,100,26);

        obtmrkslbl = new JLabel("Obtained Marks : ");
        obtmrkslbl.setBounds(100,300,110,30);

        obtmrksfield = new JTextField();
        obtmrksfield.setBounds(220,300,100,26);

        percentlbl = new JLabel("Percentage : ");
        percentlbl.setBounds(100,340,100,30);

        percentfield = new JTextField();
        percentfield.setBounds(220,340,100,26);


        cnclbtn = new JButton("Cancel");
        cnclbtn.setBounds(169,509,90,30);

        addbtn = new JButton("Enter Marks");
        addbtn.setBounds(469,500,130,40);


        //When percent field is clicked, it auto fills it.

        percentfield.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String totalmrk = totmrksfield.getText();
                String obtmrsks = obtmrksfield.getText();

                if (!totalmrk.isEmpty() && !obtmrsks.isEmpty()) {
                    try {
                        Integer totmrks = Integer.parseInt(totalmrk);
                        Integer obtmrks = Integer.parseInt(obtmrsks);
                        Float percent;

                        percent = (float) obtmrks / totmrks * 100;

                        percentfield.setText(percent.toString());
                    } catch (NumberFormatException ex) {
                        percentfield.setText("Invalid Input");
                    }
                } else {
                    percentfield.setText("Enter marks");
                }
            }
        });



        add(Admnolbl);
        add(admnoField);
        
        add(findbtn);

        add(infolbl);
        add(namelbl);
        add(namField);
        add(fnamlbl);
        add(fnamField);
        add(totmrkslbl);
        add(totmrksfield);
        add(obtmrkslbl);
        add(obtmrksfield);
        add(deptlbl);
        add(deptfield);
        add(percentlbl);
        add(percentfield);

        add(cnclbtn);
        add(addbtn);


        findbtn.addActionListener(this);
        addbtn.addActionListener(this);
        cnclbtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == addbtn){
            try{
                DBcon conn = new DBcon();

                Integer admstr = Integer.parseInt(admnoField.getText());
                Integer totlmrkstr = Integer.parseInt(totmrksfield.getText());
                Integer obtmrkstr = Integer.parseInt(obtmrksfield.getText());
                

                int result = conn.stmnt.executeUpdate("INSERT INTO exam(adm_no,total_marks,obtained_marks)  VALUES ('" + admstr + "', '" + totlmrkstr + "', '" + obtmrkstr + "')");


                if (result>0) {
                    JOptionPane.showMessageDialog(this, "Marks added Successfully");
                }
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
        }


        else if(e.getSource() == findbtn){
            try {
                DBcon conn = new DBcon();
                String admstrr = admnoField.getText();
                ResultSet rs;
        
                String query = "select name, father_name, department from student where admission_no ='" + admstrr + "'";
                rs = conn.stmnt.executeQuery(query);
        
                if (rs.next()) {
                    namField.setText(rs.getString("name"));
                    fnamField.setText(rs.getString("father_name"));
                    deptfield.setSelectedItem(rs.getString("department"));
                }
                else{
                    JOptionPane.showMessageDialog(this, "Student not Found! Please check again");
                }
        
                // Close the result set and statement
                rs.close();
                conn.stmnt.close(); // Close the statement if necessary
        
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        
        else if (e.getSource() == cnclbtn){
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new MarksEntry();
    }
}