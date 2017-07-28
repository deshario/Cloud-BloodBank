package cloud.deshario.bloodbank;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by Deshario on 7/22/2017.
 */

public class TABBAR extends AppCompatActivity {

    static NavigationTabBar navigationTabBar;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar,toptoolbar;
    private EndDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deshario_main);

        toptoolbar = (Toolbar) findViewById(R.id.top_toolbar);
        toptoolbar.inflateMenu(R.menu.main_menu);//changed
        toptoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menu){
                navigationTabBar.deselect();
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                if(menu.getItemId() == R.id.opt_menu){
                    Toast.makeText(TABBAR.this,"action_info",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initUI();
        initNavigationDrawer();
    }

    private void initUI() {
       navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_sample_5);
        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp), Color.WHITE
                ).build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_close_white_36dp), Color.WHITE
                ).build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_verified_user_white_24dp), Color.WHITE
                ).build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_outline_white_24dp), Color.WHITE
                ).build()
        );
        navigationTabBar.setModels(models5);
        navigationTabBar.setModelIndex(0, true);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                Toast.makeText(TABBAR.this, String.format("Tab :: %d", index), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.donater_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_menu:
//                navigationTabBar.deselect();
//                Toast.makeText(TABBAR.this,"Navigation Drawer",Toast.LENGTH_SHORT).show();
//                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
//                    drawerLayout.closeDrawer(Gravity.RIGHT);
//                }else{
//                    drawerLayout.openDrawer(Gravity.RIGHT);
//                }
//                break;
//            default:
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.home:
                        Toast.makeText(TABBAR.this,"Home",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        //Toast.makeText(MainActivity.this,"Settings",Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.container, new frag_one(), "createPost")
//                                .addToBackStack(null).commit();
                        break;
                    case R.id.trash:
                        Toast.makeText(TABBAR.this,"Trash",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        tv_email.setText("sd.deshario@linuxmail.org");
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        drawerToggle = new EndDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

//        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
//
//            @Override
//            public void onDrawerClosed(View v){
//                super.onDrawerClosed(v);
//            }
//
//            @Override
//            public void onDrawerOpened(View v) {
//                super.onDrawerOpened(v);
//            }
//        };
        drawerLayout.addDrawerListener(drawerToggle);
        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
        //actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else{
            super.onBackPressed();
        }
    }

    private Drawable rotateDrawable(@DrawableRes int resId, int degree){
        Bitmap bmpOriginal = BitmapFactory.decodeResource(getResources(), resId);
        Bitmap bmpResult = Bitmap.createBitmap(bmpOriginal.getHeight(), bmpOriginal.getWidth(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bmpResult);
        int pivot = bmpOriginal.getHeight() / 2;
        tempCanvas.rotate(degree, pivot, pivot);
        tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
        Drawable drawable = new BitmapDrawable(getResources(), bmpResult);
        return drawable;
    }
}
