package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

// 플래시 키고 끄는 클래스

class Torch(context: Context) {
    private var cameraId: String? = null // 카메라마다 고유한 id가 있다.
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager // 이 변수로 카메라 서비스를 사용가능하다.

    init{
        cameraId = getCameraId()
    }

    fun flashOn(){ // 플래시 켜기
        cameraManager.setTorchMode(cameraId.toString(), true)
    }

    fun flashOff(){ // 플래시 끄기
        cameraManager.setTorchMode(cameraId.toString(), false)
    }

    // 기기에서 카메라 id를 얻는다.
    private fun getCameraId(): String?{
        val cameraIds = cameraManager.cameraIdList // 기기가 가지고 있는 모든 카메라의 정보
        for(id in cameraIds){
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) // 카메라가 플래시 쓸수 있으면 true
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING) // 카메라의 렌즈 방향 LENS_FACING_BACK는 카메라가 기기의 뒷면에 있다.
            if(flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK){
                return id
            }
        }
        return null
    }
}