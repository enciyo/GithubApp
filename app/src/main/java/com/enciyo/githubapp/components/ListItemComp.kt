package com.enciyo.githubapp.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.CompListItemBinding

/**
 * @Sample
 *     <com.enciyo.githubapp.ui.components.ListItemComp
 *         android:layout_width="match_parent"
 *         app:condition="twoLine"
 *         app:headline="Mustafa K "
 *         app:supportingText="asdsadas"
 *         app:leadingIcon="@drawable/vc_github_mark"
 *         app:trailing="icon"
 *         app:leading="icon"
 *         app:trailingIcon="@drawable/vc_star_filled"
 *         app:trailingSupportingText="1000+"
 *         android:layout_height="wrap_content"/>
 */

class ListItemComp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding by viewBinding(CompListItemBinding::bind)

    init {
        inflate(context, R.layout.comp_list_item, this)
    }

    var leading: Leading = Leading.None
        set(value) {
            field = value
            updateByLeading()
        }

    var condition: Condition = Condition.OneLine
        set(value) {
            field = value
            updateByCondition()
        }

    var trailing: Trailing = Trailing.None
        set(value) {
            field = value
            updateByTrailing()
        }

    var leadingImg: Drawable? = null
        set(value) {
            field = value
            check(leading == Leading.Image || value == null) { "Leading type is ${leading.name}" }
            binding.leadingImage.setImageDrawable(value)
        }

    var leadingImgUrl:String? = null
        set(value) {
            field = value
            check(leading == Leading.Image || value.isNullOrEmpty()) { "Leading type is ${leading.name}" }
            Glide.with(this).load(value).into(binding.leadingImage)
        }


    var leadingIcon: Drawable? = null
        set(value) {
            field = value
            check(leading == Leading.Icon || value == null) { "Leading type is ${leading.name}" }
            binding.leadingIcon.setImageDrawable(value)
        }

    var headline: CharSequence = ""
        set(value) {
            field = value
            binding.conditionFirstLine.text = value
        }

    var supportingText: CharSequence? = null
        set(value) {
            field = value
            check(condition == Condition.TwoLine || value.isNullOrEmpty()) { "Condition type is ${condition.name}" }
            binding.conditionSecondLine.text = value
            binding.conditionSecondLine.isVisible = value.isNullOrEmpty().not()
        }


    var trailingSupportingText: CharSequence? = null
        set(value) {
            field = value
            binding.trailingSupportText.isVisible = value != null
            binding.trailingSupportText.text = value
        }

    var trailingIcon: Drawable? = null
        set(value) {
            field = value
            check(trailing == Trailing.Icon || value == null) { "Trailing type is ${trailing.name}" }
            binding.trailingIcon.setImageDrawable(value)
        }




    init {
        orientation = HORIZONTAL
        minimumHeight = resources.getDimensionPixelSize(R.dimen.comp_list_item_min_height)
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        with(context.obtainStyledAttributes(attrs, R.styleable.ListItemComp)) {
            leading = getEnum(
                R.styleable.ListItemComp_leading,
                if (isInEditMode) Leading.Icon else Leading.None
            )
            condition = getEnum(
                R.styleable.ListItemComp_condition,
                if (isInEditMode) Condition.TwoLine else Condition.OneLine
            )
            trailing = getEnum(
                R.styleable.ListItemComp_trailing,
                if (isInEditMode) Trailing.Icon else Trailing.Icon
            )
            leadingImg = getDrawable(R.styleable.ListItemComp_leadingImg)
            leadingIcon = getDrawable(R.styleable.ListItemComp_leadingIcon)
            headline = getString(R.styleable.ListItemComp_headline).orEmpty()
            supportingText = getString(R.styleable.ListItemComp_supportingText).orEmpty()
            trailingSupportingText = getString(R.styleable.ListItemComp_trailingSupportingText)
            trailingIcon = getDrawable(R.styleable.ListItemComp_trailingIcon)
            recycle()
        }

        updateByLeading()
        updateByCondition()
    }


    private fun updateByLeading() {
        binding.leadingIcon.isVisible = leading == Leading.Icon
        binding.leadingImage.isVisible = leading == Leading.Image
    }

    private fun updateByCondition() {
        binding.conditionSecondLine.isVisible = condition == Condition.TwoLine
    }

    private fun updateByTrailing() {
        binding.trailingIcon.isVisible = trailing == Trailing.Icon
    }


    enum class Leading {
        None,
        Image,
        Icon
    }

    enum class Condition {
        OneLine,
        TwoLine
    }

    // Maybe I can add some more features
    enum class Trailing {
        None,
        Icon
    }

    fun setTrailingIconClickListener(click: (View) -> Unit) {
        binding.trailingIcon.addBackgroundCircleRipple()
        binding.trailingIcon.setOnClickListener(click)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        addBackgroundRipple()
        super.setOnClickListener(l)
    }

}
