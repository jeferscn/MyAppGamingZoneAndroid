package pereira.jeferson.myappgamingzone.view

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import pereira.jeferson.myappgamingzone.R
import pereira.jeferson.myappgamingzone.databinding.ActivityJokepoGameBinding

class JokepoGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokepoGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokepoGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGame()
    }

    private fun setupGame(playerChoice: Choice = Choice.STONE, computerChoice: Choice = Choice.values().random()) {
        binding.buttonStone.setOnClickListener { setupGame(Choice.STONE) }
        binding.buttonPaper.setOnClickListener { setupGame(Choice.PAPER) }
        binding.buttonScissor.setOnClickListener { setupGame(Choice.SCISSOR) }

        val gameResult = determineWinner(playerChoice, computerChoice)

        setGameResultFeedback(gameResult)
        setPlayersImageChoice(playerChoice, computerChoice)
    }

    private fun setGameResultFeedback(gameResult: GameResult) {
        val textGameResult: String
        val colorGameResult: Int

        when (gameResult) {
            GameResult.PLAYER_WON -> {
                textGameResult = TEXT_GAME_RESULT_PLAYER_WON
                colorGameResult = getColor(R.color.greenLight)
            }

            GameResult.COMPUTER_WON -> {
                textGameResult = TEXT_GAME_RESULT_COMPUTER_WON
                colorGameResult = getColor(R.color.redLight)
            }

            GameResult.DRAW -> {
                textGameResult = TEXT_GAME_RESULT_DRAW
                colorGameResult = getColor(R.color.yellowLight)
            }
        }

        binding.textGameResult.text = textGameResult
        binding.root.setBackgroundColor(colorGameResult)
    }

    private fun setPlayersImageChoice(playerChoice: Choice, computerChoice: Choice) {
        binding.imagePlayerChoice.setImageDrawable(getChoiceImage(playerChoice))
        binding.imageComputerChoice.setImageDrawable(getChoiceImage(computerChoice))
    }

    private fun getChoiceImage(choice: Choice): Drawable? {
        return when (choice) {
            Choice.STONE -> AppCompatResources.getDrawable(this, R.drawable.pedra)
            Choice.PAPER -> AppCompatResources.getDrawable(this, R.drawable.papel)
            else -> AppCompatResources.getDrawable(this, R.drawable.tesoura)
        }
    }

    private fun determineWinner(playerChoice: Choice, computerChoice: Choice): GameResult {
        return when {
            playerChoice == Choice.STONE && computerChoice == Choice.SCISSOR -> GameResult.PLAYER_WON
            playerChoice == Choice.STONE && computerChoice == Choice.PAPER -> GameResult.COMPUTER_WON
            playerChoice == Choice.PAPER && computerChoice == Choice.STONE -> GameResult.PLAYER_WON
            playerChoice == Choice.PAPER && computerChoice == Choice.SCISSOR -> GameResult.COMPUTER_WON
            playerChoice == Choice.SCISSOR && computerChoice == Choice.PAPER -> GameResult.PLAYER_WON
            playerChoice == Choice.SCISSOR && computerChoice == Choice.STONE -> GameResult.COMPUTER_WON
            else -> {
                GameResult.DRAW
            }
        }
    }

    enum class GameResult {
        DRAW, PLAYER_WON, COMPUTER_WON
    }

    enum class Choice {
        STONE, PAPER, SCISSOR
    }

    companion object {

        private const val TEXT_GAME_RESULT_PLAYER_WON = "Você venceu!"
        private const val TEXT_GAME_RESULT_COMPUTER_WON = "Computador venceu!"
        private const val TEXT_GAME_RESULT_DRAW = "Empate!"
    }
}