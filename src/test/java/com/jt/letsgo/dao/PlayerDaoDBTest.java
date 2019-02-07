
package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Player;
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
public class PlayerDaoDBTest {
    
    @Autowired
    PlayerDao dao;
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Before
    public void setUp() {
        final String DELETE_PLAYERS = "DELETE FROM Player";
        jdbc.update(DELETE_PLAYERS);
    }


    @Test
    public void testCreatePlayerGetPlayerByUsername() {
        Player player = new Player();
        player.setFirstName("Jordan");
        player.setLastName("Theisen");
        player.setUserName("JT");
        player.setPlayerEmail("jordantheisen08@gmail.com");
        player.setPlayerPassword("theisen08");
        dao.createPlayer(player);
        
        Player playerToCheck = dao.getPlayerByUsername("JT");
        assertTrue(player.getLastName().equals(playerToCheck.getLastName()));
    }

    /**
     * Test of updatePlayer method, of class PlayerDaoDB.
     */
    @Test
    public void testUpdatePlayer() {
        Player player = new Player();
        player.setFirstName("Jordan");
        player.setLastName("Theisen");
        player.setUserName("JT");
        player.setPlayerEmail("jordantheisen08@gmail.com");
        player.setPlayerPassword("theisen08");
        dao.createPlayer(player);
        
        Player playerToUpdate = dao.getPlayerByUsername("JT");
        
        playerToUpdate.setPlayerEmail("alwaysfirst@gmail.com");
        playerToUpdate.setPlayerPassword("TestPassChange");
        
        dao.updatePlayer(playerToUpdate);
        Player playerToCheck = dao.getPlayerByUsername("JT");
        assertTrue(playerToCheck.getPlayerEmail().equals("alwaysfirst@gmail.com"));
        assertTrue(playerToCheck.getPlayerPassword().equals("TestPassChange"));
    }
}
