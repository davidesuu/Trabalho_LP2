-- Papeis
INSERT INTO papel (id_papel, nome)
VALUES
    (1, 'ADMIN'),
    (2, 'COORDENADOR'),
    (3, 'DOCENTE'),
    (4, 'D_DIRETOR')
    ON CONFLICT (id_papel) DO UPDATE SET nome = EXCLUDED.nome;

-- Tipos de Oportunidade
INSERT INTO tipo_oportunidade (id_tipo_oportunidade, tipo)
VALUES
    (1, 'EVENTO'),
    (2, 'OFICINA'),
    (3, 'CURSO'),
    (4, 'PROJETO')
    ON CONFLICT (id_tipo_oportunidade) DO UPDATE SET tipo = EXCLUDED.tipo;

-- Curso
INSERT INTO curso (id_curso, nome, codigo)
VALUES (1, 'Ciência da Computação', 1)
    ON CONFLICT (id_curso) DO UPDATE SET nome = EXCLUDED.nome;

-- Usuarios
INSERT INTO usuario (id_usuario, nome, email, senha, ativo)
VALUES
    (1, 'João Docente', 'joao@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true),
    (2, 'Maria Discente', 'maria@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true)
    ON CONFLICT (id_usuario) DO UPDATE SET nome = EXCLUDED.nome;

-- Papeis dos usuarios
INSERT INTO usuario_papel (id_usuario, id_papel)
VALUES
    (1, 2), -- João é COORDENADOR
    (1, 3), -- João é DOCENTE
    (2, 4)  -- Maria é D_DIRETOR
    ON CONFLICT DO NOTHING;

-- Docente
INSERT INTO docente (id_usuario, siape, departamento)
VALUES (1, '1234567', 'DEINF')
    ON CONFLICT (id_usuario) DO UPDATE SET siape = EXCLUDED.siape;

-- Discente
INSERT INTO discente (id_usuario, matricula, semestre, id_curso, ch, ch_total_cumprida)
VALUES (2, '2023001001', 3, 1, 0.0, 0)
    ON CONFLICT (id_usuario) DO UPDATE SET matricula = EXCLUDED.matricula;

-- Ajusta sequencias
SELECT setval(pg_get_serial_sequence('usuario', 'id_usuario'), COALESCE(max(id_usuario), 1)) FROM usuario;
SELECT setval(pg_get_serial_sequence('papel', 'id_papel'), COALESCE(max(id_papel), 1)) FROM papel;
SELECT setval(pg_get_serial_sequence('curso', 'id_curso'), COALESCE(max(id_curso), 1)) FROM curso;
SELECT setval(pg_get_serial_sequence('tipo_oportunidade', 'id_tipo_oportunidade'), COALESCE(max(id_tipo_oportunidade), 1)) FROM tipo_oportunidade;