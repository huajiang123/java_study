package com.huajiang.example.java_io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.java_io
 * @date 2020/3/1 上午9:02
 * @Copyright
 */
public class ServerConnect {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;

    public static void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = channel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(),SelectionKey.OP_READ, ByteBuffer.allocateDirect(1024));
        System.out.println("=======handleAccept=======");
    }
    public static void handleRead(SelectionKey selectionKey) throws IOException {
        System.out.println("=======handleRead=======");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        long bytesRead = socketChannel.read(buffer);
        while (bytesRead>0){
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println((char) buffer.get());
            }
            System.out.println();
            buffer.clear();
            bytesRead = socketChannel.read(buffer);
        }
        if (bytesRead == -1){
            socketChannel.close();
        }
    }
    public static void handlewrite(SelectionKey selectionKey) throws IOException {
        System.out.println("=======handlewrite=======");
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        buffer.flip();
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        while (buffer.hasRemaining()){
            socketChannel.write(buffer);
        }
        buffer.compact();
    }
    public static void selector(){
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;
        try{
            //开启selector
            selector = Selector.open();
            //打开ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            //指定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            //指定为非阻塞
            serverSocketChannel.configureBlocking(false);
            //向selector中注册serverSocketChannel
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            while (true){
                //没有可用的channel
                if (selector.select(TIMEOUT) == 0){
                    System.out.println("===");
                    continue;
                }
                //可用的channel列表
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                //遍历可用的channel列表
                while (iter.hasNext()){
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()){
                        handleAccept(key);
                    }
                    if (key.isReadable()){
                        handleRead(key);
                    }
                    if (key.isWritable() && key.isValid()){
                        handlewrite(key);
                    }
                    if (key.isConnectable()){
                        System.out.println("isConnected = true");
                    }
                    iter.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if (selector !=null){
                    selector.close();
                }
                if (serverSocketChannel !=null){
                    serverSocketChannel.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
