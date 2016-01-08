package com.minkbox.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minkbox.R;
import com.minkbox.model.Product;

import java.util.List;

/**
 * Created by MMFA-YOGESH on 9/1/2015.
 */
public class CustomGridAdapter extends BaseAdapter {
    public List<Product> data;
    private Activity activity;
    Context context;
    public com.minkbox.lazyload.ImageLoader imageLoader;

    private static LayoutInflater inflater = null;

    public CustomGridAdapter(Activity show, List<Product> d) {
        this.context = show;
        data = d;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader= new com.minkbox.lazyload.ImageLoader(show.getApplicationContext());
    }

    public void updateResults(List<Product> d) {
        data = d;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = null;
        convertView = null;

        if (convertView == null) {
            vi = inflater.inflate(
                    R.layout.home_grid_items, null);
            final ViewHolder holder = new ViewHolder();
            try {
                holder.tv = (TextView) vi
                        .findViewById(R.id.product_name);
                holder.product_cost = (TextView) vi
                        .findViewById(R.id.product_rate);
                holder.img = (ImageView) vi
                        .findViewById(R.id.product_image);
                holder.sold_img = (ImageView) vi
                        .findViewById(R.id.sold_image);
                holder.reserve_img = (ImageView) vi
                        .findViewById(R.id.reserve_image);
                vi.setTag(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data.size() <= 0) {

        } else {
            final ViewHolder holder = (ViewHolder) vi.getTag();
            Product product;
            product = data.get(position);

            if (product.getProduct_image1() != null
                    && !product.getProduct_image1().equalsIgnoreCase("")) {
               imageLoader.DisplayImage(product.getProduct_image1(), holder.img);


               /* ImageLoader imageLoader1 = ImageLoader.getInstance();
               imageLoader1.displayImage(product.getProduct_image1(), holder.img);*/

            } else {
//                Bitmap bitmap = BitmapFactory.decodeResource(holder.img.getResources(), R.drawable.def_product);
//                holder.img.setImageBitmap(getRoundedCornerBitmap(bitmap, 15));
                holder.img.setImageResource(R.drawable.compactdisc);
            }

            if ("1".equalsIgnoreCase(product.getProduct_sold_status())) {
                if ("0".equalsIgnoreCase(product.getProduct_sold_status())) {
                    holder.sold_img.setVisibility(View.GONE);
                } else {
                    holder.sold_img.setVisibility(View.VISIBLE);
                }

            }else
            {
                if ("0".equalsIgnoreCase(product.getProduct_reserve_status())) {
                    holder.reserve_img.setVisibility(View.GONE);
                } else {
                    holder.reserve_img.setVisibility(View.VISIBLE);
                }
            }
            holder.product_cost.setText(product.getProduct_price()+" "+product.getProduct_price_currency());
            holder.tv.setText(product.getProduct_title());

        }
        return vi;
    }



    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 70;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static class ViewHolder {
        public TextView tv;
        public TextView product_cost;
        public ImageView img;
        public ImageView sold_img;
        public ImageView reserve_img;
    }
}
