package ko.hshun.movie.viewmodel.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ko.hshun.movie.R
import ko.hshun.movie.databinding.ItemListBinding
import ko.hshun.movie.view.main.activity.WebActivity

class HomeMovieAdapter(
    private val names: ArrayList<String>,
    private val images: ArrayList<String>,
    private val links: ArrayList<String>
) :
    RecyclerView.Adapter<HomeMovieAdapter.ViewHolder>() {

    private lateinit var binding: ItemListBinding
    private val TAG = "RecyclerView"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : HomeMovieAdapter.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeMovieAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(names[position], images[position], links[position])
    }

    override fun getItemCount(): Int {
        return names.size
    }

    inner class ViewHolder(val binding: ItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, image: String, link: String) {
            binding.movieTitle.text = name
            Glide.with(binding.root)
                .load(image)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.movieImage)
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, WebActivity::class.java)
                intent.putExtra("url", link)
                binding.root.context.startActivity(intent)
            }
        }
    }
}