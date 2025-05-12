package com.app.grocer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.grocer.data.model.Product

@Composable
fun SearchItem(modifier: Modifier = Modifier, searchResponse: Product, onItemClicked: () -> Unit) {
  Card(modifier = Modifier.fillMaxSize().height(100.dp).clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
  ) {
    onItemClicked.invoke()
  }) {
    Row {
      AsyncImage(
        model = searchResponse.productImage,
        contentDescription = "product_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(150.dp).padding(10.dp).clip(RoundedCornerShape(12.dp)),
      )
      Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
        Text(searchResponse.name)
        Text("Rs. " + searchResponse.price.toString())
      }
    }
  }
}
