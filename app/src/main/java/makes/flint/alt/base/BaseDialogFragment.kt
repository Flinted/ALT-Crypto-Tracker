package makes.flint.alt.base

import android.app.DialogFragment
import makes.flint.alt.R
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.injection.components.PresenterComponent

/**
 * BaseDialogFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
open class BaseDialogFragment : DialogFragment() {

    override fun getTheme(): Int {
        return R.style.AppTheme_NoActionBar_FullScreenDialog;
    }

    internal var presenter: BaseContractPresenter<*>? = null

    protected fun getBaseApplication(): BaseApplication = activity.application as BaseApplication

    protected fun getPresenterComponent(): PresenterComponent = getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    fun onError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }
}
