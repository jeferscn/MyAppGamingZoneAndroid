package pereira.jeferson.myappgamingzone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pereira.jeferson.myappgamingzone.controller.Database
import pereira.jeferson.myappgamingzone.controller.UserAuthenticationManager
import pereira.jeferson.myappgamingzone.databinding.ActivityGamingRoomBinding

class GamingRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamingRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamingRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Database.init()

        configure()
    }

    private fun configure() {
        Database.getDatabaseData(
            Database.DATABASE_TABLE_USERS,
            Database.TABLE_FIELD_ID,
            callbackSuccess = { user ->

                binding.textWelcomePlayerName.text =
                    WELCOME_MESSAGE.format("${user?.firstName} ${user?.lastName}")
            }
        )

        binding.cardJokenpoGame.setOnClickListener {
            callJokenpoActivity()
        }

        binding.buttonUserLogout.setOnClickListener {
            UserAuthenticationManager.signOutUser()
            finish()
        }
    }

    private fun callJokenpoActivity() {
        val intent = Intent(this, JokepoGameActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val WELCOME_MESSAGE = "Seja bem-vindo\n %s"
    }
}