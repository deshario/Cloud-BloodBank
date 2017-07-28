package cloud.deshario.bloodbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;

public class MainActivity extends AppCompatActivity {

    Button donate_btn,request_btn,campagin_btn;
    AlertDialog alertDialog;

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
                //alert();
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
                BottomDialog bottomDialog = new BottomDialog.Builder(MainActivity.this)
                        .setTitle("No Campaign Available Yet !")
                        .setContent("We will inform you when campaign are ready.")
                        .setPositiveText("OK")
                        //.setCustomView(customView, 0, 0, 0, 0)
                        .build();
                bottomDialog.show();
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

    public void alert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("หัวข้อใส่ตรงนี้");
        alertDialogBuilder
                .setMessage("ใส่ข้อมูลตรงนี้")
                .setCancelable(true)
                .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
