package com.pandacorp.lechat.di.modules

import androidx.room.Room
import com.pandacorp.lechat.data.database.Database
import com.pandacorp.lechat.data.database.dao.ChatsDao
import com.pandacorp.lechat.data.mapper.ChatsMapper
import com.pandacorp.lechat.data.mapper.MessagesMapper
import com.pandacorp.lechat.data.repository.ChatsRepositoryImpl
import com.pandacorp.lechat.data.repository.TogetherRepositoryImpl
import com.pandacorp.lechat.domain.repository.ChatsRepository
import com.pandacorp.lechat.domain.repository.TogetherRepository
import com.pandacorp.lechat.presentation.vm.ChatsViewModel
import com.pandacorp.lechat.presentation.vm.MessagesViewModel
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