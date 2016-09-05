package org.wyzc.elt.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

import org.wyzc.elt.dao.EntityContext;
import org.wyzc.elt.entity.Request;
import org.wyzc.elt.entity.Response;
import org.wyzc.elt.service.ExamService;
import org.wyzc.elt.service.ExamServiceImpl;
import org.wyzc.elt.util.ReadUtil;

public class MainServer {
	public static void main(String[] args) {
		MainServer mainServer = new MainServer();
		EntityContext entityContext = new EntityContext();
		mainServer.setEntityContext(entityContext);
		mainServer.startServer();
	}

	private HashMap<String, ExamServiceImpl> services = new HashMap<String, ExamServiceImpl>();
    private EntityContext entityContext;
    public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}
	public void startServer() {
		try {
			int port = Integer.parseInt(ReadUtil.getMessage("port"));
			ServerSocket s = new ServerSocket(port);
			System.out.println("开启了服务等待客户端访问");
			while (true) {
				Socket socket = s.accept();
				new ExamThread(socket).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ExamThread extends Thread {
		private Socket socket;

		public ExamThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// 1.读取请求
			Request request = getRequest(socket);
			/*
			 * 2.创建业务对象---》对于同一个用户每个业务对象只要创建一次 当做完登陆之后再做其它操作服务器都需要认识是这个用户
			 * 第一登陆成功时候响应给客户端一个标识 下次再方法请求 时把这个标识也随着请求发送过来（请求中要封装标识) 服务器进行判断
			 * 
			 * 业务操作当创建业务之后，放入容器Map容器 表识做为key, 业务对象做为value也使得业务对象 对于同一个用户只创建一次
			 */
			ExamServiceImpl service = getService(request);

			Response response = new Response();
			response.setSessionId(request.getSessionId());
			
			// 3.调用业务方法
			try {
				Object obj = invokeMethod(service, request);
				response.setObj(obj);
			} catch (Exception e) {
				response.setException(new RuntimeException(e.getMessage()));
			}finally{
				try {
					response.setSuccess();
					ObjectOutputStream oos = new ObjectOutputStream(
							socket.getOutputStream());
					//4.写回响应
					oos.writeObject(response);
					oos.flush();
				
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}

		/**
		 * 获取请求数据
		 * 
		 * @param socket
		 * @return
		 */
		private Request getRequest(Socket socket) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				Request request = (Request) ois.readObject();
				
				return request;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * 
		 * @param sessionId
		 *            服务器端响应给客户端的唯一标识
		 * @return
		 */
		private ExamServiceImpl getService(Request request) {
			String sessionId = request.getSessionId();
			if (sessionId == null) {
				// 没有登录过
				sessionId = UUID.randomUUID().toString();
				request.setSessionId(sessionId);
				ExamServiceImpl service = new ExamServiceImpl();
				service.setEntityContext(entityContext);
				services.put(sessionId, service);
			}
			return services.get(sessionId);
		}

		private Object invokeMethod(ExamServiceImpl service, Request request)
				throws Exception {
			Class c = service.getClass();
			Object obj = null;
			try {
				//获取业务方法
				Method m = c.getMethod(request.getMethodName(),
						request.getParamTypes());
				//调用业务方法
				obj = m.invoke(service, request.getObj());
				return obj;
			} catch (InvocationTargetException e) {
				//业务方法调用差生的异常
				throw new Exception(e.getTargetException().getMessage());
			} catch (Exception e) {
				throw e;
			}

		}
	}
}
