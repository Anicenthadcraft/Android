package com.ancient.ancient_handcraft.app.carauselview;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.ancient.ancient_handcraft.R;
import com.ancient.ancient_handcraft.app.PojoObj.Banner.BannerObject;
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public class MyFragment extends Fragment {

	TypedArray images ;
	BannerObject bannerObject;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	public static Fragment newInstance(DashboardActivity context, int pos,
									   float scale, boolean IsBlured, BannerObject mBannerObject)
	{
//		bannerObject = mBannerObject;
		Bundle b = new Bundle();
		b.putInt("pos", pos);
		b.putFloat("scale", scale);
		b.putBoolean("IsBlured", IsBlured);
		b.putString("imageKey",mBannerObject.getBannerImageUrl() );
		return Fragment.instantiate(context, MyFragment.class.getName(), b);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		initializeImageLoader();

		images = getResources().obtainTypedArray(R.array.entries);
		
		LinearLayout l = (LinearLayout)
				inflater.inflate(R.layout.carausel_myfragment, container, false);
		
		int pos = this.getArguments().getInt("pos");
		final String bannerUrl = this.getArguments().getString("imageKey");


		final ImageView iv = (ImageView) l.findViewById(R.id.content);
		TextView tv = (TextView) l.findViewById(R.id.viewID);
		tv.setText("Position = " + pos);


		//imageLoader.displayImage(bannerUrl, iv);
		//iv.setImageDrawable( images.getDrawable(pos));

		if (bannerUrl != null) {
			/*imageLoader.displayImage(bannerUrl, iv, options, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					Toast.makeText(getActivity(),"onLoadingStarted",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					Toast.makeText(getActivity(),"Failed "+failReason,Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {

					if (bannerUrl.equals("")) {
						Bitmap icon_cover = BitmapFactory.decodeResource(getResources(),
								R.drawable.banner1);
						iv.setImageBitmap(icon_cover);
					} else {
						iv.setImageBitmap(loadedImage);
					}

				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {

				}
			});*/
			imageLoader.displayImage(bannerUrl, iv, options);
		}

		MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
		float scale = this.getArguments().getFloat("scale");
		root.setScaleBoth(scale);
		boolean isBlured=this.getArguments().getBoolean("IsBlured");
		if(isBlured)
		{
			ViewHelper.setAlpha(root,MyPagerAdapter.getMinAlpha());
			ViewHelper.setRotationY(root, MyPagerAdapter.getMinDegree());
		}
		return l;
	}

	private void initializeImageLoader() {
		options = new DisplayImageOptions.Builder().showImageOnLoading(getResources().getDrawable(R.drawable.banner1)
		).showImageForEmptyUri(getResources().getDrawable(R.drawable.banner1)).showImageOnFail(getResources().getDrawable(R.drawable.banner1))
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).postProcessor(new BitmapProcessor() {
					@Override
					public Bitmap process(Bitmap bmp) {
						// return Bitmap.createScaledBitmap(bmp, 480, 370,
						// false);
						return bmp;
					}
				}).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
	}
}
