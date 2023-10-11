package pereira.jeferson.myappgamingzone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pereira.jeferson.myappgamingzone.controller.UserAuthenticationManager
import pereira.jeferson.myappgamingzone.databinding.ActivityMainBinding
import pereira.jeferson.myappgamingzone.extensions.showSnackBarMessage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserAuthenticationManager.init()

        configure()
    }

    override fun onDestroy() {
        super.onDestroy()

        UserAuthenticationManager.signOutUser()
    }

    private fun configure() {
        binding.buttonSignIn.setOnClickListener {
            runCatching {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                if (email.isNotBlank() && password.isNotBlank()) {
                    userSignIn(email, password)
                } else {
                    showErrorFieldsMessage(email, password)
                }
            }
        }

        verifyUserLogged()
        configureCallSignUpActivity()
    }

    private fun verifyUserLogged() {
        UserAuthenticationManager.verifyLoggedUser(
            callbackSuccess = {
                callGamingRoomActivity()
            }
        )
    }

    private fun userSignIn(email: String, password: String) {
        UserAuthenticationManager.signInUser(
            email,
            password,
            this,
            callbackSuccess = {
                callGamingRoomActivity()
            },
            callbackFailure = { message ->
                showSnackBarMessage(binding.root, ERROR_MESSAGE_SIGN_IN_TRY.format(message))
            }
        )
    }

    private fun callGamingRoomActivity() {
        val intent = Intent(this, GamingRoomActivity::class.java)
        startActivity(intent)
    }

    private fun configureCallSignUpActivity() {
        binding.buttonStartSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showErrorFieldsMessage(email: String, password: String) {
        val message = if (email.isEmpty() && password.isEmpty()) {
            ERROR_MESSAGE_EMAIL_PASSWORD
        } else if (email.isEmpty() && password.isNotEmpty()) {
            ERROR_MESSAGE_EMAIL
        } else {
            ERROR_MESSAGE_PASSWORD
        }

        showSnackBarMessage(binding.root, message)
    }

    companion object {
        const val ERROR_MESSAGE_EMAIL_PASSWORD = "Por favor preencha email e senha, estes campos não podem ser vazios!"
        const val ERROR_MESSAGE_EMAIL = "Por favor preencha o email, este campo não pode ser vazio!"
        const val ERROR_MESSAGE_PASSWORD = "Por favor preencha a senha, este campo não pode ser vazio!"
        const val ERROR_MESSAGE_SIGN_IN_TRY = "Houve um erro ao autenticar: %s"
    }
}