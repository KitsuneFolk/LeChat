package com.pandacorp.togetheraichat.di.modules

import com.pandacorp.togetheraichat.data.repository.TogetherRepositoryImpl
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import com.pandacorp.togetheraichat.presentation.vm.MessagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val koinModule = module {
    single<TogetherRepository> {
        TogetherRepositoryImpl()
    }
    viewModelOf(::MessagesViewModel)
}