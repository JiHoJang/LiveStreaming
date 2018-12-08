package com.example.jang.se;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.ArrayList;


//implements RtspClient.Callback, Session.Callback, SurfaceHolder.Callback
public class Video extends AppCompatActivity implements IVLCVout.Callback {
    public final static String TAG ="MainActivity";
    private String mFilePath;
    private SurfaceView mSurface;
    private SurfaceHolder holder;
    private LibVLC libvlc;
    private org.videolan.libvlc.MediaPlayer mMediaPlayer = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private ImageButton Comment;
    private Button Send;
    private EditText txtComment;
    private KeyListener originalKeyListener;

    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //mSocket.connect();

        mFilePath = "rtmp://192.168.17.1:1935/live/livedu";

        Log.d(TAG, "Playing: " + mFilePath);
        mSurface = (SurfaceView) findViewById(R.id.surface);
        holder = mSurface.getHolder();

        Comment = (ImageButton) findViewById(R.id.btn_comment);
        Send = (Button) findViewById(R.id.btn_send);
        txtComment = (EditText) findViewById(R.id.txt_comment);

        originalKeyListener = txtComment.getKeyListener();
        txtComment.setKeyListener(null);

        try {
            socket = IO.socket("http://ec2-52-78-57-176.ap-northeast-2.compute.amazonaws.com:3000");

            socket.connect();

            socket.emit("join", "jiho","live");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Send.setVisibility(View.VISIBLE);
                txtComment.setVisibility(View.VISIBLE);

                txtComment.setKeyListener(originalKeyListener);
                txtComment.requestFocus();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(txtComment, InputMethodManager.SHOW_IMPLICIT);

                Send.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String message = txtComment.getText().toString().trim();
                        if (!TextUtils.isEmpty(message)) {
                            txtComment.setText("");
                            socket.emit("message", "jiho", message);
                        }
                        Send.setVisibility(View.INVISIBLE);
                        txtComment.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        //mSurfaceView.getHolder().addCallback(this);

        //initialize_step();
        //Start_Streaming();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setSize(mVideoWidth, mVideoHeight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createPlayer(mFilePath);
        socket.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
        socket.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        socket.disconnect();
    }

    private void setSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;

        if(mVideoHeight * mVideoWidth <= 1)
            return;

        if (holder == null || mSurface == null)
            return;

        int w = getWindow().getDecorView().getWidth();
        int h = getWindow().getDecorView().getHeight();
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if(w > h && isPortrait || w < h && !isPortrait) {
            int i = w;
            w = h;
            h = i;
        }

        float videoAR = (float) mVideoWidth / (float) mVideoHeight;
        float screenAR = (float) w / (float) h;

        if(screenAR < videoAR)
            h = (int) (w/videoAR);
        else
            w = (int)(h * videoAR);

