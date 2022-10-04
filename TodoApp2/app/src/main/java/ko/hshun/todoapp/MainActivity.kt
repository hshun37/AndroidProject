package ko.hshun.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ko.hshun.todoapp.model.room.TodoDatabase
import ko.hshun.todoapp.model.room.TodoEntity
import ko.hshun.todoapp.adapter.TodoAdapter
import ko.hshun.todoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val db = TodoDatabase.getDatabase(this)

        /*CoroutineScope(Dispatchers.IO).launch {
            val rv = binding.rv
            val rvAdapter = TodoAdapter(baseContext, db.todoDao().getAllData())
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(baseContext)

            binding.check.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val text = binding.todoPlus.text.toString()
                    db.todoDao().insert(TodoEntity(0, text))
                    binding.todoPlus.setText("")
                }
            }

            binding.clear.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    db.todoDao().deleteAllData()
                }
            }
        }*/

        binding.check.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val text = binding.todoPlus.text.toString()
                db.todoDao().insert(TodoEntity(0, text))
                binding.todoPlus.setText("")
            }
        }

        binding.clear.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.todoDao().deleteAllData()
            }
        }

        db.todoDao().getLiveAllData().observe(this, Observer {
            val rv = binding.rv
            val rvAdapter = TodoAdapter(baseContext, it)
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(baseContext)
        })

    }
}