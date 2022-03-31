package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.dynamicgithubuserapp.views.FollowersFragment
import xyz.heydarrn.dynamicgithubuserapp.views.FollowingFragment

class TabSectionAdapter(activity:AppCompatActivity,data :Bundle) : FragmentStateAdapter(activity) {
    private var fragmentBundle:Bundle=data

    override fun getItemCount(): Int {
        /*specify how many tabs inside tab layout.
        since we need followers tab and following tab,
        we return 2 (tabs)*/
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> fragment=FollowersFragment()
            1 -> fragment=FollowingFragment()
        }
        fragment?.arguments=this.fragmentBundle
        return fragment as Fragment
    }



}