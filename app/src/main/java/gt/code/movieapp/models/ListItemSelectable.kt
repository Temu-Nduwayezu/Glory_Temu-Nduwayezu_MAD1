package gt.code.movieapp.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ListItemSelectable(
    val title: String,
    val isSelected: Boolean
)
