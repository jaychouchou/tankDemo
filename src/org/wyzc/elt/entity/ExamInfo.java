package org.wyzc.elt.entity;

import java.io.Serializable;

public class ExamInfo implements Serializable{
	private User user;//���Ե��û���Ϣ
	private int timerLimit;//ʱ������
	private String title;//���Կ�Ŀ
	private int totalNumbers;//������Ŀ����
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getTimerLimit() {
		return timerLimit;
	}
	public void setTimerLimit(int timerLimit) {
		this.timerLimit = timerLimit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTotalNumbers() {
		return totalNumbers;
	}
	public void setTotalNumbers(int totalNumbers) {
		this.totalNumbers = totalNumbers;
	}
    @Override
    public String toString() {
    	return "����:" + user.getName() + " ���:"
				+user.getId() + " ����ʱ��:"
				+ timerLimit + "����" + " ��Ŀ:"
				+title + " ����:"
				+ totalNumbers;
    }
	
}
