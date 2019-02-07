
package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Game;
import java.util.List;



public interface NewGameDao {
    Game createNewGame(Game game);
    List<Game> getGamesByUsername(String username);
    Game getGameById(int gameId);
    Game updateGameOver(Game game);
}
