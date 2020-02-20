package com.huajiang.example.Thread;

/**
 * join()函数例子
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 下午9:43
 * @Copyright
 */
public class JoinExample {

    private class A extends Thread{
        @Override
        public void run() {
            System.out.println("this is A");
        }
    }

    private class B extends Thread{

        private A a;

        B(A a){
            this.a = a;
        }
        @Override
        public void run() {
            try {
                a.join(); //运行A，当前线程会等待A结束在继续执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("this is B");
        }
    }

    public void test(){
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();
    }
}
