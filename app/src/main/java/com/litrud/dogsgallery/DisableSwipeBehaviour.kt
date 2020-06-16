package com.litrud.dogsgallery

import android.view.View
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.snackbar.Snackbar

class DisableSwipeBehavior: SwipeDismissBehavior<Snackbar.SnackbarLayout>() {
    override fun canSwipeDismissView(view: View) : Boolean {
        return false
    }
}