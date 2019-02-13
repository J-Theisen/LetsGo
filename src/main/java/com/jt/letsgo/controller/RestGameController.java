
package com.jt.letsgo.controller;

import com.jt.letsgo.dto.BoardTile;
import com.jt.letsgo.dto.GamePlayer;
import com.jt.letsgo.service.BoardTileService;
import com.jt.letsgo.service.GamePlayerService;
import com.jt.letsgo.service.NewGameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RestGameController {
    
    @Autowired
    NewGameService gameService;
    
    @Autowired
    BoardTileService btService;
    
    @Autowired
    GamePlayerService gpService;

    @GetMapping("/api/game/{gameId}")
    public List<BoardTile> getTilesForGame(@PathVariable int gameId){
        List<BoardTile> boardTiles = btService.getBoardTilesForGame(gameId);
        return boardTiles;
    }
    
    @GetMapping("/api/game/players/{gameId}")
    public List<GamePlayer> getPlayersForGame(@PathVariable int gameId){
        List<GamePlayer> players = gpService.getAllPlayersForGame(gameId);
        return players;
    }
}
