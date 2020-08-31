package com.example.quizapplicaiton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.quizapplicaiton.SetsActivity.category_id;

public class questionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qnCount, qnTimer;
    private Button optn1, optn2, optn3, optn4;
    private List<Questions> questionsList;
    private CountDownTimer countTimer;
    private int user_score;
    int quesNo;
    private FirebaseFirestore firestore;
    private int set_no;
    private Dialog loadingDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Importing Text view from XML file
        question = findViewById(R.id.questions);
        qnCount = findViewById(R.id.qn_no);
        qnTimer =findViewById(R.id.Count);

        //importing buttons from XML file.
        optn1 = findViewById(R.id.optionBtn1);
        optn2 = findViewById(R.id.optionBtn2);
        optn3 = findViewById(R.id.optionBtn3);
        optn4 = findViewById(R.id.optionBtn4);

        optn1.setOnClickListener(this);
        optn2.setOnClickListener(this);
        optn3.setOnClickListener(this);
        optn4.setOnClickListener(this);

        loadingDiag = new Dialog(questionActivity.this);
        loadingDiag.setContentView(R.layout.loading_progress);
        loadingDiag.setCancelable(false);
        loadingDiag.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDiag.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDiag.show();

        set_no = getIntent().getIntExtra("SETNO", 1);

        //fetching question from the database

        firestore = FirebaseFirestore.getInstance();

        getQuestionList();

        user_score = 0;

    }

    // Gets the value from the Array list.
    private void getQuestionList(){
        questionsList = new ArrayList<>();
        firestore.collection("Quiz").document("CAT" + String.valueOf(category_id))
                .collection("SET"+ String.valueOf(set_no))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();

                    //Log.d("logggggggggggggg",String.valueOf(questions.size()));
                    for (QueryDocumentSnapshot doc : questions){
                        questionsList.add(new Questions(doc.getString("Question"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("Answer"))
                                ));
                    }
                    setQuestion();

                }
                else {

                    Toast.makeText( questionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
                loadingDiag.cancel();
            }
        });

    }

    //This method will set the value in Array list.
    private void setQuestion(){
        qnTimer.setText(String.valueOf(10));

        question.setText(questionsList.get(0).getQuestion());
        optn1.setText(questionsList.get(0).getOptnA());
        optn2.setText(questionsList.get(0).getOptnB());
        optn3.setText(questionsList.get(0).getOptnC());
        optn4.setText(questionsList.get(0).getOptnD());

        qnCount.setText(String.valueOf(1) + "/" + String.valueOf(questionsList.size()));

       startTimer();

        quesNo = 0;
    }

    //Fucntionality of the timer/
    private void startTimer(){
         countTimer = new CountDownTimer(11000, 1000) {
             @Override
             public void onTick(long millisUntilFinished) {
                qnTimer.setText(String.valueOf(millisUntilFinished / 1000));
             }

             @Override
             public void onFinish() {
                changeQuestion();
             }
        };
        countTimer.start();
    }


    @Override
    public void onClick(View v) {

        int selectedOptn = 0;

        switch (v.getId()){
            case R.id.optionBtn1:
                selectedOptn = 1;
                break;
            case R.id.optionBtn2:
                selectedOptn = 2;
                break;
            case R.id.optionBtn3:
                selectedOptn = 3;
                break;
            case R.id.optionBtn4:
                selectedOptn = 4;
                break;

            default:
        }

        countTimer.cancel();
        checkAnswer(selectedOptn, v);

    }
    private void checkAnswer(int selectedOptn, View view){
        //checking the user chose answer is correct or not
        if (selectedOptn == questionsList.get(quesNo).getCorrectAnswer()) {
            //Right answer

            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            user_score++;
        }else{
            //Wrong Answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionsList.get(quesNo).getCorrectAnswer()){
                case 1:
                    optn1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    optn2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    optn3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    optn4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 1100);


    }

    // This method will change the question
    private void changeQuestion(){
        if (quesNo < questionsList.size() - 1){

            quesNo++;
            optnAnim(question, 0, 0);
            optnAnim(optn1, 0, 1);
            optnAnim(optn2, 0, 2);
            optnAnim(optn3, 0, 3);
            optnAnim(optn4, 0, 4);

            qnCount.setText(String.valueOf(quesNo + 1 ) + "/" +String.valueOf(questionsList.size()));

            qnTimer.setText(String.valueOf(10));
            startTimer();

        }else{
            //change to the Score activity
            Intent intent = new Intent(questionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(user_score) + "/" + String.valueOf(questionsList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            questionActivity.this.finish();
        }
    }
    private void optnAnim(final View view, final int value, final int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0){
                            switch (viewNum){
                                case 0:
                                    ((TextView)view).setText(questionsList.get(quesNo).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionsList.get(quesNo).getOptnA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionsList.get(quesNo).getOptnB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionsList.get(quesNo).getOptnC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionsList.get(quesNo).getOptnD());
                                    break;
                            }

                            if (viewNum != 0){
                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8C8989")));
                            }

                            optnAnim(view, 1, viewNum);
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countTimer.cancel();
    }
}
