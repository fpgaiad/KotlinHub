package br.com.kotlinhub.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.kotlinhub.data.model.KotlinReposResponse
import br.com.kotlinhub.repository.GitRepository
import br.com.kotlinhub.util.EspressoIdlingResource
import br.com.kotlinhub.util.NetworkConstants.GENERIC_ERROR
import br.com.kotlinhub.util.NetworkConstants.NETWORK_IO_EXCEPTION_ERROR
import br.com.kotlinhub.util.StatusResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class GitViewModel @ViewModelInject constructor(
    private val gitRepository: GitRepository
) : ViewModel() {

    private val _kotlinRepos: MutableLiveData<StatusResponse<KotlinReposResponse>> =
        MutableLiveData()
    val kotlinRepos: LiveData<StatusResponse<KotlinReposResponse>>
        get() = _kotlinRepos

    private var kotlinReposPage = 1
    private var kotlinReposResponse: KotlinReposResponse? = null

    init {
        getKotlinRepos()
    }

    fun getKotlinRepos() {
        viewModelScope.launch {
            // Uncomment line below to run UI tests
//            EspressoIdlingResource.increment()
            _kotlinRepos.postValue(StatusResponse.Loading())
            try {
                val response = gitRepository.getKotlinRepos(kotlinReposPage)
                _kotlinRepos.postValue(handleResponse(response))
            } catch (throwable: Throwable) {
                Timber.e(throwable)
                _kotlinRepos.postValue(handleFailure(throwable))
            }
            // Uncomment line below to run UI tests
//            EspressoIdlingResource.decrement()
        }
    }

    private fun handleResponse(response: Response<KotlinReposResponse>): StatusResponse<KotlinReposResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                kotlinReposPage++
                if (kotlinReposResponse == null) {
                    kotlinReposResponse = resultResponse
                } else {
                    val oldKotlinRepos = kotlinReposResponse?.items
                    val newKotlinRepos = resultResponse.items
                    oldKotlinRepos?.addAll(newKotlinRepos)
                }
                return StatusResponse.Success(kotlinReposResponse ?: resultResponse)
            }
        }
        return StatusResponse.Error(response.code())
    }

    private fun handleFailure(throwable: Throwable): StatusResponse<KotlinReposResponse> {
        return when (throwable) {
            is IOException -> StatusResponse.Error(NETWORK_IO_EXCEPTION_ERROR)
            is HttpException -> StatusResponse.Error(throwable.code())
            else -> StatusResponse.Error(GENERIC_ERROR)
        }
    }
}