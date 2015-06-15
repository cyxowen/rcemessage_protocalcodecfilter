package com.cloudstone.rcemessage.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ImageRequestEcoder implements ProtocolEncoder {

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		ImageRequest request = (ImageRequest) message;
		IoBuffer buffer = IoBuffer.allocate(12, true);
		buffer.putInt(request.getWidth());
		buffer.putInt(request.getHeight());
		buffer.putInt(request.getNumOfCharacters());
		buffer.flip();
		out.write(buffer);
	}

	public void dispose(IoSession session) throws Exception {
		// do release resource in here.
	}
}
