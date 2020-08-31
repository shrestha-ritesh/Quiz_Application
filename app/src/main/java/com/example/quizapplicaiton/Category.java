package com.example.quizapplicaiton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import static com.example.quizapplicaiton.SplashScreen.catList;


public class Category extends AppCompatActivity {

    private GridView catGridV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catGridV = findViewById(R.id.catGrid);

        //Accessing data from catAdaptor
        catAdaptor adaptor = new catAdaptor(catList);
        catGridV.setAdapter(adaptor);



    }

    // back button functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            Category.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
