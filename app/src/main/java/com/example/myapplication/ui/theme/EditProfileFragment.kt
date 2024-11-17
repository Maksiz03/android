package com.example.myapplication.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        val editTextFullName: EditText = view.findViewById(R.id.editTextFullName)
        val editTextPosition: EditText = view.findViewById(R.id.editTextPosition)
        val buttonSave: Button = view.findViewById(R.id.buttonSave)

        // Set initial values from the ViewModel
        editTextFullName.setText(viewModel.fullName)
        editTextPosition.setText(viewModel.position)

        buttonSave.setOnClickListener {
            // Update ViewModel with new values
            viewModel.fullName = editTextFullName.text.toString()
            viewModel.position = editTextPosition.text.toString()

            // Optionally, navigate back or show a confirmation message
            // For example, using Navigation component:
            // findNavController().popBackStack()
        }

        return view
    }
}
