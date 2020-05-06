package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private TextView descriptionTv,placeOfOriginTv,alsoKnownAsTv,ingredientsTv,
            origin_tv_title,also_known_tv_title;
    private Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        descriptionTv = findViewById(R.id.description_tv);
        origin_tv_title = findViewById(R.id.origin_tv_title);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        also_known_tv_title = findViewById(R.id.also_known_tv_title);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION){
            //EXTRA_POSITION not found in Intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null){
            //Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

    }

    private void populateUI() {
        Picasso.get()
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        descriptionTv.setText(sandwich.getDescription());
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            origin_tv_title.setVisibility(View.GONE);
            placeOfOriginTv.setVisibility(View.GONE);
        }
        if (sandwich.getAlsoKnownAs().isEmpty()){
            also_known_tv_title.setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        }else {
            alsoKnownAsTv.setText(TextUtils.join("\n", sandwich.getAlsoKnownAs()));
        }
        ingredientsTv.setText(TextUtils.join("\n", sandwich.getIngredients()));

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
