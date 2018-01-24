package makes.flint.poh.data.dataController

import makes.flint.poh.data.dataController.dataSource.DataSource
import javax.inject.Inject

/**
 * DataController
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
open class DataController @Inject constructor(private val apiManager: DataSource,
                                              private val realmManager: DataSource
) {

}