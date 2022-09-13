package ko.hshun.mysololife.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ko.hshun.mysololife.R
import ko.hshun.mysololife.contentsList.BookmarkModel
import ko.hshun.mysololife.databinding.ActivityBoardWriteBinding
import ko.hshun.mysololife.utils.FBAuth
import ko.hshun.mysololife.utils.FBRef
import java.io.ByteArrayOutputStream
import kotlin.math.log

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding
    val TAG = "BoardWriteActivity"
    private var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            // FireBase Store에 이미지를 저장하고 싶다
            // 만약 내가 게시글에 글을 작성했을때, 게시글에 대한 정보를 받아오면서 이미지 값도 같은 키로 사용하면서 이미즈를 저장하면서 불러올때 같은 키값으로 이미지를 불러온다.
            // 결국 이미지 이름을 모르기때문에 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 싶게 해준 것이다.
            val key = FBRef.boardRef.push().key.toString()

            //board -> key -> boardModel(title, content, uid, time)
            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title, content, uid, time))

            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_SHORT).show()

            if (isImageUpload == true) {
                imageUpload(key)
            }
            finish()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }
    }

    private fun imageUpload(key : String) {
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")


        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }
}