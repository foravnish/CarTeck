package carteckh.carteckh.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by deep on 12/22/2015.
 */

public class Car_Version_Youtube3 extends Fragment  {


    private Activity activity;
    List<Constants> video=new ArrayList<Constants>();
    private static final String YoutubeDeveloperKey = "AIzaSyBh0MRD8hssUPYNwE1VvQt2E6JESNj-Y7M";
    TextView videoName;
    private YouTubePlayer YPlayer;
    String cut;

    //    @InjectView(R.id.parentview)
//    LinearLayout parentview;
    private Map<View, YouTubeThumbnailLoader> mLoaders;

    public Car_Version_Youtube3() {
        mLoaders = new HashMap<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.videos,  container, false);
        videoName=(TextView)view.findViewById(R.id.videoName);
//        ButterKnife.inject(this, view);

        UpadateUi();


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YoutubeDeveloperKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                try {
                    if (!b) {
                        try {
                            YPlayer = youTubePlayer;
                            YPlayer.setFullscreen(false);
                            YPlayer.setShowFullscreenButton(false);
                            YPlayer.loadVideo(cut);
                            YPlayer.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
        return view;
    }

    private void UpadateUi() {


        Log.d("gfdfgdfh", Car_Version_Overview3.Video.toString());


        for (int i = 0; i < Car_Version_Overview3.Video.length(); i++) {
            try {
                final JSONObject videoInfoDataJsonObject = Car_Version_Overview3.Video.getJSONObject(i);

                Log.d("gfdgd",videoInfoDataJsonObject.toString());


                video.add(new Constants(videoInfoDataJsonObject.optString("video_name"),videoInfoDataJsonObject.optString("video_link")));

                videoName.setText(videoInfoDataJsonObject.optString("video_name"));
                String getvideourl=videoInfoDataJsonObject.optString("video_link");

                Log.d("gdfgdfhd1",videoInfoDataJsonObject.optString("video_name"));
                Log.d("gdfgdfhd2",videoInfoDataJsonObject.optString("VideoLink"));

                //int len=getvideourl.length();
                cut=getvideourl.substring(30);

                if (getvideourl.contains("?"))
                {

                    cut=getvideourl.substring(30,41);
                    Log.d("gfvgfdgddgdfgh",cut);

                }

                Log.d("gfvdgdfgh",cut);

                //Toast.makeText(getActivity(), final_out_put.get(i) + "", Toast.LENGTH_LONG).show();

                Log.d("gvdfgdf",videoInfoDataJsonObject.optString("VideoLink"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

//                        LinearLayout linearLayoutParent = new LinearLayout(activity);
//                        linearLayoutParent.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
//                        linearLayoutParent.setPadding(10, 10, 10, 10);
//
//                        linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);
//                        linearLayoutParent.setTag((jsonObject.optString("video_link").substring((jsonObject.optString("video_link").length()) - 11)).trim());
//
//                        linearLayoutParent.setOnClickListener(this);
//
//
//                        com.google.android.youtube.player.YouTubeThumbnailView youTubeThumbnailView = new YouTubeThumbnailView(activity);
//                        youTubeThumbnailView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
//                        youTubeThumbnailView.setTag((jsonObject.optString("video_link").substring((jsonObject.optString("video_link").length()) - 11)).trim());
//                        youTubeThumbnailView.initialize(activity.getString(R.string.DEVELOPER_KEY), this);
//
//                        final YouTubeThumbnailLoader loader = mLoaders.get(youTubeThumbnailView);
//                        youTubeThumbnailView.setImageBitmap(null);
//
//                        if (loader == null) {
//                            //Loader is currently initialising
//                            youTubeThumbnailView.setTag((jsonObject.optString("video_link").substring((jsonObject.optString("video_link").length()) - 11)).trim());
//                        } else {
//                            //The loader is already initialised
//                            //Note that it's possible to get a DeadObjectException here
//                            try {
//                                loader.setVideo((jsonObject.optString("video_link").substring((jsonObject.optString("video_link").length()) - 11)).trim());
//                            } catch (IllegalStateException exception) {
//                                //If the Loader has been released then remove it from the map and re-init
//                                mLoaders.remove(youTubeThumbnailView);
//                                youTubeThumbnailView.initialize(activity.getString(R.string.DEVELOPER_KEY), this);
//
//                            }
//                        }
//
//
//                        CustomTextView customTextViewTitle1 = new CustomTextView(activity);
//                        customTextViewTitle1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                        customTextViewTitle1.setTextColor(Color.WHITE);
//                        customTextViewTitle1.setTag(i);
//                        customTextViewTitle1.setPadding(5, 5, 5, 5);
//                        customTextViewTitle1.setText(jsonObject.getString("video_name"));
//                        customTextViewTitle1.setTextSize(16);
//
//
//                        View view = new View(activity);
//                        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
//                        view.setBackgroundColor(Color.WHITE);
//
//
//                        linearLayoutParent.addView(youTubeThumbnailView);
//                        linearLayoutParent.addView(customTextViewTitle1);
//
//                        parentview.addView(linearLayoutParent);
//                        parentview.addView(view);


        }





    }




}
