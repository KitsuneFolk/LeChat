package com.pandacorp.togetheraichat.di.modules

import androidx.room.Room
import com.pandacorp.togetheraichat.data.database.Database
import com.pandacorp.togetheraichat.data.database.dao.ChatsDao
import com.pandacorp.togetheraichat.data.mapper.ChatsMapper
import com.pandacorp.togetheraichat.data.mapper.MessagesMapper
import com.pandacorp.togetheraichat.data.repository.ChatsRepositoryImpl
import com.pandacorp.togetheraichat.data.repository.TogetherRepositoryImpl
import com.pandacorp.togetheraichat.domain.repository.ChatsRepository
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import com.pandacorp.togetheraichat.presentation.vm.ChatsViewModel
import com.pandacorp.togetheraichat.presentation.vm.MessagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val koinModule = module {
    singleOf(::MessagesMapper)
    singleOf(::ChatsMapper)
    single<TogetherRepository> {
        TogetherRepositoryImpl(get())
    }
    single {
        Room.databaseBuilder(get(), Database::class.java, "CHAT_WITH_AI_DATABASE").build()
    }
    single<ChatsDao> {
        get<Database>().chatsDao()
    }
    single<ChatsRepository> {
        ChatsRepositoryImpl(get(), get())
    }
    viewModelOf(::MessagesViewModel)
    viewModelOf(::ChatsViewModel)
}