        holder.setFixedSize(mVideoWidth, mVideoHeight);
        LayoutParams lp = mSurface.getLayoutParams();
        lp.width=w;
        lp.height=h;
        mSurface.setLayoutParams(lp);
        mSurface.invalidate();
    }

    private void createPlayer (String media) {
        releasePlayer();
        try {
            if (media.length() > 0) {
                Toast toast = Toast.makeText(this, media, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }

            ArrayList<String> options = new ArrayList<String>();
            options.add("--aout=opensles");
            options.add("--audio-time-stretch");
            options.add("-vvv");
            libvlc = new LibVLC(this, options);
            holder.setKeepScreenOn(true);

            mMediaPlayer = new MediaPlayer(libvlc);
            mMediaPlayer.setEventListener(mPlayerListener);

            final IVLCVout vout = mMediaPlayer.getVLCVout();
            vout.setVideoView(mSurface);
            vout.addCallback(this);
            vout.attachViews();

            Media m = new Media(libvlc, Uri.parse(media));
            mMediaPlayer.setMedia(m);
            mMediaPlayer.play();
        } catch (Exception e) {
            Toast.makeText(this, "Error in creating player!", Toast.LENGTH_LONG).show();
        }
    }

    private void releasePlayer() {
        if (libvlc == null)
            return ;
        mMediaPlayer.stop();
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.removeCallback(this);
        vout.detachViews();
        holder = null;
        libvlc.release();
        libvlc = null;

        mVideoWidth = 0;
        mVideoHeight = 0;
    }

    private MediaPlayer.EventListener mPlayerListener = new MyPlayerListener(this);

    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        if(width * height == 0)
            return;

        mVideoWidth = width;
        mVideoHeight = height;
        setSize(mVideoWidth, mVideoHeight);
    }

    @Override
    public void onSurfacesCreated(IVLCVout vout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vout) {

    }

    @Override
    public void onHardwareAccelerationError(IVLCVout vlcVout) {
        Log.e(TAG, "Error with hardware acceleration");
        this.releasePlayer();
        Toast.makeText(this, "Error with hardware acceleration", Toast.LENGTH_LONG).show();
    }

    private static class MyPlayerListener implements MediaPlayer.EventListener {
        private WeakReference<Video> mOwner;

        public MyPlayerListener(Video owner) {
            mOwner = new WeakReference<Video>(owner);
        }

        @Override
        public void onEvent(MediaPlayer.Event event) {
            Video player = mOwner.get();

            switch (event.type) {
                case MediaPlayer.Event.EndReached:
                    Log.d(TAG, "MediaPlayerEndReached");
                    player.releasePlayer();
                    break;
                case MediaPlayer.Event.Playing:
                case MediaPlayer.Event.Paused:
                case MediaPlayer.Event.Stopped:
                default:
                    break;
            }
        }
    }

    /*
    public void initialize_step() {
        mSession = SessionBuilder.getInstance()
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setAudioQuality(new AudioQuality(8000, 16000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setSurfaceView(mSurfaceView).setPreviewOrientation(0)
                .setCallback(this).build();

        // rtsp client
        myClient = new RtspClient();
        myClient.setSession(mSession);
        myClient.setCallback(this);
        mSurfaceView.setAspectRatioMode(SurfaceView.ASPECT_RATIO_PREVIEW);

        String ip,port,path;

        Pattern uri = Pattern.compile("rtsp://(.+):(\\d+)/(.+)");
        Matcher m = uri.matcher(StreamInformation.STREAM_URL);
        m.find();
        ip = m.group(1);
        port = m.group(2);
        path = m.group(3);

        myClient.setStreamPath("/"+path);
    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {

        switch (reason){
            case Session.ERROR_CAMERA_ALREADY_IN_USE:
                break;
            case Session.ERROR_CAMERA_HAS_NO_FLASH:
                break;
            case Session.ERROR_CONFIGURATION_NOT_SUPPORTED:
                break;
            case Session.ERROR_INVALID_SURFACE:
                break;
            case Session.ERROR_STORAGE_NOT_READY:
                break;
            case Session.ERROR_OTHER:
                break;
        }
        if(e != null){
            e.printStackTrace();
        }
    }

    public void Start_Streaming(){
        if(myClient.isStreaming())
        {
            mSession.startPreview();
            myClient.startStream();
        }
        else{
            mSession.stopPreview();
            myClient.stopStream();
            mSession.release();
            mSurfaceView.getHolder().removeCallback(this);
        }
    }

    @Override
    public void onPreviewStarted() {

    }

    @Override
    public void onSessionConfigured() {

    }

    @Override
    public void onRtspUpdate(int message, Exception exception) {
        switch (message){
            case RtspClient.ERROR_CONNECTION_FAILED:
                break;
            case RtspClient.ERROR_WRONG_CREDENTIALS:
                break;
        }
    }

    @Override
    public void onSessionStarted() {

    }

    @Override
    public void onSessionStopped() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }*/
}
