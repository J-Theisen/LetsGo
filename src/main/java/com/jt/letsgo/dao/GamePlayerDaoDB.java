package com.jt.letsgo.dao;

import com.jt.letsgo.dto.GamePlayer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GamePlayerDaoDB implements GamePlayerDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public GamePlayer addPlayerToGame(GamePlayer gp) {
        final String ADD_PLAYER = "INSERT INTO GamePlayer(GameId, PlayerName) VALUES(?,?)";
        jdbc.update(ADD_PLAYER, 
                    gp.getGameId(),
                    gp.getPlayerName());
        return gp;
    }

    @Override
    public GamePlayer getPlayer(int gameId, String playerName) {
        
    }

    @Override
    public List<GamePlayer> getAllPlayersForGame(int gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GamePlayer updateGamePlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static final class GamePlayerMapper implements RowMapper<GamePlayer> {

        @Override
        public GamePlayer mapRow(ResultSet rs, int index) throws SQLException {
            GamePlayer gp = new GamePlayer();
            gp.setId(rs.getInt("Id"));
            gp.setGameId(rs.getInt("GameId"));
            gp.setPlayerName(rs.getString("PlayerName"));
            gp.setPlayerCurrency(rs.getInt("PlayerCurrency"));
            gp.setPlayerCharacter(rs.getInt("PlayerCharacter"));
            return gp;
        }
    }
}
