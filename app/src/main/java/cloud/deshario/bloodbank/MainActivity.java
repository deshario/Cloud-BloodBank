package cloud.deshario.bloodbank;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


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

}
