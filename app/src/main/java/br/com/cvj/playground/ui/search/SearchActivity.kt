package br.com.cvj.playground.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.data.repository.search.SearchCountriesDataSource
import br.com.cvj.playground.data.repository.search.SearchCountriesRaw
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import br.com.cvj.playground.ui.main.MainActivity
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.PlaygroundTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Search2Screen()
                }
            }
        }
    }

    @Composable
    fun Search2Screen(
        viewModel: SearchViewModel = viewModel(
            factory = SearchViewModel.Factory(
                repository = SearchCountriesRepository(
                    SearchCountriesDataSource(
                        SearchCountriesRaw(resources)
                    )
                )
            )
        )
    ) {
        val searchUiState = viewModel.uiState.collectAsState()

        val (value, onValueChange) = remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = value,
                label = { Text("Pesquisar cidade") },
                onValueChange = onValueChange,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.fetchCountries(value)
                    }) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Colors.Black,
                        )
                    }
                },
                placeholder = { }
            )

            when (val state = searchUiState.value) {
                is SearchUiState.Empty -> {
                    Text("Sem resultados para sua pesquisa!")
                }

                is SearchUiState.Success -> {
                    LazyColumn {
                        items(state.cities) {
                            Row(
                                modifier = Modifier.fillMaxWidth().clickable {
                                    Intent().also { intent ->
                                        intent.putExtra(MainActivity.EXTRA_CITY_ITEM, it)
                                        setResult(MainActivity.EXTRA_CITY_ITEM_RESULT_CODE, intent)
                                        finish()
                                    }
                                },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    text = it.name,
                                    maxLines = 1,
                                )

                                Icon(
                                    Icons.AutoMirrored.Outlined.ArrowForward,
                                    contentDescription = "Search",
                                    tint = Colors.Black,
                                )
                            }
                        }
                    }
                }

                is SearchUiState.Loading -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = "Procurando",
                            fontSize = 14.sp
                        )
                        CircularProgressIndicator(
                            modifier = Modifier.size(14.dp),
                            color = Colors.Salmon,
                            strokeWidth = 2.dp
                        )
                    }
                }

                else -> {}
            }
        }
    }
}