package com.danielhritcu.mathquiz.models;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.danielhritcu.mathquiz.BR;

import java.text.DecimalFormat;
import java.util.Random;

public class GameState extends BaseObservable {
    private String mPlayerName;
    private int mPlayerQuizCount;
    private int mPlayerRightAnswers;

    private Quiz mQuiz;
    private static Quiz mNextQuiz;
    private Results mResults;

    public GameState() {
        generateNextQuiz();
        getNewQuiz();
        mResults = new Results();
    }

    public void getNewQuiz() {
        setQuiz(mNextQuiz);
        generateNextQuiz();
    }

    @Bindable
    public int getPlayerQuizCount(){
        return mPlayerQuizCount;
    }

    @Bindable
    public Quiz getQuiz() {
        return mQuiz;
    }

    public void setQuiz(Quiz mQuiz) {
        this.mQuiz = mQuiz;
        notifyPropertyChanged(BR.quiz);
    }

    public Results getResults() {
        return mResults;
    }

    @Bindable
    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
        notifyPropertyChanged(BR.playerName);
    }

    @SuppressLint("DefaultLocale")
    public String getPlayerScore() {
        int score = (mPlayerRightAnswers / mPlayerQuizCount) * 100;
        return score + "%";
    }

    public void addQuizToResults() {
            mPlayerQuizCount++;
            notifyPropertyChanged(BR.playerQuizCount);

            if (mQuiz.isCorrect()) {
                mPlayerRightAnswers++;
            }

            mResults.addQuizToResults(mQuiz);
    }

    @SuppressLint("DefaultLocale")
    private void generateNextQuiz(){
        int MAX_NUMBER = 25;
        int firstNumber, secondNumber;
        String question;
        double solution;
        String mathOperators = "+-*÷";
        String questionFormat = "%d  %c  %d";

        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");

        //get operator
        char mathOperator = mathOperators.charAt(random.nextInt(mathOperators.length()));

        //get numbers
        firstNumber = random.nextInt(MAX_NUMBER * 2) - MAX_NUMBER;
        secondNumber = random.nextInt(MAX_NUMBER * 2) - MAX_NUMBER;

        //calculate solution
        switch (mathOperator) {
            case '-':
                solution = firstNumber - secondNumber;
                break;
            case '*':
                solution = firstNumber * secondNumber;
                break;
            case '÷':
                    if (firstNumber % secondNumber != 0) {
                        secondNumber = (int) (Math.floor((secondNumber + (double) firstNumber / 2) /
                                firstNumber) * firstNumber);

                    }
                    if(secondNumber == 0){
                        secondNumber=1;
                    }
                solution = (double) firstNumber / (double) secondNumber;
                break;
            default:
            case '+':
                solution = firstNumber + secondNumber;
                break;
        }


        if(secondNumber < 0){
            questionFormat = "%d  %c  (%d)";
        }

        question = String.format(questionFormat, firstNumber, mathOperator, secondNumber);

        //store new quiz
        mNextQuiz = new Quiz(question, decimalFormat.format(solution));
    }


}

