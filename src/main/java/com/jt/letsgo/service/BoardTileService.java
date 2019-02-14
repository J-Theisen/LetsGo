
package com.jt.letsgo.service;

import com.jt.letsgo.dao.BoardTileDao;
import com.jt.letsgo.dto.BoardTile;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardTileService {
 
    @Autowired
    BoardTileDao btDao;
    
    private int numTiles = 12;
    
    public List<BoardTile> createBoardTilesForGame(int gameId){
        BoardTile bt = new BoardTile();;
        Random random = new Random();
        for(int i = 1; i <= numTiles; i++){
           int randomNum = random.nextInt(99)+1;
           bt.setBoardTileId(i);
           bt.setBoardId(gameId);
           if(randomNum <= 50){
               bt.setTileType("BLUE");
           } else if(randomNum >= 60 && randomNum <= 100){
               bt.setTileType("RED");
           } //else {
               //bt.setTileType("GREEN");
          // }
           btDao.createBoardTile(bt);
        }
        return btDao.getBoardTilesForGame(gameId);
    }
    
    public BoardTile getBoardTile(int gameId, int boardTileId){
        return btDao.getBoardTile(gameId, boardTileId);
    }
    
    public List<BoardTile> getBoardTilesForGame(int gameId){
        return btDao.getBoardTilesForGame(gameId);
    }
    
}
