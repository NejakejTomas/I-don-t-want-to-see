package cz.kiec.idontwanttosee.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Visibility
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens
import cz.kiec.idontwanttosee.ui.navigation.AddRule
import cz.kiec.idontwanttosee.ui.navigation.ModifyRule
import cz.kiec.idontwanttosee.ui.theme.IDontWantToSeeTheme
import cz.kiec.idontwanttosee.ui.theme.Typography
import cz.kiec.idontwanttosee.uiState.RuleUiState
import cz.kiec.idontwanttosee.uiState.RulesUiState
import cz.kiec.idontwanttosee.viewmodel.RulesViewModel
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
private fun AnnotatedTextPreview() {
    IDontWantToSeeTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            AnnotatedText("Annotation text", "Text")
        }
    }
}

@Preview
@Composable
private fun RulePreview() {
    IDontWantToSeeTheme {
        Rule(
            Modifier, RuleUiState(
                0,
                "com.discord",
                ignoreOngoing = true,
                ignoreWithProgressBar = false,
                hideTitle = true,
                hideContent = false,
                hideLargeImage = true
            ),
            {}, {})
    }
}

@Preview
@Composable
private fun ExpandedRulePreview() {
    IDontWantToSeeTheme {
        ExpandedRule(
            Modifier, RuleUiState(
                0,
                "com.discord",
                ignoreOngoing = true,
                ignoreWithProgressBar = false,
                hideTitle = true,
                hideContent = false,
                hideLargeImage = true
            ),
            {}, {})
    }
}

