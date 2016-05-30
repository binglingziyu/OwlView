package com.ihubin.login.loginpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hubin on 16/5/30.
 */
public class OwlView2 extends View{

    private Paint mPaint;
    private Bitmap bodyBitmap;

    public OwlView2(Context context) {
        this(context,null);
    }

    public OwlView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OwlView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        bodyBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.signup_body);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bodyBitmap,0,0,mPaint);
    }
}
