package com.example.hkdimsum

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginActivityTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun login() {
        val editTextEmailAddress =
            "user1@gmail.com"
        val editTextPassword =
            "000000"
        val result = editTextEmailAddress + editTextPassword
        assertEquals(result, editTextEmailAddress + editTextPassword)
    }

    @Test
    fun goToRegister() {
    }
}