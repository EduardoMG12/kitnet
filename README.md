Kitnet - Plataforma de Imóveis
 <img width="60%"  src="./imagesREADME/palmas-horizontal-2.png"/>


Bem-vindo ao Kitnet, uma plataforma completa de imóveis projetada para conectar proprietários, corretores de imóveis e inquilinos/compradores. Este projeto visa simplificar o processo de listagem, busca e gerenciamento de propriedades, com foco em pequenas cidades. Desenvolvido como um trabalho do 5º período da faculdade de Sistemas de Informação - IFPR Campus Palmas, para as duas disciplinas ministrada respectivamente pelos professores: 
- SIS05502 - Programação de Software
  - [Eduardo Alba](https://www.linkedin.com/in/eduardo-luiz-alba-ab373a166/) | [Plano de Ensino](https://ava.ifpr.edu.br/pluginfile.php/629041/mod_resource/content/0/Plano%20de%20Ensino%20-%20PSA%20I.pdf)
- Aplicativos I e SIS05501 - Análise e Projeto de Sistemas.
  - [Marina Girolimetto](https://www.linkedin.com/in/marina-girolimetto-93b8b3b3/) | [Plano de Ensino](https://ava.ifpr.edu.br/pluginfile.php/618802/mod_resource/content/3/Pr%C3%A9via_Plano_Ensino.pdf)

Este projeto foi desenvolvido pelos dois estudantes:
- [Charles Eduardo](https://www.linkedin.com/in/eduardomg12/)
- [Willian Fragata]()

#### Sobre o Kitnet

Kitnet é uma plataforma para aluguel e venda de imóveis, desenvolvida com um foco especial na dinâmica de pequenas cidades. Ela oferece uma API robusta e uma interface web moderna para proporcionar uma experiência fluida a todos os usuários envolvidos no mercado imobiliário, sem, no entanto, lidar diretamente com transações financeiras de aluguel ou venda. O objetivo é profissionalizar as negociações imobiliárias que, em cidades pequenas, ainda dependem muito do "boca a boca".

Funcionalidades

  - Gestão de Usuários: Perfis dedicados para Locatários (Inquilinos), Locadores (Proprietários), Corretores de Imóveis e Administradores.

  - Autenticação: Login seguro com Email/Senha, Google e Apple ID (via Firebase Auth).

  - Anúncios de Propriedades: Anúncios detalhados de propriedades com opções personalizáveis para proprietários e agentes.

  - Sistema de Verificação: Sistema robusto de verificação de documentos e contas para aumentar a confiança.

  - Ferramentas de Comunicação: Agendamento de visitas. (Observação: O chat interno não está no escopo inicial do projeto).

  - Gamificação: Emblemas e conquistas para usuários ativos e com boa reputação.

<details>
  <summary style="position:relative;">Arquitetura </summary>

#### Arquitetura

O Kitnet é construído como uma aplicação multi-serviço, orquestrada com Docker Compose para desenvolvimento local.

    Frontend: Aplicação Next.js (Node.js).

    Backend: API RESTful Spring Boot (Java).

    Banco de Dados: MySQL.

    CDN Temporário: Nginx para servir arquivos estáticos (para desenvolvimento local; migrará para armazenamento em nuvem como AWS S3/CloudFront futuramente).

</details>

---

<details>
  <summary style="position:relative;">Pré-requisitos e Como rodar projeto</summary>


#### Para configurar e executar o projeto Kitnet localmente usando Docker Compose.

#### Pré-requisitos

Certifique-se de ter um dos seguinte instalado em sua máquina:

  - Docker Desktop (inclui Docker Engine e Docker Compose) 
  - Docker Engine e Docker Compose separadamente.

Estrutura do Projeto
```json

kitnet-project/
├── backend/                  # Aplicação Spring Boot Java
│   ├── src/                  # Código fonte Java
│   ├── Dockerfile            # Dockerfile para build do backend
│   ├── .env                  # Variáveis de ambiente para rodar o projeto localmente
│   ├── .env.docker           # Variáveis de ambiente para rodar o projeto no Docker
│   └── ...
├── frontend/                 # Aplicação Next.js
│   ├── public/               # Ativos estáticos
│   ├── Dockerfile            # Dockerfile para build do frontend
│   ├── .env                  # Variáveis de ambiente para rodar o projeto localmente
o projeto no Docker
│   └── ...
├── db/                       # Scripts de inicialização do banco de dados (opcional)
│   └── init.sql
├── cdn-temp/                 # CDN Nginx temporário para arquivos estáticos locais
│   ├── files/                # Diretório para arquivos estáticos (ex: imagens carregadas)
│   ├── nginx.conf            # Configuração do Nginx
│   └── Dockerfile            # Dockerfile para o CDN
├── docker-compose.yml        # Orquestra todos os serviços
└── .env                      # Variáveis de ambiente para Docker Compose e serviços

```
#### Variáveis de Ambiente (.env)

Basicamente existm 4 envs no projeto, o backend possui duas envs, o root e o frontend  possuem apenas uma. 
Voce pode utilizar o .env.*.example como exemplos de como deve ser a estrutura da env.
Crie um arquivo .env na raiz do seu projeto (kitnet-project/). Este arquivo contém informações sensíveis e parâmetros de configuração para seus serviços.
Snippet de código

# .env (na raiz do seu projeto)

```json

# Database Configurations (HIGHLY SENSITIVE!)
DATABASE_NAME=kitnet
DATABASE_URL=jdbc:mysql://localhost:3306/kitnet?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
DATABASE_USERNAME=root
DATABASE_ROOT_PASSWORD=root
DATABASE_PASSWORD=root
DATABASE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

# Server Configurations
SERVER_PORT=8081
# Variables of frontend (se houver)
FRONTEND_PORT=3001
# Variables of temp CDN 
CDN_TEMP_PORT=8080

```

Importante: Substitua os valores de placeholder como as portas das instancias, senhas e usuarios do banco de dados se for necessario.

# backend/.env (backend)

```json

SPRING_APPLICATION_NAME=kitnet-backend

# Application Configurations
APP_BASE_URL=http://localhost:8081
APP_TOKEN_EXPIRY_MINUTES=15
APP_EMAIL_FROM=seu.email@gmail.com

# Email Server (SMTP) Configurations
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu.email@gmail.com
MAIL_PASSWORD=passwordGenerateToApplications
MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
# Set to true if the SMTP server requires authentication
MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
# Enable STARTTLS for secure communication
MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=smtp.gmail.com

# Database Configurations (HIGHLY SENSITIVE!)
DATABASE_URL=jdbc:mysql://localhost:3306/kitnet?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
DATABASE_USERNAME=root
DATABASE_ROOT_PASSWORD=root
DATABASE_PASSWORD=root
DATABASE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configurations
JPA_HIBERNATE_DDL_AUTO=update
# 'update' for development, 'none' or 'validate' for production
JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL8Dialect
JPA_SHOW_SQL=true
# Display SQL queries in the console (disable in production)
JPA_FORMAT_SQL=true
# Format SQL queries (disable in production)

# Server Configurations
SERVER_PORT=8081

# Spring Security Configurations (In-memory User - HIGHLY SENSITIVE for production environments)
# THIS USER IS FOR EXAMPLE OR LOCAL DEVELOPMENT/TESTING PURPOSES ONLY.
# In a real system, user authentication should be handled via a database or identity provider.
SECURITY_USER_NAME=user
SECURITY_USER_PASSWORD=password

# JWT Configurations (HIGHLY SENSITIVE!)
# JWT_SECRET should be a long and random string. Use a secure key generator!
# DO NOT USE THIS VALUE IN PRODUCTION!
JWT_SECRET=umaChaveSecretaMuitoLongaEComplexaParaAssinarTokensJWTQueDeveSerGuardadaComMuitoCuidadoKkjasskalj
JWT_EXPIRATION=86400000
# JWT token expiration time in milliseconds (24 hours)

FIREBASE_SERVICE_ACCOUNT_PATH=kitnetapi-firebase-adminsdk-xxxxx-xxxxxxxxxx.json

# Variables of frontend (se houver)
FRONTEND_PORT=3000

# Variables of temp CDN 
CDN_TEMP_PORT=8080

# Upload path for backend
APP_UPLOAD_LOCAL_BASE_PATH=./cdn-temp/files
 # Path inside the cdn_files volume
APP_CDN_TEMP_URL=http://cdn_temp:80 
# Access cdn_temp service by its name within Docker network

```

# backend/.env.docker (backend rodando no docker)

```json

SPRING_APPLICATION_NAME=kitnet-backend

# Application Configurations
APP_BASE_URL=http://localhost:8081
APP_TOKEN_EXPIRY_MINUTES=15
APP_EMAIL_FROM=seu.email@gmail.com

# Email Server (SMTP) Configurations
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu.email@gmail.com
MAIL_PASSWORD=senhaGeradaPeloGmailParaAplicacoes
MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
# Set to true if the SMTP server requires authentication
MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
# Enable STARTTLS for secure communication
MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=smtp.gmail.com

# Database Configurations (HIGHLY SENSITIVE!)
DATABASE_URL=jdbc:mysql://db:3306/${DATABASE_NAME}?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
DATABASE_USERNAME=${DATABASE_USERNAME}
DATABASE_PASSWORD=${DATABASE_PASSWORD}
DATABASE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configurations
JPA_HIBERNATE_DDL_AUTO=update
JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL8Dialect
JPA_SHOW_SQL=true
JPA_FORMAT_SQL=true

# Server Configurations
SERVER_PORT=8081

# Spring Security Configurations (In-memory User - for development ONLY)
SECURITY_USER_NAME=user
SECURITY_USER_PASSWORD=password

# JWT Configurations
JWT_SECRET=umaChaveSecretaMuitoLongaEComplexaParaAssinarTokensJWTQueDeveSerGuardadaComMuitoCuidadoJKAsjkk
JWT_EXPIRATION=86400000

FIREBASE_SERVICE_ACCOUNT_PATH=kitnetapi-firebase-adminsdk-xxxxx-xxxxxxxxxx.json

# DevTools specific flags (copied from docker-compose, but now in its own file)
SPRING_PROFILES_ACTIVE=dev
SPRING_DEVTOOLS_RESTART_ENABLED=true
SPRING_DEVTOOLS_RESTART_POLL_INTERVAL=1000
SPRING_DEVTOOLS_RESTART_QUIET_PERIOD=500
SPRING_DEVTOOLS_REMOTE_SECRET=${SPRING_DEVTOOLS_REMOTE_SECRET}

# Upload path for backend
APP_UPLOAD_LOCAL_BASE_PATH=../usr/share/nginx/html
 # Path inside the cdn_files volume
APP_CDN_TEMP_URL=http://localhost:8080 
# Access cdn_temp service by its name within Docker network

```

# frontend/.env (frontend)

```json

# frontend/.env.docker (for 'frontend' service)

# Backend API URL (accessing 'backend' service by name)
NEXT_PUBLIC_BACKEND_API_URL=http://backend:8081/api

# Temporary CDN URL (accessing 'cdn_temp' service by name)
NEXT_PUBLIC_CDN_TEMP_URL=http://cdn_temp:80

# Next.js environment (for dev mode)
NODE_ENV=development

```

Executando com Docker Compose

    Crie seu arquivo de conta de serviço Firebase: Baixe seu arquivo JSON de conta de serviço do Firebase Console (Configurações do projeto > Contas de serviço > Gerar nova chave privada) e coloque-o em backend/src/main/resources/. Atualize o FIREBASE_SERVICE_ACCOUNT_PATH em seu .env de acordo.


Navegue até o diretório raiz: Abra seu terminal e vá para o diretório kitnet-project/ onde seus arquivos docker-compose.yml e .env estão localizados.

Inicie os serviços:
```Bash

    docker-compose up --build

    # A flag --build garante que as imagens Docker sejam reconstruídas, capturando quaisquer alterações em seus Dockerfiles ou código. Para execuções subsequentes onde você alterou apenas o código do aplicativo (e não os Dockerfiles ou dependências), você pode omitir --build para uma inicialização mais rápida (docker-compose up).

    # A flag -d remove os logs no console

```

Caso esteja com duvida se as instancias estao rodando corretamente, utilize o comando para verificar:

```Bash

docker ps

```

</details>

--- 

<details>
  <summary style="position:relative;">Acessando os Serviços </summary>

#### Acessando os Serviços

Uma vez que todos os serviços estejam funcionando:

    Frontend (Next.js): Acesse sua aplicação no navegador em http://localhost:${FRONTEND_PORT} (ex: http://localhost:3000).

    Backend (Spring Boot API): A API é acessível em http://localhost:${SERVER_PORT}/api (ex: http://localhost:8081/api).

    CDN Temporário (Nginx): Arquivos estáticos (se houverem, colocados manualmente em cdn-temp/files/ para teste) podem ser acessados em http://localhost:${CDN_TEMP_PORT} (ex: http://localhost:8080).

    Banco de Dados MySQL: Você pode se conectar ao banco de dados MySQL de sua máquina host usando localhost:3306 com as credenciais definidas em seu .env.
</details>

--- 

<details>
  <summary style="position:relative;">Desafios de Implementação e Soluções e Fluxo de Desenvolvimento </summary>

#### Desafios de Implementação e Soluções

Durante o desenvolvimento do Kitnet, enfrentamos vários desafios comuns a projetos de software complexos. Aqui estão alguns dos mais notáveis e as abordagens que utilizamos para superá-los:

- Dificuldade inicial na modelagem de banco: inicialmente ocorreram impasses na modelagem do db pois parecia estar tudo perfeito porem quanto maior ficava a aplicao mais iam aparecendo problemas, tendo horas que tivemos que parar e pensar melhor para ver se aquele diagrama era o mais adequado para nossos problemas.
- Configuração de Ambiente com Docker Compose: A orquestração de múltiplos serviços (frontend, backend, banco de dados, CDN) com Docker Compose apresentou uma curva de aprendizado inicial. Desafios incluíram a configuração correta de redes, volumes persistentes para o banco de dados e variáveis de ambiente dinâmicas entre os serviços. A solução envolveu a criação de um arquivo docker-compose.yml robusto e um gerenciamento centralizado das variáveis de ambiente no arquivo .env da raiz.
- Upload e Gerenciamento de Arquivos Estáticos: Decidir onde e como armazenar imagens e documentos de forma eficiente e escalável foi um ponto crítico. Para o desenvolvimento, optamos por um CDN temporário baseado em Nginx que foi muito dificil a implementacao pois primeiramente apos muito esforco rodou no computador de um desenvolvedor mas nao rodou no computador do outro desenvolvedor =. A transição futura para soluções de armazenamento em nuvem (ex: AWS S3) resolverá os desafios de escalabilidade e latência em produção.

--- 

#### Desafios de Implementação e Soluções e Fluxo de Desenvolvimento (Hot-Reloading)

O hot-reloading está configurado para uma experiência de desenvolvimento mais fluida:

    Frontend (Next.js): Ao salvar alterações no código do frontend, o contêiner do frontend as detectará automaticamente e recompilará, refletindo as atualizações em seu navegador imediatamente.

    Backend (Spring Boot Java): Ao salvar alterações em seu código Java na IDE, a IDE as compilará automaticamente. O Spring Boot DevTools, executando dentro do contêiner do backend, detectará essas alterações compiladas através de volumes montados e reiniciará a aplicação automaticamente. Você verá mensagens como "Restarting main application..." nos logs do contêiner do backend.

</details>

---

<details>
  <summary style="position:relative;">Documento de Software </summary>

#### Documento de Software

Este projeto é acompanhado por um Documento de Software detalhado, que serve como um artefato crucial para a disciplina SIS05501 - Análise e Projeto de Sistemas. Este documento abrange os seguintes aspectos:

- Requisitos Funcionais e Não Funcionais: Uma análise aprofundada das necessidades do sistema, incluindo o que o Kitnet deve fazer (funcionalidades) e como ele deve se comportar (desempenho, segurança, usabilidade, etc.).

- Diagramas de Arquitetura: Representações visuais da estrutura do sistema, mostrando como os diferentes componentes (frontend, backend, banco de dados, CDN) se interagem. Inclui diagramas de componentes, implantação e visão geral da arquitetura.

- Diagramas de Casos de Uso: Ilustra as interações dos usuários com o sistema, descrevendo as funcionalidades a partir da perspectiva do ator.

- Modelagem de Dados (Diagrama de Entidade-Relacionamento): O design do banco de dados, mostrando as entidades e seus relacionamentos, garantindo a integridade e eficiência dos dados.

- Planejamento de Testes: Estratégias e planos para garantir a qualidade do software, cobrindo testes unitários, de integração e de sistema.

- Gerenciamento de Projeto: Cronogramas, atribuições de tarefas e metodologias utilizadas para gerenciar o ciclo de vida do desenvolvimento.

O Documento de Software pode ser encontrado no arquivo Documento Software KitNet - Analise e Projeto de Sistemas.pdf na raiz deste repositório. Ele é fundamental para entender as decisões de design, a lógica de negócios e a estrutura geral do projeto Kitnet.


</details>

--- 

<details>
  <summary style="position:relative;">Planos Futuros </summary>

#### Planos Futuros

O Kitnet é um projeto universitário com planos futuros ambiciosos, incluindo:

    Migrar o CDN temporário para uma solução nativa de nuvem (ex: AWS S3 + CloudFront).

    Implementar estratégias avançadas de monetização, como links de afiliados, cupons e opções de impulsionamento em níveis para propriedades e perfis de usuário.

    Expandir as funcionalidades de comunicação e negociação.

    Desenvolver o recurso de avaliações ou notas para o usuário.

    Implementar um painel de administração com gráficos e dashboards.

    Adicionar gerenciamento financeiro, como geração de boletos ou relatórios contábeis.

</details>

---

<details>
  <summary style="position:relative;">Estratégia de Monetização </summary>

#### Estratégia de Monetização

A monetização do Kitnet foca em serviços de valor agregado, sem tocar no dinheiro das transações de aluguel ou venda:

    Assinaturas Premium: Para corretores de imóveis, oferecendo recursos aprimorados e maior visibilidade.

    Impulsionamento de Propriedades: Promoção paga para aumentar a visibilidade de anúncios de propriedades nos resultados de pesquisa.

    Integração de Serviços de Terceiros: Parceria com provedores de serviços como limpeza, fotografia ou assistência jurídica, gerando comissões através de indicações.

    Insights de Dados: Potencial oferta de dados de mercado anonimizados e insights.

</details>