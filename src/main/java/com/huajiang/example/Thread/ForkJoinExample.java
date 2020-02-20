package com.huajiang.example.Thread;

import java.util.concurrent.RecursiveTask;

/**
 * forkjoin并行计算实例
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 下午4:39
 * @Copyright
 */
public class ForkJoinExample extends RecursiveTask<Integer> {
    private final int threshold = 5;
    private int first;
    private int last;

    public ForkJoinExample(int first,int last){
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <=threshold){
            for (int i = first; i <= last; i++) {
                result += i;
            }
        }else {
            int middle = first + (first-last)/2;
            ForkJoinExample leftfork = new ForkJoinExample(first,middle);
            ForkJoinExample rightfork = new ForkJoinExample(middle+1,last);
            leftfork.fork();
            rightfork.fork();
            result = leftfork.join()+rightfork.join();
        }
        return result;
    }
}
