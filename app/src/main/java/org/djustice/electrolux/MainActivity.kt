package org.djustice.electrolux

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileWriter
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var fieldsList:ArrayList<ItemData>
    private lateinit var fieldsAdapter:FieldsAdapter
    private lateinit var recv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        fieldsList = ArrayList()
        recv = findViewById(R.id.fieldsView)
        fieldsAdapter = FieldsAdapter(this,fieldsList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = fieldsAdapter

        val selectedFormTextView = findViewById<TextView>(R.id.selectedFormTextView)

        val intent = intent
        val selectedField = intent.getStringExtra("fieldName")
        if (!selectedField.isNullOrBlank()) {
            println(":: -------selectedField non-null:$selectedField")
            selectedFormTextView.text = selectedField
        }

        val selectFormButton = findViewById<Button>(R.id.SelectFormButton)
        selectFormButton.setOnClickListener {
            val intent = Intent(this, FormSelectionActivity::class.java)
            startActivity(intent)
        }

        val editWorkersButton = findViewById<Button>(R.id.EditWorkersButton)
        editWorkersButton.setOnClickListener {
            val intent = Intent(this, WorkerSelection::class.java)
            startActivity(intent)
        }

        val fileName = "$filesDir/Forms.txt"
        println(File(fileName).absolutePath)

        val perms:Array<String> = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        try {
            checkPermissions(perms, 1)
            val fo = FileWriter(fileName)
            fo.write("Diamond Sheet 18/20|:" +
                    "Shift|:" +
                    "Date|:" +
                    "Compressor Base/Drip Pan|:" +
                    "Compressor/Starter Assembly|:" +
                    "Service Cord|:" +
                    "Ground Screws|:" +
                    "Condenser/Dogbone|:" +
                    "Set Automation|:" +
                    "P.I. Label/Drain Trough|:" +
                    "Dry Ice|:" +
                    "N2 Blowout/EVAP Label|:" +
                    "Evaporator Install|:" +
                    "Traveler/Plug Removal/Fire Label|:" +
                    "Drain Tube/Data Sheet|:" +
                    "Dryer|:" +
                    "Jumper|:" +
                    "Brazer/Lokring|:" +
                    "Brazer 1|:" +
                    "Brazer 2|:" +
                    "EVAP Brazer|:" +
                    "Restrictions Test|:" +
                    "Fittings|:" +
                    "Nitrogen Burst|:" +
                    "Helium Charging|:" +
                    "Helium Leak|:" +
                    "Helium Recovery|:" +
                    "Low Side Leak|:" +
                    "Line Side Repair**|:" +
                    "Team Leader**|:"
            )
            fo.close()

            val workersFile = "$filesDir/Workers.txt"
            val fw = FileWriter(workersFile)
            for (i:Int in 1..30) {
                fw.write("Name $i\n")
            }
            fw.close()
        } catch (e:Exception) {
            println(e.message)
        }

        println("::  -----------")

        if (selectedFormTextView.text == "No Form Selected")
            return

        try {
            val lines:List<String> =File(fileName).readLines()
            println(":: ----- splitline.size: " + lines[0].split(":").size)
            lines.forEach{ line -> println(line)}
            lines[0].split(":").forEach{ field ->

                if(field.split("|")[0] != lines[0].split("|")[0]) {
                    fieldsList.add(ItemData(field.split("|")[0] + ": " + field.split("|")[1]))
                    fieldsAdapter.notifyDataSetChanged()
                }
//                Toast.makeText(this, "Field: " + field.split("|")[0], Toast.LENGTH_SHORT).show()
            }

        } catch (e:Exception) {
            e.printStackTrace()
        }

        val selectedWorker = intent.getStringExtra("workerName")
        if (!selectedWorker.isNullOrEmpty()) {
            println(":: -------selectedWorker non-null:$selectedWorker")
            if (!selectedField.isNullOrEmpty()) {
                fieldsList.forEach { item ->
                    if (item.name.startsWith(selectedField)) {
                        item.name = "$selectedField$selectedWorker"
                        fieldsAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun checkPermissions(permission: Array<String>, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission[0]) == PackageManager.PERMISSION_DENIED) {
            println(":  -------- $permission DENIED")
            ActivityCompat.requestPermissions(this@MainActivity, permission, requestCode)
        } else {
            println(":  -------- $permission ALREADY GRANTED")
        }
    }
}