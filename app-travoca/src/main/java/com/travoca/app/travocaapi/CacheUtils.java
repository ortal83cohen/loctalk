package com.travoca.app.travocaapi;

import com.squareup.okhttp.Request;
import com.travoca.api.TravocaApi;

import java.io.IOException;
import java.net.URI;

/**
 * @author ortal
 * @date 2015-09-22
 */
public class CacheUtils {

    public static boolean isSearchRequest(URI uri) {
        return false;
    }

    public static boolean isRetrieveOrderRequest(Request request) throws IOException {
        // retrieve order request
        return "GET".equals(request.method()) && request.uri().getPath().startsWith(TravocaApi.PATH_ORDERS);
    }

    public static boolean isCachableRequest(Request request) throws IOException {
        return isSearchRequest(request.uri()) || isRetrieveOrderRequest(request);
    }
}
