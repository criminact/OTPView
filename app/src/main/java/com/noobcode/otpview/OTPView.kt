package com.noobcode.otpview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet

class OTPView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatEditText(context, attrs) {

//    var layoutType: Int = 0
    var otpLength: Int = 6
//    var autoSubmit: Boolean = true
    var borderThickness: Int = 1
    lateinit var mPaint: Paint
    var borderColor: Int = Color.BLACK
    var spaceBetween: Float = 24f
    var spaceBetweenWithDensity: Float = 24f
    var lineSpacing: Float = 8f
    var lineSpacingWithDensity: Float = 8f
    private var mClickListener: OnClickListener? = null

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.OTPView
        )
        try {
            otpLength = typedArray.getInteger(R.styleable.OTPView_otpLength, 6)
//            layoutType = typedArray.getInteger(R.styleable.OTPView_layoutType, 0)
//            autoSubmit = typedArray.getBoolean(R.styleable.OTPView_autoSubmit, true)
            borderThickness = typedArray.getInteger(R.styleable.OTPView_borderThickness, 2)
//            borderColor = typedArray.getResourceId(R.styleable.OTPView_layoutColor, Color.BLACK)
            spaceBetween = typedArray.getFloat(R.styleable.OTPView_spaceBetween, 24f)

            val multi = context.resources.displayMetrics.density
            mPaint = Paint(paint)
            mPaint.strokeWidth = multi * borderThickness
            mPaint.color = borderColor
            setBackgroundResource(0)
            spaceBetweenWithDensity = multi * spaceBetween //convert to pixels for our density

            lineSpacingWithDensity = multi * lineSpacing //convert to pixels for our density

            this.inputType = InputType.TYPE_CLASS_NUMBER
            this.isCursorVisible = false

            super.setOnClickListener { v -> // When tapped, move cursor to end of text.
                setSelection(text!!.length)
                mClickListener?.onClick(v)
            }
        }
        finally {
            typedArray.recycle()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: android.view.ActionMode.Callback?) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    override fun onDraw(canvas: Canvas) {
        val availableWidth = width - paddingRight - paddingLeft
        val mCharSize: Float = if (spaceBetweenWithDensity < 0) {
            availableWidth / (otpLength.toFloat() * 2 - 1)
        } else {
            (availableWidth - spaceBetweenWithDensity * (otpLength.toFloat() - 1)) / otpLength.toFloat()
        }
        var startX: Float = paddingLeft.toFloat()
        val bottom: Float = (height - paddingBottom).toFloat()

        // Text Width
        val text = text
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)
        mPaint.color = this.currentTextColor
        for (i in 0 until otpLength) {
            canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mPaint)
            if (getText()!!.length > i) {
                val middle = startX + mCharSize / 2
                paint.color = this.currentTextColor
                canvas.drawText(
                    text, i, i + 1, middle - textWidths[0] / 2, bottom - lineSpacingWithDensity,
                    paint
                )
            }
            if (spaceBetweenWithDensity < 0) {
                startX += (mCharSize * 2).toInt()
            } else {
                startX += mCharSize + spaceBetweenWithDensity
            }
        }
    }
}