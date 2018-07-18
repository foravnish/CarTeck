package carteckh.carteckh.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */
@SuppressWarnings("deprecation")
public class Car_Version_Photo3 extends Fragment implements AdapterView.OnItemClickListener {

    @InjectView(R.id.gallery1)
    Gallery gallery1;
    @InjectView(R.id.gallery2)
    Gallery gallery2;
    @InjectView(R.id.c_photoes_heading1)
    CustomTextView c_photoes_heading1;
    @InjectView(R.id.c_photoes_heading2)
    CustomTextView c_photoes_heading2;
    private Activity activity;
    private JSONArray interiorJsonArray = null;
    private JSONArray exteriorJsonArray = null;
    private ImageLoader imageLoader;

    public Car_Version_Photo3() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activity = getActivity();
        imageLoader = AppController.getInstance().getImageLoader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.c_photoes, null);
        ButterKnife.inject(this, view);
        if (Car_Version_Overview3.PhotoListInterior != null) {
            try {
                interiorJsonArray = Car_Version_Overview3.PhotoListInterior.getJSONArray("Photos");
                if (interiorJsonArray.length() > 0) {
                    c_photoes_heading1.setText("Interior");
                    c_photoes_heading1.setVisibility(View.VISIBLE);
                    gallery1.setOnItemClickListener(this);
                    gallery1.setVisibility(View.VISIBLE);

                    gallery1.setAdapter(new ImageAdapter1(activity));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (Car_Version_Overview3.PhotoListExterior != null) {
            try {
                exteriorJsonArray = Car_Version_Overview3.PhotoListExterior.getJSONArray("Photos");
                if (exteriorJsonArray.length() > 0) {
                    c_photoes_heading2.setText("Exterior");
                    c_photoes_heading2.setVisibility(View.VISIBLE);
                    gallery2.setOnItemClickListener(this);
                    gallery2.setVisibility(View.VISIBLE);
                    gallery2.setAdapter(new ImageAdapter2(activity));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // display the images selecte
        switch (parent.getId()) {
            case R.id.gallery1:
                //Toast.makeText(activity, parent.getId() + "gallery1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gallery2:
                ///Toast.makeText(activity, parent.getId() + "gallery2", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class ImageAdapter1 extends BaseAdapter {
        private Context context;

        //        private int itemBackground;
        public ImageAdapter1(Context c) {
            context = c;
            // sets a grey background; wraps around the images
//            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
//            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
//            a.recycle();
        }

        // returns the number of images
        public int getCount() {
            return interiorJsonArray.length();
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }

        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            NetworkImageView imageView = new NetworkImageView(context);
            try {
                imageView.setImageUrl(interiorJsonArray.getString(position).replace(" ","%20"), imageLoader);
                imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                imageView.setBackgroundResource(itemBackground);
                return imageView;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


        }
    }

    public class ImageAdapter2 extends BaseAdapter {
        private Context context;
//        private int itemBackground;

        public ImageAdapter2(Context c) {
            context = c;
            // sets a grey background; wraps around the images
//            TypedArray a = c.obtainStyledAttributes(R.styleable.MyGallery);
//            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
//            a.recycle();
        }

        // returns the number of images
        public int getCount() {
            return exteriorJsonArray.length();
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }

        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            NetworkImageView imageView = new NetworkImageView(context);
            try {
                imageView.setImageUrl(exteriorJsonArray.getString(position).replace(" ","%20"), imageLoader);
                imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                imageView.setBackgroundResource(itemBackground);
                return imageView;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


        }
    }
}
