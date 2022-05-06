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
import com.example.patternclinic.data.model.Argument
import com.example.patternclinic.databinding.FragmentMessageBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.microsoft.signalr.*
import org.json.JSONObject


class MessageFragment : Fragment() {
    lateinit var binding: FragmentMessageBinding
    var connection: HubConnection? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(LayoutInflater.from(context))

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
            var json = JSONObject()
            var chat = getChat("PATIENT_1650619112058", SharedPrefs.getLoggedInUser()!!.authToken)
            json.put(ApiConstants.APIParams.SK.value, "PATIENT_1650619112058")
            json.put(
                ApiConstants.APIParams.AUTH_TOKEN.value,
                SharedPrefs.getLoggedInUser()!!.authToken
            )

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


                }, Array<Argument>::class.java
            )
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        connection!!.stop()
    }
}

data class getChat(var SK: String, var AuthToken: String)