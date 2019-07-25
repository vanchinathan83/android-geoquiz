package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    public static final String IS_ANSWER_SHOWN = "is_answer_shown";
    public static final String CHEAT_ACTIVITY = "CheatActivity";

    private boolean mIsAnswerTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mShowedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if(savedInstanceState != null){
            mShowedAnswer = savedInstanceState.getBoolean(IS_ANSWER_SHOWN);
            if(mShowedAnswer) {
                setAnswerShownResult(mShowedAnswer);
            }
        }
        // getIntent() will get the Intent passed by the caller of this activity
        mIsAnswerTrue = getIntent().getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton =  (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAnswerTextView.setText(mIsAnswerTrue? R.string.true_button: R.string.false_button);
                mShowedAnswer = true;
                setAnswerShownResult(mShowedAnswer);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(CHEAT_ACTIVITY, mShowedAnswer? "true": "false");
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(IS_ANSWER_SHOWN, mShowedAnswer);
    }
    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        if(isAnswerShown){
            Log.d(CHEAT_ACTIVITY,"AnswerShown: Calling SetResult");
        }
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
