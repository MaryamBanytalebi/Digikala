package org.maktab.digikala.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.maktab.digikala.model.ProductCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetCategoryProductDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json,
                              Type typeOfT,
                              JsonDeserializationContext context) throws JsonParseException {

        List<ProductCategory> items = new ArrayList<>();

        JsonArray bodyArray = json.getAsJsonArray();
        for (int i = 0; i < bodyArray.size(); i++) {
            JsonObject productCategoryObject = bodyArray.get(i).getAsJsonObject();
            int id = productCategoryObject.get("id").getAsInt();
            String name = productCategoryObject.get("name").getAsString();
            int parent = productCategoryObject.get("parent").getAsInt();
            String description = productCategoryObject.get("description").getAsString();
            JsonObject image = productCategoryObject.getAsJsonObject("image");
            String imageSrc = image.get("src").getAsString();
            int count = productCategoryObject.get("count").getAsInt();


            ProductCategory item = new ProductCategory(id, name, parent, description, imageSrc, count);
            items.add(item);
        }

        return items;
    }
}
