package br.com.kotlinhub.data.network

import br.com.kotlinhub.data.model.KotlinReposResponse
import retrofit2.Response

interface GitApi {

    suspend fun getKotlinRepos(
        language: String,
        sortBy: String,
        page: Int
    ): Response<KotlinReposResponse>
}