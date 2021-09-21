package com.julscode.cinetrailer.repo;

import com.julscode.cinetrailer.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula,Integer> {
}
