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
        final String GET_PLAYER = "SELECT * FROM GamePlayer WHERE GameId = ? AND PlayerName = ?";
        return jdbc.queryForObject(GET_PLAYER, new GamePlayerMapper(), gameId, playerName);
    }
    
    @Override
    public GamePlayer getPlayerByGameIdTurnNumber(int gameId, int turnNumber) {
        final String GET_PLAYER = "SELECT * FROM GamePlayer WHERE GameId = ? AND PlayerTurn = ?";
        return jdbc.queryForObject(GET_PLAYER, new GamePlayerMapper(), gameId, turnNumber);
    }

    @Override
    public List<GamePlayer> getAllPlayersForGame(int gameId) {
        final String GET_ALL_PLAYERS_FOR_GAME = "SELECT * FROM GamePlayer WHERE GameId = ?";
        return jdbc.query(GET_ALL_PLAYERS_FOR_GAME, new GamePlayerMapper(), gameId);
    }

    @Override
    public GamePlayer updateGamePlayerCurrency(GamePlayer gp) {
        final String UPDATE_PLAYER = "UPDATE GamePlayer SET PlayerCurrency = ? WHERE GameId = ? AND PlayerName = ?";
        jdbc.update(UPDATE_PLAYER, gp.getPlayerCurrency(), gp.getGameId(), gp.getPlayerName());
        return gp;
    }

    @Override
    public GamePlayer updateGamePlayerCharacter(GamePlayer gp) {
        final String UPDATE_PLAYER = "UPDATE GamePlayer SET PlayerCharacter = ?, ImgUrl = ? WHERE GameId = ? AND PlayerName = ?";
        jdbc.update(UPDATE_PLAYER, gp.getPlayerCharacter(), gp.getImageUrl(), gp.getGameId(), gp.getPlayerName());
        return gp;
    }

    @Override
    public GamePlayer updateTurnNumber(GamePlayer gp) {
        final String UPDATE_PLAYER_TURN = "UPDATE GamePlayer SET PlayerTurn = ? WHERE GameId = ? AND PlayerName = ?";
        jdbc.update(UPDATE_PLAYER_TURN, gp.getPlayerTurn(), gp.getGameId(), gp.getPlayerName());
        return gp;
    }

    @Override
    public GamePlayer updateGamePlayer(GamePlayer gp) {
        final String UPDATE_GAME_PLAYER = "UPDATE GamePlayer SET PlayerCurrency = ?, SpacesMoved = ?, CurrentTile = ? WHERE Id = ?";
        jdbc.update(UPDATE_GAME_PLAYER, gp.getPlayerCurrency(), gp.getSpacesMoved(), gp.getCurrentTile(), gp.getId());
        return gp;
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
            gp.setPlayerTurn(rs.getInt("PlayerTurn"));
            gp.setImageUrl(rs.getString("ImgUrl"));
            gp.setCurrentTile(rs.getInt("CurrentTile"));
            gp.setSpacesMoved(rs.getInt("SpacesMoved"));
            return gp;
        }
    }
}
