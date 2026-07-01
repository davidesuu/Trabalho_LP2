# UFMA Extensão

#### Grupo: David Martins, Nicolas Caliman, Perla Sousa e Samantha Pereira

Sistema de gerenciamento de atividades de extensão universitária da UFMA, desenvolvido como trabalho da disciplina de Linguagem de Programação 2. Permite o cadastro e controle de usuários (docentes e discentes), grupos de extensão, oportunidades (eventos, cursos, projetos, oficinas), inscrições, aproveitamento de horas externas e certificados.

---

## Tecnologias

| Tecnologia             | Versão |
| ---------------------- | ------ |
| Java                   | 21     |
| Spring Boot            | 4.1.0  |
| Spring Data JPA        | -      |
| Spring Security Crypto | -      |
| PostgreSQL             | -      |
| Lombok                 | -      |
| Maven                  | -      |

---

## Estrutura do Projeto

```
src/
└── main/
    ├── java/com/exemplo/ufmaextensao/
    │   ├── Controller/       # Camada de entrada (endpoints REST)
    │   ├── DTO/              # Objetos de transferência de dados
    │   ├── Enum/             # Enumerações do sistema
    │   ├── entity/           # Entidades JPA (mapeamento de tabelas)
    │   ├── repository/       # Interfaces de acesso ao banco (Spring Data)
    │   └── service/          # Regras de negócio
    └── resources/
        ├── application.properties   # Configurações da aplicação
        └── data.sql                 # Dados iniciais do banco
```

---

## Configuração e Execução

### Pré-requisitos

- Java 21+
- Maven (ou use o `mvnw` incluso)
- PostgreSQL em execução (local ou remoto, ex.: Supabase)

### 1. Configurar o banco de dados

Edite o arquivo `application.properties`:

```properties
spring.sql.init.mode=always
```

Dessa forma o banco já vem populado.

> Após a primeira execução pode voltar ele para never

### 2. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### 3. Popular dados iniciais

O arquivo `data.sql` já possui dados iniciais (papéis, tipos de oportunidade, cursos, usuários de teste, grupo PETComp, oportunidades abertas e finalizadas, inscrição, certificado e aproveitamento de exemplo).

**Usuários de teste:**

| Nome             | Email          | Senha       | Papel                |
| ---------------- | -------------- | ----------- | -------------------- |
| João Docente     | joao@ufma.br   | `joao123`   | COORDENADOR, DOCENTE |
| Maria Discente   | maria@ufma.br  | `maria123`  | D_DIRETOR            |
| Ana Coordenadora | ana@ufma.br    | `ana123`    | COORDENADOR, DOCENTE |
| Carlos Admin     | carlos@ufma.br | `carlos123` | ADMIN                |
| Pedro Tesoureiro | pedro@ufma.br  | `pedro123`  | TESOUREIRO           |
| Lara Membro      | lara@ufma.br   | `lara123`   | MEMBRO               |
| Bruno Secretário | bruno@ufma.br  | `bruno123`  | SECRETARIO           |

**Grupos Iniciais:**

- O sistema já vem populado com o grupo **PETComp**, o qual os usuários acima podem interagir nos testes.

---

## Perfis de Acesso (Papéis)

| Papel         | Descrição                                   |
| ------------- | ------------------------------------------- |
| `ADMIN`       | Administrador do sistema                    |
| `COORDENADOR` | Pode criar grupos e oportunidades           |
| `DOCENTE`     | Responsável por grupos e oportunidades      |
| `D_DIRETOR`   | Discente com cargo de direção em um grupo   |
| `TESOUREIRO`  | Discente com cargo de tesouraria no grupo   |
| `SECRETARIO`  | Discente com cargo de secretaria no grupo   |
| `PRESIDENTE`  | Discente com função de presidência no grupo |
| `MEMBRO`      | Integrante do grupo                         |

---

## Endpoints da API

### Usuários — `/usuario`

