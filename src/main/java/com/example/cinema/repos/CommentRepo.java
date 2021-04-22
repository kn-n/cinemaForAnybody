package com.example.cinema.repos;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.ArrayList;

public interface CommentRepo extends JpaRepository<Comments, Long> {
    Comments findByUsername(String username);
    Comments findByFilmId(String filmId);
    ArrayList<Comments> findAllByFilmId(String filmId);
}
