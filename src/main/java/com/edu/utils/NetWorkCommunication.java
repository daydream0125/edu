/**
 * 
 */
package com.edu.utils;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author koala
 *
 */
public final class NetWorkCommunication {
	public boolean notifyJudge(String type)
	{
		 try{
		     //创建Socket实例"210.45.212.96",4629
		          Socket socket = new Socket();
		          String ip="210.45.212.96";
		          int port=4629;
		          socket.connect(new InetSocketAddress(ip, port), 3000);
		      //获取输出流
		          OutputStream outputStream = socket.getOutputStream();
		          System.out.println(type.length());
		         outputStream.write(type.getBytes());
		        //释放资源
		        outputStream.close();
		        socket.close();
		        return true;
		         }catch(Exception e){
		           e.printStackTrace();
		           return false;
		         }
		}
}
