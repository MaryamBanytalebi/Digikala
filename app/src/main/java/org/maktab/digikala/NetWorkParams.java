package org.maktab.digikala;

import android.net.Uri;

import org.maktab.digikala.model.Images;

import java.util.HashMap;
import java.util.Map;

public class NetWorkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_a4f339d5c040a41c47ae982c1008bd9a6d61de56";
    public static final String CONSUMER_SECRET = "cs_74c2f0d6cc6f9bcf6538b264a6ab515a1afec16e";

    public static final String FILTER_ORDER = "filter[order]";
    public static final String RATING_COUNT = "crating count";
    public static final String DESC = "DESC";
    public static final String FIELDS = "fields";
    public static final String AVERAGE_RATING = "average rating";
    public static final String CREATED_AT = "created_at";
    public static final String PARENT_CATEGORY = "0";
    public static final String PARENT_OF_CATEGORY = "parent";
    public static final String CATEGORY = "category";


    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static final Map<String,String> getProduct(String page){

        Map<String,String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put("page",page);

        return products;
    }

    public static Map<String, String> getMostVisitedProducts() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(FILTER_ORDER, DESC);
        products.put(FIELDS, RATING_COUNT);

        return products;
    }

    public static Map<String,String> getHighestScoreProduct(){
        Map<String,String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(FILTER_ORDER, DESC);
        products.put(FIELDS, AVERAGE_RATING);

        return products;
    }

    public static Map<String, String> getLatestProducts() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(FILTER_ORDER, DESC);
        products.put(FIELDS, CREATED_AT);

        return products;
    }

    public static Map<String, String> getProductsWithParentId(String parentName) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(CATEGORY,parentName);

        return products;
    }
    public static Map<String, String> getCategories() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PARENT_OF_CATEGORY, PARENT_CATEGORY);

        return products;
    }

    public static Map<String, String> subCategories(String parentId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PARENT_OF_CATEGORY, parentId);

        return products;
    }

    public static Uri getPhotoPageUri(Images images) {
        Uri uri = Uri.parse(images.getSrc())
                .buildUpon()
                .build();

        return uri;
    }
}
