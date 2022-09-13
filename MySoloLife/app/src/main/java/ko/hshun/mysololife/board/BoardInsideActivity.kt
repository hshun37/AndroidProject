package ko.hshun.mysololife.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ko.hshun.mysololife.R
import ko.hshun.mysololife.comment.CommentLVAdapter
import ko.hshun.mysololife.comment.CommentModel
import ko.hshun.mysololife.databinding.ActivityBoardInsideBinding
import ko.hshun.mysololife.utils.FBAuth
import ko.hshun.mysololife.utils.FBRef
import kotlin.math.log

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding

    private val TAG = "BoardInsideActivity"

    private lateinit var key : String

    private val CommentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        // 커스텀 다이얼로그
        binding.boardSetingIcon.setOnClickListener {
            showDialog()
        }

// 1. 첫번째 방법
//        val title = intent.getStringExtra("title")
//        val content = intent.getStringExtra("content")
//        val time = intent.getStringExtra("time")
//
//        binding.titleArea.text = title.toString()
//        binding.contentArea.text = content.toString()
//        binding.timeArea.text = time.toString()


        // 2. 두 번째 방법
        key = intent.getStringExtra("key").toString()
        getBaordData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener {
            unsertComment(key)
        }

        commentAdapter = CommentLVAdapter(CommentDataList)
        binding.commentLV.adapter = commentAdapter


        getCommentData(key)
    }

    fun getCommentData(key : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                CommentDataList.clear()
                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(CommentModel::class.java)
                    CommentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentdRef.child(key).addValueEventListener(postListener)
    }

    fun unsertComment(key: String) {
        //comment
        // - BoardKey
        //   - CommentKey
        //     - CommentData
        //     - CommentData
        //     - CommentData
        FBRef.commentdRef
            .child(key)
            .push()
            .setValue(
                CommentModel(
                    binding.contentArea.text.toString(),
                    FBAuth.getTime()
                )
            )

        Toast.makeText(this, "댓글 입력 완료", Toast.LENGTH_SHORT).show()
        binding.conmmentArea.setText("")
    }

    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this).load(task.result).into(imageViewFromFB)
            } else {
                binding.getImageArea.isVisible = false
            }
        })

    }

    private fun getBaordData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val data = dataSnapshot.getValue(BoardModel::class.java)

                    binding.titleArea.text = data?.title.toString()
                    binding.contentArea.text = data?.content.toString()
                    binding.timeArea.text = data?.time.toString()

                    val myUid = FBAuth.getUid()
                    val writerUid = data?.uid

                    if (myUid.equals(writerUid)) {
                        binding.boardSetingIcon.isVisible = true
                    }

                } catch (e: Exception) {
                    Log.d(TAG, "삭제완료")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }


    // 커스텀 다이얼로그
    private fun showDialog() {
        val mDialogView  = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "삭제완료", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}