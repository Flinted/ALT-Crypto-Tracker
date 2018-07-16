package did.chris.alt.base

import android.content.Context
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.injection.components.PresenterComponent

open class BaseFragment : Fragment() {

    // Properties

    internal var presenter: BaseContractPresenter<*>? = null

    // Overrides

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    // Protected Functions

    protected fun getBaseApplication(): BaseApplication = activity?.application as BaseApplication

    protected fun getPresenterComponent(): PresenterComponent =
        getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    protected fun hideKeyboard(windowToken: IBinder) {
        val imm =
            (activity as FragmentActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    protected fun showKeyboard() {
        val imm =
            (activity as FragmentActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    // Internal Functions

    internal fun showToast(stringId: Int, toastLength: Int) {
        Toast.makeText(context, getString(stringId), toastLength).show()
    }

    // Open Functions

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }
}