package org.wyzc.elt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.wyzc.elt.dao.EntityContext;
import org.wyzc.elt.entity.ExamInfo;
import org.wyzc.elt.entity.Question;
import org.wyzc.elt.entity.QuestionInfo;
import org.wyzc.elt.entity.User;
import org.wyzc.elt.exception.IdPassException;
import org.wyzc.elt.util.ReadUtil;

public class ExamServiceImpl implements ExamService {
	private EntityContext entityContext;
	private ArrayList<QuestionInfo> examQuestions;//考试的题目信息

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	@Override
	public User logon(String id, String pass) throws IdPassException {
		HashMap<String, User> users = entityContext.getUsers();
		/*
		 * if(!users.containsKey(id)) throw new IdPassException("用户不存在!");
		 * if(!users.get(id).getPass().equals(pass)) throw new
		 * IdPassException("密码不存在!");
		 * 
		 * User user = users.get(id); return user;
		 */
		User user = users.get(id);
		if (user == null) {
			throw new IdPassException("用户不存在!");
		}
		if (!user.getPass().equals(pass)) {
			throw new IdPassException("密码不正确!");
		}
		return user;
	}

	@Override
	public ExamInfo start() {
		// 获取考试题目
		getExamQuestions();

		ExamInfo examInfo = new ExamInfo();
		examInfo.setTitle(ReadUtil.getMessage("examtitle"));
		examInfo.setTimerLimit(Integer.parseInt(ReadUtil
				.getMessage("timerLimit")));
		examInfo.setTotalNumbers(this.examQuestions.size());

		return examInfo;
	}

	@Override
	public ArrayList<QuestionInfo> getExamQuestions() {
		int index = 0;
		Random random = new Random();
		// 一次考试的所有试题
		examQuestions = new ArrayList<QuestionInfo>();
		// 获得题库信息
		HashMap<Integer, ArrayList<Question>> questions = entityContext
				.getQuestions();
		for (int key = 1; key <= 10; key++) {
			// 这个等级所有的题目
			ArrayList<Question> levelQuestions = 
					new ArrayList<Question>(questions.get(key));
			Question question1 = levelQuestions.remove(random
					.nextInt(levelQuestions.size()));
			examQuestions.add(new QuestionInfo(question1, index++));
			Question question2 = levelQuestions.remove(random
					.nextInt(levelQuestions.size()));
			examQuestions.add(new QuestionInfo(question2, index++));
		}
		return examQuestions;
	}

	@Override
	public QuestionInfo getQuestionInfo(int pageIndex) {
		QuestionInfo questionInfo = this.examQuestions.get(pageIndex);
		return questionInfo;
	
	}

	@Override
	public int getTotalScore() {
		int totalScore = 0;
		for(QuestionInfo questionInfo : examQuestions){
			//获取标准答案
			ArrayList<Integer> answers = questionInfo.getQuestion().getAnswers();
			/*for (Integer integer : answers) {
				System.out.print(integer+",");
			}
			System.out.print("用户选择的答案:");*/
			//获取用户的答案
			ArrayList<Integer> userAnswers = questionInfo.getUserAnswers();
			/*for (Integer integer : userAnswers) {
				System.out.print(integer+",");
			}
			System.out.println();*/
			if(answers.equals(userAnswers))
				totalScore += questionInfo.getQuestion().getScore();	
		}
		return totalScore;
	}

	@Override
	public void saveUserAnswers(int pageIndex, ArrayList<Integer> userAnswers) {
		QuestionInfo questionInfo = this.examQuestions.get(pageIndex);
		questionInfo.getUserAnswers().clear();
		questionInfo.getUserAnswers().addAll(userAnswers);
		
	}
	
}
