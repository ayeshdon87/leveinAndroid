package com.ayesh.leveintest.presantation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayesh.leveintest.domain.models.BookItem
import com.ayesh.leveintest.presantation.ui.theme.darkText
import com.ayesh.leveintest.presantation.ui.theme.lightGreyText
import com.ayesh.leveintest.presantation.ui.theme.typoLocal
import com.ayesh.leveintest.presantation.ui.theme.white

@Composable
fun bookItemView(
    bookItm: BookItem,
    onBookClick: (String) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .padding(4.dp)
                .wrapContentHeight()
                .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                .clickable { onBookClick(bookItm.id) }
                .background(white, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Column {
            Text(
                text = bookItm.name,
                color = darkText,
                style = typoLocal.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "IBSN : ${bookItm.isbn}",
                color = lightGreyText,
                style = typoLocal.labelSmall,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "By ${bookItm.authorFirstName} ${bookItm.authorLastName}",
                color = lightGreyText,
                style = typoLocal.bodyNormal,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun bookItemPreview() {
    bookItemView(
        bookItm =
            BookItem(
                name = "Madol Duwa",
                id = "1234",
                authorId = "123",
                isbn = "1212321-3434-324",
                authorFirstName = "Ayesh",
                authorLastName = "Nanayakkara",
            ),
    ) {
//
    }
}
