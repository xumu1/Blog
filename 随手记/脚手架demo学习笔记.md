# 脚手架demo学习笔记

## es

因为使用的是spring的RestTemplate向远程es服务器发http请求，所以涉及es本身的内容并不多。

### 首先需要创建一个本地es客户端

1. 设置远程连接三个字段，分别是

- ```
  createUrlFormat= "http://es.xxx.com/index/%s/type/info/bulk";
  ```

- ```
  queryUrlFormat= "http://es.xxx.com/index/%s/template/%s/%s/search";
  ```

- ```
  deleteUrlFormat= "http://es.xxx.com/index/%s/type/info/%s";
  ```

这三个请求地址包含了创建、查询、删除的基本请求，请求参数使用占位符，在真正发起请求的时候进行填充

2. 设置连接的基本属性

- ```
  connectionTimeout = 3 * 1000;
  ```

- ```
  socketTimeout = 10 * 1000;
  ```

3. 身份验证码code，使用时填充

- ```
  private final String code;
  ```

4. save( )函数

   ```java
   public ResponseEntity<String> save(String index, Object... data) throws Throwable {
       if (null == data) {
           return null;
       }
       // 创建spring的template客户端对象
       Template rest = HttpClientFactory.create()
           .connectionTimeout(this.connectionTimeout)
           .socketTimeout(this.socketTimeout)
           .contentType(MediaType.APPLICATION_JSON_UTF8)
           .build();
       // 数组转换为JSONArray
       JSONArray array = new JSONArray();
       array.addAll(Arrays.asList(data));
       // 设置身份验证🐎，对远程es服务器发起post请求
       return rest
           .setHeader("Authentication", this.code)
           .postForEntity(String.format(createUrlFormat, index), array, String.class);
       }
```
   
5. query函数
   
   ```java
   public <T> ResponseEntity<T> query
       (String index, String template, String version, Object body, Class<T> t) 
       throws Throwable {
       // 格式化请求地址字符串
       String url = String.format(queryUrlFormat, index, template, version);
       // 创建spring的template客户端对象
       Template rest = HttpClientFactory.create()
           .connectionTimeout(this.connectionTimeout)
           .socketTimeout(this.socketTimeout)
           .contentType(MediaType.APPLICATION_JSON_UTF8)
           .build();
       if (null == body) {
           body = new HashMap<>();
    }
       return rest
           .setHeader("Authentication", this.code)
           .postForEntity(url, body, t);
   }
   ```
   
   6. delete函数
   
   ```java
   public <T> ResponseEntity<T> delete(String index, String id, Class<T> t) throws Throwable {
       // 创建spring的template客户端对象
   	Template rest = HttpClientFactory
           .create()
           .connectionTimeout(this.connectionTimeout)
           .socketTimeout(this.socketTimeout).build();
       // 格式化请求地址字符串
   	String url = String.format(deleteUrlFormat, index, id);
   	return rest
           .setHeader("Authentication", this.code)
           .deleteWithParams(url, null, t);
   }
   ```

### 再看一下esClient使用的template对象

脚手架的template本质也是将spring的RestTemplate封装了一把，方便使用

1. 一些基本连接属性

```java
private int connectionTimeout = 3 * 1000;
private int socketTimeout = 10 * 1000;
private int retryCount = 1;
private int backOffPeriod = 15;
private MediaType contentType = MediaType.APPLICATION_JSON;
private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
```

2. 封装的restTemplate

```java
public RestTemplate restTemplate() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(this.connectionTimeout);
    requestFactory.setReadTimeout(this.socketTimeout);
    return restTemplate(requestFactory);
}
private RestTemplate restTemplate(ClientHttpRequestFactory factory) {
    RestTemplate restTemplate = new RestTemplate(factory);
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
    while (iterator.hasNext()) {
        HttpMessageConverter<?> converter = iterator.next();
        //原有的String是ISO-8859-1编码 去掉
        if (converter instanceof StringHttpMessageConverter) {
            iterator.remove();
        }
        //由于系统中默认有jackson 在转换json时自动会启用  但是我们不想使用它 可以直接移除
        if (converter instanceof GsonHttpMessageConverter || converter instanceof MappingJackson2HttpMessageConverter) {
            iterator.remove();
        }
    }
    messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    messageConverters.add(fastJsonHttpMessageConverter);
    return restTemplate;
}
```

3. 介绍一下spring的RetryTemplate

   官方注释

   >  Template class that simplifies the execution of operations with retry semantics.

   常用构建方式

   ```
   RetryTemplate.builder()
                 .maxAttempts(10)
                 .fixedBackoff(1000)
                 .customBackoff(backOffPolicy)
                 .customPolicy(new MaxAttemptsRetryPolicy(this.retryCount))
                 .build();
   ```

4. getForObject函数

```java
public <T> T getForObject(String url, MultiValueMap<String, String> params, Class<T> clz) throws Throwable {
    // 工厂
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(this.connectionTimeout);
    requestFactory.setReadTimeout(this.socketTimeout);
	//创建retryTemplate，设置属性和重试策略
    RetryTemplate retryTemplate = new RetryTemplate();
    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(this.backOffPeriod);
    retryTemplate.setBackOffPolicy(backOffPolicy);
    retryTemplate.setRetryPolicy(new MaxAttemptsRetryPolicy(this.retryCount));
	// 设置uri路径
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    if (null != params && !params.isEmpty()) {
        builder.queryParams(params);
    }
    // 执行操作，传入回调函数
    return retryTemplate.execute((RetryCallback<T, Throwable>) context -> {
        this.headers.set("Content-Type", this.contentType.toString());
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, this.headers);
        return restTemplate(requestFactory).exchange(builder.build().toString(), HttpMethod.GET, requestEntity, clz).getBody();
    });
}
```

