package com.example.mygallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {
    
    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //권한이 부여됬는지 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않음

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //이전에 이미 권한이거부되었을 때
                //이전에 거부가 되었으면 왜 권한이 필요한지 메시지를 띄우고 권한을 다시 요청한다.
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다","권한이 필요한 이유"){
                    yesButton {
                        //권한 요청
                        ActivityCompat.requestPermissions(this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton {  }
                }.show()
            }else{
                //권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_READ_EXTERNAL_STORAGE)
            }
        }else{
            getAllPhotos()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //requestCode에는 요청한 권한들의 결과가 전달된다. 승인되면 PERMISSION_GRANTED이 들어있음
        when(requestCode){
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //권한이 허용됨
                    getAllPhotos()
                }else{
                    //권한 거부
                    toast("권한 거부 됨") //anko 라이브러리
                }
                return
            }
        }
    }

    private fun getAllPhotos(){
        //모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//이미지를 가져올 경로(외부 저장소)
            null,       // 가져올 데이터 항목 배열
            null,        // 조건
            null,    // 조건
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")  // 찍은 날짜 순으로 내림차순

        val fragments = ArrayList<Fragment>()
        //사진이 없으면 null이다.
        if(cursor != null){
            //moveToNext으로 다음 정보(이미지)로 이동할수 있다. 정보가 더이상 없으면 false를 반환
            while (cursor.moveToNext()){
                //사진 경로 Uri 가져오기 getColumnIndexOrThrow으로 해당 칼럼이 데티어베이스에서 몇번째 인덱스인지 알 수 있다.
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            //더이상 쓰지 않으면 close한다. 안닫으면 메모리 누수가 발생한다.
            cursor.close()
        }

        // 어댑터
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter
    }
}
