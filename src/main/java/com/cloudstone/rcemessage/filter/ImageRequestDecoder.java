package com.cloudstone.rcemessage.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ImageRequestDecoder implements ProtocolDecoder {

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		if (in.remaining()>12) {
			
			ImageRequest request = new ImageRequest(in.getInt(), in.getInt(), in.getInt());
			out.write(request);
			
		} else {
			throw new ProtocolDecoderException("request buffer length error");
		}
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {

	}

	public void dispose(IoSession session) throws Exception {

	}
}
