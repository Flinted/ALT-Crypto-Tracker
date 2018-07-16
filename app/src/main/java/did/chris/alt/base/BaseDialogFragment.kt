package did.chris.alt.base

import android.app.DialogFragment
import android.content.Context
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import did.chris.alt.R
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.injection.components.PresenterComponent

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

    protected fun getPresenterComponent(): PresenterComponent =
        getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    protected fun showKeyboard() {
        val imm =
            (activity as FragmentActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    protected fun hideKeyboard(windowToken: IBinder) {
        val imm =
            (activity as FragmentActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
