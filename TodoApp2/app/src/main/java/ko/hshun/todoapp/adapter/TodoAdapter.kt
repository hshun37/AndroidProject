package ko.hshun.todoapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ko.hshun.todoapp.databinding.ItemTodoListBinding
import ko.hshun.todoapp.model.room.TodoDatabase
import ko.hshun.todoapp.model.room.TodoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoAdapter(val context: Context, private val data: List<TodoEntity>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        return ViewHolder(
            ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        val db = TodoDatabase.getDatabase(context)
        holder.binding.textView.text = data[position].text
        holder.binding.checkBoxTrue.isVisible = data[position].check!!

        holder.binding.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.todoDao().deleteSelect(data[position])
            }
        }

            holder.binding.checkBoxFalse.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (data[position].check == false) {
                        db.todoDao().update(true, data[position].id)
                    }
                }
            }

        holder.binding.checkBoxTrue.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (data[position].check == true) {
                    db.todoDao().update(false, data[position].id)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }
    }
}