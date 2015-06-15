package com.cloudstone.rcemessage.filter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ImageResponseEcoder implements ProtocolEncoder {

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		ImageResponse response = (ImageResponse) message;
		byte [] buffer1 = getBytes(response.getImage1());
		byte [] buffer2 = getBytes(response.getImage2());
		
		int capacity = buffer1.length + buffer2.length + 8;
		IoBuffer buffer = IoBuffer.allocate(capacity, false);
		buffer.setAutoExpand(true);
		buffer.putInt(buffer1.length);
		buffer.put(buffer1);
		buffer.putInt(buffer2.length);
		buffer.put(buffer2);
		buffer.flip();
		out.write(buffer);
	}

	public void dispose(IoSession session) throws Exception {
		
	}
	
	private byte [] getBytes(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", baos);
		return baos.toByteArray();
	}
}
