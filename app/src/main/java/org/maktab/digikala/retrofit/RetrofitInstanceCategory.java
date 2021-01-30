package org.maktab.digikala.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.maktab.digikala.NetWorkParams;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceCategory {

    private static RetrofitInstanceCategory sInstance;
    private Retrofit mRetrofit;

    //retrofit instance should be singletone
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
        Type type = new TypeToken<List<ProductCategory>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetCategoryProductDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
