package com.example.quizapplicaiton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {
    private TextView appName;

    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appName = findViewById(R.id.app_Name);

        // TO add font in the application
        //Typeface typeface = ResourcesCompat.getFont()

        Animation anim = AnimationUtils.loadAnimation( this, R.anim.myanim);
        appName.setAnimation(anim);

        //Fetching data from fire store
        firestore = FirebaseFirestore.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //sleep(3000);

                loadData();

            }
        }).start();
    }

    private void loadData(){

        catList.clear();

        //Fetching collection from firebase

        firestore.collection("Quiz").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){
                        long count = (long)doc.get("Count");

                        for(int i = 1; i <= count; i++){
                            String catName = doc.getString("CAT"+ String.valueOf(i));
                            catList.add(catName);
                        }

                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    }
                    else {
                        Toast.makeText(SplashScreen.this, "No file exists", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {

                    Toast.makeText(SplashScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
