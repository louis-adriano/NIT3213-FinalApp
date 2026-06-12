package com.louis.nit3213finalapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.louis.nit3213finalapp.data.model.Book
import com.louis.nit3213finalapp.data.model.DashboardResponse
import com.louis.nit3213finalapp.data.repository.BookRepository
import com.louis.nit3213finalapp.ui.dashboard.DashboardState
import com.louis.nit3213finalapp.ui.dashboard.DashboardViewModel
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
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: BookRepository
    private lateinit var viewModel: DashboardViewModel

    private val fakeBooks = listOf(
        Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 1960, "A novel about racial injustice"),
        Book("1984", "George Orwell", "Dystopian", 1949, "A story about totalitarianism")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadBooks emits Success state with books`() = runTest {
        whenever(repository.getDashboard())
            .thenReturn(Response.success(DashboardResponse(fakeBooks, 2)))

        viewModel.loadBooks()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Success)
        assertEquals(2, (state as DashboardState.Success).books.size)
    }

    @Test
    fun `loadBooks emits correct book data`() = runTest {
        whenever(repository.getDashboard())
            .thenReturn(Response.success(DashboardResponse(fakeBooks, 2)))

        viewModel.loadBooks()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value as DashboardState.Success
        assertEquals("To Kill a Mockingbird", state.books[0].title)
        assertEquals("Harper Lee", state.books[0].author)
    }

    @Test
    fun `loadBooks emits Error state on failed response`() = runTest {
        whenever(repository.getDashboard())
            .thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "")))

        viewModel.loadBooks()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Error)
    }

    @Test
    fun `loadBooks emits Loading state first`() = runTest {
        whenever(repository.getDashboard())
            .thenReturn(Response.success(DashboardResponse(fakeBooks, 2)))

        viewModel.loadBooks()

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Loading)
    }

    @Test
    fun `loadBooks emits Error state on network exception`() = runTest {
        whenever(repository.getDashboard())
            .thenThrow(RuntimeException("Network error"))

        viewModel.loadBooks()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Error)
    }
}
