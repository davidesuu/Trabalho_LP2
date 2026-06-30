-- Insere Papéis (Papeis)
INSERT INTO papel (id_papel, nome)
VALUES 
    (1, 'ADMIN'),
    (2, 'COORDENADOR'),
    (3, 'DOCENTE'),
    (4, 'D_DIRETOR')
ON CONFLICT (id_papel) DO UPDATE SET nome = EXCLUDED.nome;

-- Insere Cursos
INSERT INTO curso (id_curso, nome, codigo)
VALUES 
    (1, 'Ciência da Computação', 1),
    (2, 'Engenharia de Computação', 2)
ON CONFLICT (id_curso) DO UPDATE SET nome = EXCLUDED.nome, codigo = EXCLUDED.codigo;

-- Insere PPC (Projeto Pedagógico de Curso)
INSERT INTO ppc (id, id_curso, ano_vigencia, carga_horaria)
VALUES 
    (1, 1, 2023, 3200.0),
    (2, 2, 2023, 3600.0)
ON CONFLICT (id) DO UPDATE SET id_curso = EXCLUDED.id_curso, ano_vigencia = EXCLUDED.ano_vigencia, carga_horaria = EXCLUDED.carga_horaria;

-- Insere Tipos de Oportunidade
INSERT INTO tipo_oportunidade (id_tipo_oportunidade, tipo)
VALUES 
    (1, 'EVENTO'),
    (2, 'OFICINA'),
    (3, 'CURSO'),
    (4, 'PROJETO')
ON CONFLICT (id_tipo_oportunidade) DO UPDATE SET tipo = EXCLUDED.tipo;

