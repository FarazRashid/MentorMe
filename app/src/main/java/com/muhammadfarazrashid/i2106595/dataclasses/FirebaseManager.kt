package com.muhammadfarazrashid.i2106595.dataclasses

import android.app.Activity
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.muhammadfarazrashid.i2106595.ChatAdapter
import com.muhammadfarazrashid.i2106595.ChatMessage

class FirebaseManager {

    private lateinit var selectedImageUri: Uri
    private fun sendImageToStorage(
        selectedImageUri: Uri,
        chatId: String,
        chat_type: String,
        chatAdapter: ChatAdapter
    ) {
        val storage = FirebaseStorage.getInstance()
        val currentUser = UserManager.getCurrentUser()?.id
        val database = FirebaseDatabase.getInstance()
        val chatRef = currentUser?.let {
            database.getReference("chat").child(chat_type).child(chatId).child("messages").push()
        }
        val storageRef = currentUser?.let {
            storage.reference.child("chat_images").child(it).child(
                chatRef?.key.toString()
            )
        }
        val uploadTask = storageRef?.putFile(selectedImageUri)

        uploadTask?.addOnSuccessListener {
            Log.d(ContentValues.TAG, "Image uploaded successfully")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                sendImageToChat(uri, chatRef?.key.toString(), chatId, chat_type, "", chatAdapter)
            }
        }?.addOnFailureListener { e ->
            Log.e(ContentValues.TAG, "Failed to upload image: ${e.message}")
        }
    }

    private fun sendImageToChat(
        selectedImageUri: Uri,
        chatRef: String = "",
        chatId: String,
        chat_type: String,
        mentorImageUrl: String,
        chatAdapter: ChatAdapter
    ) {
        val database = FirebaseDatabase.getInstance()
        val currentUser = UserManager.getCurrentUser()?.id
        val chatRef = currentUser?.let {
            database.getReference("chat").child(chat_type).child(chatId).child("messages")
                .child(chatRef)
        }

        if (chatRef != null) {
            val date = java.text.SimpleDateFormat("dd MMMM").format(java.util.Date())
            chatRef.setValue(
                mapOf(
                    "message" to "",
                    "time" to "",
                    "date" to date,
                    "userId" to currentUser,
                    "messageImageUrl" to selectedImageUri.toString()
                )
            )
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Image saved successfully")
                    Log.d(ContentValues.TAG, "Image: ${chatRef.key}, Time: ")
                    chatAdapter.addMessage(
                        ChatMessage(
                            chatRef.key.toString(),
                            "",
                            "",
                            true,
                            mentorImageUrl,
                            selectedImageUri.toString()
                        )
                    )

                }
                .addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Failed to save image: ${e.message}")
                }
        } else {
            Log.e(ContentValues.TAG, "Failed to get chat reference")
        }
    }

    companion object {
        public fun sendImageToStorage(
            selectedImageUri: Uri,
            chatId: String,
            chat_type: String,
            chatAdapter: ChatAdapter
        ) {
            val storage = FirebaseStorage.getInstance()
            val currentUser = UserManager.getCurrentUser()?.id
            val database = FirebaseDatabase.getInstance()
            val chatRef = currentUser?.let {
                database.getReference("chat").child(chat_type).child(chatId).child("messages")
                    .push()
            }
            val storageRef = currentUser?.let {
                storage.reference.child("chat_images").child(it).child(
                    chatRef?.key.toString()
                )
            }
            val uploadTask = storageRef?.putFile(selectedImageUri)

            uploadTask?.addOnSuccessListener {
                Log.d(ContentValues.TAG, "Image uploaded successfully")
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    sendImageToChat(
                        uri,
                        chatRef?.key.toString(),
                        chatId,
                        chat_type,
                        "",
                        chatAdapter
                    )
                }
            }?.addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Failed to upload image: ${e.message}")
            }
        }

        public fun sendImageToChat(
            selectedImageUri: Uri,
            chatRef: String = "",
            chatId: String,
            chat_type: String,
            mentorImageUrl: String,
            chatAdapter: ChatAdapter
        ) {
            val database = FirebaseDatabase.getInstance()
            val currentUser = UserManager.getCurrentUser()?.id
            val chatRef = currentUser?.let {
                database.getReference("chat").child(chat_type).child(chatId).child("messages")
                    .child(chatRef)
            }

            if (chatRef != null) {
                val date = java.text.SimpleDateFormat("dd MMMM").format(java.util.Date())
                chatRef.setValue(
                    mapOf(
                        "message" to "",
                        "time" to "",
                        "date" to date,
                        "userId" to currentUser,
                        "messageImageUrl" to selectedImageUri.toString()
                    )
                )
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "Image saved successfully")
                        Log.d(ContentValues.TAG, "Image: ${chatRef.key}, Time: ")
                        chatAdapter.addMessage(
                            ChatMessage(
                                chatRef.key.toString(),
                                "",
                                "",
                                true,
                                mentorImageUrl,
                                selectedImageUri.toString()
                            )
                        )

                    }
                    .addOnFailureListener { e ->
                        Log.e(ContentValues.TAG, "Failed to save image: ${e.message}")
                    }
            } else {
                Log.e(ContentValues.TAG, "Failed to get chat reference")
            }

        }
    }
}


