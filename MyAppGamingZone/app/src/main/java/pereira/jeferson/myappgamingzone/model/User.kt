package pereira.jeferson.myappgamingzone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("first")
    val firstName: String,
    @SerializedName("last")
    val lastName: String
): Serializable
