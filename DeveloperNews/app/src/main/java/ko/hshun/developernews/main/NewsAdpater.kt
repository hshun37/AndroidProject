package ko.hshun.developernews.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ko.hshun.developernews.R
import ko.hshun.developernews.api.NewsAPI
import ko.hshun.developernews.api.NewsData
import ko.hshun.developernews.api.WebViewActivity
import ko.hshun.developernews.databinding.ItemListBinding
import retrofit2.Response
import java.util.*

class NewsAdpater(val context: Context, val datas: List<NewsData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as NewsViewHolder).binding

        val data = datas[position].publishedAt
        val date = data.chunked(10)

//        binding.newsImage.setImageResource(R.drawable.news)
        Glide.with(context).load(datas[position].urlToImage).into(binding.newsImage)
        binding.newsText.text = datas[position].title
        binding.newsDate.text = datas[position].publishedAt

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", datas[position].url)
            holder.binding.root.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    inner class NewsViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }
    }



}