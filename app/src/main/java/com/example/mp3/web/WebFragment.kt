package com.example.mp3.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mp3.R
import com.example.mp3.download.view.MultiThreadDownLoadFragment
import kotlinx.android.synthetic.main.fragment_web.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WebFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
    }

    private fun initWebView() {
        webView.apply {
            //如果不设置WebViewClient，请求会跳转系统浏览器
            setWebViewClient()
            setDownloadListener()
            initSettings()
            loadUrl("http://music.ifkdy.com")
        }
    }

    private fun WebView.setWebViewClient() {
        webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }
        }
    }

    private fun WebView.initSettings() {
        settings.apply {
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
            //允许脚本
            javaScriptEnabled = true
            useWideViewPort = true //将图片调整到适合webview的大小

            loadWithOverviewMode = true //缩放操作

            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。

            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放

            displayZoomControls = false //隐藏原生的缩放控件

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存

            allowFileAccess = true //设置可以访问文件

            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口

            loadsImagesAutomatically = true //支持自动加载图片

            defaultTextEncodingName = "utf-8" //设置编码格式
        }
    }

    private fun WebView.setDownloadListener() {
        setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            val multiThreadDownLoadFragment=MultiThreadDownLoadFragment()
            multiThreadDownLoadFragment.arguments=Bundle().apply {
                putString("url",url)
            }
            /**使用app下载*/
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.mainFragment, multiThreadDownLoadFragment).addToBackStack(null)
                    .commit()
            }
            /**使用默认浏览器下载*/
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.addCategory(Intent.CATEGORY_BROWSABLE)
//            intent.data = Uri.parse(url)
//            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WebFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}