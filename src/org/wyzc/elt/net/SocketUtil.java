package org.wyzc.elt.net;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.wyzc.elt.entity.Request;
import org.wyzc.elt.entity.Response;
import org.wyzc.elt.util.ReadUtil;

public class SocketUtil {
	public static Response remoteAll(Request request){
		try {
			int port = Integer.parseInt(ReadUtil.getMessage("port"));
			//连接服务器
			Socket socket = new Socket(ReadUtil.getMessage("ip"),port);
			//发送请求在网络中进行发送
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(request);
			oos.flush();
		
			//读取响应
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			return (Response)ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
