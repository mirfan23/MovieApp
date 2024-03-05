package com.movieappfinal.viewmodel

import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    lateinit var useCase: AppUseCase

    @Mock
    lateinit var fireRepo: FirebaseRepository
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authViewModel = AuthViewModel(useCase, fireRepo)
    }

    @Test
    fun testSignUpWithFirebase_Success() = coroutineRule.runBlockingTest{
        val email = "alex@gmail"
        val password = "12345678"

        `when`(useCase.signUpFirebase(email, password)).thenReturn(flowOf(true))

        val resultFlow = authViewModel.signUpWithFirebase(email, password)
        val result = mutableListOf<Boolean>()
        resultFlow.collect {
            result.add(it)
        }

        verify(useCase).signUpFirebase(email, password)
        assert(result.last())
    }

    @Test
    fun testSignUpWithFirebase_Failure() = coroutineRule.runBlockingTest{
        val email = "satu@gmail.com"
        val password = "12345678"

        `when`(useCase.signUpFirebase(email, password)).thenReturn(flowOf(false))

        val resultFlow = authViewModel.signUpWithFirebase(email, password)
        val result = mutableListOf<Boolean>()
        resultFlow.collect {
            result.add(it)
        }

        verify(useCase).signUpFirebase(email, password)
        assert(!result.last())
    }
}
