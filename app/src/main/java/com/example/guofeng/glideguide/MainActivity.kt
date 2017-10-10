package com.example.guofeng.glideguide

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.guofeng.glideguide.app.GlideApp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/s%3D500/sign=515d1da4bf99a9013f355b362d940a58/6a63f6246b600c33c9291f781d4c510fd9f9a12a.jpg"
        load.setOnClickListener {
            GlideApp.with(this)
                    .load(url)
                    // 占位符。请求时显示，如果请求失败、或请求的url/model为null并且没有设置error和fallback,则占位符将被持续显示。
                    .placeholder(R.mipmap.ic_launcher)
                    // 错误符。 在请求永久失败时显示。同样也会在请求的url/model为null,并且没有设置fallback 时显示。
                    .error(R.mipmap.ic_launcher_foreground)
                    // 后备回调符。在请求的url/model为null时显示。null也可能表示这个原数据根本就是不合法的，或者取不到的。
                    .fallback(R.mipmap.ic_launcher_round)
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

    }

}
