package com.example.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.example.common.common.RecyclerViewItemDecoration
import com.example.common.common.launchAndRepeatWithViewLifecycle
import com.example.common.common.subscribe
import com.example.favorites.R
import com.example.favorites.databinding.FragmentFavoriteBinding
import com.example.favorites.di.DaggerFavoriteComponent
import com.example.favorites.di.FavoriteModule
import com.example.navigation.Router
import com.example.navigation.di.ResourceProviderProvider
import javax.inject.Inject
import javax.inject.Named

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var factory: FavoriteViewModel.Factory

    private val adapterMain = FavoriteAdapter()

    private val viewModel: FavoriteViewModel by viewModels {
        FavoriteViewModel.LambdaFactory(this) { handle: SavedStateHandle ->
            factory.build(handle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        initDagger()
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() {
        binding.rcFavorite.apply {
            adapter = adapterMain
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration(horizontal = false))
        }
    }

    private fun handleUiLabel(uiLabel: FavoriteView.UiLabel): Unit =
        when (uiLabel) {
            else -> {}
        }

    private fun handleState(model: FavoriteView.Model) {
        adapterMain.items = model.itemRc
        binding.childTitleFavorite.text = model.text
    }

    private fun initViewModel() {
        with(viewModel) {
            subscribe(uiLabels, ::handleUiLabel)
            launchAndRepeatWithViewLifecycle { uiState.collect(::handleState) }
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

    private fun initDagger() {
        val resourceProvider = (requireActivity().application as ResourceProviderProvider).getResourceProvider()
        DaggerFavoriteComponent.factory().create(resourceProvider, FavoriteModule(requireActivity().application)).inject(this)
    }
}
