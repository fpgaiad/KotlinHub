package br.com.kotlinhub.repository

import br.com.kotlinhub.data.model.KotlinReposResponse
import br.com.kotlinhub.data.network.GitApi
import br.com.kotlinhub.util.NetworkConstants.KOTLIN_LANGUAGE
import br.com.kotlinhub.util.NetworkConstants.STARS
import retrofit2.Response
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(
    private val gitApi: GitApi
) : GitRepository {

    override suspend fun getKotlinRepos(page: Int): Response<KotlinReposResponse> {
        return gitApi.getKotlinRepos(
            KOTLIN_LANGUAGE,
            STARS,
            page
        )
    }
}