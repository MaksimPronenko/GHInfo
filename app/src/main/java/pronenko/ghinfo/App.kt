package pronenko.ghinfo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import pronenko.ghinfo.ui.details.DetailsScreenViewModel
import pronenko.ghinfo.ui.search.SearchScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pronenko.ghinfo.data.Repository
import pronenko.ghinfo.domain.PreferencesManager
import pronenko.ghinfo.ui.common.LAST_SEARCH
import pronenko.ghinfo.ui.profile.ProfileScreenViewModel

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                module{
                    single<Repository> {
                        Repository()
                    }
                    single<SharedPreferences> {
                        provideSharedPreferences(context = get())
                    }
                    single<PreferencesManager> {
                        PreferencesManager(sharedPreferences = get())
                    }
                    viewModel<SearchScreenViewModel> {
                        SearchScreenViewModel(repository = get(), preferencesManager = get())
                    }
                    viewModel<DetailsScreenViewModel> {
                        DetailsScreenViewModel(repository = get())
                    }
                    single<ProfileScreenViewModel> {
                        ProfileScreenViewModel()
                    }
                }
            )
        }
    }

    private fun provideSharedPreferences(context: Context): SharedPreferences {
        return (context as App).getSharedPreferences(LAST_SEARCH, Context.MODE_PRIVATE)
    }
}
