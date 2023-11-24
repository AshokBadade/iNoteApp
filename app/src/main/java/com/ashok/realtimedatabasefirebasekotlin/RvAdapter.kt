package com.ashok.realtimedatabasefirebasekotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RvAdapter(val  datalist : ArrayList<Data> , val context : Context)   : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    open class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
          val  textview =  itemView.findViewById<TextView>(R.id.textView)
         val Deletebutton = itemView.findViewById<Button>(R.id.delete)
         val updatebutton = itemView.findViewById<Button>(R.id.update)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.revdesign,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.textview.setText(datalist.get(position).data)
         holder.Deletebutton.setOnClickListener{
             val key = datalist.get(position).key
             val dbref = Firebase.database.getReference("Notes").child(key!!)
             dbref.removeValue().addOnCompleteListener{
                 if(it.isSuccessful){
                      notifyDataSetChanged()
                     Toast.makeText(context,"data removed",Toast.LENGTH_LONG).show()
                 }
             }
         }

        holder.updatebutton.setOnClickListener{
            val intent = Intent(context,CreateUpdate::class.java)
            intent.putExtra("MODE","UPDATE")
            intent.putExtra("KEY",datalist.get(position).key)
            intent.putExtra("DATA",datalist.get(position).data )
            context.startActivity(intent)

        }



    }



}