package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.dynamicgithubuserapp.views.FollowersFragment
import xyz.heydarrn.dynamicgithubuserapp.views.FollowingFragment

class TabSectionAdapter(activity:AppCompatActivity) : FragmentStateAdapter(activity) {
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
        return fragment as Fragment
    }



}