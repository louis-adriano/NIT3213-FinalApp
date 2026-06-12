package com.louis.nit3213finalapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.louis.nit3213finalapp.data.model.LoginResponse
import com.louis.nit3213finalapp.data.repository.BookRepository
import com.louis.nit3213finalapp.ui.login.LoginState
import com.louis.nit3213finalapp.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: BookRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid credentials emits Success state`() = runTest {
        whenever(repository.login("s8112376", "Louis"))
            .thenReturn(Response.success(LoginResponse("books")))

        viewModel.login("s8112376", "Louis")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Success)
        assertEquals("books", (state as LoginState.Success).keypass)
    }

    @Test
    fun `login with empty username emits Error state`() = runTest {
        viewModel.login("", "Louis")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
    }

    @Test
    fun `login with empty password emits Error state`() = runTest {
        viewModel.login("s8112376", "")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
    }

    @Test
    fun `login with failed response emits Error state`() = runTest {
        whenever(repository.login("wrong", "wrong"))
            .thenReturn(Response.error(401, okhttp3.ResponseBody.create(null, "")))

        viewModel.login("wrong", "wrong")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
    }

    @Test
    fun `login emits Loading state first`() = runTest {
        whenever(repository.login("s8112376", "Louis"))
            .thenReturn(Response.success(LoginResponse("books")))

        viewModel.login("s8112376", "Louis")

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Loading)
    }
}
