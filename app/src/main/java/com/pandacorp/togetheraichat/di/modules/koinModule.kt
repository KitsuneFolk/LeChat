package com.pandacorp.togetheraichat.di.modules

import com.pandacorp.togetheraichat.data.mapper.MessagesMapper
import com.pandacorp.togetheraichat.data.repository.TogetherRepositoryImpl
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import com.pandacorp.togetheraichat.presentation.vm.MessagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val koinModule = module {
    singleOf(::MessagesMapper)
    single<TogetherRepository> {
        TogetherRepositoryImpl(get())
    }
    viewModelOf(::MessagesViewModel)
}