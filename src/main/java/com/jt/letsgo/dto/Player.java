package com.jt.letsgo.dto;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Player {
    
    @Id
    @NotBlank(message = "Username must not be empty.")
    @Size(max = 20, message = "Username must be less than 20 characters.")
    private String userName;

    @NotBlank(message = "First name must not be empty.")
    @Size(max = 20, message = "First name must be less than 20 characters.")
    private String firstName;

    @NotBlank(message = "Last name must not be empty.")
    @Size(max = 20, message = "Last name must be less than 20 characters.")
    private String lastName;

    private String playerEmail;

    @NotBlank(message = "Password must not be empty.")
    @Size(max = 30, message = "Password must be less than 30 characters.")
    private String playerPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public String getPlayerPassword() {
        return playerPassword;
    }

    public void setPlayerPassword(String playerPassword) {
        this.playerPassword = playerPassword;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.userName);
        hash = 19 * hash + Objects.hashCode(this.firstName);
        hash = 19 * hash + Objects.hashCode(this.lastName);
        hash = 19 * hash + Objects.hashCode(this.playerEmail);
        hash = 19 * hash + Objects.hashCode(this.playerPassword);
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
        final Player other = (Player) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.playerEmail, other.playerEmail)) {
            return false;
        }
        if (!Objects.equals(this.playerPassword, other.playerPassword)) {
            return false;
        }
        return true;
    }
}
