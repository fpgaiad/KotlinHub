package br.com.kotlinhub.data.network

import br.com.kotlinhub.data.model.KotlinReposResponse
import br.com.kotlinhub.util.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitGitApi : GitApi {

    @GET(NetworkConstants.URL_PATH_SEARCH_REPOS)
    override suspend fun getKotlinRepos(
        @Query("q") language: String,
        @Query("sort") sortBy: String,
        @Query("page") page: Int
    ): Response<KotlinReposResponse>
}