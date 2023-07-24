package br.com.cvj.playground.ui.search

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import br.com.cvj.playground.domain.model.search.SearchCityItem

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    suggestions: List<String>,
    searchResults: List<SearchCityItem>,
    isCompleted: Boolean
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var suggestions by mutableStateOf(suggestions)
    var searchResults by mutableStateOf(searchResults)
    var isCompleted by mutableStateOf(isCompleted)

    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.InitialResults
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            isCompleted && searchResults.isNotEmpty() -> SearchDisplay.Results
            else -> SearchDisplay.Error
        }

    override fun toString(): String {
        return "🚀 State query: $query, focused: $focused, searching: $searching " +
                "suggestions: ${suggestions.size}, " +
                "searchResults: ${searchResults.size}, " +
                " searchDisplay: $searchDisplay"

    }
}