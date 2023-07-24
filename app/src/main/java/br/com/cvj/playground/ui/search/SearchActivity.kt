@file:OptIn(ExperimentalAnimationApi::class)

package br.com.cvj.playground.ui.search

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.data.repository.search.SearchCountriesDataSource
import br.com.cvj.playground.data.repository.search.SearchCountriesRaw
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.ui.components.dropdownmenu.CompExposedDropDownMenu
import br.com.cvj.playground.ui.components.dropdownmenu.CompExposedDropDownMenuState
import br.com.cvj.playground.ui.components.dropdownmenu.rememberCompExposedDropDownMenuState
import br.com.cvj.playground.ui.theme.Black
import br.com.cvj.playground.ui.theme.Blue500
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.R)
class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen(
                viewModel = viewModel(
                    factory = SearchViewModelFactory(
                        SearchCountriesRepository(
                            SearchCountriesDataSource(
                                SearchCountriesRaw(LocalContext.current.resources),
                                ioDispatcher = Dispatchers.IO
                            )
                        )
                    )
                )
            )
        }
    }
}


@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    suggestions: List<String> = emptyList(),
    searchResults: List<SearchCityItem> = emptyList(),
    isCompleted: Boolean = false
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            suggestions = suggestions,
            searchResults = searchResults,
            isCompleted = isCompleted
        )
    }
}

@Composable
private fun SearchHint(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Text(
            color = Color.Black,
            text = "Pesquisa sua cidade"
        )
    }
}

@Composable
fun SearchTextField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .then(
                Modifier
                    .height(56.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = if (!focused) 16.dp else 0.dp,
                        end = 16.dp
                    )
            ),
        color = White,
        shape = RoundedCornerShape(percent = 50),
    ) {
        CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
            ) {

                if (query.text.isEmpty()) {
                    SearchHint(modifier.padding(start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .onFocusChanged {
                                onSearchFocusChange(it.isFocused)
                            }
                            .focusRequester(focusRequester)
                            .padding(top = 9.dp, bottom = 8.dp, start = 24.dp, end = 8.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Black)
                    )

                    when {
                        searching -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(36.dp)
                            )
                        }

                        query.text.isNotEmpty() -> {
                            IconButton(onClick = onClearQuery) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }

    }

}


@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            // Back button
            IconButton(
                modifier = Modifier.padding(start = 2.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }

        SearchTextField(
            query,
            onQueryChange,
            onSearchFocusChange,
            onClearQuery,
            searching,
            focused,
            modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    state: SearchState = rememberSearchState(),
    dropDownMenuState: CompExposedDropDownMenuState<SearchCityItem> = rememberCompExposedDropDownMenuState(
        iconEnabled = android.R.drawable.arrow_up_float,
        iconDisabled = android.R.drawable.arrow_down_float,
        items = if (state.searchResults.isEmpty()) {
            arrayListOf()
        } else {
            state.searchResults as ArrayList
        }
    )
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Black),
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { state.query = it },
            onSearchFocusChange = { state.focused = it },
            onClearQuery = { state.query = TextFieldValue("") },
            onBack = { state.query = TextFieldValue("") },
            searching = state.searching,
            focused = state.focused,
            modifier = modifier
        )

        Row {
            CompExposedDropDownMenu(
                state = dropDownMenuState,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        LaunchedEffect(state.query.text) {
            state.searching = true
            delay(1000)
            if (state.query.text.isNotEmpty()) {
                viewModel.getCountries(state.query.text)
            }
            state.searching = false
            viewModel.uiState.collect {
                if (it is SearchUiState.Success) {
                    state.isCompleted = true
                    state.searching = false
                    state.searchResults = it.countries
                }
            }
        }

        when (state.searchDisplay) {
            SearchDisplay.InitialResults -> {

            }

            SearchDisplay.NoResults -> {

            }

            SearchDisplay.Suggestions -> {

            }

            SearchDisplay.Results -> {
                dropDownMenuState.onUpdateItems(state.searchResults)
                state.searchResults.also { searches ->
                    LazyColumn {
                        items(searches) {search ->
                            Text(text = search.name, color = White)
                        }
                    }
                }
            }
            else -> {

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true, backgroundColor = 0x000)
@Composable
fun GreetingPreview() {
    PlaygroundTheme {
        SearchScreen(
            viewModel = viewModel(
                factory = SearchViewModelFactory(
                    SearchCountriesRepository(
                        SearchCountriesDataSource(
                            SearchCountriesRaw(LocalContext.current.resources),
                            ioDispatcher = Dispatchers.IO
                        )
                    )
                )
            )
        )
    }
}