3. getForEntity函数

   实现类似于getForObject，封装了更多的http信息

4. 其他函数比较类似，不做展示。

### 看一下实际代码如何使用

比较简单，实际调用也就一句，把函数参数写好就好了

```java
@PostMapping("esSave")
public ResponseModel esSave(@RequestBody UserInfoEntity entity) throws Throwable {
    ResponseEntity<String> rsp = esClient.save("tcwireless-apibase", entity);
    if (!HttpStatus.OK.equals(rsp.getStatusCode())) {
        return ResponseModel.fail();
    }
    return ResponseModel.success(rsp.getBody());
}

@GetMapping("esSearchAll")
public ResponseModel esSearchAll() throws Throwable {
    ResponseEntity<String> rsp = esClient.query("tcwireless-apibase", "search_200", "latest", null, String.class);
    if (!HttpStatus.OK.equals(rsp.getStatusCode())) {
        return ResponseModel.fail();
    }
    return ResponseModel.success(rsp.getBody());
}

@DeleteMapping("esDelete")
public ResponseModel esDelete() throws Throwable {
    ResponseEntity<String> rsp = esClient.delete("tcwireless-apibase", "b112ef0e-92df-4a78-9eba-9489bf89783b", String.class);
    if (!HttpStatus.OK.equals(rsp.getStatusCode())) {
        return ResponseModel.fail();
    }
    return ResponseModel.success(rsp.getBody());
}

@PostMapping("esUpdate")
public ResponseModel esUpdate(@RequestBody JSONObject entity) throws Throwable {
    ResponseEntity<String> rsp = esClient.save("tcwireless-apibase", entity);
    if (!HttpStatus.OK.equals(rsp.getStatusCode())) {
        return ResponseModel.fail();
    }
    return ResponseModel.success(rsp.getBody());
}
```

## kafka

分为消费者(consumer)和生产者(producer)

### 先看consumer

分为真实consumer和consumerClient

**consumer抽象类**

```java
public abstract class Consumer {

    private long timeout = 0;

    public Consumer(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Kafka Consumer timeout must not be negative");
        }
        this.timeout = timeout;
    }

    long getTimeout() {
        return this.timeout;
    }

    protected abstract void consume(ConsumerRecords<String, String> records);
}
```

**consumerClient**

构造函数和基本属性

```java
private final Properties properties;

private KafkaConsumer<String, String> consumer;

private volatile boolean status = false;

public KafkaConsumerClient(Properties properties) {
    this.properties = properties;
}
```

build函数

```java
public void build() {
    if (null == this.properties) {
        return;
    }
    if (null == this.consumer) {
        this.consumer = new KafkaConsumer<String, String>(this.properties);
        this.status = true;
    }
}
```

订阅和消费函数，consume（）接受一个Consumer c，从自己的消费队列中拿出一个数据交给接受的consumer进行消费。

```java
// 订阅
public void subscribe(String... topics) {
    this.consumer.subscribe(Arrays.asList(topics));
}

// 消费   阻塞
public void consume(Consumer c) {
    if (!this.status || null == this.consumer) {
        throw new RuntimeException("consumer已经关闭，不能再次使用");
    }
    while (this.status && null != this.consumer) {
        ConsumerRecords<String, String> data = this.consumer.poll(c.getTimeout());
        c.consume(data);
    }
    if (null != this.consumer) {
        this.consumer.close();
        this.consumer = null;
    }
}
```

关闭、检查是否关闭、toString（）

```java
// 关闭消费
public void close() {
    this.status = false;
}

// 当前开关状态
public boolean running() {
    return this.status;
}


@Override
public String toString() {
    return "KafkaConsumerClient{" +
            "properties=" + properties +
            '}';
}
```

### 再看producer

**KafkaProducerClient**

基本属性、构造函数

```java
private final Properties properties;

private KafkaProducer<String, String> producer;

private volatile boolean status = false;

public KafkaProducerClient(Properties properties) {
        this.properties = properties;
    }
```

build（）

```java
public void build() {
    if (null == this.properties) {
        return;
    }
    if (null == this.producer) {
        this.producer = new KafkaProducer<String, String>(this.properties);
        this.status = true;
    }
}
```

生产函数send（）

```java
public void send(String topic, String key, String msg, Callback callback) {
    if (!this.status) {
        throw new RuntimeException("producer已经关闭，不能再次使用");
    }
    if (null == this.producer) {
        throw new RuntimeException("null producer");
    }
    if (null == callback) {
        this.producer.send(new ProducerRecord<>(topic, key, msg));
    } else {
        this.producer.send(new ProducerRecord<>(topic, key, msg), callback);
    }

}
public void send(String topic, String msg, Callback callback) {
    if (!this.status) {
        throw new RuntimeException("producer已经关闭，不能再次使用");
    }
    if (null == this.producer) {
        throw new RuntimeException("null producer");
    }
    if (null == callback) {
        this.producer.send(new ProducerRecord<>(topic, msg));
    } else {
        this.producer.send(new ProducerRecord<>(topic, msg), callback);
    }
}
```

开关、toString（）

```java
// 当前开关状态
public boolean running() {
    return this.status;
}
public void close() {
    this.status = false;
    if (null != this.producer) {
        this.producer.close();
        this.producer = null;
    }
}
@Override
public String toString() {
    return "KafkaProducerClient{" +
            "properties=" + properties +
            '}';
}
```



> 生产方式固定、消费方式五花八门，所以consumerClient接收需要一个专用的consumer