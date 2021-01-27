package org.maktab.digikala.retrofit;

import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("products")
    Call<List<Product>> Products(@QueryMap Map<String,String> options);

   /* @GET("products")
    Call<List<Product>> getProductWithParentId(@QueryMap Map<String,String> options);
*/
    @GET("products/categories")
    Call<List<ProductCategory>> productCategory(@QueryMap Map<String,String> options);

    /*@GET("products/categories")
    Call<List<ProductCategory>> subProductCategory(@QueryMap Map<String,String> options);*/

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id,@QueryMap Map<String,String> options);

    @GET("reports/sales/top_sellers")
    Call<List<Product>> topSellers(@QueryMap Map<String,String> options);


}
