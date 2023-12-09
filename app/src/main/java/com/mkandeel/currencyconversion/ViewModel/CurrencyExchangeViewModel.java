package com.mkandeel.currencyconversion.ViewModel;

import static com.mkandeel.currencyconversion.Helper.Constants.API_TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mkandeel.currencyconversion.Repository.CurrencyConversionRepo;
import com.mkandeel.currencyconversion.data.CurrencyModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CurrencyExchangeViewModel extends AndroidViewModel {
    private CurrencyConversionRepo repo;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<CurrencyModel> allCurrencyPerEURO = new MutableLiveData<>();
    public CurrencyExchangeViewModel(@NonNull Application application) {
        super(application);
        this.repo = new CurrencyConversionRepo(application);
        compositeDisposable = new CompositeDisposable();
    }

    public void getAllCurrency() {
        compositeDisposable.add(repo.getCurrencyPerEURO()
                .subscribe(currencyModel -> allCurrencyPerEURO.postValue(currencyModel),
                        throwable -> Log.e(API_TAG, "getAllCurrency: ", throwable)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<CurrencyModel> getAllCurrencyPerEuro() {
        return allCurrencyPerEURO;
    }
}
