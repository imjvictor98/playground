package br.com.cvj.playground.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.R
import br.com.cvj.playground.data.repository.search.SearchCountriesDataSource
import br.com.cvj.playground.data.repository.search.SearchCountriesRaw
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import br.com.cvj.playground.ui.home.HomeActivity.Companion.EXTRA_CITY_ITEM
import br.com.cvj.playground.ui.home.HomeActivity.Companion.EXTRA_CITY_ITEM_RESULT_CODE
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.ui.theme.Typography
import br.com.cvj.playground.util.extension.activity
import br.com.cvj.playground.util.extension.hideKeyboard

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Colors.White
                ) {
                    SearchScreen()
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.Factory(
            repository = SearchCountriesRepository(
                SearchCountriesDataSource(
                    SearchCountriesRaw(LocalContext.current.resources)
                )
            )
        )
    )
) {
    val context = LocalContext.current

    val searchUiState = viewModel.uiState.collectAsState()

    val (value, onValueChange) = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            enabled = searchUiState.value != SearchUiState.Loading,
            value = value,
            label = {
                Text(
                    stringResource(id = R.string.activity_search_city),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            onValueChange = {
                viewModel.userIsTyping()
                onValueChange(it)
            },
            singleLine = true,
            isError = searchUiState.value == SearchUiState.Empty,
            supportingText = {
                if (searchUiState.value == SearchUiState.Empty) {
                    Text(stringResource(id = R.string.activity_search_no_cities_found))
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = if (value.isNotEmpty()) ImeAction.Search else ImeAction.Default),
            keyboardActions = KeyboardActions(onSearch = {
                if (value.isNotEmpty()) {
                    viewModel.fetchCountries(value)
                    context.hideKeyboard()
                }
            }),
            trailingIcon = {
                IconButton(
                    enabled = value.isNotEmpty() && searchUiState.value != SearchUiState.Loading,
                    onClick = {
                        viewModel.fetchCountries(value)
                    }) {
                    if (searchUiState.value == SearchUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Colors.Salmon,
                            strokeWidth = 2.dp
                        )
                    }
                }
            },
            leadingIcon = {
                IconButton(
                    enabled = value.isNotEmpty(),
                    onClick = {
                        if (searchUiState.value != SearchUiState.Loading) {
                            viewModel.fetchCountries(value)
                        } else {
                            viewModel.cancelFetchCountries()
                        }
                    }) {
                    if (searchUiState.value != SearchUiState.Loading) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Colors.Black,
                        )
                    } else {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = "Search",
                            tint = Colors.Black,
                        )
                    }
                }
            },
            placeholder = { }
        )

        when (val state = searchUiState.value) {
            is SearchUiState.Success -> {
                LazyColumn {
                    items(state.cities) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    Intent().also { intent ->
                                        intent.putExtra(EXTRA_CITY_ITEM, it)
                                        context.activity?.setResult(
                                            EXTRA_CITY_ITEM_RESULT_CODE,
                                            intent
                                        )
                                        context.activity?.finish()
                                    }
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    style = Typography.labelSmall,
                                    modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp),
                                    text = it.name,
                                    maxLines = 1,
                                    fontSize = 16.sp
                                )

                                Text(
                                    style = Typography.bodySmall,
                                    modifier = Modifier.padding(top = 2.dp, bottom = 8.dp, start = 4.dp, end = 4.dp),
                                    text = it.country,
                                    color = Colors.Gray600,
                                    maxLines = 1,
                                    fontSize = 16.sp
                                )
                            }
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                                contentDescription = "Search",
                                tint = Colors.Black,
                            )
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
@Preview
fun SearchPreview() {
    PlaygroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen()
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFE5E9EE)
fun SearchListItemPreview() {
    PlaygroundTheme {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White, shape = RoundedCornerShape(32.dp))
                    .padding(4.dp)
                    .clickable {},
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        style = Typography.labelSmall,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                        text = "Buenos Aires",
                        maxLines = 1,
                        fontSize = 16.sp
                    )

                    Text(
                        style = Typography.bodySmall,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                        text = "Argentina",
                        maxLines = 1,
                        color = Colors.Gray600,
                        fontSize = 16.sp
                    )
                }

                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = "Search",
                    tint = Colors.Black,
                )
            }
        }
    }
}