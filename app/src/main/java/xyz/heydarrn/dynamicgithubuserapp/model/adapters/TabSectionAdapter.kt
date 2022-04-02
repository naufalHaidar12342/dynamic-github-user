package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.dynamicgithubuserapp.views.FollowerListFragment
import xyz.heydarrn.dynamicgithubuserapp.views.FollowingListFragment

class TabSectionAdapter(activity:AppCompatActivity, data:Bundle) : FragmentStateAdapter(activity) {
    //initialize fragmentBundle value from parameter
    private var fragmentBundle:Bundle = data

    override fun getItemCount(): Int {
        /*specify how many tabs inside tab layout.
        since we need followers tab and following tab,
        we return 2 (tabs)*/
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment?=null
        when(position){
            0 -> fragment = FollowerListFragment()
            1 -> fragment = FollowingListFragment()
        }
        /*when one of tab selected, it will send argument, containing the bundle here,
        * to the fragment associated with the selected tabs*/
        fragment?.arguments=this.fragmentBundle
        return fragment as Fragment
    }


}