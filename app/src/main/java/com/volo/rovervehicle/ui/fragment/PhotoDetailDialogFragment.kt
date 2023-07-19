package com.volo.rovervehicle.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.volo.rovervehicle.data.model.Photo
import com.volo.voloandroidtask.databinding.FragmentDetailDialogBinding

class PhotoDetailDialogFragment(private val photo: Photo) : DialogFragment() {
    private lateinit var binding: FragmentDetailDialogBinding


    companion object {
        const val TAG = "PhotoDetailDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.photo = photo

        binding.ivClose.setOnClickListener {
            dialog?.dismiss()
        }
    }
}