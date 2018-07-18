package carteckh.carteckh.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;

/**
 * Created by developer on 27-Nov-15.
 */
public class Full_Overview extends Fragment {
    @InjectView(R.id.tv_question)
    TextView tv_title;

//    @InjectView(R.id.tv_ans)
//    TextView tv_ans;

    @InjectView(R.id.tv_ans)
    WebView tv_ans;


    @InjectView(R.id.tv_postby)
    TextView tv_postby;
    @InjectView(R.id.tv_postby1)
    TextView tv_postby1;
    @InjectView(R.id.tv_postdate)
    TextView tv_postdate;
    @InjectView(R.id.tv_postdate1)
    TextView tv_postdate1;
    @InjectView(R.id.tv_totalview)
    TextView tv_totalview;
    @InjectView(R.id.tv_totalview1)
    TextView tv_totalview1;

//    @InjectView(R.id.pager)
//    ViewPager pager;
    //ViewPager pager;

    View view;
//
//    @InjectView(R.id.imageData)
//    NetworkImageView imageData;

Button bt_tweet;

    String images;

    ArrayList<String > sss=new ArrayList<>();

    List<String> final_out_put = new ArrayList<String>();

    //ViewPagerAdapter viewPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.full_overview, container, false);
        ButterKnife.inject(this, view);
        //pager=(ViewPager) view.findViewById(R.id.pager);
        bt_tweet = (Button) view.findViewById(R.id.twi_id);

        //viewPagerAdapter=new ViewPagerAdapter();



        bt_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                        .text(getArguments().getString("tv_url").toString());
//                        .image(myImageUri);
                builder.show();
            }
        });


        Bundle bundle = getArguments();
        final String htmlText = " %s ";

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tv_title.setTypeface(typeface);
        //tv_ans.setTypeface(typeface);
        tv_postby.setTypeface(typeface);
        tv_postdate1.setTypeface(typeface);
        tv_totalview1.setTypeface(typeface);

        String message ="<font color='white'>"+ getArguments().getString("tv_ans")+ "</font>";


        tv_title.setText("" + Html.fromHtml(getArguments().getString("tv_title")));
        //tv_ans.setText("" + Html.fromHtml(getArguments().getString("tv_ans")));

        tv_ans.getSettings().setJavaScriptEnabled(true);

        tv_ans.loadData(message,"text/html; charset=utf-8", "UTF-8");
        tv_ans.getSettings();

        tv_ans.setBackgroundColor(Color.TRANSPARENT);
        tv_postby.setText("" + getArguments().getString("tv_postby1"));
        tv_postdate1.setText("" + getArguments().getString("tv_postdate1"));
        tv_totalview1.setText("" + getArguments().getInt("tv_totalview1"));
//        btn_readmore.setVisibility(View.GONE);

        images= getArguments().getString("tv_ans");

//        String mydata= String.valueOf(Html.fromHtml(getArguments().getString("tv_ans")));
//        //Log.d("gdfgdf",getArguments().getString("tv_ans").toString());
//
////        Log.d("gdfgdf",String.valueOf((getArguments().getString("tv_ans"))));
//        String htmlView = "<html><body style=\"text-align:justify\"> "+mydata+" </body></Html>";
//        tv_ans.loadData(String.format(htmlText,htmlView),"text/html", "utf-8");
//        tv_ans.setBackgroundColor(Color.GRAY);

        LikeView likeView = (LikeView) view.findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        likeView.setObjectIdAndType(
                "https://www.carteckh.com/did-you-know-detail/?dknow=10",
                LikeView.ObjectType.OPEN_GRAPH);


        String guess = "http";
        String guess2 = ".JPG";
        String guess2_2 = ".jpg";

        List<Integer> first_index = new ArrayList<Integer>();
        List<Integer> last_index = new ArrayList<Integer>();

//        FacebookLikeButton facebookLikeButton=(FacebookLikeButton)view.findViewById(R.id.likeView);
//
//        facebookLikeButton.setPageUrl("https://www.carteckh.com/did-you-know-detail/?dknow=10");
//
//        facebookLikeButton.setPagePictureUrl("https://www.carteckh.com/did-you-know-detail/?dknow=10");


//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/{page-id}/likes",
//                null,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//            /* handle the result */
//                    }
//                }
//        ).executeAsync();


        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(getArguments().getString("tv_url").toString()))
                .build();

        ShareButton shareButton = (ShareButton) view.findViewById(R.id.shareButton);
        shareButton.setShareContent(content);


