/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.tch.test.chat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles a server-side channel.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

	private static AtomicInteger counter = new AtomicInteger(0);
	private static Map<Channel, Integer> channels = new ConcurrentHashMap<Channel, Integer>();

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws UnknownHostException {
    	Integer channelNum = counter.incrementAndGet();
    	channels.put(ctx.channel(), channelNum);
    	ctx.writeAndFlush("Hello user-" + channelNum + "\r\n");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Send the received message to all channels but the current one.
        for (Map.Entry<Channel, Integer> entry : channels.entrySet()) {
        	Channel c = entry.getKey();
            if (c != ctx.channel()) {
                c.writeAndFlush("user-" + channels.get(ctx.channel()) + " said: " + msg + '\n');
            } else {
                c.writeAndFlush("[you] said: " + msg + '\n');
            }
        }

        // Close the connection if the client has sent 'bye'.
        if ("bye".equals(msg.toLowerCase())) {
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
