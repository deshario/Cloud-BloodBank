package cloud.deshario.bloodbank.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cloud.deshario.bloodbank.Adapters.TimelineAdapter;
import cloud.deshario.bloodbank.Models.Requester;
import cloud.deshario.bloodbank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Timeline_Frag extends Fragment {


    public Timeline_Frag(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        TimelineAdapter ca = new TimelineAdapter(createList(2));
        recyclerView.setAdapter(ca);
        int resId = R.anim.layout_anim_slide;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        return view;
    }

    private List<Requester> createList(int size){
        List<Requester> result = new ArrayList<Requester>();
        for (int i=1; i <= size; i++) {
            Requester ci = new Requester();
            ci.setRequester_name("Name"+i);
            ci.setRequester_bloodgroup("Group"+i);
            ci.setRequester_reason("Accident"+i);
            ci.setRequester_location(""+i);
            ci.setRequester_tel("Tel"+i);
            result.add(ci);
        }
        return result;
    }

    public static void alert(final Context context, final Requester contactInfo){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bottom_dialog_items, null);
        final Dialog btm_dialog = new Dialog(context, R.style.MaterialDialogSheet);
        btm_dialog.setContentView(view);
        btm_dialog.setCancelable(true);
        btm_dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btm_dialog.getWindow().setGravity(Gravity.BOTTOM);
        btm_dialog.show();

        LinearLayout close = (LinearLayout) view.findViewById(R.id.close_layout);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Exit : "+contactInfo.getRequester_location(),Toast.LENGTH_SHORT).show();
                btm_dialog.dismiss();
            }
        });

    }

    public static void show_image(final Context context, Requester requester){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_image, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setDimAmount(0.7f);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button img_btn = (Button)view.findViewById(R.id.select_image);
        img_btn.setVisibility(View.GONE);
        final TextView title = (TextView)view.findViewById(R.id.img_title);
        title.setText(requester.getRequester_reason());
        ImageButton img = (ImageButton)view.findViewById(R.id.close_modal);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
