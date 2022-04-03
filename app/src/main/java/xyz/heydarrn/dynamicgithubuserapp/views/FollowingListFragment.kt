package xyz.heydarrn.dynamicgithubuserapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.heydarrn.dynamicgithubuserapp.databinding.FragmentFollowingListBinding
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.FollowingAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.FollowingViewModel

class FollowingListFragment : Fragment() {
    private var _followingBind:FragmentFollowingListBinding? =null
    private val followingBind get() = _followingBind
    private val followingAdapter by lazy { FollowingAdapter() }
    private val followingViewModel by viewModels<FollowingViewModel>()
    private lateinit var followingUsername:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _followingBind= FragmentFollowingListBinding.inflate(inflater,container,false)
        return followingBind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFollowingRecyclerView()
        setFollowingData()
        observeFollowingData()
    }

    private fun setFollowingRecyclerView(){
        followingAdapter.notifyDataSetChanged()
        followingBind?.recyclerViewFollowing?.apply {
            this.setHasFixedSize(true)
            this.layoutManager= LinearLayoutManager(context)
            this.adapter=followingAdapter
        }
    }

    private fun setFollowingData(){
        val arguments=arguments
        followingUsername=arguments?.getString(DetailOfUserActivity.SEND_USERNAME).toString()
        followingViewModel.viewModelScope.launch(Dispatchers.IO){
            followingViewModel.showFollowingInfo(followingUsername)
        }
    }

    private fun observeFollowingData(){
        followingViewModel.viewModelScope.launch(Dispatchers.Main){
            followingViewModel.monitorFollowingInfo().observe(viewLifecycleOwner){followingObserver ->
                if (followingObserver!=null){
                    false.showFollowingLoading()
                    followingAdapter.setFollowingListForAdapter(followingObserver)
                }
            }
        }


    }

    private fun Boolean.showFollowingLoading() {
        when(this){
            true -> followingBind?.progressBarFollowing?.visibility=View.VISIBLE
            false -> followingBind?.progressBarFollowing?.visibility=View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _followingBind=null
    }
}