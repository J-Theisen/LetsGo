
package com.jt.letsgo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoardTile {
    
    @Id
    private int boardTileId;
    private int boardId;
    private String tileType;

    public int getBoardTileId() {
        return boardTileId;
    }

    public void setBoardTileId(int boardTileId) {
        this.boardTileId = boardTileId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getTileType() {
        return tileType;
    }

    public void setTileType(String tileType) {
        this.tileType = tileType;
    }
}
