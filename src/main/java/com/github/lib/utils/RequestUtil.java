package com.github.lib.utils;

import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2022/1/12.
 *
 * @author wy
 */
public final class RequestUtil {

    /**
     * http get 请求
     *
     * @param url       url
     * @param headerMap headerMap
     * @return {@code String}
     */
    @NonNull
    public String get(@NonNull final String url, final Map<String, String> headerMap) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                builder.header(key, headerMap.get(key));
            }
        }
        try {
            Response response = client.newCall(builder.build()).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    return body.string();
                }
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * http get 请求
     *
     * @param url url
     * @return {@link Document}
     */
    @NonNull
    public Document get(@NonNull final String url) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36 Edg/96.0.1054.43");
        String body = get(url, headerMap);
        if ("".equals(body)) {
            throw new IllegalArgumentException("response body is empty");
        }
        return Jsoup.parse(body);
    }
}
