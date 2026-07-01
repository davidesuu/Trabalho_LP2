-- Papeis
INSERT INTO papel (id_papel, nome)
VALUES
    (1, 'ADMIN'),
    (2, 'COORDENADOR'),
    (3, 'DOCENTE'),
    (4, 'D_DIRETOR'),
    (5, 'TESOUREIRO'),
    (6, 'SECRETARIO'),
    (7, 'PRESIDENTE'),
    (8, 'MEMBRO')
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
    (1, 'João Docente', 'joao@ufma.br', '$2b$12$BEBFP1pQ79kv0ZHQDi7UcOFEOjpBxz6Z6NShUXapziDE325aqbgoq', true),
    (2, 'Maria Discente', 'maria@ufma.br', '$2b$12$rh2ebrf5AR6TgcOTQpIB.OIkiLWvxX28InKzjER5uVhHd62R3vdri', true),
    (3, 'Ana Coordenadora', 'ana@ufma.br', '$2b$12$JW98M6BHvQfdHQgbAf7N9OtyyVRJSyUSZRoEaSeRJsEDeJLSHVKiW', true),
    (4, 'Carlos Admin', 'carlos@ufma.br', '$2b$12$GwsBy8lsRkiXu.6dKYb.AOXUU2TTz8d9gJVB38.c6F/cY6BCquRSO', true),
    (5, 'Pedro Tesoureiro', 'pedro@ufma.br', '$2b$12$lPGD7QLqi355Dx8pJnQVKu2Z1LRYU1sicXelfA2/G8Z6wm3kFzKjK', true),
    (6, 'Lara Membro', 'lara@ufma.br', '$2b$12$JDYFkkIkAArDP0unnBUmPeMOJ5XG5NQr/Eut8ZbnnDDSu9vTicuke', true),
    (7, 'Bruno Secretário', 'bruno@ufma.br', '$2b$12$Y5vH07wp8UjSU0GbbTqaDODIM76.qkfBuT626HFbHH/8pwMvjX4Ty', true)
    ON CONFLICT (id_usuario) DO UPDATE SET nome = EXCLUDED.nome, email = EXCLUDED.email, senha = EXCLUDED.senha, ativo = EXCLUDED.ativo;

-- Papeis dos usuarios
INSERT INTO usuario_papel (id_usuario, id_papel)
VALUES
    (1, 2), -- João é COORDENADOR
    (1, 3), -- João é DOCENTE
    (2, 4), -- Maria é D_DIRETOR (discente diretor)
    (3, 2), -- Ana é COORDENADOR
    (3, 3), -- Ana também é DOCENTE
    (4, 1), -- Carlos é ADMIN
    (5, 5), -- Pedro é TESOUREIRO
    (6, 8), -- Lara é MEMBRO
    (7, 6)  -- Bruno é SECRETARIO
    ON CONFLICT DO NOTHING;

-- Docente
INSERT INTO docente (id_usuario, siape, departamento)
VALUES
    (1, '1234567', 'DEINF'),
    (3, '7654321', 'DEINF')
    ON CONFLICT (id_usuario) DO UPDATE SET siape = EXCLUDED.siape, departamento = EXCLUDED.departamento;

-- Discente
INSERT INTO discente (id_usuario, matricula, semestre, id_curso, ch, ch_total_cumprida)
VALUES
    (2, '2023001001', 3, 1, 0.0, 0),
    (5, '2023001002', 5, 1, 0.0, 0),
    (6, '2023001003', 2, 1, 0.0, 0)
    ON CONFLICT (id_usuario) DO UPDATE SET matricula = EXCLUDED.matricula, semestre = EXCLUDED.semestre, id_curso = EXCLUDED.id_curso, ch = EXCLUDED.ch, ch_total_cumprida = EXCLUDED.ch_total_cumprida;

-- Grupo PETComp
INSERT INTO grupo (id_grupo, nome, email, descricao, id_usuario)
VALUES (1, 'PETComp', 'petcomp@ufma.br', 'Grupo PET de Computação', 1)
    ON CONFLICT (id_grupo) DO UPDATE SET nome = EXCLUDED.nome, email = EXCLUDED.email, descricao = EXCLUDED.descricao, id_usuario = EXCLUDED.id_usuario;

-- Membros do grupo PETComp
INSERT INTO grupo_discente (id_grupo, id_usuario)
VALUES
    (1, 2), -- Maria é discente diretora do grupo
    (1, 6)  -- Lara é discente membro do grupo
    ON CONFLICT DO NOTHING;

-- Diretoria do grupo PETComp
INSERT INTO diretoria (id_grupo, id_usuario)
VALUES (1, 2)
    ON CONFLICT DO NOTHING;

