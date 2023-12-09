package com.mkandeel.currencyconversion.Retrofit;

import com.mkandeel.currencyconversion.data.CurrencyModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIserves {
    @GET("api/latest")
    Observable<CurrencyModel> getAllCurrency(@Query("access_key") String API_KEY);
    @GET("api/latest/{date}")
    Observable<CurrencyModel> getHistory(@Query("access_key") String API_KEY,
                                         @Path("date") String date);
}
