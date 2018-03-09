package makes.flint.alt.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.injection.components.PresenterComponent
import org.jetbrains.anko.AnkoLogger


/**
 * BaseActivity
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    // Properties

    private var presenter: BaseContractPresenter<*>? = null

    // Overrides

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    // Protected Functions

    protected fun getBaseApplication(): BaseApplication = application as BaseApplication

    protected fun getPresenterComponent(): PresenterComponent = getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    // Internal Functions

    internal fun showToast(stringId: Int, toastLength: Int) {
        Toast.makeText(this, getString(stringId), toastLength).show()
    }

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun showError(stringId: Int?) {
        ErrorHandler.showError(this, stringId)
    }
}
