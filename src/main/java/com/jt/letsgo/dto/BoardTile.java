
package com.jt.letsgo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoardTile {
    
    @Id
    private int boardTileId;
    private int boardId;
    private String tileType;
    
}
