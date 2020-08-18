# KotlinHub

App para consulta no GitHub de repositórios com linguagem Kotlin, ordenados por número de estrelas.

## Funcionalidades

Desenvolvido em Kotlin, contempla as seguintes funcionalidades:

* RecyclerView com resultado da requisição feita para a Api pública do GitHub;
* Cada célula da lista contém: Foto do autor; Nome do autor; Nome do repositório; Breve descrição do repositório; Quantidade de estrelas; Quantidade de forks.
* Scroll Infinito com paginação.
* Mudança de orientação de tela sem perda de estado.
* Ao clicar em um elemento da lista usuário é direcionado para site do repositório via browser, para obter mais detalhes.
* Tratamento de erros.
* Testes Unitários e de UI.

## Estrutura de Pastas

![](/assets/package_structure.png?raw=true "Estrutura de Pastas")

## Arquitetura e Libs Utilizadas

O código é estruturado com arquitetura MVVM/Clean Architecture utilizando:
* **ViewModel:** Junto com LiveData o ViewModel facilita o tratamento de rotações de tela, pois as variáveis utilizadas nas views permanecem ligadas ao lifecycle do ViewModel (que não é destruído nas rotações das views);
* **LiveData:** Utilza do padrão observable para atualizar as variáveis nas views, deixando as views atualizadas com as últimas alterações nas propriedades observadas no ViewModel;
* **Material Components:** Facilitou o uso de boas práticas de design, seguindo o padrão Material Design no desenvolvimento do app;
* **Dagger Hilt:** Injeção de dependência com geração de código em tempo de compilação com anotações específicas para o framework Android, facilitando sua aplicação e gerando menos linhas de código que o Dagger;
* **Coroutines:** Utilzado para tarefas assíncronas, tem fácil implementação e melhora a leitura do código;
* **Retrofit:** Uma das bibliotecas mais utilizadas para requisições de dados, e com suporte para coroutines;
* **Glide:** Biblioteca para exibição e cache de imagens com o menor tempo de download e exibição das imagens em cache, [segundo estudo](https://proandroiddev.com/coil-vs-picasso-vs-glide-get-ready-go-774add8cfd40);
* **Timber:** Utilizada para facilitar utilização de Logs durante o projeto;
* **Stetho:** Utilizada para facilitar debug das requisições para a Api.

## ScreenShot

**Lista com os repositórios**

![](/assets/repositories_list.png?raw=true "RecyclerView")

## Instruções para rodar o projeto

Clonar o projeto em seu computador e instalar no emulador ou device.

Para rodar os testes de UI é necessário descomentar as linhas indicadas no _GitViewModel.kt_(linhas 39 e 49) devido a necessidade de adicionar o IdlingResource do Espresso em testes que lidam com tarefas assíncronas.

## API Utilizada

* [GitHub Api](https://docs.github.com/en/rest).
