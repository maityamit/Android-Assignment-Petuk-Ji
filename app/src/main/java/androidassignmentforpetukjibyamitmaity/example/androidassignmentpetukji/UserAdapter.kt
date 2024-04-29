package androidassignmentforpetukjibyamitmaity.example.androidassignmentpetukji

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val fragment:MainActivity, private var goalList: ArrayList<Pair<String, String>>) :
    RecyclerView.Adapter<UserAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.userview_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.userName.text = goalList[position].second

        holder.itemView.setOnClickListener {
            val intent:Intent = Intent(fragment,ChatActivity::class.java)
            intent.putExtra("userID",goalList[position].first)
            fragment.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return goalList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.mainActUserName)

    }
}