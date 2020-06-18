package com.danielhritcu.mathquiz.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Results extends BaseObservable {
    private List<Quiz> mResults = new ArrayList<>();
    private List<Quiz> mResultsList;
    private boolean showAllResults = true;
    private boolean showRightResults;
    private boolean showWrongResults;
    private boolean showSortedAscendingResults;
    private boolean showSortedDescendingResults;

    public void addQuizToResults(Quiz quiz){
        mResults.add(quiz);
    }

    @Bindable
    public List<Quiz> getResultsList(){
        if(showRightResults){
            mResultsList = getRight();
        }
        else if(showWrongResults){
            mResultsList = getWrong();
        }
        else if(showSortedAscendingResults){
            mResultsList = getSortedAscending();
        }
        else if(showSortedDescendingResults){
            mResultsList = getSortedDescending();
        }
        else{
            mResultsList = mResults;
        }

        return mResultsList;
    }

    public void setResultsList(List<Quiz> resultsList){
        mResultsList = resultsList;
    }

    public boolean isShowAllResults() {
        return showAllResults;
    }

    public void setShowAllResults(boolean showAllResults) {
        this.showAllResults = showAllResults;
    }

    @Bindable
    public boolean isShowRightResults() {
        return showRightResults;
    }

    public void setShowRightResults(boolean showRightResults) {
        this.showRightResults = showRightResults;
    }

    @Bindable
    public boolean isShowWrongResults() {
        return showWrongResults;
    }

    public void setShowWrongResults(boolean showWrongResults) {
        this.showWrongResults = showWrongResults;
    }

    @Bindable
    public boolean isShowSortedAscendingResults() {
        return showSortedAscendingResults;
    }

    public void setShowSortedAscendingResults(boolean showSortedAscendingResults) {
        this.showSortedAscendingResults = showSortedAscendingResults;
    }

    @Bindable
    public boolean isShowSortedDescendingResults() {
        return showSortedDescendingResults;
    }

    public void setShowSortedDescendingResults(boolean showSortedDescendingResults) {
        this.showSortedDescendingResults = showSortedDescendingResults;

    }

    private List<Quiz> getRight(){
        List<Quiz> results;
        results = mResults.stream()
                .filter(Quiz::isCorrect)
                .collect(Collectors.toList());

        return results;
    }

    private List<Quiz> getWrong(){
        List<Quiz> results;
        results = mResults.stream()
                .filter(quiz -> !quiz.isCorrect())
                .collect(Collectors.toList());

        return results;
    }

    private List<Quiz> getSortedAscending(){
        List<Quiz> results = mResults;
        Collections
                .sort(results, (quiz1, quiz2) ->
                        Boolean.compare(quiz2.isCorrect(),quiz1.isCorrect()));

        return results;
    }

    private List<Quiz> getSortedDescending(){
        List<Quiz> results = mResults;
        Collections
                .sort(results, (quiz1, quiz2) ->
                        Boolean.compare(quiz1.isCorrect(),quiz2.isCorrect()));

        return results;
    }
}
