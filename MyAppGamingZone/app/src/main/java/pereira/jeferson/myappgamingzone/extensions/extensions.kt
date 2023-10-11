package pereira.jeferson.myappgamingzone.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBarMessage(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}