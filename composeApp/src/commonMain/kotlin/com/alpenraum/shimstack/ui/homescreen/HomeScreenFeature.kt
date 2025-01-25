package com.alpenraum.shimstack.ui.homescreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alpenraum.shimstack.base.use
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.model.cardsetup.CardSetup
import com.alpenraum.shimstack.domain.model.cardsetup.CardType
import com.alpenraum.shimstack.ui.base.compose.components.AttachToLifeCycle
import com.alpenraum.shimstack.ui.base.compose.components.BikePager
import com.alpenraum.shimstack.ui.base.compose.components.CARD_MARGIN
import com.alpenraum.shimstack.ui.base.compose.components.LoadingSpinner
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.copy_add_new_bike
import shimstackmultiplatform.composeapp.generated.resources.il_empty_mountain

@Composable
fun HomeScreenFeature(
    navController: NavController,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    AttachToLifeCycle(viewModel = viewModel)
    val (state, intents, event) = use(viewModel = viewModel, navController)
    HomeScreenContent(
        state = state,
        event = event,
        intents = intents
    )
    LaunchedEffect(Unit) {
        viewModel.initVm()
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeScreenContract.State,
    event: SharedFlow<HomeScreenContract.Event>,
    intents: (HomeScreenContract.Intent) -> Unit
) {
    val isLoading = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val pagerState =
        androidx.compose.foundation.pager
            .rememberPagerState(initialPage = 0) { state.bikes.size + 1 }
    val lastPagerPosition = remember { mutableIntStateOf(0) }
    val snackState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        event.collectLatest {
            when (it) {
                HomeScreenContract.Event.Error -> scope.launch { snackState.showSnackbar("ERROR") }
                HomeScreenContract.Event.FinishedLoading -> {
                    isLoading.value = false
                }

                HomeScreenContract.Event.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BikePager(
            modifier =
                Modifier
                    .padding(top = 32.dp, bottom = 16.dp),
            showPlaceholder = isLoading.value,
            bikes = state.bikes,
            pagerState = pagerState,
            onAddNewBikeClick = { intents(HomeScreenContract.Intent.OnAddNewBike) },
            onDetailsClick = {
                intents(
                    HomeScreenContract.Intent.OnBikeDetailsClicked(it)
                )
            },
            onSelectedBikeChanged = {}
        )
        AnimatedVisibility(isLoading.value) {
            LoadingSpinner()
        }

        AnimatedVisibility(!isLoading.value) {
            AnimatedContent(
                state.getBike(pagerState.currentPage),
                modifier = Modifier.fillMaxHeight(),
                label = "BikeDetails",
                transitionSpec = {
                    if (lastPagerPosition.intValue > pagerState.currentPage) {
                        // scroll to left
                        (slideInHorizontally { x -> -x } + fadeIn()).togetherWith(
                            slideOutHorizontally { x -> x } + fadeOut()
                        )
                    } else {
                        (slideInHorizontally { x -> x } + fadeIn()).togetherWith(
                            slideOutHorizontally { x -> -x } + fadeOut()
                        )
                    }
                }
            ) { bike ->
                SideEffect {
                    lastPagerPosition.intValue = pagerState.currentPage
                }
                bike?.let { bike1 ->
                    Spacer(modifier = Modifier.height(16.dp))
                    BikeDetails(
                        bike = bike1,
                        isMetric = state.isMetric,
                        cardSetup = state.detailCardsSetup,
                        intents = intents
                    )
                } ?: EmptyDetailsEyeCandy()
            }
        }
    }
    SnackbarHost(hostState = snackState)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EmptyDetailsEyeCandy() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Image(
            painter = painterResource(Res.drawable.il_empty_mountain),
            contentDescription = null,
            modifier =
                Modifier
                    .semantics {
                        invisibleToUser()
                    }.fillMaxSize(0.6f)
                    .padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(Res.string.copy_add_new_bike),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontStyle = FontStyle.Italic
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BikeDetails(
    bike: Bike,
    cardSetup: ImmutableList<CardSetup>,
    isMetric: Boolean,
    intents: (HomeScreenContract.Intent) -> Unit
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(CARD_MARGIN, alignment = Alignment.Start)
        ) {
            cardSetup.forEach {
                when (it.type) {
                    CardType.TIRES ->
                        TireDetails(
                            bigCard = it.bigCard,
                            bike = bike,
                            isMetric,
                            modifier = Modifier.align(Alignment.Bottom).fillMaxRowHeight()
                        )

                    CardType.FORK ->
                        ForkDetails(
                            bigCard = it.bigCard,
                            bike = bike,
                            isMetric,
                            modifier = Modifier.align(Alignment.Bottom).fillMaxRowHeight()
                        )

                    CardType.SHOCK ->
                        ShockDetails(
                            bigCard = it.bigCard,
                            bike = bike,
                            isMetric,
                            modifier = Modifier.align(Alignment.Bottom).fillMaxRowHeight()
                        )
                }
            }
        }
        HorizontalDivider(
            modifier =
                Modifier
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(100)),
            thickness = 2.dp
        )

        Button(
            onClick = {} // TODO: editable detail cards config
        ) {
            Text("Edit detail cards")
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun Preview() {
    AppTheme {
        HomeScreenContent(
            state = HomeScreenContract.State(persistentListOf(), persistentListOf()),
            event = MutableSharedFlow(),
            intents = {}
        )
    }
}