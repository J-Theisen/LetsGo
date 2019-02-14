package com.jt.letsgo.service;

import com.jt.letsgo.dao.GamePlayerDao;
import com.jt.letsgo.dto.Game;
import com.jt.letsgo.dto.GamePlayer;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePlayerService {

    @Autowired
    GamePlayerDao gpDao;

    @Autowired
    GameService gameDao;

    public GamePlayer addPlayerToGame(String username, int gameId) {
        // Check and see if playerName is not empt
        GamePlayer gp = new GamePlayer();
        gp.setPlayerName(username);
        gp.setGameId(gameId);
        return gpDao.addPlayerToGame(gp);
    }

    public GamePlayer getPlayer(int gameId, String playerName) {
        return gpDao.getPlayer(gameId, playerName);
    }

    public List<GamePlayer> getAllPlayersForGame(int gameId) {
        return gpDao.getAllPlayersForGame(gameId);
    }

    public GamePlayer updateGamePlayerCurrency(GamePlayer gp) {
        return gpDao.updateGamePlayerCurrency(gp);
    }

    public GamePlayer updateGamePlayerCharacter(GamePlayer gp) {
        return gpDao.updateGamePlayerCharacter(gp);
    }

    public Game updatePlayerCharactersAndTurnNumber(int gameId, List<GamePlayer> players) {
        Game game = gameDao.getGameById(gameId);
        int isGameStarted = game.isGameStarted();

        if (isGameStarted == 0) {

            int counter = 1;
            int numPlayers = players.size();
            int[] turnNumbers = new int[numPlayers];
            for (GamePlayer gp : players) {
                //Sets the 
                gp.setPlayerCharacter(counter);

                if (counter == 1) {
                    gp.setImageUrl("/images/squirrel.png");
                } else if (counter == 2) {
                    gp.setImageUrl("/images/dog.png");
                } else if (counter == 3) {
                    gp.setImageUrl("/images/cat.png");
                } else {
                    gp.setImageUrl("/images/bird.png");
                }

                gpDao.updateGamePlayerCharacter(gp);
                turnNumbers[counter - 1] = counter;
                counter++;
                
                gameDao.updateGameStarted(game);
            }

            shufflePlayerTurnArray(turnNumbers);

            int arrayCounter = 0;
            for (GamePlayer gp : players) {
                gp.setPlayerTurn(turnNumbers[arrayCounter]);
                gpDao.updateTurnNumber(gp);
                arrayCounter++;
            }
        } 
        return game;
    }
    
    public GamePlayer updateGamePlayer(GamePlayer gp){
        return gpDao.updateGamePlayer(gp);
    }

    public static void shufflePlayerTurnArray(int[] playerTurnArray) {
        int n = playerTurnArray.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(playerTurnArray, i, change);
        }
    }

    private static void swap(int[] playerTurnArray, int i, int change) {
        int helper = playerTurnArray[i];
        playerTurnArray[i] = playerTurnArray[change];
        playerTurnArray[change] = helper;
    }
}
