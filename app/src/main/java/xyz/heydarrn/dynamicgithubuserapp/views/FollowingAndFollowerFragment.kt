package xyz.heydarrn.dynamicgithubuserapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.dynamicgithubuserapp.databinding.FragmentFollowersAndFollowingBinding
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.FollowersAdapter
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.FollowingAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.FollowersViewModel

class FollowingAndFollowerFragment : Fragment() {
    private var _bindingFollowers:FragmentFollowersAndFollowingBinding? = null
    private val bindingFollowers get() = _bindingFollowers
    private val githubUserViewModel by viewModels<FollowersViewModel>()
    private lateinit var userFollowersAdapter :FollowersAdapter
    private lateinit var usernameReceived:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingFollowers= FragmentFollowersAndFollowingBinding.inflate(inflater,container,false)
        return bindingFollowers?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(TAB_INDEX,0)
        observeDataFollowers()
        setFollowersRecViewAdapter()
    }

    private fun setFollowersRecViewAdapter(){
        userFollowersAdapter= FollowersAdapter()
        userFollowersAdapter.notifyDataSetChanged()

        bindingFollowers?.recyclerviewFollowersAndFollowing?.apply {
            this.setHasFixedSize(true)
            this.layoutManager=LinearLayoutManager(context)
            this.adapter=userFollowersAdapter
        }
    }

    private fun observeDataFollowers(){
        val argumentFollowers=arguments
        usernameReceived=argumentFollowers?.getString(USER_FOLLOWERS).toString()

        githubUserViewModel.setUserFollowersInfo(usernameReceived)
        githubUserViewModel.setSelectedUserFollowersInfo().observe(viewLifecycleOwner){
            if (it!=null){
                userFollowersAdapter.setFollowersListForAdapter(it)
            }
        }
    }

    fun setFollowingRecViewAdapter(){
        val followingAdapter:FollowingAdapter by lazy {
            FollowingAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFollowers=null
    }

    companion object {
        const val USER_FOLLOWERS="follower"
        const val TAB_INDEX="index of each tab"
    }
}