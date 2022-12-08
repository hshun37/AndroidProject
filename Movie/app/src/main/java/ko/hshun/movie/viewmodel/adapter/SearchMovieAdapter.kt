package ko.hshun.movie.viewmodel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ko.hshun.movie.R
import ko.hshun.movie.databinding.SearchItemListBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.naver_item.Item
import ko.hshun.movie.view.main.activity.WebActivity

class SearchMovieAdapter(private val items: List<Item?>?) :
    RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {
    private lateinit var binding: SearchItemListBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMovieAdapter.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.search_item_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchMovieAdapter.ViewHolder, position: Int) {
        holder.bind(items!![position]!!)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    inner class ViewHolder(val binding: SearchItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            if (item.image != "") {
                val title = item.title!!.replace("<b>", "").replace("</b>", "").replace("amp;", "")
                binding.movieTitle.text = title
                Glide.with(binding.root)
                    .load(item.image)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.movieImage)
                binding.root.setOnClickListener {
                    val intent = Intent(binding.root.context, WebActivity::class.java)
                    intent.putExtra("url", item.link)
                    binding.root.context.startActivity(intent)
                }
            } else {
                val title = item.title!!.replace("<b>", "").replace("</b>", "").replace("&amp;", "")
                binding.movieTitle.text = title
                binding.movieImage.setImageResource(R.drawable.ic_no_image)
                binding.root.setOnClickListener {
                    val intent = Intent(binding.root.context, WebActivity::class.java)
                    intent.putExtra("url", item.link)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }
}