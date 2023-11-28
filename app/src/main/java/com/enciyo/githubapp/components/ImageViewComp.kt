package com.enciyo.githubapp.components

import android.content.Context
import android.util.AttributeSet
import com.enciyo.githubapp.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment


class ImageViewComp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) : ShapeableImageView(context, attrs, defStyle) {


    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.ImageViewComp)) {
            val isCircle = getBoolean(R.styleable.ImageViewComp_isCircle, false)
            if (isCircle) applyCircle()
            recycle()
        }
    }

    private fun applyCircle() {
        shapeAppearanceModel = shapeAppearanceModel.toBuilder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()
    }

}