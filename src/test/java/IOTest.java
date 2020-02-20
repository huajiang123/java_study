import com.huajiang.example.entity.UserEntity;
import com.huajiang.example.java_io.IOExample;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jianghua
 * @version v1.0
 * @package PACKAGE_NAME
 * @date 2020/2/12 上午9:53
 * @Copyright
 */
public class IOTest {

    @Test
    public void test_01() throws IOException {
        IOExample example = new IOExample();
        String src = "/usr/myjavaprojects/java_study/src/main/resources/test_io/A.txt";
        String dist = "/usr/myjavaprojects/java_study/src/main/resources/test_io/B.txt";
        example.copyFile(src,dist);
    }

    /**
     * 带缓冲功能的FileInputStream,FileOutputStream
     * 1.根据一个地址，开启一个输入输出流
     * 2.根据业务需求选择合适的装饰器
     * 3.开辟一个字节数组，实现输入输出同步
     * 4.输入出完成，关闭输入输出流
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        String src = "/usr/myjavaprojects/java_study/src/main/resources/test_io/A.txt";
        String dist = "/usr/myjavaprojects/java_study/src/main/resources/test_io/B.txt";
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dist);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        byte [] bytes = new byte[20 * 1024];
        int cnt;
        while ((cnt=bufferedInputStream.read(bytes,0,bytes.length))!=-1){
            bufferedOutputStream.write(bytes,0,cnt);
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }

    @Test
    public void test_03() throws IOException {
        String src = "/usr/myjavaprojects/java_study/src/main/resources/test_io/A.txt";
        String dist = "/usr/myjavaprojects/java_study/src/main/resources/test_io/B.txt";
        FileReader fileReader = new FileReader(src);
        FileWriter fileWriter = new FileWriter(dist);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String line;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
            //写入之后全部都写到了一行内
            bufferedWriter.write(line);
            //到下一行
            bufferedWriter.newLine();
        }
        // 装饰者模式使得 BufferedReader 组合了一个 Reader 对象
        // 在调用 BufferedReader 的 close() 方法时会去调用 Reader 的 close() 方法
        // 因此只要一个 close() 调用即可
        bufferedReader.close();
        //不关闭，不能写入成功。
        bufferedWriter.close();
    }


    /**
     * test Serializable
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void test_04() throws IOException, ClassNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("aaa");
        userEntity.setName("username");
        userEntity.setPwd("pwd");
        String dist = "test_serializable";
        FileOutputStream fileOutputStream = new FileOutputStream(dist);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(userEntity);
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(dist);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        UserEntity user =(UserEntity) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(user);
    }

    /**
     * 1.获取输入输出字节流
     * 2.获取字节流对应的通道
     * 3.获取一个缓冲区
     * 4.进行输入输出操作
     * 5.清空缓冲区
     * test NIO
     * @throws IOException
     */
    @Test
    public void test_05() throws IOException {
        String src = "/usr/myjavaprojects/java_study/src/main/resources/test_io/A.txt";
        String dist = "/usr/myjavaprojects/java_study/src/main/resources/test_io/B.txt";
        /*获得源文件字节输入流*/
        FileInputStream fins = new FileInputStream(src);
        /*获得源文件字节输入流的文件通道*/
        FileChannel fileChannel = fins.getChannel();
        /*获得输出文件的字节输出流*/
        FileOutputStream fous = new FileOutputStream(dist);
        /*获得输出文件字节流的文件通道*/
        FileChannel fcout = fous.getChannel();
        /*为缓冲区分配1024个字节*/
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true){
            /*从输入通道中读取数据到缓冲区*/
            int r = fileChannel.read(buffer);
            if (r==-1){
                break;
            }
            /*切换读写*/
            buffer.flip();
            /*把缓冲区中的内容写到输出文件中*/
            fcout.write(buffer);
            /*清空缓冲区*/
            buffer.clear();
        }
    }
}
