package com.volo.rovervehicle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volo.rovervehicle.adapter.PhotoAdapter
import com.volo.rovervehicle.data.model.VehicleType
import com.volo.rovervehicle.viewmodel.PhotoViewModel
import com.volo.voloandroidtask.R
import com.volo.voloandroidtask.databinding.FragmentPhotosBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PhotoFragment(private val vehicleType: VehicleType = VehicleType.CURIOSITY) : Fragment() {

    private var _binding: FragmentPhotosBinding? = null

    private val binding get() = _binding!!

    private val photoViewModel: PhotoViewModel by viewModels()

    private val adapter by lazy {
        PhotoAdapter {
            PhotoDetailDialogFragment(it).show(childFragmentManager, PhotoDetailDialogFragment.TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = photoViewModel
        binding.recyclerViewPhoto.adapter = adapter

        // Filters observer
        photoViewModel.filter.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    photoViewModel.getPhotos(vehicleType.name, photoViewModel.filter.value)
                        .collectLatest { pagingData ->
                            adapter.submitData(pagingData)
                        }
                }
            }
        }

        binding.filter.setOnClickListener {
            showPopupWindow(it)
        }
    }

    // Filter popup window
    private fun showPopupWindow(filterView: View) {
        val popupView = layoutInflater.inflate(R.layout.menu_layout, null)
        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )
        // setup view
        val recyclerView = popupView.findViewById<RecyclerView>(R.id.recycler_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val popAdapter = PopupWindowAdapter(
            popupWindow
        )
        recyclerView.adapter =
            popAdapter

        popupWindow.elevation = 10f


        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                // Get List of cameras
                val cameraList = photoViewModel.getCameraList(vehicleType.name).toMutableList()

                cameraList.add(0, getString(R.string.all))

                popAdapter.submitList(cameraList.toList())
                popupWindow.showAsDropDown(filterView)
            }
        }
    }


    private inner class PopupWindowAdapter(
        val popupWindow: PopupWindow,
    ) : RecyclerView.Adapter<PopupWindowAdapter.ViewHolder>() {
        // Define your data source
        private var itemList = mutableListOf<String>()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val itemView =
                layoutInflater.inflate(R.layout.item_text_view, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = itemList[position]
            holder.textView.text =
                item
            holder.textView.setOnClickListener {
                photoViewModel.filter.value =
                    if (item == getString(R.string.all)) null else item
                popupWindow.dismiss()
            }
            photoViewModel.filter.value?.let {
                holder.textView.setBackgroundColor(
                    requireActivity().getColor(if (it == item) R.color.purple_200 else android.R.color.transparent)
                )
            }

        }

        fun submitList(list: List<String>) {
            itemList = list.toMutableList()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView as TextView
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}