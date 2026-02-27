package com.example.financetracker.di

import com.example.financetracker.repository.IFinanceEntryRepository
import com.example.financetracker.repository.TestFinanceEntryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideFinanceEntryRepository(): IFinanceEntryRepository = TestFinanceEntryRepository()
}