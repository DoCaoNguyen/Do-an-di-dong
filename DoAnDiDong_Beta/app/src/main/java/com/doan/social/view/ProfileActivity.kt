package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doan.social.R
import com.doan.social.adapter.PostProfileAdapter
import com.doan.social.model.PostModel
import com.doan.social.model.UserProfileModel
import com.doan.social.viewmodel.UserViewmodel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import xyz.schwaab.avvylib.AvatarView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.collections.emptyList

class ProfileActivity : AppCompatActivity(), PostProfileAdapter.OnClickPostItem {
    private lateinit var img_setting_profile: ImageView
    private lateinit var avtV_user: AvatarView
    private lateinit var txt_userName: TextView
    private lateinit var txt_userGender: TextView
    private lateinit var txt_userPhone: TextView
    private lateinit var txt_userBirthday: TextView
    private lateinit var txt_followers: TextView
    private lateinit var txt_following: TextView
    private lateinit var rcv_postProfile: RecyclerView
    private val client = OkHttpClient()

    private fun saveUserToPrefs(user: UserProfileModel) {
        val prefs = getSharedPreferences("user_profile", MODE_PRIVATE)
        prefs.edit()
            .putString("avatar", user.avatarurl)
            .putString("username", user.username)
            .putString("gender", user.gender)
            .putString("phone", user.phone)
            .putString("birthday", user.birthday)
            .apply()
    }

    val postView = UserViewmodel(client)
    var listPosts: MutableList<PostModel> = mutableListOf<PostModel>()

    var user: UserProfileModel = UserProfileModel(0,"","","","","","","","", 0,0, emptyList())


    private val updateProfileLauncher =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                reloadProfile()
            }
        }


    fun reloadProfile() {
        val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
        val accessToken = userdata.getString("accessToken", "")
        val userId = userdata.getInt("userid",0)

        lifecycleScope.launch {
            user = postView.getUserProfile(accessToken)
            saveUserToPrefs(user)
            avtV_user.setAvatar(user.avatarurl)
            txt_userName.setText(user.username)
            txt_userGender.setText(user.gender)
            txt_userPhone.setText(user.phone)
            txt_userBirthday.setText(convertDateFormat(user.birthday))
            txt_followers.setText(user.followersCount.toString())
            txt_following.setText(user.followingCount.toString())
            listPosts =  postView.getPostProfile(userId)
            rcv_postProfile.layoutManager = LinearLayoutManager(this@ProfileActivity)
            rcv_postProfile.adapter = PostProfileAdapter(listPosts,this@ProfileActivity)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        avtV_user = findViewById(R.id.avtV_user)
        txt_userName =  findViewById(R.id.txt_userName)
        txt_userGender =  findViewById(R.id.txt_gender)
        txt_userPhone =  findViewById(R.id.txt_phone)
        txt_userBirthday =  findViewById(R.id.txt_birthday)
        txt_followers =  findViewById(R.id.txt_follower)
        txt_following =  findViewById(R.id.txt_following)
        rcv_postProfile = findViewById(R.id.rcv_postProfile)
        img_setting_profile = findViewById(R.id.img_setting_profile)

        reloadProfile()

        val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
        val accessToken = userdata.getString("accessToken", "")
        img_setting_profile.setOnClickListener {
            val intent = Intent(this, SettingProfileActivity::class.java)
            intent.putExtra("accessToken",accessToken)
            updateProfileLauncher.launch(intent)
        }

        val botNav = findViewById<BottomNavigationView>(R.id.btnNavi)
        botNav.setSelectedItemId(R.id.bottom_profile)

        botNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> {
                    finish()
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.bottom_search -> {
                    finish()
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.bottom_notification -> {
                    finish()
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }
                R.id.bottom_profile -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun onClickPostItem(post: Int) {
        val selectedPost = listPosts.find { postItem ->
            postItem.id == post
        }

        if (selectedPost != null) {
            val postJson = Json.encodeToString(selectedPost)

            val intent = Intent(this, PostDetailActivity::class.java)
            intent.putExtra("post_data", postJson)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Không tìm thấy bài viết", Toast.LENGTH_SHORT).show()
        }
    }

    fun AvatarView.setAvatar(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.avartar_profile)
            .error(R.drawable.avartar_profile)
            .circleCrop()
            .into(this)
    }

    fun convertDateFormat (date: String?): String {

        if (date.isNullOrBlank()) {
            return "Chưa có dữ liệu"
        }
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
        val date = LocalDate.parse(user.birthday, inputFormatter)

        return date.format(outputFormatter)
    }
}