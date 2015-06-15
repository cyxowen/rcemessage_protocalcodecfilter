package com.cloudstone.rcemessage.filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageIoHandler extends IoHandlerAdapter {

	private static final String INDEX_KEY = ImageIoHandler.class.getName() + ".INDEX";
	private static final String CHARACTER = "mina protocol encoder and decoder";
	public void sessionOpened(IoSession session) throws Exception {
		session.setAttribute(INDEX_KEY, Integer.valueOf(0));
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		Logger lo = LoggerFactory.getLogger(ImageIoHandler.class);
		lo.warn(cause.getMessage(), cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		ImageRequest request = (ImageRequest) message;
		ImageResponse response = new ImageResponse(createImage(session, request), createImage(session, request));
		session.write(response);
	}

	private BufferedImage createImage(IoSession session, ImageRequest request) {
		BufferedImage image = new BufferedImage(request.getWidth(), request.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.BLUE);
		graphics.fillRect(0, 0, request.getWidth(), request.getHeight());
		Font serif = new Font("serif", Font.BOLD, 20);
		graphics.setColor(Color.RED);
		graphics.setFont(serif);
		graphics.drawString(genString(session, request.getNumOfCharacters()), 50, 50);
		return image;
	}
	
	private String genString(IoSession session, int length) {
		int index = ((Integer) session.getAttribute(INDEX_KEY)).intValue();
		StringBuffer sb = new StringBuffer();
		if (sb.length()<length) {
			sb.append(CHARACTER.charAt(index));
			index++;
			if (index>=CHARACTER.length()) 
				index=0;
		}
		session.setAttribute(INDEX_KEY, Integer.valueOf(index));
		return sb.toString();
	}
}