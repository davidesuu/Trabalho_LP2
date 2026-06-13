package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "email")
    protected String email;

    @OneToMany(mappedBy = "usuario")
    private List<Papel> papel;

    protected String senha;
    private boolean ativo;

}
