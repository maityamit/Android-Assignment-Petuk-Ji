package androidassignmentforpetukjibyamitmaity.example.androidassignmentpetukji

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User

class MainActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    lateinit var rootRef: DatabaseReference
    lateinit var progressDialog: ProgressDialog
    var goalList = ArrayList<Pair<String,String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setTitle("Loading....")
        recyclerView = findViewById(R.id.userListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rootRef = FirebaseDatabase.getInstance().reference.child("Users")


    }

    override fun onStart() {
        super.onStart()
        progressDialog.show()

        val currentUserID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        rootRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currList = ArrayList<Pair<String,String>>()
                for(dataSnapshot in snapshot.children){
                    val goal = dataSnapshot.child("userName").getValue().toString()
                    currList.add(Pair(dataSnapshot.key!!, goal))
                }

                goalList = currList
                progressDialog.dismiss()

                recyclerView.adapter = UserAdapter(this@MainActivity,goalList)

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        )
    }
}