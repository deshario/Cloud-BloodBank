package cloud.deshario.bloodbank.Fragments;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.deshario.bloodbank.Adapters.TimelineAdapter;
import cloud.deshario.bloodbank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequest_Frag extends Fragment {

    EditText user,email,loc,age;
    Button btn_request;


    public NewRequest_Frag() {
        // Required empty public constructor
    }


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

}
