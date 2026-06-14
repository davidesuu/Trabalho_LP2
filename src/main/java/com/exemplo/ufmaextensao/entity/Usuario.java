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

    @ManyToMany
    @JoinTable(name = "usuario_papel", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_papel"))
    private List<Papel> papeis;

    protected String senha;
    private boolean ativo;

}
