package com.game.game.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.game.game.R
import com.game.game.data.SharedPreferencesGame
import com.github.dhaval2404.imagepicker.ImagePicker

class EditActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var buttonAddPhoto: ImageView
    private lateinit var textInputLayout: com.google.android.material.textfield.TextInputLayout
    private lateinit var textInputEditText: com.google.android.material.textfield.TextInputEditText
    private lateinit var buttonSaveChanges: AppCompatButton

    private lateinit var imageViewAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initViews()
        setListeners()
        if (SharedPreferencesGame.getPathAvatar(this) != "") {
            Glide.with(this).load(SharedPreferencesGame.getPathAvatar(this)).circleCrop().into(imageViewAvatar)
        }
    }

    //launch activity fun
    companion object {
        fun launch(activity: AppCompatActivity) {
            val intent = Intent(activity, EditActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto)
        textInputLayout = findViewById(R.id.textInputLayout)
        textInputEditText = findViewById(R.id.textInputEditText)
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges)
        imageViewAvatar = findViewById(R.id.imageViewAvatar)
    }

    private fun setListeners() {
        toolbar.setNavigationOnClickListener { onBackPressed() }
        buttonAddPhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }
        textInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboardAndClearFocus()
                true
            } else {
                false
            }
        }
        buttonSaveChanges.setOnClickListener {
            val name = textInputEditText.text.toString()
            //text > 3 and text < 15
            if (name.length < 3 || name.length > 15) {
                textInputLayout.error = "Name must be between 3 and 15 characters"
            } else {
                SharedPreferencesGame.setName(this, name)
                SettingsActivity.launch(this)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyboardAndClearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboardAndClearFocus() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        textInputLayout.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            SharedPreferencesGame.setPathAvatar(this, uri.toString())
            imageViewAvatar.setImageURI(uri)
            Glide.with(this).load(uri).circleCrop().into(imageViewAvatar)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        SettingsActivity.launch(this)
    }
}
