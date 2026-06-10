# Safe Plant — Sistema de Monitoramento de Safras

Aplicação em console desenvolvida em **Java** como parte da **Global Solution (FIAP)**, servindo de back-end para o aplicativo **Safe Plant**. O sistema permite que agricultores gerenciem suas safras, produtos cultivados e perfil pessoal diretamente pelo terminal.

ODS relacionada ao projeto: ODS 2

**Acesse o vídeo de apresentação** 👉 [Clicando aqui](https://youtu.be/XsQDMARpclQ)

---

## 👥 Autores
| NOME COMPLETO                   | RM         |
|---------------------------------|------------|
| Luís Felipe Scacchetti Mariano  | **562241** |
| Pedro Lucas Almeida Cunha       | **566256** |
| Gabriel Amara                   | **561403** |
| Camila Martins Lopes dos Santos | **561492** |
| Guilherme Godoy dos Santos      | **564417** |

_Projeto desenvolvido pelo grupo **GAPC** para a **Global Solution — FIAP**._

## Funcionalidades

### 🔐 Autenticação
- Login com nome de usuário e senha (senha mascarada via `Console`)
- Cadastro de novo usuário em 3 etapas:
    1. **Dados do usuário** — nome completo, nome de usuário, e-mail e senha
    2. **Dados do agricultor** — CPF, data de nascimento e celular
    3. **Endereço** — busca automática por CEP via API **ViaCEP** ou preenchimento manual

### 🌾 Gerenciamento de Safras
- Listar todas as safras do agricultor autenticado
- Registrar nova safra em 2 etapas:
    1. **Informações principais** — nome, data de início, data de encerramento e cultivos
    2. **Terreno** — latitude, longitude e área (m²)
- Visualizar detalhes de uma safra específica
- Editar safras:
    - Atualizar cultivos (adicionar ou remover produtos)
    - Atualizar status da safra
    - Atualizar data de encerramento
    - Excluir safra (com confirmação)

#### Status de Safra disponíveis
| Status | Descrição |
|---|---|
| `PLANEJADA` | Safra ainda não iniciada |
| `EM_ANDAMENTO` | Safra em execução |
| `COLHEITA` | Safra em fase de colheita |
| `FINALIZADA` | Safra concluída |
| `EM_ALERTA` | Safra com algum problema |
| `CANCELADA` | Safra cancelada |

### 🧺 Gerenciamento de Produtos
- Listar produtos cultivados por safra
- Visualizar estoque completo por tipo de produto
- Registrar novo produto — nome, nome científico, tipo e tempo médio de colheita (dias)
- Editar produto:
    - Atualizar nome comum
    - Atualizar nome científico (binomial obrigatório)
    - Atualizar tempo médio de colheita
    - Atualizar tipo do produto
    - Excluir produto (com confirmação)

#### Tipos de Produto disponíveis
| Tipo | Cor no Terminal |
|---|---|
| `LEGUME` | Roxo |
| `VEGETAL` | Verde claro |
| `FRUTA` | Laranja |
| `CEREAL` | Amarelo |
| `LACTINIO` | Azul |

### 👤 Perfil do Agricultor
- Exibir dados pessoais do agricultor autenticado (nome, CPF, celular, endereço, etc.)

---

## Arquitetura

```
src/br/com/safeplant/monitoramentosafras/
├── Main.java                          # Ponto de entrada
├── enums/
│   ├── StatusSafra.java               # Status possíveis de uma safra
│   └── TipoProduto.java               # Categorias de produto agrícola
├── helper/
│   ├── Interacao.java                 # Utilitários de entrada/saída no console
│   └── Verificador.java               # Validações de dados (CPF, data, inteiro etc.)
├── interfaces/
│   ├── IAgricultor.java
│   ├── IDatabase.java
│   ├── ILocalizacao.java
│   ├── IOperacoesPadrao.java
│   ├── IProduto.java
│   ├── ISafra.java
│   └── IUsuario.java
├── models/
│   ├── Agricultor.java                # Agricultor (estende Usuario)
│   ├── Database.java                  # Persistência em arquivos JSON via Gson
│   ├── Endereco.java                  # Endereço com integração ViaCEP
│   ├── Localizacao.java               # Coordenadas geográficas (lat/long)
│   ├── Produto.java                   # Produto agrícola
│   ├── Safra.java                     # Safra (estende Terreno)
│   ├── Terreno.java                   # Terreno (estende Localizacao)
│   └── Usuario.java                   # Usuário base do sistema
└── view/
    ├── MenuAutenticacao.java          # Telas de login e cadastro
    ├── MenuPrincipal.java             # Menu principal pós-login
    ├── MenuProduto.java               # Gerenciamento de produtos
    └── MenuSafra.java                 # Gerenciamento de safras
```

### 🏗️ Hierarquia de modelos

```
Localizacao
    └── Terreno
            └── Safra

Usuario
    └── Agricultor
```

### 🛡️ Persistência

Os dados são salvos e lidos em **arquivos JSON** locais via biblioteca **Gson**, sem uso de banco de dados relacional. Cada entidade (`Usuario`, `Agricultor`, `Produto`, `Safra`, `Endereco`) possui seu próprio arquivo JSON.

---

## Como Executar

**🚀 Pré-requisitos:**
- Java JDK 21 ou superior
- IntelliJ IDEA (recomendado)
- Dependência: [Gson](https://github.com/google/gson)

**🛠️ Passos:**
```bash
git clone https://github.com/ESPV1/gs_safeplant.git
```
1. Abra o projeto na IDE desejada
2. Certifique-se de que o Gson está no classpath
3. Execute a classe `Main.java`

---

## Tecnologias

- **Java** — linguagem principal
- **Gson** — serialização/deserialização JSON
- **API ViaCEP** — consulta de endereços por CEP (`java.net.http.HttpClient`)
- **Java Console API** — leitura de senha mascarada no terminal

---

## Integrações Externas

| Serviço | Uso |
|---|---|
| [ViaCEP](https://viacep.com.br) | Busca automática de endereço a partir do CEP informado no cadastro |

---

## Licença
- **Projeto Open-source sob licença do [MIT](https://en.wikipedia.org/wiki/MIT_License)**
