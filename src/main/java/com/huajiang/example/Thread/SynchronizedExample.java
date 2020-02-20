package com.huajiang.example.Thread;

/**
 * 它对应的内存间交互操作为：lock 和 unlock，在虚拟机实现上对应的字节码指令为 monitorenter 和 monitorexit。
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 下午6:25
 * @Copyright
 */

public class SynchronizedExample {

    public void func1(){
        //同步代码块
        synchronized (this){
            for (int i=0;i<20;i++){
                System.out.print(i+ " ");
            }
        }
    }

    //同步一个方法
    public synchronized void func2(){
        for (int i=0;i<20;i++){
            System.out.print(i+ " ");
        }
    }

    //同步一个类
    public void func3(){
        synchronized (SynchronizedExample.class){
            for (int i=0;i<20;i++){
                System.out.print(i+ " ");
            }
        }
    }
}
