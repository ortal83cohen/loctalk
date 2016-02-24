package com.travoca.api;

import android.support.v4.util.ArrayMap;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.travoca.api.mock.ResultsMockClient;
import com.travoca.api.model.OrderRequest;
import com.travoca.api.model.OrderResponse;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.model.SearchRequest;
import com.travoca.api.model.search.ImageRequest;
import com.travoca.api.model.search.LikeRequest;
import com.travoca.api.model.search.ListType;
import com.travoca.api.model.search.Type;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.QueryMap;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class TravocaApi {

    public static final String PATH_RECORDS = "/records";
    public static final String PATH_IMAGE = "/image";
    public static final String PATH_ORDERS = "/etbstatic/placeAnOrder.json";
    public static final int LIMIT = 15;
    private OkHttpClient mHttpClient;

    private TravocaApiConfig mConfig;

    private Interceptor mRequestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request.httpUrl().newBuilder()
                    .addQueryParameter("apiKey", mConfig.getApiKey())
                    .addQueryParameter("campaignId", String.valueOf(mConfig.getCampaignId()))
                    .build();


            return chain.proceed(request.newBuilder().url(url).build());
        }
    };

    public TravocaApi(String apiKey, int campaignId) {
        this(new TravocaApiConfig(apiKey, campaignId), null);
    }

    public TravocaApi(TravocaApiConfig config) {
        this(config, null);
    }

    public TravocaApi(TravocaApiConfig config, OkHttpClient httpClient) {
        mConfig = config;
        mHttpClient = httpClient == null ? new OkHttpClient() : httpClient;
        mHttpClient.interceptors().add(0, mRequestInterceptor);
        mHttpClient.interceptors().add(new ResultsMockClient());
    }

    public TravocaApiConfig getConfig() {
        return mConfig;
    }

    private Service create() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mConfig.getEndpoint());

        Retrofit restAdapter = builder.build();

        return restAdapter.create(Service.class);
    }

    public Call<ResultsResponse> records(SearchRequest searchRequest, int offset) throws InvalidParameterException {

        Service service = create();

        ArrayMap<String, String> query = new ArrayMap<>();

        // Location
        Type loc = searchRequest.getType();
        if (loc == null) {
            throw new InvalidParameterException();
        }
        query.put("type", loc.getType());
        query.put("context", loc.getContext());

        query.put("currency", searchRequest.getCurrency());
        query.put("language", searchRequest.getLanguage());

        // Limit
        if (searchRequest.getType() instanceof ListType) {
            query.put("limit", String.valueOf(999));
            query.put("offset", String.valueOf(0));
        } else {
            query.put("limit", String.valueOf(LIMIT));
            query.put("offset", String.valueOf(offset));
        }

        // Sort
        SearchRequest.Sort sort = searchRequest.getSort();
        if (sort != null) {
            String[] sortStr = sort.type.split("_");
            query.put("orderBy", sortStr[0]);
            if (sortStr.length > 1) {
                query.put("order", sortStr[1]);
            }
        }

        query.put("customerCountryCode", searchRequest.getCustomerCountryCode());

        return service.records(query);
    }

    public Call<ResultsResponse> like(int id,String userId) throws InvalidParameterException {

        Service service = create();

        return service.like(new LikeRequest(String.valueOf(id), "like",userId));
    }

    public Call<ResultsResponse> unlike(int id,String userId) throws InvalidParameterException {

        Service service = create();

        return service.like(new LikeRequest(String.valueOf(id), "unlike",userId));
    }


    public Call<ResultsResponse> saveRecordDetails(String image, String record, String title, String description, String locationName, String lat, String lon, String type,String userId) {

        Service service = create();


        return service.saveRecordDetails(new ImageRequest(image, record, title, description, locationName, lat, lon, type,userId));
    }


    public interface Service {

        @GET(PATH_RECORDS)
        Call<ResultsResponse> records(@QueryMap Map<String, String> query);

        @PUT(PATH_RECORDS)
        Call<ResultsResponse> like(@Body LikeRequest request);

        @POST(PATH_RECORDS)
        Call<ResultsResponse> saveRecordDetails(@Body ImageRequest request);

    }

}
