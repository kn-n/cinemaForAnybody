package com.example.cinema.controller;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Item;
import com.example.cinema.domain.Film;
import com.example.cinema.domain.Message;
import com.example.cinema.repos.FilmRepo;
import com.example.cinema.repos.MessageRepo;
import com.google.gson.Gson;
import org.apache.juli.logging.Log;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;

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
    private MessageRepo messageRepo;

    @Autowired
    private FilmRepo filmRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){

        ArrayList<Film> f = new ArrayList<Film>();

        if (filter != null && !filter.isEmpty()){
            for (int i = 1; i<=250; i++) {
                if (item.items.get(i - 1).title.contains(filter)){
                    f.add(item.items.get(i - 1));
                }
            }
            model.put("films", f);
        }else {
            model.put("films", item.items);
        }

        return "main";
    }

}
