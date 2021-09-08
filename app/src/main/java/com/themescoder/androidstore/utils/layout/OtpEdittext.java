package com.themescoder.androidstore.utils.layout;

import android.content.Context;
import android.graphics.Canvas;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Muhammad Nabeel on 07/01/2019.
 */
public class OtpEdittext extends AppCompatEditText {
    float originalLeftPadding = -1;
    
    public OtpEdittext(Context context) {
        super(context);
    }
    
    public OtpEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public OtpEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculatePrefix();
    }
    
    private void calculatePrefix() {
        if (originalLeftPadding == -1) {
            String prefix = (String) getTag();
            float[] widths = new float[prefix.length()];
            getPaint().getTextWidths(prefix, widths);
            float textWidth = 0;
            for (float w : widths) {
                textWidth += w;
            }
            originalLeftPadding = getCompoundPaddingLeft();
            setPadding((int) (textWidth + originalLeftPadding),
                    getPaddingRight(), getPaddingTop(),
                    getPaddingBottom());
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String prefix = (String) getTag();
        canvas.drawText(prefix, originalLeftPadding, getLineBounds(0, null), getPaint());
    }
}