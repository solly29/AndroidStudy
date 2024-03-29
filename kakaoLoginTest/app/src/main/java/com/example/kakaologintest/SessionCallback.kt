package com.example.kakaologintest

import android.util.Log
import com.kakao.auth.ApiResponseCallback
import com.kakao.auth.AuthService
import com.kakao.auth.ISessionCallback
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.kakao.sdk.user.model.AccessTokenInfo
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException


class SessionCallback : ISessionCallback {
    //private val disposables: CompositeDisposable = CompositeDisposable()
    // 로그인에 성공한 상태
    override fun onSessionOpened() {
        requestMe()
    }

    // 로그인에 실패한 상태
    override fun onSessionOpenFailed(exception: KakaoException) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.message)
    }

    /*fun userMe() {
        UserApiClient.rx.me()
            .subscribe ({ user -> }) { error -> *//* 에러 처리 *//*}
            .addTo(disposables)
    }*/

    // 사용자 정보 요청
    fun requestMe() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
            }

            override fun onFailure(errorResult: ErrorResult) {
                Log.e("KAKAO_API", "사용자 정보 요청 실패: $errorResult")
            }

            override fun onSuccess(result: MeV2Response) {
                Log.i("KAKAO_API", "사용자 아이디: " + result.id)
                val kakaoAccount = result.kakaoAccount
                if (kakaoAccount != null) {

                    // 이메일
                    val email = kakaoAccount.email
                    if (email != null) {
                        Log.i("KAKAO_API", "email: $email")
                    } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                        // 동의 요청 후 이메일 획득 가능
                        // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.
                    } else {
                        // 이메일 획득 불가
                    }

                    // 프로필
                    val profile = kakaoAccount.profile
                    if (profile != null) {
                        Log.d("KAKAO_API", "nickname: " + profile.nickname)
                        Log.d("KAKAO_API", "profile image: " + profile.profileImageUrl)
                        Log.d("KAKAO_API", "thumbnail image: " + profile.thumbnailImageUrl)
                    } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                        // 동의 요청 후 프로필 정보 획득 가능
                    } else {
                        // 프로필 획득 불가
                    }

                    // 토큰 정보 보기
                    AuthService.getInstance()
                        .requestAccessTokenInfo(object : ApiResponseCallback<AccessTokenInfoResponse>(){
                            override fun onFailure(errorResult: ErrorResult?) {
                                Log.e("KAKAO_API", "토큰 정보 요청 실패: $errorResult")
                            }

                            override fun onSuccess(result: AccessTokenInfoResponse?) {
                                Log.i("KAKAO_API", "사용자 아이디: " + result?.userId)
                                Log.i("KAKAO_API", "남은 시간 (ms): " + result?.expiresInMillis)
                            }

                            override fun onSessionClosed(errorResult: ErrorResult?) {
                                Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
                            }

                        })
                }
            }
        })
    }
}