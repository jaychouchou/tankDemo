package org.wyzc.elt.test;

import java.util.ArrayList;

import org.wyzc.elt.dao.EntityContext;
import org.wyzc.elt.entity.QuestionInfo;
import org.wyzc.elt.service.ExamServiceImpl;

public class ExamServiceTest {
	public static void main(String[] args) {
		ExamServiceImpl examService = new ExamServiceImpl();
		EntityContext entityContext = new EntityContext();
		examService.setEntityContext(entityContext);
		/*try {
			User user = examService.logon("10001", "1456");
			System.out.println(user.getName()+","+user.getEmail());
		} catch (IdPassException e) {
			System.out.println(e.getMessage());
		}*/
		ArrayList<QuestionInfo> examQuestions = examService.getExamQuestions();
		for (QuestionInfo questioninfo : examQuestions) {
			System.out.println(questioninfo.getPageIndex()+","+questioninfo.getQuestion());
		}
	}

}
