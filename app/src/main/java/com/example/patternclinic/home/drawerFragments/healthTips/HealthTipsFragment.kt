package com.example.patternclinic.home.drawerFragments.healthTips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.patternclinic.R
import com.example.patternclinic.databinding.FragmentHealthTipsBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.home.drawerFragments.healthTips.HealthTipViewModel
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthTipsFragment : Fragment() {

    private lateinit var binding: FragmentHealthTipsBinding
    val viewModel: HealthTipViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_health_tips, container, false)
        binding.viewModel = viewModel
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
//        bindingFragment.cv1.setOnClickListener {
//            startActivity(Intent(requireContext(),HealthTipDetailActivity::class.java).putExtra(Keys.FROM_CARD_ONE,"cv1"))
//        }
//        bindingFragment.cv2.setOnClickListener {
//            startActivity(Intent(requireContext(),HealthTipDetailActivity::class.java).putExtra(Keys.FROM_CARD_TWO,"cv2"))
//        }
    }
    private fun setObservers() {
        /**
         * observer for loader
         */
        viewModel.showLoader.observe( requireActivity())
        {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
            }
        }
        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe( requireActivity())
        {
            requireActivity().showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe( requireActivity())
        {
            requireActivity().showToast(it.toString())
        }
        viewModel.healthTipResponse.observe( requireActivity()){
            if(it.response==1){
                //nothing to do
            }else{
                requireActivity().showToast(it.errorMessage)
            }
        }

    }


}