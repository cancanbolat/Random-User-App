package com.example.kotlinretrofit.UI.Adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinretrofit.Model.Results
import com.example.kotlinretrofit.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(var userList: ArrayList<Results>, var onUserClickListener: OnUserClickListener) :
    RecyclerView.Adapter<UserAdapter.viewHolder>(),
    Filterable {

    var userFilterList = ArrayList<Results>(userList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.userName.text = "${userList[position].name.first} ${userList[position].name.last}"
        holder.userEmail.text = userList[position].email
        Picasso.get()
            .load(userList[position].picture.large)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.userImage)

        ViewCompat.setTransitionName(holder.userImage, "${userList[position].name.first} ${userList[position].name.last}")

        holder.itemView.setOnClickListener {
            onUserClickListener.onUserClickListener(userList[position])
        }

    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userEmail: TextView = itemView.findViewById(R.id.user_email)
        val userImage = itemView.findViewById<CircleImageView>(R.id.circleImageView)
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Results>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(userFilterList)
            } else {
                val filteredStrings: String = constraint.toString().toLowerCase().trim()

                for (results: Results in userFilterList) {
                    val fullName = "${results.name.first} ${results.name.last}"
                    if (fullName.toLowerCase().contains(filteredStrings)) {
                        filteredList.add(results)
                    }
                }
            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            userList.clear()
            userList.addAll(results?.values as ArrayList<Results>)
            notifyDataSetChanged()
        }

    }

    interface OnUserClickListener{
        fun onUserClickListener(results: Results)
    }

}