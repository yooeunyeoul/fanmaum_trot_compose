package com.trotfan.trot.ui.home.mypage.modify.component

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.mypage.home.MyPageViewModel
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickableSingle
import java.io.File

@Composable
fun ProfileImgModify(
    modifier: Modifier,
    viewModel: MyPageViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    var profileUri by remember { mutableStateOf<Uri?>(null) }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            profileUri = result.uriContent
            val filePath = result.getUriFilePath(context, true)
            val image = filePath?.let { File(filePath) }
            image?.let {
                Log.d("file", it.path + "//" + it.length() + "bytes")
                viewModel.postUserProfile(image = image)
            }
        }
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val cropImageOptions = CropImageOptions()
            cropImageOptions.imageSourceIncludeCamera = false
            cropImageOptions.aspectRatioX = 1
            cropImageOptions.aspectRatioY = 1
            cropImageOptions.fixAspectRatio = true
            cropImageOptions.outputCompressFormat = CompressFormat.JPEG
            cropImageOptions.toolbarColor = Gray900.hashCode()
            cropImageOptions.activityBackgroundColor = Gray900.hashCode()
            cropImageOptions.cropMenuCropButtonTitle = "완료"
            val cropOptions = CropImageContractOptions(uri, cropImageOptions)
            imageCropLauncher.launch(cropOptions)
        }

    Box(modifier = modifier
        .size(136.dp)
        .clickableSingle {
            imagePickerLauncher.launch("image/*")
        }) {
        Box(
            modifier = Modifier
                .size(136.dp)
                .border(2.dp, Gray200, CircleShape)
                .clip(CircleShape)
                .background(Gray100)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mypage_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.Center)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profileUri)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(CircleShape)
            )
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    brush = gradient03,
                    shape = CircleShape,
                )
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_camera),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Preview
@Composable
fun ProfileImgModifyPreview() {
    FanwooriTheme {
        ProfileImgModify(Modifier)
    }
}