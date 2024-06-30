package com.example.newsandroidproject.fragment;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.UserInfoActivity;
import com.example.newsandroidproject.api.UserApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.UserNavigationMenu;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {
    private Toolbar toolbar;
    private UserApi userApi;
    private UserNavigationMenu userSetting;
    private ShapeableImageView ic_usrSetting;
    private TextView txtUsrNameSetting;
    private CardView crdvUserSetting, historySetting, helpSetting, aboutSetting, logout;
    LinearLayout layoutSettingPage;
    ConstraintLayout csUserSetting;
    ListView detailsUserSetting;
    View rootView;

    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        toolbar = rootView.findViewById(R.id.topToolBarSettingPage);
        layoutSettingPage = rootView.findViewById(R.id.layoutSettingPage);
        csUserSetting = rootView.findViewById(R.id.csUserSetting);
        crdvUserSetting = rootView.findViewById(R.id.crdvUserSetting);
        ic_usrSetting = rootView.findViewById(R.id.ic_usrSetting);
        txtUsrNameSetting = rootView.findViewById(R.id.txtUsrNameSetting);
        historySetting = rootView.findViewById(R.id.crdvHistory);
        helpSetting = rootView.findViewById(R.id.crdvHelp);
        aboutSetting = rootView.findViewById(R.id.crdvAbout);
        logout = rootView.findViewById(R.id.crdvLogout);
        detailsUserSetting = rootView.findViewById(R.id.detailsUserSetting);
        layoutSettingPage.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        csUserSetting.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        loadData();

        return rootView;
    }

    private void loadData() {
        userApi = RetrofitService.getClient(getContext()).create(UserApi.class);
        userApi.getUserNavigationMenu().enqueue(new Callback<UserNavigationMenu>() {
            @Override
            public void onResponse(Call<UserNavigationMenu> call, Response<UserNavigationMenu> response) {
                if(response.body() != null){
                    userSetting = response.body();
                    setClickEvent(userSetting.getUserId());
                    if (userSetting.getAvatar() != null) {
                        byte[] avatarByteData = Base64.decode(userSetting.getAvatar(), Base64.DEFAULT);
                        ic_usrSetting.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
                    }
                    else{
                        ic_usrSetting.setImageResource(R.drawable.ic_blank_avatar);
                    }
                    txtUsrNameSetting.setText(userSetting.getName());
                }
                else{
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserNavigationMenu> call, Throwable throwable) {
                Log.d("Test API", "Failure: " + throwable.getMessage());
            }
        });
    }

    private void setClickEvent(Long userId){
        crdvUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandUserSetting(csUserSetting, detailsUserSetting, userId);
            }
        });
        historySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistoryFragment();
            }
        });
        favoriteSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavoriteFragment();
            }
        });
        seeLaterSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeeLaterFragment();
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
    }

    private void openFavoriteFragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.openFavoriteFragment();
        }
    }

    private void openSeeLaterFragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.openSeeLaterFragment();
        }
    }

    private void openHistoryFragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.openHistoryFragmentWithSelectedIds();
        }
    }

    private void expandUserSetting(ConstraintLayout csUserSetting, ListView detailsUserSetting, Long userId){
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
                        if(arg2 == 0){
                            goToUserInfoPage(userId);
                            // Chỗ này chưa có page sửa thông tin nên dùng tạm UserInfoPage
                        }
                    }
                });
    }
    private void goToUserInfoPage(Long userId) {
        Intent myIntent = new Intent(getContext(), UserInfoActivity.class);
        Bundle myBunble = new Bundle();
        myBunble.putLong("userId", userId);

        myIntent.putExtra("myPackage", myBunble);
        getContext().startActivity(myIntent);
    }
}