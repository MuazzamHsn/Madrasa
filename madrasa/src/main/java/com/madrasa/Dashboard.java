package com.madrasa;


import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Dashboard extends JFrame implements ActionListener{

    JLabel name,villnam,projnm,cpyrght;

    JButton addstudbtn,addtechrbtn,studinfobtn,techinfobtn,fundbtn,expensebtn,mrksentrybtn,examsbtn,givsalbtn,salinfobtn,financebtn,genrtcrtbtn,closebtn;

    
    JMenuBar menubar = new JMenuBar();
    JMenu admin = new JMenu("Admin");
    JMenu student = new JMenu("Student");
    JMenu teacher = new JMenu("Teacher");
    JMenu accounts = new JMenu("Accounts");
    JMenu power = new JMenu("Power");

    JMenuItem addstudent = new JMenuItem("Add new Student");
    JMenuItem addteacher = new JMenuItem("Add new Teacher");
    JMenuItem addfund = new JMenuItem("Add new Fund");
    JMenuItem teacherinfo = new JMenuItem("Teacher Info");
    JMenuItem studentinfo = new JMenuItem("Student Info");
    JMenuItem expenses = new JMenuItem("Calculate Expenses");

    JMenuItem logoff = new JMenuItem("Log Off");
    JMenuItem exit = new JMenuItem("Close");

    JLabel bdbtn;
    JPanel menupanel;


    public Dashboard(){
        super("DashBoard");
        this.setJMenuBar(menubar);
        setLayout(null);

        projnm = new JLabel("Madrasa Management System");
        projnm.setFont((new Font("Century Gothic", Font.BOLD, 30)));
        projnm.setForeground(new Color(0x433637));
        projnm.setBounds(350,10,1000,200);
        add(projnm);

        name = new JLabel("جامعہ عمرینؓ تعلیم القرآن");
        name.setFont((new Font("Urdu Typesetting", Font.PLAIN, 100)));
        name.setForeground(new Color(0x009000));
        name.setBounds(540,250,1000,200);
        add(name);

        villnam = new JLabel("مشتوبانڈہ    بگٹو    ھنگو");
        villnam.setFont((new Font("Urdu Typesetting", Font.PLAIN, 40)));
        villnam.setForeground(new Color(0xce4a00));
        villnam.setBounds(750,400,1000,200);
        add(villnam);

                //IMAGE BACKGROUND

        // Load the image into an ImageIcon
        ImageIcon imageIcon = new ImageIcon("madrasa\\src\\main\\resources\\images\\logo.png");
        // Resize the image to 100x100 pixels
        Image smallImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        // Set the resized image to a new ImageIcon
        ImageIcon smallIcon = new ImageIcon(smallImage);
        // Create a new JLabel and set the icon to the resized image
        JLabel label = new JLabel(smallIcon);
        label.setBounds(1085,5,250,250);
        // Add the label to the JFrame
        add(label);


        cpyrght = new JLabel("© 2024 Copyrighted. All rights reserved with Developer MSK");
        cpyrght.setFont(new Font("Alata",Font.PLAIN,15));
        cpyrght.setBounds(600,650,500,30);
        add(cpyrght);

        menupanel = new JPanel();
        menupanel.setBounds(0,0,290,1000);
        menupanel.setBackground(new Color(0x2a2c41));
        menupanel.setLayout(null);
        add(menupanel);

        //Buttons system from here

        addstudbtn = new JButton("Add New Student");
        addtechrbtn = new JButton("Add New Teacher");
        studinfobtn = new JButton("Students Info");
        techinfobtn = new JButton("Teachers Info");
        fundbtn = new JButton("Add new Fund");
        expensebtn = new JButton("Calculate Expenses");
        mrksentrybtn = new JButton("Enter Marks");
        examsbtn = new JButton("Exam");
        givsalbtn = new JButton("Pay Salary");
        salinfobtn = new JButton("Salaries Information");
        financebtn = new JButton("Finance");
        genrtcrtbtn = new JButton("Generate Certificate");
        closebtn = new JButton("CLOSE");


        addstudbtn.setBounds(50,50,180,30);
        addtechrbtn.setBounds(50,90,180,30);
        studinfobtn.setBounds(50,130,180,30);
        techinfobtn.setBounds(50,170,180,30);
        fundbtn.setBounds(50,210,180,30);
        expensebtn.setBounds(50,250,180,30);
        examsbtn.setBounds(50,290,180,30);
        mrksentrybtn.setBounds(50,330,180,30);
        givsalbtn.setBounds(50,370,180,30);
        salinfobtn.setBounds(50,410,180,30);
        financebtn.setBounds(50,450,180,30);
        genrtcrtbtn.setBounds(50,490,180,30);
        closebtn.setBounds(50,650,180,30);

        // Color myColor = new Color(0xFC3131);

        addstudbtn.setBackground(new Color(0xE9EDE0));
        addtechrbtn.setBackground(new Color(0xE9EDE0));
        techinfobtn.setBackground(new Color(0xE9EDE0));
        studinfobtn.setBackground(new Color(0xE9EDE0));
        fundbtn.setBackground(new Color(0xE9EDE0));
        expensebtn.setBackground(new Color(0xE9EDE0));
        examsbtn.setBackground(new Color(0xE9EDE0));
        mrksentrybtn.setBackground(new Color(0xE9EDE0));
        givsalbtn.setBackground(new Color(0xE9EDE0));
        salinfobtn.setBackground(new Color(0xE9EDE0));
        financebtn.setBackground(new Color(0xE9EDE0));
        salinfobtn.setBackground(new Color(0xE9EDE0));
        genrtcrtbtn.setBackground(new Color(0xE9EDE0));
        closebtn.setBackground(new Color(0xE9EDE0));
 
        

        closebtn.setForeground(new Color(0xb10209));

        menupanel.add(addstudbtn);
        menupanel.add(addtechrbtn);
        menupanel.add(studinfobtn);
        menupanel.add(techinfobtn);
        menupanel.add(fundbtn);
        menupanel.add(expensebtn);
        menupanel.add(examsbtn);
        menupanel.add(mrksentrybtn);
        menupanel.add(givsalbtn);
        menupanel.add(salinfobtn);
        menupanel.add(financebtn);
        menupanel.add(genrtcrtbtn);
        menupanel.add(closebtn);

        addstudbtn.addActionListener(this);
        addtechrbtn.addActionListener(this);
        studinfobtn.addActionListener(this);
        techinfobtn.addActionListener(this);
        fundbtn.addActionListener(this);
        expensebtn.addActionListener(this);
        examsbtn.addActionListener(this);;
        mrksentrybtn.addActionListener(this);
        salinfobtn.addActionListener(this);
        givsalbtn.addActionListener(this);
        financebtn.addActionListener(this);
        genrtcrtbtn.addActionListener(this);
        closebtn.addActionListener(this);

// for image insertion
        // bdbtn = new JLabel();
        // bdbtn.setIcon(new ImageIcon(getClass().getResource("/icons/bg.jpg")));
        // bdbtn.setBounds(0,0,2000,1000);
        // add(bdbtn);


        //MENU Setting from here
        
        admin.add(addstudent);
        admin.add(addteacher);

        student.add(studentinfo);
        teacher.add(teacherinfo);

        accounts.add(addfund);
        accounts.add(expenses);

        power.add(logoff);
        power.add(exit);

        menubar.add(admin);
        menubar.add(student);
        menubar.add(teacher);
        menubar.add(accounts);
        menubar.add(power);

        studentinfo.addActionListener(this);
        teacherinfo.addActionListener(this);
        addstudent.addActionListener(this);
        addteacher.addActionListener(this);
        expenses.addActionListener(this);
        addfund.addActionListener(this);
        logoff.addActionListener(this);
        exit.addActionListener(this);

        setSize(2000,1000);
        setVisible(true);
    }


    public static void main(String [] args){
        new Dashboard();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addstudent || e.getSource() == addstudbtn) {
            new Addstudent();
        }
        else if (e.getSource() == addteacher || e.getSource() == addtechrbtn ){
            new Addteacher();
        }
        else if (e.getSource() == addfund || e.getSource() == fundbtn){
            new Addfunds();
        }
        else if (e.getSource() == studentinfo || e.getSource() == studinfobtn){
            new Studentsinfo();
        }
        else if (e.getSource() == teacherinfo || e.getSource() == techinfobtn){
            new Teachersinfo();
        }
        else if (e.getSource() == expenses || e.getSource() == expensebtn){
            new Expenses();
        }
        else if(e.getSource() == examsbtn){
            new Exams();
        }
        else if(e.getSource() == mrksentrybtn){
            new MarksEntry();
        }
        else if(e.getSource() == salinfobtn){
            new Salaries();
        }
        else if(e.getSource() == givsalbtn){
            new PaySal();
        }
        else if(e.getSource() == financebtn){
            new ShowFinance();
        }
        else if(e.getSource() == genrtcrtbtn){
            new CertificateGenerator();
        }
        else if(e.getSource() == exit || e.getSource() == closebtn){
            System.exit(0);
        }
        else if (e.getSource() == logoff){
            this.dispose();
            new Login();
        }
    }
}

