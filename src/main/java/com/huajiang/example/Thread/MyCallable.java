package com.huajiang.example.Thread;

import java.util.concurrent.Callable;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 上午11:20
 * @Copyright
 */
public class MyCallable implements Callable {
    public Object call() throws Exception {
        return "this is MyCallable";
    }
}
