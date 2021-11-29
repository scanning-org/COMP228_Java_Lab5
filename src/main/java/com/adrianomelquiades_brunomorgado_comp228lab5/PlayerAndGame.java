package com.adrianomelquiades_brunomorgado_comp228lab5;

public class PlayerAndGame {

    private int player_game_id;
    private int game_id;
    private int player_id;
    private String playing_date;
    private int score;


    //Constructor

    public PlayerAndGame(){}
    public PlayerAndGame(int player_game_id, int game_id, int player_id, String playing_date, int score) {
        this.player_game_id = player_game_id;
        this.game_id = game_id;
        this.player_id = player_id;
        this.playing_date = playing_date;
        this.score = score;
    }

    public int getPlayer_game_id() {
        return player_game_id;
    }

    public void setPlayer_game_id(int player_game_id) {
        this.player_game_id = player_game_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlaying_date() {
        return playing_date;
    }

    public void setPlaying_date(String playing_date) {
        this.playing_date = playing_date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
