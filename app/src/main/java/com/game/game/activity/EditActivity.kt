package com.game.game.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.game.game.R

class EditActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var buttonAddPhoto: ImageView
    private lateinit var textInputLayout: com.google.android.material.textfield.TextInputLayout
    private lateinit var textInputEditText: com.google.android.material.textfield.TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initViews()
        setListeners()
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
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setListeners() {
        buttonAddPhoto.setOnClickListener {
        }
        textInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboardAndClearFocus()
                true
            } else {
                false
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
}
