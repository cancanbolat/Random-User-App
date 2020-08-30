package com.example.kotlinretrofit.UI.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinretrofit.Model.Results
import com.example.kotlinretrofit.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.items.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportStartPostponedEnterTransition()

        val results: Results =
            intent.extras?.getSerializable(MainActivity.EXTRA_RESULT_ITEM) as Results

        username_detail.text = "${results.name.first} ${results.name.last}"
        email_detail.text = results.email
        Log.d("DetailActivity",results.email)
        address_detail.text = "${results.location.city} / ${results.location.state}"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionName: String? =
                intent.extras!!.getString(MainActivity.EXTRA_RESULT_TRANSITION_NAME)
            circleImageView_detail.transitionName = transitionName
        }

        Picasso.get()
            .load(results.picture.large)
            .into(circleImageView_detail, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })

    }
}