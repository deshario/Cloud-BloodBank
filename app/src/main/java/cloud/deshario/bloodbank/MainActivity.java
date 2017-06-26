package cloud.deshario.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;

public class MainActivity extends AppCompatActivity {

    Button donate_btn,request_btn,campagin_btn;

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
                Toast.makeText(MainActivity.this,"Donate in process !",Toast.LENGTH_SHORT).show();
            }
        });
        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Request in process !",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Donatertab.class));
            }
        });
        campagin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Searching Campaign ...",Toast.LENGTH_SHORT).show();

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
}