-- Insere Usuários (Admin, Docentes e Discentes)
-- Senha padrão: "123" (BCrypt hash)
INSERT INTO usuario (id_usuario, nome, email, senha, ativo)
VALUES 
    (1, 'Administrador do Sistema', 'admin@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true),
    (2, 'João Docente (Coordenador)', 'joao@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true),
    (3, 'Ana Docente', 'ana@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true),
    (4, 'Maria Discente', 'maria@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true),
    (5, 'Pedro Discente', 'pedro@ufma.br', '$2a$10$u6/wM1jRmJ/jNDorwkmnKOsiPmyfuVPAMoGInuhuu3IcAgqAMTjfW', true)
ON CONFLICT (id_usuario) DO UPDATE SET nome = EXCLUDED.nome, email = EXCLUDED.email, senha = EXCLUDED.senha, ativo = EXCLUDED.ativo;

-- Atribui papéis aos usuários
INSERT INTO usuario_papel (id_usuario, id_papel)
VALUES 
    (1, 1), -- Admin é ADMIN
    (2, 2), -- João é COORDENADOR
    (2, 3), -- João é DOCENTE
    (3, 3), -- Ana é DOCENTE
    (4, 4), -- Maria é D_DIRETOR
    (5, 4)  -- Pedro é D_DIRETOR
ON CONFLICT DO NOTHING;

-- Insere Docentes
INSERT INTO docente (id_usuario, siape, departamento)
VALUES 
    (2, '1234567', 'DEINF'),
    (3, '7654321', 'DEINF')
ON CONFLICT (id_usuario) DO UPDATE SET siape = EXCLUDED.siape, departamento = EXCLUDED.departamento;

-- Insere Discentes
INSERT INTO discente (id_usuario, matricula, semestre, id_curso, ch, ch_total_cumprida)
VALUES 
    (4, '2023001001', 3, 1, 320.0, 0),
    (5, '2023001002', 4, 1, 320.0, 60)
ON CONFLICT (id_usuario) DO UPDATE SET matricula = EXCLUDED.matricula, semestre = EXCLUDED.semestre, id_curso = EXCLUDED.id_curso, ch = EXCLUDED.ch, ch_total_cumprida = EXCLUDED.ch_total_cumprida;

-- Insere Grupos
INSERT INTO grupo (id_grupo, nome, email, descricao, id_usuario)
VALUES 
    (1, 'PET Computação', 'petcomp@ufma.br', 'Programa de Educação Tutorial', 2),
    (2, 'Fábrica de Software', 'fabricasoftware@ufma.br', 'Desenvolvimento de software e projetos práticos', 3)
ON CONFLICT (id_grupo) DO UPDATE SET nome = EXCLUDED.nome, email = EXCLUDED.email, descricao = EXCLUDED.descricao, id_usuario = EXCLUDED.id_usuario;

-- Relaciona Discentes com Grupos (Membros)
INSERT INTO grupo_discente (id_grupo, id_usuario)
VALUES 
    (1, 4),
    (2, 5)
ON CONFLICT DO NOTHING;

-- Relaciona Discentes com Diretoria do Grupo
INSERT INTO diretoria (id_grupo, id_usuario)
VALUES 
    (1, 4)
ON CONFLICT DO NOTHING;

-- Insere Oportunidades
INSERT INTO oportunidade (id_oportunidade, titulo, descricao, tipo_oportunidade, mobilidade, carga_horaria, vagas, vagas_ocupadas, status, data_inicio, data_fim, responsavel_id, autor_id, id_grupo)
VALUES 
    (1, 'Desenvolvimento Web com Spring Boot', 'Oficina prática de desenvolvimento backend em Java.', 2, 'REMOTO', 20, 30, 1, 'PUBLICADA', '2026-07-01', '2026-07-15', 3, 3, 2),
    (2, 'II Semana de Computação da UFMA', 'Evento acadêmico com palestras, painéis e workshops.', 1, 'PRESENCIAL', 40, 200, 0, 'PUBLICADA', '2026-08-10', '2026-08-14', 2, 2, 1)
ON CONFLICT (id_oportunidade) DO UPDATE SET titulo = EXCLUDED.titulo, descricao = EXCLUDED.descricao, tipo_oportunidade = EXCLUDED.tipo_oportunidade, mobilidade = EXCLUDED.mobilidade, carga_horaria = EXCLUDED.carga_horaria, vagas = EXCLUDED.vagas, vagas_ocupadas = EXCLUDED.vagas_ocupadas, status = EXCLUDED.status, data_inicio = EXCLUDED.data_inicio, data_fim = EXCLUDED.data_fim, responsavel_id = EXCLUDED.responsavel_id, autor_id = EXCLUDED.autor_id, id_grupo = EXCLUDED.id_grupo;

-- Insere Inscrições
INSERT INTO inscricao (id, id_oportunidade, id_discente, status_inscricao, motivacao, created_at)
VALUES 
    (1, 1, 5, 'APROVADA', 'Quero aprender mais sobre desenvolvimento backend com Spring Boot para projetos da Fábrica.', '2026-06-28')
ON CONFLICT (id) DO UPDATE SET id_oportunidade = EXCLUDED.id_oportunidade, id_discente = EXCLUDED.id_discente, status_inscricao = EXCLUDED.status_inscricao, motivacao = EXCLUDED.motivacao, created_at = EXCLUDED.created_at;

-- Insere Certificados
INSERT INTO certificado (id_certificado, uuid_hash, id_discente, id_oportunidade, horas, status_assinatura, data_emissao)
VALUES 
    (1, 'CERT-2026-PEDRO-SPRING', 5, 1, 20, 'ASSINADO', '2026-07-16')
ON CONFLICT (id_certificado) DO UPDATE SET uuid_hash = EXCLUDED.uuid_hash, id_discente = EXCLUDED.id_discente, id_oportunidade = EXCLUDED.id_oportunidade, horas = EXCLUDED.horas, status_assinatura = EXCLUDED.status_assinatura, data_emissao = EXCLUDED.data_emissao;

-- Ajusta as sequências de auto-incremento para evitar conflitos futuros em novas inserções
SELECT setval(pg_get_serial_sequence('usuario', 'id_usuario'), COALESCE(max(id_usuario), 1)) FROM usuario;
SELECT setval(pg_get_serial_sequence('papel', 'id_papel'), COALESCE(max(id_papel), 1)) FROM papel;
SELECT setval(pg_get_serial_sequence('curso', 'id_curso'), COALESCE(max(id_curso), 1)) FROM curso;
SELECT setval(pg_get_serial_sequence('ppc', 'id'), COALESCE(max(id), 1)) FROM ppc;
SELECT setval(pg_get_serial_sequence('tipo_oportunidade', 'id_tipo_oportunidade'), COALESCE(max(id_tipo_oportunidade), 1)) FROM tipo_oportunidade;
SELECT setval(pg_get_serial_sequence('grupo', 'id_grupo'), COALESCE(max(id_grupo), 1)) FROM grupo;
SELECT setval(pg_get_serial_sequence('oportunidade', 'id_oportunidade'), COALESCE(max(id_oportunidade), 1)) FROM oportunidade;
SELECT setval(pg_get_serial_sequence('inscricao', 'id'), COALESCE(max(id), 1)) FROM inscricao;
SELECT setval(pg_get_serial_sequence('certificado', 'id_certificado'), COALESCE(max(id_certificado), 1)) FROM certificado;