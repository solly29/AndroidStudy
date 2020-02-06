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

        // 이렇게 해야 웹뷰 호환이 잘된다.
        WebView.apply {
            settings.javaScriptEnabled = true // 자바스크립트 허용
            webViewClient = WebViewClient()
        }

        WebView.loadUrl("https://www.google.com")

        // 자판의 검색버튼을 눌렀을때
        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                WebView.loadUrl(urlEditText.text.toString())
                true
            }else
                false
        }

        registerForContextMenu(WebView) // 컨텍스트 메뉴 등록, 컨텍스트 메뉴는 화면을 꾹 눌렀을때(링크를) 나오는 메뉴이다.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu) //엑티비티에 표시
        return true
    }

    // 컨텍스트 메뉴를 쓰려면 이 메소드를 오버라이딩을 해야된다.
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu) // 엑티비티에 표시
    }

    // 옵션 메뉴를 선택했을때 item에 선택한 메뉴의 id가 들어있다.
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
                // 암시적 인텐트 : 안드로이드에서 미리 정의된 인텐트이다.
                // 암시적 인텐트의 종류는 전화, 문자, 웹 브라우저, 공유, 이메일 보내기가 있다.
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

    // 컨텍스트 메뉴를 선택했을때
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

    // 뒤로가기를 눌렀을때
    override fun onBackPressed() {
        if(WebView.canGoBack())
            WebView.goBack()
        else
            super.onBackPressed()
    }
}
