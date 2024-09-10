package com.example.responsesScreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.search.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalScreenTwoFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_search_two_fragment, container, false)
}
