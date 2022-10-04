package com.noobcode.otpview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet

class OTPView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    //------------------------variables------------------------
    var otpLength: Int = 6
    var borderThickness: Int = 1
    var mPaint: Paint
    var borderColor: Int = Color.BLACK
    var spaceBetween: Float = 24f
    var spaceBetweenWithDensity: Float = 24f
    var lineSpacing: Float = 8f
    var lineSpacingWithDensity: Float = 8f
    private var OtpListener: OTPListener? = null
    private var mClickListener: OnClickListener? = null
    private var otpLayoutColor: Int = this.currentTextColor
    private var otpLayoutType: Int = 1

    //------------------------listeners------------------------
    private var mTextWatcher: TextWatcher? = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(p0?.length == otpLength){
                OtpListener?.onOTPCompleted(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            //
        }

    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.OTPView
        )
        try {
            otpLength = typedArray.getInteger(R.styleable.OTPView_otpLength, 6)
            borderThickness = typedArray.getInteger(R.styleable.OTPView_borderThickness, 2)
            spaceBetween = typedArray.getFloat(R.styleable.OTPView_spaceBetween, 24f)
            otpLayoutColor = typedArray.getInteger(R.styleable.OTPView_otpLayoutColor, this.currentTextColor)
            otpLayoutType = typedArray.getInteger(R.styleable.OTPView_otpLayoutType, 1)

            val multi = context.resources.displayMetrics.density
            mPaint = Paint(paint)
            mPaint.strokeWidth = multi * borderThickness
            mPaint.color = borderColor
            setBackgroundResource(0)
            spaceBetweenWithDensity = multi * spaceBetween //convert to pixels for our density

            lineSpacingWithDensity = multi * lineSpacing //convert to pixels for our density

            this.inputType = InputType.TYPE_CLASS_NUMBER
            this.isCursorVisible = false

            setMaxLength(otpLength)

            super.setOnClickListener { v -> // When tapped, move cursor to end of text.
                setSelection(text!!.length)
                mClickListener?.onClick(v)
            }

            super.addTextChangedListener(mTextWatcher)
        }
        finally {
            typedArray.recycle()
        }
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
        var top: Float = 120f

        // Text Width
        val text = text
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)
        mPaint.color = otpLayoutColor
        if(otpLayoutType == 1){
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
        }else{
            mPaint.style = Paint.Style.STROKE
            for (i in 0 until otpLength) {
                canvas.drawRect(startX, top, startX + mCharSize, bottom, mPaint)
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

    //------------------------setters------------------------
    fun setOTPListener(listener: OTPListener) {
        OtpListener = listener
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: android.view.ActionMode.Callback?) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    private fun setMaxLength(length: Int) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = LengthFilter(length)
        super.setFilters(filterArray)
    }

    //------------------------callbacks------------------------
    interface OTPListener {
        fun onOTPCompleted(otp: String)
    }


}