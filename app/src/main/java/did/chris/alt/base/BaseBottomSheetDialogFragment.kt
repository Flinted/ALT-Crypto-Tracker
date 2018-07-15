package did.chris.alt.base

import android.content.Context
import android.os.IBinder
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import did.chris.alt.injection.components.PresenterComponent

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    internal var presenter: BaseContractPresenter<*>? = null

    // Protected Functions

    protected fun getBaseApplication(): BaseApplication = activity?.application as BaseApplication

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
