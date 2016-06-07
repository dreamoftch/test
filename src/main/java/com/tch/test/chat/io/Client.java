package com.tch.test.chat.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Throwable {
		Socket socket = new Socket("localhost", 8889);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			new Thread(new ServerResponseHandler(in)).start();
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while((msg = userInput.readLine()) != null){
				out.println(msg);
				out.flush();
			}
		} finally{
			socket.close();
		}
	}
	
	private static class ServerResponseHandler implements Runnable{
		BufferedReader in;
		public ServerResponseHandler(BufferedReader in){
			this.in = in;
		}
		@Override
		public void run() {
			try {
				String msg = null;
				while((msg = in.readLine()) != null){
					System.out.println(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
