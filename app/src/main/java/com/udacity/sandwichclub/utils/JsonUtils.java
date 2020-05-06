package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject ObjectName = sandwichJSON.getJSONObject("name");

           String nameOfFood        =  ObjectName.getString("mainName");
           String placeOfOrigin     =  sandwichJSON.getString("placeOfOrigin");
           String descriptionOfFood = sandwichJSON.getString("description");
           String image             =  sandwichJSON.getString("image");

            JSONArray ingredientsJson = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i=0; i<ingredientsJson.length();i++){
                ingredients.add(ingredientsJson.getString(i));
            }

            JSONArray alsoKnownAsJson = sandwichJSON.getJSONObject("name").getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i=0; i<alsoKnownAsJson.length();i++){
                alsoKnownAs.add(alsoKnownAsJson.getString(i));
            }

            return  new Sandwich(nameOfFood,alsoKnownAs,placeOfOrigin,descriptionOfFood,image,ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
