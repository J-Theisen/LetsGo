package com.jt.letsgo.controller;

import com.jt.letsgo.dto.GamePlayer;
import com.jt.letsgo.service.BoardTileService;
import com.jt.letsgo.service.GamePlayerService;
import com.jt.letsgo.service.GameService;
import com.jt.letsgo.service.PlayerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private PlayerService players;

    @Autowired
    GamePlayerService gpService;

    @Autowired
    BoardTileService btService;

    @GetMapping("game/{username}/{gameIdString}")
    public String game(@PathVariable String username, @PathVariable String gameIdString, Model model) {
        List<GamePlayer> players = gpService.getAllPlayersForGame(Integer.parseInt(gameIdString));
        model.addAttribute("players", players);
        model.addAttribute("game", gpService.updatePlayerCharactersAndTurnNumber(Integer.parseInt(gameIdString), players));
        return "game";
    }
    
    @PutMapping("/gameover/api/{gameIdString}")
    public String updateGameOver(@PathVariable String gameIdString, Model model){
        List<GamePlayer> players = gpService.getAllPlayersForGame(Integer.parseInt(gameIdString));
        model.addAttribute("players", players);
        model.addAttribute("game", gpService.updatePlayerCharactersAndTurnNumber(Integer.parseInt(gameIdString), players));
        gameService.updateGameOver(gameService.getGameById(Integer.parseInt(gameIdString)));
        return "game";
    }
}
