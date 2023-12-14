package br.com.cvj.playground.ui.widget.molecule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.widget.atom.TabAtom
import br.com.cvj.playground.util.extension.pagerTabIndicatorOffset

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> TabLayoutMolecule(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabData: List<T>? = null,
    text: @Composable ((index: Int, tabItem: T) -> Unit)?,
    onClick: (index: Int, tabItem: T) -> Unit
) {
    if (tabData?.isNotEmpty() == true) {
        ScrollableTabRow(
            containerColor = Colors.Transparent,
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 5.dp,
                    color = Colors.Transparent
                )
            },
            divider = {},


            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .then(modifier)

        ) {
            tabData.forEachIndexed { index, tabItem ->
                TabAtom(
                    modifier = Modifier
                        .drawBehind {
                            if (pagerState.currentPage == index) {
                                drawRoundRect(
                                    Colors.Salmon,
                                    Offset(0f, 0f),
                                    Size(size.width, size.height),
                                    CornerRadius(72f, 72f)
                                )
                                drawRoundRect(
                                    color = Colors.White,
                                    cornerRadius = CornerRadius(72f, 72f),
                                    style = Stroke(width = 2f),
                                )
                            } else {
                                drawRoundRect(
                                    color = Colors.Gray600,
                                    cornerRadius = CornerRadius(72f, 72f),
                                    style = Stroke(width = 2f),
                                )
                            }

                        },
                    selected = pagerState.currentPage == index,
                    text = { text?.invoke(index, tabItem) },
                    onClick = { onClick(index, tabItem) }
                )
            }
        }
    }
}