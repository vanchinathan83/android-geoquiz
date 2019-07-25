package com.bignerdranch.android.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean isAnswerTrue){
        this.mTextResId = textResId;
        this.mAnswerTrue = isAnswerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(final int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(final boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
