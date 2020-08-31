package com.example.quizapplicaiton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView score;
    private Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = findViewById(R.id.quiz_Score);
        done = findViewById(R.id.done_btn);

        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);

        // ActionListner for done button
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoreActivity.this, Category.class);
                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();
            }
        });
    }
}