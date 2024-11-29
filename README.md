# CatPaws - Adote um Gato! 🐾

Bem-vindo ao **CatPaws**, um aplicativo desenvolvido com **Jetpack Compose** que permite visualizar imagens de gatos consumindo a [The Cat API](https://thecatapi.com) e gerenciar mensagens relacionadas a eles. Com o CatPaws, você pode buscar imagens aleatórias de gatos, salvar ou compartilhar as fotos e criar mensagens personalizadas para cada imagem. Um CRUD completo foi implementado para gerenciar as mensagens associadas.

---

## Funcionalidades Principais

- **Buscar imagens de gatos:** Consome a The Cat API para exibir fotos adoráveis de gatos.
- **Gerenciar mensagens:** Adicione, edite ou remova mensagens personalizadas associadas às imagens dos gatos.
- **Compartilhar imagens:** Compartilhe as fotos diretamente com seus amigos.
- **Salvar imagens na galeria:** Faça o download das imagens exibidas no aplicativo.
- **Design moderno e responsivo:** Desenvolvido com **Jetpack Compose**, com um layout elegante e intuitivo.

---

## Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **Framework:** Jetpack Compose
- **API:** The Cat API
- **Bibliotecas:** 
  - **Coil:** Para carregamento eficiente de imagens.
  - **Retrofit:** Para consumir a API de gatos.
  - **Material3:** Para design e componentes modernos.

---

## Configuração do Projeto

### Pré-requisitos

- Android Studio instalado.
- Chave de API da The Cat API (obtenha [aqui](https://thecatapi.com/)).

### Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/malobr/CatPaws.git
   cd catpaws
   ```

2. Abra o projeto no Android Studio.

3. Adicione sua chave de API:
   No arquivo onde o `apiKey` é usado, substitua pelo valor da sua chave obtida no site da The Cat API.

4. Compile e execute o aplicativo no emulador ou dispositivo físico.

---

## Estrutura do Projeto

### Pacotes

- **`model`:** Contém as classes de modelo, como `Cat`, e a configuração do Retrofit.
- **`view`:** Inclui os componentes visuais e lógicos do Jetpack Compose, como o arquivo principal `CatApp`.

### Componentes Importantes

#### `CatApp`

- **Busca de gatos:** 
  - O botão "Buscar Gato" faz uma chamada à API para obter uma imagem aleatória.
  - Utiliza `Retrofit` para fazer a requisição.
  
- **Mensagens:** 
  - Gerenciamento de mensagens em uma lista dinâmica.
  - Possibilidade de adicionar, editar ou excluir mensagens.

- **Compartilhar e salvar imagens:** 
  - Usa funcionalidades nativas do Android para compartilhamento.
  - Implementa `saveImageToGallery` para salvar imagens localmente.

---

## Funcionalidades Detalhadas

### 1. **Buscar Gato**
- Botão que consome a API e exibe uma imagem aleatória.
- Mensagens aleatórias são exibidas para cada nova imagem.

### 2. **Gerenciar Mensagens**
- Adicione novas mensagens através de um campo de texto.
- Edite mensagens existentes com facilidade.
- Exclua mensagens que não deseja mais usar.

### 3. **Compartilhar Imagem**
- Um botão que utiliza um `Intent` para compartilhar a imagem carregada via aplicativos disponíveis no dispositivo.

### 4. **Salvar Imagem**
- Salve imagens na galeria do dispositivo para acessá-las futuramente.

---

## Exemplo de Uso

1. Abra o aplicativo.
2. Clique no botão "Buscar Gato" para carregar uma nova imagem.
3. Veja a imagem com uma mensagem aleatória ou crie sua própria mensagem.
4. Compartilhe ou salve a imagem usando os botões dedicados.
5. Adicione, edite ou exclua mensagens na seção de gerenciamento.

---

## Melhorias Futuras

- Integração com banco de dados local para salvar mensagens e imagens.
- Sistema de autenticação para gerenciar gatos e mensagens por usuário.
- Modo escuro para uma experiência mais confortável.

---

