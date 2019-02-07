package com.jt.letsgo.dao;

import com.jt.letsgo.dto.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerDaoDB implements PlayerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Player createPlayer(Player player) {
        final String CREATE_PLAYER = "INSERT INTO Player(Username, FirstName, LastName, PlayerEmail, PlayerPassword) VALUES(?, ?, ?, ?, ?)";
        jdbc.update(CREATE_PLAYER,
                player.getUserName(),
                player.getFirstName(),
                player.getLastName(),
                player.getPlayerEmail(),
                player.getPlayerPassword());

        return player;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        try {
            final String SELECT_PLAYER_BY_USERNAME = "SELECT * FROM Player WHERE Username=?";
            return jdbc.queryForObject(SELECT_PLAYER_BY_USERNAME, new PlayerMapper(), username);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updatePlayer(Player player) {
        final String UPDATE_PLAYER = "UPDATE Player SET PlayerEmail = ?, PlayerPassword = ? WHERE Username = ?";
        jdbc.update(UPDATE_PLAYER,
                player.getPlayerEmail(),
                player.getPlayerPassword(),
                player.getUserName());
    }

    public static final class PlayerMapper implements RowMapper<Player> {

        @Override
        public Player mapRow(ResultSet rs, int index) throws SQLException {
            Player player = new Player();
            player.setUserName(rs.getString("Username"));
            player.setFirstName(rs.getString("FirstName"));
            player.setLastName(rs.getString("LastName"));
            player.setPlayerEmail(rs.getString("PlayerEmail"));
            player.setPlayerPassword(rs.getString("PlayerPassword"));
            return player;
        }
    }
}
