package com.myjar.jarassignment.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val _itemDetail = MutableStateFlow<ComputerItem?>(null)

    val itemDetail: StateFlow<ComputerItem?>
        get() = _itemDetail

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())

    fun fetchData() {
        viewModelScope.launch {
            repository.fetchResults().collect {
                _listStringData.value = it
            }
        }
    }

    fun fetchItemDetail(itemId: String) {
        viewModelScope.launch {
            repository.fetchResultById(itemId).collect {
                _itemDetail.value = it
            }
        }
    }

    //search feature
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery

    val filterItems: StateFlow<List<ComputerItem>> =
        _searchQuery.combine(_listStringData) { query, items ->
            if (query.isBlank()) {
                items
            } else {
                items.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()

        )

    fun searchQueryChange(query: String){
        _searchQuery.value = query
    }
}