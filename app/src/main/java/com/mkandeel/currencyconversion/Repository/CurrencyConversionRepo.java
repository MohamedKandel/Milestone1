package com.mkandeel.currencyconversion.Repository;

import static com.mkandeel.currencyconversion.Helper.Constants.API_KEY;

import android.app.Application;

import com.mkandeel.currencyconversion.Retrofit.APIserves;
import com.mkandeel.currencyconversion.Retrofit.RetrofitClient;
import com.mkandeel.currencyconversion.data.CurrencyModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CurrencyConversionRepo {
    private Application application;
    private APIserves apiserves;
    public CurrencyConversionRepo(Application application) {
        this.application = application;
        this.apiserves = RetrofitClient.getInstance()
                .create(APIserves.class);
    }

    public Observable<CurrencyModel> getCurrencyPerEURO() {
        return apiserves.getAllCurrency(API_KEY)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .debounce(2, TimeUnit.SECONDS)
                .distinct();
    }
}
