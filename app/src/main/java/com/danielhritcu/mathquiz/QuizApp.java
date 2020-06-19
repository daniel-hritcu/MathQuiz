package com.danielhritcu.mathquiz;

import android.app.Application;

import com.danielhritcu.mathquiz.models.GameState;

public class QuizApp extends Application {
    private GameState mGameState;

    @Override
    public void onCreate() {
        super.onCreate();

        //Init game
        mGameState = new GameState();
    }

    public GameState getGameState(){
        return mGameState;
    }
}