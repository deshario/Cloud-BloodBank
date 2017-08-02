package cloud.deshario.bloodbank;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{

    Button donate_btn,request_btn,campagin_btn;
    AlertDialog alertDialog;
    private static final int REQUEST_PERMISSION = 10;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;
    private static boolean permissionIsGranted = false;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setIcon(R.mipmap.blood_icon);
        binder();
        donate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean android_version = version_android();
//                if(android_version == true){ // Android >= MARSHMALLOW
//                    boolean access = checkPermission(MainActivity.this,PERMISSIONS_STORAGE[0]);
//                    if(access == true){ // If Permission Granted
//                        Toast.makeText(MainActivity.this,"permission already granted",Toast.LENGTH_SHORT).show();
//                    }else{ // Ask Permission
//                        request(MainActivity.this);
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this, "Lower than marshmallow", Toast.LENGTH_SHORT).show();
//                    permissionIsGranted = true;
//                }
                startActivity(new Intent(MainActivity.this,TABBAR.class));
            }
        });
        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_dialog();
            }
        });
        campagin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert(MainActivity.this);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }

    public void binder(){
        donate_btn = (Button)findViewById(R.id.donate);
        request_btn = (Button)findViewById(R.id.request);
        campagin_btn = (Button)findViewById(R.id.campaigns);

    }

    public void alert_dialog(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.request_blood, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setDimAmount(0.5f);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final EditText loc = (EditText)view.findViewById(R.id.location);
        Button btn_request = (Button)view.findViewById(R.id.request_btn);
        ImageButton img = (ImageButton)view.findViewById(R.id.close_modal);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = loc.getText().toString();
                if(location == null || location.isEmpty()){
                    Toast.makeText(MainActivity.this,"Location is null !",Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this,Donatertab.class));
                }
            }
        });
    }

    public static void alert(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_bottom, null);
        //Spinner spin1 = (Spinner) view.findViewById(R.id.spin1);
        //Spinner spin2 = (Spinner) view.findViewById(R.id.spin2);
        // ListView catList = (ListView) view.findViewById(R.id.listItems);
        //Button btnDone = (Button) view.findViewById(R.id.btnDone);
        Button btnok = (Button) view.findViewById(R.id.ok_btn);

        final Dialog mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        String[] FRUITS = { "Apple", "Avocado", "Banana",
                "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
                "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };



        //spin1.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, FRUITS));
        //spin2.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, FRUITS));
        ArrayAdapter adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, FRUITS);
        //catList.setAdapter(adapter);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

// btnDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBottomSheetDialog.dismiss();
//            }
//        });
    }


    private void request(Context context){
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private static boolean version_android(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return true;
        }else{
            return false;
        }
    }

    private static boolean checkPermission(Context context, String permission_name) {
        return ActivityCompat.checkSelfPermission(context, permission_name) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionIsGranted = true;
                    Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
                } else {
                    permissionIsGranted = false;
                    manual_permission();
                }
                break;
        }
    }

    public void manual_permission(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Permission");
        dialog.setIcon(R.drawable.user_male);
        dialog.setMessage("Enable Permission Manually From Settings !");
        dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.setData(Uri.parse("package:" + getPackageName()));
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Dismiss ok");
            }
        }).show();
    }
}
