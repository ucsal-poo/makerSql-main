# SQL Maker
## Descrição
O SQL Maker é uma biblioteca Java que facilita a geração de consultas SQL dinâmicas a
partir de classes Java anotadas. Ele permite criar consultas de seleção, inserção, exclusão
e atualização de forma mais simples e flexível, levando em consideração as anotações
presentes nas classes Java.
## Instruções de Uso
1. Certifique-se de ter configurado corretamente o ambiente de desenvolvimento Java.
2. Execute o arquivo Main.java.
## Dependências
```java
<dependencies>
        <dependency>
            <groupId>com.sql</groupId>
            <artifactId>maker</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>github</id>
            <name>My Maven Repo on GitHub</name>
            <url>https://github.com/DanielTM999/makerSql/</url>
        </repository>
    </repositories>
```
- O projeto depende da biblioteca `sql-core` para a funcionalidade de geração de consultas
SQL.
- Todas as dependências necessárias devem ser inclusas no classpath antes de executar o
projeto.
# Main
## Descrição
O arquivo Main.java representa o ponto de entrada principal do projeto. Ele demonstra a
inicialização básica do sistema e a utilização de funcionalidades relacionadas à geração de
consultas SQL.
## Funcionalidades
- Utilização da fábrica `SqlGenFactory` para criar uma instância de `SqlMakerExtended`
- Geração de uma consulta SQL de seleção utilizando o método `generateSelectSql`
## Exemplo de Uso
```java
public class Main {
public static void main(String[] args) {
// Inicializa a fábrica de geração de consultas SQL
SqlGenFactory factory = new SqlGenFactory();
// Cria uma instância de SqlMakerExtended
SqlMaker sqlMaker = factory.createSqlMakerExtended(null);
// Gera uma consulta SQL de seleção
sqlMaker.generateSelectSql(null, "OR", "username", "email");
}
}
```
# Executors
# ConnectionInfo
## Descrição
A classe `ConnectionInfo` fornece métodos para configurar os componentes necessários
para estabelecer uma conexão com o banco de dados.
## Uso
- Utilize os métodos para definir os parâmetros de conexão, como host, porta, banco de
dados, usuário, senha e tipo de banco de dados.
- Os métodos `getHost`, `getPort`, `getDatabase`, `getUser`, `getPassword` e `getDbType`
são usados para acessar os valores configurados.
# ConnFactory
## Descrição
A classe `ConnFactory` é responsável por criar instâncias de conexões SQL e extensões de
conexões.
# ExecutorsData
## Descrição
A classe `ExecutorsData` implementa métodos para executar consultas SQL no banco de
dados.
# ExecutorsFactoryData
## Descrição
A classe `ExecutorsFactoryData` é uma fábrica de instâncias de executores SQL,
responsável por criar instâncias de executores para consultas SQL simples e estendidas.
# SqlConnectionData
## Descrição
A classe `SqlConnectionData` implementa a interface `SqlConnectionExtended` para
fornecer funcionalidades de conexão com o banco de dados.
## Funcionalidades
- Gera conexões com o banco de dados com base em diferentes configurações.
- Implementa métodos para gerar conexões individuais e em pool.
- Utiliza métodos para criar e gerenciar conexões com o banco de dados.
# SqlConnectionDataSingle
## Descrição
A classe `SqlConnectionDataSingle` implementa a interface `SqlConnection` para fornecer
funcionalidades de geração de conexão com o banco de dados.
## Funcionalidades
- Gera uma única conexão com o banco de dados com base em diferentes configurações.
- Implementa métodos para gerar conexões individuais usando configurações específicas.
- Utiliza métodos auxiliares para criar e configurar corretamente a conexão com o banco de
dados.
---
# Makers
# DbContext
## Descrição
A classe `DbContext` estende a classe abstrata `ContextHolder`, fornecendo
funcionalidades para gerenciar o contexto das classes no banco de dados.
## Funcionalidades
- Implementa métodos para adicionar classes ao contexto e obter o contexto atual.
- Usa uma lista para armazenar as classes no contexto do banco de dados.
# IdType
## Descrição
O enum `IdType` define os tipos de identificação para chaves primárias em entidades no
banco de dados.
## Funcionalidades
- Define dois tipos de identificação: `AUTO_INCREMENT` e `NONE`.
- `AUTO_INCREMENT`: Indica que a chave primária é gerada automaticamente pelo banco
de dados.
- `NONE`: Indica que não há identificação automática para a chave primária.
# SqlGenerator
## Descrição
A classe `SqlGenerator` implementa a interface `SqlMakerExtended`, fornecendo
funcionalidades para gerar consultas SQL dinamicamente com base nas classes de
entidades do banco de dados.
## Funcionalidades
- Gera consultas SQL para SELECT, INSERT, UPDATE e DELETE.
- Suporta operações de agregação como SUM e AVG.
- Permite a geração de consultas SQL com cláusulas WHERE e ORDER BY.
- Gerencia o cache de consultas SQL para reutilização.
- Lida com anotações como @Entity, @Table, @Id, @Column e @OrderBy para
mapeamento objeto-relacional.
# SqlGenFactory
## Descrição
A classe `SqlGenFactory` implementa a interface `SqlmakerFactory`, fornecendo métodos
para criar instâncias de `SqlMaker` e `SqlMakerExtended` com base no tipo de banco de
dados.
## Funcionalidades
- Cria instâncias de `SqlGenerator` para diferentes tipos de bancos de dados.
# SqlQueryData
## Descrição
A classe `SqlQueryData` estende a classe `SqlResponse` e representa uma resposta que
contém uma consulta SQL e informações relacionadas.
## Funcionalidades
- Armazena uma consulta SQL e informações adicionais.
- Fornece métodos para acessar e manipular a consulta SQL e as informações internas.
---
# PA
# ProxyControll
## Descrição
A classe `ProxyControll` é responsável por controlar operações em um repositório, como
salvar, excluir, e encontrar entidades. Ela recebe informações essenciais, como a interface
do repositório, a classe de ID, a classe do repositório, uma conexão SQL, um gerador de
consultas SQL estendido e um executor SQL.
## Funcionalidades
- Implementa métodos para executar operações no repositório, como salvar, excluir,
encontrar todas as entidades e encontrar por ID.
- Utiliza a classe `SqlMakerExtended` para gerar consultas SQL, como a consulta para
encontrar todas as entidades.
- As operações executadas são exibidas no console para fins de depuração.
# RepositoryData
## Descrição
A classe `RepositoryData` é responsável por inicializar e criar proxies para os repositórios.
Ela implementa a interface `RepositoryInitializer` e utiliza reflexão para determinar o tipo de
repositório com base nos argumentos genéricos. Além disso, cria instâncias do controle do
repositório (`ProxyControll`) e retorna proxies dinâmicos para os repositórios.
## Funcionalidades
- Inicializa e cria proxies para os repositórios baseados em interfaces.
- Utiliza reflexão para determinar o tipo de repositório e a classe de ID com base nos
argumentos genéricos.
- Cria instâncias do controle do repositório e as associa com os proxies gerados.
- Fornece um método estático para criar proxies de forma simplificada.
---
## Como Usar
### Anotações
- `@Column`
- `@Entity`
- `@Id`
- `@OrderBy`
- `@Table`
### Classes Principais
- **`SqlGenerator`**
- **`SqlGenFactory`**
- **`SqlQueryData`**
## Exemplo de Criação de Entidade
```java
package com.example.model;
import com.sql.anotations.Column;
import com.sql.anotations.Entity;
import com.sql.anotations.Id;
import com.sql.anotations.Table;
import com.sql.data.maker.IdType;
@Entity
@Table(name = "users")
public class MyEntity {
@Id
@Column(CollumName = "user_id")
private Long userId;
@OrderBy
@Column(CollumName = "username")
private String username;
@Column(CollumName = "email")
private String email;
// Construtores, getters e setters aqui...
}
```
## Exemplo de Uso
```java
// Criar uma fábrica de Sq# SQL Maker
## Descrição
O SQL Maker é uma biblioteca Java que facilita a geração de consultas SQL dinâmicas a
partir de classes Java anotadas. Ele permite criar consultas de seleção, inserção, exclusão
e atualização de forma mais simples e flexível, levando em consideração as anotações
presentes nas classes Java.
## Instruções de Uso
1. Certifique-se de ter configurado corretamente o ambiente de desenvolvimento Java.
2. Execute o arquivo Main.java.
## Dependências
- O projeto depende da biblioteca `sql-core` para a funcionalidade de geração de consultas
SQL.
- Todas as dependências necessárias devem ser inclusas no classpath antes de executar o
projeto.
# Main
## Descrição
O arquivo Main.java representa o ponto de entrada principal do projeto. Ele demonstra a
inicialização básica do sistema e a utilização de funcionalidades relacionadas à geração de
consultas SQL.
## Funcionalidades
- Utilização da fábrica `SqlGenFactory` para criar uma instância de `SqlMakerExtended`
- Geração de uma consulta SQL de seleção utilizando o método `generateSelectSql`
## Exemplo de Uso
```java
public class Main {
public static void main(String[] args) {
// Inicializa a fábrica de geração de consultas SQL
SqlGenFactory factory = new SqlGenFactory();
// Cria uma instância de SqlMakerExtended
SqlMaker sqlMaker = factory.createSqlMakerExtended(null);
// Gera uma consulta SQL de seleção
sqlMaker.generateSelectSql(null, "OR", "username", "email");
}
}
```
# Executors
# ConnectionInfo
## Descrição
A classe `ConnectionInfo` fornece métodos para configurar os componentes necessários
para estabelecer uma conexão com o banco de dados.
## Uso
- Utilize os métodos para definir os parâmetros de conexão, como host, porta, banco de
dados, usuário, senha e tipo de banco de dados.
- Os métodos `getHost`, `getPort`, `getDatabase`, `getUser`, `getPassword` e `getDbType`
são usados para acessar os valores configurados.
# ConnFactory
## Descrição
A classe `ConnFactory` é responsável por criar instâncias de conexões SQL e extensões de
conexões.
# ExecutorsData
## Descrição
A classe `ExecutorsData` implementa métodos para executar consultas SQL no banco de
dados.
# ExecutorsFactoryData
## Descrição
A classe `ExecutorsFactoryData` é uma fábrica de instâncias de executores SQL,
responsável por criar instâncias de executores para consultas SQL simples e estendidas.
# SqlConnectionData
## Descrição
A classe `SqlConnectionData` implementa a interface `SqlConnectionExtended` para
fornecer funcionalidades de conexão com o banco de dados.
## Funcionalidades
- Gera conexões com o banco de dados com base em diferentes configurações.
- Implementa métodos para gerar conexões individuais e em pool.
- Utiliza métodos para criar e gerenciar conexões com o banco de dados.
# SqlConnectionDataSingle
## Descrição
A classe `SqlConnectionDataSingle` implementa a interface `SqlConnection` para fornecer
funcionalidades de geração de conexão com o banco de dados.
## Funcionalidades
- Gera uma única conexão com o banco de dados com base em diferentes configurações.
- Implementa métodos para gerar conexões individuais usando configurações específicas.
- Utiliza métodos auxiliares para criar e configurar corretamente a conexão com o banco de
dados.
---
# Makers
# DbContext
## Descrição
A classe `DbContext` estende a classe abstrata `ContextHolder`, fornecendo
funcionalidades para gerenciar o contexto das classes no banco de dados.
## Funcionalidades
- Implementa métodos para adicionar classes ao contexto e obter o contexto atual.
- Usa uma lista para armazenar as classes no contexto do banco de dados.
# IdType
## Descrição
O enum `IdType` define os tipos de identificação para chaves primárias em entidades no
banco de dados.
## Funcionalidades
- Define dois tipos de identificação: `AUTO_INCREMENT` e `NONE`.
- `AUTO_INCREMENT`: Indica que a chave primária é gerada automaticamente pelo banco
de dados.
- `NONE`: Indica que não há identificação automática para a chave primária.
# SqlGenerator
## Descrição
A classe `SqlGenerator` implementa a interface `SqlMakerExtended`, fornecendo
funcionalidades para gerar consultas SQL dinamicamente com base nas classes de
entidades do banco de dados.
## Funcionalidades
- Gera consultas SQL para SELECT, INSERT, UPDATE e DELETE.
- Suporta operações de agregação como SUM e AVG.
- Permite a geração de consultas SQL com cláusulas WHERE e ORDER BY.
- Gerencia o cache de consultas SQL para reutilização.
- Lida com anotações como @Entity, @Table, @Id, @Column e @OrderBy para
mapeamento objeto-relacional.
# SqlGenFactory
## Descrição
A classe `SqlGenFactory` implementa a interface `SqlmakerFactory`, fornecendo métodos
para criar instâncias de `SqlMaker` e `SqlMakerExtended` com base no tipo de banco de
dados.
## Funcionalidades
- Cria instâncias de `SqlGenerator` para diferentes tipos de bancos de dados.
# SqlQueryData
## Descrição
A classe `SqlQueryData` estende a classe `SqlResponse` e representa uma resposta que
contém uma consulta SQL e informações relacionadas.
## Funcionalidades
- Armazena uma consulta SQL e informações adicionais.
- Fornece métodos para acessar e manipular a consulta SQL e as informações internas.
---
# PA
# ProxyControll
## Descrição
A classe `ProxyControll` é responsável por controlar operações em um repositório, como
salvar, excluir, e encontrar entidades. Ela recebe informações essenciais, como a interface
do repositório, a classe de ID, a classe do repositório, uma conexão SQL, um gerador de
consultas SQL estendido e um executor SQL.
## Funcionalidades
- Implementa métodos para executar operações no repositório, como salvar, excluir,
encontrar todas as entidades e encontrar por ID.
- Utiliza a classe `SqlMakerExtended` para gerar consultas SQL, como a consulta para
encontrar todas as entidades.
- As operações executadas são exibidas no console para fins de depuração.
# RepositoryData
## Descrição
A classe `RepositoryData` é responsável por inicializar e criar proxies para os repositórios.
Ela implementa a interface `RepositoryInitializer` e utiliza reflexão para determinar o tipo de
repositório com base nos argumentos genéricos. Além disso, cria instâncias do controle do
repositório (`ProxyControll`) e retorna proxies dinâmicos para os repositórios.
## Funcionalidades
- Inicializa e cria proxies para os repositórios baseados em interfaces.
- Utiliza reflexão para determinar o tipo de repositório e a classe de ID com base nos
argumentos genéricos.
- Cria instâncias do controle do repositório e as associa com os proxies gerados.
- Fornece um método estático para criar proxies de forma simplificada.
---
## Como Usar
### Anotações
- `@Column`
- `@Entity`
- `@Id`
- `@OrderBy`
- `@Table`
### Classes Principais
- **`SqlGenerator`**
- **`SqlGenFactory`**
- **`SqlQueryData`**
## Exemplo de Criação de Entidade
```java
package com.example.model;
import com.sql.anotations.Column;
import com.sql.anotations.Entity;
import com.sql.anotations.Id;
import com.sql.anotations.Table;
import com.sql.data.maker.IdType;
@Entity
@Table(name = "users")
public class MyEntity {
@Id
@Column(CollumName = "user_id")
private Long userId;
@OrderBy
@Column(CollumName = "username")
private String username;
@Column(CollumName = "email")
private String email;
// Construtores, getters e setters aqui...
}
```
## Exemplo de Uso
```java
// Criar uma fábrica de SqlGenerator
SqlGenFactory factory = new SqlGenFactory();
// Criar uma instância de SqlGenerator
SqlMaker sqlMaker = factory.createSqlMakerExtended(null);
// Usar métodos do SqlGenerator para gerar consultas SQL dinâmicas
SqlResponse selectAllQuery = sqlMaker.generateSelectAllSql(MyEntity.class);
SqlResponse selectEmailOrUserQuery = sqlMaker.generateSelectSql(MyEntity.class, "OR",
"username", "email");
SqlResponse selectEmailAndUserQuery = sqlMaker.generateSelectSql(MyEntity.class,
"AND", "username", "email");
SqlResponse insertQuery = sqlMaker.generateInsertSql(MyEntity.class);
SqlResponse deleteQuery = sqlMaker.generateDeleteSql(MyEntity.class);
SqlResponse updateQuery = sqlMaker.generateUpdateSql(MyEntity.class);
```
lGenerator
SqlGenFactory factory = new SqlGenFactory();
// Criar uma instância de SqlGenerator
SqlMaker sqlMaker = factory.createSqlMakerExtended(null);
// Usar métodos do SqlGenerator para gerar consultas SQL dinâmicas
SqlResponse selectAllQuery = sqlMaker.generateSelectAllSql(MyEntity.class);
SqlResponse selectEmailOrUserQuery = sqlMaker.generateSelectSql(MyEntity.class, "OR",
"username", "email");
SqlResponse selectEmailAndUserQuery = sqlMaker.generateSelectSql(MyEntity.class,
"AND", "username", "email");
SqlResponse insertQuery = sqlMaker.generateInsertSql(MyEntity.class);
SqlResponse deleteQuery = sqlMaker.generateDeleteSql(MyEntity.class);
SqlResponse updateQuery = sqlMaker.generateUpdateSql(MyEntity.class);