@Preview
@Composable
private fun ExpandableRulePreview() {
    IDontWantToSeeTheme {
        ExpandableRule(
            Modifier, RuleUiState(
                0,
                "com.discord",
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

@Preview
@Composable
fun RuleDialogPreview() {
    RuleDialog({}, {}, {})
}

@Composable
private fun AnnotatedText(annotation: String, text: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = annotation,
            style = Typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = text,
        )
    }
}

@Composable
private fun Boolean.checkboxColor(): Color {
    return if (this) CheckboxDefaults.colors().checkedBoxColor
    else CheckboxDefaults.colors().disabledCheckedBoxColor
}

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
private fun Rule(
    modifier: Modifier = Modifier,
    state: RuleUiState,
    onLongClick: (RuleUiState) -> Unit,
    onClick: (RuleUiState) -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(state) },
                onLongClick = { onLongClick(state) },
            ),
    ) {
        Column(Modifier.padding(Dimens.D5)) {
            AnnotatedText(stringResource(R.string.package_name), state.packageName)
            Card(Modifier.fillMaxWidth()) {
                FlowRow(Modifier.padding(Dimens.D2)) {
                    Icon(
                        painterResource(R.drawable.ic_progressbar_ongoing),
                        contentDescription = stringResource(id = R.string.ignore_ongoing),
                        tint = state.ignoreOngoing.checkboxColor()
                    )
                    Icon(
                        painterResource(R.drawable.ic_progressbar),
                        contentDescription = stringResource(id = R.string.ignore_with_progressbar),
                        tint = state.ignoreWithProgressBar.checkboxColor()
                    )
                }
            }

            Spacer(Modifier.height(Dimens.D1))

            Card(Modifier.fillMaxWidth()) {
                FlowRow(Modifier.padding(Dimens.D2)) {
                    Icon(
                        painterResource(R.drawable.ic_title),
                        contentDescription = stringResource(id = R.string.hide_title),
                        tint = state.hideTitle.checkboxColor()
                    )
                    Icon(
                        painterResource(R.drawable.ic_text),
                        contentDescription = stringResource(id = R.string.hide_content),
                        tint = state.hideContent.checkboxColor()
                    )
                    Icon(
                        painterResource(R.drawable.ic_image),
                        contentDescription = stringResource(id = R.string.hide_image),
                        tint = state.hideLargeImage.checkboxColor()
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ExpandedRule(
    modifier: Modifier = Modifier,
    state: RuleUiState,
    onLongClick: (RuleUiState) -> Unit,
    onClick: (RuleUiState) -> Unit,
) {
    @Composable
    fun Entry(
        @StringRes text: Int,
        @DrawableRes icon: Int,
        iconColor: Color,
        modifier: Modifier = Modifier
    ) {
        Row(modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(icon),
                contentDescription = null,
                tint = iconColor
            )
            Spacer(Modifier.width(Dimens.D2))
            Text(stringResource(text))
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
            AnnotatedText(stringResource(R.string.package_name), state.packageName)
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(Dimens.D2)) {
                    Entry(
                        R.string.ignore_ongoing,
                        R.drawable.ic_progressbar_ongoing,
                        state.ignoreOngoing.checkboxColor()
                    )
                    Entry(
                        R.string.ignore_with_progressbar,
                        R.drawable.ic_progressbar,
                        state.ignoreWithProgressBar.checkboxColor()
                    )
                }
            }

            Spacer(Modifier.height(Dimens.D1))

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(Dimens.D2)) {
                    Entry(R.string.hide_title, R.drawable.ic_title, state.hideTitle.checkboxColor())
                    Entry(
                        R.string.hide_content,
                        R.drawable.ic_text,
                        state.hideContent.checkboxColor()
                    )
                    Entry(
                        R.string.hide_image,
                        R.drawable.ic_image,
                        state.hideLargeImage.checkboxColor()
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ExpandableRule(
    modifier: Modifier = Modifier,
    state: RuleUiState,
    isExpanded: Boolean,
    onLongClick: (RuleUiState) -> Unit,
    onClick: (RuleUiState) -> Unit,
) {
    data class Item(
        @StringRes val text: Int, @DrawableRes val icon: Int, val iconColor: Color,
    )

    val filterItems = listOf(
        Item(
            R.string.ignore_ongoing,
            R.drawable.ic_progressbar_ongoing,
            state.ignoreOngoing.checkboxColor()
        ), Item(
            R.string.ignore_with_progressbar,
            R.drawable.ic_progressbar,
            state.ignoreWithProgressBar.checkboxColor()
        )
    )

    val actionItems = listOf(
        Item(R.string.hide_title, R.drawable.ic_title, state.hideTitle.checkboxColor()),
        Item(R.string.hide_content, R.drawable.ic_text, state.hideContent.checkboxColor()),
        Item(R.string.hide_image, R.drawable.ic_image, state.hideLargeImage.checkboxColor()),
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
                animateChanges = true
            ) {
                forEachIndexed { i, item ->
                    Icon(
                        painter = painterResource(item.icon),
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
            AnnotatedText(stringResource(R.string.package_name), state.packageName)
            filterItems.card()
            Spacer(Modifier.height(Dimens.D1))
            actionItems.card()

        }
    }
}

@Composable
private fun NewRuleButton(
    onNewRule: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = onNewRule
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_rule)
        )
    }
}

@Composable
private fun RuleDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onModify: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(Modifier) {
            Column(
                Modifier
                    .padding(Dimens.D5)
                    .width(IntrinsicSize.Max)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss()
                        onModify()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.ic_edit), null)
                        Spacer(modifier = Modifier.width(Dimens.D5))
                        Text(stringResource(R.string.edit))
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss()
                        onDelete()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.ic_delete), null)
                        Spacer(modifier = Modifier.width(Dimens.D5))
                        Text(stringResource(R.string.delete))
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.ic_cancel), null)
                        Spacer(modifier = Modifier.width(Dimens.D5))
                        Text(stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Composable
fun RulesScreen(
    setScreenDecors: @Composable (@Composable ScreenDecors.() -> ScreenDecors) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    rulesViewModel: RulesViewModel = koinViewModel(),
) {
    val uiState by rulesViewModel.uiState.collectAsStateWithLifecycle(RulesUiState())

    setScreenDecors {
        copy(actions = {
            NewRuleButton(onNewRule = { navController.navigate(AddRule) })
        })
    }

    LazyColumn(modifier) {
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
                onLongClick = rulesViewModel::onLongClick,
                onClick = rulesViewModel::onClick,
                isExpanded = uiState.expandedRules.contains(rule)
            )
        }
    }

    uiState.editDialogForRule?.let {
        RuleDialog(onDismiss = rulesViewModel::hideDialog, onDelete = {
            rulesViewModel.deleteRule(it)
        }, onModify = {
            navController.navigate(ModifyRule(it.id))
        })
    }
}