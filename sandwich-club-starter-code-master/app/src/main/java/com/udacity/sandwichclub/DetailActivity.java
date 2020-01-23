package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //declare the views for use later
    private ImageView ivSandwichImage;
    private TextView tvPlaceOfOrigin;
    private TextView tvDescription;
    private TextView tvAlsoKnownAs;
    private TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //initialize the views
        ivSandwichImage = findViewById(R.id.image_iv);
        tvAlsoKnownAs = findViewById( R.id.also_known_tv );
        tvDescription = findViewById( R.id.description_tv );
        tvIngredients = findViewById( R.id.ingredients_tv );
        tvPlaceOfOrigin = findViewById( R.id.origin_tv );

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        //get the JSON for the desired position of the sandwiches array. put in try catch to handle exceptions
        try{
            sandwich = JsonUtils.parseSandwichJson( json );
        }catch (Exception e){
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
    //populate the UI using the method and picasso api
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ivSandwichImage);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI( Sandwich sandwich ) {
        tvPlaceOfOrigin.setText( sandwich.getPlaceOfOrigin() );
        tvIngredients.setText(TextUtils.join("," , sandwich.getIngredients() ));
        tvDescription.setText( sandwich.getDescription() );
        tvAlsoKnownAs.setText( TextUtils.join( "," , sandwich.getAlsoKnownAs() ));
    }
}
