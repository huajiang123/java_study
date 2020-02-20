package com.huajiang.example.Thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用jdk实现的reentrantlock
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 下午9:09
 * @Copyright
 */

public class LockExample {

    private Lock lock = new ReentrantLock();

    public void func(){
        lock.lock();
        try {
            for (int i=0;i<20;i++){
                System.out.print(i+ " ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        finally {
            lock.unlock(); //避免死锁
        }
    }
}
