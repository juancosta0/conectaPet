# ConectaPet Backend

Backend da aplicação ConectaPet - Sistema de adoção de pets desenvolvido com Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **H2 Database** (desenvolvimento)
- **PostgreSQL** (produção)
- **Maven**

## 📋 Funcionalidades

### Autenticação e Autorização
- ✅ Registro de usuários (Adotantes e ONGs)
- ✅ Login com JWT
- ✅ Proteção de rotas com Spring Security
- ✅ Validação de dados

### Gestão de Pets
- ✅ Cadastro de pets para adoção
- ✅ Listagem de pets disponíveis
- ✅ Busca e filtros (espécie, porte, idade)
- ✅ Upload de múltiplas imagens
- ✅ Atualização de status (Disponível, Em Processo, Adotado)

### Gestão de Usuários
- ✅ Perfis diferenciados (Adotante/ONG)
- ✅ Atualização de perfil
- ✅ Sistema de favoritos
- ✅ Listagem de ONGs

### Solicitações de Adoção
- ✅ Criação de solicitações
- ✅ Aprovação/Rejeição por ONGs
- ✅ Histórico de solicitações

## 🛠️ Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Passos para execução

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd conectapet-backend
```

2. **Execute a aplicação**
```bash
mvn spring-boot:run
```

3. **Acesse a aplicação**
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:conectapet`
  - Username: `sa`
  - Password: `password`

## 📚 Documentação da API

### Autenticação

#### POST /auth/login
```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

#### POST /auth/register
```json
{
  "name": "Nome do Usuário",
  "email": "usuario@email.com",
  "password": "123456",
  "userType": "ADOTANTE", // ou "ONG"
  "cnpj": "12.345.678/0001-90", // apenas para ONGs
  "description": "Descrição da ONG" // apenas para ONGs
}
```

### Pets

#### GET /api/pets
Lista todos os pets disponíveis para adoção

#### GET /api/pets/{id}
Busca um pet específico por ID

#### POST /api/pets (Autenticado)
```json
{
  "nome": "Nome do Pet",
  "tipoAnimal": "Cachorro",
  "raca": "SRD",
  "idade": 2,
  "porte": "MEDIO",
  "cor": "Caramelo",
  "descricao": "Descrição do pet",
  "imagens": ["url1", "url2"]
}
```

#### GET /api/pets/search
Busca pets com filtros:
- `species`: Espécie do animal
- `size`: Porte (PEQUENO, MEDIO, GRANDE)
- `minAge`: Idade mínima
- `maxAge`: Idade máxima
- `name`: Nome do pet

### Usuários

#### GET /api/users/profile (Autenticado)
Retorna o perfil do usuário logado

#### PUT /api/users/profile (Autenticado)
Atualiza o perfil do usuário

#### GET /api/users/ongs
Lista todas as ONGs ativas

## 🗄️ Estrutura do Banco de Dados

### Tabelas Principais
- `users` - Usuários (Adotantes e ONGs)
- `pets` - Pets para adoção
- `pet_images` - Imagens dos pets
- `adoption_requests` - Solicitações de adoção
- `user_favorite_pets` - Relacionamento de favoritos

## 🔧 Configuração

### Profiles
- **dev** (padrão): Usa H2 em memória
- **prod**: Usa PostgreSQL

### Variáveis de Ambiente (Produção)
```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/conectapet
DATABASE_USERNAME=conectapet
DATABASE_PASSWORD=password
```

## 🧪 Dados de Teste

A aplicação vem com dados de exemplo:

### Usuários
- **Adotante**: joao@email.com / 123456
- **ONG**: ong@amigos.com / 123456

### Pets
- Caramelo (Cachorro)
- Mimi (Gato Siamês)
- Rex (Pastor Alemão)
- Luna (Gato SRD)
- Thor (Golden Retriever)
- Princesa (Gato Persa)

## 🔒 Segurança

- Senhas criptografadas com BCrypt
- Autenticação JWT
- CORS configurado para frontend
- Validação de dados com Bean Validation
- Proteção contra SQL Injection com JPA

## 📝 Logs

Os logs estão configurados para mostrar:
- Queries SQL (desenvolvimento)
- Erros de autenticação
- Operações importantes

## 🚀 Deploy

Para deploy em produção:

1. Configure as variáveis de ambiente
2. Altere o profile para `prod`
3. Execute: `mvn clean package`
4. Execute: `java -jar target/conectapet-backend-0.0.1-SNAPSHOT.jar`

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.