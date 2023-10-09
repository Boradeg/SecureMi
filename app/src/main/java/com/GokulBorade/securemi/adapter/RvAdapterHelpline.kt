package com.GokulBorade.securemi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.GokulBorade.securemi.R
import com.GokulBorade.securemi.dataClasses.ModalHelplinerRV

class RvAdapterHelpline(val requireContext: Context, private val phoneDetails: ArrayList<ModalHelplinerRV>):
    RecyclerView.Adapter<RvAdapterHelpline.exampleViewHolder>()
{
    private lateinit var mlistener: onItemClickListener
    interface onItemClickListener
    {
        fun onItemClick(position:Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener=listener
    }

    class exampleViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init{
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
        val personName: TextView = itemView.findViewById(R.id.EM_name)
        val personNumber: TextView = itemView.findViewById(R.id.EM_no)
        val personImg: ImageView = itemView.findViewById(R.id.EM_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exampleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_helpline, parent, false)
        return exampleViewHolder(view,mlistener)
    }

    override fun getItemCount(): Int {
        return phoneDetails.size
    }

    override fun onBindViewHolder(holder: exampleViewHolder, position: Int) {
        var food=phoneDetails[position]
        holder.personName.text = phoneDetails[position].rvName
        holder.personNumber.text = phoneDetails[position].rvNum
        holder.personImg.setImageResource(phoneDetails[position].img)

    }
}