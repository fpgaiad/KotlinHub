package br.com.kotlinhub.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.kotlinhub.R
import br.com.kotlinhub.adapter.RepoListAdapter
import br.com.kotlinhub.util.InfiniteScrollListener
import br.com.kotlinhub.util.NetworkConstants.CLIENT_ERROR
import br.com.kotlinhub.util.NetworkConstants.NETWORK_IO_EXCEPTION_ERROR
import br.com.kotlinhub.util.NetworkConstants.SERVER_ERROR
import br.com.kotlinhub.util.NetworkConstants.getErrorCode
import br.com.kotlinhub.util.StatusResponse
import br.com.kotlinhub.viewmodel.GitViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: GitViewModel by viewModels()

    private lateinit var repoListAdapter: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel.kotlinRepos.observe(this, Observer { response ->
            when (response) {
                is StatusResponse.Success -> {
                    hideProgressBar(getCurrentProgressBar())
                    response.data?.items?.let {
                        repoListAdapter.differ.submitList(it.toList())
                    }
                }
                is StatusResponse.Loading -> {
                    showProgressBar(getCurrentProgressBar())
                }
                is StatusResponse.Error -> {
                    hideProgressBar(getCurrentProgressBar())

                    val errorMessage: String
                    var messageLength = Snackbar.LENGTH_LONG
                    var isNetworkError = false
                    val errorCode = getErrorCode(response.code)

                    errorMessage = when (errorCode) {
                        NETWORK_IO_EXCEPTION_ERROR -> {
                            getString(R.string.no_internet_error).also {
                                isNetworkError = true
                                messageLength = Snackbar.LENGTH_INDEFINITE
                            }
                        }
                        CLIENT_ERROR -> getString(R.string.client_error)
                        SERVER_ERROR -> getString(R.string.server_error)
                        else -> getString(R.string.generic_error)
                    }
                    showSnackbarError(errorMessage, messageLength, isNetworkError)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        repoListAdapter = RepoListAdapter(::openBrowserAt)
        rvRepos.apply {
            adapter = repoListAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object : InfiniteScrollListener(linearLayoutManager) {
                override fun onLoadMore() {
                    fetchKotlinRepos()
                }
            })
        }
    }

    private fun showSnackbarError(
        errorMessage: String,
        messageLength: Int,
        isNetworkError: Boolean
    ) {
        val textColor = resources.getColor(R.color.secondaryTextColor)
        Snackbar.make(mainContainer, errorMessage, messageLength).apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(textColor)
            setActionTextColor(textColor)
            if (isNetworkError) {
                setAction("RETRY") { fetchKotlinRepos() }
            }
            show()
        }
    }

    private fun fetchKotlinRepos() {
        viewModel.getKotlinRepos()
    }

    private fun getCurrentProgressBar(): ProgressBar {
        return if (repoListAdapter.differ.currentList.isEmpty()) pbEmptyList
        else pbReposList
    }

    private fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

    private fun openBrowserAt(htmlUrl: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(htmlUrl)
            this.resolveActivity(packageManager)?.let {
                startActivity(this@apply)
            }
        }
    }
}