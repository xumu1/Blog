【MySQL】SQL中On和Where的区别
数据库再通过链接两张表或者多张表时来返回记录时，都会生成一张中间的临时表，然后再将这张表返回给用户；

在使用left join时，on和where条件的区别如下：

1. on条件是在生成临时表时使用的条件，它不管on中的条件是否为真，都会返回左边表中的记录，还会返回on条件为真的记录
2. where条件是在临时表生成好后，再对临时表进行过滤的条件。这时已经没有left join的含义（必须返回左边表的记录）了，条件不为真的就全部过滤掉。