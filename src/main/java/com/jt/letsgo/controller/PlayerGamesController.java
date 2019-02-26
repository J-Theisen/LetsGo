package com.jt.letsgo.controller;

import com.jt.letsgo.dto.Game;
import com.jt.letsgo.dto.GamePlayer;
import com.jt.letsgo.dto.Player;
import com.jt.letsgo.service.GamePlayerService;
import com.jt.letsgo.service.GameService;
import com.jt.letsgo.service.PlayerService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlayerGamesController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GamePlayerService gpService;

    @Autowired
    private PlayerService players;

    @GetMapping("/continue-game")
    public String continueGameLogin() {
        return "continueGameLogin";
    }

    @PostMapping("/continue-game")
    public String continueGamePostLogin(String userName, String playerPassword, Model model) {
        Player player = players.getPlayerByUsername(userName);
        if (player == null) {
            return "new-game";
        } else if (!player.getPlayerPassword().equals(playerPassword)) { // Going to have to wipe this eventually and do real security.
            return "new-game";
        } else {
            return "redirect:continue-game/" + userName;
        }
    }

    @GetMapping("/continue-game/{username}")
    public String listGamesforPlayer(@PathVariable String username, Model model) {
        List<Game> gamesForPlayer = gameService.getGamesNotCompleted(username);
        HashMap<Integer, String> gpMapForGames = new HashMap();
        for (Game game : gamesForPlayer) {
            List<GamePlayer> players = gpService.getAllPlayersForGame(game.getGameId());
            String allPlayersString = "";
            for (GamePlayer p : players) {
                allPlayersString += p.getPlayerName() + ", ";
            }
            gpMapForGames.put(game.getGameId(), allPlayersString);
        }
        model.addAttribute("games", gamesForPlayer);
        model.addAttribute("map", gpMapForGames);
        return "playerGames";
    }

}
