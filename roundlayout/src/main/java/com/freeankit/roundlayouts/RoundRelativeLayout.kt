package com.freeankit.roundlayouts

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.widget.RelativeLayout
import android.util.AttributeSet

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 03/04/2018 (MM/DD/YYYY)
 */
class RoundRelativeLayout@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        RelativeLayout(context, attrs, defStyleAttr) {
    private val canvasRounder: CanvasRounder

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout, 0, 0)
        val cornerRadius = array.getDimension(R.styleable.RoundRelativeLayout_corner_radius, 0f)
        array.recycle()
        canvasRounder = CanvasRounder(cornerRadius)
        updateOutlineProvider(cornerRadius)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onSizeChanged(currentWidth: Int, currentHeight: Int, oldWidth: Int, oldheight: Int) {
        super.onSizeChanged(currentWidth, currentHeight, oldWidth, oldheight)
        canvasRounder.updateSize(currentWidth, currentHeight)
    }

    override fun draw(canvas: Canvas) = canvasRounder.round(canvas) { super.draw(it) }

    override fun dispatchDraw(canvas: Canvas) = canvasRounder.round(canvas) { super.dispatchDraw(it)}

    fun setCornerRadius(cornerRadius: Float) {
        canvasRounder.cornerRadius = cornerRadius
        updateOutlineProvider(cornerRadius)
        invalidate()
    }
}