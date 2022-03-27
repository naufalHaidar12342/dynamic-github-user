package xyz.heydarrn.dynamicgithubuserapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import xyz.heydarrn.dynamicgithubuserapp.R
import xyz.heydarrn.dynamicgithubuserapp.databinding.ActivityDetailOfUserBinding

class DetailOfUserActivity : AppCompatActivity() {
    private lateinit var userDetailBind:ActivityDetailOfUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_of_user)

        userDetailBind= ActivityDetailOfUserBinding.inflate(layoutInflater)
        setContentView(userDetailBind.root)

    }

    companion object{
        const val EXTRA_USERNAME="username of github user goes here"
    }
}