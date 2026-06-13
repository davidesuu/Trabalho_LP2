-- Usuario base
INSERT INTO usuario (nome, email, senha, ativo)
VALUES ('João Docente', 'joao@ufma.br', '123', true);

INSERT INTO usuario (nome, email, senha, ativo)
VALUES ('Maria Discente', 'maria@ufma.br', '123', true);

-- Docente
INSERT INTO docente (id_usuario, siape, departamento)
VALUES (1, '1234567', 'DEINF');

-- Curso
INSERT INTO curso (nome, codigo) VALUES ('Ciência da Computação', 1);

-- Discente
INSERT INTO discente (id_usuario, matricula, semestre, id_curso)
VALUES (2, '2023001001', 3, 1);

-- Grupo
INSERT INTO grupo (nome, email, descricao, id_usuario)
VALUES ('PETComp', 'petcomp@ufma.br', 'Grupo de estudos', 1);