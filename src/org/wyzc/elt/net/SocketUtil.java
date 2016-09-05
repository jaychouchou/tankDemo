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
			//���ӷ�����
			Socket socket = new Socket(ReadUtil.getMessage("ip"),port);
			//���������������н��з���
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(request);
			oos.flush();
		
			//��ȡ��Ӧ
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			return (Response)ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
