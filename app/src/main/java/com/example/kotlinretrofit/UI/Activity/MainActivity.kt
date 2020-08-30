package com.example.kotlinretrofit.UI.Activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinretrofit.Data.ApiClient
import com.example.kotlinretrofit.Data.ApiService
import com.example.kotlinretrofit.Model.Results
import com.example.kotlinretrofit.Model.UserResponse
import com.example.kotlinretrofit.R
import com.example.kotlinretrofit.UI.Adapter.UserAdapter
import com.example.kotlinretrofit.gone
import com.example.kotlinretrofit.visible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private val apiClient: ApiService by lazy { ApiClient.getApiClient() }
    private lateinit var adapter: UserAdapter

    companion object {
        const val EXTRA_RESULT_ITEM = "extra_result_item"
        const val EXTRA_RESULT_TRANSITION_NAME = "extra_transition_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        progressBar.visible()
        recycler_view.gone()
        getUsers()

        swiperefresh.setOnRefreshListener { getUsers() }

        // TODO("Double click back to exit app")
        // TODO("Clear app cache button")
        // TODO("Use LottieAnimationView")
        // TODO("Use Slidableactivity")
        // TODO("Use SliderAdapter & PagerAdapter -> SplashScreen")
        // TODO("Internet control")

    }

    private fun getUsers() {
        apiClient.getUsers(10).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("MainActivity", t.message)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                //System.out.println("gelen => "+response.body()?.userList?.size.toString())
                if (response.isSuccessful) {
                    adapter = UserAdapter(response.body()?.userList!!, this@MainActivity)
                    recycler_view.adapter = adapter
                    progressBar.gone()
                    recycler_view.visible()
                    swiperefresh.isRefreshing = false
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }

        })

        return return true
    }

    override fun onUserClickListener(results: Results, sharedImageView: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_RESULT_ITEM, results)
        intent.putExtra(EXTRA_RESULT_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView))

        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

}