| Método | Endpoint                           | Descrição                              | Parâmetros                                    |
| ------ | ---------------------------------- | -------------------------------------- | --------------------------------------------- |
| `POST` | `/usuario/login`                   | Autentica um usuário                   | Body: `{ email, senha }`                      |
| `POST` | `/usuario/criarDiscente`           | Cadastra um novo discente              | `?usuarioId` `?cursoId` + Body: `DiscenteDTO` |
| `POST` | `/usuario/criarDocente`            | Cadastra um novo docente               | `?usuarioId` + Body: `DocenteDTO`             |
| `GET`  | `/usuario/listarUsuarios`          | Lista todos os usuários                | —                                             |
| `GET`  | `/usuario/painelHoras/{usuarioId}` | Exibe o painel de horas de um discente | Path: `usuarioId`                             |
| `PUT`  | `/usuario/{id}/desativar`          | Desativa um usuário                    | Path: `id`, `?adminId`                        |
| `PUT`  | `/usuario/{id}/reativar`           | Reativa um usuário                     | Path: `id`, `?adminId`                        |

---

### Grupos — `/grupo`

| Método | Endpoint           | Descrição                                   | Parâmetros                                     |
| ------ | ------------------ | ------------------------------------------- | ---------------------------------------------- |
| `POST` | `/grupo/criar`     | Cria um novo grupo de extensão              | `?usuarioId` `?docenteId` + Body: `GrupoDTO`   |
| `POST` | `/grupo/adicionar` | Adiciona um discente ao grupo               | `?grupoId` `?discenteId` `?docenteId`          |
| `POST` | `/grupo/remover`   | Remove um discente do grupo                 | `?grupoId` `?discenteId` `?docenteId`          |
| `POST` | `/grupo/promover`  | Promove um discente a um cargo na diretoria | `?grupoId` `?discenteId` `?docenteId` `?cargo` |
| `POST` | `/grupo/rebaixar`  | Remove um discente da diretoria do grupo    | `?grupoId` `?discenteId` `?docenteId` `?cargo` |

> Todas as operações de grupo geram um registro na tabela `log` com o autor, o discente afetado, o grupo e o cargo.

---

### Oportunidades — `/oportunidade`

| Método | Endpoint                                                           | Descrição                                  | Parâmetros                                                           |
| ------ | ------------------------------------------------------------------ | ------------------------------------------ | -------------------------------------------------------------------- |
| `POST` | `/oportunidade/criar`                                              | Cria uma nova oportunidade                 | `?usuarioId` `?tipo` `?grupoId` (opcional) + Body: `OportunidadeDTO` |
| `GET`  | `/oportunidade/{oportunidadeId}`                                   | Busca oportunidade por ID                  | Path: `oportunidadeId`                                               |
| `GET`  | `/oportunidade/publicadas`                                         | Lista oportunidades publicadas             | —                                                                    |
| `GET`  | `/oportunidade/pendentes`                                          | Lista oportunidades pendentes de aprovação | —                                                                    |
| `PUT`  | `/oportunidade/publicar/{oportunidadeId}`                          | Publica uma oportunidade                   | Path: `oportunidadeId`, `?responsavelId`                             |
| `PUT`  | `/oportunidade/rejeitar/{oportunidadeId}`                          | Rejeita uma oportunidade                   | Path: `oportunidadeId`, `?responsavelId`                             |
| `PUT`  | `/oportunidade/finalizarOportunidade/{usuarioId}/{oportunidadeId}` | Finaliza uma oportunidade                  | Path: `usuarioId`, `oportunidadeId`                                  |

**Tipos de oportunidade disponíveis:** `EVENTO`, `OFICINA`, `CURSO`, `PROJETO`

---

### Inscrições — `/inscricao`

