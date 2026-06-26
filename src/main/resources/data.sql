-- Usuario base
INSERT INTO usuario (nome, email, senha, ativo)
VALUES ('João Docente', 'joao@ufma.br', '123', true)
    ON CONFLICT DO NOTHING;

INSERT INTO usuario (nome, email, senha, ativo)
VALUES ('Maria Discente', 'maria@ufma.br', '123', true)
    ON CONFLICT DO NOTHING;

-- Docente
INSERT INTO docente (id_usuario, siape, departamento)
VALUES (1, '1234567', 'DEINF')
    ON CONFLICT DO NOTHING;

-- Curso
INSERT INTO curso (nome, codigo)
VALUES ('Ciência da Computação', 1)
    ON CONFLICT DO NOTHING;

-- Discente
INSERT INTO discente (id_usuario, matricula, semestre, id_curso)
VALUES (2, '2023001001', 3, 1)
    ON CONFLICT DO NOTHING;

-- Papeis
INSERT INTO papel (nome) VALUES ('ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO papel (nome) VALUES ('COORDENADOR') ON CONFLICT DO NOTHING;
INSERT INTO papel (nome) VALUES ('DOCENTE') ON CONFLICT DO NOTHING;
INSERT INTO papel (nome) VALUES ('D_DIRETOR') ON CONFLICT DO NOTHING;

-- Vincula João como COORDENADOR
INSERT INTO usuario_papel (id_usuario, id_papel)
VALUES (1, 2)
    ON CONFLICT DO NOTHING;