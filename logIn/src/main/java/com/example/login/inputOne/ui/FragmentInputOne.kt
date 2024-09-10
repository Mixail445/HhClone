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
        router.init(this)
        initView()
        initViewModel()
        return binding.root
    }

    private fun initViewModel() =
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
        }

    private fun handleUiLabel(uiLabel: InputOneView.UiLabel) {
        when (uiLabel) {
            is InputOneView.UiLabel.ShowInputTwoScreen -> router.navigateTo(uiLabel.screens)
        }
    }

    private fun handleState(model: InputOneView.Model) =
        with(binding) {
            if (etMain.text.toString() != model.textEdit) {
                etMain.setText(model.textEdit)
                etMain.setSelection(model.textEdit.length)
            }

            bvMain.isEnabled = model.isBottomActive

            textInputLayout.apply {
                if (!model.showIconStart) {
                    startIconDrawable = null
                    error = null
                } else {
                    setStartIconDrawable(com.example.common.R.drawable.icon_message)
                }

                if (model.wrongEmail) {
                    error = getString(R.string.error_email)
                    isErrorEnabled = true
                    boxStrokeColor = resources.getColor(com.example.common.R.color.grey_2, null)
                } else {
                    error = null
                    isErrorEnabled = false
                    boxStrokeColor = resources.getColor(com.example.common.R.color.grey_2, null)
                }
            }
        }

    private fun initView() =
        with(binding) {
            textInputLayout.setEndIconOnClickListener {
                viewModel.onEvent(InputOneView.Event.OnClickClearText)
            }

            etMain.onTextChanged { text ->
                viewModel.onEvent(InputOneView.Event.OnTextChanged(text))
            }

            bvMain.setOnClickListener {
                val bundle =
                    Bundle().apply {
                        putString("email", viewModel.uiState.value.textEdit)
                    }
                viewModel.onEvent(InputOneView.Event.OnClickNext(bundle))
            }
        }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        initDagger()
        super.onAttach(context)
    }

    override fun onStop() {
        router.clear()
        super.onStop()
    }

    private fun initDagger() {
        val resourceProvider = (requireActivity().application as ResourceProviderProvider).getResourceProvider()
        DaggerInputOneComponent.factory().create(resourceProvider).inject(this)
    }
}
