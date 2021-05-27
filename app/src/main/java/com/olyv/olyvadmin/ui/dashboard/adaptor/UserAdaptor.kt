package com.olyv.olyvadmin.ui.dashboard.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.olyv.olyvadmin.R
import com.olyv.olyvadmin.model.UserDataByAdmin

class UserAdaptor(
    val list: ArrayList<UserDataByAdmin>,
    val toggleRight: (String, Boolean) -> Unit
) : RecyclerView.Adapter<UserAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.child_users, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.switch_main.text = list[position].number
        holder.switch_main.isChecked = list[position].isAllowed
        holder.switch_main.setOnCheckedChangeListener { buttonView, isChecked ->
            toggleRight(list[position].uuid,isChecked)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val switch_main = itemView.findViewById<SwitchCompat>(R.id.switch_main)
    }

}