package xyz.heydarrn.dynamicgithubuserapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.dynamicgithubuserapp.databinding.FragmentFollowersBinding
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.FollowersAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.GithubUserViewModel

class FollowersFragment : Fragment() {
    private var _bindingFollowers:FragmentFollowersBinding? = null
    private val bindingFollowers =_bindingFollowers
    private val githubUserViewModel by viewModels<GithubUserViewModel>()
    private lateinit var userFollowersAdapter :FollowersAdapter
    private lateinit var usernameReceived:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingFollowers= FragmentFollowersBinding.inflate(inflater,container,false)
        return bindingFollowers?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        _bindingFollowers= FragmentFollowersBinding.bind(view)

        observeDataFollowers()
        setFollowersRecViewAdapter()
    }

    private fun setFollowersRecViewAdapter(){
        userFollowersAdapter= FollowersAdapter()
        userFollowersAdapter.notifyDataSetChanged()

        bindingFollowers?.apply {
            recyclerviewFollowers.setHasFixedSize(true)
            recyclerviewFollowers.layoutManager=LinearLayoutManager(context)
            recyclerviewFollowers.adapter=userFollowersAdapter
        }
    }

    private fun observeDataFollowers(){
        val argumentFollowers=arguments
        usernameReceived=argumentFollowers?.getString(USER_FOLLOWERS).toString()

        githubUserViewModel.apply {
            this.setUserFollowersInfo(usernameReceived)
            this.setSelectedUserFollowersInfo().observe(viewLifecycleOwner){ observeFollowers ->
                if (observeFollowers!=null){
                    userFollowersAdapter.setFollowersListForAdapter(observeFollowers)

                }
            }
        }


    }
    

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFollowers=null
    }

    companion object {
        const val USER_FOLLOWERS="follower"
    }
}