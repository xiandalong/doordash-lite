package com.doordash.doordashlite.network;

import com.doordash.doordashlite.models.Restaurant;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface DoorDashApi {
    @GET("restaurant")
    Flowable<List<Restaurant>> getRestaurantsByLocation(@Query("lat") String lat,
                                                        @Query("lng") String lng,
                                                        @Query("offset") String offset,
                                                        @Query("limit") String limit);
}
