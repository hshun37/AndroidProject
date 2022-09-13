package ko.hshun.mysololife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ko.hshun.mysololife.R
import ko.hshun.mysololife.databinding.ActivityBoardEditBinding
import ko.hshun.mysololife.databinding.ActivityBoardInsideBinding
import ko.hshun.mysololife.utils.FBAuth
import ko.hshun.mysololife.utils.FBRef

class BoardEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardEditBinding

    private lateinit var key : String

    private val TAG = "BoardEditActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)


        key = intent.getStringExtra("key").toString()

        getBaordData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }
    }


    private fun getBaordData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val data = dataSnapshot.getValue(BoardModel::class.java)

                binding.titleArea.setText(data?.title)
                binding.contentArea.setText(data?.content)

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this).load(task.result).into(imageViewFromFB)
            } else {

            }
        })
    }

    private fun editBoardData(key : String) {
        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(
                    binding.titleArea.text.toString(),
                    binding.contentArea.text.toString(),
                    FBAuth.getUid(),
                    FBAuth.getTime()
                )
            )
        Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show()
        finish()
    }
}
