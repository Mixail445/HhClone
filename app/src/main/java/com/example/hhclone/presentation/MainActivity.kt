package com.example.hhclone.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.favorites.di.FavoriteModule
import com.example.hhclone.databinding.ActivityMainBinding
import com.example.hhclone.di.DaggerAppComponent
import com.example.navigation.Router
import com.example.search.di.SearchModel
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Named("Host")
    @Inject
    lateinit var router: Router

    private fun initDagger() {
        DaggerAppComponent.factory().create(application, SearchModel(this), FavoriteModule(this)).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        router.init(
            fragment = null,
            fragmentManager = supportFragmentManager,
            tabElementView = binding.navBar,
        )
    }
}
