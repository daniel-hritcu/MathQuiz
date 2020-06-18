package com.danielhritcu.mathquiz.models;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.Random;

public class Game extends BaseObservable {
    private String mPlayerName;
    private int mPlayerQuizCount;
    private int mPlayerRightAnswers;

    private Results mResults = new Results();

    public Quiz getNewQuiz() {
        return generateQuiz();
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
    }

    @SuppressLint("DefaultLocale")
    public String getPlayerScore() {
        int score = (mPlayerRightAnswers / mPlayerQuizCount) * 100;
        return score + "%";
    }

    public void addQuizToResults(Quiz quiz) {
        mPlayerQuizCount++;

        if (quiz.isCorrect()) {
            mPlayerRightAnswers++;
        }

        mResults.addQuizToResults(quiz);
    }

    @Bindable
    public int getPlayerQuizCount() {
        return mPlayerQuizCount;
    }

    /**
     * Provides a new quiz with a random generated questions
     * and it's solution.
     *
     * @return Quiz
     */
    @SuppressLint("DefaultLocale")
    private Quiz generateQuiz() {
        int MAX_NUMBER = 10;
        int firstNumber, secondNumber;
        String question;
        double solution;
        String mathOperators = "+-*/";
        Random random = new Random();

        //get operator
        char mathOperator = mathOperators.charAt(random.nextInt(mathOperators.length()));

        //get numbers
        firstNumber = random.nextInt(MAX_NUMBER * 2) - MAX_NUMBER;
        secondNumber = random.nextInt(MAX_NUMBER * 2) - MAX_NUMBER;

        //handle division by zero
        if (mathOperator == '/' && secondNumber == 0) {
            if (firstNumber != 0) {
                int tempNumber = firstNumber;
                firstNumber = secondNumber;
                secondNumber = tempNumber;
            } else {
                secondNumber++;
            }
        }

        //calculate solution
        switch (mathOperator) {
            case '-':
                solution = firstNumber - secondNumber;
                break;
            case '*':
                solution = firstNumber * secondNumber;
                break;
            case '/':
                solution = firstNumber * 1.0 / secondNumber;
                break;
            default:
            case '+':
                solution = firstNumber + secondNumber;
                break;
        }

        //format question
        if (secondNumber < 0) {
            question = String.format("%d %c (%d)", firstNumber, mathOperator, secondNumber);
        } else {
            question = String.format("%d %c %d", firstNumber, mathOperator, secondNumber);
        }

        //return new quiz
        return new Quiz(question, solution);
    }
}

