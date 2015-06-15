package com.cloudstone.rcemessage.filter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ImageResponseDecoder implements ProtocolDecoder {

	private static final String DECODER_STATE_KEY = ImageRequestDecoder.class.getName()+".STATE";
	private static final int MAX_SIZE = 5 *1024 * 1024;
	private class DecodeState {
		BufferedImage image1;
	}
	
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		DecodeState state = (DecodeState) session.getAttribute(DECODER_STATE_KEY);
		if (state==null) {
			state = new DecodeState();
			session.setAttribute(DECODER_STATE_KEY, state);
		}
		if (state.image1==null) {
			if (in.prefixedDataAvailable(4, MAX_SIZE) ) {
				state.image1 = getImage(in);
			} else {
				throw new ProtocolDecoderException("error prefixed data available");
			}
		}
		if (state.image1!=null) {
			if (in.prefixedDataAvailable(4, MAX_SIZE)) {
				BufferedImage image = getImage(in);
				ImageResponse response = new ImageResponse(state.image1, image);
				out.write(response);
				state.image1 = null;
			} else {
				throw new ProtocolDecoderException("error prefixed data available");
			}
		}
	}
	
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {

	}

	public void dispose(IoSession session) throws Exception {
		
	}
	
	
	private BufferedImage getImage(IoBuffer buffer) throws IOException {
		int length = buffer.getInt();
		byte [] b = new byte[length];
		buffer.get(b);
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return ImageIO.read(bais);
	}
}
