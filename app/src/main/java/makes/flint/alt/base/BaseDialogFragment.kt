package makes.flint.alt.base

import android.app.DialogFragment
import makes.flint.alt.R
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.injection.components.PresenterComponent

/**
 * BaseDialogFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
open class BaseDialogFragment : DialogFragment() {

    // Properties

    internal var presenter: BaseContractPresenter<*>? = null

    // Overrides

    override fun getTheme(): Int {
        return R.style.AppTheme_NoActionBar_FullScreenDialog;
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    // Internal Functions

    internal fun onError(stringId: Int?) = ErrorHandler.showError(activity, stringId)

    // Protected Functions

    protected fun getBaseApplication(): BaseApplication = activity.application as BaseApplication

    protected fun getPresenterComponent(): PresenterComponent = getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }
}
