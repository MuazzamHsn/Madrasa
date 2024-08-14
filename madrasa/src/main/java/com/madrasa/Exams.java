package com.madrasa;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.sql.ResultSet;

import javax.swing.*;


import net.proteanit.sql.DbUtils;

public class Exams extends JFrame implements ActionListener, Printable {
    JLabel fltrbylbl,stdinfo;
    JComboBox<String> fltrbyfield;
    String departments[] = {"none","ILM","Hifz","Nazira","Qaaida" };
    JButton loadbtn,backbtn,prntbtn;
    JTable table;

    private JLabel admlbl, Namelbl, FatherNamelbl, Departmentlbl, totalmrklbl,obtmrklbl,percentlbl;


    public Exams(){
        super("Examinations");
        setLayout(null);
        setBounds(90,30,1200, 700);

        fltrbyfield = new JComboBox<String>(departments);
        fltrbyfield.setBounds(550,20,300,30);

        fltrbylbl = new JLabel("Filter by: ");
        fltrbylbl.setBounds(480, 22, 90, 26);

        stdinfo = new JLabel("Exam Details");
        stdinfo.setFont(new Font("Typograph pro",Font.BOLD,15));
        stdinfo.setBounds(120,60,300,40);

        backbtn = new JButton("Back");
        backbtn.setBounds(950,630,90,26);

        prntbtn = new JButton("Print");
        prntbtn.setBounds(750,625,90,30);
        
        loadbtn = new JButton("Load");
        loadbtn.setBounds(600,625,90,30);



         //Table Column Head names 
         admlbl = new JLabel("Admission Number");
         admlbl.setBounds(35, 90, 200, 30);
         add(admlbl);
 
         Namelbl = new JLabel("Name");
         Namelbl.setBounds(200,90,200,30);
         add(Namelbl);
         
         FatherNamelbl = new JLabel("Father's Name");
         FatherNamelbl.setBounds(360, 90, 200, 30);
         add(FatherNamelbl);
         
         Departmentlbl = new JLabel("Department");
         Departmentlbl.setBounds(510, 90, 200, 30);
         add(Departmentlbl);
         
         totalmrklbl = new JLabel("Total Marks");
         totalmrklbl.setBounds(670, 90, 200, 30);
         add(totalmrklbl);
         
         obtmrklbl = new JLabel("Obtained Marks");
         obtmrklbl.setBounds(825, 90, 200, 30);
         add(obtmrklbl);
         
         percentlbl = new JLabel("Percentage");
         percentlbl.setBounds(980, 90, 200, 30);
         add(percentlbl);
         
         



        table = new JTable();
        table.setBounds(30,120,1100,500);



        add(fltrbyfield);
        add(fltrbylbl);
        add(stdinfo);
        add(backbtn);
        add(loadbtn);
        add(prntbtn);
        add(table);

        loadbtn.addActionListener(this);
        prntbtn.addActionListener(this);
        backbtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent eee){
        if((eee.getSource() == loadbtn)){
            String filterstr = (String) fltrbyfield.getSelectedItem();

            if(filterstr =="none" ){
                try{
                    DBcon connection = new DBcon();
    
                    ResultSet rs =connection.stmnt.executeQuery("select admission_no,name,father_name,department,total_marks,obtained_marks,percentage from student inner join exam on student.admission_no = exam.adm_no order by percentage");
    
                    table.setModel(DbUtils.resultSetToTableModel(rs));
    
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            else{
                try{
                    DBcon connection = new DBcon();
    
                    String sql = "SELECT student.admission_no, student.name, student.father_name, student.department, exam.total_marks, exam.obtained_marks, (exam.obtained_marks / exam.total_marks * 100) AS percentage " +
                    "FROM student " +
                    "INNER JOIN exam ON student.admission_no = exam.adm_no " +
                    "WHERE student.department ='"+filterstr+"' ";

                    ResultSet rs = connection.stmnt.executeQuery(sql);
       
    
                    table.setModel(DbUtils.resultSetToTableModel(rs));
    
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

        }

        else if(eee.getSource() == prntbtn){
            printresult();
        }

        else if(eee.getSource() == backbtn){
            this.dispose();
        }
    }




    public BufferedImage frameimg() {
        // Define the scale factor for higher resolution (e.g., 2x or 4x)
        double scaleFactor = 4.0;
    
        // Calculate the scaled dimensions
        int width = (int) (this.getWidth() * scaleFactor);
        int height = (int) (this.getHeight() * scaleFactor);
    
        // Create a high-resolution BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
        // Create a Graphics2D object from the BufferedImage
        Graphics2D gd = image.createGraphics();
    
        // Apply the scaling transformation
        gd.scale(scaleFactor, scaleFactor);
    
        // Paint the JFrame onto the high-resolution BufferedImage
        this.paint(gd);
    
        // Dispose of the Graphics2D object
        gd.dispose();
    
        return image;
    }
    









        public void printresult(){
           PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);
            
            boolean doPrint = job.printDialog();
            if(doPrint){
                try {
                    job.print();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        }
    

        @Override
public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
    if (page > 0) {
        return NO_SUCH_PAGE;
    }

    Graphics2D g2d = (Graphics2D) g;

    // Translate to the top-left corner of the printable area
    g2d.translate(pf.getImageableX(), pf.getImageableY());

    // Define the Y range to print (85 to 630)
    int yStart = 85;
    int yEnd = 652;
    int heightToPrint = yEnd - yStart;

    // Calculate scale factors to fit the specified portion onto the printed page
    double scaleX = pf.getImageableWidth() / this.getWidth();
    double scaleY = pf.getImageableHeight() / heightToPrint;
    double scale = Math.min(scaleX, scaleY);

    // Apply the scaling
    g2d.scale(scale, scale);

    // Translate up by yStart to print the correct portion
    g2d.translate(0, yStart);

    // Create a clipping area for the specific Y range
    g2d.setClip(0, yStart, this.getWidth(), heightToPrint);

    // Paint the portion of the JFrame onto the Graphics context
    this.printAll(g2d);

    return PAGE_EXISTS;
}

        





    public static void main(String[] args) {
        new Exams();
    }
    
}
