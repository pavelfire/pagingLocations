package com.example.android.codelabs.paging.experimental.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.withStyledAttributes
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.RocketDetailViewBinding

// https://www.youtube.com/watch?v=RhZLVoWPYJs&list=PLtNLwG5hHZ-I7YBaPLGnqQxSYIQx6_wJQ&index=5

class RocketDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CardView(context, attrs) {

    val binding = RocketDetailViewBinding.inflate(LayoutInflater.from(context), this)

    var title: String = ""
        set(value){
            field = value
            binding.rocketDetailTitleTv.text = value
        }

    var subTitle: String = ""
        set(value){
            field = value
            binding.rocketDetailSubtitleTv.text = value
        }

    init {
//        radius = dip(18).toFloat()
//        elevation = dip(24).toFloat()
        context.withStyledAttributes(attrs, R.styleable.RocketDetailView){
            title = getString(R.styleable.RocketDetailView_rdv_title) ?: ""
            subTitle = getString(R.styleable.RocketDetailView_rdv_subTitle) ?: ""
        }
    }
}