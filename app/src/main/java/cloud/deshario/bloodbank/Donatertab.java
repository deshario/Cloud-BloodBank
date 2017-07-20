package cloud.deshario.bloodbank;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by Deshario on 6/26/2017.
 */

public class Donatertab extends AppCompatActivity {

    private PulsatorLayout mPulsator;
    Toolbar myToolbar;
    Button btn_found,btn_cancel;
    CircularImageView cm_profile;
    CircularImageView retry_img;
    private CircularImageView foundDevice;
    LinearLayout noDonors;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester);

        btn_found = (Button)findViewById(R.id.found);
        btn_cancel = (Button)findViewById(R.id.cancel);
        cm_profile = (CircularImageView)findViewById(R.id.profile);
        foundDevice = (CircularImageView)findViewById(R.id.foundDevice);
        retry_img = (CircularImageView)findViewById(R.id.retry);
        noDonors = (LinearLayout)findViewById(R.id.donors_not_found);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setTitle("Searching...");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mPulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        mPulsator.start();

        retry_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                retry_img.startAnimation(animation1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        re_search();
                    }
                },3000);
            }
        });

        btn_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Donatertab.this,"Deep Search Activated !",Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        foundDevice();
                    }
                },3000);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myToolbar.getMenu().clear();
                stop_searching();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.donater_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void stop_searching(){
        if(mPulsator.isStarted()){
            mPulsator.stop();
        }
        cm_profile.setVisibility(View.GONE);
        noDonors.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(noDonors, "alpha", 0f, 1f);
        anim.setDuration(1000);
        anim.start();

    }

    private void re_search(){
        mPulsator.start();
        noDonors.setVisibility(View.GONE);
        cm_profile.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(cm_profile, "alpha", 0f, 1f);
        anim.setDuration(1000);
        anim.start();
    }

    private void foundDevice(){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice.setVisibility(View.VISIBLE);
        animatorSet.start();
    }


}
