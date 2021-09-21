package com.julscode.cinetrailer.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Genero {

    @Id
    @Column(name = "idgenero")
    private Integer id;
    private String titulo;

}
