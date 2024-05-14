package com.example.newsandroidproject.fragment;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        LinearLayout layoutSettingPage = rootView.findViewById(R.id.layoutSettingPage);
        ConstraintLayout csUserSetting = rootView.findViewById(R.id.csUserSetting);
        CardView userSetting = rootView.findViewById(R.id.crdvUserSetting);
        CardView historySetting = rootView.findViewById(R.id.crdvHistory);
        CardView helpSetting = rootView.findViewById(R.id.crdvHelp);
        CardView aboutSetting = rootView.findViewById(R.id.crdvAbout);
        CardView logout = rootView.findViewById(R.id.crdvLogout);
        ListView detailsUserSetting = rootView.findViewById(R.id.detailsUserSetting);
        layoutSettingPage.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        csUserSetting.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        userSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandUserSetting(csUserSetting, detailsUserSetting);
            }
        });
        historySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistoryFragment();
            }
        });
        helpSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        aboutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }

    private void openHistoryFragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.openHistoryFragment();
        }
    }

    private void expandUserSetting(ConstraintLayout csUserSetting, ListView detailsUserSetting){
        String data[] = {"Thông tin cá nhân", "Thay đổi mật khẩu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        detailsUserSetting.setAdapter(adapter);
        int setList = (detailsUserSetting.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(csUserSetting, new AutoTransition());
        detailsUserSetting.setVisibility(setList);

        detailsUserSetting.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?>arg0, View arg1, int arg2, long arg3) {
                        System.out.println("Test click on details user setting: " + data[arg2]);
                    }
                });
    }
}