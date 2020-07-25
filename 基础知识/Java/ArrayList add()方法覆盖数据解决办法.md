# ArrayList add()方法覆盖数据解决办法

在循环中使用list.add(vo)可能最后只有一个vo,之前的都被覆盖了，原因是vo的对象创建了在循环外面，只有一个对象，所以每次都会被覆盖

```
List<Retdata> li = new ArrayList<Retdata>();
Retdata data=new Retdata();

for (int i = 0; i < retList.size(); i++) {

    data.setMobile((String) jo11.get("mobile"));
    data.setSendcontent((String) jo11.get("sendcontent"));
    data.setSendDate((String) jo11.get("SendDate"));
    li.add(data);

}
```

{SendDate=2017/6/7, sendcontent=111, mobile=123456789}
1
{SendDate=2017/6/8, sendcontent=22, mobile=22222222}
2
{SendDate=2017/6/7, sendcontent=33, mobile=3333333333}
0
com.format.Retdata@26807f
1
com.format.Retdata@26807f
2
com.format.Retdata@26807f

可以看到上面list中的对象都是同一个内存地址。

解决方法：将对象在循环中创建

```
List<Retdata> li = new ArrayList<Retdata>();

for (int i = 0; i < retList.size(); i++) {
    Retdata data=new Retdata();

    data.setMobile((String) jo11.get("mobile"));
    data.setSendcontent((String) jo11.get("sendcontent"));
    data.setSendDate((String) jo11.get("SendDate"));
    li.add(data);

}
```

