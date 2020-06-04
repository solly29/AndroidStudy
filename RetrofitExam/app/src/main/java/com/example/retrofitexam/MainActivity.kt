package com.example.retrofitexam

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.JsonObject
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.ObjectInput


class MainActivity : AppCompatActivity() {

    var photoString : Uri? = null
    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //172.30.1.24:8080

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.24:8080")
            .addConverterFactory(ScalarsConverterFactory.create()) // 응답을 String으로 받으려고
            .addConverterFactory(GsonConverterFactory.create())    // 응답을 json으로 받아서 gson라이브러리가 자바형태로 직렬화, 역직렬화해서 사용하는데
                                                                   // retrofit이 gson라이브러리를 사용하기 때문에 이것을 자동으로 해준다.
            .build()

        val server = retrofit.create(retrofitService::class.java)

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON_TOUCH)
            .start(this)

        // select
        button_get.setOnClickListener {
            server.getRequest("1").enqueue(object:Callback<List<ResponseDTO>>{
                override fun onFailure(call: Call<List<ResponseDTO>>, t: Throwable) {
                    // object 모델이 ResponseDTO가 아니면 여기로온다.
                    println(t)
                }

                override fun onResponse(call: Call<List<ResponseDTO>>, response: Response<List<ResponseDTO>>) {
                    println(response.body().toString())
                }
            })
        }

        button_get_param.setOnClickListener {
            // 괄호안에는 경로를 넣는다. 그 경로로가서 결과값을 받아온다.
            server.getParamRequest("selectTest.jsp","2").enqueue((object:Callback<List<ResponseDTO>>{
                override fun onFailure(call: Call<List<ResponseDTO>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<ResponseDTO>>, response: Response<List<ResponseDTO>>) {
                    println(response.body().toString())
                }
            }))
        }

        // 디비에 값을 insert
        // 여기서 json을 만들어서 보내면 스트링 값으로 보내어지고
        // jsp에서 스트링을 json으로 변환해서 사용해야된다.
        button_post.setOnClickListener {
            val jsontest = JSONObject()
            jsontest.put("id", "6")
            jsontest.put("context", "test2")
            server.postRequest(jsontest).enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    println(response.body().toString())
                }
            })
        }

        button_update.setOnClickListener {
            server.putRequest("board01", "수정할 내용").enqueue(object : Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response.body().toString())
                }
            })
        }

        button_delete.setOnClickListener {
            server.deleteRequest("board01").enqueue(object: Callback<ResponseDTO>{
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response.body().toString())
                }
            })
        }

        // 이미지 업로드
        button_post_image.setOnClickListener {


            val requestBody = RequestBody.create(MediaType.parse("image/*"), File(photoString!!.path!!))
            val body : MultipartBody.Part = MultipartBody.Part.createFormData("a", "abc.jpg", requestBody)

            server.post_Image_Request("a","test", body).enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    println(response.body().toString())
                }
            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //requestCode에는 요청한 권한들의 결과가 전달된다. 승인되면 PERMISSION_GRANTED이 들어있음
        when(requestCode){
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //권한이 허용됨
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    //intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    startActivityForResult(intent, 1)
                }else{
                    //권한 거부
                    toast("권한 거부 됨") //anko 라이브러리
                }
                return
            }
        }
    }

    // 겔러리에서 사진을 선택했을때
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                photoString = result.uri
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    // 절대경로로 변경하는 메소드
    private fun getRealPathFromURI(contentUri: Uri): String {
        var column_index = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = getContentResolver().query(contentUri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        return cursor.getString(column_index)
    }

    // 사진 권한을 얻어온다.
    private fun setAuthority(){
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
            val intent = Intent(Intent.ACTION_PICK)
            //intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, 1)
        }
    }
}
