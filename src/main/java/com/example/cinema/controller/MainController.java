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

    @GetMapping("/main")
    public String main(Map<String, Object> model) throws IOException {

        String url = "https://imdb-api.com/en/API/Top250Movies/k_a3bczbcf";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Gson g = new Gson();
        item = g.fromJson(response.toString(), Item.class);

        AllFilms aue = new AllFilms();

//        for (Film i:item.items)
//        {
//            aue.setStringId(i.id);
//            aue.setRank(i.rank);
//            aue.setTitle(i.title);
//            aue.setFullTitle(i.fullTitle);
//            aue.setYear(i.year);
//            aue.setImage(i.image);
//            aue.setCrew(i.crew);
//            aue.setImDbRating(i.imDbRating);
//            aue.setImDbRatingCount(i.imDbRatingCount);
//            filmRepo.save(aue);
//        }

        for (int i = 1; i<=250; i++) {
            aue.setStringId(item.items.get(i - 1).id);
            aue.setRank(item.items.get(i - 1).rank);
            aue.setTitle(item.items.get(i - 1).title);
            aue.setFullTitle(item.items.get(i - 1).fullTitle);
            aue.setYear(item.items.get(i - 1).year);
            aue.setImage(item.items.get(i - 1).image);
            aue.setCrew(item.items.get(i - 1).crew);
            aue.setImDbRating(item.items.get(i - 1).imDbRating);
            aue.setImDbRatingCount(item.items.get(i - 1).imDbRatingCount);
            System.out.println("Jizn voram"+(i-1));
            filmRepo.save(aue);
            System.out.println("Smert voram"+(i-1));
        }

        model.put("films", item.items);

        return "main";
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
