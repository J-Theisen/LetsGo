package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Player;

public interface PlayerDao {
    Player createPlayer(Player player);
    Player getPlayerByUsername(String username);
    void updatePlayer(Player player);
}
