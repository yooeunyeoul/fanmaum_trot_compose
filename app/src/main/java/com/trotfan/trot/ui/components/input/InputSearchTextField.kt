package com.trotfan.trot.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

enum class SearchStatus {
    TrySearch, NoResult, SearchResult, Loading
}

@Composable
fun SearchTextField(
    inputText: (String) -> Unit,
    isEnabled: Boolean = true,
    onClickSearch: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    placeHolder: Int,
) {
    var text by remember {
        mutableStateOf("")
    }

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current


    var showClearIcon by remember {
        mutableStateOf(false)
    }

    showClearIcon = text.isNotEmpty()

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = text,
        shape = RoundedCornerShape(12.dp),
        enabled = isEnabled,
        onValueChange = { outpout ->
            text = outpout
            // refresh search results
            inputText.invoke(outpout)

        },
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = 0.dp),
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = "Search Icon",
                tint = Gray700
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                Icon(
                    painterResource(id = R.drawable.input_clear),
                    contentDescription = "Clear Text",
                    modifier = Modifier.clickable {
                        text = ""
                        inputText.invoke("")
                    }, tint = Gray700
                )
            }
        },
        maxLines = 1,
        singleLine = true,
        textStyle = FanwooriTypography.body3,
        placeholder = {
            Text(
                text = stringResource(id = placeHolder),
                style = FanwooriTypography.body3,
                color = Gray600,
                maxLines = 1,
                fontSize = 17.sp,
                letterSpacing = (-0.25).sp,
                textAlign = TextAlign.Start,

                )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Gray100,
            cursorColor = Gray800,
            textColor = Gray800
        ),
        keyboardActions = KeyboardActions {
            onClickSearch.invoke(text)
            focusManager.clearFocus()
        }
    )
}

@Preview
@Composable
fun PreviewSearchTextField() {
    SearchTextField(inputText = {}, placeHolder = R.string.hint_search_star)
}