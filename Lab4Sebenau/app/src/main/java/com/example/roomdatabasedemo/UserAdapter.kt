package com.example.roomdatabasedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val onUserSelected: (User) -> Unit) : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    private var fullUserList: List<User> = listOf()

    fun submitFullList(users: List<User>) {
        fullUserList = users
        submitList(users)
    }

    fun filter(query: String) {
        val filteredList = if (query.isBlank()) {
            fullUserList
        } else {
            fullUserList.filter { user ->
                user.name.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(android.R.id.text1)
        private val ageTextView: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(user: User) {
            nameTextView.text = user.name
            ageTextView.text = "Age: ${user.age}"

            itemView.setOnClickListener {
                onUserSelected(user) // Notify the MainActivity about the selected user
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
