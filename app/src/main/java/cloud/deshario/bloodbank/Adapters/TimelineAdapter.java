package cloud.deshario.bloodbank.Adapters;

/**
 * Created by Deshario on 7/30/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cloud.deshario.bloodbank.Fragments.Timeline_Frag;
import cloud.deshario.bloodbank.Models.Requester;
import cloud.deshario.bloodbank.R;
import cloud.deshario.bloodbank.TABBAR;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ContactViewHolder> {

    private List<Requester> contactList;
    Context context;

    public TimelineAdapter(List<Requester> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public Requester getItem(int position) {
        return contactList.get(position);
    }


    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Requester ci = contactList.get(i);
        contactViewHolder.requester.append(ci.getRequester_name());
        contactViewHolder.bloodgroup.append(ci.getRequester_bloodgroup());
        contactViewHolder.reason.append(ci.getRequester_reason());
        contactViewHolder.location.append(ci.getRequester_location());
        contactViewHolder.tel.append(ci.getRequester_tel());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_timeline, viewGroup, false);
        context = viewGroup.getContext();
        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView requester;
        private TextView bloodgroup;
        private TextView reason;
        private TextView location;
        private TextView tel;
        private TextView show_img;
        private ImageButton btn_opt;
        private Button btn_accept;
        private Button btn_reject;
        private Button btn_navigate;

        public ContactViewHolder(View view) {
            super(view);
            requester =  (TextView) view.findViewById(R.id.requester_name);
            bloodgroup =  (TextView) view.findViewById(R.id.bloodgroup);
            reason =  (TextView) view.findViewById(R.id.reason);
            location =  (TextView) view.findViewById(R.id.location);
            tel =  (TextView) view.findViewById(R.id.tel);
            show_img =  (TextView) view.findViewById(R.id.show_image_btn);
            btn_opt =  (ImageButton) view.findViewById(R.id.record_options);
            btn_accept =  (Button) view.findViewById(R.id.accept_btn);
            btn_reject =  (Button) view.findViewById(R.id.reject_btn);
            btn_navigate =  (Button) view.findViewById(R.id.navigate_btn);

            Drawable drawable = TABBAR.rotateDrawable(context,R.drawable.ic_place_white_24dp,180);
            bloodgroup.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            modify_tint(bloodgroup,context.getResources().getColor(R.color.material_red));

            Drawable phone = TABBAR.rotateDrawable(context,android.R.drawable.stat_sys_phone_call,60);
            tel.setCompoundDrawablesWithIntrinsicBounds(phone, null, null, null);
            modify_tint(tel,context.getResources().getColor(R.color.material_red));

            // android:drawabletint only works on Api 23 or higher
            int api_version = Build.VERSION.SDK_INT;
            if (api_version < 23) {
                TextView[] textViews = {requester,bloodgroup,reason,tel};
                modify_tint(textViews,context.getResources().getColor(R.color.material_red));
                modify_tint(btn_accept,context.getResources().getColor(R.color.primary_deshario));
                modify_tint(btn_reject,context.getResources().getColor(R.color.material_red));
                modify_tint(btn_navigate,context.getResources().getColor(R.color.success_bootstrap));
                modify_tint(location,context.getResources().getColor(R.color.default_bootstrap));
            }



            btn_opt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    Timeline_Frag.alert(context,requester);
                }
            });

            show_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    //Toast.makeText(context,"Image "+requester.getRequester_location()+" : Loading",Toast.LENGTH_SHORT).show();
                    Timeline_Frag.show_image(context,requester);
                }
            });

            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    Toast.makeText(context,"Location "+requester.getRequester_location()+" : Accepted",Toast.LENGTH_SHORT).show();
                }
            });

            btn_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    Toast.makeText(context,"Location "+requester.getRequester_location()+" : Rejected",Toast.LENGTH_SHORT).show();
                }
            });

            btn_navigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    Toast.makeText(context,"Navigation to Location "+requester.getRequester_location()+" : Started",Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void modify_tint(TextView[] textViews, int color){
            for(int i=0; i<textViews.length; i++){
                Drawable[] drawables = textViews[i].getCompoundDrawables();
                if (drawables[0] != null) {  // left drawable
                    drawables[0].setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                }
            }
        }

        private void modify_tint(Button button, int color){
            Drawable[] drawables = button.getCompoundDrawables();
            if (drawables[0] != null) {  // left drawable
                drawables[0].setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        }

        public void modify_tint(TextView textView, int color){
            Drawable[] drawables = textView.getCompoundDrawables();
            if (drawables[0] != null) {  // left drawable
                drawables[0].setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        }

    }
}