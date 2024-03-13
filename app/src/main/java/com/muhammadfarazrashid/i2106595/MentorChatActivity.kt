package com.muhammadfarazrashid.i2106595

import UserManager
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.muhammadfarazrashid.i2106595.dataclasses.FirebaseManager
import com.squareup.picasso.Picasso

class MentorChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var mentorName: TextView
    private lateinit var currentMentor: Mentor
    private lateinit var sendButton: Button
    private lateinit var micButton: Button
    private lateinit var messageField: EditText
    private lateinit var takePhoto: Button
    private lateinit var sendImage: Button
    private lateinit var attachImage: Button
    private var chatId: String =""
    private var mentorImageUrl: String = ""
    private var selectedMessageId: String? = null
    private lateinit var selectedImageUri: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentorchat)

        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!
        setMentorDetails(currentMentor)
        initViews()

        getChatId(object : ChatIdCallback {
            override fun onChatIdReceived(chatId1: String) {
                Log.d("MentorChatActivity", "Chat ID: $chatId")
                chatId = chatId1

            }

            override fun onChatIdError(errorMessage: String) {
                Log.e("MentorChatActivity", errorMessage)
                // Handle the error here
            }
        })


        setButtonClickListeners()
        setBottomNavigationListener()
        setAddMentorClickListener()

    }

    private fun setMentorDetails(mentor: Mentor) {
        mentorName = findViewById(R.id.mentorName)
        mentorName.text = currentMentor.name

        // Call getImageUrl function to get the mentor's image URL
        Mentor.getImageUrl(mentor.id, object : Mentor.OnImageUrlListener {
            override fun onSuccess(imageUrl: String) {
                // Load image using Picasso
                Picasso.get().load(imageUrl).fetch(object: com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        mentorImageUrl = imageUrl
                        fetchUserMessages(mentorImageUrl)

                    }

                    override fun onError(e: Exception?) {
                        // Handle failure to load image
                        Log.e("MentorChatActivity", "Failed to load mentor image: ${e?.message}")
                    }
                })
            }

            override fun onFailure(errorMessage: String) {
                // Handle failure to retrieve image URL
                Log.e("MentorChatActivity", "Failed to retrieve image URL: $errorMessage")
            }
        })
    }

    private fun initViews() {
        micButton = findViewById(R.id.micButton)
        messageField = findViewById(R.id.reviewText)
        takePhoto = findViewById(R.id.takePhoto)
        sendImage = findViewById(R.id.sendImage)
        attachImage = findViewById(R.id.sendFile)
        sendButton = findViewById(R.id.sendButton)

        recyclerView = findViewById(R.id.communityChatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        val chatMessages = ArrayList<ChatMessage>()
        chatAdapter = ChatAdapter(chatMessages, this, object : ChatAdapter.onMessageClickListener {
            override fun onMessageClick(position: Int) {
                val view = recyclerView.layoutManager?.findViewByPosition(position)
                showPopupMenu(chatMessages[position], view)
            }
        })
        recyclerView.adapter = chatAdapter
    }

    private fun showPopupMenu(chatMessage: ChatMessage, view: View?)
    {
        view?.let {
            val contextWrapper = ContextThemeWrapper(this, R.style.MyMenuItemStyle)
            val popupMenu = PopupMenu(contextWrapper, it)
            popupMenu.menuInflater.inflate(R.menu.edit_delete_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.editItem -> {
                        // Handle edit message
                        // val editMessageDialog = EditMessageDialog(chatMessage.message, chatMessage.id, this)
                        // editMessageDialog.show(supportFragmentManager, "EditMessageDialog")
                        messageField.setText(chatMessage.message)
                        selectedMessageId = chatMessage.id

                        true
                    }
                    R.id.deleteItem -> {
                        FirebaseManager.deleteMessageInDatabase(chatMessage.id, "mentor_chats", "chat_videos", chatId, chatAdapter)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }




    private fun sendMessage() {
        val message = messageField.text.toString()
        val currentTime = java.text.SimpleDateFormat("HH:mm a").format(java.util.Date())
        if (message.isNotEmpty()) {
            if (selectedMessageId != null) {
                // Update the message in the database
                FirebaseManager.editMessageInDatabase(message, selectedMessageId!!, "mentor_chats", chatId, chatAdapter)
                // Clear the selected message ID
                selectedMessageId = null
            } else {
                // Add a new message to the database
                FirebaseManager.saveMessageToDatabase(message, currentTime, "mentor_chats",chatId , chatAdapter)
            }
            // Clear the message field
            messageField.text.clear()
        }
    }



    private val pickImageGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data ?: return@registerForActivityResult
            // Send the image to the chat
            FirebaseManager.sendImageToStorage(selectedImageUri, chatId, "mentor_chats",chatAdapter,"chat_images")
        }
    }



    private fun sendImage(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageGalleryLauncher.launch(galleryIntent)
    }

    private val pickFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedFileUri = result.data?.data ?: return@registerForActivityResult
            val fileType = getFileType(selectedFileUri)
            if (fileType != null) {

                when (fileType) {
                    FileType.IMAGE -> FirebaseManager.sendImageToStorage(selectedFileUri, chatId, "mentor_chats", chatAdapter, "chat_images")
                    FileType.VIDEO -> FirebaseManager.sendImageToStorage(selectedFileUri, chatId, "mentor_chats", chatAdapter, "chat_videos")
                    FileType.PDF -> FirebaseManager.sendImageToStorage(selectedFileUri, chatId, "mentor_chats", chatAdapter, "chat_documents")
                    FileType.AUDIO -> FirebaseManager.sendImageToStorage(selectedFileUri, chatId, "mentor_chats", chatAdapter, "chat_audio")
                }
            } else {

                Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun attachFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        pickFile.launch(intent)
    }

    private fun getFileType(uri: Uri): FileType? {
        val contentResolver = applicationContext.contentResolver
        val mimeType = contentResolver.getType(uri)
        return when {
            mimeType?.startsWith("image") == true -> FileType.IMAGE
            mimeType?.startsWith("video") == true -> FileType.VIDEO
            mimeType?.endsWith("pdf") == true -> FileType.PDF
            mimeType?.startsWith("audio") == true -> FileType.AUDIO
            else -> null
        }
    }

    enum class FileType {
        IMAGE,
        VIDEO,
        PDF,
        AUDIO
    }

    private fun setButtonClickListeners() {

        sendButton.setOnClickListener {
            val message = messageField.text.toString()
            if (message.isNotEmpty()) {
                sendMessage()
                messageField.text.clear()
            }
        }

        micButton.setOnClickListener {
           // recordVoiceMessage()
        }

        takePhoto.setOnClickListener {
            //takePhoto()
        }

        sendImage.setOnClickListener {
           sendImage()
        }

        attachImage.setOnClickListener {
            attachFile()
        }

        findViewById<Button>(R.id.callButton).setOnClickListener {
            startActivity(Intent(this, PhoneCallActivity::class.java))
        }

        findViewById<Button>(R.id.videoButton).setOnClickListener {
            startActivity(Intent(this, VideoCallActivity::class.java))
        }

        findViewById<Button>(R.id.backbutton).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.takePhoto).setOnClickListener {
            startActivity(Intent(this, PhotoActivity::class.java))
        }
    }

    interface ChatIdCallback {
        fun onChatIdReceived(chatId: String)
        fun onChatIdError(errorMessage: String)
    }


    private fun getChatId(callback: ChatIdCallback) {
        val database = FirebaseDatabase.getInstance()
        val currentUser = UserManager.getCurrentUser()?.id
        val chatRef = currentUser?.let { database.getReference("users").child(it).child("chats").child("mentor_chats") }

        chatRef?.get()?.addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { chatSnapshot ->
                val chatId = chatSnapshot.key
                val mentorId = chatSnapshot.value as String
                if (mentorId == currentMentor.id) {
                    if (chatId != null) {
                        callback.onChatIdReceived(chatId)
                    }
                    return@addOnSuccessListener  // Exit the loop if chatId is found
                }
            }
            // If no matching chatId is found
            callback.onChatIdError("Chat ID not found")
        }?.addOnFailureListener { exception ->
            callback.onChatIdError("Failed to retrieve chat ID: ${exception.message}")
        }
    }


    private fun setBottomNavigationListener() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            val intent = when (item.itemId) {
                R.id.menu_search -> Intent(this, searchPageActivity::class.java)
                R.id.menu_home -> Intent(this, homePageActivity::class.java)
                R.id.menu_chat -> Intent(this, mainChatActivity::class.java)
                R.id.menu_profile -> Intent(this, MyProfileActivity::class.java)
                else -> null
            }
            intent?.let {
                startActivity(it)
            }
        }
    }

    private fun setAddMentorClickListener() {
        findViewById<ImageView>(R.id.addMentorButton).setOnClickListener {
            startActivity(Intent(this, AddAMentor::class.java))
        }
    }


    private fun fetchUserMessages(mentorImageUrl: String) {
        val database = FirebaseDatabase.getInstance()
        val currentUser = UserManager.getCurrentUser()?.id
        val chatRef = currentUser?.let { database.getReference("chat").child("mentor_chats").child(chatId).child("messages") }

        chatRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (messageSnapshot in dataSnapshot.children) {
                    val message = messageSnapshot.child("message").value as String
                    val time = messageSnapshot.child("time").value as String
                    val date = messageSnapshot.child("date").value as String
                    val userId = messageSnapshot.child("userId").value as String
                    val messageId= messageSnapshot.key.toString()
                    val isCurrentUser = userId == currentUser
                    var messageImageUrl=""
                    var messageVideoUrl=""
                    var messageAudioUrl=""
                    var messageDocumentUrl=""

                    if(messageSnapshot.child("messageImageUrl").exists())
                        messageImageUrl= messageSnapshot.child("messageImageUrl").value as String
                    if(messageSnapshot.child("messageVideoUrl").exists())
                        messageVideoUrl= messageSnapshot.child("messageVideoUrl").value as String
                    if(messageSnapshot.child("messageAudioUrl").exists())
                        messageAudioUrl= messageSnapshot.child("messageAudioUrl").value as String
                    if(messageDocumentUrl.isNotEmpty())
                        messageDocumentUrl= messageSnapshot.child("messageDocumentUrl").value as String
                    chatAdapter.addMessage(ChatMessage(messageId,message, time, isCurrentUser, mentorImageUrl,messageImageUrl,messageVideoUrl,messageAudioUrl,messageDocumentUrl))

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("MentorChatActivity", "Failed to retrieve chat messages: ${databaseError.message}")
            }
        })
    }




}
