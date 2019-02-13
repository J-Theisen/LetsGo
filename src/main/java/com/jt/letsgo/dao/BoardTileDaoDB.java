
package com.jt.letsgo.dao;

import com.jt.letsgo.dto.BoardTile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardTileDaoDB implements BoardTileDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public BoardTile createBoardTile(BoardTile bt) {
        final String CREATE_TILE = "INSERT INTO BoardTile(BoardTileId, BoardId, TileType) VALUES(?, ?, ?)";
        jdbc.update(CREATE_TILE, bt.getBoardTileId(), bt.getBoardId(), bt.getTileType());
        return bt;
    }

    @Override
    public BoardTile getBoardTile(int gameId, int boardTileId) {
        final String GET_TILE = "SELECT * FROM BoardTile WHERE BoardTileId = ? AND BoardId = ?";
        return jdbc.queryForObject(GET_TILE, new BoardTileMapper(), boardTileId, gameId);
    }

    @Override
    public List<BoardTile> getBoardTilesForGame(int gameId) {
         final String GET_All_TILES = "SELECT * FROM BoardTile WHERE BoardId = ?";
         return jdbc.query(GET_All_TILES, new BoardTileMapper(), gameId);
    }
    
    public static final class BoardTileMapper implements RowMapper<BoardTile>{
       
        @Override
        public BoardTile mapRow(ResultSet rs, int index) throws SQLException{
            BoardTile bt = new BoardTile();
            bt.setBoardTileId(rs.getInt("BoardTileId"));
            bt.setBoardId(rs.getInt("BoardId"));
            bt.setTileType(rs.getString("TileType"));
            return bt;
        }
    }
}
