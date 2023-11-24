package com.ashok.realtimedatabasefirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateUpdate : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update)

        val  note = findViewById<EditText>(R.id.editTextTextPersonName)
        val  button = findViewById<Button>(R.id.add)


        database = Firebase.database.reference



        if (intent.getStringExtra("MODE").equals("CREATE")){
            title="add"
            button.setOnClickListener{
                val key =  database.child("Notes").push().key
                database.child("Notes").child(key!!).setValue(Data(note.text.toString(),key)).addOnCompleteListener {
                        task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Note Added",Toast.LENGTH_LONG).show()
                        startActivity( Intent(this@CreateUpdate,MainActivity::class.java) )
                    }
                    else{
                        Toast.makeText(this,task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
        else{
            title="UPDATE"
            button.setText("UPDATE NOTE")
            note.setText(intent.getStringExtra("DATA"))
            val key = intent.getStringExtra("KEY")
             button.setOnClickListener{
                 val dbref = Firebase.database.getReference("Notes").child(key!!)
                 val data = Data(note.text.toString(),key)

                 dbref.setValue(data).addOnCompleteListener{
                         task ->
                     if(task.isSuccessful){
                         Toast.makeText(this,"Note Updated",Toast.LENGTH_LONG).show()
                         startActivity( Intent(this@CreateUpdate,MainActivity::class.java) )
                     }
                     else{
                         Toast.makeText(this,task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
                     }

                 }

             }



        }




    }




}