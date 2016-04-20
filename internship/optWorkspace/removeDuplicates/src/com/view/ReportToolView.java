package com.view;

import java.awt.Color;
import java.awt.Container;  
import java.awt.Point;  
import java.awt.Toolkit;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.io.File;  
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;  
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JOptionPane;  
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;  
import javax.swing.JTextArea;
import javax.swing.JTextField;  

import com.api.CutDuplicates;



public class ReportToolView implements ActionListener {  
	String[] str = {"1","2","3","4","5","6"};
	String input;
	String output;
    JFrame frame = new JFrame("CutDuplicates");// 框架布局  
    JTabbedPane tabPane = new JTabbedPane();// 选项卡布局  
    Container con = new Container();//  
    JLabel label1 = new JLabel("input path");  
    JRadioButton sbutton1 = new JRadioButton("去开头");
    JRadioButton sbutton2 = new JRadioButton("去重复");
    JComboBox jcb = new JComboBox(str);
    JLabel label2 = new JLabel("行数/重复次数:");
    ButtonGroup group = new ButtonGroup();
    JLabel label3 = new JLabel("==messages==",JLabel.CENTER);
    JTextField text1 = new JTextField();// TextField 目录的路径  
    JButton button1 = new JButton("...");// 选择  
    JFileChooser jfc = new JFileChooser();// 文件选择器  
    JButton button3 = new JButton("run file");
    JTextArea text3;
    
    public ReportToolView() {
    	frame.setResizable(false);
    	if(input!=null){
    		text1.setText(input);
    	}
        jfc.setCurrentDirectory(new File("C:\\")); 
          
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
          
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
          
        frame.setLocation(new Point((int) (lx / 2) - 250, (int) (ly / 2) - 250));// 设定窗口出现位置  
        frame.setSize(480, 220);// 设定窗口大小  
        frame.setContentPane(tabPane);// 设置布局  
        label1.setBounds(45, 10, 70, 20);  
        text1.setBounds(110, 10, 255, 20);
        group.add(sbutton1);
        group.add(sbutton2); 
        sbutton1.setBounds(100, 50, 80, 20); 
        sbutton1.setSelected(true);
        sbutton2.setBounds(180, 50, 80, 20);
        label2.setBounds(270,50,90,20);
        jcb.setBounds(360,50,50,20);
        button1.setBounds(365, 10, 50, 20);  
        button3.setBounds(165, 90, 110, 20);  
        label3.setBounds(145, 110, 150, 20); 
        button1.addActionListener(this); // 添加事件处理  
        button3.addActionListener(this); // 添加事件处理  
        con.add(label2);
        con.add(jcb);
        con.add(sbutton1);
        con.add(sbutton2);
        con.add(label1);  
        con.add(text1);  
        con.add(button1);  
        con.add(button3);
        con.add(label3);
       
        frame.setVisible(true);// 窗口可见  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序  
        tabPane.add("CutDuplicates", con);// 添加布局1  
    }  
    /** 
     * 时间监听的方法 
     */  
    public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个  
            jfc.setFileSelectionMode(1);// 设定只能选择到文件夹  
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;  
            } else {  
                File f = jfc.getSelectedFile();// f为选择到的目录  
                text1.setText(f.getAbsolutePath());  
            }  
        }  
        
        
        if (e.getSource().equals(button3)) {  
        	 if(text1.getText()==null||text1.getText().equals("")){
       		  JOptionPane.showMessageDialog(null, "please complete input and output path!!!", "warning", 2); 
       		  label3.setText("input wrong");
       	  }else{
       		  String path  = text1.getText().toString().trim()+"\\";
       		  System.out.println(path);   
       		  CutDuplicates cd = new CutDuplicates();
       		  String type = null;
       		  int num = 0;
       		  if(sbutton1.isSelected()){
       			type = "begin";
       		  }else if(sbutton2.isSelected()){
       			type =  "removeCopy";
       		  }
       		  num = Integer.parseInt(jcb.getSelectedItem().toString());
       		  cd.cut(path,type,num);
       		  JOptionPane.showMessageDialog(null, "All Finish", "information", 2); 
       		  label3.setText("==messages==");
       	  }
        } 
        
        
    }
}  