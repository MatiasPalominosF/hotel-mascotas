package com.example.hotelmascotas.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${FirebaseAuth.getInstance().uid ?: throw NullPointerException(
                "UID is null."
            )}"
        )

    /*fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(
                    FirebaseAuth.getInstance()?.uid ?: "",
                    FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                    FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                    FirebaseAuth.getInstance().currentUser?.email ?: ""

                    )
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else
                onComplete()
        }

    }

    fun updateCurrentUser(
        name: String = "",
        lastName: String = "",
        email: String = "",
        profilePicturePath: String? = null
    ) {
        val userFieldMap = mutableMapOf<String, Any>()

        if (name.isNotBlank()) userFieldMap["name"] = name
        if (lastName.isNotBlank()) userFieldMap["lastName"] = lastName
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (profilePicturePath != null) userFieldMap["profilePicturePath"] = profilePicturePath

        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User::class.java)!!)
            }

    }*/

}