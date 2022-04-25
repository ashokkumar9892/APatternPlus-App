package com.example.patternclinic.selectTeam

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.getResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class SelectTeamViewModel @Inject constructor(val mainRepository: MainRepository):BaseViewModel() {
    var selectTeamModel=MutableLiveData<ResponseBody>()
    var binding=SelectPatternPlusTeam.binding


}