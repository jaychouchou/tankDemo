package org.wyzc.elt.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.wyzc.elt.control.ClientContext;

public class MenuFrame extends JFrame {
	private JLabel userLabel;

	/*public static void main(String[] args) {
		new MenuFrame().setVisible(true);
	}*/

	private ClientContext clientContext;

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
		this.clientContext.setMenuFrame(this);
	}

	public MenuFrame() {
		this.setTitle("我赢在线测评系统");
		this.setSize(600, 380);
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
		// 获取当前类所在包下的路径作为url
		URL url = this.getClass().getResource("title.png");
		// 创建图片对象
		ImageIcon icon = new ImageIcon(url);
		JLabel label = new JLabel(icon);
		panel.add(BorderLayout.NORTH, label);
		panel.add(createCenterPanel());
		panel.add(BorderLayout.SOUTH, new JLabel("沃赢科技.版权所有!盗版必究!",
				JLabel.RIGHT));
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		userLabel = new JLabel("欢迎xxx来到考试系统", JLabel.CENTER);
		panel.add(BorderLayout.NORTH, userLabel);
		panel.add(BorderLayout.CENTER, createBtnPanel());
		return panel;
	}

	private JPanel createBtnPanel() {
		JPanel panel = new JPanel();
		JButton startButton = createBtn("开始", "exam.png");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.start();
			}
		});
		panel.add(startButton);
		JButton resultButton = createBtn("分数", "result.png");
		resultButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			   clientContext.getResult();
			}
		});
		panel.add(resultButton);
		panel.add(createBtn("规则", "message.png"));
		JButton exit = createBtn("离开", "exit.png");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clientContext.exit();
			}
		});
		panel.add(exit);
		return panel;
	}

	private JButton createBtn(String name, String imageName) {
		URL url = this.getClass().getResource(imageName);
		JButton button = new JButton(name, new ImageIcon(url));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		return button;
	}

	public void updateMenuFrame() {
		userLabel.setText("欢迎" + clientContext.getUser().getName() + "来到考试系统");
		//设置颜色
		userLabel.setForeground(Color.red);
	}
}
