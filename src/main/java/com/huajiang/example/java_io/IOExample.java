package com.huajiang.example.java_io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.java_io
 * @date 2020/2/12 上午9:27
 * @Copyright
 */
public class IOExample {

    /**
     * 实现文件拷贝
     * @param src
     * @param dist
     * @throws FileNotFoundException
     */
    public void copyFile(String src,String dist) throws IOException {

        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dist);
        //20kb
        byte [] buffer = new byte[20 * 1024];
        int cnt;
        //read()从文件开头开始读，一次最多读取buffer.length个字节
        //返回的是实际读取的字节的个数
        //返回-1即读取到文件末尾
        while ((cnt = inputStream.read(buffer,0,buffer.length)) !=-1){
            System.out.println(cnt);
            outputStream.write(buffer,0,cnt);
        }
        inputStream.close();
        outputStream.close();
    }
}
