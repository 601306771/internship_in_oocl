package com.cargosmart.view;

import java.awt.Color;
import java.awt.Container;  
import java.awt.Point;  
import java.awt.Toolkit;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;  
import javax.swing.JTextArea;

import com.cargosmart.java.api.Options;
import com.cargosmart.java.comm.PropertiesHandlers;

public class OptionView implements ActionListener { 
	String input;
	String output;
    JFrame frame = new JFrame("OPTIONS");// 框架布局  
    JTabbedPane tabPane = new JTabbedPane();// 选项卡布局  
    Container con = new Container();//  
    JLabel label1 = new JLabel("File names : use \',\' divide names"); 
    JButton button1 = new JButton("change");// 选择  
    JButton button2 = new JButton("save/leave");// 选择 
    JTextArea text1;
      
    OptionView(String inputStr,String outputStr) {
    	this.input = inputStr;
    	this.output = outputStr;
    	//System.out.println(input+","+output);
    	frame.setResizable(false);
    	PropertiesHandlers config = new PropertiesHandlers();
    	Map<Integer,String> fileNames = config.getFileName();
    	StringBuffer fileStr = new StringBuffer();
    	for(int i=0;i<fileNames.size();i++){
    		fileStr.append(fileNames.get(i)+",\n");
    	}
    	
    	
    	text1 = new JTextArea(fileStr.toString(), 20, 43);
    	text1.setSelectedTextColor(Color.RED);
    	text1.setLineWrap(true);        //激活自动换行功能 
    	text1.setWrapStyleWord(true);            // 激活断行不断字功能
    	text1.setEditable(false);
    	text1.setOpaque(false);
    	text1.setBorder(BorderFactory.createLineBorder(Color.gray,3));
    	JScrollPane scroll = new JScrollPane(text1);
    	scroll.setVerticalScrollBarPolicy( 
    			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
          
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
          
        frame.setLocation(new Point((int) (lx / 2) - 200, (int) (ly / 2) - 250));// 设定窗口出现位置  
        frame.setSize(400, 580);// 设定窗口大小  
        frame.setContentPane(tabPane);// 设置布局  
        
        label1.setBounds(45, 10, 270, 20);  
        scroll.setBounds(45, 40, 300, 400); 
        button1.setBounds(80, 470, 100, 20); 
        button2.setBounds(200, 470, 100, 20); 
        
        button1.addActionListener(this); // 添加事件处理  
        button2.addActionListener(this); // 添加事件处理  
        
        con.add(label1);
        con.add(scroll);
        con.add(button1);  
        con.add(button2);  
          
       
        frame.setVisible(true);// 窗口可见  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序  
        tabPane.add("FileName Setting", con);// 添加布局1  
    }  
    /** 
     * 时间监听的方法 
     */  
    public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个  
        	Object[] options = {"edit","cancel"};
        	int response = JOptionPane.showOptionDialog(tabPane, "Do you want to edit file names? if yes ,must use ',' divide names", "Notice!",JOptionPane.YES_OPTION, 
        			  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        	  
        	if(response==0)
        	{ 
        		text1.setEditable(true);
            	text1.setOpaque(true);
        	}else if(response==1)
        	{ 
        		frame.dispose(); 
        	}
        	
        	
        } 
        
        if (e.getSource().equals(button2)) {// 判断触发方法的按钮是哪个
    		JOptionPane.showMessageDialog(null,"Save successful", "Notic!!", 2);
        	//text1.setEditable(false);
        	//text1.setOpaque(false);
        	frame.dispose();
        	StringBuffer newFileNames = new StringBuffer();
    		newFileNames.append(text1.getText());
    		Options ops = new Options();
    		ops.changeFileName(newFileNames);
    		System.out.println(input+","+output);
    		ReportToolView s2 =  new ReportToolView(input,output);
        } 
  
    } 
    
    
}  
