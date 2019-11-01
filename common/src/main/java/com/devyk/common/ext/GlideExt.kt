package com.devyk.common.ext

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devyk.common.weiget.AppCompatAvatarImageView

/**
 * Created by benny on 7/4/17.
 */
fun AppCompatAvatarImageView.loadWithGlide(url: String, textPlaceHolder: Char, requestOptions: RequestOptions = RequestOptions()){
    textPlaceHolder.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }

        Glide.with(this)
            .load(url)
            .apply(requestOptions)
            .into(this)

}