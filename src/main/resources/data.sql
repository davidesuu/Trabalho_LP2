-- Usuario base
INSERT OR IGNORE INTO usuario (nome, email, senha, ativo)
VALUES ('João Docente', 'joao@ufma.br', '123', 1);

INSERT OR IGNORE INTO usuario (nome, email, senha, ativo)
VALUES ('Maria Discente', 'maria@ufma.br', '123', 1);

-- Docente
INSERT OR IGNORE INTO docente (id_usuario, siape, departamento)
VALUES (1, '1234567', 'DEINF');

-- Curso
INSERT OR IGNORE INTO curso (nome, codigo) VALUES ('Ciência da Computação', 1);

-- Discente
INSERT OR IGNORE INTO discente (id_usuario, matricula, semestre, id_curso)
VALUES (2, '2023001001', 3, 1);

-- Grupo
INSERT OR IGNORE INTO grupo (nome, email, descricao, id_usuario)
VALUES ('PETComp', 'petcomp@ufma.br', 'Grupo de estudos', 1);

-- Papeis
INSERT OR IGNORE INTO papel (nome) VALUES ('ADMIN');
INSERT OR IGNORE INTO papel (nome) VALUES ('COORDENADOR');
INSERT OR IGNORE INTO papel (nome) VALUES ('DOCENTE');
INSERT OR IGNORE INTO papel (nome) VALUES ('DISCENTE');

-- Vincula João como COORDENADOR
INSERT OR IGNORE INTO usuario_papel (id_usuario, id_papel) VALUES (1, 2);