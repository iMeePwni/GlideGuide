package com.example.guofeng.glideguide

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.guofeng.glideguide.app.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/s%3D500/sign=515d1da4bf99a9013f355b362d940a58/6a63f6246b600c33c9291f781d4c510fd9f9a12a.jpg"
        load.setOnClickListener {
            GlideApp.with(this)
                    .load(url)
                    // 从性能考虑禁用交叉淡入好，但会导致占位符存在在请求到图片后面。
                    .transition(DrawableTransitionOptions.withCrossFade())
                    // 占位符。请求时显示，如果请求失败、或请求的url/model为null并且没有设置error和fallback,则占位符将被持续显示。
                    .placeholder(R.mipmap.ic_launcher)
                    // 错误符。 在请求永久失败时显示。同样也会在请求的url/model为null,并且没有设置fallback 时显示。
                    .error(R.mipmap.ic_launcher_foreground)
                    // 后备回调符。在请求的url/model为null时显示。null也可能表示这个原数据根本就是不合法的，或者取不到的。
                    .fallback(R.mipmap.ic_launcher_round)
                    // 注意多重变换 每个transform()调用，或任何特定转换方法fitCenter()、centerCrop()、bitmapTransform()
                    // 等等的调用会替换之前的变换。
                    // 如果你想在单次加载中应用多个变换，请使用MultiTransformation类。
                    .transform(FitCenter())
                    .into(imageView)
        }
        custom_load.setOnClickListener {
            GlideApp.with(this)
                    .asBitmap()
                    .load(url)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            if (resource == null)
                                return
                            val scaleX: Float = resources.displayMetrics.widthPixels * 1f / resource.width
                            val matrix = Matrix()
                            matrix.postScale(scaleX, scaleX)
                            val bitmap = Bitmap.createBitmap(resource, 0, 0, resource.width, resource.height, matrix, false)
                            imageView.setImageBitmap(bitmap)
                        }
                    })
        }
        listener_load.setOnClickListener {
            GlideApp.with(this)
                    .load(url)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            Toast.makeText(this@MainActivity, "Drawable is ready", Toast.LENGTH_SHORT).show()
                            return false
                        }

                    })
                    .into(imageView)
        }
        extension_load.setOnClickListener {
            GlideApp.with(this)
                    .load(url)
                    .miniThumb()
                    .into(imageView)
        }
        request_load.setOnClickListener {
            Glide.with(this)
                    .load(url)
                    .apply(RequestOptions.centerCropTransform())
                    // apply() 方法可以被调用多次，因此 RequestOption 可以被组合使用。
                    // 如果 RequestOptions 对象之间存在相互冲突的设置，那么只有最后一个被应用的 RequestOptions 会生效。
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView)
        }
        var target: Target<Drawable> by Delegates.notNull<Target<Drawable>>()
        transform_load.setOnClickListener {
            target = GlideApp.with(this)
                    .load(url)
                    // 不同于RequestOptions，TransitionOptions是特定资源类型独有的，
                    // 你能使用的变换取决于你让Glide加载哪种类型的资源。
                    /*
                    在Glide中，图像可能从四个地方中的任何一个位置加载出来：
                    1）Glide 的内存缓存
                    2）Glide 的磁盘缓存
                    3) 设备本地可用的一个源文件或Uri
                    4) 仅远程可用的一个源Url或Uri
                    如果图像从Glide的内存缓存中加载出来，Glide的内置过渡将不会执行。
                    然而，在另外三种场景下，Glide的内置过渡都会被执行。
                     */
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .into(imageView)
        }
        cancel_target.setOnClickListener {
            Glide.with(this).clear(target)
        }
    }

}
