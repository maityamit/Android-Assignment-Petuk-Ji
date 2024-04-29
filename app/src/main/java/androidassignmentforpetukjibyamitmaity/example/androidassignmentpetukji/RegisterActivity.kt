package androidassignmentforpetukjibyamitmaity.example.androidassignmentpetukji

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        progressDialog= ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setTitle("Loading....")
        database = Firebase.database.reference

        findViewById<Button>(R.id.register_user_button).setOnClickListener{

            val user_name = findViewById<EditText>(R.id.register_user_name).text.toString()
            val user_email = findViewById<EditText>(R.id.register_user_email).text.toString()
            val user_password = findViewById<EditText>(R.id.register_user_password).text.toString()


            if(user_name.isBlank()){
                Toast.makeText(applicationContext,"Enter Your Name", Toast.LENGTH_SHORT).show()
            }else if(user_email.isBlank()){
                Toast.makeText(applicationContext,"Enter Your Email", Toast.LENGTH_SHORT).show()
            }else if(user_password.isBlank()){
                Toast.makeText(applicationContext,"Enter Your Password", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog.show()
                createANewUserOnFirebase(user_name,user_password,user_email)
            }
        }

    }

    private fun createANewUserOnFirebase(userName: String, userPassword: String, userEmail: String) {

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = FirebaseAuth.getInstance().currentUser
                    if(user!=null){
                        val userId = user.uid.toString()

                        val userData = hashMapOf(
                            "userName" to userName,
                            "userEmail" to userEmail,
                            "userID" to userId
                        )


                        database.child("Users").child(userId).setValue(userData)
                            .addOnSuccessListener {
                               Toast.makeText(applicationContext,"User Created Sucessful", Toast.LENGTH_SHORT).show()
                                val intent:Intent = Intent(applicationContext,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(applicationContext,"User Not Created Sucessful", Toast.LENGTH_SHORT).show()
                            }




                    }else {
                        Toast.makeText(
                            baseContext,
                            "Creating User failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Creating User failed.", Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }
    }
}