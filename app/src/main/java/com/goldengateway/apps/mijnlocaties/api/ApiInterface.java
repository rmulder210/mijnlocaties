package com.goldengateway.apps.mijnlocaties.api;

import com.goldengateway.apps.mijnlocaties.model.Products;
import com.goldengateway.apps.mijnlocaties.model.Times;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Rob on 02-10-15.
 */
public interface ApiInterface {

    @GET("/v1/products")
    Products getProducts(@Query("latitude") double latitude,
                         @Query("longitude") double longitude);

    @GET("/v1/products")
    void getProducts(@Query("latitude") double latitude,
                     @Query("longitude") double longitude,
                     Callback<Products> productsCallback);


    @GET("/v1/estimates/time")
    Times getTimeEstimates(@Query("start_latitude") double startLatitude,
                           @Query("start_longitude") double startLongitude,
                           @Query("customer_uuid") String customerUUID,
                           @Query("product_id") String productId);

    /**
     * @see #getTimeEstimates(double, double, String, String)
     */
    @GET("/v1/estimates/time")
    void getTimeEstimates(@Query("start_latitude") double startLatitude,
                          @Query("start_longitude") double startLongitude,
                          @Query("customer_uuid") String customerUUID,
                          @Query("product_id") String productId,
                          Callback<Times> timesCallback);


}