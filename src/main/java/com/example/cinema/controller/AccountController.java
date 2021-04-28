package com.example.cinema.controller;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.User;
import com.example.cinema.repos.FilmRepo;
import com.example.cinema.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/account")
    public String account (Map<String, Object> model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepo.findByUsername(auth.getName());

        ArrayList<AllFilms> favFilms = new ArrayList<AllFilms>();

        if (user.getFavoriteFilms() != null) {
            for (String id : user.getFavoriteFilms()) {
                favFilms.add(filmRepo.findByStringId(id));
            }
        }

        model.put("username", user.getUsername());
        model.put("films", favFilms);

        return "account";
    }

}
