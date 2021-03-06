package com.github.lib.utils;

import cn.hutool.http.HttpRequest;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

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
    public static String get(@NonNull final String url, final Map<String, String> headerMap) {
        try {
            HttpRequest request = HttpRequest.get(url)
                    .setReadTimeout(10000)
                    .setConnectionTimeout(8000);
            if (null != headerMap) {
                request.headerMap(headerMap, true);
            }
            return request.execute().body();
        }catch (Exception e) {
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
    public static Document get(@NonNull final String url) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36 Edg/96.0.1054.43");
        String body = get(url, headerMap);
        if ("".equals(body)) {
            throw new IllegalArgumentException("response body is empty");
        }
        return Jsoup.parse(body);
    }
}
