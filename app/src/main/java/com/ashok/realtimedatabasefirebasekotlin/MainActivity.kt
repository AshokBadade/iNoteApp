package com.ashok.realtimedatabasefirebasekotlin

import android.content.Intent
import android.content.ReceiverCallNotAllowedException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = ArrayList<Data>()
        supportActionBar?.hide()


        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val rvadpater = RvAdapter(list,this)
        recyclerView.adapter = rvadpater

        databaseRef = Firebase.database.getReference("Notes")
        databaseRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for(datasnap in dataSnapshot.children){
                   val data = datasnap.getValue(Data::class.java)
                    list.add(data!!)
                    rvadpater.notifyDataSetChanged()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        } )


        val editbutton = findViewById<ImageButton>(R.id.imageButton)
        editbutton.setOnClickListener{
            startActivity(Intent(this,CreateUpdate::class.java ).putExtra("MODE","CREATE"))
        }


    }
}