### Projeto Kotlin com Spring Boot

Este é um projeto de estudo utilizando Kotlin com Spring Boot, configurado com Gradle e diversas tecnologias importantes para desenvolvimento web e persistência de dados. O projeto utiliza o banco de dados PostgreSQL, integração com o Flyway para migração de banco de dados, segurança com JWT (JSON Web Token) e outras bibliotecas essenciais do ecossistema Spring.

#### Tecnologias Utilizadas

- **Spring Boot**: Framework de aplicação Java/Kotlin baseado em convenção sobre configuração.
- **Gradle**: Gerenciador de dependências e automação de builds.
- **Kotlin**: Linguagem moderna e concisa para desenvolvimento JVM.
- **Spring Data JPA**: Facilita a implementação de repositórios baseados em JPA.
- **Hibernate**: Framework de mapeamento objeto-relacional (ORM) para JPA.
- **Flyway**: Gerenciador de migração de banco de dados.
- **PostgreSQL**: Banco de dados relacional.
- **JWT (JSON Web Token)**: Para autenticação stateless.
- **Spring Security**: Oferece segurança para aplicações Spring.
- **Jackson Kotlin Module**: Facilita a serialização e desserialização de objetos Kotlin para JSON.
- **Lombok**: Biblioteca que automatiza a geração de código repetitivo.
- **JUnit 5**: Framework de testes para Java/Kotlin.

#### Estrutura do Projeto

O projeto está estruturado com os seguintes módulos principais:

- **Configuração do Spring Security**: Configurações para autenticação e autorização.
- **Configuração do Flyway**: Migrações de banco de dados utilizando Flyway.
- **Entidades e Repositórios**: Definição de entidades JPA e seus respectivos repositórios.
- **Controladores**: Endpoints REST para comunicação com o frontend ou outras aplicações.
- **Serviços**: Lógica de negócio e serviços que interagem com os repositórios.
- **Filtros de Segurança**: Implementação de filtros para segurança customizada.




#### Tecnologias utilizadas no projeto

![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.6.4-green)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-blue)
![Gradle](https://img.shields.io/badge/Gradle-7.3.1-orange)
![Hibernate](https://img.shields.io/badge/Hibernate-5.6.7-yellow)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.5.0-blue)
![JWT](https://img.shields.io/badge/JWT-4.4.0-red)
![Flyway](https://img.shields.io/badge/Flyway-9.21.0-lightgrey)


### Configuração

Para executar o projeto localmente, você precisa configurar um banco de dados PostgreSQL. Certifique-se de ter o JDK 17 instalado e configurado corretamente. Em seguida, execute o seguinte comando:

```bash
./gradlew bootRun
