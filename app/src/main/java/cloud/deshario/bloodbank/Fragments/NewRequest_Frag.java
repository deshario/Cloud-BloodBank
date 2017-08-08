package cloud.deshario.bloodbank.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.deshario.bloodbank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequest_Frag extends Fragment {

    EditText user,email,loc,age,image_field;
    Button btn_request,btn_clear;


    public NewRequest_Frag() {
        // Required empty public constructor
    }

    private static final int CAMERA_STORAGE_PERM = 1;

    private int REQUEST_CAMERA = 11, SELECT_FILE = 22;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;
    private String bitmap_code;
    private boolean img_inserted = false;

    public String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_request, container, false);

        CardView recyclerView = (CardView)view.findViewById(R.id.card_view);
        int resId = R.anim.layout_anim_slide;
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
            public void onClick(View v){
                String bmp = bitmap_code;
                String img = image_field.getText().toString();
                Bitmap bitmap;
                if(img.isEmpty() || img == ""){
                    Drawable myDrawable = getResources().getDrawable(R.drawable.user_male);
                    Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
                    bitmap = anImage;
                }else{
                    bitmap = decodeBase64(bmp);
                }
                show_image(getActivity(),"Reason",bitmap);
            }
        });

        // android:drawabletint only works on Api 23 or higher
        int api_version = Build.VERSION.SDK_INT;
        if (api_version < 23) {
            EditText[] editTexts = {user, email, loc, age, image_field};
            modify_tint(editTexts, getActivity().getResources().getColor(R.color.default_bootstrap));
        }


        btn_clear = (Button) view.findViewById(R.id.clear_form);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText("");
                email.setText("");
                loc.setText("");
                age.setText("");
                image_field.setText("");
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

    public void show_image(final Context context,String reason, Bitmap bitmap){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_image, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setDimAmount(0.9f);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ivImage = (ImageView)view.findViewById(R.id.my_image);
        ivImage.setImageBitmap(bitmap);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission_work();
            }
        });
        Button sel_img_btn = (Button)view.findViewById(R.id.select_image);
        sel_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission_work();
            }
        });
        final TextView title = (TextView)view.findViewById(R.id.img_title);
        title.setText(reason);
        ImageButton close_btn = (ImageButton)view.findViewById(R.id.close_modal);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(img_inserted == true){
                    BitmapDrawable drawable = (BitmapDrawable) ivImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    String img = encodeTobase64(bitmap);
                    image_field.setText("Image Selected");
                    image_field.setTextColor(getResources().getColor(R.color.primary_deshario));
                    bitmap_code = img;
                }
                dialog.dismiss();
            }
        });
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void modify_tint(EditText[] editTexts, int color){
        for(int i=0; i<editTexts.length; i++){
            Drawable[] drawables = editTexts[i].getCompoundDrawables();
            if (drawables[0] != null) {  // left drawable
                drawables[0].setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        }
    }


    private void permission_work(){
        boolean android_version = is_marshmallow();
        if(android_version == true){ // Android >= MARSHMALLOW
            boolean access = check_permissions(PERMISSIONS_STORAGE);
            if(access == true){ gallery_camera(); } // Granted
        }else{ // Lower than MarshMallow
            gallery_camera();
        }
    }

    private static boolean is_marshmallow(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return true;
        }else{
            return false;
        }
    }

    private boolean check_permissions(String[] permissions){
        ArrayList<String> granted_permissions = new ArrayList<String>();
        ArrayList<String> required_permissions = new ArrayList<String>();
        for(int i=0; i<permissions.length; i++){
            if(ActivityCompat.checkSelfPermission(getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED){
                required_permissions.add(permissions[i]);
            }else{
                granted_permissions.add(permissions[i]);
            }
        }

        //if(granted_permissions.size() == 2){
        //    System.out.println("All Permission Granted");
        //}

        if (!required_permissions.isEmpty()) { // denied permissions
            requestPermissions(required_permissions.toArray(new String[required_permissions.size()]),CAMERA_STORAGE_PERM );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        ArrayList<String> denied_per = new ArrayList<String>();
        switch (requestCode) {
            case CAMERA_STORAGE_PERM:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }

                if(perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    denied_per.add("STORAGE");
                }

                if(perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    denied_per.add("CAMERA");
                }

                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    gallery_camera();
                }else{
                    manual_permission(getActivity(),denied_per);
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void request(Context context, String permission){
        //requestPermissions(new String[]{permission}, STORAGE_PERMISSION_CODE);
        //requestPermissions(PERMISSIONS_STORAGE,CAMERA_STORAGE_PERM );
    }

    public void manual_permission(Context context, ArrayList<String> denied_permissions){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        View view = getActivity().getLayoutInflater().inflate(R.layout.permission_denied, null);
        dialog.setView(view);

        TextView storage_title = (TextView)view.findViewById(R.id.denied_title_1);
        TextView storage_explain = (TextView)view.findViewById(R.id.denied_data_1);
        TextView camera_title = (TextView)view.findViewById(R.id.denied_title_2);
        TextView camera_explain = (TextView)view.findViewById(R.id.denied_data_2);

        boolean storage = false;
        boolean camera = false;

        for(int i=0; i<denied_permissions.size(); i++){
            if (denied_permissions.get(i).equals("STORAGE")){ storage = true; }
            if (denied_permissions.get(i).equals("CAMERA")){ camera = true; }
        }

        if (storage == false){
            storage_title.setVisibility(View.GONE);
            storage_explain.setVisibility(View.GONE);
        }

        if (camera == false){
            camera_title.setVisibility(View.GONE);
            camera_explain.setVisibility(View.GONE);
        }

        dialog.setPositiveButton("Enable Manually", new DialogInterface.OnClickListener() {
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
                dialog.dismiss();
            }
        });

        AlertDialog mydialog = dialog.create();
        mydialog.show();

        WindowManager.LayoutParams lp = mydialog.getWindow().getAttributes();
        lp.dimAmount = 1f;
        mydialog.getWindow().setAttributes(lp);
        mydialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    public void gallery_camera(){
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = check_permissions(PERMISSIONS_STORAGE);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivImage.setImageBitmap(thumbnail);
        img_inserted = true;
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
        img_inserted = true;
    }
}
