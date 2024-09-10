package com.example.search.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.example.common.common.RecyclerViewItemDecoration
import com.example.common.common.launchAndRepeatWithViewLifecycle
import com.example.common.common.subscribe
import com.example.navigation.Router
import com.example.navigation.di.ResourceProviderProvider
import com.example.search.R
import com.example.search.databinding.SearchFragmentBinding
import com.example.search.di.DaggerSearchComponent
import com.example.search.di.SearchModel
import javax.inject.Inject
import javax.inject.Named

class SearchFragment : Fragment(R.layout.search_fragment) {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: SearchViewModel.Factory

    private val adapterMain =
        SearchScreenAdapter(
            onFavoriteClick = { id ->
                viewModel.toggleFavorite(id)
            },
            onItemClicked = { id ->
                val bundle = Bundle().apply { putString("id", id) }
                viewModel.onEvent(SearchView.Event.OnClickMainRc(bundle))
            },
        )

    private val adapterChild =
        OffersAdapter(
            onCardClick = { id ->
                viewModel.onEvent(SearchView.Event.OnClickChildRc(id))
            },
        )

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(handle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        initDagger()
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() {
        binding.rcMain.apply {
            adapter = adapterMain
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration(horizontal = false))
        }
        binding.rcChild.apply {
            adapter = adapterChild
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration(vertical = false))
        }
        binding.button.setOnClickListener {
            viewModel.onEvent(SearchView.Event.OnClickAllRcItem)
        }
        binding.textInputEditText.setStartIconOnClickListener {
            viewModel.onEvent(SearchView.Event.OnClickIcTextEdit)
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
        }
    }

    private fun handleUiLabel(uiLabel: SearchView.UiLabel): Unit =
        when (uiLabel) {
            is SearchView.UiLabel.ShowDetailScreen -> router.navigateTo(uiLabel.screens, uiLabel.id)
            is SearchView.UiLabel.ShowBrowse -> showBrowser(uiLabel.url)
        }

    private fun handleState(model: SearchView.Model) {
        adapterMain.items = model.itemMainRc
        adapterChild.items = model.itemChildRc
        binding.button.isVisible = !model.modeAllRcItem
        binding.rcChild.isVisible = !model.modeAllRcItem
        binding.textView.isVisible = !model.modeAllRcItem
        binding.linearOption.isVisible = model.modeAllRcItem
        binding.textView20.text = model.countVacancy
        router.setBadge(1, model.countFavorite, this)
        if (model.modeAllRcItem) {
            binding.textInputEditText.startIconDrawable =
                ContextCompat.getDrawable(
                    this.requireActivity(),
                    com.example.common.R.drawable.icon_left_arrow,
                )
        } else {
            binding.textInputEditText.startIconDrawable =
                ContextCompat.getDrawable(
                    this.requireActivity(),
                    com.example.common.R.drawable.icon_search_one,
                )
        }
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

    private fun showBrowser(uri: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    }

    private fun initDagger() {
        val resourceProvider = (requireActivity().application as ResourceProviderProvider).getResourceProvider()
        DaggerSearchComponent.factory().create(resourceProvider, SearchModel(requireActivity().application)).inject(this)
    }
}
