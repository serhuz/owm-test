package com.example.owmtest.retrofit;

import com.example.owmtest.retrofit.entities.OwmResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface OwmApi {

    @GET("/data/2.5/weather")
    Observable<OwmResponse> getWeatherForLocation(@Query("lat") double lat,
                                                  @Query("lon") double lng,
                                                  @Query("units") String units,
                                                  @Query("appId") String appId);

    @GET("/data/2.5/weather")
    Observable<OwmResponse> searchWeatherForCity(@Query("q") String city,
                                                 @Query("units") String units,
                                                 @Query("appId") String appId);
}
