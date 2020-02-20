package com.huajiang.example.entity;

import java.io.Serializable;


/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.entity
 * @date 2020/2/9 下午9:40
 * @Copyright
 */
public class EqualExample implements Serializable {

    private int x;
    private int y;
    private int z;
    public EqualExample(int x,int y,int z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * 重写Object类的equals 方法
     *
     检查是否为同一个对象的引用，如果是直接返回 true；
     检查是否是同一个类型，如果不是，直接返回 false；
     将 Object 对象进行转型；
     判断每个关键域是否相等。

     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualExample that = (EqualExample) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    /**
     * 重写equals方法总应该重写此方法
     * @return
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
