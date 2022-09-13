package ko.hshun.mysololife.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import ko.hshun.mysololife.R
import ko.hshun.mysololife.board.BoardModel
import ko.hshun.mysololife.utils.FBAuth

class CommentLVAdapter(val commntList: MutableList<CommentModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return commntList.size
    }

    override fun getItem(p0: Int): Any {
        return commntList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1

        if (view == null) {
            view = LayoutInflater.from(p2?.context)
                .inflate(R.layout.comment_list_item, p2, false)
        }

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)


        title?.text = commntList[p0].commentTitle
        time?.text = commntList[p0].commentCreatedTime


        return view!!
    }
}