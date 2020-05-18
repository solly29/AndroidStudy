package com.example.kakaologintest

import android.app.Application
import com.kakao.auth.*

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        // Kakao Sdk 초기화
        KakaoSDK.init(KakaoSDKAdapter())
    }

    // 애플리케이션 객체와 모든 컴포넌트가 종료될 때 호출
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    inner class KakaoSDKAdapter : KakaoAdapter() {
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig { // object 키워드는 클래스를 정의 하면서 바로 객체를 생성한다. 여기선 무명 클래스에 ISessionConfig인터페이스를 구현
                // 인증 수단을 설정한다.
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType? {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        // Application이 가지고 있는 정보를 얻기 위한 인터페이스
        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { globalApplicationContext!! }
        }
    }

    companion object {
        private var instance: GlobalApplication? = null
        val globalApplicationContext: GlobalApplication?
            get() { // getter를 커스텀으로 구현한다.
                checkNotNull(instance) { "This Application does not inherit com.kakao.GlobalApplication" } // instance가 null이면 {}안의 문장을 출력한다.
                return instance
            }
    }
}