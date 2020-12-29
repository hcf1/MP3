package com.example.mp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
//todo 显示下载列表
    }
}