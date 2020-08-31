package com.example.quizapplicaiton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetsActivity extends AppCompatActivity {

    private GridView sets_grid;
    private FirebaseFirestore firestore;

    public static int category_id;
    private Dialog loadingDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //To get the category name and display intent name in sets activity.
        String title = getIntent().getStringExtra("title");
        category_id = getIntent().getIntExtra("CATEGORY_ID",1);
        getSupportActionBar().setTitle(title);


        sets_grid = findViewById(R.id.sets_gridview);

        loadingDiag = new Dialog(SetsActivity.this);
        loadingDiag.setContentView(R.layout.loading_progress);
        loadingDiag.setCancelable(false);
        loadingDiag.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDiag.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDiag.show();

        //Fetching data from the firestore
        firestore = FirebaseFirestore.getInstance();

        loadSets();

    }

    public void loadSets(){
        //Fetching collection from firebase

        firestore.collection("Quiz").document("CAT"+(category_id))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){
                        long sets = (long)doc.get("Sets");

                        //adding view adapter in sets activity
                        setsAdapter adapter = new setsAdapter((int)sets);
                        sets_grid.setAdapter(adapter);


                    }
                    else {
                        Toast.makeText(SetsActivity.this, "No CAT Document exists", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {

                    Toast.makeText(SetsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
                loadingDiag.cancel();
            }
        });
    }

    //back button functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
