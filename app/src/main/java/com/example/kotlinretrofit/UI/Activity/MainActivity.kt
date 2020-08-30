package com.example.kotlinretrofit.UI.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinretrofit.Data.ApiClient
import com.example.kotlinretrofit.Data.ApiService
import com.example.kotlinretrofit.Model.UserResponse
import com.example.kotlinretrofit.R
import com.example.kotlinretrofit.UI.Adapter.UserAdapter
import com.example.kotlinretrofit.gone
import com.example.kotlinretrofit.visible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiClient: ApiService by lazy { ApiClient.getApiClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        progressBar.visible()
        recycler_view.gone()
        getUsers()

        swiperefresh.setOnRefreshListener { getUsers() }

        TODO("Double click back to exit app")
        TODO("Clear app cache button")
        TODO("Use LottieAnimationView")
        TODO("Use Slidableactivity")
        TODO("Use SliderAdapter & PagerAdapter -> SplashScreen")
        TODO("Internet control")

    }

    private fun getUsers() {
        apiClient.getUsers(10).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("MainActivity", t.message)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                //System.out.println("gelen => "+response.body()?.userList?.size.toString())
                if (response.isSuccessful) {
                    recycler_view.adapter = UserAdapter(response.body()?.userList!!)
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

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return return true
    }

}