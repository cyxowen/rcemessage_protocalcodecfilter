package com.cloudstone.rcemessage.Demo_ProtocalCodecFilter;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.cloudstone.rcemessage.filter.ImageCodecFactory;
import com.cloudstone.rcemessage.filter.ImageRequest;
import com.cloudstone.rcemessage.filter.ImageResponse;

public class client extends JFrame{
	static IoSession session;
	private static JFrame mFrame;
	private static JPanel mPanel;
	private static JButton mButton;
	private static ImageIcon mImage;
	private  void init() {
		mFrame = new JFrame();
		mFrame.setLayout(new GridLayout());
		
		mFrame.setSize(200, 200);
		
		mPanel = new JPanel();
		mButton = new JButton("发送");
		mPanel.add(mButton);
		
		mImage = new ImageIcon();
		mButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("单机了");
				ImageRequest request = new ImageRequest(100, 100, 4);
				session.write(request);
			}
		});
		mFrame.add(mPanel);
		mFrame.setVisible(true);
	}
	public static void main(String[] args) {
		IoConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ImageCodecFactory(true)));
		//connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
		connector.setHandler(new MyIoHandler());
		
		
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress("192.168.0.10", 2910));
				future.awaitUninterruptibly();
				session = future.getSession();
				//session.write("hello");
				
				break;
			} catch (Exception e) {
				System.out.println("connect error " + e.getMessage());
			}
		}
		
		new client().init();
		
	}

	static class MyIoHandler extends IoHandlerAdapter {

		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			//System.out.println(message.toString());
			ImageResponse response = (ImageResponse) message;
			BufferedImage image1 = response.getImage1();
			
			mImage.setImage(image1);
			
			JLabel label = new JLabel(mImage);
			mFrame.add(label);
			System.out.println("image size of width " + image1.getWidth() + " and height " + image1.getHeight());
		}
	}
}
