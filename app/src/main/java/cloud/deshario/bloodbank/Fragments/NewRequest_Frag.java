package cloud.deshario.bloodbank.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

import cloud.deshario.bloodbank.AbsRuntimePermission;
import cloud.deshario.bloodbank.MainActivity;
import cloud.deshario.bloodbank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequest_Frag extends Fragment {

    EditText user,email,loc,age,image_field;
    Button btn_request,sel_img_btn;


    public NewRequest_Frag() {
        // Required empty public constructor
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private static final int REQUEST_PERMISSION = 10;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;
    private boolean permissionIsGranted = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_request, container, false);

        CardView recyclerView = (CardView)view.findViewById(R.id.card_view);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        LinearLayout bloodrequest = (LinearLayout)view.findViewById(R.id.main_linear);
        GradientDrawable drawable = new GradientDrawable();
        //drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(8, getResources().getColor(R.color.material_red));
        drawable.setCornerRadius(0);
        bloodrequest.setBackgroundDrawable(drawable);

        user = (EditText)view.findViewById(R.id.username);
        email = (EditText)view.findViewById(R.id.email);
        loc = (EditText)view.findViewById(R.id.location);
        age = (EditText)view.findViewById(R.id.myage);
        image_field = (EditText)view.findViewById(R.id.image);
        image_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_image(getActivity(),"Reason");
            }
        });


        btn_request = (Button)view.findViewById(R.id.request_btn);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_user = user.getText().toString();
                String str_email = email.getText().toString();
                String str_loc = loc.getText().toString();
                String str_age = age.getText().toString();
                if(str_user.isEmpty() || str_email.isEmpty() || str_loc.isEmpty() || str_age.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view, "Please fill all the fields !", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    Snackbar snackbar = Snackbar.make(view, "Ready To Request", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        return view;
    }

    public void show_image(final Context context,String reason){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_image, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setDimAmount(0.7f);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button sel_img_btn = (Button)view.findViewById(R.id.select_image);
        sel_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission_work(getActivity());
                //selectImage(context);
                //Toast.makeText(context,"Select Image",Toast.LENGTH_SHORT).show();
            }
        });
        final TextView title = (TextView)view.findViewById(R.id.img_title);
        title.setText(reason);
        ImageButton img = (ImageButton)view.findViewById(R.id.close_modal);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void permission_work(Context context){
        boolean android_version = version_android();
        if(android_version == true){ // Android >= MARSHMALLOW
            boolean access = checkPermission(context,PERMISSIONS_STORAGE[1]);
            if(access == true){ // If Permission Granted
                Toast.makeText(context,"Permission Already Granted",Toast.LENGTH_SHORT).show();
            }else{ // Ask Permission
                request(context);
            }
        }else{
            Toast.makeText(context, "Version > Marshmallow", Toast.LENGTH_SHORT).show();
            permissionIsGranted = true;
        }
    }
    // Select image from camera and gallery
//    private void selectImage(final Context context) {
//        ImageView imageview;
//        Button btnSelectImage;
//        Bitmap bitmap;
//        File destination = null;
//        InputStream inputStreamImg;
//        String imgPath = null;
//        int sel = 0;
//
//        final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
//        try {
//            PackageManager pm = context.getPackageManager();
////            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, context.getPackageName());
//            boolean hasPerm = verifyStoragePermissions(getActivity());
////            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
//            if (hasPerm == true) {
//                final CharSequence[] options = {"Take Photo", "Choose From Gallery"};
//               AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Select Option");
//                builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener(){
//                    public void onClick(DialogInterface dialog, int item) {
//                        ListView lv = ((AlertDialog)dialog).getListView();
//                        lv.setTag(new Integer(item));
//                    }
//                });
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lv = ((AlertDialog)dialog).getListView();
//                        Integer selected = (Integer)lv.getTag();
//                        if(selected != null) {
//                            if(selected == 0){
//                                dialog.dismiss();
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, PICK_IMAGE_CAMERA);
//                            }else if(selected == 1){
//                                dialog.dismiss();
//                                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
//                            }else{
//                                dialog.dismiss();
//                            }
//                        }else{ // Null
//                           // Toast.makeText(context,"selected = "+selected,Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builder.show();
//            } else{
//                Toast.makeText(context, "Camera Permission error", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(context, "Camera Permission error", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }




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

    private static boolean checkPermission(Context context, String permission_name){
        return ActivityCompat.checkSelfPermission(context, permission_name) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionIsGranted = true;
                    Toast.makeText(getActivity(),"permission granted",Toast.LENGTH_SHORT).show();
                } else {
                    permissionIsGranted = false;
                    manual_permission(getActivity());
                }
                break;
        }
    }

    public void manual_permission(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Enable Permission");
        dialog.setIcon(R.drawable.user_male);
        dialog.setMessage("Enable Permission Manually From Settings !");
        dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.setData(Uri.parse("package:" + getActivity().getPackageName()));
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
