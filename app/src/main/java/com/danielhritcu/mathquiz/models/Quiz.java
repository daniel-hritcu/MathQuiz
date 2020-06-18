package com.danielhritcu.mathquiz.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Quiz extends BaseObservable {
    private String question;
    private double answer;
    private double solution;
    private boolean correct;

    public Quiz(String question, double solution) {
        this.question = question;
        this.solution = solution;
    }

    @Bindable
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Bindable
    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    @Bindable
    public double getSolution() {
        return solution;
    }

    public void setSolution(double solution) {
        this.solution = solution;
    }

    @Bindable
    public boolean isCorrect() {
        return answer == solution;
    }
}