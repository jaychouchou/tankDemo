package org.wyzc.elt.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.wyzc.elt.entity.Question;
import org.wyzc.elt.entity.User;
import org.wyzc.elt.util.IOUtil;
import org.wyzc.elt.util.ReadUtil;

public class EntityContext {
	// 用于存放用户信息
	private HashMap<String, User> users = new HashMap<String, User>();
	/*
	 * 存放题库的集合---》所有的题以等级为key,ArrayList为value ArrayList中存放的是同一个等级的题目
	 */
	private HashMap<Integer, ArrayList<Question>> questions = new HashMap<Integer, ArrayList<Question>>();

	public EntityContext() {
		try {
			loadUser();
			loadQuestion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadUser() throws IOException {
		BufferedReader br = IOUtil.getReader(ReadUtil.getMessage("userpath"));
		String str = null;
		while ((str = br.readLine()) != null) {
			if (str.startsWith("#"))
				continue;
			String[] usermessage = str.split(":");
			User user = new User(usermessage[0], usermessage[1],
					usermessage[2], usermessage[3]);
			users.put(user.getId(), user);
		}
		IOUtil.close();
	}

	private void loadQuestion()throws IOException{
		BufferedReader br =IOUtil.getReader(ReadUtil.getMessage("questionpath"));
		String row = null;
		while((row = br.readLine())!=null){
			Question question = parseQuestion(br, row);
			putQuestion(question);
		}

	}
	/**
	 * 把题库中的题目等级做为key   题目的集合做为value放入Map
	 * @param question
	 */
    private void putQuestion(Question question){
    	int level = question.getLevel();
    	if(questions.containsKey(level)){
    		questions.get(level).add(question);
    	}else{
    		ArrayList<Question> levelQuestions = new ArrayList<Question>();
    		levelQuestions.add(question);
    		questions.put(level, levelQuestions);
    	}
    }
    /**
     * 解析一个题目，并封装成question对象
     * @param br
     * @param row
     * @return
     * @throws IOException
     */
	private Question parseQuestion(BufferedReader br, String row)
			throws IOException {
		Question question = new Question();
		String regex = "[@,][a-z]+=";
		String[] ss = row.split(regex);
		question.setScore(Integer.parseInt(ss[2]));
		question.setLevel(Integer.parseInt(ss[3]));
		question.setAnswers(getAnswers(ss[1]));
		String title = br.readLine();
		question.setTitle(title);
		question.setOptions(getOptions(br));
		return question;
	}
	/**
	 * 解析题目的选项
	 * @param br
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> getOptions(BufferedReader br)throws IOException{
		ArrayList<String> options = new ArrayList<String>();
		options.add(br.readLine());
		options.add(br.readLine());
		options.add(br.readLine());
		options.add(br.readLine());
		return options;
	}
	/**
	 * 解析题目的答案
	 * @param ans
	 * @return
	 */
	private ArrayList<Integer> getAnswers(String ans){
		String[] answers= ans.split("/");
		ArrayList<Integer> as = new ArrayList<Integer>();
		for(String answer:answers)
			as.add(Integer.parseInt(answer));
		return as;
	}
	/**
	 * 返回题库
	 * @return
	 */
	public HashMap<Integer, ArrayList<Question>> getQuestions() {
		return questions;
	}
	
    /**
     * 返回用户信息
     * @return
     */
	public HashMap<String, User> getUsers() {
		return users;
	}

}
