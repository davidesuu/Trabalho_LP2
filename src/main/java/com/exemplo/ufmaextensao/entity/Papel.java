package com.exemplo.ufmaextensao.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "papel")
@Data
public class Papel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_papel")
    private Integer id;

    private String nome; // "COORDENADOR", "ADMIN", "DOCENTE"

    @ManyToMany(mappedBy = "papeis")
    private List<Usuario> usuarios;
}