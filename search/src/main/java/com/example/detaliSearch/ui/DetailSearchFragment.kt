package com.example.detaliSearch.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.example.common.common.RecyclerViewItemDecoration
import com.example.common.common.launchAndRepeatWithViewLifecycle
import com.example.common.common.subscribe
import com.example.detaliSearch.di.DaggerDetailComponent
import com.example.navigation.Router
import com.example.navigation.di.ResourceProviderProvider
import com.example.search.R
import com.example.search.databinding.DetailSearchFragmentBinding
import com.example.search.di.SearchModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named

class DetailSearchFragment : Fragment(R.layout.detail_search_fragment) {
    private var _binding: DetailSearchFragmentBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: DetailSearchViewModel.Factory

    private val viewModel: DetailSearchViewModel by viewModels {
        DetailSearchViewModel.LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(arguments?.getString("id").toString(), handle)
        }
    }

    val adapterButton =
        DetailButtonAdapter(
            onItemClicked = { text ->
                showDialog(text)
            },
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailSearchFragmentBinding.inflate(inflater, container, false)
        initDagger()
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

    private fun handleUiLabel(uiLabel: DetailSearchView.UiLabel): Unit =
        when (uiLabel) {
            is DetailSearchView.UiLabel.ShowModelScreen -> router.navigateTo(screen = uiLabel.screens)
        }

    private fun handleState(model: DetailSearchView.Model) {
        binding.tvTitle.text = model.title
        binding.tvSalary.text = model.salary
        binding.tvExperience.text = model.experience
        binding.tvSchedules.text = model.schedules
        binding.bvTextResponses.text = model.appliedNumber
        binding.bvTextLoocking.text = model.lookingNumber
        binding.cvResponses.isVisible = model.cardResponses
        binding.cvLoocking.isVisible = model.cardLoocking
        binding.tvNameCompany.text = model.company
        binding.tvAddressHard.text = model.address
        binding.tvMoreText.text = Html.fromHtml(model.description, Html.FROM_HTML_MODE_LEGACY)
        binding.tvResponsivility.text = Html.fromHtml(model.responsibilities, Html.FROM_HTML_MODE_LEGACY)
        model.iconFavorite?.let { binding.icFavorites.setImageDrawable(it) }
        val buttonItems = model.questions.map { question -> ButtonUi(question, UUID.randomUUID().toString()) }
        adapterButton.items = buttonItems
    }

    private fun initView() {
        binding.icBack.setOnClickListener {
            router.back()
        }
        binding.rcButton.apply {
            adapter = adapterButton
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration(horizontal = false))
        }

        binding.bvResponsesDialog.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this.requireActivity())
            val view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_one_fragment, null)

            bottomSheet.setContentView(view)
            bottomSheet.show()

            val bottomResponse = view.findViewById<AppCompatButton>(R.id.bv_responses)
            bottomResponse.setOnClickListener {
                bottomSheet.dismiss()
            }
            val bottomAddMessage = view.findViewById<TextView>(R.id.textView17)
            bottomAddMessage.setOnClickListener {
                bottomSheet.dismiss()
                showDialog()
            }
        }
    }

    private fun showDialog(text: String? = "") {
        val dialog = Dialog(this.requireActivity())
        dialog.setContentView(R.layout.dialog_search_two_fragment)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        val button = dialog.findViewById<AppCompatButton>(R.id.response_two_dialog)
        val etHelper = dialog.findViewById<EditText>(R.id.et_cover_letter)
        etHelper.setText(text)
        button?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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
