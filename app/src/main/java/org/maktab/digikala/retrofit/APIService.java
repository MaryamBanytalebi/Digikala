package org.maktab.digikala.retrofit;

import org.maktab.digikala.model.Comment;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.model.SalesReport;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<SalesReport>> sales(@QueryMap Map<String, String> options);

    @GET("products/reviews")
    Call<List<Comment>> comments(@QueryMap Map<String, String> options);

    @POST("products/reviews")
    Call<Comment> addComment(@Body Comment comment, @QueryMap Map<String, String> options);

    @GET("products/reviews/{id}")
    Call<Comment> getCommentWithId(@Path("id") int id,@QueryMap Map<String, String> options);

    @PUT("products/reviews/{id}")
    @Headers({ "Content-Type: application/json"})
    Call<Comment> putCommentWithId(@Body Comment comment,@Path("id") int id,@QueryMap Map<String, String> options);

    @DELETE("products/reviews/{id}")
    Call<Comment> deleteCommentWithId(@Path("id") int id,@QueryMap Map<String, String> options);


 @FormUrlEncoded
    @POST("customers")
    Call<Customer> customer(@Field("email") String email,
                            @Field("first_name") String first_name, @Field("last_name") String last_name,
                            @Field("username") String username,
                            @QueryMap Map<String, String> options);


}
