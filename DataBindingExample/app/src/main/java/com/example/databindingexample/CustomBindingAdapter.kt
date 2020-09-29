package com.example.databindingexample

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// 어뎁터에서 정한 문자열 그대로 xml에서 지젇해줘야된다.
// 여기서 app: bind_image 는 int형 값만 받을수 있다.
// 정한 인자에 맞게 사용해야된다.
@BindingAdapter("bind_image")
fun bindImage(view: ImageView, res: Int?){
    Glide.with(view.context)
        .load(res)
        .into(view)
}

@BindingAdapter("bind_image", "bind_error_image")
fun bindImage(view: ImageView, res: Int?, error: Drawable){
    val option = RequestOptions().error(error)

    Glide.with(view.context)
        .load(res)
        .apply(option)
        .into(view)
}