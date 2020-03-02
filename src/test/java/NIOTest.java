import com.huajiang.example.java_io.ServerConnect;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghua
 * @version v1.0
 * @package PACKAGE_NAME
 * @date 2020/2/29 下午8:56
 * @Copyright
 */
public class NIOTest {

    /**
     * 1.获取通道
     * 2.分配指定大小的buffer
     * 3.从本地读取文件，并发送到服务端
     * 4.接收反馈
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void client() throws IOException {
       SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));
       socketChannel.configureBlocking(false);
       FileChannel fileChannel = FileChannel.open(Paths.get("/usr/myjavaprojects/java_study/src/main/resources/test_io/A.txt"), StandardOpenOption.READ);
       ByteBuffer buffer =ByteBuffer.allocate(1024);
       while (fileChannel.read(buffer)!=-1){
           buffer.flip();
           socketChannel.write(buffer);
           buffer.clear();
       }

       int len=1;
       while ((len = socketChannel.read(buffer)) !=-1){
           System.out.println("========"+ len);
           buffer.flip();
           System.out.println(new String(buffer.array(),0,len));
           buffer.clear();
       }
        System.out.println("this is none-blocking socketchannel");
       fileChannel.close();
       socketChannel.close();
    }
    /**
     * 1.获取通道
     * 2.指定链接
     * 3.获取客户端的连接通道
     * 4.获取指定大小的buffer
     * 5.接收客户端的shuju，并保存到本地
     * 6.发送反馈给客户端
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        FileChannel fileChannel = FileChannel.open(Paths.get("/usr/myjavaprojects/java_study/src/main/resources/test_io/c.txt"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//选择器对通道的监听事件，需要第二个参数ops指定
        while (socketChannel.read(buffer) !=-1){   //read函数是一个阻塞函数如果客户端没有声明断开连接那么它会认为客户端仍旧可能会发送数据，类似的还有： readLine()、DataInputStream种的readUTF()等。
            buffer.flip();
            fileChannel.write(buffer);
            System.out.println(new String(buffer.array()));
            buffer.clear();
        }
        System.out.println("接收数据成功");
        buffer.put("接收数据成功".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        fileChannel.close();
        serverSocketChannel.close();
        socketChannel.close();
    }
    @Test
    public void server_02() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        FileChannel fileChannel = FileChannel.open(Paths.get("/usr/myjavaprojects/java_study/src/main/resources/test_io/c.txt"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//选择器对通道的监听事件，需要第二个参数ops指定
        while (selector.select() > 0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()){
                    SocketChannel socketChannel =serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    System.out.println(selector.keys());
                }
                else if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(buf)) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }
                }
                iterator.remove();
            }
        }
        System.out.println("接收数据成功");
        /*buffer.put("接收数据成功".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        fileChannel.close();
        serverSocketChannel.close();
        socketChannel.close();*/
        serverSocketChannel.close();
    }

    @Test
    public void test_01() throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(8080));
        if (socketChannel.finishConnect()){
            int i=0;
            while (true){

                String info = "I am "+i+++"-th information from client1";
                buffer.clear();
                buffer.put(info.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println(buffer);
                    socketChannel.write(buffer);
                }
                TimeUnit.SECONDS.sleep(30);
            }
        }
        if (socketChannel != null){
            socketChannel.close();
        }

    }

    @Test
    public void test_01_1() throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(8080));
        if (socketChannel.finishConnect()){
            int i=0;
            while (true){

                String info = "I am "+i+++"-th information from client2";
                buffer.clear();
                buffer.put(info.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println(buffer);
                    socketChannel.write(buffer);
                }
                TimeUnit.SECONDS.sleep(30);
            }
        }
        if (socketChannel != null){
            socketChannel.close();
        }

    }

    @Test
    public void test_02() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        int recvMsgSize = 0;
        byte [] recvBuf = new byte[1024];
        while (true){
            Socket socket = serverSocket.accept();
            SocketAddress address = socket.getRemoteSocketAddress();
            System.out.println("Handling client at" + address);
            InputStream in = socket.getInputStream();
            while ((recvMsgSize=in.read(recvBuf))!=-1){
                byte [] temp = new byte[recvMsgSize];
                System.arraycopy(recvBuf,0,temp,0,recvMsgSize);
                System.out.println(new String(temp));
            }
        }

    }

    @Test
    public void test_03(){
        ServerConnect.selector();
    }
}
