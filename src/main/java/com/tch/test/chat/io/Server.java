package com.tch.test.chat.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

	private static AtomicInteger counter = new AtomicInteger(0);
	private static Map<Integer, PrintWriter> clients = new ConcurrentHashMap<>();
	private static ExecutorService pool = Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws Throwable {
		ServerSocket serverSocket = new ServerSocket(8888);
		try {
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("a client connected ...");
				pool.execute(new ClientSocketHandler(socket));
			}
		} finally{
			serverSocket.close();
		}
	}
	
	private static class ClientSocketHandler implements Runnable{
		private Socket socket;
		private Integer id;
		public ClientSocketHandler(Socket socket){
			this.socket = socket;
			id = counter.incrementAndGet();
		}
		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				clients.put(id, out);
				System.out.println("client num:" + clients.size());
				String msg = null;
				while((msg = in.readLine()) != null){
					System.out.println("receive msg '" + msg + "' from client-" + id);
					sendBack2Client("cllient-" + id + " said : " + msg);
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void sendBack2Client(String msg){
		for(PrintWriter client : clients.values()){
			client.println(msg);
			client.flush();
		}
	}
	
}
