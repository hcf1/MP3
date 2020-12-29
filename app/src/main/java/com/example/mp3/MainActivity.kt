package com.example.mp3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mp3.databinding.ActivityMainBinding
import com.example.mp3.web.WebFragment


class MainActivity : AppCompatActivity() {
    private lateinit var webFragment: WebFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainActivity = this
        initView()
        openFragment(webFragment)
    }

    private fun initView() {
        webFragment = WebFragment()
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun searchClick() {
        openFragment(webFragment)
    }

    fun downLoadClick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val dirUri = FileProvider.getUriForFile(
            this,
            "myAuthority",
            this.getExternalFilesDir("download")!!
        )
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setDataAndType(dirUri, "*/*")
        startActivity(intent)
    }
}