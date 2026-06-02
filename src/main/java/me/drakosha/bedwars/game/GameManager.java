package me.drakosha.bedwars.game;

import lombok.Getter;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager instance;
    @Getter
    private List<Game> activeGame = new ArrayList<>();

    private GameManager(){
    }
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }
    public void createGame(int numberMap){
        var game = new Game(numberMap);
        activeGame.add(game);
    }
}
