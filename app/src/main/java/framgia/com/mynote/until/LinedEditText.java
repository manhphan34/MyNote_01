package framgia.com.mynote.until;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class LinedEditText extends android.support.v7.widget.AppCompatEditText {
    // the vertical offset scaling factor (10% of the height of the text)
    private static final float VERTICAL_OFFSET_SCALING_FACTOR = 0.15f;

    // the dashed line scale factors
    private static final float DASHED_LINE_ON_SCALE_FACTOR = 0.01f;
    private static final float DASHED_LINE_OFF_SCALE_FACTOR = 0.0125f;

    // the paint we will use to draw the lines
    private Paint mPaint;

    // a reusable rect object
    private Rect mRect;

    public LinedEditText(Context context) {
        super(context);
        init();
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        // instantiate the rect
        mRect = new Rect();

        // instantiate the paint
        mPaint = new Paint();
        mPaint.setARGB(200, 0, 0, 0);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // set the path effect based on the view width
        mPaint.setPathEffect(
                new DashPathEffect(
                        new float[]{
                                getWidth() * DASHED_LINE_ON_SCALE_FACTOR,
                                getWidth() * DASHED_LINE_OFF_SCALE_FACTOR},
                        0));

        // get the height of the view
        int height = getHeight();

        // get the height of each line (not subtracting one from the line height makes lines uneven)
        int lineHeight = getLineHeight();

        // set the vertical offset basef off of the view width
        int verticalOffset = (int) (lineHeight * VERTICAL_OFFSET_SCALING_FACTOR);

        // the number of lines equals the height divided by the line height
        int numberOfLines = height / lineHeight;

        // if there are more lines than what appear on screen
        if (getLineCount() > numberOfLines) {

            // set the number of lines to the line count
            numberOfLines = getLineCount();
        }

        // get the baseline for the first line
        int baseline = getLineBounds(0, mRect);

        // for each line
        for (int i = 0; i < numberOfLines; i++) {

            // draw the line
            canvas.drawLine(
                    mRect.left,             // left
                    baseline + verticalOffset,      // top
                    mRect.right,            // right
                    baseline + verticalOffset,      // bottom
                    mPaint);               // paint instance

            // get the baseline for the next line
            baseline += lineHeight;
        }

        super.onDraw(canvas);
    }
}
