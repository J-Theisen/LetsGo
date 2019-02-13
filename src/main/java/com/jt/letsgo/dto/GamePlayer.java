
package com.jt.letsgo.dto;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class GamePlayer {
    
    @Id
    private int id;
    private int gameId;
    @NotBlank(message="Player Name cannot be empty!")
    @Size(max = 20, message = "PlayerName cannot be greater than 20 characters.")
    private String playerName;
    private int playerCurrency;
    private int playerCharacter;
    private int playerTurn;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerCurrency() {
        return playerCurrency;
    }

    public void setPlayerCurrency(int playerCurrency) {
        this.playerCurrency = playerCurrency;
    }

    public int getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(int playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + this.gameId;
        hash = 59 * hash + Objects.hashCode(this.playerName);
        hash = 59 * hash + this.playerCurrency;
        hash = 59 * hash + this.playerCharacter;
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
        final GamePlayer other = (GamePlayer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.playerCurrency != other.playerCurrency) {
            return false;
        }
        if (this.playerCharacter != other.playerCharacter) {
            return false;
        }
        if (!Objects.equals(this.playerName, other.playerName)) {
            return false;
        }
        return true;
    }
}