| Método  | Endpoint                                        | Descrição                                          | Parâmetros                                             |
| ------- | ----------------------------------------------- | -------------------------------------------------- | ------------------------------------------------------ |
| `POST`  | `/inscricao/criar`                              | Cria uma inscrição em uma oportunidade             | `?oportunidadeId` `?discenteId` + Body: `InscricaoDTO` |
| `GET`   | `/inscricao/{inscricaoId}`                      | Busca inscrição por ID                             | Path: `inscricaoId`                                    |
| `GET`   | `/inscricao/pendentes/{oportunidadeId}`         | Lista inscrições pendentes de uma oportunidade     | Path: `oportunidadeId`                                 |
| `GET`   | `/inscricao/discente/{discenteId}`              | Lista todas as inscrições de um discente           | Path: `discenteId`                                     |
| `GET`   | `/inscricao/discente/{discenteId}/ativas`       | Lista inscrições aprovadas e pendentes do discente | Path: `discenteId`                                     |
| `GET`   | `/inscricao/oportunidade/{oportunidadeId}`      | Lista todas as inscrições de uma oportunidade      | Path: `oportunidadeId`                                 |
| `PATCH` | `/inscricao/aprovar/{inscricaoId}/{docenteId}`  | Aprova uma inscrição                               | Path: `inscricaoId`, `docenteId`                       |
| `PATCH` | `/inscricao/rejeitar/{inscricaoId}/{docenteId}` | Rejeita uma inscrição                              | Path: `inscricaoId`, `docenteId`                       |
| `PATCH` | `/inscricao/cancelar/{inscricaoId}`             | Cancela uma inscrição                              | Path: `inscricaoId`                                    |

**Status possíveis de inscrição:** `PENDENTE`, `APROVADA`, `REJEITADA`, `CANCELADA`

---

### Aproveitamento — `/aproveitamento`

| Método | Endpoint                                | Descrição                                  | Parâmetros                                |
| ------ | --------------------------------------- | ------------------------------------------ | ----------------------------------------- |
| `POST` | `/aproveitamento/solicitar`             | Solicita aproveitamento de horas externas  | `?discenteId` + Body: `AproveitamentoDTO` |
| `PUT`  | `/aproveitamento/{id}/aprovar`          | Aprova uma solicitação de aproveitamento   | Path: `id`, `?avaliadorId`                |
| `PUT`  | `/aproveitamento/{id}/indeferir`        | Indeferi uma solicitação de aproveitamento | Path: `id`, `?avaliadorId`, `?motivo`     |
| `PUT`  | `/aproveitamento/{id}/cancelar`         | Cancela uma solicitação de aproveitamento  | Path: `id`, `?discenteId`                 |
| `GET`  | `/aproveitamento/pendentes`             | Lista solicitações pendentes               | —                                         |
| `GET`  | `/aproveitamento/discente/{discenteId}` | Lista aproveitamentos de um discente       | Path: `discenteId`                        |

---

### Certificados — `/certificado`

| Método | Endpoint                        | Descrição                                  | Parâmetros                            |
| ------ | ------------------------------- | ------------------------------------------ | ------------------------------------- |
| `PUT`  | `/certificado/assinar`          | Assina um certificado                      | `?docenteId` + Body: `CertificadoDTO` |
| `GET`  | `/certificado/pendentes`        | Lista certificados pendentes de assinatura | —                                     |
| `GET`  | `/certificado/assinados`        | Lista certificados assinados               | —                                     |
| `GET`  | `/certificado/validar/{codigo}` | Valida um certificado a partir do código   | Path: `codigo`                        |

---

## Modelo de Dados (Principais Entidades)

```
Usuario (superclasse)
├── Docente  → possui SIAPE e departamento
└── Discente → possui matrícula, semestre, banco de horas e curso

Grupo
├── Responsável (Docente)
├── Discentes (membros)
└── Diretoria (discentes com cargo)

Oportunidade
├── Tipo (TipoOportunidade)
├── Responsável (Docente)
└── Autores (Usuarios)

Inscricao
├── Oportunidade
└── Discente

Aproveitamento
├── Discente
├── Descrição
├── Instituição
├── Horas
├── Status (PENDENTE, APROVADO, INDEFERIDO, CANCELADO)
└── Certificado (caminho)

Certificado
├── Discente
├── Oportunidade
├── Horas
└── Status de assinatura (PENDENTE, ASSINADO)

Log                          ← registro de operações em grupos
├── Autor (nome + SIAPE)
├── Afetado (nome + matrícula)
├── Grupo (nome)
├── Cargo
├── Operação (ADICIONAR | REMOVER | PROMOVER | REBAIXAR)
└── Data/Hora
```
