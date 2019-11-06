package com.devyk.common.ext

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.devyk.common.weiget.AppCompatAvatarImageView


fun AppCompatAvatarImageView.loadWithGlide(url: String, textPlaceHolder: Char, requestOptions: RequestOptions = RequestOptions().diskCacheStrategy(
    DiskCacheStrategy.RESOURCE)){
    textPlaceHolder.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }

        Glide.with(this)
            .load(url)
            .apply(requestOptions)
            .into(this)

}