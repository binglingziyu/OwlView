package com.ihubin.login.loginpage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by hubin on 16/5/30.
 */
public class OwlView2 extends View implements View.OnFocusChangeListener{

    private Paint mPaint;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    private Bitmap eyeLeftBitmap;
    private Bitmap eyeRightBitmap;
    private Bitmap bodyBitmap;
    private Bitmap footBitmap;
    private Bitmap armBitmap;

    private Matrix bodyMatrix;
    private Matrix armMatrix;
    private Matrix eyeLeftMatrix;
    private Matrix eyeRightMatrix;

    private ArrayList<EditText> watchViews;


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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dip2px(210),dip2px(250));
    }

    private void init() {

        watchViews = new ArrayList<EditText>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);

        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

        bodyMatrix = new Matrix();
        armMatrix = new Matrix();
        eyeLeftMatrix = new Matrix();
        eyeRightMatrix = new Matrix();

        eyeLeftBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.owl_eye);
        eyeLeftBitmap = compressBitmap(eyeLeftBitmap,dip2px(15),dip2px(15),false);
        eyeRightBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.owl_eye);
        eyeRightBitmap = compressBitmap(eyeRightBitmap,dip2px(15),dip2px(15),false);
        bodyBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.signup_body);
        bodyBitmap = compressBitmap(bodyBitmap,dip2px(153),dip2px(192),false);
        footBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.signup_feet);
        footBitmap = compressBitmap(footBitmap,dip2px(100),dip2px(22),false);
        armBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.signup_arm);
        armBitmap = compressBitmap(armBitmap,dip2px(61),dip2px(69),false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawBitmap(footBitmap,dip2px(60),dip2px(230),mPaint);

        armMatrix.reset();
        armMatrix.setRotate(currentArmAngle,armBitmap.getWidth(),armBitmap.getHeight()+dip2px(80));
        armMatrix.preTranslate(0,dip2px(100));
        canvas.drawBitmap(armBitmap,armMatrix,mPaint);

        bodyMatrix.reset();
        bodyMatrix.setRotate(currentBodyAngle,bodyBitmap.getWidth()/2+dip2px(50),bodyBitmap.getHeight()/2+dip2px(50));
        bodyMatrix.preTranslate(dip2px(50),dip2px(50));
        canvas.drawBitmap(bodyBitmap,bodyMatrix,mPaint);

        eyeLeftMatrix.reset();
        eyeLeftMatrix.setRotate(-15-currentEyeAngle,eyeLeftBitmap.getWidth()/2+dip2px(69),eyeLeftBitmap.getHeight()/2+dip2px(113)+currentEyeLeftTrans);
        eyeLeftMatrix.preTranslate(dip2px(69),dip2px(113)+currentEyeLeftTrans);
        canvas.drawBitmap(eyeLeftBitmap,eyeLeftMatrix,mPaint);

        eyeRightMatrix.reset();
        eyeRightMatrix.setRotate(-13-currentEyeAngle,eyeRightBitmap.getWidth()/2+dip2px(128),eyeRightBitmap.getHeight()/2+dip2px(126)+currentEyeRightTrans);
        eyeRightMatrix.preTranslate(dip2px(128),dip2px(126)+currentEyeRightTrans);
        canvas.drawBitmap(eyeRightBitmap,eyeRightMatrix,mPaint);

    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private Bitmap compressBitmap(Bitmap bitmap, float reqsW, float reqsH, boolean isAdjust) {
        if (bitmap == null || reqsW == 0 || reqsH == 0) return bitmap;
        if (bitmap.getWidth() > reqsW || bitmap.getHeight() > reqsH) {
            float scaleX = new BigDecimal(reqsW).divide(new BigDecimal(bitmap.getWidth()), 4, RoundingMode.DOWN).floatValue();
            float scaleY = new BigDecimal(reqsH).divide(new BigDecimal(bitmap.getHeight()), 4, RoundingMode.DOWN).floatValue();
            if (isAdjust) {
                scaleX = scaleX < scaleY ? scaleX : scaleY;
                scaleY = scaleX;
            }
            Matrix matrix = new Matrix();
            matrix.postScale(scaleX, scaleY);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    public void addWatchEditText(EditText tmp){
        watchViews.add(tmp);
        tmp.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus)return;
        handleAction(v);
    }

    // 处理动作
    private void handleAction(View v) {
        if(v == null) return;
        switch (v.getId()){
            case R.id.fullname:
                playAnimator(0,0,0,0,0);
                break;
            case R.id.email:
                playAnimator(-5,-15,15,dip2px(13),dip2px(7));
                break;
            case R.id.password:
                playAnimator(-10,-55,30,dip2px(23),dip2px(13));
                break;
        }

    }

    private int currentBodyAngle;
    private int currentArmAngle;
    private int currentEyeAngle;
    private int currentEyeLeftTrans;
    private int currentEyeRightTrans;
    private void playAnimator(int bodyAngle,int armAngle,int eyeAngle,int eyeLeftTransY,int eyeRightTransY){
        ValueAnimator bodyRotate = ValueAnimator.ofInt(currentBodyAngle,bodyAngle);
        bodyRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentBodyAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator armRotate = ValueAnimator.ofInt(currentArmAngle,armAngle);
        armRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentArmAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator eyeRotate = ValueAnimator.ofInt(currentEyeAngle,eyeAngle);
        eyeRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentEyeAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator eyeLeftTrans = ValueAnimator.ofInt(currentEyeLeftTrans,eyeLeftTransY);
        eyeLeftTrans.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentEyeLeftTrans = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator eyeRightTrans = ValueAnimator.ofInt(currentEyeRightTrans,eyeRightTransY);
        eyeRightTrans.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentEyeRightTrans = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet as = new AnimatorSet();
        as.playTogether(bodyRotate,armRotate,eyeRotate,eyeLeftTrans,eyeRightTrans);
        as.setDuration(500);
        as.start();

    }


}
