package org.wyzc.elt.control;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.wyzc.elt.entity.ExamInfo;
import org.wyzc.elt.entity.QuestionInfo;
import org.wyzc.elt.entity.User;
import org.wyzc.elt.exception.IdPassException;
import org.wyzc.elt.service.ExamService;
import org.wyzc.elt.view.ExamFrame;
import org.wyzc.elt.view.LoginFrame;
import org.wyzc.elt.view.MenuFrame;

public class ClientContext {
	private LoginFrame loginFrame;
	private MenuFrame menuFrame;
	private ExamFrame examFrame;
	private ExamService examService;
	private QuestionInfo currentQuestionInfo;
	private ExamInfo examInfo;
	private User user;// 当前登陆用户
	private int totalScore;
	private boolean isOver;
	private Timer timer;

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public void setMenuFrame(MenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	public void setExamFrame(ExamFrame examFrame) {
		this.examFrame = examFrame;
	}

	public void exit() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
				"确定要退出吗?")) {
			System.exit(0);
		}
	}

	public void logon() throws IdPassException {
		String id = loginFrame.getId();
		if (id == null || "".equals(id))
			throw new IdPassException("编号不能为空!");
		String pass = loginFrame.getPassword();
		if (pass == null || "".equals(pass))
			throw new IdPassException("密码不能为空!");
		user = examService.logon(id, pass);
		loginFrame.setVisible(false);
		menuFrame.updateMenuFrame();
		menuFrame.setVisible(true);
	}

	public User getUser() {
		return user;
	}

	// 开始考试的方法
	public void start() {
		if (isOver) {
			JOptionPane.showMessageDialog(examFrame, "您已经参加完考试");
			return;
		}
		// 显示考试的页面
		// 1.准备页面上标签的数据
		examInfo = examService.start();
		examInfo.setUser(user);
		// 2.准备题目数据
		this.currentQuestionInfo = examService.getQuestionInfo(0);
		examFrame.updateView(examInfo, currentQuestionInfo);
		menuFrame.setVisible(false);
		examFrame.setVisible(true);
		startTime();
	}

	public void startTime() {
		// 考试结束时间
		final long end = System.currentTimeMillis() + examInfo.getTimerLimit()
				* 60 * 1000;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long sub = end - System.currentTimeMillis();
				if (sub <= 0) {
					over();
					return;
				}
				examFrame.updateTimer(sub);
			}
		}, 0, 1000);
	}

	public void next() {
		int index = this.currentQuestionInfo.getPageIndex();
		// 保存用户选择的答案
		ArrayList<Integer> userAnswers = examFrame.getUserAnswers();
		// this.currentQuestionInfo.setUserAnswers(userAnswers);
		examService.saveUserAnswers(index, userAnswers);
		// 获取新的页
		int pageIndex = index + 1;
		QuestionInfo questionInfo = this.examService.getQuestionInfo(pageIndex);
		examFrame.updateView(examInfo, questionInfo);
		this.currentQuestionInfo = questionInfo;
	}

	public void pre() {
		int index = this.currentQuestionInfo.getPageIndex();
		// 保存用户选择的答案
		ArrayList<Integer> userAnswers = examFrame.getUserAnswers();
		// this.currentQuestionInfo.setUserAnswers(userAnswers);
		examService.saveUserAnswers(index, userAnswers);
		int pageIndex = this.currentQuestionInfo.getPageIndex() - 1;
		QuestionInfo questionInfo = this.examService.getQuestionInfo(pageIndex);
		examFrame.updateView(examInfo, questionInfo);
		this.currentQuestionInfo = questionInfo;
	}

	public void send() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(examFrame,
				"您确定要交卷吗?")) {

			over();
		} else
			return;
	}

	private void over() {
		isOver = true;
		timer.cancel();
		// 把点交卷按钮时所在的页面当前的题的答案保留下来
		ArrayList<Integer> userAnswers = this.examFrame.getUserAnswers();
		// this.currentQuestionInfo.setUserAnswers(userAnswers);
		examService.saveUserAnswers(this.currentQuestionInfo.getPageIndex(),
				userAnswers);
		totalScore = this.examService.getTotalScore();
		JOptionPane.showMessageDialog(examFrame, totalScore);
		examFrame.setVisible(false);
		menuFrame.setVisible(true);

	}

	public void getResult() {
		if (isOver)
			JOptionPane.showMessageDialog(menuFrame, "您考试的成绩是:" + totalScore);
		else
			JOptionPane.showMessageDialog(menuFrame, "请您先参加考试!");
	}
}
