package br.com.kotlinhub.repository

import br.com.kotlinhub.data.model.KotlinReposResponse
import retrofit2.Response

interface GitRepository {

    suspend fun getKotlinRepos(page: Int): Response<KotlinReposResponse>
}