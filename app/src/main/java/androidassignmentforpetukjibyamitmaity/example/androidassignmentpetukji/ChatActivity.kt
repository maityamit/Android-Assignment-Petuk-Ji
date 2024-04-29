package androidassignmentforpetukjibyamitmaity.example.androidassignmentpetukji

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    lateinit var editText:EditText
    private lateinit var progressDialog: ProgressDialog
    lateinit var imageView:ImageView

    lateinit var recyclerView: RecyclerView
    lateinit var rootRef: DatabaseReference
    var chatList = ArrayList<Pair<Pair<String,String>,String>>()
    var selfUserID:String = ""
    var TargettEduserID:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setTitle("Loading....")

        val extras = intent.extras
        TargettEduserID = extras?.getString("userID")
        selfUserID = FirebaseAuth.getInstance().currentUser?.uid.toString()


        recyclerView = findViewById(R.id.chatViewRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(TargettEduserID!=null){
            rootRef = FirebaseDatabase.getInstance().reference.child("Chats").child(selfUserID).child(TargettEduserID!!)
        }


        editText = findViewById(R.id.user_chat_edittext)
        imageView = findViewById(R.id.user_chat_sendButton)

        imageView.setOnClickListener{
            val str:String = editText.text.toString()
            if(str.equals("")){
                Toast.makeText(applicationContext,"Enter Chat",Toast.LENGTH_SHORT).show()
            }else{
                progressDialog.show()
                TargettEduserID?.let { it1 -> saveToFireBase(it1,selfUserID,str) }
            }
        }


    }
    private fun saveToFireBase(targettEduserID: String, selfUserID: String, str: String) {


        val chatData = hashMapOf(
            "senderID" to selfUserID,
            "chat" to str
        )

        val newRefKey = Firebase.database.reference.child("Chats").child(selfUserID).child(targettEduserID).push().key.toString()

        Firebase.database.reference.child("Chats").child(selfUserID).child(targettEduserID)
            .child(newRefKey).setValue(chatData).addOnSuccessListener {

                Firebase.database.reference.child("Chats").child(targettEduserID).child(selfUserID)
                    .child(newRefKey).setValue(chatData).addOnSuccessListener {
                        progressDialog.dismiss()
                        editText.text = null

                    }
                    .addOnFailureListener{
                        Toast.makeText(applicationContext,"Error!!",Toast.LENGTH_SHORT).show()
                    }

            }
            .addOnFailureListener{
                Toast.makeText(applicationContext,"Error!!",Toast.LENGTH_SHORT).show()
            }


    }


    override fun onStart() {
        super.onStart()

        progressDialog.show()

        rootRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currList = ArrayList<Pair<Pair<String,String>,String>>()
                for(dataSnapshot in snapshot.children){
                    val chat = dataSnapshot.child("chat").getValue().toString()
                    val senderID = dataSnapshot.child("senderID").getValue().toString()
                    currList.add(Pair(Pair(selfUserID,senderID),chat))
                }

                chatList = currList
                progressDialog.dismiss()

                val chatAdapter:ChatAdapter = ChatAdapter(this@ChatActivity,chatList)

                recyclerView.adapter = chatAdapter
                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1)

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        )


    }

}