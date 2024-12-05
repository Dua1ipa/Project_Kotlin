package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LoginActivity"
    }
    private lateinit var binding : ActivityLoginBinding
    // 카카오계정으로 로그인 공통 callback 구성 //
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    // 네이버 로그인 런처 //
//    private val launcher = registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result ->
//        when(result.resultCode) {
//            RESULT_OK -> {
//                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
////                binding.tvAccessToken.text = NaverIdLoginSDK.getAccessToken()
////                binding.tvRefreshToken.text = NaverIdLoginSDK.getRefreshToken()
////                binding.tvExpires.text = NaverIdLoginSDK.getExpiresAt().toString()
////                binding.tvType.text = NaverIdLoginSDK.getTokenType()
////                binding.tvState.text = NaverIdLoginSDK.getState().toString()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//            RESULT_CANCELED -> {
//                // 실패 or 에러
//                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
//                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                Toast.makeText(this, "errorCode:$errorCode, errorDesc:$errorDescription", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kakao SDK 초기화
        KakaoSdk.init(this, "{NATIVE_APP_KEY}")
        // Naver SDK 초기화
        NaverIdLoginSDK.initialize(this, "tIMR_4wnaQ3H8wQBdEP4", "4sBmuDOm3a", "LoginModule")

        binding.kakaoLoginImageView.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

            // 토큰 정보 보기
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Log.e(TAG, "토큰 정보 보기 실패", error)
                }
                else if (tokenInfo != null) {
                    Log.i(TAG, "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초")
                }
            }
        }

//        binding.naverOauthLoginButton.setOAuthLogin(launcher)
//        NaverIdLoginSDK.authenticate(this, launcher)
    }

}