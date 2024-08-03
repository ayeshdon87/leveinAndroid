package com.ayesh.leveintest.presantation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayesh.leveintest.presantation.ui.theme.darkText
import com.ayesh.leveintest.presantation.ui.theme.white
import com.ayesh.leveintest.presantation.ui.theme.typoLocal

@Composable
fun tagView(
    tag: String,
    onTagClick: (String) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .padding(4.dp)
                .height(20.dp)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
                .clickable { onTagClick(tag) }
                .background(white, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = tag,
            color = darkText,
            style = typoLocal.bodyNormal,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
