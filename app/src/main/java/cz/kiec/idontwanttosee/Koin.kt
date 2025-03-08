package cz.kiec.idontwanttosee

import androidx.room.Room
import cz.kiec.idontwanttosee.dbs.AppDatabase
import cz.kiec.idontwanttosee.dbs.dao.NotificationChannelDao
import cz.kiec.idontwanttosee.dbs.dao.RuleDao
import cz.kiec.idontwanttosee.dbs.repository.NotificationChannelRepository
import cz.kiec.idontwanttosee.dbs.repository.RuleRepository
import cz.kiec.idontwanttosee.ui.screen.addnotificationchannel.AddNotificationChannelViewModel
import cz.kiec.idontwanttosee.ui.screen.addrule.AddRuleViewModel
import cz.kiec.idontwanttosee.ui.screen.modifyrule.ModifyRuleViewModel
import cz.kiec.idontwanttosee.ui.screen.notificationchannels.NotificationChannelsViewModel
import cz.kiec.idontwanttosee.ui.screen.rules.RulesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<RuleDao> {
        val database = get<AppDatabase>()
        database.ruleDao()
    }
    single<NotificationChannelDao> {
        val database = get<AppDatabase>()
        database.notificationChannelDao()
    }

    singleOf(::RuleRepository)
    singleOf(::NotificationChannelRepository)

    viewModelOf(::RulesViewModel)
    viewModelOf(::AddRuleViewModel)
    viewModel { params -> ModifyRuleViewModel(params.get(), get()) }
    viewModelOf(::NotificationChannelsViewModel)
    viewModelOf(::AddNotificationChannelViewModel)

    // Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppDatabase::class.simpleName
        ).fallbackToDestructiveMigration().build()
    }
}