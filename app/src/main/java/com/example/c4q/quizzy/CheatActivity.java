package com.example.c4q.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * I extend QuizActivity because I do not want to redefine the layout + methods
 */

public class CheatActivity extends QuizActivity{

    private static final int PICK_CONTACT_REQUEST = 111 ;
    private Question [] copyOfquestions;
    private Question currentQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // I do not have to set contentview because I extended
        Button button = (Button) findViewById(R.id.cheat_btn);
        copyOfquestions = super.questionBank;
        // I am setting my cheat button invisibile.
        button.setVisibility(View.GONE);
        // I am now going to display the answer
        TextView questionTV = (TextView) findViewById(R.id.question_text_view);
        Intent intent = getIntent();

        int index = intent.getExtras().getInt("CURRENT INDEX");
        currentQuestion = copyOfquestions[index];
        boolean myAnswer = getCurrentQuestion().isAnswerTrue();
        questionTV.setText(currentQuestion.getTextResId() + ":");
        questionTV.append("" + myAnswer);


//        String myQuestion = intent.getExtras().getString("CURRENT QUESTION");
//        boolean myAnswer = intent.getExtras().getBoolean("CURRENT ANSWER");
//        questionTV.setText(myQuestion + ":" + myAnswer);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
//                 The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                Toast.makeText(this, "USER CHEATED", Toast.LENGTH_SHORT).show();
                // Do something with the contact here (bigger example below)
//            }
            }
        }
    }
}