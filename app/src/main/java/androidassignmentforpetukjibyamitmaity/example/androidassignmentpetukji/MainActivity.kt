package androidassignmentforpetukjibyamitmaity.example.androidassignmentpetukji

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    lateinit var rootRef: DatabaseReference
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}