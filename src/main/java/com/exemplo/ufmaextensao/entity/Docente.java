package com.exemplo.ufmaextensao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Docente extends Usuario {
    @Column(name = "siape")
    private String siape;
    @Column(name = "departamento")
    private String departamento;

    @JsonIgnore
    @OneToMany(mappedBy = "responsavel")
    private List<Grupo> grupos;
}
