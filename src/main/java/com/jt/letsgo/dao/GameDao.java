
package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Game;
import java.util.List;



public interface GameDao {
    Game createNewGame(Game game);
    List<Game> getGamesByUsername(String username);
    Game getGameById(int gameId);
    Game updateGameOver(Game game);
    Game updateGameStarted(Game game);
    Game updateGame(Game game);
    List<Game> getGamesNotCompleted(String username);
}
