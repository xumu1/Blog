先回答问题

### go 语言是传值，不是传引用，可以类比 java 的传值传引用的讨论

直接上代码

```
package main

import "fmt"

func main() {
	// p是一个指针
	p := new(person)
	p.age = 5
	p.name = "xm"
	fmt.Println("main ---person's address",&p)
	fmt.Println("main ---address of person's name",&p.name)
	fun2(p)
	fun3(*p)
}
func fun3(person2 person)  {
	fmt.Println("fun3 ---address of person's name",&person2.name)
}
func fun2(person2  *person)  {
	fmt.Println("fun2 ---address of person's name",&person2.name)
	fmt.Println("fun2 ---person's address",&person2)
}
type person struct {
	name string
	age int
	desc string
}
```

输出

```
main ---person's address 0xc000006028
main ---address of person's name 0xc000076330
fun2 ---address of person's name 0xc000076330
fun2 ---person's address 0xc000006038
fun3 ---address of person's name 0xc000076360
```

根据 person 的地址和 person.name 的地址，可以看出

1.  fun2 形参是实参的复制，并不是同一个指针，和实参指向的是同一个对象。
2.  fun2 形参是实参的复制，是 copy 出来的另一个对象，地址是不一样的。

经验：编写代码时，多使用 fun2 传指针的方式，不用复制整个对象，提升效率。

### 尾巴

三种对象创建方式

1.

```
p1 = person{"xm",12,"desc"}
```

2.

```
p2 = person{name:"xm",age:12,desc:"desc"}
```

3.

```
p2 = new(person)
```

p1 p2 得到的是对象，p3 得到的是指向 person 指针
