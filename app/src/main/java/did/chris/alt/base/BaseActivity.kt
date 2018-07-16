package did.chris.alt.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.injection.components.PresenterComponent
import org.jetbrains.anko.AnkoLogger

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

    protected fun getPresenterComponent(): PresenterComponent =
        getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    // Internal Functions

    internal fun showToast(stringId: Int, toastLength: Int) {
        Toast.makeText(this, getString(stringId), toastLength).show()
    }

    // Open Functions

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun showError(stringId: Int?) {
        ErrorHandler.showError(this, stringId)
    }
}
