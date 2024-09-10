package com.example.login.inputTwo

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.example.common.common.launchAndRepeatWithViewLifecycle
import com.example.common.common.subscribe
import com.example.login.R
import com.example.login.databinding.FragmentInputTwoBinding
import com.example.login.inputOne.di.DaggerInputOneComponent
import com.example.navigation.Router
import com.example.navigation.di.ResourceProviderProvider
import javax.inject.Inject
import javax.inject.Named

class FragmentInputTwo : Fragment(R.layout.fragment_input_two) {
    private var _binding: FragmentInputTwoBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: InputTwoViewModel.Factory

    private val viewModel: InputTwoViewModel by viewModels {
        InputTwoViewModel.LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(
                handle,
                arguments?.getString("email") ?: "",
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInputTwoBinding.inflate(inflater, container, false)
        initView()
        initViewModel()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    override fun onStart() {
        super.onStart()
        router.init(this)
    }

    override fun onStop() {
        super.onStop()
        router.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() =
        with(binding) {
            bvResponses.setOnClickListener {
                viewModel.onEvent(InputTwoView.Event.OnClickNext)
            }
            setupOtpInputs()
        }

    private fun initViewModel() =
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
        }

    private fun handleUiLabel(uiLabel: InputTwoView.UiLabel) {
        when (uiLabel) {
            is InputTwoView.UiLabel.ShowBottomFragment -> router.navigateTo(uiLabel.screens)
        }
    }

    private fun handleState(model: InputTwoView.Model) =
        with(binding) {
            textView2.text = model.email
            bvResponses.isEnabled = model.bottomActive
        }

    private fun setupOtpInputs() {
        val otpFields = arrayOf(binding.etOne, binding.edTwo, binding.etThree, binding.etFour)

        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {}

                    override fun afterTextChanged(s: Editable?) {
                        val text = s?.toString() ?: ""
                        viewModel.onOtpDigitChanged(index, text)

                        if (text.length == 1 && index < otpFields.size - 1) {
                            otpFields[index + 1].requestFocus()
                        }
                    }
                },
            )

            editText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    if (editText.text.isEmpty() && index > 0) {
                        otpFields[index - 1].requestFocus()
                    }
                }
                false
            }
        }

        otpFields.first().requestFocus()
    }

    private fun initDagger() {
        val resourceProvider = (requireActivity().application as ResourceProviderProvider).getResourceProvider()
        DaggerInputOneComponent.factory().create(resourceProvider).inject(this)
    }
}
