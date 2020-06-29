package com.litrud.dogsgallery.network.monitoring

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.snackbar.Snackbar
import com.litrud.dogsgallery.R

class NetworkEventsSnackbar(
    private val activity: FragmentActivity
) {

    private val networkSnack =
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            activity.getString(R.string.msg_internet_unavailable),
            Snackbar.LENGTH_INDEFINITE
        )

    fun subscribe(swipeBehavior: Boolean) {
        if (!swipeBehavior) {
            // disable swipe behaviour
            val snackBarLayout = networkSnack.view
            snackBarLayout.viewTreeObserver.addOnGlobalLayoutListener {
                val lp = snackBarLayout.layoutParams
                if (lp is CoordinatorLayout.LayoutParams) {
                    lp.behavior = DisableSwipeBehavior
                    snackBarLayout.layoutParams = lp
                }
            }
        }
        NetworkEvents.observe(activity, Observer {
            when (it) {
                is Event.ConnectivityAvailable -> {
                    if (networkSnack.isShown) networkSnack.dismiss()
                }
                is Event.ConnectivityLost -> {
                    networkSnack.show()
                }
            }
        })
    }

    internal object DisableSwipeBehavior : SwipeDismissBehavior<Snackbar.SnackbarLayout>() {
        override fun canSwipeDismissView(view: View) : Boolean {
            return false
        }
    }
}