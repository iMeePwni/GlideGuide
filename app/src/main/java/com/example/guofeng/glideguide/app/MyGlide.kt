package com.example.guofeng.glideguide.app

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.guofeng.glideguide.R

/**
 * Created by guofeng on 2017/10/8.
 */
/*
应用程序（Application）需要：
1）恰当地添加一个AppGlideModule实现。
2）（可选）添加一个或多个LibraryGlideModule实现。
3）给上述两种实现添加@GlideModule注解。
4）添加对Glide地注解解析器地依赖。
5）在proguard中，添加对AppGlideModule的keep。
所有应用都必须添加一个AppGlideModule实现。这是一个信号，它会让Glide的注解解析器生成一个单一的所有已发现的LibraryGlideModules的联合类。
可以通过注解@Excludes 解决冲突或避免不必要的依赖。
 */
@GlideModule
class Module : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 设置内存缓存
        builder.setMemoryCache(LruResourceCache(20 * 1024 * 1024))
        // 设置磁盘缓存
        builder.setDiskCache(ExternalCacheDiskCacheFactory(context, context.getString(R.string.app_name),100 * 1024 * 1024))
        // 设置默认请求选项
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
        // 未捕获一场策略（UncaughtThrowableStrategy）
        // 设置日志级别
        builder.setLogLevel(Log.VERBOSE)
    }

    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
        super.registerComponents(context, glide, registry)
    }
    // 完全禁用清单解析，可以改善Glide的初始启动时间，并避免尝试解析元数据时的一些潜在问题。
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}

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