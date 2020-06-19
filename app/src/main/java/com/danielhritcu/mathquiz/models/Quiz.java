package com.danielhritcu.mathquiz.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.danielhritcu.mathquiz.BR;

public class Quiz extends BaseObservable {
    private String question;
    private String answer;
    private String solution;
    private boolean correct;
    private boolean answered;

    public Quiz(String question, String solution) {
        this.question = question;
        this.solution = solution;
        this.answer = "";
    }

    @Bindable
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
        notifyPropertyChanged(BR.question);
    }

    @Bindable
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        notifyPropertyChanged(BR.answer);
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isCorrect() {
        if(isAnswered()){
            return Double.parseDouble(answer) == Double.parseDouble(solution);
        }
        else{
            return false;
        }
    }

    @Bindable
    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
        notifyPropertyChanged(BR.answered);
    }
}