//        try {
//            if (getArguments().getString("tv_ans").contains("JPG")) {
//                for (int index = images.toString().indexOf(guess);
//                     index >= 0;
//                     index = images.toString().indexOf(guess, index + 1)) {
//                    //Toast.makeText(getActivity(), index + "", Toast.LENGTH_SHORT).show();
//                    first_index.add(index);
//                }
//
//                for (int index = images.toString().indexOf(guess2);
//                     index >= 0;
//                     index = images.toString().indexOf(guess2, index + 1)) {
//                    //Toast.makeText(getActivity(), index + "", Toast.LENGTH_SHORT).show();
//                    last_index.add(index);
//                }
//
//
//                for (int i = 0; i < first_index.size(); i++) {
//
//                    int start_index = first_index.get(i);
//                    int end_index = last_index.get(i);
//
//                    String image_url = images.toString().substring(start_index, end_index);
//                    String space=image_url.replace(" ","%20");
//                    final_out_put.add(space + ".JPG");
//
//                    Log.d("dfgdfhgfhf", final_out_put.get(i));
//                    //Toast.makeText(getActivity(), final_out_put.get(i) + "", Toast.LENGTH_LONG).show();
//                }
//
//            }
//            else if (getArguments().getString("tv_ans").contains("jpg")) {
//                for (int index = images.toString().indexOf(guess);
//                     index >= 0;
//                     index = images.toString().indexOf(guess, index + 1)) {
//                    //Toast.makeText(getActivity(), index + "", Toast.LENGTH_SHORT).show();
//                    first_index.add(index);
//                }
//
//                for (int index = images.toString().indexOf(guess2_2);
//                     index >= 0;
//                     index = images.toString().indexOf(guess2_2, index + 1)) {
//                    //Toast.makeText(getActivity(), index + "", Toast.LENGTH_SHORT).show();
//                    last_index.add(index);
//                }
//
//
//                final_out_put.clear();
//                for (int i = 0; i < first_index.size(); i++) {
//
//                    int start_index = first_index.get(i);
//                    int end_index = last_index.get(i);
//
//                    String image_url = images.toString().substring(start_index, end_index);
//                    String space=image_url.replace(" ","%20");
//                    final_out_put.add(space + ".jpg");
//
//                    Log.d("dfgdfhgfhf", final_out_put.get(i));
//                    //Toast.makeText(getActivity(), final_out_put.get(i) + "", Toast.LENGTH_LONG).show();
//                }
//
//            }
//            else if (getArguments().getString("tv_ans").contains("href")) {
//
//
//                for (int index = images.toString().indexOf(guess);
//                     index >= 0;
//                     index = images.toString().indexOf(guess, index + 1)) {
//                    //Toast.makeText(getActivity(), index + "", Toast.LENGTH_SHORT).show();
//                    first_index.add(index);
//                }
//
//                for (int index = images.toString().indexOf(guess2);
//                     index >= 0;
//                     index = images.toString().indexOf(guess2, index + 1)) {
//                    //Toast.makeText(getActivity(), index + "", Toast.LENGTH_SHORT).show();
//                    last_index.add(index);
//                }
//
//
//                for (int i = 0; i < first_index.size(); i++) {
//
//                    int start_index = first_index.get(i);
//                    int end_index = last_index.get(i);
//
//                    String image_url = images.toString().substring(start_index, end_index);
//                    String space=image_url.replace(" ","%20");
//                    final_out_put.add(space + ".JPG");
//
//                    Log.d("dfgdfhgfhf", final_out_put.get(i));
//                    //Toast.makeText(getActivity(), final_out_put.get(i) + "", Toast.LENGTH_LONG).show();
//                }
//                Toast.makeText(getActivity(), "exist", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.d("fdgdfgdfgd",images.toString());
//        Log.d("fdgdfgdf", String.valueOf(Html.fromHtml(getArguments().getString("tv_ans"))));

