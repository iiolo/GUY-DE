package com.hifi.hifi_shopping.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hifi.hifi_shopping.databinding.FragmentAuthLoginBinding

class AuthLoginFragment : Fragment() {

    lateinit var fragmentAuthLoginBinding: FragmentAuthLoginBinding
    lateinit var authActivity : AuthActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentAuthLoginBinding = FragmentAuthLoginBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        fragmentAuthLoginBinding.run{

            // 회원가입 텍스트 클릭 => LoginFragment로 교체
            textViewAuthJoin.run{
                setOnClickListener{
                    authActivity.replaceFragment(AuthActivity.AUTH_JOIN_FRAGMENT, true, null)
                }
            }
            // 비번찾기 텍스트 클릭 => FindPwFragment
            textViewAuthFindPw.run{
                setOnClickListener{
                    authActivity.replaceFragment(AuthActivity.AUTH_FIND_PW_FRAGMENT, true, null)
                }
            }
        }

        return fragmentAuthLoginBinding.root
    }
}