# Sistema de Gestão de Oportunidades Acadêmicas

Sistema desenvolvido em Java puro para gerenciamento de oportunidades acadêmicas, inscrições, aproveitamentos e certificados em ambiente universitário.


## Arquitetura

O projeto segue a separação em quatro camadas:

**Entity** — representa os dados do domínio e contém regras simples de negócio sobre si mesma.

**Repository** — responsável pelo acesso aos dados, representando um banco de dados

**Service** — orquestra as regras de negócio, coordena repository e entities. 

**Tela** - Criado pra organizar cada tela de interface no terminal

## O que é possivel fazer no momento:
**Discente:**
1.Ver e se inscrever em uma oportunidade
2.Solicitar aproveitamento

**Discente Diretor:**
1.Tudo de discente
2.Criar Oportunidade pendentes

**Docente:**
1.Criar Oportunidades Aprovadas
2.Aprovar ou Rejeitar oportunidades e aproveitamentos

##O que falta implementar já feito:##
1.Gerar os certificados e usar a funçao GerarQrCode
