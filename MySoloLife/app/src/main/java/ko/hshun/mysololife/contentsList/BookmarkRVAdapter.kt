package ko.hshun.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ko.hshun.mysololife.R
import ko.hshun.mysololife.utils.FBAuth
import ko.hshun.mysololife.utils.FBRef

class BookmarkRVAdapter(
    val context: Context,
    val items: ArrayList<ContentModel>,
    val keyList: ArrayList<String>,
    val bookmarkIdList: MutableList<String>
) : RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {
        holder.binding(items[position], keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(item: ContentModel, key: String) {

            itemView.setOnClickListener {
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }

            val contentTilte = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if (bookmarkIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }


            contentTilte.text = item.title
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }
    }
}