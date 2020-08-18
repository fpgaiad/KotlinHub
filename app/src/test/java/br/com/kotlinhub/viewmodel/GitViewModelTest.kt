package br.com.kotlinhub.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.kotlinhub.FakeData
import br.com.kotlinhub.repository.GitRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GitViewModelTest {

    @get:Rule
    val coroutineRule: TestRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var gitViewModel: GitViewModel

    @Mock
    private lateinit var mockGitRepository: GitRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        gitViewModel = GitViewModel(mockGitRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when call getKotlinRepos and response isSuccessful should update kotlinRepos accordingly`() {
        val fakeSuccessResponse = FakeData.getFakeKotlinReposSuccessResponse()
        runBlocking {
            `when`(mockGitRepository.getKotlinRepos(anyInt()))
                .thenReturn(fakeSuccessResponse)
        }

        gitViewModel.getKotlinRepos()

        assertEquals(
            FakeData.getFakeKotlinReposSuccessResponse().body(),
            gitViewModel.kotlinRepos.value?.data
        )
    }

    @Test
    fun `when call getKotlinRepos and response isNotSuccessful should update kotlinRepos accordingly`() {
        val fakeErrorResponse = FakeData.getFake404ErrorResponse()
        runBlocking {
            `when`(mockGitRepository.getKotlinRepos(anyInt()))
                .thenReturn(fakeErrorResponse)
        }

        gitViewModel.getKotlinRepos()

        assertEquals(
            FakeData.getFake404ErrorResponse().code(),
            gitViewModel.kotlinRepos.value?.code
        )
    }
}