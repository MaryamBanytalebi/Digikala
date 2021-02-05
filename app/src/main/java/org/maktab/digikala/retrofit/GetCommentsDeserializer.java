package org.maktab.digikala.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.maktab.digikala.model.Comment;

import java.lang.reflect.Type;

public class GetCommentsDeserializer implements JsonDeserializer<Comment> {

    @Override
    public Comment deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        Comment comment;

        JsonObject bodyObject = json.getAsJsonObject();
        int id = bodyObject.get("id").getAsInt();
        String date_created_gmt = bodyObject.get("date_created_gmt").getAsString();
        String reviewer = bodyObject.get("reviewer").getAsString();
        String reviewer_email = bodyObject.get("reviewer_email").getAsString();
        String review = bodyObject.get("review").getAsString();
        int rating = bodyObject.get("rating").getAsInt();
        boolean verified = bodyObject.get("verified").getAsBoolean();


        comment = new Comment(id,date_created_gmt,reviewer,reviewer_email,review,rating,verified);


        return comment;
    }
}