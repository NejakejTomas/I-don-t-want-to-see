package cz.kiec.idontwanttosee.ui.screen.rules

import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Card
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Visibility
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens
import cz.kiec.idontwanttosee.ui.elements.AnnotatedText
import cz.kiec.idontwanttosee.ui.elements.BottomNavigationBar
import cz.kiec.idontwanttosee.ui.elements.EditDeleteDialog
import cz.kiec.idontwanttosee.ui.elements.TopAppBar
import cz.kiec.idontwanttosee.ui.elements.screenContentPadding
import cz.kiec.idontwanttosee.ui.navigation.BottomNavBarEntries
import cz.kiec.idontwanttosee.ui.navigation.Screen
import cz.kiec.idontwanttosee.ui.theme.IDontWantToSeeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RulesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RulesViewModel = koinViewModel(),
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.top_bar_title_rules),
                navController = navController,
                bottomNavBarEntries = BottomNavBarEntries.global,
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, BottomNavBarEntries.global)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddRule) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.floating_button_add_rule_description)
                )
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle(RulesUiState())

        LazyColumn(
            Modifier
                .padding(innerPadding)
                .screenContentPadding()
        ) {
            items(uiState.rules, key = { it.id }) { rule ->
                val m =
                    Modifier
                        .fillMaxWidth()
                        .let {
                            if (rule != uiState.rules.last()) it.padding(bottom = Dimens.D5) else it
                        }
                ExpandableRule(
                    modifier = m,
                    state = rule,
                    onLongClick = viewModel::onLongClick,
                    onClick = viewModel::onClick,
                    isExpanded = uiState.expandedRules.contains(rule)
                )
            }
        }

        uiState.editDialogForRule?.let {
            EditDeleteDialog(onDismiss = viewModel::hideDialog, onDelete = {
                viewModel.deleteRule(it)
            }, onModify = {
                navController.navigate(Screen.ModifyRule(it.id))
            })
        }
    }
}

@Preview
@Composable
private fun ExpandableRulePreview() {
    IDontWantToSeeTheme {
        ExpandableRule(
            Modifier, RulesUiState.RuleUiState(
                id = 0,
                packageName = "com.discord",
                isEnabled = true,
                ignoreOngoing = true,
                ignoreWithProgressBar = false,
                hideTitle = true,
                hideContent = false,
                hideLargeImage = true
            ),
            false,
            {}, {})
    }
}

@Composable
private fun Boolean.checkboxColor(): Color {
    return if (this) CheckboxDefaults.colors().checkedBoxColor
    else CheckboxDefaults.colors().disabledCheckedBoxColor
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ExpandableRule(
    modifier: Modifier = Modifier,
    state: RulesUiState.RuleUiState,
    isExpanded: Boolean,
    onLongClick: (RulesUiState.RuleUiState) -> Unit,
    onClick: (RulesUiState.RuleUiState) -> Unit,
) {
    data class Item(
        @StringRes val text: Int, val icon: ImageVector, val iconColor: Color,
    )

    val filterItems = listOf(
        Item(
            R.string.rule_filter_is_enabled,
            Icons.Filled.CheckCircleOutline,
            state.isEnabled.checkboxColor()
        ),
        Item(
            R.string.rule_filter_ignore_ongoing,
            ImageVector.vectorResource(R.drawable.ic_progressbar_ongoing),
            state.ignoreOngoing.checkboxColor()
        ),
        Item(
            R.string.rule_filter_ignore_with_progressbar,
            ImageVector.vectorResource(R.drawable.ic_progressbar),
            state.ignoreWithProgressBar.checkboxColor()
        )
    )

    val actionItems = listOf(
        Item(
            R.string.rule_action_hide_title,
            Icons.Filled.Title,
            state.hideTitle.checkboxColor()
        ),
        Item(
            R.string.rule_action_hide_content,
            Icons.Filled.Abc,
            state.hideContent.checkboxColor()
        ),
        Item(
            R.string.rule_action_hide_image,
            Icons.Filled.Image,
            state.hideLargeImage.checkboxColor()
        ),
    )

    fun List<Item>.shrunkConstraints() = ConstraintSet {
        val refs = mapIndexed { i, item ->
            Triple(
                createRefFor("icon_$i"),
                item,
                createRefFor("text_$i")
            )
        }

        createHorizontalChain(*refs.map { ref -> ref.first }.toTypedArray())

        refs.forEach { ref ->
            constrain(ref.third) {
                visibility = Visibility.Gone
                start.linkTo(ref.first.end)
                top.linkTo(ref.first.top)
                bottom.linkTo(ref.first.bottom)
            }
        }
    }

    fun List<Item>.expandedConstraints() = ConstraintSet {
        val refs = mapIndexed { i, item ->
            Triple(
                createRefFor("icon_$i"),
                item,
                createRefFor("text_$i")
            )
        }

        createVerticalChain(*refs.map { ref -> ref.first }.toTypedArray())
        refs.forEach { ref ->
            constrain(ref.third) {
                start.linkTo(ref.first.end)
                top.linkTo(ref.first.top)
                bottom.linkTo(ref.first.bottom)
            }
        }
    }

    @Composable
    fun List<Item>.card(modifier: Modifier = Modifier) {
        Card(modifier.fillMaxWidth()) {
            val shrunk = shrunkConstraints()
            val expanded = expandedConstraints()

            ConstraintLayout(
                if (isExpanded) expanded else shrunk,
                Modifier.padding(Dimens.D2),
                animateChangesSpec = tween<Float>(),
            ) {
                forEachIndexed { i, item ->
                    Icon(
                        imageVector = item.icon,
                        contentDescription = if (isExpanded) null else stringResource(item.text),
                        tint = item.iconColor,
                        modifier = Modifier.layoutId("icon_$i")
                    )

                    Text(
                        stringResource(item.text),
                        Modifier
                            .padding(start = Dimens.D2)
                            .layoutId("text_$i")
                    )
                }
            }
        }

    }

    ElevatedCard(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(state) },
                onLongClick = { onLongClick(state) },
            ),
    ) {
        Column(Modifier.padding(Dimens.D5)) {
            AnnotatedText(stringResource(R.string.rule_filter_package_name), state.packageName)
            filterItems.card()
            Spacer(Modifier.height(Dimens.D1))
            actionItems.card()
        }
    }
}