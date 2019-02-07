package com.jt.letsgo.controller;

import com.jt.letsgo.dao.PlayerDao;
import com.jt.letsgo.dto.Game;
import com.jt.letsgo.dto.Player;
import com.jt.letsgo.service.NewGameService;
import com.jt.letsgo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewGameController {

    @Autowired
    private PlayerService players;

    @Autowired
    private NewGameService gameService;

    @GetMapping("/new-game")
    public String displayNewGame() {

        return "new-game";
    }

    @PostMapping("/new-game")
    public String newGame(String userName, String playerPassword, Model model) {
        Player player = players.getPlayerByUsername(userName);
        if (player == null) {
            return "new-game";
        } else if (!player.getPlayerPassword().equals(playerPassword)) {
            return "new-game";
        } else {
            Player playerModel = players.getPlayerByUsername(userName);
            //model.addAttribute("player", playerModel);
            Game returnGame = gameService.createNewGame(userName);
            return "redirect:add-game/" + playerModel.getUserName() + "/" + Integer.toString(returnGame.getGameId());
        }
    }
    
    @GetMapping("/add-game/{username}/{gameIdString}")
    public String addPlayerForm(@PathVariable String username, @PathVariable String gameIdString) {
        
        return "add-player";
    }
 
}
