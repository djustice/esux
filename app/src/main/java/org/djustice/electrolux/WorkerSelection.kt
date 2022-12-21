package org.djustice.electrolux

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class WorkerSelection : AppCompatActivity() {
    private lateinit var workerList:ArrayList<ItemData>
    private lateinit var workerAdapter:WorkerAdapter
    private lateinit var recv:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_selection)

        workerList = ArrayList()
        recv = findViewById(R.id.workerView)
        workerAdapter = WorkerAdapter(this,workerList)
        recv.layoutManager = GridLayoutManager(this, 2)
        recv.adapter = workerAdapter

        val intent = intent
        if(!intent.getStringExtra("fieldName").isNullOrEmpty()) {
            workerAdapter.selectedField = intent.getStringExtra("fieldName").toString()
            workerAdapter.getWorker = true
        }

        val fileName:String = "$filesDir/Workers.txt"
        try {
            val lines:List<String> = File(fileName).readLines()
            lines.forEach { line ->
                println(line)
                workerList.add(ItemData(line))
                workerAdapter.notifyDataSetChanged()
            }
        } catch (e:Exception) {
            println(e.message)
        }

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener { addWorker() }
    }

    private fun addWorker() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        val userName = v.findViewById<EditText>(R.id.userName)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = userName.text.toString()
            workerList.add(ItemData(names))
            workerAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
}