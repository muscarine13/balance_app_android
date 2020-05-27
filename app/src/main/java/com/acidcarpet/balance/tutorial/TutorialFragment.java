package com.acidcarpet.balance.tutorial;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.acidcarpet.balance.R;

/**
 A simple {@link Fragment} subclass.
 Use the {@link TutorialFragment#newInstance} factory method to
 create an instance of this fragment.
 */
public class TutorialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";


    // TODO: Rename and change types of parameters
    private TutorialActivity.Screen type;


    Button ok_button;
    TextView text_label;

    public TutorialFragment() {
        // Required empty public constructor
    }

    public static TutorialFragment newInstance(TutorialActivity.Screen type) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type.getText());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        if (getArguments() != null) {

            if(getArguments().getString(ARG_PARAM1).equals("INTRO")){
                type = TutorialActivity.Screen.INTRO;
                return;
            }
            if(getArguments().getString(ARG_PARAM1).equals("GOOD")){
                type = TutorialActivity.Screen.GOOD;
                return;
            }
            if(getArguments().getString(ARG_PARAM1).equals("BAD")){
                type = TutorialActivity.Screen.BAD;
                return;
            }
            if(getArguments().getString(ARG_PARAM1).equals("SUMMARY")){
                type = TutorialActivity.Screen.SUMMARY;
                return;
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View out = inflater.inflate(R.layout.fragment_tutorial, container, false);

        ok_button = (Button) out.findViewById(R.id.tutorial_ok_button);
        text_label = (TextView) out.findViewById(R.id.tutorial_text_label);

        switch (type){
            case INTRO:
                text_label.setText(R.string.tutorial_intro_text);
                text_label.setTextColor(getResources().getColor(R.color.my_text, getResources().newTheme()));
                break;
            case GOOD:
                text_label.setText(R.string.tutorial_good_text);
                text_label.setTextColor(getResources().getColor(R.color.my_green, getResources().newTheme()));
                break;
            case BAD:
                text_label.setText(R.string.tutorial_bad_text);
                text_label.setTextColor(getResources().getColor(R.color.my_red, getResources().newTheme()));
                break;
            case SUMMARY:
                text_label.setText(R.string.tutorial_summary_text);
                text_label.setTextColor(getResources().getColor(R.color.my_text, getResources().newTheme()));
                break;
            default:
                text_label.setText("");
        }

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialActivity.change_screen();
                startActivity(new Intent(getContext(), TutorialActivity.class));
            }
        });


        return  out;
    }
}