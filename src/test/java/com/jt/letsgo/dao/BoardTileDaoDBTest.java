
package com.jt.letsgo.dao;

import com.jt.letsgo.dto.BoardTile;
import com.jt.letsgo.dto.Game;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardTileDaoDBTest {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    BoardTileDao btDao;
    
    Game game;
    
    @Before
    public void setUp() {
        final String DELETE_BOARDTILES = "DELETE FROM BoardTile";
        jdbc.update(DELETE_BOARDTILES);
        
        game = new Game();
        game.setGameLeader("Jordan");
        game.setStartTime(LocalDateTime.now());
        gameDao.createNewGame(game);
    }

    @Test
    public void testCreateBoardTile() {
        BoardTile tile = new BoardTile();
        tile.setBoardTileId(1);
        tile.setBoardId(game.getGameId());
        tile.setTileType("BLUE");
        btDao.createBoardTile(tile);
        
        assertEquals("BLUE", btDao.getBoardTile(game.getGameId(), 1).getTileType());
    }


    @Test
    public void testGetBoardTilesForGame() {
        BoardTile tile = new BoardTile();
        tile.setBoardTileId(1);
        tile.setBoardId(game.getGameId());
        tile.setTileType("BLUE");
        btDao.createBoardTile(tile);
        
        assertEquals("BLUE", btDao.getBoardTile(game.getGameId(), 1).getTileType());
        
        BoardTile tile2 = new BoardTile();
        tile2.setBoardTileId(2);
        tile2.setBoardId(game.getGameId());
        tile2.setTileType("BLUE");
        btDao.createBoardTile(tile2);
        
        assertEquals(2, btDao.getBoardTilesForGame(game.getGameId()).size());
    }
    
}
