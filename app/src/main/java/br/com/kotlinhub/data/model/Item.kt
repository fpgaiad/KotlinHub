package br.com.kotlinhub.data.model

data class Item(
    val description: String,
    val forks_count: Int,
    val html_url: String,
    val id: Int,
    val name: String,
    val owner: Owner,
    val stargazers_count: Int
)