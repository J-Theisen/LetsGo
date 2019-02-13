package com.jt.letsgo.dao;

import com.jt.letsgo.dto.BoardTile;
import java.util.List;

public interface BoardTileDao {
    BoardTile createBoardTile(BoardTile bt);
    BoardTile getBoardTile(int gameId, int boardTileId);
    List<BoardTile> getBoardTilesForGame(int gameId);
}
