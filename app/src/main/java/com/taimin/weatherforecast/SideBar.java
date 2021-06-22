package com.taimin.weatherforecast;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class SideBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener	onTouchingLetterChangedListener;
    // 26个字母
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" ,"#"};
    private int	choose	= -1;																																			// 选中
    public int start; //文字起始位置
    public int singleHeight; //每个文字高度
    private Paint paint	= new Paint();
    private Context context;

    public void setB(String[] b) {
        SideBar.b = b;
        Log.v("setB",b.toString());
        invalidate();
    }

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);this.context = context;
    }

    public SideBar(Context context) {
        super(context);this.context = context;
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        if(b.length>0){
            super.onDraw(canvas);
            // 获取焦点改变背景颜色.
            int height = getMeasuredHeight()-20;// 获取对应高度
            int width = getWidth(); // 获取对应宽度
            singleHeight = height / 27;// 获取每一个字母的高度
            start = height/2 - singleHeight * b.length/2;

            for (int i = 0; i < b.length; i++) {
                paint.setColor(Color.GRAY);
                paint.setAntiAlias(true);
                paint.setTextSize(35);
                paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
                // x坐标等于中间-字符串宽度的一半.
                Rect rect = new Rect();
                paint.getTextBounds(b[i], 0, b[i].length(), rect);
                int w = rect.width();
                int h = rect.height();
                float xPos = width / 2 - w / 2;
                float yPos = singleHeight * i + singleHeight+start;
                // 选中的状态
                if(i == choose) {
                    paint.setColor(Color.parseColor("#ffffff"));
                    Paint cPaint = new Paint();
                    cPaint.setColor(getResources().getColor(R.color.colorPrimary));

                    canvas.drawCircle(width / 2, yPos-singleHeight/2, 20, cPaint);
                }

                canvas.drawText(b[i], xPos, yPos-(singleHeight-h)/2, paint);
                paint.reset();// 重置画笔
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c;
        if(b.length>0){
            c = (int) ((y - start )/ singleHeight);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
            Log.v("scrolly", Float.toString(y));
            Log.v("start", Integer.toString(start));

        }else{
            c = 0;
        }

        switch (action) {
            case MotionEvent.ACTION_UP:// 抬起
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if(mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if(oldChoose != c) {
                    if(c >= 0 && c < b.length) {
                        if(listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                            test();
                        }
                        if(mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        Vibrator vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
                        vibrator.vibrate(15);
                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public void test(){
        Log.v("this_sideBar",this.toString());
    }

    /**
     * 接口
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}
