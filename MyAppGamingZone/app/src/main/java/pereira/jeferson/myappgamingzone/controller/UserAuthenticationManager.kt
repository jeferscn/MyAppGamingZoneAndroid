package pereira.jeferson.myappgamingzone.controller

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object UserAuthenticationManager {

    private var auth: FirebaseAuth? = null

    /**
     * Call this function in method onCreate()
     */
    fun init() {
        auth = Firebase.auth
    }

    /**
     * Call this function in method onStart()
     */
    fun verifyLoggedUser(callbackSuccess: () -> Unit) {
        val currentUser = auth?.currentUser
        if (currentUser != null) {
            callbackSuccess.invoke()
        }
    }

    /**
     *  Call this method to create a new user
     */
    fun createNewUser(email: String, password: String, activity: Activity, callbackSuccess: () -> Unit, callbackFailure: (String) -> Unit) {
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "createUserWithEmail:success")
                    callbackSuccess.invoke()
                } else {

                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                    callbackFailure.invoke(task.exception?.message ?: "")
                }
            }
    }

    fun signInUser(
        email: String,
        password: String,
        activity: Activity,
        callbackSuccess: () -> Unit,
        callbackFailure: (String) -> Unit
    ) {

        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "signInWithEmail:success")
                    callbackSuccess.invoke()
                } else {

                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    callbackFailure.invoke(task.exception?.message ?: "")
                }
            }
    }

    fun signOutUser() {
        auth?.signOut()
    }

    private fun getCurrentUser() : FirebaseUser? {
        return auth?.currentUser
    }

    fun getUserTokenId(callbackSuccess: (String) -> Unit) {
        val currentUser = getCurrentUser()
        val userId = currentUser?.uid ?: ""
        callbackSuccess.invoke(userId)
    }

}