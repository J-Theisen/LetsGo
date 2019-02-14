package com.jt.letsgo.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {

    @Id
    private int gameId;
    private String gameLeader;
    private int boardId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int gameStarted;
    private int playerTurn;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameLeader() {
        return gameLeader;
    }

    public void setGameLeader(String gameLeader) {
        this.gameLeader = gameLeader;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    
     public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(int gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.gameId;
        hash = 53 * hash + Objects.hashCode(this.gameLeader);
        hash = 53 * hash + this.boardId;
        hash = 53 * hash + Objects.hashCode(this.startTime);
        hash = 53 * hash + Objects.hashCode(this.endTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.boardId != other.boardId) {
            return false;
        }
        if (!Objects.equals(this.gameLeader, other.gameLeader)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        return true;
    }

}
