#一级标题
##二级标题
###三级标题
####四级标题
#####五级标题
######六级标题
第一段，段落末尾加上两个空格或空一行表示分段  

第二段  <u>下划线</u>
***
第三段  ~~删除线~~
***
*斜体文本*  
* * *
***粗体文本***  
***

创建脚注类是这样 [^RUNOOB]  

[^RUNOOB]: life is too short to bogged down and be discouraged.So you have to keep moving and keep going.  
* 第一项
* 第二项
* 第三项  
1. first
2. second
3. third  
***
1. 第一项：
    - 第一项嵌套的第一个元素
    * 第一项嵌套的第二个元素  
2. 第二项：
    - 第二项嵌套的第一个元素
    * 第二项嵌套的第二个元素  
***
> 第一个区块  
> > 第一层嵌套  
> > > 第二层嵌套

> 第二个区块  
> 第三个区块  
***
> 区块中使用列表  
> 1. 第一项  
> 2. 第二项
> * 第一项
> * 第二项
***
* 第一项
    > 第一项第一个区块
    > 第一项第二个区块
* 第二项
***
    @Test
        public void test_11(){
            TreeMap<String,String> treeMap = new TreeMap<String, String>();
            treeMap.put("aaa","I AM AAA");
            treeMap.put("bbb","I AM BBB");
            treeMap.put("ccc","I AM CCC");
            treeMap.put("aaa","I AM A");
            treeMap.put("xxx","I AM XXX");
            treeMap.put("ddd","I AM DDD");
            treeMap.put("eee","I AM EEE");
            Set set= treeMap.keySet();
           Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }
***
这是一个链接 [百度](https://baidu.com)  
![这是一张图片](http://static.runoob.com/images/runoob-logo.png)  
<img src="http://static.runoob.com/images/runoob-logo.png" width="50%"/>  

| 表头 | 表头 |  
| ---- | ---- |  
| 单元格 | 单元格 |  
| 单元格 | 单元格 |  
***
| 左对齐 | 居中对齐 | 右对齐 |    
| :---- | :----: | ----: |
| 单元格格 | 单元格 | 单元格格 |  
| 单元格格 | 单元格 | 单元格格 |  