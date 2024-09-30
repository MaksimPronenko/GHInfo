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

//    private lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "db"
//        )
//            .fallbackToDestructiveMigration()
//            .build()

        startKoin {
            androidContext(this@App)
            modules(
                module{
//                    single<WeatherDao> {
//                        provideWeatherDao(context = get())
//                    }
                    single<Repository> {
                        Repository(
//                            dao = get()
                        )
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
                    viewModel<ProfileScreenViewModel> {
                        ProfileScreenViewModel(repository = get())
                    }
                }
            )
        }
    }

//    private fun provideWeatherDao(context: Context): WeatherDao {
//        return (context as App).db.weatherDao()
//    }
    private fun provideSharedPreferences(context: Context): SharedPreferences {
        return (context as App).getSharedPreferences(LAST_SEARCH, Context.MODE_PRIVATE)
    }
}