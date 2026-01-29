package com.doan.social.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.doan.social.R
import com.doan.social.viewmodel.PostCreateViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.io.File


class PostCreateActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private lateinit var btn_backCratePost: ImageButton
    private lateinit var imgb_AddImage: ImageView
    private lateinit var btn_submitCreatePost: Button
    private lateinit var edt_titleCreatePost: EditText
    private lateinit var edt_ContentPost: EditText
    private lateinit var edt_TagCreatePost: EditText
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri == null) return@registerForActivityResult

            imgb_AddImage.setImageURI(uri)
            imgb_AddImage.visibility = View.VISIBLE

            selectedImageUri = uri
        }

    private fun showPickImageDialog() {
        val options = arrayOf("Chọn ảnh từ thư viện", "Huỷ")

        AlertDialog.Builder(this)
            .setTitle("Thêm ảnh")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> pickImageLauncher.launch("image/*")
                    else -> dialog.dismiss()
                }
            }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_create)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_backCratePost = findViewById(R.id.imgbtn_backCratePost)
        imgb_AddImage = findViewById(R.id.imgb_AddImage)
        btn_submitCreatePost = findViewById(R.id.btn_submitCreatePost)
        edt_titleCreatePost = findViewById(R.id.edt_titleCreatePost)
        edt_ContentPost = findViewById(R.id.edt_ContentPost)
        edt_TagCreatePost = findViewById(R.id.edt_TagCreatePost)

        val accessToken = getSharedPreferences("user_data", MODE_PRIVATE).getString("accessToken", "")
        val postCreateViewModel = PostCreateViewModel(client)

        btn_backCratePost.setOnClickListener {
            finish()
        }

        imgb_AddImage.setOnClickListener {
            showPickImageDialog()
        }

        btn_submitCreatePost.setOnClickListener {
            lifecycleScope.launch {
                try {
                    if (edt_titleCreatePost.text != null && edt_ContentPost.text != null && edt_TagCreatePost.text != null){

                        val postCreateResponse = postCreateViewModel.postCreatePost(accessToken, edt_titleCreatePost.text.toString(), edt_ContentPost.text.toString(), selectedImageUri, edt_TagCreatePost.text.toString(), contentResolver)

                        if (postCreateResponse.status == "success"){
                            AlertDialog.Builder(this@PostCreateActivity)
                                .setTitle("Thông báo")
                                .setMessage("Đăng bài viết thành công")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                    setResult(RESULT_OK)
                                    finish()
                                }
                                .show()
                        }else{
                            AlertDialog.Builder(this@PostCreateActivity)
                                .setTitle("Thông báo")
                                .setMessage("Đăng bài viết thất bại")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                }catch (e:Throwable){
                    Log.d("PostCreate","${e.message}")
                }
            }
        }

    }
}