-- Oportunidades de exemplo
INSERT INTO oportunidade (id_oportunidade, titulo, descricao, tipo_oportunidade, mobilidade, carga_horaria, vagas, vagas_ocupadas, status, data_inicio, data_fim, responsavel_id, autor_id, id_grupo)
VALUES
    (1, 'Workshop de Python para Inovação', 'Oportunidade aberta para participação em workshop de Python.', 2, 'PRESENCIAL', 20, 30, 0, 'PUBLICADA', '2026-07-05', '2026-07-20', 1, 3, 1),
    (2, 'Ciclo de Palestras de IA', 'Oportunidade já encerrada para certificação de participação.', 1, 'HIBRIDO', 40, 20, 18, 'FINALIZADA', '2026-05-01', '2026-05-20', 3, 1, 1)
    ON CONFLICT (id_oportunidade) DO UPDATE SET titulo = EXCLUDED.titulo, descricao = EXCLUDED.descricao, tipo_oportunidade = EXCLUDED.tipo_oportunidade, mobilidade = EXCLUDED.mobilidade, carga_horaria = EXCLUDED.carga_horaria, vagas = EXCLUDED.vagas, vagas_ocupadas = EXCLUDED.vagas_ocupadas, status = EXCLUDED.status, data_inicio = EXCLUDED.data_inicio, data_fim = EXCLUDED.data_fim, responsavel_id = EXCLUDED.responsavel_id, autor_id = EXCLUDED.autor_id, id_grupo = EXCLUDED.id_grupo;

-- Inscrição de exemplo em oportunidade aberta
INSERT INTO inscricao (id_inscricao, id_oportunidade, id_discente, status_inscricao, motivacao)
VALUES
    (1, 1, 2, 'PENDENTE', 'Quero aprender Python para projetos de extensão.')
    ON CONFLICT (id_inscricao) DO UPDATE SET id_oportunidade = EXCLUDED.id_oportunidade, id_discente = EXCLUDED.id_discente, status_inscricao = EXCLUDED.status_inscricao, motivacao = EXCLUDED.motivacao;

-- Certificado de exemplo para oportunidade finalizada
INSERT INTO certificado (id_certificado, uuid_hash, id_discente, id_oportunidade, horas, status_assinatura, data_emissao)
VALUES
    (1, 'CERT-2026-0001', 2, 2, 40, 'ASSINADO', '2026-05-21')
    ON CONFLICT (id_certificado) DO UPDATE SET uuid_hash = EXCLUDED.uuid_hash, id_discente = EXCLUDED.id_discente, id_oportunidade = EXCLUDED.id_oportunidade, horas = EXCLUDED.horas, status_assinatura = EXCLUDED.status_assinatura, data_emissao = EXCLUDED.data_emissao;

-- Solicitação de aproveitamento de exemplo
INSERT INTO aproveitamento (id_aproveitamento, id_discente, descricao, instituicao, horas, status, certificado_path)
VALUES
    (1, 2, 'Curso externo de Inteligência Artificial', 'UFMA', 20, 'PENDENTE', '/certificados/curso-ia.pdf')
    ON CONFLICT (id_aproveitamento) DO UPDATE SET id_discente = EXCLUDED.id_discente, descricao = EXCLUDED.descricao, instituicao = EXCLUDED.instituicao, horas = EXCLUDED.horas, status = EXCLUDED.status, certificado_path = EXCLUDED.certificado_path;

-- Ajusta sequencias
SELECT setval(pg_get_serial_sequence('usuario', 'id_usuario'), COALESCE(max(id_usuario), 1)) FROM usuario;
SELECT setval(pg_get_serial_sequence('papel', 'id_papel'), COALESCE(max(id_papel), 1)) FROM papel;
SELECT setval(pg_get_serial_sequence('curso', 'id_curso'), COALESCE(max(id_curso), 1)) FROM curso;
SELECT setval(pg_get_serial_sequence('tipo_oportunidade', 'id_tipo_oportunidade'), COALESCE(max(id_tipo_oportunidade), 1)) FROM tipo_oportunidade;
SELECT setval(pg_get_serial_sequence('grupo', 'id_grupo'), COALESCE(max(id_grupo), 1)) FROM grupo;
SELECT setval(pg_get_serial_sequence('oportunidade', 'id_oportunidade'), COALESCE(max(id_oportunidade), 1)) FROM oportunidade;
SELECT setval(pg_get_serial_sequence('inscricao', 'id_inscricao'), COALESCE(max(id_inscricao), 1)) FROM inscricao;
SELECT setval(pg_get_serial_sequence('certificado', 'id_certificado'), COALESCE(max(id_certificado), 1)) FROM certificado;
SELECT setval(pg_get_serial_sequence('aproveitamento', 'id_aproveitamento'), COALESCE(max(id_aproveitamento), 1)) FROM aproveitamento;