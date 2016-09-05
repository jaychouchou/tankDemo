package org.wyzc.elt.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionInfo implements Serializable{
	private Question question;//��ǰҳ�Ŀ�����Ŀ
	private int pageIndex;//��ǰҳ
	private ArrayList<Integer> userAnswers = new ArrayList<Integer>();//�û�ѡ��Ĵ�
	public QuestionInfo(){};
	
	public QuestionInfo(Question question, int pageIndex) {
		super();
		this.question = question;
		this.pageIndex = pageIndex;
	}

	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public ArrayList<Integer> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(ArrayList<Integer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return question.toString();
	}
	
}
