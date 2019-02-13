package com.jt.letsgo.service;

import com.jt.letsgo.dto.Game;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.letsgo.dao.NewGameDao;
import java.time.LocalDateTime;

@Service
public class NewGameService {
    
    @Autowired
    NewGameDao newGameDao;
    
    public Game createNewGame(String username) {
        Game gameToCreate = new Game();
        gameToCreate.setGameLeader(username);
        gameToCreate.setStartTime(LocalDateTime.now());
        return newGameDao.createNewGame(gameToCreate);
    }
    
    public List<Game> getGamesByUsername(String username) {
        return newGameDao.getGamesByUsername(username);
    }
    
    public Game updateGameOver(Game game) {
        return newGameDao.updateGameOver(game);
    }
    
    public Game getGameById(int gameId){
        return newGameDao.getGameById(gameId);
    }
}
