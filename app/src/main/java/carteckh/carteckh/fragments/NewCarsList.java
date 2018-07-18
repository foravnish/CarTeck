package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.model.BrandDatum;
import carteckh.carteckh.model.FindNewCar;
import carteckh.carteckh.model.RoadSideAssistance;
import carteckh.carteckh.network.GsonRequest;
import carteckh.carteckh.network.VolleySingleton;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;


public class NewCarsList extends Fragment implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    @InjectView(R.id.grid_view)
    GridView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.btn_add)
    Button btn_add;
    @InjectView(R.id.text)
    TextView text;
    View view;
    Adapter adapter = null;
    //List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private GsonRequest<FindNewCar> gsonRequest;
    private RoadSideAssistance roadSideAssistance;
    public static Stack<String> stringStack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        //Initializing the dialog
        dialog = new Dialog(NewCarsList.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.road_assistance, container, false);
        ButterKnife.inject(this, view);
        Constants.flag2=true;

        text.setText("New Cars");
        Constants.stringStack = new Stack<>();
        fragmentManager = getFragmentManager();

        initMenuFragment();
        adapter = new Adapter();
        btn_add.setVisibility(View.VISIBLE);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            }
        });


        grid_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mPosition = 0;
            int mOffset = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int position = grid_view.getFirstVisiblePosition();
                View v = grid_view.getChildAt(0);
                int offset = (v == null) ? 0 : v.getTop();

                if (mPosition < position || (mPosition == position && mOffset < offset)) {
                    // Scrolled up
                    tv_relative.setVisibility(View.GONE);

                } else {
                    // Scrolled down
                    tv_relative.setVisibility(View.VISIBLE);

                }
            }
        });


        doNetworkProcess();




        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment fragment = null;
                    //Fragment fragment2 = null;
                    //fragment2 = new Home();
                    fragment = new Home();
                    if (fragment != null) {

                        Bundle bundle1=new Bundle();
                        bundle1.putString("tncval",Constants.sharedPreferences.getString("tncvalnew",""));

                        fragment.setArguments(bundle1);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                        //  beginTransction(fragment2,"");
                    }
                    return true;
                }
                return false;
            }
        } );


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("ghfhfdfggdfghdh",Constants.sharedPreferences.getString("state_id", ""));

                Fragment fragment = new NewCarListByBrandSwipe();
                if (fragment != null) {
                    Constants.stringStack.push("NewCarListByBrandSwipe");
                    FragmentManager fragmentManager = getFragmentManager();
                    Bundle bundle = new Bundle();

                    bundle.putString("id", roadSideAssistance.getBrandData().get(position).getGetBrandId());
                    Log.d("rssb",roadSideAssistance.getBrandData().get(position).getGetBrandId());
                    Constants.editor.putString("idnew",roadSideAssistance.getBrandData().get(position).getGetBrandId());
                    Constants.editor.commit();
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();

                }


            }
        });
    }

    private void doNetworkProcess() {
        gsonRequest = new GsonRequest<>(Request.Method.GET, Constants.ROAD_ASSISTANCE_URL, FindNewCar.class, successListener(), errorListener());
        // Constants.showProgressDialog(getActivity(), getResources().getString(R.string.loading));
        Constants.showdialog(dialog);
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        gsonRequest.setShouldCache(false);
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest, "FindNewCar");
        Log.e("Test", " Request gson URL  " + gsonRequest);

    }

    public Response.Listener successListener() {
        return new Response.Listener<FindNewCar>() {
            @Override
            public void onResponse(FindNewCar response) {
                if (response != null) {

                    roadSideAssistance = response.getRoadSideAssistance();
                    if (roadSideAssistance != null) {
                        grid_view.setAdapter(adapter);
                    }
                    adapter.notifyDataSetChanged();

                    Log.e("Test", " Responce  " + response.getRoadSideAssistance().getBrandData().size());
                   // Constants.exitdialog(dialog);
                    dialog.dismiss();
                }
            }


        };
    }

    public Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Testing", " Responce Error data " + error.toString());

                Constants.handleVolleyError(error,getActivity());
                Constants.exitdialog(dialog);

            }
        };
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("Find New Cars");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("Compare Cars");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("New Launches");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("Upcoming Cars");
        addFav.setResource(R.drawable.icn_4);

//        MenuObject addAdvanceSearch = new MenuObject("Advanced Car Search");
//        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.icn_5);
//        addAdvanceSearch.setBitmap(b1);


        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);

        //menuObjects.add(addAdvanceSearch);

        return menuObjects;
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Fragment fragment = null;

        switch (position) {
            case 2:
                fragment = new Compare_Car();
                break;
            case 3:
                fragment = new NewLaunchCars();
                break;
            case 4:
                fragment = new UpComing_Cars();
                break;
//            case 5:
//                fragment = new AdvancedCarSearch();
        }

        if (fragment != null) {
            Constants.stringStack.push("NewCarListByBrandSwipe");
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
        }

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(getActivity(), "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        NetworkImageView imageview;
        TextView title;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) NewCarsList.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return roadSideAssistance.getBrandData().size();
        }

        @Override
        public Object getItem(int position) {
            return roadSideAssistance.getBrandData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_roadsideassitance, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.imageview = (NetworkImageView) convertView.findViewById(R.id.iv_img);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_brandname);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.title.setTypeface(typeface);
            BrandDatum brandDatum = roadSideAssistance.getBrandData().get(position);
            viewHolder.title.setText("" + brandDatum.getGetBrandName());
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.imageview.setImageUrl(brandDatum.getIcon(), imageLoader);


            return convertView;
        }
    }

}
