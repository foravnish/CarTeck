package carteckh.carteckh.fragments;


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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos extends Fragment {

    private static CarOverView carOverView;
    List<Constants> video=new ArrayList<Constants>();
    private static final String YoutubeDeveloperKey = "AIzaSyBh0MRD8hssUPYNwE1VvQt2E6JESNj-Y7M";
    TextView videoName;
    private YouTubePlayer YPlayer;
    String cut;

    String final_out_put = new String();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.videos, container, false);

        videoName=(TextView)view.findViewById(R.id.videoName);

//        YPlayer = (YouTubePlayer)view.findViewById(R.id.youtube_fragment);
        if (carOverView.VideoData != null) {
            try {
                final JSONObject videoInfoDataJsonObject = carOverView.VideoData.getJSONObject("VideoInfo");

                video.add(new Constants(videoInfoDataJsonObject.optString("VideoName"),videoInfoDataJsonObject.optString("VideoLink")));

                videoName.setText(videoInfoDataJsonObject.optString("VideoName"));
                String getvideourl=videoInfoDataJsonObject.optString("VideoLink");

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

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                            //YPlayer.play();

//                            YPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
//                                @Override
//                                public void onPlaying() {
//                                    //YPlayer.pause();
//                                }
//
//                                @Override
//                                public void onPaused() {
//
//                                }
//
//                                @Override
//                                public void onStopped() {
//
//                                }
//
//                                @Override
//                                public void onBuffering(boolean b) {
//
//                                }
//
//                                @Override
//                                public void onSeekTo(int i) {
//
//                                }
//                            });
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

}
