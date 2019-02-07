package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Game;
import java.time.LocalDateTime;
import java.util.List;
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
public class GameDaoDBTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    NewGameDao dao;

    @Before
    public void setUp() {
        final String DELETE_GAMES = "DELETE FROM Game";
        jdbc.update(DELETE_GAMES);
    }

    /**
     * Test of createNewGame method, of class GameDaoDB.
     */
    @Test
    public void testCreateNewGameGetGamesByUser() {
        Game game = new Game();
        game.setGameLeader("Jordan");
        game.setStartTime(LocalDateTime.now());
        dao.createNewGame(game);

        List<Game> gamesForUser = dao.getGamesByUsername("Jordan");
        assertTrue(gamesForUser.size() == 1);
        
        Game game2 = new Game();
        game2.setGameLeader("Jordan");
        game2.setStartTime(LocalDateTime.now());
        dao.createNewGame(game2);
        
        gamesForUser = dao.getGamesByUsername("Jordan");
        assertTrue(gamesForUser.size() == 2);
    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setGameLeader("Jordan");
        game.setStartTime(LocalDateTime.now());
        dao.createNewGame(game);
        List<Game> gamesForUser = dao.getGamesByUsername("Jordan");
        game = gamesForUser.get(0);
        
        assertTrue(gamesForUser.size() == 1);
        
        dao.updateGameOver(game);
        List<Game> gamesList = dao.getGamesByUsername("Jordan");
        Game gameToCheck = gamesList.get(0);
        
        assertTrue(gameToCheck.getEndTime().isAfter(LocalDateTime.of(1900, 1, 1, 0, 0, 0)));
        
    }

}
