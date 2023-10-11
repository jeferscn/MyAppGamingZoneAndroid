package pereira.jeferson.myappgamingzone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pereira.jeferson.myappgamingzone.controller.Database
import pereira.jeferson.myappgamingzone.controller.UserAuthenticationManager
import pereira.jeferson.myappgamingzone.databinding.ActivitySignUpScreenBinding
import pereira.jeferson.myappgamingzone.extensions.showSnackBarMessage

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UserAuthenticationManager.init()
        Database.init()

        userSignUp()
    }

    private fun userSignUp() {
        binding.buttonSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val firstName = binding.editTextFirstName.text.toString().trim()
            val lastName = binding.editTextLastName.text.toString().trim()
            val areValidFields =
                email.isNotBlank() && password.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank()

            if (areValidFields) {
                saveUserCredentials(email, password, firstName, lastName)
            } else {
                showErrorFieldsMessage()
            }
        }
    }

    private fun saveUserCredentials(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        UserAuthenticationManager.createNewUser(
            email = email,
            password = password,
            activity = this,
            callbackSuccess = {
                saveUserData(firstName, lastName)
            },
            callbackFailure = { message ->
                showSnackBarMessage(binding.root, ERROR_MESSAGE_SIGN_UP_USER.format(message))
            }
        )
    }

    private fun saveUserData(first: String, last: String) {

        UserAuthenticationManager.getUserTokenId { userId ->
            val user = hashMapOf(
                Database.TABLE_FIELD_ID to userId,
                Database.TABLE_FIELD_FIRST_NAME to first,
                Database.TABLE_FIELD_LAST_NAME to last
            )

            Database.setDatabaseData(
                Database.DATABASE_TABLE_USERS,
                user,
                callbackSuccess = {
                    finishActivity()
                },
                callbackFailure = {
                    print("")
                }
            )
        }
    }

    private fun finishActivity() {
        finish()
    }

    private fun showErrorFieldsMessage() {
        val message = ERROR_MESSAGE_FIELDS

        showSnackBarMessage(binding.root, message)
    }

    companion object {
        const val ERROR_MESSAGE_FIELDS = "Por favor preencha todos os campos!"
        const val ERROR_MESSAGE_SIGN_UP_USER = "Houve um erro ao criar o usuário: %s"
    }
}