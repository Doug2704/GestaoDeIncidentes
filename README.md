# Gestão de Incidentes
Software para gestão de incidentes em empresas.
Está sob a licensa MIT - livre para qualquer finalidade. Reservado o direito de autoria.   
![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)

## Tecnologias Usadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)  
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)


## Conteúdo

- [Introdução](#Introdução)
- [Instalação](#Instalação)
- [Uso](#Uso)
- [Banco de Dados](#banco-de-dados)
- [Contribuição](#contribuição)

## Introdução

Esse projeto tem como finalidade gerenciar incidentes em empresas, a fim de documentar medidas a serem tomadas de acordo com o evento ocorrido.
Desenvolvido iniciamente, com a colaboração de Josué Soares (https://github.com/Doutrinador08) e Jackson Santos () para conclusão das UCs Processos e Projetos de TI e Governança e Serviços de TI, onde abordamos conceitos de ITIL, com toda a documentação do projeto e do processo elaborada previamente.
A aplicação poderá integrar qualquer front-end desenvolvido para esse fim, desde que atenda os requisitos de consumo de APIs Rest.

## Instalação

1. Instale o Java na versão 17.
2. Instale sua IDE favorita (recomendo Intellij IDEA).
3. Instale o PostgreSQL localmente.
4. Instale o Postman.

5. Clone o repositório localmente:

```bash
git clone https://github.com/Doug2704/GestaoDeIncidentes.git
```

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.1. Para os colaboradores oficiais do projeto, será disponibilizada a branch dev para envio de alterações. 
Então, após clonar o repositário, execute os comandos:

```bash
git checkout -b dev origin/dev
git branch --set-upstream-to=origin/dev
git pull

```

## Uso 

### (Para Desenvolvedores)

1. Abra o projeto em sua IDE preferida (recomendo Intellij IDEA para Java) ou via terminal de comando.

2. A aplicação estará acessível em http://localhost:8080 em sua máquina ou na porta que for configurada.
**obs**: Verifique se há alguma aplicação utilizando a referida porta localmente e altere conforme a necessidade.
3. Execute a aplicação.
4. Altere o código conforme a necessidade e configure as requisições no controlador. Por exemplo "api/v1/users" para listar usuários.
3.1. Teste as requisições via Postman (ou testador de API de sua escolha). Por exemplo: "http://localhost:8080/api/v1/users" para consumir a requisição.
3.2. Em caso de documentação Swagger, as requisições poderãm ser testadas em "http://localhost:8080/swagger-ui/index.html"

### (Para Usuários)

Com a aplicação em execução , basta fornecer os parâmetros requeridos (por exemplo: "nome", "descrição") e realizar as consultas.

## Banco de Dados
O projeto utiliza o  <span style="color:#0366d6;">PostgreSQL</span> como banco de dados.

## Contribuição

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra uma issue ou envie um pull request para o repositório.

Ao contribuir para este projeto, siga o estilo de código existente, as [convenções de commit](https://www.conventionalcommits.org/en/v1.0.0/) e envie suas alterações em uma branch separada.

