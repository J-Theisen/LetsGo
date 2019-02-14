package com.jt.letsgo.controller;

import com.jt.letsgo.dto.BoardTile;
import com.jt.letsgo.dto.Game;
import com.jt.letsgo.dto.GamePlayer;
import com.jt.letsgo.service.BoardTileService;
import com.jt.letsgo.service.GamePlayerService;
import com.jt.letsgo.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RestGameController {

    @Autowired
    GameService gameService;

    @Autowired
    BoardTileService btService;

    @Autowired
    GamePlayerService gpService;

    //Get all tiles for game
    @GetMapping("/api/getTiles/{gameId}")
    public List<BoardTile> getTilesForGame(@PathVariable int gameId) {
        List<BoardTile> boardTiles = btService.getBoardTilesForGame(gameId);
        return boardTiles;
    }

    //Gets a game
    @GetMapping("/api/get-game/{gameId}")
    public Game getGame(@PathVariable int gameId) {
        return gameService.getGameById(gameId);
    }

    //Gets all the players for a game
    @GetMapping("/api/game/players/{gameId}")
    public List<GamePlayer> getPlayersForGame(@PathVariable int gameId) {
        List<GamePlayer> players = gpService.getAllPlayersForGame(gameId);
        return players;
    }

    //Gets a gameplayer for a game
    @GetMapping("/api/get-game-player/{gameId}/{turnNumber}")
    public GamePlayer getGamePlayer(@PathVariable int gameId, @PathVariable int turnNumber) {
        return gpService.getPlayerByGameIdTurnNumber(gameId, turnNumber);

    }

    @PutMapping("/api/game")
    public Game updateGamePlayerTurn(@RequestBody Game game) {
        return gameService.updateGame(game);
    }

    @PutMapping("/api/game-player")
    public GamePlayer updateGamePlayer(@RequestBody GamePlayer gp) {
        return gpService.updateGamePlayer(gp);
    }

    //@PutMapping("/api/game-player/{gameId}/playerName")
}
