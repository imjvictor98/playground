package br.com.cvj.playground.ui.components.dropdownmenu

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size

class CompExposedDropDownMenuState<T>(
    @DrawableRes private val _iconEnabled: Int,
    @DrawableRes private val _iconDisabled: Int,
    private val _items: ArrayList<T>
) {
    val items: List<T>
        get() = _items

    private var _enabled by mutableStateOf(false)
    val enabled
        get() = _enabled
    private var _value by mutableStateOf<T?>(null)
    val value
        get() = _value
    private var _selectedIndex by mutableStateOf(-1)
    val selectedIndex
        get() = _selectedIndex
    private var _size by mutableStateOf(Size.Zero)
    val size
        get() = _size

    @get:DrawableRes
    private val _icon: Int
        @Composable get() = if (_enabled) {
            _iconEnabled
        } else {
            _iconDisabled
        }
    val icon
        @Composable get() = _icon


    fun onEnabled(newValue: Boolean) {
        _enabled = newValue
    }

    fun onSelectedIndex(newIndex: Int) {
        if (items.isNotEmpty()) {
            _selectedIndex = newIndex
            _value = items[newIndex]
        }

    }

    fun onSize(newSize: Size) {
        _size = newSize
    }

    fun onUpdateItems(items: List<T>) {
        _items.clear()
        _items.addAll(items)
    }
}

@Composable
fun <T> rememberCompExposedDropDownMenuState(
    @DrawableRes iconEnabled: Int,
    @DrawableRes iconDisabled: Int,
    items: ArrayList<T>
) = remember { CompExposedDropDownMenuState(iconEnabled, iconDisabled, items) }