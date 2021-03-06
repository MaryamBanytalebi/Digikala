package org.maktab.digikala.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.maktab.digikala.NetWorkParams;
import org.maktab.digikala.model.Product;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceListOfProduct {

    private static RetrofitInstanceListOfProduct sInstance;
    private Retrofit mRetrofit;

    public static RetrofitInstanceListOfProduct getInstance(){

        if (sInstance == null)
            sInstance = new RetrofitInstanceListOfProduct();
        return sInstance;
    }

    public RetrofitInstanceListOfProduct() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetWorkParams.BASE_URL)
                .addConverterFactory(createGsonConverter())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private static Converter.Factory createGsonConverter() {
        Type type = new TypeToken<List<Product>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetListOfProductDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
