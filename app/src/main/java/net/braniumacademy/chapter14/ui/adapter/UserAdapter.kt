package net.braniumacademy.chapter14.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.data.model.User
import net.braniumacademy.chapter14.databinding.UserItemBinding
import net.braniumacademy.chapter14.ui.home.HomeFragment

class UserAdapter(
    private val users: MutableList<User> = mutableListOf(),
    private val menuListener: HomeFragment.OptionMenuClickListener,
    private val clickListener: HomeFragment.OnClickListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
        binding: UserItemBinding,
        private val menuListener: HomeFragment.OptionMenuClickListener,
        private val clickListener: HomeFragment.OnClickListener
    ) : RecyclerView.ViewHolder(view) {
        private val binding: UserItemBinding
        private lateinit var user: User

        init {
            this.binding = binding
            this.binding.btnOption.setOnClickListener { onOptionMenuClick() }
        }

        fun bindData(user: User) {
            this.user = user
            binding.textEmail.text = user.email
            binding.textFullName.text = user.fullName
            Glide.with(binding.root.context)
                .load(user.avatar)
                .error(R.drawable.ic_profile)
                .into(binding.imgAvatar)
            binding.root.setOnClickListener {
                clickListener.onCLick(user.id)
            }
        }

        private fun onOptionMenuClick() {
            // todo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: UserItemBinding = UserItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding, menuListener, clickListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(users[position])
    }

    fun updateData(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
    }
}