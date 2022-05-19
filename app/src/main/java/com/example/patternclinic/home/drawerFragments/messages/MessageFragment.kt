package com.example.patternclinic.home.drawerFragments.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.FragmentMessageBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : Fragment() {
    lateinit var binding: FragmentMessageBinding
    var userDetail: LoginResponse? = null
    val viewModel: MessageFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(LayoutInflater.from(context))
        userDetail = SharedPrefs.getLoggedInUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clicks()
        setObservers()
        val map=HashMap<String,Any>()
        map[ApiConstants.APIParams.AUTH_TOKEN.value]=userDetail!!.authToken
        map[ApiConstants.APIParams.SK.value]=userDetail!!.patientInfo.sk
        viewModel.getUserChatList(map)

    }



        fun setObservers() {
            /**
             * observer for getUsersChat up response
             */
            activity?.let {
                viewModel.usersChat.observe(it) {
                    if (it.response == 1) {
                        binding.rvMessages.adapter =
                            MessageRecyclerAdapter(it.chatlist.toMutableList())
                    } else {
                        activity?.showToast(it.errorMessage)
                    }
                }
            }

            /**
             * observer for loader
             */
            activity?.let {
                viewModel.showLoader.observe(it)
                {
                    if (it) {
                        binding.loader.visibility = View.VISIBLE
                    } else {
                        binding.loader.visibility = View.GONE
                    }
                }
            }
            /**
             * observer for error-response
             */
            activity?.let {
                viewModel.errorMessage.observe(it)
                {
                    activity?.showToast(it)
                }
            }
            /**
             * observer for failure-response
             */
            activity?.let {
                viewModel.onFailure.observe(it)
                {
                    activity?.showToast(it.toString())
                }
            }

        }


    private fun clicks() {
        binding.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }


    }


}

