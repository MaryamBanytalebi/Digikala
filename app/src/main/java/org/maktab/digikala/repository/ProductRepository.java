package org.maktab.digikala.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.maktab.digikala.NetWorkParams;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.retrofit.APIService;
import org.maktab.digikala.retrofit.RetrofitInstanceCategory;
import org.maktab.digikala.retrofit.RetrofitInstanceListOfProduct;
import org.maktab.digikala.retrofit.RetrofitInstanceProduct;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductRepository {

    public static final String TAG = "product repository";

    private APIService mAPIServiceProduct;
    private APIService mAPIServiceListOfProduct;
    private APIService mAPIServiceCategory;

    private String mPage;

    private MutableLiveData<List<Product>> mMostVisitedProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mLatestProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mHighestScoreProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductWithParentIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductCategory>> mProductCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductLiveData = new MutableLiveData<>();


    public MutableLiveData<List<Product>> getMostVisitedProductsLiveData() {
        return mMostVisitedProductsLiveData;
    }

    public MutableLiveData<List<Product>> getLatestProductsLiveData() {
        return mLatestProductsLiveData;
    }

    public MutableLiveData<List<Product>> getHighestScoreProductsLiveData() {
        return mHighestScoreProductsLiveData;
    }

    public MutableLiveData<List<Product>> getProductItemsLiveData() {
        return mProductItemsLiveData;
    }

    public MutableLiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public MutableLiveData<List<Product>> getProductWithParentIdLiveData() {
        return mProductWithParentIdLiveData;
    }

    public MutableLiveData<List<ProductCategory>> getProductCategoryLiveData() {
        return mProductCategoryLiveData;
    }

    public MutableLiveData<List<Product>> getSearchProductsLiveData() {
        return mSearchProductsLiveData;
    }

    public ProductRepository() {

        Retrofit retrofitProduct = RetrofitInstanceProduct.getInstance().getRetrofit();
        mAPIServiceProduct = retrofitProduct.create(APIService.class);
        mPage = "1";

        Retrofit retrofitListOfProduct = RetrofitInstanceListOfProduct.getInstance().getRetrofit();
        mAPIServiceListOfProduct = retrofitListOfProduct.create(APIService.class);

        Retrofit retrofitCategoryProduct = RetrofitInstanceCategory.getInstance().getRetrofit();
        mAPIServiceCategory = retrofitCategoryProduct.create(APIService.class);
    }

    public String getPage() {
        return mPage;
    }

    public void setPage(String page) {
        mPage = page;
    }

    //This method can be run on background Thread
    public List<Product> fetchProductItems(String page){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(NetWorkParams.getProduct(page));
        try {
            Response<List<Product>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
            return null;
        }
    }

    public void fetchProductItemsAsync(String page){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(NetWorkParams.getProduct(page));
        //new Thread
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> items = response.body();
                //update adapter of recyclerview
                mProductItemsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductItemsAsync(int id){
        Call<Product> call = mAPIServiceProduct.getProduct(id,NetWorkParams.getProduct("1"));
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchMostVisitedItemsAsync(){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(
                NetWorkParams.getMostVisitedProducts());
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> items = response.body();
                mMostVisitedProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchHighestScoreItemsAsync(){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(
                NetWorkParams.getHighestScoreProduct());
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> items = response.body();
                mHighestScoreProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchLatestItemsAsync(){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(
                NetWorkParams.getLatestProducts());
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> items = response.body();
                mLatestProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchGetProductWithIdItemsAsync(String id){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(
                NetWorkParams.getProductsWithParentId(id));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategoryItemsAsync(){
        Call<List<ProductCategory>> call =
                mAPIServiceCategory.productCategory(NetWorkParams.getCategories());

        call.enqueue(new Callback<List<ProductCategory>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                List<ProductCategory> items = response.body();

                //update adapter of recyclerview
                mProductCategoryLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSubCategoryItemsAsync(String id){
        Call<List<ProductCategory>> call =
                mAPIServiceCategory.productCategory(NetWorkParams.subCategories(id));

        call.enqueue(new Callback<List<ProductCategory>>() {

            //this run on main thread
            @Override
            //use call back
            public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                List<ProductCategory> items = response.body();

                //update adapter of recyclerview
                mProductCategoryLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.Products(NetWorkParams.getSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}
