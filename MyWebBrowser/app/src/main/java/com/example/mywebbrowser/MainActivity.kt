package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        WebView.loadUrl("https://www.google.com")

        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                WebView.loadUrl(urlEditText.text.toString())
                true
            }else
                false
        }

        registerForContextMenu(WebView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu) //엑티비티에 표시
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_google, R.id.action_home -> {
                WebView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver -> {
                WebView.loadUrl("https://m.naver.com")
                return true
            }
            R.id.action_daum -> {
                WebView.loadUrl("https://www.daum.com")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-1234-5678")
                if(intent.resolveActivity(packageManager) != null)//해당 엑티비티(앱)이 있는지 확인(null이면 없음)
                    startActivity(intent)
            }
            R.id.action_text -> {
                //문자보내기
                sendSMS("010-1234-5678", WebView.url)
                return true
            }
            R.id.action_email -> {
                //이메일 보내기
                email("test@a.com", "좋은 사이트", WebView.url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                //페이지 공유
                share(WebView.url)
                return true
            }
            R.id.action_browser -> {
                //기본 웹 브라우저에서 열기
                browse(WebView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onBackPressed() {
        if(WebView.canGoBack())
            WebView.goBack()
        else
            super.onBackPressed()
    }
}
