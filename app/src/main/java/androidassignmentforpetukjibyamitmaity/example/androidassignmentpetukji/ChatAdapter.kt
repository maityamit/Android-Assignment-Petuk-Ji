package androidassignmentforpetukjibyamitmaity.example.androidassignmentpetukji

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ChatAdapter(private val fragment:ChatActivity, private var chatList: ArrayList<Pair<Pair<String,String>,String>>) :
    RecyclerView.Adapter<ChatAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(chatList[position].first.first == chatList[position].first.second){
            holder.senderSelf_Test.visibility = View.VISIBLE
            holder.senderSelf_Test.text = chatList[position].second
        }else{
            holder.receiverSelf_Test.visibility = View.VISIBLE
            holder.receiverSelf_Test.text = chatList[position].second
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderSelf_Test: TextView = itemView.findViewById(R.id.senderSelf_Test)
        val receiverSelf_Test:TextView = itemView.findViewById(R.id.receiverSelf_Test)


    }
}