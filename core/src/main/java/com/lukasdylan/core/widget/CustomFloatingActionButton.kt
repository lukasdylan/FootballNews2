package com.lukasdylan.core.widget

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomFloatingActionButton : FloatingActionButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private var currentMatrix: Matrix? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        currentMatrix = imageMatrix
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        imageMatrix = currentMatrix
    }
}
