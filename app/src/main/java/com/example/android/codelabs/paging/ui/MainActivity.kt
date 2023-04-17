package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.contract.HasCustomTitle
import com.example.android.codelabs.paging.contract.Navigator
import com.example.android.codelabs.paging.contract.ResultListener
import com.example.android.codelabs.paging.databinding.ActivityMainBinding
import com.example.android.codelabs.paging.model.character.CharacterDto
import com.example.android.codelabs.paging.ui.character.CharactersFragment

private const val LAST_SELECTED_ITEM = "item"
private const val KEY_RESULT = "KEY_RESULT"
private var received = 0

class MainActivity: AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setSupportActionBar(findViewById(R.id.toolbar))

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    val charactersFragment = CharactersFragment()
                    replaceFragment(charactersFragment)
                }
                R.id.locations -> {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, CharactersFragment())
                        .addToBackStack("")
                        .replace(R.id.fragment_container, LocationsFragment())
                        .commit()
                }
                R.id.episodes -> {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, CharactersFragment())
                        .addToBackStack("")
                        .replace(R.id.fragment_container, EpisodesFragment())
                        .commit()
                }
            }
            true
        }

        if (savedInstanceState == null) {
            binding.bottomNavigationView.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SplashFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun moveToNext() {
        supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharactersFragment())
            .commit()
        binding.bottomNavigationView.selectedItemId = R.id.characters
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("TAG", "onSaveInstanceState Called")
        outState.putInt(LAST_SELECTED_ITEM, received)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onBackPressed() {
        back()
    }

    override fun onSupportNavigateUp(): Boolean {
        back()
        return true
    }
    private fun back(){
        onBackPressedDispatcher.onBackPressed()
        if (currentFragment.toString().take(12) == "CharactersFr") {
            binding.bottomNavigationView.selectedItemId = R.id.characters
        }
    }

    private fun updateUi() {
        val fragment = currentFragment

        if (fragment is HasCustomTitle) {
            binding.toolbar.title = getString(fragment.getTitleRes())
        } else {
            binding.toolbar.title = getString(R.string.app_name)
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }

        if (fragment.toString().take(6) == "Splash") {
            binding.bottomNavigationView.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
        } else {
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    override fun showSplashScreen() {
        launchFragment(SplashFragment())
    }

    override fun showCharactersScreen() {}

    override fun showCharactersScreenDetail(character: CharacterDto) {}

    override fun showLocationsScreen() {}

    override fun showEpisodesScreen() {}

    override fun goBack() {
        back()
    }

    override fun goToCharacters() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(
            result.javaClass.name,
            bundleOf(KEY_RESULT to result)
        )
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(
            clazz.name,
            owner,
            FragmentResultListener { key, bundle ->
                listener.invoke(bundle.getParcelable(KEY_RESULT)!!)
            })
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}