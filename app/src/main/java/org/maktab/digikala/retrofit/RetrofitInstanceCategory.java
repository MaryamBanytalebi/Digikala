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

public class RetrofitInstanceCategory {

    private static RetrofitInstanceCategory sInstance;
    private Retrofit mRetrofit;

    public static RetrofitInstanceCategory getInstance() {
        if (sInstance == null)
            sInstance = new RetrofitInstanceCategory();

        return sInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private RetrofitInstanceCategory() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetWorkParams.BASE_URL)
                .addConverterFactory(createGsonConverter())
                .build();
    }

    private static Converter.Factory createGsonConverter() {
        Type type = new TypeToken<Product>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetProductDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
