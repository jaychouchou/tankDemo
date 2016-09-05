package org.wyzc.elt.test;

import org.wyzc.elt.control.ClientContext;
import org.wyzc.elt.dao.EntityContext;
import org.wyzc.elt.net.ExamServiceProxy;
import org.wyzc.elt.service.ExamServiceImpl;
import org.wyzc.elt.view.ExamFrame;
import org.wyzc.elt.view.LoginFrame;
import org.wyzc.elt.view.MenuFrame;

public class ExamTest {
	public static void main(String[] args) {
		//视图对象
		LoginFrame loginFrame = new LoginFrame();
		MenuFrame menuFrame = new MenuFrame();
		ExamFrame examFrame = new ExamFrame();
		//控制器对象
		ClientContext clientContext = new ClientContext();
		//设置视图与控制器之间的依赖
		loginFrame.setClientContext(clientContext);
		menuFrame.setClientContext(clientContext);
		examFrame.setClientContext(clientContext);
		
	    /*clientContext.setLoginFrame(loginFrame);
		clientContext.setMenuFrame(menuFrame);
		clientContext.setExamFrame(examFrame);*/
		//创建数据层对象
		EntityContext entityContext = new EntityContext();
		//创建业务对象
		//ExamServiceImpl examService = new ExamServiceImpl();
		ExamServiceProxy examService = new ExamServiceProxy();
		//建立业务和数据层之间的依赖
		//examService.setEntityContext(entityContext);
		//创建控制器和业务之间的依赖
		clientContext.setExamService(examService);
		
		loginFrame.setVisible(true);
	}

}
