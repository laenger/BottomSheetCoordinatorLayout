package biz.laenger.android.bscl;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;

public class BottomSheetCoordinatorLayout extends CoordinatorLayout implements NestedScrollingChild {

    private final NestedScrollingChildHelper scrollingChildHelper;
    private final int appBarLayoutId;

    private boolean appBarExpanded;
    private AppBarLayout appBarLayout;

    public BottomSheetCoordinatorLayout(Context context) {
        this(context, null);
    }

    public BottomSheetCoordinatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSheetCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetCoordinatorLayout, defStyleAttr, 0);
        appBarLayoutId = a.getResourceId(R.styleable.BottomSheetCoordinatorLayout_appBarLayout, 0);
        a.recycle();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (appBarLayoutId != 0) {
            appBarLayout = ((AppBarLayout) findViewById(appBarLayoutId));
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    appBarExpanded = verticalOffset == 0;
                }
            });
        }
    }

    AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    boolean isAppBarExpanded() {
        return appBarExpanded;
    }

    // scrolling child

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        scrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return scrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return scrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        scrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return scrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return scrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return scrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return scrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return scrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    // scrolling parent

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        boolean handled1 = super.onStartNestedScroll(child, target, nestedScrollAxes);
        boolean handled2 = startNestedScroll(nestedScrollAxes);
        return handled1 || handled2;
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        stopNestedScroll();
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, null);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int[] tmpIntPair1 = new int[2];
        super.onNestedPreScroll(target, dx, dy, tmpIntPair1);

        int[] tmpIntPair2 = new int[2];
        dispatchNestedPreScroll(dx - tmpIntPair1[0], dy - tmpIntPair1[1], tmpIntPair2, null);

        consumed[0] = tmpIntPair1[0] + tmpIntPair2[0];
        consumed[1] = tmpIntPair1[1] + tmpIntPair2[1];
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        boolean handled1 = super.onNestedFling(target, velocityX, velocityY, consumed);
        boolean handled2 = dispatchNestedFling(velocityX, velocityY, consumed);
        return handled1 || handled2;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        boolean handled1 = super.onNestedPreFling(target, velocityX, velocityY);
        boolean handled2 = dispatchNestedPreFling(velocityX, velocityY);
        return handled1 || handled2;
    }

}
