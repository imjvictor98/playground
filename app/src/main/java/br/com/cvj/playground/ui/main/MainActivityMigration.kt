package br.com.cvj.playground.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
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
import br.com.cvj.playground.ui.widget.atom.TextTabAtom
import br.com.cvj.playground.ui.widget.molecule.SearchableHeaderMolecule
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ShapeDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import br.com.cvj.playground.ui.widget.atom.TabAtom
import br.com.cvj.playground.ui.widget.molecule.TabLayoutMolecule
import br.com.cvj.playground.util.extension.pagerTabIndicatorOffset
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val tabData = getTabList()
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0F) { tabData.size }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Colors.Black100)) {
        SearchableHeaderMolecule(text = "São Paulo, SP", modifier = Modifier.padding(16.dp))
        TabLayout(tabData, pagerState)
        TabContent(tabData, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(tabData: List<Pair<String, ImageVector>>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabLayoutMolecule(pagerState = pagerState, tabData = tabData, text = { index, tabItem ->
        if (pagerState.currentPage == index) {
            TextTabAtom(text = tabItem.first, textColor = Color.White)
        } else {
            TextTabAtom(text = tabItem.first, textColor = Colors.Gray100)
        }
    }, onClick = { index, _ ->
        scope.launch {
            pagerState.animateScrollToPage(index)
        }
    })
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(tabData: List<Pair<String, ImageVector>>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, userScrollEnabled = true) { index ->
        HomeScreen(index)
    }
}

@Composable
fun HomeScreen(index: Int) {
    Column(Modifier.fillMaxSize()) {
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

@Preview
@Composable
fun PreviewContent() {
    PlaygroundTheme {
        MainScreen()
    }
}