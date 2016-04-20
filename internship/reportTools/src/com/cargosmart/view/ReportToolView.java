package com.cargosmart.view;

import java.awt.Color;
import java.awt.Container;  
import java.awt.Point;  
import java.awt.Toolkit;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.io.File;  
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;  
import javax.swing.JDialog;
import javax.swing.JFileChooser;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JOptionPane;  
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;  
import javax.swing.JTextArea;
import javax.swing.JTextField;  

import com.cargosmart.java.api.ReportProducer;
import com.cargosmart.java.comm.PropertiesHandlers;


public class ReportToolView implements ActionListener {  
	String input;
	String output;
    JFrame frame = new JFrame("ReportTools");// 框架布局  
    JTabbedPane tabPane = new JTabbedPane();// 选项卡布局  
    Container con = new Container();//  
    JLabel label1 = new JLabel("input path");  
    JLabel label2 = new JLabel("output path"); 
    JLabel label3 = new JLabel("==messages==",JLabel.CENTER);
    JTextField text1 = new JTextField();// TextField 目录的路径  
    JTextField text2 = new JTextField();// 文件的路径  
    JButton button1 = new JButton("...");// 选择  
    JButton button2 = new JButton("...");// 选择  
    JFileChooser jfc = new JFileChooser();// 文件选择器  
    JButton button3 = new JButton("run reports");
    JButton button4 = new JButton("change File");
    JTextArea text3;
    
    public ReportToolView(String inputStr,String outputStr) {
    	this.input = inputStr;
    	this.output = outputStr;
    	frame.setResizable(false);
    	if(input!=null){
    		text1.setText(input);
    	}
    	if(output!=null){
    		text2.setText(output);
    	}
    	StringBuffer fileStr = getNames();
    	
    	text3 = new JTextArea(fileStr.toString(), 20, 43);
    	text3.setSelectedTextColor(Color.RED);
    	text3.setLineWrap(true);        //激活自动换行功能 
    	text3.setWrapStyleWord(true);            // 激活断行不断字功能
    	text3.setEditable(false);
    	//text3.setOpaque(false);
    	text3.setBorder(BorderFactory.createLineBorder(Color.gray,3));
    	JScrollPane scroll = new JScrollPane(text3);
    	scroll.setVerticalScrollBarPolicy( 
    			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	
        jfc.setCurrentDirectory(new File("C:\\"));// 文件选择器的初始目录定为d盘  
          
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
          
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
          
        frame.setLocation(new Point((int) (lx / 2) - 250, (int) (ly / 2) - 250));// 设定窗口出现位置  
        frame.setSize(500, 400);// 设定窗口大小  
        frame.setContentPane(tabPane);// 设置布局  
        label1.setBounds(45, 10, 70, 20);  
        text1.setBounds(110, 10, 255, 20);  
        button1.setBounds(365, 10, 50, 20);  
        label2.setBounds(45, 35, 70, 20);  
        text2.setBounds(110, 35, 255, 20);  
        button2.setBounds(365, 35, 50, 20); 
        scroll.setBounds(45, 70, 380, 200); 
        button3.setBounds(120, 280, 110, 20);  
        button4.setBounds(250, 280, 110, 20); 
        label3.setBounds(145, 305, 150, 20); 
        button1.addActionListener(this); // 添加事件处理  
        button2.addActionListener(this); // 添加事件处理  
        button3.addActionListener(this); // 添加事件处理  
        button4.addActionListener(this); // 添加事件处理  
        con.add(label1);  
        con.add(text1);  
        con.add(button1);  
        con.add(label2);  
        con.add(text2);  
        con.add(button2);
        con.add(scroll);  
        con.add(button3);
        con.add(button4);
        con.add(label3);
       
        frame.setVisible(true);// 窗口可见  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序  
        tabPane.add("REPORTPRODUCER", con);// 添加布局1  
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
        // 绑定到选择文件，先择文件事件  
        if (e.getSource().equals(button2)) {  
            jfc.setFileSelectionMode(1);// 设定只能选择到文件  
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;// 撤销则返回  
            } else {  
                File f = jfc.getSelectedFile();// f为选择到的文件  
                text2.setText(f.getAbsolutePath());  
            }  
        }  
        
        if (e.getSource().equals(button3)) {  
        	 // label3.setText("running report. . .");
        	  if(text1.getText()==null||text1.getText().equals("")||text2.getText()==null||text2.getText().equals("")){
        		  JOptionPane.showMessageDialog(null, "please complete input and output path!!!", "warning", 2); 
        		  label3.setText("==messages==");
        	  }else{
        		  
        		  String input = text1.getText();
	        	  String output = text2.getText(); 
	        	  label3.setText("running report. . .");
	        	  Object[] options = {"start","reset"};
	        	  int response = JOptionPane.showOptionDialog(tabPane, "Please make sure the input and output information \n input     :"
	        			  +text1.getText()+"\n output  :"+text2.getText(), "Notice!",JOptionPane.YES_OPTION, 
	        			  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	        	  
	        	  if(response==0)
	        	  { 
		        	  ReportProducer reportProducer = new ReportProducer(input,output);
		        	  reportProducer.startProducing();
		        	  label3.setText("finish");	
		        	  JOptionPane.showMessageDialog(null,"Your report is here:"+text2.getText(), "Finish running", 2);
	        	  }
	        	  else if(response==1)
	        	  { 
	        		  label3.setText("please set path");	
	        	  }
        	  }
        } 
        
        // 绑定事件  
        if (e.getSource().equals(button4)) {  
        	input = text1.getText().trim();
        	output = text2.getText().trim();
        	OptionView option = new OptionView(input,output);
        	frame.dispose();
        }  
        StringBuffer fileStr = getNames();
    	text3.setText(fileStr.toString());
    } 
    
    /**
     * get file name from config
     * @return
     */
    public StringBuffer getNames(){
    	PropertiesHandlers config = new PropertiesHandlers();
    	Map<Integer,String> fileNames = config.getFileName();
    	
    	StringBuffer fileStr = new StringBuffer();
    	fileStr.append("Input File Names: \n \n");
    	for(int i=0;i<fileNames.size();i++){
    		fileStr.append(fileNames.get(i)+"       ");
    		
    	}
    	return fileStr;
    }
}  