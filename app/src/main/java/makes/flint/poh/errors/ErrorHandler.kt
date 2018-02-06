package makes.flint.poh.errors

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import makes.flint.poh.R

/**
 * ErrorHandler
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
object ErrorHandler {

    val API_FAILURE = R.string.error_api_failure
    var GENERAL_ERROR = R.string.error_general_error
    var ADD_TRANSACTION_FAILURE = R.string.error_transaction_failure
    var ERROR_SYNC_TIMEOUT = R.string.error_sync_timeout

    fun showError(context: Context, id: Int?) {
        val errorId = id ?: GENERAL_ERROR
        val errorString = context.getString(errorId)
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBarError(view: View, id: Int?) {
        val errorId = id ?: GENERAL_ERROR
        Snackbar.make(view, errorId, Snackbar.LENGTH_SHORT)
    }
}
