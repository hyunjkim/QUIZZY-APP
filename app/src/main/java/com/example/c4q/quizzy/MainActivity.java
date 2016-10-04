package com.example.c4q.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Hyun on 9/28/16.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button mainbtn;
    TextView text;
    EditText edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainbtn = (Button)findViewById(R.id.submit_btn);
        text=(TextView)findViewById(R.id.quiz_prompt);
        edit=(EditText)findViewById(R.id.name_edit_text);
        mainbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, QuizActivity.class);
        Log.d("onClick","click");
        String strName = edit.getText().toString();
        i.putExtra("name", strName);
        startActivity(i);
    }
}
