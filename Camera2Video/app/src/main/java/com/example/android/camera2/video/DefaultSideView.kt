package com.example.android.camera2.video

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import com.lzf.easyfloat.interfaces.OnTouchRangeListener
import com.lzf.easyfloat.widget.BaseSwitchView

class DefaultSideView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseSwitchView(context, attrs, defStyleAttr) {

    private lateinit var paint: Paint
    private var path = Path()
    private var width = 0f
    private var height = 0f
    private var region = Region()
    private val totalRegion = Region()
    private var inRange = false
    private var zoomSize = 18f
    private var listener: OnTouchRangeListener? = null

    override fun setTouchRangeListener(event: MotionEvent, listener: OnTouchRangeListener?) {
        this.listener = listener
        initTouchRange(event)
    }

    private fun initTouchRange(event: MotionEvent): Boolean {
        val location = IntArray(2)
        // 获取在整个屏幕内的绝对坐标
        getLocationOnScreen(location)
        val currentInRange = region.contains(
            event.rawX.toInt() - location[0], event.rawY.toInt() - location[1]
        )
        if (currentInRange != inRange) {
            inRange = currentInRange
            invalidate()
        }
        listener?.touchInRange(currentInRange, this)
        if (event.action == MotionEvent.ACTION_UP && currentInRange) {
            listener?.touchUpInRange()
        }
        return currentInRange
    }

}