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
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Game createNewGame(Game game) {
        final String CREATE_NEW_GAME = "INSERT INTO Game(GameLeader, StartTime) VALUES(?, ?)";
        int updated = jdbc.update(CREATE_NEW_GAME, game.getGameLeader(), game.getStartTime());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
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
    public Game getGameById(int gameId) {
        final String GET_GAME_BY_ID = "SELECT * FROM Game WHERE GameId = ?";
        return jdbc.queryForObject(GET_GAME_BY_ID, new GameMapper(), gameId);
    }

    @Override
    public Game updateGameOver(Game game) {
        final String UPDATE_GAME = "UPDATE Game SET EndTime = ? WHERE GameId = ?";
        LocalDateTime endTime = LocalDateTime.now();
        jdbc.update(UPDATE_GAME,
                endTime,
                game.getGameId());
         return getGameById(game.getGameId());
    }

    @Override
    public Game updateGameStarted(Game game) {
        final String UPDATE_GAME_OVER = "UPDATE Game SET GameStarted = 1 WHERE GameId = ?";
        jdbc.update(UPDATE_GAME_OVER, game.getGameId());
         return getGameById(game.getGameId());
    }

    @Override
    public Game updateGame(Game game) {
         final String UPDATE_GAME_OVER = "UPDATE Game SET PlayerTurn = ? WHERE GameId = ?";
         jdbc.update(UPDATE_GAME_OVER, game.getPlayerTurn(), game.getGameId());
         return getGameById(game.getGameId());
    }
    
    

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {

            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setGameLeader(rs.getString("GameLeader"));
            game.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
            game.setEndTime(rs.getTimestamp("EndTime").toLocalDateTime());
            game.setGameStarted(rs.getInt("GameStarted"));
            game.setPlayerTurn(rs.getInt("PlayerTurn"));
            return game;
        }
    }
}
