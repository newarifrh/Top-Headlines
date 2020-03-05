package dev.blank.topheadline.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "news_table",
    indices = [Index(value = ["title"], unique = true)]
)
class News {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("title")
    var title: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("url")
    var url: String = ""

    @SerializedName("urlToImage")
    var urlToImage: String = ""

    @SerializedName("publishedAt")
    var publishedAt: String = ""


    companion object {
        @JvmStatic
        @BindingAdapter("bind:urlToImage")
        fun loadImage(view: ImageView, url: String) {
            val requestOptions =
                RequestOptions.placeholderOf(dev.blank.topheadline.R.color.grey)
                    .error(dev.blank.topheadline.R.color.grey).fitCenter()
            Glide.with(view).load(url).apply(requestOptions).into(view)

        }
    }


}