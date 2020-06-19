package com.danielhritcu.mathquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.danielhritcu.mathquiz.QuizApp;
import com.danielhritcu.mathquiz.databinding.QuizActivityBinding;
import com.danielhritcu.mathquiz.models.GameState;
import com.danielhritcu.mathquiz.R;

import es.dmoral.toasty.Toasty;

//TODO: Refactor
public class QuizActivity extends AppCompatActivity {
    private GameState mGameState;
    private QuizActivityBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.quiz_activity);
        setTextEditActionListener();

        mGameState = ((QuizApp)getApplication()).getGameState();

        mBinding.setGameState(mGameState);
        mBinding.setActivity(this);
    }

    public void setTextEditActionListener(){
        EditText etAnswer = findViewById(R.id.et_answer);
        etAnswer.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(etAnswer.getText().toString().isEmpty()){
                    Toasty.info(this, "Answer can not be empty!", Toast.LENGTH_SHORT, true).show();
                    //do not hide the keyboard
                    return true;
                }
                else {
                    doValidation();
                }
            }
            //hide the keyboard
            return false;
        });
    }

    public String getActivityTitle(){
            return String.format("%s %s",
                    mGameState.getPlayerName(),
                    mGameState.getPlayerScore());
    }

    public void onKeyPadClick(View view, char key){
        String answer = String.valueOf(mGameState.getQuiz().getAnswer());

        if(key == '-' && mGameState.getQuiz().getAnswer().isEmpty()){
            answer+= key;
        }
        else if (key == '.' && canAddDot()){
            answer+= key;
        }
        else if (Character.isDigit(key)){
            answer+= key;
        }

        mGameState.getQuiz().setAnswer(answer);
    }

    public void onGenerateClick(View view){
            newQuiz();
    }

    public void onValidateClick(View view){
        doValidation();
    }

    public void doValidation(){
        mGameState.getQuiz().setAnswered(true);
        mGameState.addQuizToResults();
        getValidateConfirmation();
    }

    public void onClearClick(View view){
        mGameState.getQuiz().setAnswer("");
    }

    public void onScoreClick(View view){
        startActivity(new Intent(this, ResultsActivity.class));
    }

    public void onFinishClick(View view){
        finish();
        System.exit(0);
    }

    private void newQuiz(){
        mGameState.getNewQuiz();
        mGameState.getQuiz().setAnswered(false);
    }

    private boolean canAddDot(){
        if(!mGameState.getQuiz().getAnswer().isEmpty()){
            char lastChar =  mGameState.getQuiz().getAnswer()
                    .charAt(mGameState.getQuiz().getAnswer().length() - 1);
            boolean containsDot = mGameState.getQuiz().getAnswer()
                    .contains(Character.toString('.'));
            return lastChar != '-' && !containsDot;
        }
        return true;
    }

    private void getValidateConfirmation(){
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //set icon based on answer
        builder.setIcon(
                mGameState.getQuiz().isCorrect() ?
                        R.drawable.ic_baseline_thumb_up_alt_24 :
                        R.drawable.ic_baseline_thumb_down_alt_24);

        //set title
        builder.setTitle(mGameState.getQuiz().isCorrect() ? "Correct" : "Incorrect");

        //set message
        builder.setMessage(
                String.format("%s  =  %s",
                        mGameState.getQuiz().getQuestion(),
                        mGameState.getQuiz().getSolution()));

        // set button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.show();

        // set message style
        TextView messageView = dialog.findViewById(android.R.id.message);
        if (messageView != null) {
            messageView.setGravity(Gravity.CENTER);
            messageView.setTextSize(28);
        }

        dialog.show();
    }

}