//        if (getArguments().getString("tv_ans").contains("jpg")){
//            Log.d("frefsfgdfhgdfh_small","true");
//            String ss3= null;
//            try {
//                String ss=StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "jpg");
//                // Toast.makeText(getActivity(),ss.toString() , Toast.LENGTH_LONG).show();
//                String ss2="http"+ss.toString()+"jpg";
//                ss3 = ss2.replace(" ","%20");
//
//                ImageLoader imageLoader= AppController.getInstance().getImageLoader();
//                imageData.setImageUrl(ss3.toString(),imageLoader);
//                Log.d("sfgdfhgdfh1",ss3.toString());
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
//        }
//        else if (getArguments().getString("tv_ans").contains("jpeg")){
//            String ss3= null;
//            try {
//                String ss=StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "jpeg");
//                // Toast.makeText(getActivity(),ss.toString() , Toast.LENGTH_LONG).show();
//                String ss2="http"+ss.toString()+"jpeg";
//                ss3 = ss2.replace(" ","%20");
//                ImageLoader imageLoader= AppController.getInstance().getImageLoader();
//                imageData.setImageUrl(ss3.toString(),imageLoader);
//                Log.d("sfgdfhgdfh2",ss3.toString());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else if (getArguments().getString("tv_ans").contains("png")){
//            String ss3= null;
//            try {
//                String ss=StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "png");
//                // Toast.makeText(getActivity(),ss.toString() , Toast.LENGTH_LONG).show();
//                String ss2="http"+ss.toString()+"png";
//                ss3 = ss2.replace(" ","%20");
//                ImageLoader imageLoader= AppController.getInstance().getImageLoader();
//                imageData.setImageUrl(ss3.toString(),imageLoader);
//                Log.d("sfgdfhgdfh3",ss3.toString());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        else if (getArguments().getString("tv_ans").contains("JPG")){
//            Log.d("frefsfgdfhgdfh_caps","true");
//            String ss3= null;
//            try {
//               // for (int i=0;i<3;i++){
//                //sss.set(i, StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "JPG"));}
//                //Log.d("sssss",sss.toString());
//                //Log.d("sssss1","ggg");
//                String ss=StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "JPG");
//                // Toast.makeText(getActivity(),ss.toString() , Toast.LENGTH_LONG).show();
//                String ss2="http"+ss.toString()+"JPG";
//                ss3 = ss2.replace(" ","%20");
//
//                ImageLoader imageLoader= AppController.getInstance().getImageLoader();
//                imageData.setImageUrl(ss3.toString(),imageLoader);
//                Log.d("sfgdfhgdfh_cap1",ss3.toString());
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
//        }
//        else if (getArguments().getString("tv_ans").contains("JPEG")){
//            String ss3= null;
//            try {
//                String ss=StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "JPEG");
//                // Toast.makeText(getActivity(),ss.toString() , Toast.LENGTH_LONG).show();
//                String ss2="http"+ss.toString()+"JPEG";
//                ss3 = ss2.replace(" ","%20");
//                ImageLoader imageLoader= AppController.getInstance().getImageLoader();
//                imageData.setImageUrl(ss3.toString(),imageLoader);
//                Log.d("sfgdfhgdfh_cap2",ss3.toString());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else if (getArguments().getString("tv_ans").contains("PNG")){
//            String ss3= null;
//            try {
//                String ss=StringUtils.substringBetween(getArguments().getString("tv_ans"), "http", "PNG");
//                // Toast.makeText(getActivity(),ss.toString() , Toast.LENGTH_LONG).show();
//                String ss2="http"+ss.toString()+"PNG";
//                ss3 = ss2.replace(" ","%20");
//                ImageLoader imageLoader= AppController.getInstance().getImageLoader();
//                imageData.setImageUrl(ss3.toString(),imageLoader);
//                Log.d("sfgdfhgdfh_cap3",ss3.toString());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


            //String mid= getArguments().getString("tv_ans")

            //Log.d("fgvdfgdfhbd",ss3.toString());
//        pager.setAdapter(viewPagerAdapter);
//        pager.setCurrentItem(1);
//        CirclePageIndicator indicator = (CirclePageIndicator)view.findViewById(R.id.indicat);
//        indicator.setViewPager(pager);

            return view;
        }
//    public class ViewPagerAdapter extends PagerAdapter  {
//
//        LayoutInflater layoutInflater;
//
//        @Override
//        public int getCount() {
//            return final_out_put.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//
//            NetworkImageView imageView;
//
//
//            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = layoutInflater.inflate(R.layout.custom_images1, container, false);
//            imageView = (NetworkImageView) view.findViewById(R.id.imageView);
//
//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//            imageView.setImageUrl(final_out_put.get(position),imageLoader);
//
//            Log.d("fdbgfhgf",final_out_put.get(position));
//            (container).addView(view);
//
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            (container).removeView((LinearLayout) object);
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            return super.POSITION_NONE;
//        }
//
//
//    }
}
