package com.doan.social.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.net.toFile
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.doan.social.R
import com.doan.social.viewmodel.SettingProfileViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import xyz.schwaab.avvylib.AvatarView
import java.io.File

class SettingProfileActivity : AppCompatActivity() {

    private lateinit var avtV_User: AvatarView

    private lateinit var user_name: TextView

    private lateinit var img_btn_back_profile: ImageButton

    private lateinit var btn_Logout: Button

    private lateinit var btn_ChangeAvatar: Button

    private lateinit var btn_UpdateProfile: Button

    private lateinit var edt_phone: EditText

    private lateinit var edt_birthday: EditText

    private lateinit var rdb_nam: RadioButton

    private lateinit var rdb_nu: RadioButton

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        user_name = findViewById(R.id.txt_userName)
        avtV_User = findViewById(R.id.avtV_user)
        img_btn_back_profile = findViewById(R.id.img_btn_back_profile)
        btn_UpdateProfile = findViewById(R.id.btn_UpdateProfile)
        btn_ChangeAvatar = findViewById(R.id.btn_ChangeAvatar)
        btn_Logout = findViewById(R.id.btn_Logout)
        edt_birthday = findViewById(R.id.edt_birthday)
        edt_phone = findViewById(R.id.edt_phone)
        rdb_nam = findViewById(R.id.rdb_nam)
        rdb_nu = findViewById(R.id.rdb_nu)

        val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
        val accessToken = userdata.getString("accessToken", "")
        val settingProfileViewModel = SettingProfileViewModel(client, userdata)
        val gender = intent.getStringExtra("gender")
        val phone = intent.getStringExtra("phone")
        val birthday = intent.getStringExtra("birthday")
        val avatar = intent.getStringExtra("avatar")
        val username = intent.getStringExtra("username")

        //Sai Logic
        if (gender == "Nam") {
            rdb_nam.isChecked = true
            rdb_nu.isChecked = false
        } else {
            rdb_nam.isChecked = false
            rdb_nu.isChecked = true
        }

        avtV_User.setAvatar(avatar)
        user_name.setText(username)
        edt_birthday.setText(birthday)
        edt_phone.setText(phone)

        img_btn_back_profile.setOnClickListener {
            finish()
        }
        val onboardprefs = getSharedPreferences("onboarding_done", MODE_PRIVATE)
        btn_Logout.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val logoutResponse = settingProfileViewModel.postLogout(accessToken)
                    if (logoutResponse?.status == "success") {
                        onboardprefs.edit { putBoolean("onboarding_done", false) }
                        val intent = Intent(this@SettingProfileActivity, OnBoardingActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                }catch (e: Throwable){
                    Log.d("Logout","${e.message}")
                }
            }
        }

        btn_UpdateProfile.setOnClickListener {
            lifecycleScope.launch {
                try {
                    if (edt_phone.text.toString() != phone.toString() || edt_birthday.text.toString() != birthday.toString() || gender != if (rdb_nam.isChecked) "Nam" else "Nữ") {
                        val updateResponse = settingProfileViewModel.updateProfile(accessToken, edt_phone.text.toString(), if (rdb_nam.isChecked) "Nam" else "Nữ", edt_birthday.text.toString())

                        if (updateResponse?.status == "success") { //Sau khi update rồi
                            AlertDialog.Builder(this@SettingProfileActivity)
                                .setTitle("Thông báo")
                                .setMessage("Cập nhật thành công")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                    setResult(RESULT_OK)
                                    finish()
                                }
                                .show()
                        }

                    }
                }catch (e: Throwable){
                    Log.d("Logout","${e.message}")
                }
            }
        }

        btn_ChangeAvatar.setOnClickListener {

        }

        avtV_User.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

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


}