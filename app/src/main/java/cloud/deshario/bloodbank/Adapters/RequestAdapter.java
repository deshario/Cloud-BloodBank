package cloud.deshario.bloodbank.Adapters;

/**
 * Created by Deshario on 7/30/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cloud.deshario.bloodbank.Fragments.TestFragment;
import cloud.deshario.bloodbank.Models.Requester;
import cloud.deshario.bloodbank.R;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ContactViewHolder> {

    private List<Requester> contactList;
    Context context;

    public RequestAdapter(List<Requester> contactList) {
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
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycardview, viewGroup, false);
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

            btn_opt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    TestFragment.alert(context,requester);
                }
            });

            show_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getPosition();
                    Requester requester = getItem(itemPosition);
                    //Toast.makeText(context,"Image "+requester.getRequester_location()+" : Loading",Toast.LENGTH_SHORT).show();
                    TestFragment.show_image(context,requester);
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
    }
}