package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Grupo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "descricao")
    private String descricao;
    //private StatusGrupo status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Docente responsavel;

    @ManyToMany
    @JoinTable(name = "grupo_discente",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private List<Discente> discentes;
    //private List<Long> membroIds = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "diretoria",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private List<Discente> diretoria;
}
