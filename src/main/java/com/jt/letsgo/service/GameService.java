package com.jt.letsgo.service;

import com.jt.letsgo.dto.Game;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.jt.letsgo.dao.GameDao;

@Service
public class GameService {
    
    @Autowired
    GameDao gameDao;
    
    public Game createNewGame(String username) {
        Game gameToCreate = new Game();
        gameToCreate.setGameLeader(username);
        gameToCreate.setStartTime(LocalDateTime.now());
        return gameDao.createNewGame(gameToCreate);
    }
    
    public List<Game> getGamesByUsername(String username) {
        return gameDao.getGamesByUsername(username);
    }
    
    public Game updateGameOver(Game game) {
        return gameDao.updateGameOver(game);
    }
    
    public Game getGameById(int gameId){
        return gameDao.getGameById(gameId);
    }
    
    public Game updateGameStarted(Game game){
        return gameDao.updateGameStarted(game);
    }
    
    public Game updateGame(Game game){
        return gameDao.updateGame(game);
    }
}
