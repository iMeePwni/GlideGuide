package com.example.guofeng.glideguide.app

import android.annotation.SuppressLint
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 * Created by guofeng on 2017/10/8.
 */
@GlideModule
class Module : AppGlideModule()

@SuppressLint("CheckResult")
@GlideExtension
object Extension {
    private val MINI_THUMB_SIZE = 100

    @GlideOption
    @JvmStatic
    fun miniThumb(requestOptions: RequestOptions) {
        requestOptions
                .fitCenter()
                .override(MINI_THUMB_SIZE)
    }

}