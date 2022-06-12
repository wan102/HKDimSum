package com.example.hkdimsum

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

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
        val email = editTextEmailAddress.toString()
        val password = editTextPassword.toString()
        val result = email + password
        assertEquals(result, editTextEmailAddress + editTextPassword)
    }

    @Test
    fun performLogin() {

        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor::class.java)
        val context = Mockito.mock(Context::class.java)

        //simulate the behaviour of the objects
        Mockito.`when`(context.getSharedPreferences(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(sharedPrefs)
        Mockito.`when`(sharedPrefs.edit()).thenReturn(sharedPrefsEditor)
        Mockito.`when`(sharedPrefsEditor.putString(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(sharedPrefsEditor)

        val email = "user1@gmail.com"
        val preKey = "LOGINEMAIL_KEY"
        val preKey2 = "PASSWORD_KEY"

        val login = LoginActivity(email)
        login.performLogin(email)
        //verify is the method call
        Mockito.verify(sharedPrefsEditor).putString(Mockito.argThat { key -> key == preKey },
            Mockito.argThat { value -> value == email })
        Mockito.verify(sharedPrefsEditor).commit()
    }

    @Test
    fun goToRegister() {
    }
}