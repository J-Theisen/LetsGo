package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Game;
import com.jt.letsgo.dto.GamePlayer;
import java.time.LocalDateTime;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamePlayerDaoDBTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    GamePlayerDao dao;
    
    @Autowired 
    NewGameDao gameDao;
    
    Game game;

    @Before
    public void setUp() {
        final String DELETE_GAMEPLAYERS = "DELETE FROM GamePlayer";
        jdbc.update(DELETE_GAMEPLAYERS);
        
        game = new Game();
        game.setGameLeader("Jordan");
        game.setStartTime(LocalDateTime.now());
        gameDao.createNewGame(game);
    }

    @Test
    public void testAddPlayerToGameGetPlayerGetAllPlayers() {
        GamePlayer gp = new GamePlayer();
        gp.setGameId(game.getGameId());
        gp.setPlayerName("Jordan");
        dao.addPlayerToGame(gp);
        
        assertTrue(dao.getPlayer(game.getGameId(), gp.getPlayerName()).getPlayerName().equals(gp.getPlayerName()));
        
        GamePlayer gp2 = new GamePlayer();
        gp2.setGameId(game.getGameId());
        gp2.setPlayerName("Bobby");
        dao.addPlayerToGame(gp2);
        
        assertEquals(2, dao.getAllPlayersForGame(game.getGameId()).size());
    }

    @Test
    public void testUpdateGamePlayer() {
        GamePlayer gp = new GamePlayer();
        gp.setGameId(game.getGameId());
        gp.setPlayerName("Jordan");
        dao.addPlayerToGame(gp);
        
        gp.setPlayerCurrency(5);
        dao.updateGamePlayerCurrency(gp);
        assertTrue(dao.getPlayer(game.getGameId(), gp.getPlayerName()).getPlayerCurrency()==5);
        
        gp.setPlayerCharacter(3);
        dao.updateGamePlayerCharacter(gp);
        assertTrue(dao.getPlayer(game.getGameId(), gp.getPlayerName()).getPlayerCharacter()==3);
        
    }

}
