package carteckh.carteckh.fragments;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Show_Details extends Fragment {
    @InjectView(R.id.text_brandlist)
    TextView text_brandlist;
    @InjectView(R.id.text_model)
    TextView text_model;
    @InjectView(R.id.text_ver)
    TextView text_ver;
    @InjectView(R.id.text_months)
    TextView text_months;
    @InjectView(R.id.text_year)
    TextView text_year;
    @InjectView(R.id.text_kms)
    TextView text_kms;
    @InjectView(R.id.text_seller)
    TextView text_seller;
    @InjectView(R.id.text_pin)
    TextView text_pin;
    @InjectView(R.id.text_price)
    TextView text_price;
    @InjectView(R.id.text_owner)
    TextView text_owner;
    @InjectView(R.id.text_state)
    TextView text_state;
    @InjectView(R.id.text_insurance)
    TextView text_insurance;
    @InjectView(R.id.text_fuel)
    TextView text_fuel;
    @InjectView(R.id.text_transmission)
    TextView text_transmission;
    @InjectView(R.id.mobile_No)
    TextView mobile_No;
    @InjectView(R.id.text_images1)
    ImageView text_images1;
    @InjectView(R.id.text_images2)
    ImageView text_images2;
    @InjectView(R.id.text_images3)
    ImageView text_images3;
    @InjectView(R.id.text_images4)
    ImageView text_images4;

    @InjectView(R.id.submit)
    Button submit;
//    @InjectView(R.id.edit)
//    Button edit;

    TextView tv1;
    String encodedImage = "";



    String encodedImage1 = "";
    String encodedImage2 = "";
    String encodedImage3 = "";
    String encodedImage4 = "";
    Dialog dialog,dialog2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.show__details, container, false);

        ButterKnife.inject(this,view);
        dialog = new Dialog(Show_Details.this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2 = new Dialog(Show_Details.this.getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.uploadcar);
        dialog2.setCancelable(false);
        Bundle  bundle=getArguments();
        text_brandlist.setText(bundle.getString("b2_brandlist"));
        text_model.setText(bundle.getString("b2_modellist"));
        text_ver.setText(bundle.getString("b1_verlist"));
        text_months.setText(bundle.getString("b1_monthlist"));
        text_year.setText(bundle.getString("b1_yearlist"));
        text_kms.setText(bundle.getString("b1_kmslist"));
        text_seller.setText(bundle.getString("b1_locationlist"));
        text_pin.setText(bundle.getString("b1_pinlist"));
        mobile_No.setText(bundle.getString("b1_mobile"));
        text_price.setText(bundle.getString("b1_pricelist"));
        text_owner.setText(bundle.getString("b1_ownerlist"));
        text_state.setText(bundle.getString("b1_sellerlist"));
        text_insurance.setText(bundle.getString("b1_insuranceist"));
        text_fuel.setText(bundle.getString("b1_fuellist"));

        if (bundle.getInt("b1_transmissionlist")==1){
            text_transmission.setText("Manual");
        }
        else if (bundle.getInt("b1_transmissionlist")==2) {
            text_transmission.setText("Automatic");
        }

        final String ima1=bundle.getString("image1");
        String ima2=bundle.getString("image2");
        String ima3=bundle.getString("image3");
        String ima4=bundle.getString("image4");


//        Log.d("dfsghfhfgdf",encodedImage1);
//        Log.d("dfsghfhfgdf",encodedImage2);
//        Log.d("dfsghfhfgdf",encodedImage3);
//        Log.d("dfsghfhfgdf",encodedImage4);


        byte[] decodedString1 = Base64.decode(ima1, Base64.DEFAULT);
        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);

        try {
            text_images1.setImageBitmap(decodedByte1);
            Drawable drawable =text_images1.getDrawable();
            final Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            Bitmap im= addWaterMark(bitmap);


            Bitmap resized = Bitmap.createScaledBitmap(im, 1600, 1000, true);


            Drawable drawable1 = new BitmapDrawable(getResources(), resized);
            text_images1.setImageDrawable(drawable1);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
                Log.d("dfsdf",encodedImage1.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


//            /**===============jai ho===========*/
//              byte[] decodedSt = Base64.decode(encodedImage1, Base64.DEFAULT);
//            Bitmap decodedBy = BitmapFactory.decodeByteArray(decodedSt, 0, decodedSt.length);
//            text_images4.setImageBitmap(decodedBy);
//            /**===============jai ho===========*/



            byte[] decodedString2 = Base64.decode(ima2, Base64.DEFAULT);
            Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            text_images2.setImageBitmap(decodedByte2);

            Drawable drawable_img2 =text_images2.getDrawable();
            final Bitmap bitmap_img2 = ((BitmapDrawable)drawable_img2).getBitmap();
            Bitmap im2= addWaterMark(bitmap_img2);

            Bitmap resized2 = Bitmap.createScaledBitmap(im2, 1600, 1050, true);


            Drawable drawable1_img2 = new BitmapDrawable(getResources(), resized2);
            text_images2.setImageDrawable(drawable1_img2);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resized2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage2 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }



            byte[] decodedString3 = Base64.decode(ima3, Base64.DEFAULT);
            Bitmap decodedByte3 = BitmapFactory.decodeByteArray(decodedString3, 0, decodedString3.length);
            text_images3.setImageBitmap(decodedByte3);

            Drawable drawable1111 =text_images3.getDrawable();
            final Bitmap bitmap111 = ((BitmapDrawable)drawable1111).getBitmap();
            Bitmap im3= addWaterMark(bitmap111);

            Bitmap resized3 = Bitmap.createScaledBitmap(im3, 1600, 1050, true);


            Drawable drawable111111 = new BitmapDrawable(getResources(), resized3);
            text_images3.setImageDrawable(drawable111111);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resized3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage3 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }




            byte[] decodedString4 = Base64.decode(ima4, Base64.DEFAULT);
            Bitmap decodedByte4 = BitmapFactory.decodeByteArray(decodedString4, 0, decodedString4.length);
            text_images4.setImageBitmap(decodedByte4);

            Drawable drawable11111 =text_images4.getDrawable();
            final Bitmap bitmap11111 = ((BitmapDrawable)drawable11111).getBitmap();
            Bitmap im4= addWaterMark(bitmap11111);


            Bitmap resized4 = Bitmap.createScaledBitmap(im4, 1600, 1050, true);


            Drawable drawable1111111 = new BitmapDrawable(getResources(), resized4);
            text_images4.setImageDrawable(drawable1111111);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resized4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage4 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Constants.showdialog(dialog);
                dialog2.show();

                final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.Url + "task=getSellCarPhotos", new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       // Constants.exitdialog(dialog);
                        dialog2.dismiss();

                        if (jsonObject.optString("Success").equals("1")) {

                            Log.d("RSSB",s+"");



                            Fragment fragment =new congrarts();
                            FragmentManager fm=getFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction();
                            ft.replace(R.id.container,fragment).addToBackStack("test").commit();

                            //Crouton.makeText(getActivity(), "" + jsonObject.optString("message"), Style.CONFIRM).show();
                            //Toast.makeText(getActivity(), Constants.sharedPreferences.getString("id","") , Toast.LENGTH_SHORT).show();

                            // Toast.makeText(getActivity(), "inserted" , Toast.LENGTH_SHORT).show();
                            Constants.editor.commit();


                        } else {
                            Crouton.makeText(getActivity(), "" + jsonObject.optString("message"), Style.ALERT).show();
                            //Toast.makeText(getActivity(), "not inerted" , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Constants.exitdialog(dialog);
                        dialog2.dismiss();
                        Constants.handleVolleyError(volleyError, getActivity());

                        Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.INFO).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("car_id", Constants.sharedPreferences.getString("id",""));
                        params.put("getImg1", String.valueOf(encodedImage1));
                        params.put("getImg2", String.valueOf(encodedImage2));
                        params.put("getImg3", String.valueOf(encodedImage3));
                        params.put("getImg4", String.valueOf(encodedImage4));


                        return params;

                    }
                };
                stringRequest2.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                stringRequest2.setShouldCache(false);

                AppController.getInstance().addToRequestQueue(stringRequest2);



