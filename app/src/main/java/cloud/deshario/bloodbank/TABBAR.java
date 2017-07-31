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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cloud.deshario.bloodbank.Fragments.BlankFragment;
import cloud.deshario.bloodbank.Fragments.NewRequest_Frag;
import cloud.deshario.bloodbank.Fragments.Timeline_Frag;
import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by Deshario on 7/22/2017.
 */

public class TABBAR extends AppCompatActivity {

    static NavigationTabBar navigationTabBar;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar,toptoolbar;
    private EndDrawerToggle drawerToggle;

    Fragment fragment_to_open;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deshario_main);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container, new Timeline_Frag());
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //mFragmentTransaction.addToBackStack(null); //You don't have to add ft.addToBackStack(null); while adding first fragment.
        mFragmentTransaction.commit();

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
                    //Toast.makeText(TABBAR.this,"action_info",Toast.LENGTH_LONG).show();
                    MainActivity.alert(TABBAR.this);
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
                        getResources().getDrawable(R.drawable.ic_dashboard_white_24dp), Color.WHITE
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
//                        setTint( getResources().getDrawable(R.drawable.ic_info_outline_black_36dp), Color.BLACK), Color.RED
                ).build()
        );
        navigationTabBar.setModels(models5);
        navigationTabBar.setModelIndex(0, true);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                int page = index;
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                switch(page){
                    case 0:
                        fragment_to_open = new Timeline_Frag();
                        break;
                    case 1:
                        fragment_to_open = new NewRequest_Frag();
                        break;
                    case 2:
                        fragment_to_open = new BlankFragment();
                        break;
                    case 3:
                        fragment_to_open = new BlankFragment();
                        break;
                    default:
                }
                if(fragment_to_open != null){
                    fragmentTransaction.replace(R.id.container, fragment_to_open);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                //Toast.makeText(TABBAR.this,"onEndTabSelected :: "+index,Toast.LENGTH_SHORT).show();
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
            boolean status = clearBackStack();
            if(status == true){
                //System.out.println("ready to close");
            }
        }
    }

    private boolean clearBackStack(){
        boolean status = false;
        navigationTabBar.setModelIndex(0, true);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            status = true;
        }
        while(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
            //status = false;
        }
        return status;
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

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }


}
