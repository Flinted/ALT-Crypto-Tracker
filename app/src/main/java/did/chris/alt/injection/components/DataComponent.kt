package did.chris.alt.injection.components

import dagger.Component
import did.chris.alt.injection.modules.DataModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DataModule::class))
interface DataComponent {
}