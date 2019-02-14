package com.jt.letsgo.controller;

import com.jt.letsgo.dto.Game;
import com.jt.letsgo.dto.GamePlayer;
import com.jt.letsgo.dto.Player;
import com.jt.letsgo.service.BoardTileService;
import com.jt.letsgo.service.GamePlayerService;
import com.jt.letsgo.service.GameService;
import com.jt.letsgo.service.PlayerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewGameController {

    @Autowired
    private PlayerService players;

    @Autowired
    private GameService gameService;

    @Autowired
    GamePlayerService gpService;

    @Autowired
    BoardTileService btService;

    @GetMapping("/new-game")
    public String displayNewGame() {
        return "new-game";
    }

    @PostMapping("/new-game")
    public String newGame(String userName, String playerPassword, Model model) {
        Player player = players.getPlayerByUsername(userName);
        if (player == null) {
            return "new-game";
        } else if (!player.getPlayerPassword().equals(playerPassword)) { // Going to have to wipe this eventually and do real security.
            return "new-game";
        } else {
            Player playerModel = players.getPlayerByUsername(userName);
            //model.addAttribute("player", playerModel);
            Game returnGame = gameService.createNewGame(userName);
            gpService.addPlayerToGame(userName, returnGame.getGameId());
            btService.createBoardTilesForGame(returnGame.getGameId());
            return "redirect:add-game/" + playerModel.getUserName() + "/" + Integer.toString(returnGame.getGameId());
        }
    }

    @GetMapping("/add-game/{username}/{gameIdString}")
    public String addPlayerForm(GamePlayer gamePlayer, @PathVariable String username, @PathVariable String gameIdString, Model model) {

        List<GamePlayer> players = gpService.getAllPlayersForGame(Integer.parseInt(gameIdString));
        model.addAttribute("players", players);
        return "add-player";
    }

    @PostMapping("/add-game/{username}/{gameIdString}")
    public String redisplayAddPlayerForm(@Valid GamePlayer gamePlayer, BindingResult result, @PathVariable String username, @PathVariable String gameIdString, String playerName, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result);
            List<GamePlayer> players = gpService.getAllPlayersForGame(Integer.parseInt(gameIdString));
            model.addAttribute("players", players);
            return "add-player";
        } else {
            gpService.addPlayerToGame(playerName, Integer.parseInt(gameIdString));
            List<GamePlayer> players = gpService.getAllPlayersForGame(Integer.parseInt(gameIdString));
            model.addAttribute("players", players);
            model.addAttribute("gameId", gameIdString);
            model.addAttribute("gameLeader", username);
            return "add-player";
        }
    }

}
