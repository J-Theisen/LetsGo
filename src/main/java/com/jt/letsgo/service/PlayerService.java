package com.jt.letsgo.service;

import com.jt.letsgo.dao.PlayerDao;
import com.jt.letsgo.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerDao dao;
    
    public Player createPlayer(Player player) {
        dao.createPlayer(player);
        return player;
    }
    
    public Player getPlayerByUsername(String username) {
        return dao.getPlayerByUsername(username);
    }
    
    public void updatePlayer(Player player) {
        dao.updatePlayer(player);
    }
}
