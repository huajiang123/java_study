package com.huajiang.example.entity;

import java.util.ArrayList;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.entity
 * @date 2020/2/10 上午8:46
 * @Copyright
 */
public class SubClass extends SuperClass {

    /**
     * 子类方法访问权限为 public，大于父类的 protected。
     * 子类的返回类型为 ArrayList，是父类返回类型 List 的子类。
     * 子类抛出的异常类型为 Exception，是父类抛出异常 Throwable 的子类。
     * 子类重写方法使用 @Override 注解，从而让编译器自动检查是否满足限制条件
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Integer> func() throws Exception{
        return new ArrayList<Integer>();
    }
}
