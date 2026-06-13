package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;

@Entity
public class Papel {
    @Id
    private Long id;
    String papel;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
