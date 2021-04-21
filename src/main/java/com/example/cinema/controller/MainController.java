package com.example.cinema.controller;

import com.example.cinema.domain.*;
import com.example.cinema.repos.CommentRepo;
import com.example.cinema.repos.FilmRepo;
import com.example.cinema.repos.UserRepo;
import com.google.gson.Gson;
import org.apache.juli.logging.Log;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class MainController {
    public static Item item;

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){

        List<AllFilms> filmsFromDb = filmRepo.findAll();
        ArrayList<AllFilms> f = new ArrayList<AllFilms>();

        if (filter != null && !filter.isEmpty()){
            for (int i = 1; i<=250; i++) {
                if (filmsFromDb.get(i - 1).title.contains(filter)){
                    f.add(filmsFromDb.get(i - 1));
                }
            }
            model.put("films", f);
        }else {
            model.put("films", filmsFromDb);
        }

        return "main";
    }

    @PostMapping("addFilm")
    public String addFilm(@RequestParam String filmID){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepo.findByUsername(auth.getName());

        if (user.getFavoriteFilms() == null)
        {
            ArrayList<String> film = new ArrayList<>();
            film.add(filmID);
            user.setFavoriteFilms(film);
        }else {
            user.getFavoriteFilms().add(filmID);
        }

        userRepo.save(user);
        System.out.println(user.getUsername());
        System.out.println(filmID);
        System.out.println(user.getFavoriteFilms());
//fds
        return "redirect:/main";
    }

    @PostMapping("toMovie")
    public String toMovie(@RequestParam String filmID, Map<String, Object> model){

        AllFilms film = filmRepo.findByStringId(filmID);
        Comments comments = commentRepo.findByFilmId(filmID);
        model.put("films", film);
        if(comments!=null)
            model.put("comments", comments);
        return "film";
    }

    @PostMapping("sendComment")
    public String sendComment(@RequestParam String comment,@RequestParam String filmID, Map<String, Object> model){

        System.out.println("suka");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Comments newComment = new Comments();
        newComment.setIdFilm(filmID);
        newComment.setUsername(auth.getName());
        newComment.setData(new Date().toString());
        newComment.setText(comment);
        commentRepo.save(newComment);

        AllFilms film = filmRepo.findByStringId(filmID);
        Comments comments = commentRepo.findByFilmId(filmID);
        model.put("films", film);
        if(comments!=null)
            model.put("comments", comments);
        return "redirect:/film";
    }
}