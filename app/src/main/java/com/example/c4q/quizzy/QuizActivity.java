package com.example.c4q.quizzy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Stack;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int QUIZ_ACTIVITY = 111;
    private static final int CHEAT_REQUEST = 222;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private Button cheatButton;
    private TextView quizTakerName;
    private TextView questionTextView;
    private String titleString;
    private Random random;
    private int randomNumber;
    private Stack<Integer> randomIndices;

    public Question[] questionBank;
    private int currentIndex;

    public void initializeQuestions() {
        questionBank = new Question[]{
                new Question(R.string.question_static, false),
                new Question(R.string.question_private_class, true),
                new Question(R.string.question_method, false),
                new Question(R.string.question_method_2, false),
                new Question(R.string.question_one, true),
                new Question(R.string.question_two, false),
                new Question(R.string.question_three, true)
        };
        updateQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        random = new Random();
        randomIndices = new Stack<>();

        initializeViews(); //find view by id methods
        titleString = getResources().getString(R.string.quiz_for) + getIntent().getStringExtra("name") + " !";
        quizTakerName.setText(titleString);

        initializeQuestions(); // loads Question objects into array of questions called questionBank
        randomNumber = random.nextInt(questionBank.length) + 1;

        initializeListeners(); //sets onClickListeners for buttton views.
    }


    public void initializeViews() {
        trueButton = (Button) findViewById(R.id.true_btn);
        falseButton = (Button) findViewById(R.id.false_btn);
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        nextButton = (Button) findViewById(R.id.next_btn);
        prevButton = (Button)findViewById(R.id.prev_btn);
        quizTakerName = (TextView) findViewById(R.id.quizzer_name);
        cheatButton = (Button) findViewById(R.id.cheat_btn);
        resetButtonColors();
    }


    public void initializeListeners(){
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        cheatButton.setOnClickListener(this);
    }


    public void resetButtonColors(){
        trueButton.setBackgroundResource(android.R.drawable.btn_default);
        falseButton.setBackgroundResource(android.R.drawable.btn_default);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fixme - implement restartQuiz and add a way to save the quiz taker's score
        switch (item.getItemId()) {
            case R.id.restart_quiz_action:
            case R.id.save_score:
                Toast.makeText(this, "No implementation found. Implement the restartQuiz method", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateQuestion() {
        currentIndex = random.nextInt(questionBank.length);
        if(currentIndex >= 0 && currentIndex < questionBank.length ){
            Question currentQuestion = questionBank[currentIndex];
            int textResId = currentQuestion.getTextResId();
            questionTextView.setText(textResId);
        }else{
            currentIndex = 0;
            Toast.makeText(this, "Out of bounds Exception. Resetting index to Zero", Toast.LENGTH_SHORT).show();
        }
    }




    //fixme
    public void restartQuiz() {
        Toast.makeText(this, "No implementation found. Implement the restartQuiz method", Toast.LENGTH_LONG).show();
    }

    public Question getCurrentQuestion() {
        return questionBank[currentIndex];
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode,resultCode,data);
        // Check which request we're responding to
        if (requestCode == CHEAT_REQUEST) {
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

    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override  //overriding on click method of OnClickListener interface.
    public void onClick(View v) {

        resetButtonColors();
        switch (v.getId()) {
            case R.id.true_btn:
            case R.id.false_btn:
                Question question = getCurrentQuestion();
                if (question.isAnswerTrue()) {
                    falseButton.setBackgroundResource(R.color.red);
                    trueButton.setBackgroundResource(R.color.green);
                    randomIndices.push(currentIndex);
                } else {
                    falseButton.setBackgroundResource(R.color.green);
                    trueButton.setBackgroundResource(R.color.red);
                    randomIndices.push(currentIndex);
                }
                break;
            case R.id.prev_btn:
                //TODO why does this only go back once?
                if(randomIndices.size() != 0){
                    questionTextView.setText(questionBank[randomIndices.pop()].getTextResId());
                }
                break;
            case R.id.next_btn:
                updateQuestion();
                break;
            case R.id.cheat_btn:
                Toast.makeText(this,"Showing cheat mode",Toast.LENGTH_SHORT).show();
                Intent cheater = new Intent (this, CheatActivity.class);

                //add extras before going into the CheatActivity class

                Question currentQuestion = questionBank[currentIndex];
                String questionsStr = getResources().getString(currentQuestion.getTextResId());
                //cheater.putExtra("questions obj", currentQuestion);
//                cheater.putExtra("CURRENT QUESTION",questionsStr);

                // pass in the answer
                Boolean answer = currentQuestion.isAnswerTrue();
//                cheater.putExtra("CURRENT ANSWER", answer);

                // pass in the index
//                int index = currentIndex;
                cheater.putExtra(questionsStr, answer);
                cheater.putExtra("CURRENT INDEX", currentIndex);

                //startActivity(cheater);
                startActivityForResult(cheater,QUIZ_ACTIVITY);
                break;
        }
    }
}
