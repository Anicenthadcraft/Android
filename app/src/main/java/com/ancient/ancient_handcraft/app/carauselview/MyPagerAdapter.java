package com.ancient.ancient_handcraft.app.carauselview;


import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.ancient.ancient_handcraft.R;
import com.ancient.ancient_handcraft.Utils.AnimUtils;
import com.ancient.ancient_handcraft.app.PojoObj.Banner.BannerObject;
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener {



	private boolean swipedLeft=false;
	private int lastPage=5;
	private int totalCount=5;
	private MyLinearLayout cur = null;
	private MyLinearLayout next = null;
	private MyLinearLayout prev = null;
	private MyLinearLayout prevprev = null;
	private MyLinearLayout nextnext = null;
	private DashboardActivity context;
	private FragmentManager fm;
	private float scale;
	private boolean IsBlured;
	private static float minAlpha=0.8f;
	private static float maxAlpha=1f;
	private static float minDegree=30.0f;
	private int counter=0;
	public final static int LOOPS = 1000;

	public static float getMinDegree()
	{
		return minDegree;
	}
	public static float getMinAlpha()
	{
		return minAlpha;
	}
	public static float getMaxAlpha()
	{
		return maxAlpha;
	}
	ViewPager mPager;
	private int  mScrollState;
	private int  mCurrentPosition;
	ArrayList<BannerObject> bannerArray = null;

	private LinearLayout mContainer;
	private int mDrawable;
	private int mSpacing;
	private int mSize;

	private int mInitialPage = 0;

	private int defaultSizeInDp = 8;
	private int defaultSpacingInDp = 8;
	
	public MyPagerAdapter(DashboardActivity context, FragmentManager fm, ArrayList<BannerObject> mBannerArray, ViewPager pager, @NonNull LinearLayout containerView, @DrawableRes int drawableRes) {
		super(fm);
		this.fm = fm;
		this.context = context;
		bannerArray = mBannerArray;
		mPager = pager;
		lastPage = bannerArray.size();
		totalCount = bannerArray.size();

		mContainer = containerView;
		mDrawable = drawableRes;
	}

	@Override
	public Fragment getItem(int position)
	{

		// make the first pager bigger than others
		if (position == AnimUtils.FIRST_PAGE)
			scale = AnimUtils.BIG_SCALE;
		else
		{
			scale = AnimUtils.SMALL_SCALE;
			IsBlured=true;

		}

		position = position % totalCount;

		Log.d("position", String.valueOf(position));
		Fragment curFragment= MyFragment.newInstance(context, position, scale,IsBlured,bannerArray.get(position));
		cur = getRootView(position);
		next = getRootView(position +1);
		prev = getRootView(position -1);

		return curFragment;
	}

	@Override
	public int getCount()
	{		
		return bannerArray.size()*100;
	}



	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{

// if (positionOffset >= 0f && positionOffset <= 1f)
		{
			positionOffset = positionOffset * positionOffset;
			cur = getRootView(position);
			next = getRootView(position + 1);
			if (position != 0) {
				prev = getRootView(position - 1);
			} else {
				prev = getRootView(mPager.getAdapter().getCount()  - 2);
			}

			if (position != mPager.getAdapter().getCount() - 1) {
				nextnext = getRootView(position + 2);
			} else {
				nextnext = getRootView(0);

			}

			ViewHelper.setAlpha(cur, maxAlpha - 0.5f * positionOffset);
			ViewHelper.setAlpha(next, minAlpha + 0.5f * positionOffset);
			ViewHelper.setAlpha(prev, minAlpha + 0.5f * positionOffset);


			if (nextnext != null) {
				ViewHelper.setAlpha(nextnext, minAlpha);
				ViewHelper.setRotationY(nextnext, -minDegree);
			}
			if (cur != null) {
				cur.setScaleBoth(AnimUtils.BIG_SCALE
						- AnimUtils.DIFF_SCALE * positionOffset);

				ViewHelper.setRotationY(cur, 0);
			}

			if (next != null) {
				next.setScaleBoth(AnimUtils.SMALL_SCALE
						+ AnimUtils.DIFF_SCALE * positionOffset);
				ViewHelper.setRotationY(next, -minDegree);
			}
			if (prev != null) {
				ViewHelper.setRotationY(prev, minDegree);
			}

			
			/*To animate it properly we must understand swipe direction
			 * this code adjusts the rotation according to direction.
			 */
			if (swipedLeft) {
				if (next != null)
					ViewHelper.setRotationY(next, -minDegree + minDegree * positionOffset);
				if (cur != null)
					ViewHelper.setRotationY(cur, 0 + minDegree * positionOffset);
			} else {
				if (next != null)
					ViewHelper.setRotationY(next, -minDegree + minDegree * positionOffset);
				if (cur != null) {
					ViewHelper.setRotationY(cur, 0 + minDegree * positionOffset);
				}
			}

	//	}

		}
	}

	@Override
	public void onPageSelected(int position) {

/*
 * to get finger swipe direction
 */
		if(lastPage<=position || position==mPager.getAdapter().getCount() - 1)
		{
			swipedLeft=true;
		}
		else if(lastPage>position || position ==0 )
		{
			swipedLeft=false;
		}
		lastPage=position;

		mCurrentPosition = position;

		int index = position % totalCount;
		setIndicatorAsSelected(index);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

		handleScrollState(state);
		mScrollState = state;

	}


	private void handleScrollState(final int state) {
		if (state == ViewPager.SCROLL_STATE_IDLE) {
			setNextItemIfNeeded();
		}
	}

	private void setNextItemIfNeeded() {
		if (!isScrollStateSettling()) {
			handleSetNextItem();
		}
	}


	private boolean isScrollStateSettling() {
		return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
	}

	private void handleSetNextItem() {
		final int lastPosition = mPager.getAdapter().getCount() - 1;
		if(mCurrentPosition == 0) {
			mPager.setCurrentItem(lastPosition, false);
		} else if(mCurrentPosition == lastPosition) {
			mPager.setCurrentItem(0, false);
		}
	}


	private MyLinearLayout getRootView(int position)
	{
		MyLinearLayout ly;
		try {
			ly = (MyLinearLayout) 
					fm.findFragmentByTag(this.getFragmentTag(position))
					.getView().findViewById(R.id.root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		if(ly!=null)
			return ly;
		return null;
	}

	private String getFragmentTag(int position)
	{
		return "android:switcher:" + mPager.getId() + ":" + position;
	}

	public void setInitialPage(int page) {
		mInitialPage = page;
	}

	public void setDrawable(@DrawableRes int drawable) {
		mDrawable = drawable;
	}

	public void setSpacingRes(@DimenRes int spacingRes) {
		mSpacing = spacingRes;
	}

	public void setSize(@DimenRes int dimenRes) {
		mSize = dimenRes;
	}

	public void show() {
		initIndicators();
		setIndicatorAsSelected(mInitialPage);
	}

	private void initIndicators() {
		if (mContainer == null || totalCount <= 0) {
			return;
		}

		mPager.addOnPageChangeListener(this);
		Resources res = context.getResources();
		mContainer.removeAllViews();
		for (int i = 0; i < totalCount; i++) {
			View view = new View(context);
			int dimen = mSize != 0 ? res.getDimensionPixelSize(mSize) : ((int) res.getDisplayMetrics().density * defaultSizeInDp);
			int margin = mSpacing != 0 ? res.getDimensionPixelSize(mSpacing) : ((int) res.getDisplayMetrics().density * defaultSpacingInDp);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dimen, dimen);
			lp.setMargins(i == 0 ? 0 : margin, 0, 0, 0);
			view.setLayoutParams(lp);
			view.setBackgroundResource(mDrawable);
			view.setSelected(i == 0);
			mContainer.addView(view);
		}
	}

	private void setIndicatorAsSelected(int index) {
		if (mContainer == null) {
			return;
		}
		for (int i = 0; i < mContainer.getChildCount(); i++) {
			View view = mContainer.getChildAt(i);
			view.setSelected(i == index);
		}
	}


	public void cleanup() {
		mPager.clearOnPageChangeListeners();
	}
}
