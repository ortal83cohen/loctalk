package com.travoca.app.core;

import com.squareup.okhttp.OkHttpClient;
import com.travoca.api.mock.ResultsMockClient;
import com.travoca.api.model.search.Poi;
import com.travoca.app.Config;
import com.travoca.app.travocaapi.RetrofitLogger;
import com.travoca.app.travocaapi.UserAgentInterceptor;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author ortal
 * @date 2015-04-07
 */
public class CoreInterface {

    public static Service create(Context context) {

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new UserAgentInterceptor(context));
        client.interceptors().add(RetrofitLogger.create());
        client.interceptors().add(new ResultsMockClient());

        Retrofit restAdapter = new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.getCoreInterfaceEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return restAdapter.create(Service.class);
    }


    public interface Service {


        @Headers("Accept: application/json")
        @GET("/core_interface/sway.php/phone/customerservice/{country}")
        Call<String> customerServicePhone(@Path("country") String countryCode);

        @Headers("Accept: application/json")
        @GET("/core_interface/sway.php/uripars")
        Call<ArrayMap<String, String>> uriParse(@Query("uri") String uri);


        @Headers("Accept: application/json")
        @GET("/core_interface/sway.php/poi/list")
        Call<List<Poi>> poiList(@Query("lon") String lon, @Query("lat") String lat,
                @Query("radius") String radiusInMeters);

        @Headers("Accept: application/json")
        @GET("/core_interface/sway.php/poi/list")
        Call<List<Poi>> poiList(@Query("northeastlon") String northeastlon,
                @Query("northeastlat") String northeastlat,
                @Query("southwestlon") String southwestlon,
                @Query("southwestlat") String southwestlat);
    }


}
