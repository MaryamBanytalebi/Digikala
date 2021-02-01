package org.maktab.digikala.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.maktab.digikala.model.BillingAddress;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.ShippingAddress;

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

        JsonObject billing_address = bodyObject.getAsJsonObject("billing");
        String first_name_billing_address = billing_address.get("first_name").getAsString();
        String last_name_billing_address = billing_address.get("last_name").getAsString();
        String company_billing_address = billing_address.get("company").getAsString();
        String address_1_billing_address = billing_address.get("address_1").getAsString();
        String address_2_billing_address = billing_address.get("address_2").getAsString();
        String city_billing_address = billing_address.get("city").getAsString();
        String state_billing_address = billing_address.get("state").getAsString();
        String postcode_billing_address = billing_address.get("postcode").getAsString();
        String country_billing_address = billing_address.get("country").getAsString();
        String email_billing_address = billing_address.get("email").getAsString();
        String phone_billing_address = billing_address.get("phone").getAsString();

        BillingAddress billingAddresses = new BillingAddress(first_name_billing_address,last_name_billing_address,
                company_billing_address,address_1_billing_address,address_2_billing_address,
                city_billing_address,state_billing_address,postcode_billing_address,country_billing_address,
                email_billing_address,phone_billing_address);

        JsonObject shipping_address = bodyObject.getAsJsonObject("shipping");
        String first_name_shipping_address = shipping_address.get("first_name").getAsString();
        String last_name_shipping_address = shipping_address.get("last_name").getAsString();
        String company_shipping_address = shipping_address.get("company").getAsString();
        String address_1_shipping_address = shipping_address.get("address_1").getAsString();
        String address_2_shipping_address = shipping_address.get("address_2").getAsString();
        String city_shipping_address = shipping_address.get("city").getAsString();
        String state_shipping_address = shipping_address.get("state").getAsString();
        String postcode_shipping_address = shipping_address.get("postcode").getAsString();
        String country_shipping_address = shipping_address.get("country").getAsString();

        ShippingAddress shippingAddresses = new ShippingAddress(first_name_shipping_address,last_name_shipping_address,
                company_shipping_address,address_1_shipping_address,address_2_shipping_address,
                city_shipping_address,state_shipping_address,postcode_shipping_address,country_shipping_address);


        customer = new Customer(id,email,first_name,last_name,username,billingAddresses,shippingAddresses);


        return customer;
    }
}
