package ko.hshun.mysololife.board

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import ko.hshun.mysololife.R
import ko.hshun.mysololife.utils.FBAuth

class BoardListLVAdapter(val boardList: MutableList<BoardModel>): BaseAdapter() {
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(p0: Int): Any {
        return boardList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
//        if (view == null) {

            view = LayoutInflater.from(p2?.context).inflate(R.layout.board_list_item, p2, false)
//        }

        val itemLinear = view?.findViewById<LinearLayout>(R.id.itemView)

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        if (boardList[p0].uid.equals(FBAuth.getUid())) {
            itemLinear?.setBackgroundColor(Color.parseColor("#ffa500"))
        }

        title?.text = boardList[p0].title
        content?.text = boardList[p0].content
        time?.text = boardList[p0].time


        return view!!
    }
}