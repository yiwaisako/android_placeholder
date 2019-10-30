package jp.co.yiwaisako.android_placeholder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

class Placeholder(private val logo: Drawable, @ColorRes private val bg: Int) {

    companion object {
        private var placeholder: Placeholder? = null

        fun with(context: Context, @DrawableRes logoDrawableRes: Int, @ColorRes bgColorInt: Int): Placeholder {
            val logo = ContextCompat.getDrawable(context, logoDrawableRes)
            logo ?: throw IllegalArgumentException()
            return Placeholder(logo, bgColorInt).apply {
                placeholder = this
            }
        }

        fun createPlaceHolderFromPixel(c: Context, wPixel: Int, hPixel: Int): Drawable {
            val layers = arrayOf(
                createBg(c, wPixel, hPixel),
                createAppNameLogo(c, wPixel, hPixel)
            )
            return LayerDrawable(layers)
        }

        private fun createBg(c: Context, w: Int, h: Int): Drawable {
            val placeholder = placeholder
            placeholder ?: return GradientDrawable()

            return GradientDrawable().also {
                it.shape = GradientDrawable.RECTANGLE
                it.setColor(ContextCompat.getColor(c, placeholder.bg))
                it.setSize(w, h)
            }
        }

        private fun createAppNameLogo(c: Context, w: Int, h: Int): Drawable {
            // アプリ名のDrawableを作成
            // サムネイルの横サイズが「マンガpark」の画像よりも狭かった場合はサイズを調整する
            val placeholder = placeholder
            placeholder ?: return GradientDrawable()

            val bitmap = placeholder.logo.toBitmap()
            val bitmapWidth = bitmap.width
            val bitmapHeight = bitmap.height

            val isOver = w < bitmapWidth
            val bigLogoWidth = bitmapWidth * 2 / 3
            val bigLogoHeight = bitmapHeight * 2 / 3
            val smallLogoWidth = w * 4 / 5

            val appNameWidthPixel = if (isOver) smallLogoWidth else bigLogoWidth
            val appNameHeightPixel =
                if (isOver) bitmapHeight * smallLogoWidth / w else bigLogoHeight
            val appName = BitmapDrawable(
                c.resources,
                Bitmap.createScaledBitmap(bitmap, appNameWidthPixel, appNameHeightPixel, true)
            )
            appName.gravity = Gravity.CENTER
            return appName
        }
    }
}
