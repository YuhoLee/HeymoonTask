package com.project.presentation.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.presentation.R
import com.project.presentation.base.BaseActionHeader
import com.project.presentation.detail.item.CollectionDetailItem
import com.project.presentation.theme.gray400
import com.project.presentation.theme.gray900
import com.project.presentation.theme.heymoonTypography
import com.project.presentation.theme.white

class CollectionDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 이전 화면에 대한 경로
        val prevRoute = arguments?.getString("prevRoute") ?: ""
        // 이전 화면에서 가져온 item
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("item", CollectionDetailItem::class.java)
        } else {
            arguments?.getParcelable("item")
        }
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    CollectionDetailScreen(
                        prevRoute = prevRoute, item = item,
                        onNext = {
                            val navController = findNavController()
                            navController.navigate(R.id.action_collection_detail_to_favorites)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CollectionDetailScreen(
    prevRoute: String,
    item: CollectionDetailItem?,
    viewModel: CollectionDetailViewModel = hiltViewModel(),
    onNext: () -> Unit
) {
    if (item != null) {
        val state = viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(state.value.isAddFavoriteSuccess) {
            if(state.value.isAddFavoriteSuccess){
                onNext()
            }
        }

        var isFavoritePopup by remember {
            mutableStateOf(false)
        }

        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(white)
            ) {
                CollectionDetailHeader(
                    text = item.productNameKo,
                    prevRoute = prevRoute,
                    onFavoriteCollection = {
                        isFavoritePopup = true
                    }
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    item {
                        CollectionDetailImg(
                            imgUrl = item.mainImgUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 400.dp)
                                .aspectRatio(1f)
                        )
                    }

                    item {
                        CollectionDetailInfo(
                            modifier = Modifier.padding(top = 30.dp, bottom = 30.dp),
                            item = item
                        )
                    }
                }
            }

            if (isFavoritePopup) {
                FavoritePopup(
                    modifier = Modifier.fillMaxSize(),
                    onAdd = {
                        viewModel.onEvent(CollectionDetailEvent.AddFavoriteCollection(item = item))
                        isFavoritePopup = false
                    },
                    onCancel = {
                        isFavoritePopup = false
                    }
                )
            }
        }
    }
}

@Composable
fun CollectionDetailHeader(
    text: String,
    prevRoute: String,
    modifier: Modifier = Modifier,
    onFavoriteCollection: () -> Unit
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            BaseActionHeader(context).apply {
                setOnBackPressed {

                }
                setOnFavoritePressed {
                    if (prevRoute == "Search") {
                        onFavoriteCollection()
                    }
                }
            }
        },
        update = {
            it.setTitle(title = text)
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollectionDetailImg(
    imgUrl: String,
    modifier: Modifier = Modifier
) {
    GlideImage(
        modifier = modifier, model = imgUrl,
        contentDescription = "CollectionDetailImg_GlideImage_${imgUrl}",
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun CollectionDetailInfo(
    item: CollectionDetailItem,
    modifier: Modifier = Modifier
) {
    val itemGroup: List<Pair<String, String>> = listOf(
        Pair(stringResource(R.string.text_detail_writer), item.maker),
        Pair(stringResource(R.string.text_detail_year_created), item.manufactureYear),
        Pair(stringResource(R.string.text_detail_category), item.productClass),
        Pair(stringResource(R.string.text_detail_dimensions), item.whSize),
        Pair(stringResource(R.string.text_detail_year_collected), item.collectYear),
        Pair(stringResource(R.string.text_detail_material_techniques), item.materialTechnic)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.productNameKo,
            color = gray900,
            fontSize = 22.sp,
            style = heymoonTypography.textSemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.productNameEn,
            color = gray900,
            fontSize = 14.sp,
            style = heymoonTypography.textSemiBold
        )

        Spacer(modifier = Modifier.height(30.dp))

        itemGroup.forEach {
            CollectionDetailInfoItemRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                category = it.first,
                value = it.second
            )
        }
    }
}

@Composable
fun CollectionDetailInfoItemRow(
    category: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = category,
            color = gray400,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            style = heymoonTypography.textSemiBold
        )

        Text(
            modifier = Modifier.weight(1f),
            text = value,
            color = gray400,
            textAlign = TextAlign.End,
            fontSize = 14.sp,
            style = heymoonTypography.textSemiBold
        )
    }
}

@Composable
fun FavoritePopup(
    modifier: Modifier = Modifier,
    onAdd: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = gray900.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(white)
                .align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 30.dp, bottom = 15.dp),
                text = stringResource(R.string.text_detail_popup_favorite_add_title),
                style = heymoonTypography.textRegular,
                textAlign = TextAlign.Center,
                color = gray900,
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onCancel()
                        }
                        .padding(vertical = 20.dp),
                    text = stringResource(R.string.text_common_no),
                    style = heymoonTypography.textSemiBold,
                    textAlign = TextAlign.Center,
                    color = gray900,
                    fontSize = 14.sp
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAdd()
                        }
                        .padding(vertical = 20.dp),
                    text = stringResource(R.string.text_common_add),
                    style = heymoonTypography.textSemiBold,
                    textAlign = TextAlign.Center,
                    color = gray900,
                    fontSize = 14.sp
                )
            }
        }
    }
}