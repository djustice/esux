package org.djustice.electrolux

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class FieldsAdapter(val c:Context,val itemList:ArrayList<ItemData>):RecyclerView.Adapter<FieldsAdapter.FieldsViewHolder>()
{

    inner class FieldsViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView = v.findViewById<TextView>(R.id.mTitle)

        init {
            name.setOnClickListener {
                println(name.text)
                val intent = Intent(c, WorkerSelection::class.java)
                intent.putExtra("fieldName", name.text)

                c.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return FieldsViewHolder(v)
    }

    override fun onBindViewHolder(holder: FieldsViewHolder, position: Int) {
        val newList = itemList[position]
        holder.name.text = newList.name
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }
}