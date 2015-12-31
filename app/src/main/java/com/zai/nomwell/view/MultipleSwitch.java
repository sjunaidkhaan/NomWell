package com.zai.nomwell.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zai.nomwell.R;

/**
 * TODO: document your custom view class.
 */
public class MultipleSwitch extends View {

    private String switchText; // TODO: use a default from R.string...
    private int switchBackground = Color.RED; // TODO: use a default from R.color...
    private int numOfSwitches = 0; // TODO: use a default from R.dimen...
    private Drawable switchDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public MultipleSwitch(Context context) {
        super(context);
        init(null, 0);
    }

    public MultipleSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MultipleSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MultipleSwitch, defStyle, 0);

        switchText = a.getString(
                R.styleable.MultipleSwitch_switchText);
        switchBackground = a.getColor(
                R.styleable.MultipleSwitch_switchBackground,
                switchBackground);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        numOfSwitches = a.getInt(
                R.styleable.MultipleSwitch_numOfSwitches,
                numOfSwitches);

        if (a.hasValue(R.styleable.MultipleSwitch_switchDrawable)) {
            switchDrawable = a.getDrawable(
                    R.styleable.MultipleSwitch_switchDrawable);
            switchDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(numOfSwitches);
        mTextPaint.setColor(switchBackground);
        mTextWidth = mTextPaint.measureText(switchText);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(switchText,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (switchDrawable != null) {
            switchDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            switchDrawable.draw(canvas);
        }
    }

    /**
     * Gets the switch string attribute value.
     *
     * @return The switch string attribute value.
     */
    public String getSwitchText() {
        return switchText;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param switchText The example string attribute value to use.
     */
    public void setSwitchText(String switchText) {
        this.switchText = switchText;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the SwitchBackground color attribute value.
     *
     * @return The SwitchBackground color attribute value.
     */
    public int getSwitchBackground() {
        return switchBackground;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param switchBackground The example color attribute value to use.
     */
    public void setSwitchBackground(int switchBackground) {
        this.switchBackground = switchBackground;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public int getNumOfSwitches() {
        return numOfSwitches;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param numOfSwitches The example dimension attribute value to use.
     */
    public void setNumOfSwitches(int numOfSwitches) {
        this.numOfSwitches = numOfSwitches;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getSwitchDrawable() {
        return switchDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param switchDrawable The example drawable attribute value to use.
     */
    public void setSwitchDrawable(Drawable switchDrawable) {
        this.switchDrawable = switchDrawable;
    }
}
