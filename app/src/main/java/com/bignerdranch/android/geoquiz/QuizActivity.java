package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CHEAT = 0;
    public static final String IS_CHEATER = "is_cheater";
    public static final String NO_OF_CHEATS_LEFT = "no_of_cheats_left";
    private boolean mIsCheater;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private Button mcheatButton;
    private TextView mQuestionTextView;
    private TextView mCheatsLeftText;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private final Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa, false),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private int mNoOfCheatsLeft = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG,"OnCreate(Bundle) called!");
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(IS_CHEATER);
            mNoOfCheatsLeft = savedInstanceState.getInt(NO_OF_CHEATS_LEFT);
            if(mNoOfCheatsLeft == 0){
                mcheatButton.setClickable(false);
            }
        }
        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());

        mCheatsLeftText = findViewById(R.id.cheats_left);
        mCheatsLeftText.setText("Cheats Left : " + mNoOfCheatsLeft);
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                makeToast(true);
                mFalseButton.setClickable(false);
                mTrueButton.setClickable(false);
            }
        });

        mcheatButton = findViewById(R.id.cheat_button);
        mcheatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(mNoOfCheatsLeft != 0) {
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                    //startActivity(intent);
                    // Activity Manager coordinates between the parent and the child activty
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }else {
                    mcheatButton.setClickable(false);
                }
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeToast(false);
                mFalseButton.setClickable(false);
                mTrueButton.setClickable(false);
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mIsCheater = false;
                if(mCurrentIndex < mQuestionBank.length -1) {
                    mCurrentIndex++;
                }else{
                    mCurrentIndex = 0;
                }
                mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
                mFalseButton.setClickable(true);
                mTrueButton.setClickable(true);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if(mIsCheater && mNoOfCheatsLeft > 0){
                mNoOfCheatsLeft--;
                mCheatsLeftText.setText("Cheats Left = " + mNoOfCheatsLeft);
            }else if(mNoOfCheatsLeft == 0){
                mcheatButton.setClickable(false);
            }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called!");
    }

    @Override
    public void onStop(){
       super.onStop();
       Log.d(TAG, "onStop() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy () called");
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(IS_CHEATER, mIsCheater);
        savedInstanceState.putInt(NO_OF_CHEATS_LEFT, mNoOfCheatsLeft);
    }
    private void makeToast(boolean clickedTrue) {
        if(mIsCheater){
            showToast(R.string.judgement_toast);
        }
        if(clickedTrue && mQuestionBank[mCurrentIndex].isAnswerTrue() || !clickedTrue && !mQuestionBank[mCurrentIndex].isAnswerTrue()) {
            showToast(R.string.correct_toast);
        }else {
            showToast(R.string.incorrect_toast);
        }
    }

    private void showToast(final int textResource) {
        Toast correctToast = Toast.makeText(QuizActivity.this, textResource, Toast.LENGTH_SHORT);
        correctToast.setGravity(Gravity.TOP, 0, 100);
        correctToast.show();
    }
}
