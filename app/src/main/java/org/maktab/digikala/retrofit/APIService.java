package org.maktab.digikala.retrofit;

import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.model.SalesReport;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<List<Product>> topSellers(@QueryMap Map<String, String> options);

    @GET("reports/sales")
    Call<SalesReport> sales(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("customers")
    Call<Customer> customer(@Field("email") String email,
                            @Field("first_name") String first_name, @Field("last_name") String last_name,
                            @Field("username") String username,
                            @QueryMap Map<String, String> options);


}
