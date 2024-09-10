package com.example.login.inputOne.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.example.common.common.launchAndRepeatWithViewLifecycle
import com.example.common.common.onTextChanged
import com.example.common.common.subscribe
import com.example.login.R
import com.example.login.databinding.FragmentInputOneBinding
import com.example.login.inputOne.di.DaggerInputOneComponent
import com.example.navigation.Router
import com.example.navigation.Screens
import com.example.navigation.di.ResourceProviderProvider
import javax.inject.Inject
import javax.inject.Named

class FragmentInputOne : Fragment(R.layout.fragment_input_one) {
    private var _binding: FragmentInputOneBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: InputOneViewModel.Factory

    private val viewModel: InputOneViewModel by viewModels {
        InputOneViewModel.LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(handle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInputOneBinding.inflate(inflater, container, false)
        router.init(this@FragmentInputOne)
        initView()
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
        }
    }

    private fun showInputTwoScreen(screens: Screens) {
        router.navigateTo(screens)
    }

    private fun handleUiLabel(uiLabel: InputOneView.UiLabel): Unit =
        when (uiLabel) {
            is InputOneView.UiLabel.ShowInputTwoScreen -> showInputTwoScreen(uiLabel.screens)
        }

    private fun handleState(model: InputOneView.Model) {
        if (binding.etMain.text.toString() != model.textEdit) {
            binding.etMain.setText(model.textEdit)
            binding.etMain.setSelection(model.textEdit.length)
        }

        binding.bvMain.isEnabled = model.isBottomActive

        if (!model.showIconStart) {
            binding.textInputLayout.startIconDrawable = null
            binding.textInputLayout.error = null
        } else {
            binding.textInputLayout.setStartIconDrawable(com.example.common.R.drawable.icon_message)
        }

        if (model.wrongEmail) {
            binding.textInputLayout.error = getString(R.string.error_email)
            binding.textInputLayout.isErrorEnabled = true
            binding.textInputLayout.boxStrokeColor = resources.getColor(com.example.common.R.color.grey_2, null)
        } else {
            binding.textInputLayout.error = null
            binding.textInputLayout.isErrorEnabled = false
            binding.textInputLayout.boxStrokeColor = resources.getColor(com.example.common.R.color.grey_2, null)
        }
    }

    private fun initView() {
        binding.textInputLayout.setEndIconOnClickListener {
            viewModel.onEvent(InputOneView.Event.OnClickClearText)
        }

        binding.etMain.onTextChanged { text ->
            viewModel.onEvent(InputOneView.Event.OnTextChanged(text))
        }

        binding.bvMain.setOnClickListener {
            val bundle =
                Bundle().apply {
                    putString("email", viewModel.uiState.value.textEdit)
                }
            viewModel.onEvent(InputOneView.Event.OnClickNext(bundle))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        initDagger()
        super.onAttach(context)
    }

    override fun onStop() {
        super.onStop()
        router.clear()
    }

    private fun initDagger() {
        val resourceProvider = (requireActivity().application as ResourceProviderProvider).getResourceProvider()
        DaggerInputOneComponent.factory().create(resourceProvider).inject(this)
    }
}
