```java
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class OkHttpClientUtil {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType TEXT = MediaType.parse("text/plain");
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60000, TimeUnit.MILLISECONDS)
            .writeTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS).build();
    private static final OkHttpClient esClient = new OkHttpClient.Builder()
            .addInterceptor(new RetryIntercepter(2))
            .connectTimeout(100, TimeUnit.MILLISECONDS)
            .writeTimeout(100, TimeUnit.MILLISECONDS)
            .readTimeout(100, TimeUnit.MILLISECONDS).build();

    /**
     * 同步请求
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * 同步请求。
     *
     * @return
     * @throws IOException
     */
    public static Response post(String url, String content) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, content))
                .build();

        return client.newCall(request).execute();
    }

    public static Response post(String url, String content, Headers headers) throws IOException {
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .post(RequestBody.create(JSON, content))
                .build();

        return client.newCall(request).execute();
    }

    public static Response post(String url, String content, MediaType mediaType) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, content))
                .build();

        return client.newCall(request).execute();
    }

    /**
     * 同步请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get().build();

        return client.newCall(request).execute();
    }

    public static Response get(String url, Headers headers) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .get().build();
        return client.newCall(request).execute();
    }

    /**
     * 同步请求。
     *
     * @return
     * @throws IOException
     */
    public static Response post(String url, String content, int connectTimeOut,
                                int writeTimeOut, int readTimeOut) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = RequestBody.create(TEXT, content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 同步请求。
     *
     * @return
     * @throws IOException
     */
    public static String postJSON(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            //PlatformLogger.warn(String.format("url=%s, json=%s, http code exception:%s", url, json, response.code()));
            throw new RuntimeException(url + " request:" + json + " status: " + response.code());
        }
    }

    /**
     * Del
     *
     * @return
     * @throws IOException
     */
    public static String del(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .addHeader("Authentication", "df5db1fcbcdf76da6346b7baa55a7a24")
                .url(url)
                .delete(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(url + " request:" + json + " status: " + response.code());
        }
    }

    /**
     * ES查询
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static Response searchJsonEs(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .addHeader("Authentication", "df5db1fcbcdf76da6346b7baa55a7a24")
                .url(url)
                .post(requestBody)
                .build();

        return esClient.newCall(request).execute();
    }

    /**
     * 该不会开启异步线程。
     *
     * @param timeout 毫秒数
     * @return
     * @throws IOException
     */
    public static String postJSON(String url, String json, long timeout) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newBuilder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .build()
                .newCall(request)
                .execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            //PlatformLogger.warn(String.format("url=%s, json=%s, http code exception:%s", url, json, response.code()));
            throw new RuntimeException(url + " request:" + json + " status: " + response.code());
        }
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        client.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     *
     * @param request
     */
    public static void enqueue(Request request) {
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    static class RetryIntercepter implements Interceptor {

        private static final Logger log = LoggerFactory.getLogger(RetryIntercepter.class);
        //最大重试次数
        public int maxRetry;
        //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
        private int retryNum = 0;

        public RetryIntercepter(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                log.info("查询ES重试,重试次数" + retryNum);
                response = chain.proceed(request);
            }
            return response;
        }

    }
}

```

