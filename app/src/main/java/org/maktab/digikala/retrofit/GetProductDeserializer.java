package org.maktab.digikala.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.maktab.digikala.model.Images;
import org.maktab.digikala.model.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetProductDeserializer implements JsonDeserializer<Product> {


    @Override
    public Product deserialize(JsonElement json,
                               Type typeOfT,
                               JsonDeserializationContext context) throws JsonParseException {
        Product product;

        JsonObject bodyObject = json.getAsJsonObject();
        String title = bodyObject.get("name").getAsString();
        int id = bodyObject.get("id").getAsInt();
        String price = bodyObject.get("price").getAsString();
        String regular_price = bodyObject.get("regular_price").getAsString();
        String sale_price = bodyObject.get("sale_price").getAsString();
           /* String sale_price_dates_from = productObject.get("date_on_sale_from").getAsString();
            String sale_price_dates_to = productObject.get("date_on_sale_to").getAsString();*/
        String weight = bodyObject.get("weight").getAsString();
        JsonObject dimensions = bodyObject.getAsJsonObject("dimensions");
        String length = dimensions.get("length").getAsString();
        String width = dimensions.get("width").getAsString();
        String height = dimensions.get("height").getAsString();
        String description = bodyObject.get("description").getAsString();
        String short_description = bodyObject.get("short_description").getAsString();
        String average_rating = bodyObject.get("average_rating").getAsString();
        int rating_count = bodyObject.get("rating_count").getAsInt();
        int total_sales = bodyObject.get("total_sales").getAsInt();
        JsonArray photoArray = bodyObject.get("images").getAsJsonArray();
        List<Images> imagesArray = new ArrayList<>();
        for (int j = 0; j < photoArray.size(); j++) {
            JsonObject photoObject = photoArray.get(j).getAsJsonObject();
            int imageId = photoObject.get("id").getAsInt();
            String url = photoObject.get("src").getAsString();
            Images images = new Images(imageId, url);
            imagesArray.add(images);
        }

        product = new Product(id,title,price,regular_price,sale_price,weight,length,width,height,description,short_description,
                average_rating,rating_count, total_sales, imagesArray);


        return product;
    }
}
