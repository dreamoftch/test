package com.tch.test.chat.nio;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NioClient extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextArea area = new JTextArea();
	private JTextField textField = new JTextField();
	private JButton button = new JButton("Send Message");
	private static Selector selector;
	
	static{
		 try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		NioClient client = new NioClient();
		client.start();
	}
	
	private void start() throws Exception{
		//��ʼ��frame����
		initFrame();
		//��ʼ��socketchannel
		initSocketChannel();
		while(true){
			int ready = selector.select();
			if(ready > 0){
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey selectionKey = iterator.next();
					if(selectionKey.isReadable()){
						//��ȡ��Ϣ
						readMsg(selectionKey);
					}
					iterator.remove();
				}
			}
		}
	}
	
	private void readMsg(SelectionKey selectionKey) throws Exception{
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
		socketChannel.read(buffer);
		buffer.flip();
		area.setText(area.getText().trim()+"\n"+new String(buffer.array(),0,buffer.limit(),"utf-8"));
		buffer.clear();
	}
	
	private void initFrame(){
		setBounds(200, 200, 300, 400);
		setLayout(new GridLayout(3, 1));
		add(area);
		add(textField);
		add(button);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initSocketChannel() throws Exception{
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 7878));
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		button.addActionListener(new MyActionListener(socketChannel));
	}
	
	private class MyActionListener implements ActionListener{
		
		private SocketChannel socketChannel;
		
		public MyActionListener(SocketChannel socketChannel){
			this.socketChannel = socketChannel;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				String message = textField.getText();
				if(message == null || message.trim().isEmpty()){
					System.out.println("empty message");
					return;
				}
				textField.setText("");
				buffer.put(message.getBytes("utf-8"));
				buffer.flip();
				while(buffer.hasRemaining()){
					socketChannel.write(buffer);
				}
				buffer.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
