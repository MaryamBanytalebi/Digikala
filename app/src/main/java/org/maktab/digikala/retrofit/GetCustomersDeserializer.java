package org.maktab.digikala.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.maktab.digikala.model.Customer;

import java.lang.reflect.Type;

public class GetCustomersDeserializer implements JsonDeserializer<Customer> {
    @Override
    public Customer deserialize(JsonElement json,
                                Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
        Customer customer;

        JsonObject bodyObject = json.getAsJsonObject();
        JsonObject customerObject = bodyObject.getAsJsonObject("customer");
        int id = customerObject.get("id").getAsInt();
        String email = customerObject.get("email").getAsString();
        String first_name = customerObject.get("first_name").getAsString();
        String last_name = customerObject.get("last_name").getAsString();
        String username = customerObject.get("username").getAsString();

        customer = new Customer(id,email,first_name,last_name,username);

        return customer;
    }
}
