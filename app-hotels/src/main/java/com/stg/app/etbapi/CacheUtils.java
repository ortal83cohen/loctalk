package com.stg.app.etbapi;

import com.socialtravelguide.api.StgApi;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.URI;

/**
 * @author ortal
 * @date 2015-09-22
 */
public class CacheUtils {

    public static boolean isSearchRequest(URI uri) {
        return uri.getPath().startsWith(StgApi.PATH_ACCOMMODATIONS);
    }

    public static boolean isRetrieveOrderRequest(Request request) throws IOException {
        // retrieve order request
        return "GET".equals(request.method()) && request.uri().getPath().startsWith(StgApi.PATH_ORDERS);
    }

    public static boolean isCachableRequest(Request request) throws IOException {
        return isSearchRequest(request.uri()) || isRetrieveOrderRequest(request);
    }
}
