package org.djustice.electrolux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.lang.Exception

class FormSelectionActivity : AppCompatActivity() {

    private lateinit var itemList:ArrayList<ItemData>
    private lateinit var formAdapter:FormAdapter
    private lateinit var recv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_selection)

        val fileName:String = "$filesDir/Forms.txt"
        itemList = ArrayList()
        recv = findViewById(R.id.formView)
        formAdapter = FormAdapter(this,itemList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = formAdapter

        try {
            val lines:List<String> = File(fileName).readLines()
            println(":: ----- splitline.size: " + lines[0].split(":").size)
            lines.forEach{ line ->
                println(line)
                itemList.add(ItemData(line.split(":")[0].split("|")[0]))
                formAdapter.notifyDataSetChanged()
            }
            lines[0].split(":").forEach{ field ->
                println(field)
//                Toast.makeText(this, "Field: " + field.split("|")[0], Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}