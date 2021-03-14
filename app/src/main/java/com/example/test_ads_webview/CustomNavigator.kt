package com.example.test_ads_webview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object CustomNavigator {

    private var fragmentManager: FragmentManager? = null

    fun bind(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
        if (!homeInitialized()) navigateTo(SomeHomeFragment(), "home",true)
    }

    fun navigateTo(fragment: Fragment, tag: String, isHome: Boolean = false) {
        val transaction = fragmentManager?.beginTransaction() ?: return
        transaction.replace(R.id.fragment_container, fragment, tag)
        if (!isHome) transaction.addToBackStack(tag)
        transaction.commitAllowingStateLoss()
        fragmentManager?.executePendingTransactions()
    }

    fun unbind() {
        fragmentManager = null
    }

    fun homeInitialized() = fragmentManager?.findFragmentByTag("home") != null

}
