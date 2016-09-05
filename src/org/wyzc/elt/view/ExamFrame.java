package org.wyzc.elt.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.wyzc.elt.control.ClientContext;
import org.wyzc.elt.entity.ExamInfo;
import org.wyzc.elt.entity.QuestionInfo;
import org.wyzc.elt.util.ReadUtil;

public class ExamFrame extends JFrame {
	private JLabel examInfoLabel;
	private JTextArea jta;
	private JLabel label;
	private JButton pre;
	private JButton next;
	private JLabel timerLabel;
	private ArrayList<Option> options = new ArrayList<Option>();
	/*
	 * public static void main(String[] args) { new
	 * ExamFrame().setVisible(true); }
	 */

	private ClientContext clientContext;

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
		this.clientContext.setExamFrame(this);
	}

	public ExamFrame() {
		this.setTitle("我赢在线测评系统");
		this.setSize(680, 380);
		this.setLocationRelativeTo(this);
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clientContext.exit();
			}
		});
		this.setContentPane(createContentPanel());
	}

	private JPanel createContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		URL url = this.getClass().getResource("exam_title.png");
		ImageIcon icon = new ImageIcon(url);
		panel.add(BorderLayout.NORTH, new JLabel(icon, JLabel.CENTER));
		panel.add(BorderLayout.CENTER, createCenterPanel());
		panel.add(BorderLayout.SOUTH, createSPanel());
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		examInfoLabel = new JLabel("姓名:xxx 编号:0001 考试时间:1分钟 题量:20",
				JLabel.CENTER);
		panel.add(BorderLayout.NORTH, examInfoLabel);
		panel.add(BorderLayout.CENTER, createQuestionPanel());
		panel.add(BorderLayout.SOUTH, createOptionsPanel());
		return panel;
	}

	private JScrollPane createQuestionPanel() {
		JScrollPane panel = new JScrollPane();
		jta = new JTextArea();
		jta.setText("A\n\nB\n\n");
		jta.setLineWrap(true);
		jta.setFocusable(false);
		panel.getViewport().add(jta);
		return panel;
	}

	private JPanel createOptionsPanel() {
		JPanel panel = new JPanel();
		
		/*JCheckBox box1 = new JCheckBox("A");
		JCheckBox box2 = new JCheckBox("B");
		JCheckBox box3 = new JCheckBox("C");
		JCheckBox box4 = new JCheckBox("D");*/
		Option box1 = new Option("A", 1);
		Option box2 = new Option("B", 2);
		Option box3 = new Option("C", 3);
		Option box4 = new Option("D", 4);
		panel.add(box1);
		panel.add(box2);
		panel.add(box3);
		panel.add(box4);
		
		options.add(box1);
		options.add(box2);
		options.add(box3);
		options.add(box4);
		return panel;
	}
	private class Option extends JCheckBox{
		private int value;
		public Option(String name,int value){
			super(name);
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}

	private JPanel createSPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		label = new JLabel("共20题当前是第一个", JLabel.LEFT);
		panel.add(BorderLayout.WEST, label);
		panel.add(BorderLayout.CENTER, createBtnPanel());
		timerLabel =  new JLabel("00:00:59", JLabel.RIGHT);
		panel.add(BorderLayout.EAST,timerLabel);
		return panel;
	}

	private JPanel createBtnPanel() {
		JPanel panel = new JPanel();
		pre = new JButton("前一题");
		pre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.pre();
			}
		});
		next = new JButton("后一题");
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.next();
			}
		});
		JButton submit = new JButton("交卷");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.send();
			}
		});
		panel.add(pre);
		panel.add(next);
		panel.add(submit);
		return panel;
	}

	public void updateView(ExamInfo examInfo, QuestionInfo questionInfo) {
		examInfoLabel.setText(examInfo.toString());
		int pageIndex = questionInfo.getPageIndex();
		updateBtn(pageIndex);
		jta.setText(questionInfo.getQuestion().toString());
		label.setText("共20天当前是第" + (questionInfo.getPageIndex() + 1) + "题");
	    updateOptions(questionInfo);
	}
	private void updateBtn(int pageIndex){
		if(pageIndex==0)
			this.pre.setEnabled(false);
		else
			this.pre.setEnabled(true);
		if(pageIndex==19)
			this.next.setEnabled(false);
		else
			this.next.setEnabled(true);
	}
	private void updateOptions(QuestionInfo questionInfo){
		ArrayList<Integer> userAnswers = questionInfo.getUserAnswers();
		
		for(Option option:options){
			option.setSelected(userAnswers.contains(option.getValue()));
		}
	}
	/**
	 * 保存用户选择的答案
	 * @return
	 */
    public ArrayList<Integer> getUserAnswers(){
    	ArrayList<Integer> userAnswers = new ArrayList<Integer>();
    	for(Option option:options){
    		if(option.isSelected())
    			userAnswers.add(option.getValue());
    	}
    	return userAnswers;
    }
    public void updateTimer(long sub){
    	int h = (int)(sub / 1000 / 60 / 60);
    	int m = (int)(sub/1000/60%60);
    	int s = (int)(sub/1000 % 60);
    	timerLabel.setText(h+":"+m+":"+s);
    }
}
