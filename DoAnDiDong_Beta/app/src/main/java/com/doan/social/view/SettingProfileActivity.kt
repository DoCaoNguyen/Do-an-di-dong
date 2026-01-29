package com.doan.social.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
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
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class SettingProfileActivity : AppCompatActivity() {

    private lateinit var avtV_User: AvatarView

    private lateinit var txt_username: TextView

    private lateinit var img_btn_back_profile: ImageButton

    private lateinit var btn_Logout: Button

    private lateinit var btn_ChangeAvatar: Button

    private lateinit var btn_UpdateProfile: Button

    private lateinit var edt_Phone: EditText

    private lateinit var edt_birthday: EditText

    private lateinit var rdb_nam: RadioButton

    private lateinit var rdb_nu: RadioButton

    private lateinit var rg_gender: RadioGroup


    private val client = OkHttpClient()
    private var selectedImageUri: Uri? = null
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri == null) return@registerForActivityResult

            avtV_User.setImageURI(uri)
            avtV_User.visibility = View.VISIBLE

            selectedImageUri = uri

            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            Glide.with(this)
                .load(uri)
                .circleCrop()
                .override(120, 100)
                .placeholder(R.drawable.avartar_profile)
                .into(avtV_User)
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
        setContentView(R.layout.activity_setting_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        txt_username = findViewById(R.id.txt_userName)
        avtV_User = findViewById(R.id.avtV_user)
        img_btn_back_profile = findViewById(R.id.img_btn_back_profile)
        btn_UpdateProfile = findViewById(R.id.btn_UpdateProfile)
        btn_ChangeAvatar = findViewById(R.id.btn_ChangeAvatar)
        btn_Logout = findViewById(R.id.btn_Logout)
        edt_birthday = findViewById(R.id.edt_birthday)
        edt_Phone = findViewById(R.id.edt_phone)
        rdb_nam = findViewById(R.id.rdb_nam)
        rdb_nu = findViewById(R.id.rdb_nu)
        rg_gender = findViewById(R.id.rg_gender)


        val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
        val accessToken = userdata.getString("accessToken", "")
        val settingProfileViewModel = SettingProfileViewModel(client, userdata)
        val prefs = getSharedPreferences("user_profile", MODE_PRIVATE)
        val avatar = prefs.getString("avatar", null)
        val username = prefs.getString("username", "")
        val gender = prefs.getString("gender", "")
        val phone = prefs.getString("phone", "")
        val birthday = prefs.getString("birthday", "")
        val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val onboardprefs = getSharedPreferences("onboarding_done", MODE_PRIVATE)

        when (gender) {
            "Nam" -> rdb_nam.isChecked = true
            "Nữ"  -> rdb_nu.isChecked = true
        }

        rg_gender.setOnCheckedChangeListener { _, checkedId ->
            val genderSelected = when (checkedId) {
                R.id.rdb_nam -> "Nam"
                R.id.rdb_nu -> "Nữ"
                else -> ""
            }

            sharedPref.edit()
                .putString("gender", genderSelected)
                .apply()
        }




        txt_username.setText(username)
        edt_Phone.setText(phone)
        edt_birthday.setText(convertDateFormat(birthday))

        Glide.with(this)
            .load(avatar)
            .placeholder(R.drawable.avartar_profile)
            .circleCrop()
            .into(avtV_User)

        img_btn_back_profile.setOnClickListener {
            finish()
        }

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
                    if (edt_Phone.text.toString() != phone.toString() || edt_birthday.text.toString() != birthday.toString() || gender != if (rdb_nam.isChecked) "Nam" else "Nữ") {
                        val updateResponse = settingProfileViewModel.updateProfile(accessToken, edt_Phone.text.toString(), if (rdb_nam.isChecked) "Nam" else "Nữ", edt_birthday.text.toString())// selectedImageUri, contentResolver)
                        if (updateResponse?.status == "success") {
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
                    Log.d("Update profile","${e.message}")
                }
            }
        }

        btn_ChangeAvatar.setOnClickListener {
            showPickImageDialog()
        }
    }

    fun convertDateFormat(date: String?): String {
        if (date.isNullOrBlank()) return ""

        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

            LocalDate.parse(date, inputFormatter).format(outputFormatter)
        } catch (e: Exception) {
            ""
        }
    }


}