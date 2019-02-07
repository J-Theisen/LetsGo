package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Game;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GameDaoDB implements NewGameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Game createNewGame(Game game) {
        final String CREATE_NEW_GAME = "INSERT INTO Game(GameLeader, StartTime) VALUES(?, ?)";
        jdbc.update(CREATE_NEW_GAME, game.getGameLeader(), game.getStartTime());
        return game;
    }

    @Override
    public List<Game> getGamesByUsername(String username) {
        try {
            final String GET_GAMES_BY_USERNAME = "SELECT * FROM Game WHERE GameLeader = ?";
            return jdbc.query(GET_GAMES_BY_USERNAME, new GameMapper(), username);
           
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Game updateGameOver(Game game) {
        final String UPDATE_GAME = "UPDATE Game SET EndTime = ? WHERE GameId = ?";
        LocalDateTime endTime = LocalDateTime.now();
        jdbc.update(UPDATE_GAME,
                endTime,
                game.getGameId());
        return game;
    }
    
    //Dont think ill need
    @Override
    public Game getGameById(int gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {

            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setGameLeader(rs.getString("GameLeader"));
            game.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
            game.setEndTime(rs.getTimestamp("EndTime").toLocalDateTime());
            return game;
        }
    }
}
