# 安装
 略，参考不同操作系统自行解决。

# 卸载
略，参考不同操作系统自行解决。

# 登录
 mysql -uroot -p密码

# MySQL退出

exit
quit

>  几个基本概念
>  1. linux下的路径
usr/bin/mysql 是指：mysql的运行路径
var/lib/mysql 是指：mysql数据库文件的存放路径
usr/lib/mysql 是指：mysql的安装路径
> 2. 数据库：文件夹
表：文件
数据：数据
> 3. SQL分类
		1) DDL(Data Definition Language)数据定义语言
			用来定义数据库对象：数据库，表，列等。关键字：create, drop,alter 等
		2) DML(Data Manipulation Language)数据操作语言
			用来对数据库中表的数据进行增删改。关键字：insert, delete, update 等
		3) DQL(Data Query Language)数据查询语言
			用来查询数据库中表的记录(数据)。关键字：select, where 等
		4) DCL(Data Control Language)数据控制语言（了解）
			用来定义数据库的访问权限和安全级别，及创建用户。关键字：GRANT， REVOKE 等
---
# 常用命令第一类： DDL

## 操作数据库：CRUD
### 1. C(Create):创建
create database 数据库名称---------------------------------------创建数据库： 

create database if not exists 数据库名称 -----------------------创建数据库，判断不存在，再创建： 

create database 数据库名称 character set 字符集名   --------创建数据库，并指定字符集： 

### 2. R(Retrieve)：查询
show databases-----------------------------------------------查询所有数据库的名称

show create database 数据库名称------------------------查询某个数据库的字符集:查询某个数据库的创建语句

### 3. U(Update):修改
alter database 数据库名称 character set 字符集名称------------修改数据库的字符集

### 4. D(Delete):删除
drop database 数据库名称

drop database if exists 数据库名称

### 5. 使用数据库
select database()--------------------------查询当前正在使用的数据库名称

use 数据库名称----------------------------使用数据库

## 操作表：CRUD
### 1. C(Create):创建
create table 表名(
					列名1 数据类型1,
					列名2 数据类型2,
					....
					列名n 数据类型n
				);
* 注意：最后一列，不需要加逗号（,）
* 数据库类型：
		1. int：整数类型
						* age int,
		2. double:小数类型
						* score double(5,2)
		3. date:日期，只包含年月日，yyyy-MM-dd
		4. datetime:日期，包含年月日时分秒	 yyyy-MM-dd HH:mm:ss
		5. timestamp:时间错类型	包含年月日时分秒	 yyyy-MM-dd HH:mm:ss	
* 如果将来不给这个字段赋值，或赋值为null，则默认使用当前的系统时间，来自动赋值
                6. varchar：字符串
* create table 表名 like 被复制的表名------------------------- 复制表：
### 2. R(Retrieve)：查询
show tables-------------------查询某个数据库中所有的表名称

desc 表名-----------------------查询表结构

### 3. U(Update):修改
1. 修改表名
				alter table 表名 rename to 新的表名;
2. 修改表的字符集
				alter table 表名 character set 字符集名称;
3. 添加一列
				alter table 表名 add 列名 数据类型;
4. 修改列名称 类型
				alter table 表名 change 列名 新列别 新数据类型;
				alter table 表名 modify 列名 新数据类型;
5. 删除列
				alter table 表名 drop 列名;
### 4. D(Delete):删除
drop table 表名;
drop table  if exists 表名 ;
---
# 常用命令2：DML
## DML：增删改表中数据
### 1. 添加数据：
insert into 表名(列名1,列名2,...列名n) values(值1,值2,...值n);
		* 注意：
			1. 列名和值要一一对应。
			2. 如果表名后，不定义列名，则默认给所有列添加值
				insert into 表名 values(值1,值2,...值n);
			3. 除了数字类型，其他类型需要使用引号(单双都可以)引起来

### 2. 删除数据：
delete from 表名 [where 条件]
* 注意如果要删除所有记录
				1. delete from 表名; -- 不推荐使用。有多少条记录就会执行多少次删除操作
				2. TRUNCATE TABLE 表名; -- 推荐使用，效率更高 先删除表，然后再创建一张一样的表。
### 3. 修改数据：
update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];

---
# 常用命令3 ：DQL
## DQL：查询表中的记录
select * from 表名;
	
### 1. 语法：
select
&emsp;&emsp;字段列表
from
&emsp;&emsp;表名列表
where
&emsp;&emsp;条件列表
group by
&emsp;&emsp;分组字段
having
&emsp;&emsp;分组之后的条件
order by
&emsp;&emsp;排序
limit
&emsp;&emsp;分页限定


### 2. 基础查询
1. 多个字段的查询
select 字段名1，字段名2... from 表名；

2. 去除重复：
distinct

3. 计算列
一般可以使用四则运算计算一些列的值。（一般只会进行数值型的计算）

4. 起别名：
as：as也可以省略
			

### 3. 条件查询
1. where子句后跟条件
2. 运算符
<  、> 、<= 、>= 、= 、<>
BETWEEN...AND  
IN( 集合) 
LIKE：模糊查询
占位符：
 _:单个任意字符
%：多个任意字符
IS NULL  
and  或 &&
or  或 || 
not  或 !

### 4. 排序查询
order by 排序字段1 排序方式1 ，  排序字段2 排序方式2...
排序方式：
ASC：升序，默认的。
DESC：降序。
* 如果有多个排序条件，则当前边的条件值一样时，才会判断第二条件。

### 5. 聚合函数：将一列数据作为一个整体，进行纵向的计算。
1. count：计算个数
			1). 一般选择非空的列：主键
			2). count(*)
2. max：计算最大值
3. min：计算最小值
4. sum：计算和
5. avg：计算平均值
* 注意：聚合函数的计算，排除null值。
			解决方案：
				1. 选择不包含非空的列进行计算
				2. IFNULL函数

### 6. 分组查询:
1. 语法：group by 分组字段；
> 注意：
       1. 分组之后查询的字段：分组字段、聚合函数
       2. where 和 having 的区别？
where 在分组之前进行限定，如果不满足条件，则不参与分组。having在分组之后进行限定，如果不满足结果，则不会被查询出来
      where 后不可以跟聚合函数，having可以进行聚合函数的判断。

examples
--  按照性别分组。分别查询男、女同学的平均分,人数 要求：分数低于70分的人，不参与分组,分组之后。人数要大于2个人
SELECT sex , AVG(math),COUNT(id) FROM student WHERE math > 70 GROUP BY sex HAVING COUNT(id) > 2;
SELECT sex , AVG(math),COUNT(id) 人数 FROM student WHERE math > 70 GROUP BY sex HAVING 人数 > 2;


			
### 7. 分页查询
1. 语法：limit 开始的索引,每页查询的条数;
2. 公式：开始的索引 = （当前的页码 - 1） * 每页显示的条数
-- 每页显示3条记录 
SELECT * FROM student LIMIT 0,3; -- 第1页			
SELECT * FROM student LIMIT 3,3; -- 第2页
SELECT * FROM student LIMIT 6,3; -- 第3页
3. limit 是一个MySQL"方言"
 
