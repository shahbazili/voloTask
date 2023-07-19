package com.volo.rovervehicle.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.volo.rovervehicle.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.volo.rovervehicle.data.model.VehicleType
import com.volo.voloandroidtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.pager.adapter = pagerAdapter

        // setup tabs with viewpager
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, pos ->
            tab.text = VehicleType.values()[pos].typeName
            pagerAdapter.createFragment(VehicleType.values()[pos].ordinal)
        }.attach()
    }


}