package com.example.gpsmap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private lateinit var fusedLocatProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // 지도가 준비되면 알림을 받는다.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit()
    }

    private fun locationInit(){
        fusedLocatProviderClient = FusedLocationProviderClient(this)

        locationCallback =  MyLocationCallBack()

        //위치 정보 요청에 대한 세부 정보를 설정한다.
        locationRequest = LocationRequest()

        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY//정확도 설정(지금 설정한것이 가장 정확한 위치이다.)
        locationRequest.interval = 10000 // 위치를 갱신하는데 필요한 시간(밀리초 단위)
        locationRequest.fastestInterval = 5000 // 다른 앱에서 위치를 갱신했을때 그 정보를 확인하는 시간
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * 지도를 사용할 준비가 되었으면 밑의 메소드를 호출한다.
     * 만얃 google play 서비스가 설치되어 있지 않으면 설치하라는 메세지가 나오고
     * 설치하고 다시 앱으로 돌아오면 밑의 메소드를 호출한다.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0) //해당 위도, 경도의 위치
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")) // 위의 위치로 마커를 이동시킨다. 여기선 시드니로
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    // 액티비티가 활성화가 되면 실행한다.
    override fun onResume() {
        super.onResume()

        //이렇게 해도되고
        //permissionCheck(::showPermissionInfoDialog,::addLocationListener)

        permissionCheck(cancel = {showPermissionInfoDialog()}, ok = {addLocationListener()}) // 이렇게 해도됨
    }

    // 액티비티가 가려지거나 앱이 작동하지 않으면 위치 정보를 삭제한다.
    override fun onPause() {
        super.onPause()
        removeLoactionListener()
    }

    private fun removeLoactionListener(){
        // 현제 위치 요청 삭제
        fusedLocatProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun addLocationListener(){ // 위치 정보 요청하기
        fusedLocatProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
               cancel()
            }else{
                //권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_ACCESS_FINE_LOCATION)
            }
        }else{
            ok()
        }
    }

    private fun showPermissionInfoDialog(){
        //이전에 이미 권한이거부되었을 때
        //이전에 거부가 되었으면 왜 권한이 필요한지 메시지를 띄우고 권한을 다시 요청한다.
        alert("현재 위치 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다","권한이 필요한 이유"){
            yesButton {
                //권한 요청 여기서 this는 DialogInterface 객체이다. 액티비티를 명시적으로 나타내려면 @를 써야된다.
                ActivityCompat.requestPermissions(this@MapsActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton {  }
        }.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //requestCode에는 요청한 권한들의 결과가 전달된다. 승인되면 PERMISSION_GRANTED이 들어있음
        when(requestCode){
            REQUEST_ACCESS_FINE_LOCATION -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //권한이 허용됨
                    addLocationListener()
                }else{
                    //권한 거부
                    toast("권한 거부 됨") //anko 라이브러리
                }
                return
            }
        }
    }

    // 현재 위치에 대한 정보르 얻는다.
    inner class MyLocationCallBack : LocationCallback(){
        //위치 정보 갱신
        override fun onLocationResult(locationRequest: LocationResult?) {
            super.onLocationResult(locationRequest)

            val location = locationRequest?.lastLocation
            location?.run{
                // 현재 위치로 카메라이동하고 확대까지
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity","위도: ${latitude}, 경도: ${longitude}")
            }
        }
    }
}
