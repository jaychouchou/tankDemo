package org.wyzc.elt.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtil {
	private static BufferedReader br;
	public static BufferedReader getReader(String filepath)throws IOException{
		br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filepath)));
		return br;
	}
	public static void close()throws IOException{
		if(br!=null)
			br.close();
	}
}
