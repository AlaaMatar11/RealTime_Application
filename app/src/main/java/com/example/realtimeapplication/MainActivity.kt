package com.example.realtimeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.realtimeapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var id:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference()

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val number = binding.numberEditText.text.toString()
            val address = binding.addressEditText.text.toString()

            val person = hashMapOf(
                "name" to name,
                "number" to number,
                "address" to address
            )
          myRef.child("person").child("$id").setValue(person)
            id++
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }
        binding.getButton.setOnClickListener {
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                    binding.textData.text = value.toString()
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}