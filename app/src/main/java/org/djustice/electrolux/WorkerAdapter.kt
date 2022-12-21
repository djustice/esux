package org.djustice.electrolux

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import org.djustice.electrolux.R
import org.djustice.electrolux.ItemData

class WorkerAdapter(val c:Context,val workerList:ArrayList<ItemData>):RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>()
{

    public var getWorker:Boolean = false
    public var selectedField:String = ""

    inner class WorkerViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView = v.findViewById<TextView>(R.id.mTitle)
        var mMenus:ImageView = v.findViewById(R.id.mMenus)

        init {
            name.setOnClickListener {
                if(getWorker) {
                    var intent = Intent(c, MainActivity::class.java)
                    intent.putExtra("fieldName", selectedField)
                    intent.putExtra("workerName", name.text)
                    c.startActivity(intent)
                    getWorker = false
                }
                println(name.text)
            }
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            val position = workerList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.userName)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.name = name.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_launcher_foreground)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                workerList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return WorkerViewHolder(v)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val newList = workerList[position]
        holder.name.text = newList.name
    }

    override fun getItemCount(): Int {
        return  workerList.size
    }
}