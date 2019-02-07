package com.jt.letsgo.controller;

import com.jt.letsgo.dto.Player;
import com.jt.letsgo.service.PlayerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlayerController {

    @Autowired
    PlayerService players;

    @GetMapping("/create-account")
    public String displayCreatePlayer(Player player) {
        return "create-account";
    }

    @PostMapping("/create-account")
    public String createPlayer(@Valid Player player, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result);
            return "create-account";
        }
        players.createPlayer(player);
        return "redirect:/";
    }
}
