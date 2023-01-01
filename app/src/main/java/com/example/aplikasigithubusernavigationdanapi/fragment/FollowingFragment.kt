package com.example.aplikasigithubusernavigationdanapi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigithubusernavigationdanapi.retrofit.ApiConfig
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.adapter.FollowingAdapter
import com.example.aplikasigithubusernavigationdanapi.data.Following
import com.example.aplikasigithubusernavigationdanapi.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment(private val username: String) : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApiFollowing()
    }

    private fun getApiFollowing() {
        binding.progressBarFollowing.visibility = View.VISIBLE
        val following = ApiConfig.getApiFollowing().getFollowing(username)
        following.enqueue(object : Callback<List<Following>> {
            override fun onResponse(
                call: Call<List<Following>>,
                response: Response<List<Following>>
            ) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val data = response.body()
                if (data != null) {
                    val recyclerView: RecyclerView = requireView().findViewById(R.id.rv_following)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = FollowingAdapter(data)
                }
            }

            override fun onFailure(call: Call<List<Following>>, t: Throwable) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                Log.d("Following", "Error")
            }

        })
    }

}