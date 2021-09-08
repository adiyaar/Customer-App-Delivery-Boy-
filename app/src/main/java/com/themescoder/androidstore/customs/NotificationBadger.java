package com.themescoder.androidstore.customs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.core.content.ContextCompat;

import com.themescoder.androidstore.R;


/**
 * NotificationBadger Adds Badge count on menu items like cart, notification etc
 **/

public class NotificationBadger extends Drawable {

    private boolean draw1;
    private String count1 = "";
    private Rect rect1 = new Rect();
    private Paint badgePaint1, badgePaint2, textPaint;


    public NotificationBadger(Context context) {

        float mTextSize = context.getResources().getDimension(R.dimen.badge_text_size);

        badgePaint1 = new Paint();
        badgePaint1.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorAccentGreen));
        badgePaint1.setAntiAlias(true);
        badgePaint1.setStyle(Paint.Style.FILL);

        badgePaint2 = new Paint();
        badgePaint2.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorAccentGreen));
        badgePaint2.setAntiAlias(true);
        badgePaint2.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextSize(mTextSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    public void draw(Canvas canvas) {

        if (!draw1) {
            return;
        }

        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;

        // Position the Badge in the Top-Right Quadrant of the Icon
        //** Using Math.max rather than Math.min **//

        float radius = ((Math.max(width, height) / 2)) / 2;
        float centerX = (width - radius - 1) +5;
        float centerY = radius -5;

        if(count1.length() <= 2) {
            // Draw Badge circle.
            canvas.drawCircle(centerX, centerY, (int)(radius+7.5), badgePaint2);
            canvas.drawCircle(centerX, centerY, (int)(radius+5.5), badgePaint1);
        }
        else {
            canvas.drawCircle(centerX, centerY, (int)(radius+8.5), badgePaint2);
            canvas.drawCircle(centerX, centerY, (int)(radius+6.5), badgePaint1);
//          canvas.drawRoundRect(radius, radius, radius, radius, 10, 10, paint1);
        }

        // Draw Badge count text inside the circle.
        textPaint.getTextBounds(count1, 0, count1.length(), rect1);

        float textHeight = rect1.bottom - rect1.top;
        float textY = centerY + (textHeight / 2f);

        if(count1.length() > 2)
            canvas.drawText("99+", centerX, textY, textPaint);
        else
            canvas.drawText(count1, centerX, textY, textPaint);
    }



    //*********** Sets the count on Badge ********//

    public void setCount(String count) {
        count1 = count;

        // Only draw a Badge if there are some Notifications
        draw1 = !count.equalsIgnoreCase("0");
        invalidateSelf();
    }


    @Override
    public void setAlpha(int alpha) {
        // set alpha here
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // set color filter here
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }



    //*********** Sets Notifications BadgeCount ********//

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        NotificationBadger badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);

        if (reuse != null && reuse instanceof NotificationBadger) {
            // Reuse Badge
            badge = (NotificationBadger) reuse;
        } else {
            // Create new Badge
            badge = new NotificationBadger(context);
        }

        // Set Count on Badge
        badge.setCount(count);

        // Set badge on LayerDrawable
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

}

