package com.cloudstone.rcemessage.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ImageCodecFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	public ImageCodecFactory(boolean client) {
		if (client) {
			encoder = new ImageRequestEcoder();
			decoder = new ImageResponseDecoder();
		} else {
			encoder = new ImageResponseEcoder();
			decoder = new ImageRequestDecoder();
		}
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
}	
