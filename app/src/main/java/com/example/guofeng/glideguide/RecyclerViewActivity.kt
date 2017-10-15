package com.example.guofeng.glideguide

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.example.guofeng.glideguide.app.GlideApp
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.item_single_image.view.*
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val myUrls = arrayListOf(
                "http://img9.zol.com.cn/dp_800/442/441917.jpg",
                "http://img9.zol.com.cn/dp_800/442/431917.jpg",
                "http://img9.zol.com.cn/dp_800/442/441617.jpg",
                "http://img9.zol.com.cn/dp_800/442/441927.jpg",
                "http://img9.zol.com.cn/dp_800/442/441917.jpg",
                "http://img9.zol.com.cn/dp_800/442/441987.jpg",
                "http://img9.zol.com.cn/dp_800/442/441287.jpg",
                "http://img9.zol.com.cn/dp_800/442/441917.jpg",
                "http://img9.zol.com.cn/dp_800/442/442507.jpg",
                "http://img9.zol.com.cn/dp_800/442/441317.jpg")
        val sizeProvider = FixedPreloadSizeProvider<String>(1920, 300)
        val modelProvider: ListPreloader.PreloadModelProvider<String> = object : ListPreloader.PreloadModelProvider<String> {
            override fun getPreloadRequestBuilder(item: String?): RequestBuilder<*> {
                return GlideApp.with(this@RecyclerViewActivity).load(item)
            }

            override fun getPreloadItems(position: Int): MutableList<String> {
                val url = myUrls[position]
                if (TextUtils.isEmpty(url))
                    return Collections.emptyList()
                else
                    return Collections.singletonList(url)
            }
        }
        val recyclerViewPreLoader = RecyclerViewPreloader<String>(this, modelProvider, sizeProvider, 2)
        with(recycler_view) {
            addOnScrollListener(recyclerViewPreLoader)
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MyAdapter(this@RecyclerViewActivity, myUrls)
        }
    }

    class MyAdapter(val context: Context,
                    var list: List<String>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
                = MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_image, parent, false))

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            GlideApp.with(context)
                    .load(list[position])
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            Log.d("**(", "fail")
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            Log.d("**(", "fail")
                            return false
                        }
                    })
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_foreground)
                    .into(holder.itemView.image_view)
        }

        override fun getItemCount() = list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
