package com.example.android.codelabs.paging.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.android.codelabs.paging.model.character.CharacterDto
import com.example.android.codelabs.paging.model.character.CharacterEntity

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator{
    return requireActivity() as Navigator
}
interface Navigator {

    fun showSplashScreen()

    fun showCharactersScreen()

    fun showCharactersScreenDetail(character: CharacterEntity)

    fun showLocationsScreen()

    fun showEpisodesScreen()

    fun showAboutScreen()

    fun goBack()

    fun goToCharacters()

    fun <T: Parcelable> publishResult(result: T)

    fun <T : Parcelable>  listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}