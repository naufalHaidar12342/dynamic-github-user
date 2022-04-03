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
import xyz.heydarrn.dynamicgithubuserapp.databinding.FragmentFollowerListBinding
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.FollowersAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.FollowersViewModel

class FollowerListFragment : Fragment() {
    private var _followerBind:FragmentFollowerListBinding?=null
    private val followerBind get() = _followerBind
    private val followerAdapter by lazy { FollowersAdapter() }
    private val followerViewModel by viewModels<FollowersViewModel>()
    private lateinit var followerUsername:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _followerBind= FragmentFollowerListBinding.inflate(inflater,container,false)
        return followerBind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFollowerRecyclerView()
        setFollowerData()
        observeFollowerData()

    }

    private fun setFollowerRecyclerView(){
        followerAdapter.notifyDataSetChanged()
        followerBind?.recyclerViewFollowers?.apply {
            this.setHasFixedSize(true)
            this.layoutManager=LinearLayoutManager(context)
            this.adapter=followerAdapter
        }
    }

    private fun setFollowerData(){
        val argumentFollowers=arguments
        followerUsername=argumentFollowers?.getString(DetailOfUserActivity.SEND_USERNAME).toString()
        followerViewModel.setUserFollowersInfo(followerUsername)
    }

    private fun observeFollowerData(){
        followerViewModel.viewModelScope.launch(Dispatchers.Main){
            followerViewModel.setSelectedUserFollowersInfo().observe(viewLifecycleOwner){followerObserver ->
                if (followerObserver!=null){
                    false.followerAnimation()
                    followerAdapter.setFollowersListForAdapter(followerObserver)
                }
            }

        }
    }



    private fun Boolean.followerAnimation() {
        when(this){
            true -> followerBind?.progressBarFollowers?.visibility=View.VISIBLE
            false -> followerBind?.progressBarFollowers?.visibility=View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _followerBind=null
    }
}