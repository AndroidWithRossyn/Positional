package app.simple.positional.decorations.fastscroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

@SuppressLint ("MissingSuperCall")
public class FastScrollNestedScrollView extends NestedScrollView implements ViewHelperProvider {

    @NonNull
    private final ViewHelper mViewHelper = new ViewHelper();

    public FastScrollNestedScrollView(@NonNull Context context) {
        super(context);

        init();
    }
    
    public FastScrollNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        
        init();
    }
    
    public FastScrollNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init();
    }
    
    private void init() {
        setScrollContainer(true);
    }
    
    @NonNull
    @Override
    public FastScroller.ViewHelper getViewHelper() {
        return mViewHelper;
    }
    
    @Override
    public void draw(@NonNull Canvas canvas) {
        mViewHelper.draw(canvas);
    }
    
    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        mViewHelper.onScrollChanged(left, top, oldLeft, oldTop);
    }
    
    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent event) {
        return mViewHelper.onInterceptTouchEvent(event);
    }
    
    @SuppressLint ("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return mViewHelper.onTouchEvent(event);
    }
    
    private class ViewHelper extends SimpleViewHelper {
        
        @Override
        public int getScrollRange() {
            return super.getScrollRange() + getPaddingTop() + getPaddingBottom();
        }
        
        @Override
        protected void superDraw(@NonNull Canvas canvas) {
            FastScrollNestedScrollView.super.draw(canvas);
        }
        
        @Override
        protected void superOnScrollChanged(int left, int top, int oldLeft, int oldTop) {
            FastScrollNestedScrollView.super.onScrollChanged(left, top, oldLeft, oldTop);
        }
        
        @Override
        protected boolean superOnInterceptTouchEvent(@NonNull MotionEvent event) {
            return FastScrollNestedScrollView.super.onInterceptTouchEvent(event);
        }
        
        @Override
        protected boolean superOnTouchEvent(@NonNull MotionEvent event) {
            return FastScrollNestedScrollView.super.onTouchEvent(event);
        }
        
        @Override
        @SuppressLint ("RestrictedApi")
        protected int computeVerticalScrollRange() {
            return FastScrollNestedScrollView.this.computeVerticalScrollRange();
        }
        
        @Override
        @SuppressLint ("RestrictedApi")
        protected int computeVerticalScrollOffset() {
            return FastScrollNestedScrollView.this.computeVerticalScrollOffset();
        }
        
        @Override
        protected int getScrollX() {
            return FastScrollNestedScrollView.this.getScrollX();
        }
        
        @Override
        protected void scrollTo(int x, int y) {
            FastScrollNestedScrollView.this.scrollTo(x, y);
        }
    }
}
