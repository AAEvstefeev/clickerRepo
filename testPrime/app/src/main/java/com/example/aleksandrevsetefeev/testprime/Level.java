package com.example.aleksandrevsetefeev.testprime;


public class Level {
    private int scorePerClick;
    private int scoreToNextLevel;

    public Level(int _scorePerClick, int _scoreToNextLevel) {
        scorePerClick = _scorePerClick;
        scoreToNextLevel = _scoreToNextLevel;
    }

    public int getScoreToNextLevel() {
        return scoreToNextLevel;
    }

    public int getScorePerClick() {
        return scorePerClick;
    }
}
