
package com.jt.letsgo.dao;

import com.jt.letsgo.dto.GamePlayer;
import java.util.List;


public interface GamePlayerDao {
    GamePlayer addPlayerToGame(GamePlayer gp);
    GamePlayer getPlayer(int gameId, String playerName);
    List<GamePlayer> getAllPlayersForGame(int gameId);
    GamePlayer updateGamePlayerCurrency(GamePlayer gp);
    GamePlayer updateGamePlayerCharacter(GamePlayer gp);
    GamePlayer updateTurnNumber(GamePlayer gp);
}
