package com.cloudstone.rcemessage.Demo_ProtocalCodecFilter;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.cloudstone.rcemessage.filter.ImageCodecFactory;
import com.cloudstone.rcemessage.filter.ImageIoHandler;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception{
    	
    	IoAcceptor accepter = new NioSocketAcceptor();
    	accepter.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new ImageCodecFactory(false)));
    	//accepter.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
    	accepter.setHandler(new ImageIoHandler());
//    	accepter.setHandler(new IoHandlerAdapter(){
//    		@Override
//    		public void messageReceived(IoSession session, Object message)
//    				throws Exception {
//    			System.out.println(message.toString());
//    			session.write("client hello !!");
//    		}
//    	});
    	accepter.bind(new InetSocketAddress(2910));
    	System.out.println("server is running and bind at 2910 port.");
    }
}
