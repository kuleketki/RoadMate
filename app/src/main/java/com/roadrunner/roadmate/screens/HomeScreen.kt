package com.roadrunner.roadmate.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.roadrunner.roadmate.R

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val optionsImages = listOf<Int>(
        R.drawable.vehicle_safety,
        R.drawable.speed_limit,
        R.drawable.intersections,
        R.drawable.traffic_lanes,
        R.drawable.traffic_signs,
        R.drawable.traffic_intersections,
        R.drawable.signaling_merging,
        R.drawable.rightway,
        R.drawable.parking,
    )
    val optionsText = listOf<String>(
        "Vehicle Safety",
        "Speed Limit",
        "Intersections",
        "Traffic Lanes",
        "Traffic Signs",
        "Traffic Intersections",
        "Signaling & Merging",
        "Right of way",
        "Parking",
    )

    val recommendedCardHeaderText =
        listOf<String>("News", "Visit DMV", "Book Slot", "Study")

    val recommendedCardImages =
        listOf<Int>(
            R.drawable.news,
            R.drawable.dmv,
            R.drawable.appointment,
            R.drawable.educational_material
        )

    val recommendedCardDetailsText = listOf<String>(
        "All the latest news around DMV",
        "Go to official DMV website",
        "Book appointments to avoid queues",
        "Watch the educational materials by DMV"
    )

    val recommendedCardDetailsLink = listOf<String>(
        RoadMateScreenNav.NewsListScreen.name,
        "https://www.dmv.ca.gov/portal/",
        "https://www.dmv.ca.gov/portal/appointments/select-appointment-type",
        "https://www.dmv.ca.gov/portal/driver-licenses-identification-cards/preparing-for-knowledge-and-drive-tests/"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "menu icon",
                tint = Color(0XFF27214D),
                modifier = modifier.size(24.dp)
            )


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = modifier.size(54.dp)
                )
                Spacer(modifier = modifier.height(1.dp))

                Text(text = "Lets go!", fontSize = 8.sp, color = colorResource(R.color.road_navy))
            }

        }

        //Greeting text
        Text(
            text = "Explore your options!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 28.sp,
            modifier = modifier.padding(bottom = 24.dp)
        )

        //search bar
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.search_bar_grey),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray,
                modifier = modifier.size(24.dp)
            )

            Spacer(modifier = modifier.width(12.dp))

            Text(text = "Search options", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.outline_filter_list_24),
                contentDescription = null,
                modifier = modifier.height(15.dp)
            )
        }

        Spacer(modifier = modifier.height(32.dp))

        Text(
            text = "Important Links",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .padding(1.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(recommendedCardDetailsText.size) { index ->
                RecommendedComboCard(
                    name = recommendedCardHeaderText[index],
                    details = recommendedCardDetailsText[index],
                    image = recommendedCardImages[index],
                    url = recommendedCardDetailsLink[index],
                    navController = navController
                )
            }
        }


        LazyVerticalGrid(
            modifier = modifier.padding(10.dp),
            columns = GridCells.Fixed(2),
            content = {
                items(count = optionsImages.size) { i ->
                    HomeScreenOptionsCard(modifier, image = optionsImages[i], name = optionsText[i])
                }
            },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
    }
}

@Composable
fun RecommendedComboCard(
    modifier: Modifier = Modifier,
    name: String,
    image: Int,
    details: String,
    url: String,
    navController: NavController
) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = modifier
            .width(130.dp)
            .clickable(onClick = {
                if (url != RoadMateScreenNav.NewsListScreen.name) {
                    uriHandler.openUri(url)
                } else {
                    navController.navigate(RoadMateScreenNav.NewsListScreen.name)
                }
            }),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = modifier.padding(10.dp)) {


            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = modifier.height(4.dp))

            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontSize = 12.sp
            )

            Spacer(modifier = modifier.height(4.dp))

            Text(
                text = details,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 12.sp,
                color = colorResource(R.color.road_navy)
            )

        }
    }
}

@Composable
fun HomeScreenOptionsCard(modifier: Modifier = Modifier, image: Int, name: String) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = modifier.padding(5.dp)) {
            Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            }

            Spacer(modifier = modifier.height(8.dp))

            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    //HomeScreen()
    //HomeScreenOptionsCard(name = "Vehicle Safety", image = R.drawable.vehicle_safety)
}

