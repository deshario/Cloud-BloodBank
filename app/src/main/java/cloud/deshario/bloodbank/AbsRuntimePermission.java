package cloud.deshario.bloodbank;

/**
 * Created by Deshario on 8/2/2017.
 */


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;

import static android.R.id.message;

/**
 * Created by NgocTri on 7/4/2016.
 */
public abstract class AbsRuntimePermission extends AppCompatActivity {
    private SparseIntArray mErrorString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();

    }

    public abstract void onPermissionsGranted(int requestCode);

    public void requestAppPermissions(final String[]requestedPermissions, final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;
        for(String permission: requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            showRequestPermissions = showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck!=PackageManager.PERMISSION_GRANTED) {
            if(showRequestPermissions) {

                ask(requestedPermissions,requestCode);

//                Snackbar.make(findViewById(android.R.id.content), stringId, Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ActivityCompat.requestPermissions(AbsRuntimePermission.this, requestedPermissions, requestCode);
//                    }
//                }).show();
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int permisson : grantResults) {
            permissionCheck = permissionCheck + permisson;
        }

        if( (grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionsGranted(requestCode);
        } else {
            //Display message when contain some Dangerous permisson not accept

            manual_permission(requestCode);
//            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode),
//                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent();
//                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    i.setData(Uri.parse("package:" + getPackageName()));
//                    i.addCategory(Intent.CATEGORY_DEFAULT);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                    startActivity(i);
//                }
//            }).show();
        }
    }

    public void ask(final String[] requestedPermissions, final int requestCode){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Permission");
        dialog.setIcon(R.drawable.user_male);
        dialog.setMessage("Alert message to be shown");
        dialog.setPositiveButton("ASK PERMISSion", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("ask ok");
                dialog.dismiss();
                ActivityCompat.requestPermissions(AbsRuntimePermission.this, requestedPermissions, requestCode);
            }
        });
        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Dismiss ok");
            }
        }).show();
    }

    public void manual_permission(int requestCode){
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
