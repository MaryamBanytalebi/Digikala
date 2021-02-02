package org.maktab.digikala.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.maktab.digikala.NetWorkParams;
import org.maktab.digikala.model.Product;

import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceSales {

    private static RetrofitInstanceSales sInstance;
    private Retrofit mRetrofit;

    public static RetrofitInstanceSales getInstance() {
        if (sInstance == null)
            sInstance = new RetrofitInstanceSales();

        return sInstance;
    }

    private static Converter.Factory createGsonConverter() {
        Type type = new TypeToken<Product>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetSalesDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private RetrofitInstanceSales() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetWorkParams.BASE_URL)
                .addConverterFactory(createGsonConverter())
                .build();
    }
}
