package com.example.app.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentProfileBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.profiles.ProfileViewModel

class ProfileFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_profile
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        setupViewModel()
        loadProfile()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(ServiceRepositoryImpl()) as T
            }
        })[ProfileViewModel::class.java]
    }

    private fun loadProfile() {
        viewModel.loadProfile()
        observeProfile()
    }

    private fun observeProfile() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.apply {
                    tvUserName.text = it.name
                    tvUserEmail.text = it.email
                    tvUserPhone.text = it.phone
                    tvUserLocation.text = it.location

                    Glide.with(this@ProfileFragment)
                        .load(it.profileImageUrl)
                        .circleCrop()
                        .into(ivUserProfile)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_editProfile)
        }

        binding.btnMyBookings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_myBookings)
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_settings)
        }

        binding.btnLogout.setOnClickListener {
            // Implémenter la déconnexion
        }
    }
}
