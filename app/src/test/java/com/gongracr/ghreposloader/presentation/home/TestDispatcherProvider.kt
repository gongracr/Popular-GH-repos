package com.gongracr.ghreposloader.presentation.home

import com.gongracr.core.utility.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider(private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()) : DispatcherProvider {
    override fun main() = dispatcher
    override fun io() = dispatcher
    override fun default() = dispatcher
    override fun unconfined() = dispatcher
}