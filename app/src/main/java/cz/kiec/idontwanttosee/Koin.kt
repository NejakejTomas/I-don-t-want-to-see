package cz.kiec.idontwanttosee

import androidx.room.Room
import cz.kiec.idontwanttosee.repository.RuleRepository
import cz.kiec.idontwanttosee.repository.RuleRepositoryImpl
import cz.kiec.idontwanttosee.repository.dbs.AppDatabase
import cz.kiec.idontwanttosee.repository.dbs.dao.RuleDao
import cz.kiec.idontwanttosee.viewmodel.AddRuleViewModel
import cz.kiec.idontwanttosee.viewmodel.ModifyRuleViewModel
import cz.kiec.idontwanttosee.viewmodel.RulesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<RuleDao> {
        val database = get<AppDatabase>()
        database.ruleDao()
    }

    singleOf(::RuleRepositoryImpl) { bind<RuleRepository>() }

    viewModelOf(::RulesViewModel)
    viewModel { params -> ModifyRuleViewModel(params.get(), get(), get()) }
    viewModelOf(::AddRuleViewModel)

    // Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppDatabase::class.simpleName
        ).fallbackToDestructiveMigration().build()
    }
}