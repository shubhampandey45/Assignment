package com.myjar.jarassignment.data.repository

import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.model.ComputerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface JarRepository {
    suspend fun fetchResults(): Flow<List<ComputerItem>>

    suspend fun fetchResultById(id: String): Flow<ComputerItem>
}

class JarRepositoryImpl(
    private val apiService: ApiService
) : JarRepository {
    override suspend fun fetchResults(): Flow<List<ComputerItem>> = flow {
        val result = apiService.fetchResults()
        emit(result)
    }

    override suspend fun fetchResultById(id: String): Flow<ComputerItem> = flow {
        val result = apiService.fetchResultById(id)
        emit(result)
    }
}