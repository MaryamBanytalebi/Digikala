package org.maktab.digikala.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.maktab.digikala.NetWorkParams;
import org.maktab.digikala.model.Comment;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.model.SalesReport;
import org.maktab.digikala.retrofit.APIService;
import org.maktab.digikala.retrofit.RetrofitInstanceCategory;
import org.maktab.digikala.retrofit.RetrofitInstanceComments;
import org.maktab.digikala.retrofit.RetrofitInstanceCustomer;
import org.maktab.digikala.retrofit.RetrofitInstanceListOfProduct;
import org.maktab.digikala.retrofit.RetrofitInstanceProduct;
import org.maktab.digikala.retrofit.RetrofitInstanceSales;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductRepository {

    public static final String TAG = "product repository";

    private final APIService mAPIServiceProduct;
    private final APIService mAPIServiceListOfProduct;
    private final APIService mAPIServiceCategory;
    private final APIService mAPIServiceCustomer;
    private final APIService mAPIServiceSalesReport;
    private final APIService mAPIServiceComment;

    private String mPage;
    private static int mSort;

    private MutableLiveData<List<Product>> mMostVisitedProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mLatestProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mHighestScoreProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductWithParentIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductCategory>> mProductCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedLowToHighSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedHighToLowSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedTopSellersSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductsLiveData1 = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductsLiveData2 = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductsLiveData3 = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedTotalSalesSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<SalesReport> mSalesReportMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Customer> mCustomerLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> mLiveDataComment = new MutableLiveData<>();
    private MutableLiveData<Comment> mCommentLiveData = new MutableLiveData<>();
    private MutableLiveData<Comment> mLiveDataOneComment = new MutableLiveData<>();
    private MutableLiveData<Comment> mLiveDataPUTComment = new MutableLiveData<>();
    private MutableLiveData<Comment> mLiveDataDeleteComment = new MutableLiveData<>();

    public MutableLiveData<Comment> getLiveDataDeleteComment() {
        return mLiveDataDeleteComment;
    }

    public static int getSort() {
        return mSort;
    }

    public static void setSort(int mSort) {
        ProductRepository.mSort = mSort;
    }

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

    public MutableLiveData<SalesReport> getSalesReportMutableLiveData() {
        return mSalesReportMutableLiveData;
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

    public MutableLiveData<List<Product>> getSortedLowToHighSearchProductsLiveData() {
        return mSortedLowToHighSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSortedHighToLowSearchProductsLiveData() {
        return mSortedHighToLowSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSortedTopSellersSearchProductsLiveData() {
        return mSortedTopSellersSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSpecialProductsLiveData1() {
        return mSpecialProductsLiveData1;
    }

    public MutableLiveData<List<Product>> getSpecialProductsLiveData2() {
        return mSpecialProductsLiveData2;
    }

    public MutableLiveData<List<Product>> getSpecialProductsLiveData3() {
        return mSpecialProductsLiveData3;
    }

    public MutableLiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public MutableLiveData<List<Comment>> getLiveDataComment() {
        return mLiveDataComment;
    }

    public MutableLiveData<Comment> getCommentLiveData() {
        return mCommentLiveData;
    }

    public MutableLiveData<Comment> getLiveDataPUTComment() {
        return mLiveDataPUTComment;
    }

    public MutableLiveData<Comment> getLiveDataOneComment() {
        return mLiveDataOneComment;
    }
    public ProductRepository() {

        Retrofit retrofitProduct = RetrofitInstanceProduct.getInstance().getRetrofit();
        mAPIServiceProduct = retrofitProduct.create(APIService.class);

        Retrofit retrofitListOfProduct = RetrofitInstanceListOfProduct.getInstance().getRetrofit();
        mAPIServiceListOfProduct = retrofitListOfProduct.create(APIService.class);

        Retrofit retrofitCategoryProduct = RetrofitInstanceCategory.getInstance().getRetrofit();
        mAPIServiceCategory = retrofitCategoryProduct.create(APIService.class);

        Retrofit retrofitCustomer = RetrofitInstanceCustomer.getInstance().getRetrofit();
        mAPIServiceCustomer = retrofitCustomer.create(APIService.class);

        Retrofit retrofitSalesReport = RetrofitInstanceSales.getInstance().getRetrofit();
        mAPIServiceSalesReport = retrofitSalesReport.create(APIService.class);

        Retrofit retrofitComment = RetrofitInstanceComments.getInstance().getRetrofit();
        mAPIServiceComment = retrofitComment.create(APIService.class);
        mPage = "1";

    }

    public String getPage() {
        return mPage;
    }

    public void setPage(String page) {
        mPage = page;
    }

    public List<SalesReport> fetchSalesReport() {
        Call<List<SalesReport>> call = mAPIServiceSalesReport.sales(NetWorkParams.getTotalItemsSalesProducts());
        try {
            Response<List<SalesReport>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    //This method can be run on background Thread
    /*public SalesReport fetchProductItems(){
        Call<SalesReport> call = mAPIServiceListOfProduct.sales(NetWorkParams.getTotalItemsSalesProducts());
        try {
            Response<SalesReport> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }*/

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
                Product items = response.body();

                //update adapter of recyclerview
                mProductLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductWithParentIdAsync(String id){
        Call<List<Product>> call = mAPIServiceListOfProduct.Products(
                NetWorkParams.getProductsWithParentId(id));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                //update adapter of recyclerview
                mProductWithParentIdLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
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
        Call<List<Product>> call =
                mAPIServiceListOfProduct.Products(NetWorkParams.getLatestProducts());

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                mLatestProductsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                fetchLatestItemsAsync();
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

    public void fetchSortedLowToHighSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.Products(NetWorkParams.getSortedLowToHighSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSortedLowToHighSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSortedHighToLowSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.Products(NetWorkParams.getSortedHighToLowSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSortedHighToLowSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void postCreateCustomerAsync(Customer customer) {
        Call<Customer> call =
                mAPIServiceCustomer.customer(customer.getEmail(),customer.getFirst_name(),
                        customer.getLast_name(),customer.getUsername(),NetWorkParams.getMainAddress());

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer items = response.body();

                mCustomerLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSpecialProductItemsAsync(String id, String page) {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.Products(NetWorkParams.getSpecialProducts(id, page));

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                if (page.equalsIgnoreCase("1"))
                    mSpecialProductsLiveData1.postValue(items);
                else if (page.equalsIgnoreCase("2"))
                    mSpecialProductsLiveData2.postValue(items);
                else if (page.equalsIgnoreCase("3"))
                    mSpecialProductsLiveData3.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

    }

    public void fetchSortedTotalSalesSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.Products(NetWorkParams.getSortedTotalSalesSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSortedTotalSalesSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCommentAsync(String productId) {
        Call<List<Comment>> call =
                mAPIServiceComment.comments(NetWorkParams.getCommentOfProduct(productId));

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> items = response.body();

                mLiveDataComment.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchAddCommentAsync(Comment comment) {
        Call<Comment> call =
                mAPIServiceComment.addComment(comment,NetWorkParams.getAddCommentOfProduct());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mCommentLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchOneCommentAsync(int commentId) {
        Call<Comment> call =
                mAPIServiceComment.getCommentWithId(commentId,NetWorkParams.getAddCommentOfProduct());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mLiveDataOneComment.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchPUTCommentAsync(Comment comment) {
        Call<Comment> call =
                mAPIServiceComment.putCommentWithId(comment,comment.getId(),
                        NetWorkParams.getAddCommentOfProduct());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mLiveDataPUTComment.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchDeleteCommentAsync(int commentId) {
        Call<Comment> call =
                mAPIServiceComment.deleteCommentWithId(commentId,
                        NetWorkParams.deleteCommentOfProduct());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mLiveDataDeleteComment.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

   /* public void fetchTotalItemsSalesItemsAsync() {
        Call<SalesReport> call =
                mAPIServiceProduct.sales(NetWorkParams.getTotalItemsSalesProducts());

        call.enqueue(new Callback<SalesReport>() {
            @Override
            public void onResponse(Call<SalesReport> call, Response<SalesReport> response) {
                SalesReport salesReport = response.body();

                mSalesReportMutableLiveData.postValue(salesReport);
            }

            @Override
            public void onFailure(Call<SalesReport> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }*/
}
