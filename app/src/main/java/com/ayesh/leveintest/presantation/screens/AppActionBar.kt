package com.ayesh.leveintest.presantation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ayesh.leveintest.presantation.ui.theme.darkText
import com.ayesh.leveintest.presantation.ui.theme.white
import com.ayesh.leveintest.presantation.ui.theme.typoLocal

@Composable
fun appActionBar(title: String) {

    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(white)
            .shadow(elevation = 10.dp, spotColor = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(top = 40.dp, bottom = 16.dp, start = 20.dp, end = 20.dp),
            color = darkText,
            style = typoLocal.bodyNormal
        )
    }


}