package com.example.cinema.repos;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comments, Long> {
    Comments findByUsername(String username);
    Comments findByFilmId(String filmId);
}