//                AlertDialog.Builder builder2=new AlertDialog.Builder(getActivity());
//                    builder2.setTitle("Congratulation...");
//                    builder2.setMessage("You Car Details saved Sucessfully, It will Upload by Admin");
//                    builder2.setPositiveButton("Car Listing", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialog, int which) {
//                           Fragment fragment =new ManageCarListing();
//                           Bundle bundle2 = new Bundle();
//                           bundle2.putString("key","avnisht1");
//                           fragment.setArguments(bundle2);
//                           FragmentManager fm=getFragmentManager();
//                           FragmentTransaction ft=fm.beginTransaction();
//                           ft.replace(R.id.container,fragment).addToBackStack("test").commit();
//                       }
//                   });
//                    builder2.setCancelable(false);
//                    builder2.show();
            }
        });

//        edit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Fragment fragment =new Home();
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("tncval", "true");
//                fragment.setArguments(bundle1);
//                FragmentManager fm=getFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                ft.replace(R.id.container,fragment).addToBackStack("test").commit();
//
//
//            }
//        });
        return view;
    }
    private Bitmap addWaterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Bitmap waterMark = BitmapFactory.decodeResource(getResources(), R.drawable.watermark);
        Bitmap resized = Bitmap.createScaledBitmap(waterMark, 50, 30, true);

        float centerX = (w  - resized.getWidth()) * 0.5f;
        Log.d("w=",w+"");
        Log.d("w2=",resized.getWidth()+"");
        Log.d("center=",centerX+"");
        float centerY = (h- resized.getHeight()) * 0.5f;
        canvas.drawBitmap(resized, centerX, centerY, null);


        return result;
    }




}
