package com.volo.rovervehicle.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volo.rovervehicle.data.model.VehicleType
import com.volo.rovervehicle.ui.fragment.PhotoFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return VehicleType.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment(VehicleType.values()[position])
    }
}