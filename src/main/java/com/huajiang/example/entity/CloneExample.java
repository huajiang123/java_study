package com.huajiang.example.entity;

import java.io.Serializable;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.entity
 * @date 2020/2/9 下午10:15
 * @Copyright
 */
//Object类clone()
public class CloneExample implements Serializable,Cloneable{
    private int a;
    private int b;

    /**
     * 必须覆盖object的clone接口，clone() 是 Object 的 protected 方法，它不是 public，
     * 一个类不显式去重写 clone()，其它类就不能直接去调用该类实例的 clone() 方法
     * 父类的protected成员是包内可见的，并且对子类可见；
     * 若子类与父类不在同一包中，那么在子类中，子类实例可以访问其从父类继承而来的protected方法，
     * 而不能访问父类实例的protected方法
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public CloneExample clone() throws CloneNotSupportedException{
        return (CloneExample) super.clone();
    }
}
