package pereira.jeferson.myappgamingzone.controller

import android.annotation.SuppressLint
import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import pereira.jeferson.myappgamingzone.model.User

object Database {

    @SuppressLint("StaticFieldLeak")
    private lateinit var database: FirebaseFirestore

    fun init() {
        database = Firebase.firestore
    }

    fun getDatabaseData(table: String, getByField: String, callbackSuccess: (User?) -> Unit) {
        UserAuthenticationManager.getUserTokenId { userId ->
            database.collection(table)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.data[getByField] == userId) {
                            callbackSuccess.invoke(mapToDataClass(document.data))
                            break
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("DATABASE_TRY_GET", "Error getting documents.", exception)
                }
        }
    }

    fun setDatabaseData(
        table: String,
        data: Any,
        callbackSuccess: (CollectionReference) -> Unit,
        callbackFailure: (String) -> Unit
    ) {
        database.collection(table)
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("DATABASE_TRY_SET", "DocumentSnapshot added with ID: ${documentReference.id}")
                callbackSuccess.invoke(documentReference.firestore.collection(table))
            }
            .addOnFailureListener { e ->
                Log.w("DATABASE_TRY_SET", "Error adding document", e)
                callbackFailure.invoke(e.message ?: "")
            }
    }

    private fun mapToDataClass(dataMap: Map<String, Any>): User? {
        val gson = Gson()
        val json = gson.toJson(dataMap)
        return gson.fromJson(json, object : TypeToken<User>() {}.type)
    }

    const val DATABASE_TABLE_USERS = "users"
    const val TABLE_FIELD_ID = "id"
    const val TABLE_FIELD_FIRST_NAME = "first"
    const val TABLE_FIELD_LAST_NAME = "last"
}