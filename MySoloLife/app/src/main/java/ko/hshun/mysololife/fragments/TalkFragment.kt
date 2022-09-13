package ko.hshun.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ko.hshun.mysololife.R
import ko.hshun.mysololife.board.BoardInsideActivity
import ko.hshun.mysololife.board.BoardListLVAdapter
import ko.hshun.mysololife.board.BoardModel
import ko.hshun.mysololife.board.BoardWriteActivity
import ko.hshun.mysololife.contentsList.ContentModel
import ko.hshun.mysololife.databinding.FragmentHomeBinding
import ko.hshun.mysololife.databinding.FragmentTalkBinding
import ko.hshun.mysololife.databinding.FragmentTipBinding
import ko.hshun.mysololife.utils.FBRef


class TalkFragment : Fragment() {

    private lateinit var binding: FragmentTalkBinding

    private val TAG = "TalkFragment"

    private val boardDataList = mutableListOf<BoardModel>()

    private val boardKeyList = mutableListOf<String>()

    private lateinit var boardRVAdapter : BoardListLVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter


        binding.boardListView.setOnItemClickListener { adapterView, view, position, id ->

            // 1. listview에 있는 데이터(title, content, time)를 다른 액티비티로 전달해줘서 만들기
//            val intent = Intent(context, BoardInsideActivity::class.java)
//            intent.putExtra("title", boardDataList[position].title)
//            intent.putExtra("content", boardDataList[position].content)
//            intent.putExtra("time", boardDataList[position].time)
//            startActivity(intent)

            // 2. FireBase의 아이디 값을 전달해줘서 id값을 기반으로 다시 데이터를 받아오는거
            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)

        }





        binding.writeBtn.setOnClickListener {
            val intent = Intent(requireContext(), BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.homeTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookmarkTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        getFBBoardData()

        return binding.root
    }

    private fun getFBBoardData() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                boardDataList.clear()
                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

    }

}