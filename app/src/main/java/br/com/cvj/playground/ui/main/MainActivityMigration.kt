package br.com.cvj.playground.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.ui.widget.molecule.SearchableHeaderMolecule
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivityIntegration : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val tabData = getTabList()
    val pagerState = rememberPagerState(pageCount = tabData.size)
    Column(modifier = Modifier.fillMaxSize()) {
        SearchableHeaderMolecule(text = "São Paulo, SP")
        TabLayout(tabData, pagerState)
        TabContent(tabData, pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(tabData: List<Pair<String, ImageVector>>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 5.dp,
                color = Colors.Transparent
            )
        },


        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        tabData.forEachIndexed { index, s ->
            Tab(selected = pagerState.currentPage == index, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
                icon = {
                    Icon(imageVector = s.second, contentDescription = null)
                },
                text = {
                    Text(text = s.first)
                })
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(tabData: List<Pair<String, ImageVector>>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { index ->
        when (index) {
            0 -> {
                HomeScreen(index)
            }

            1 -> {
                HomeScreen(index)
            }

            2 -> {
                HomeScreen(index)
            }

            3 -> {
                HomeScreen(index)
            }
        }

    }

}

@Composable
fun HomeScreen(index: Int) {
    Box {
        Text(text = "Page $index")
    }
}

private fun getTabList(): List<Pair<String, ImageVector>> {
    return listOf(
        "Home" to Icons.Default.Home,
        "Search" to Icons.Default.Search,
        "Favorites" to Icons.Default.Favorite,
        "Settings" to Icons.Default.Settings,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Preview()
@Composable
fun PreviewTab() {
    PlaygroundTheme {
        val tabData = getTabList()
        val pagerState = rememberPagerState(pageCount = tabData.size)
        TabLayout(tabData, pagerState)
    }
}

@Preview()
@Composable
fun PreviewContent() {
    PlaygroundTheme {
        MainScreen()
    }
}