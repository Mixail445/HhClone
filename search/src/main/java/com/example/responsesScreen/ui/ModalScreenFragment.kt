package com.example.responsesScreen.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.example.detaliSearch.di.DaggerDetailComponent
import com.example.navigation.Router
import com.example.navigation.di.ResourceProviderProvider
import com.example.search.R
import com.example.search.databinding.DialogOneFragmentBinding
import com.example.search.di.SearchModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject
import javax.inject.Named

class ModalScreenFragment : BottomSheetDialogFragment(R.layout.dialog_one_fragment) {
    private var _binding: DialogOneFragmentBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: ModalScreenViewModel.Factory

    private val viewModel: ModalScreenViewModel by viewModels {
        ModalScreenViewModel.LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(handle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogOneFragmentBinding.inflate(inflater, container, false)
        initDagger()
        // initView()
//        initViewModel()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), com.example.common.R.style.ModalBottomSheetDialog)

//    private fun initViewModel() {
//        with(viewModel) {
//            subscribe(uiLabels, ::handleUiLabel)
//            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
//        }
//    }
    override fun onStart() {
        super.onStart()
        router.init(this)
    }

    override fun onStop() {
        super.onStop()
        router.clear()
    }

    private fun initDagger() {
        val resourceProvider = (requireActivity().application as ResourceProviderProvider).getResourceProvider()
        DaggerDetailComponent.factory().create(resourceProvider, SearchModel(requireActivity().application)).inject(this)
    }
}
