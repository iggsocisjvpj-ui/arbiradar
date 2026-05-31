package com.arbiradar.mobile.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JokeGeneratorScreen() {
    var joke by remember { mutableStateOf("") }
    var setup by remember { mutableStateOf("") }
    var delivery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("any") }
    var showDelivery by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            "🎭 Joke Generator",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Category Selection
        CategoryDropdown(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        // Joke Card
        JokeCard(
            setup = setup,
            delivery = delivery,
            joke = joke,
            showDelivery = showDelivery,
            isFavorite = isFavorite,
            onShowDelivery = { showDelivery = !showDelivery },
            onFavoriteClick = { isFavorite = !isFavorite },
            onShareClick = { shareJoke(joke, setup, delivery) }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    isLoading = true
                    // Fetch joke from API
                    // isLoading = false
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("Get Joke", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf(
        "any",
        "general",
        "programming",
        "knock-knock",
        "christmas"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            "📂 Category: $selectedCategory",
            modifier = Modifier.align(Alignment.CenterStart)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun JokeCard(
    setup: String,
    delivery: String,
    joke: String,
    showDelivery: Boolean,
    isFavorite: Boolean,
    onShowDelivery: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (setup.isNotEmpty()) {
                Text(
                    setup,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                if (showDelivery && delivery.isNotEmpty()) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        delivery,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = onShowDelivery,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(if (showDelivery) "Hide Punchline" else "Show Punchline")
                }
            } else if (joke.isNotEmpty()) {
                Text(
                    joke,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Add to favorites",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }

                IconButton(onClick = onShareClick) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Share joke",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

private fun shareJoke(joke: String, setup: String, delivery: String) {
    val jokeText = if (setup.isNotEmpty()) {
        "$setup\n\n$delivery"
    } else {
        joke
    }
    // Implement share functionality
    // val shareIntent = Intent().apply {
    //     action = Intent.ACTION_SEND
    //     putExtra(Intent.EXTRA_TEXT, jokeText)
    //     type = "text/plain"
    // }
}
