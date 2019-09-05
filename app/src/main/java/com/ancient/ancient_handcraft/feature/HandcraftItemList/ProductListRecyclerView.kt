package com.ancient.ancient_handcraft.feature.HandcraftItemList

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import kotlinx.android.synthetic.main.populate_featured_item.view.*
import android.graphics.drawable.BitmapDrawable
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.PojoObj.HandcraftItemList.HandcraftItemModel
import kotlinx.android.synthetic.main.populate_handcraft_item.view.*


class ProductListRecyclerView(var mContext: Context, var itemList:ArrayList<HandcraftItemModel>, var onClickInf: OnProductListItemClickInterface) : RecyclerView.Adapter<ProductListRecyclerView.MyViewHolder>() {


    private var context: Context
    private var mItemList:ArrayList<HandcraftItemModel>
    private var mOnClickInf: OnProductListItemClickInterface
    private lateinit var options: DisplayImageOptions
    private val imageLoader = ImageLoader.getInstance()

    init {
        context = mContext
        mItemList = itemList
        mOnClickInf = onClickInf
        initializeImageLoader()
    }
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.populate_handcraft_item, parent, false)
        return MyViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(mItemList.get(position),imageLoader,options,context)

        holder.parent_item_rl.setOnClickListener(){
            mOnClickInf.onItemClick(position,mItemList.get(position))
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        if(mItemList!=null && mItemList.size>0) return mItemList.size else return 0
    }

    //the class is hodling the recycler view
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editor_item_iv = itemView.editor_item_iv
        val parent_item_rl = itemView.parent_item_rl
        fun bindItems(
            item: HandcraftItemModel,
            imageLoader: ImageLoader,
            options: DisplayImageOptions,
            context: Context
        ) {
            //imageLoader.displayImage(item.itemUrl, editor_item_iv, options)
            itemView.item_name_tv.text = item.itemName
            if(item.isFaviourite){
                itemView.fav_iv.setImageDrawable(context.resources.getDrawable(R.drawable.ic_contact_list_favorite_icon_selected))
            }else{
                itemView.fav_iv.setImageDrawable(context.resources.getDrawable(R.drawable.ic_contact_list_favorite_icon_deselected))
            }
            imageLoader.loadImage(item.itemUrl,object :ImageLoadingListener{
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    itemView.latest_item_iv.setImageDrawable(BitmapDrawable(context.resources, loadedImage))
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                }

            })
        }
    }

    private fun initializeImageLoader() {
        options = DisplayImageOptions.Builder().showImageOnLoading(mContext.resources.getDrawable(R.drawable.banner1)
        ).showImageForEmptyUri(mContext.resources.getDrawable(R.drawable.banner1)).showImageOnFail(mContext.resources.getDrawable(R.drawable.banner1))
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).postProcessor { bmp ->
                // return Bitmap.createScaledBitmap(bmp, 480, 370,
                // false);
                bmp
            }.build()
        imageLoader.init(ImageLoaderConfiguration.createDefault(context))
    }
}