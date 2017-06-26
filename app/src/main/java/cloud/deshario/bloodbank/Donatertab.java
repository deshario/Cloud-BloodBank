package cloud.deshario.bloodbank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Deshario on 6/26/2017.
 */

public class Donatertab extends AppCompatActivity {
    ListView listview;
    List<HashMap<String, String>> datalist;
    SimpleAdapter simpleAdapter;
    ImageView info_donater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_tab);
        listview = (ListView)findViewById(R.id.list_view);

        dowork();
        info_donater = (ImageView)findViewById(R.id.info);

    }

    public void dowork(){

        datalist = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 10; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("teacher_name", "Donater "+(i+1));
            hm.put("teacher_name", "Donater "+(i+1));
            hm.put("teacher_faculty", "Bloodgroup : "+(i+1));
            hm.put("teacher_img",String.valueOf(R.drawable.user_male));
            hm.put("teacher_id",""+i);
            datalist.add(hm);
        }

        String[] from = {"teacher_img", "teacher_name", "teacher_faculty"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        simpleAdapter = new SimpleAdapter(this, datalist, R.layout.teachers_custom_listview, from, to);
        listview.setAdapter(simpleAdapter);


    }
}
