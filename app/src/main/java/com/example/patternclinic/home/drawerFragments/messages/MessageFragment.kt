package com.example.patternclinic.home.drawerFragments.messages

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.GetUserChatResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.FragmentMessageBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.utils.SharedPrefs
import com.microsoft.signalr.*


class MessageFragment : Fragment() {
    lateinit var binding: FragmentMessageBinding
    var connection: HubConnection? = null
    var userDetail:LoginResponse?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(LayoutInflater.from(context))
        userDetail=SharedPrefs.getLoggedInUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        binding.loader.visibility=View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            makeConnection()
        },700)

    }

    private fun makeConnection() {
        connection = HubConnectionBuilder.create(ApiConstants.CHAT_HUB_URL).build()
        connection!!.start().blockingAwait()

        if (connection!!.connectionState.name.equals("connected", true)) {

            val chat = GetChats(userDetail!!.patientInfo.sk, userDetail!!.authToken)


            try {
                connection!!.invoke("UserChatList", chat)
            } catch (e: Exception) {
                Log.e("exception socket", e.toString())
            }



            connection?.on(
                "ShowUserChatList",
                {
                    activity?.runOnUiThread {
                        binding.loader.visibility=View.GONE
//                        activity?.showToast(it.toString())
                        binding.rvMessages.adapter =
                            MessageRecyclerAdapter(it.toMutableList())
                    }


                }, Array<GetUserChatResponse>::class.java
            )
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        connection!!.stop()
    }
}

data class GetChats(var SK: String, var AuthToken: String)