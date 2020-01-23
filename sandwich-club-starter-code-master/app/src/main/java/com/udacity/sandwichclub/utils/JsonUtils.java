package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich(  );
        //get the sandwich JSON split in right way. JSONDetails will have details about the sandwich
        // like description, place of origin, ingredients, image url. Whereas Sandwich name with have
        // main name of the item and also known as values.
        JSONObject sandwichDetails = new JSONObject( json );
        JSONObject sandwichName =  sandwichDetails.getJSONObject( "name" );

        List<String> akaList = new ArrayList<>(  );
        List<String> ingredientsList = new ArrayList<>(  );

        JSONArray alsoKnownAs = sandwichName.getJSONArray( "alsoKnownAs" );
        JSONArray ingredients = sandwichDetails.getJSONArray( "ingredients" );

        for(int i=0;i< alsoKnownAs.length();i++)
            akaList.add( alsoKnownAs.getString( i ) );

        for(int i=0; i<ingredients.length(); i++)
            ingredientsList.add( ingredients.getString( i ) );

        //set the values of sandwich from JSON into the sandwich object that is to be returned.
        sandwich.setAlsoKnownAs( akaList );
        sandwich.setDescription( sandwichDetails.getString( "description" ) );
        sandwich.setIngredients( ingredientsList );
        sandwich.setPlaceOfOrigin( sandwichDetails.getString( "placeOfOrigin" ) );
        sandwich.setMainName( sandwichName.getString( "mainName" ) );
        sandwich.setImage( sandwichDetails.getString( "image" ) );

        return sandwich;
    }
}
