# HealthCheck API
## Visão Geral
HealthCheck API é um sistema backend para um aplicativo de vigilância epidemiológica, desenvolvido para que agentes de saúde possam cadastrar e gerenciar focos de doenças em diferentes localidades. Esta API permite o registro de agentes, autenticação, cadastro de casos e visualização georreferenciada dos focos de doenças.

## Principais Funcionalidades
- Gerenciamento de Agentes: Cadastro e autenticação de agentes de saúde
- Registro de Casos: Cadastro de ocorrências de doenças com dados geográficos
- Busca Avançada: Filtragem de casos por cidade, bairro e tipo de doença
- Visualização em Mapa: Exibição georreferenciada de casos utilizando coordenadas geográficas
- Segurança: Validação para garantir que agentes só registrem casos em suas respectivas cidades
- Geocodificação: Conversão automática de endereços em coordenadas geográficas

## Tecnologias Utilizadas
- Java 17: Linguagem de programação utilizada no backend
- Spring Boot 3.2.5: Framework para desenvolvimento de aplicações Java
- Spring Data JPA: Persistência de dados
- Spring Security: Implementação de BCrypt para criptografia de senhas
- Swagger/OpenAPI: Documentação interativa da API
- Spring Actuator: Monitoramento de saúde e métricas da aplicação
- Lombok: Redução de código boilerplate
- ModelMapper: Conversão entre DTOs e entidades
- API Geoapify: Serviço de geocodificação para obtenção de coordenadas geográficas

## Estrutura da API
### Endpoints Principais
#### Agentes
- POST /api/agents/register: Cadastro de novos agentes de saúde
- POST /api/agents/login: Autenticação de agentes

#### Casos
- POST /api/cases: Registro de novos casos de doenças
- GET /api/cases/search: Busca de casos com filtros específicos

#### Mapa
- GET /api/map/cases: Obtenção de todos os casos com coordenadas para exibição em mapa

## Modelos de Dados
- Agent: Representa um agente de saúde com credenciais e localização
- Case: Representa um caso/foco de doença com dados de localização e georreferenciamento

## Geocodificação Integrada
A API integra-se com o serviço Geoapify para converter automaticamente endereços em coordenadas geográficas (latitude e longitude), permitindo a visualização precisa dos focos de doenças em mapas interativos. Esta funcionalidade está implementada no CaseServiceImpl e utiliza uma chave de API configurada no arquivo de propriedades da aplicação.

## Explicação usando como base alguns conceitos do modelo C4

### Diagrama de Contexto
![Diagrama de Contexto](https://github.com/LuigiBelanda/HealthCheck/tree/master/Outros/structurizr-SystemContext.png)

### Diagrama de Contêineres
![Diagrama de Contêineres](docs/diagrams/structurizr-Containers.png)

### Diagrama de Componentes
![Diagrama de Componentes](docs/diagrams/structurizr-Components.png)

O modelo C4 foi utilizado para descrever os níveis de abstração da aplicação:

- **Contexto**: Visão geral do sistema e os atores que interagem com ele.
- **Contêineres**: Estrutura lógica da aplicação, incluindo API, banco de dados e serviços externos.
- **Componentes**: Divisão interna da API, destacando serviços, controladores e repositórios.

