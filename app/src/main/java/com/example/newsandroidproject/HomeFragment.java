package com.example.newsandroidproject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.model.MinimalArticleModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // View
    private Toolbar toolbar;
    private ImageButton nav_menu_button;
    private MenuItem logoItem;
    private MenuItem searchItem;
    private RecyclerView categories_recy;
    private Spinner filtersSpinner;

    // Data - Adapter - Query Data Func - Data Changed - Set Up Adapter
    //// Category
    ArrayList<String> categories;
    CategoryRecycleViewAdapter category_recycle_view_adapter;
    RecyclerView article_recycle_view;
    private void queryCategories() {
        categories.add("Tất cả");
        categories.add("Thời sự");
        categories.add("Chính trị");
        categories.add("Thế giới");
        categories.add("Khoa học");
        categories.add("Kinh tế");
        categories.add("Thể thao");
        categories.add("Công nghệ");
        categories.add("Giáo dục");
        categories.add("Pháp luật");
        categories.add("Sức khỏe");
        categories.add("Văn hóa");
        categories.add("Du lịch");
        categories.add("Giải trí");
        categories.add("Khám phá");
        categories.add("Đời sống");
        categories.add("Nghệ thuật");
        categories.add("Bất động sản");
    }
    @SuppressLint("NotifyDataSetChanged")
    private void notifyCategoriesChanged() {
        category_recycle_view_adapter.notifyDataSetChanged();
    }
    private void setUpCategoriesRecycleViewAdapter() {
        categories = new ArrayList<>();
        category_recycle_view_adapter = new CategoryRecycleViewAdapter(getActivity(), categories);
        categories_recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categories_recy.setAdapter(category_recycle_view_adapter);
    }
    ////Filter Items
    ArrayList<String> filters;
    FilterSpinnerAdapterArray filtersAdapter;
    private void queryFilters() {
        filters.add("Mới nhất");
        filters.add("Hôm nay");
        filters.add("Tuần qua");
        filters.add("Tháng qua");
    }
    private void notifyFiltersChanged() {
        filtersAdapter.notifyDataSetChanged();
    }
    private void setUpFiltersSpinnerAdapter() {
        filters = new ArrayList<>();
        filtersAdapter = new FilterSpinnerAdapterArray(getActivity(), filters);
        filtersSpinner.setAdapter(filtersAdapter);
    }

    ////MinimalArticleModel
    ArrayList<MinimalArticleModel> articles;
    ArticleRecycleViewAdapter articlesAdapter;
    private void queryArticles() {
        Bitmap thumbnailBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thumbnail);
        Bitmap authorImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ava);


        articles.add(new MinimalArticleModel(
                "Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND TP.HCM xét xử sơ thẩm vụ án Trương Mỹ Lan - Vạn Thịnh Phát, nhiều nội dung tranh luận giữa Viện KSND TP.HCM (Viện kiểm sát) và luật sư về tội danh, thiệt hại, vai trò đồng phạm giúp sức nhưng chưa thống nhất quan điểm. HĐXX đang nghị án.",
                "Lê Quốc Minh",
                100, // Số lượt xem
                50,  // Số lượt bình luận
                200, // Số lượt theo dõi
                thumbnailBitmap,
                authorImageBitmap,
                new Date() // Thời gian đăng bài
        ));
        articles.add(new MinimalArticleModel(
                "Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND TP.HCM xét xử sơ thẩm vụ án Trương Mỹ Lan - Vạn Thịnh Phát, nhiều nội dung tranh luận giữa Viện KSND TP.HCM (Viện kiểm sát) và luật sư về tội danh, thiệt hại, vai trò đồng phạm giúp sức nhưng chưa thống nhất quan điểm. HĐXX đang nghị án.",
                "Lê Quốc Minh",
                100, // Số lượt xem
                50,  // Số lượt bình luận
                200, // Số lượt theo dõi
                thumbnailBitmap,
                authorImageBitmap,
                new Date() // Thời gian đăng bài
        ));
        articles.add(new MinimalArticleModel(
                "Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND TP.HCM xét xử sơ thẩm vụ án Trương Mỹ Lan - Vạn Thịnh Phát, nhiều nội dung tranh luận giữa Viện KSND TP.HCM (Viện kiểm sát) và luật sư về tội danh, thiệt hại, vai trò đồng phạm giúp sức nhưng chưa thống nhất quan điểm. HĐXX đang nghị án.",
                "Lê Quốc Minh",
                100, // Số lượt xem
                50,  // Số lượt bình luận
                200, // Số lượt theo dõi
                thumbnailBitmap,
                authorImageBitmap,
                new Date() // Thời gian đăng bài
        ));
        articles.add(new MinimalArticleModel(
                "Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND TP.HCM xét xử sơ thẩm vụ án Trương Mỹ Lan - Vạn Thịnh Phát, nhiều nội dung tranh luận giữa Viện KSND TP.HCM (Viện kiểm sát) và luật sư về tội danh, thiệt hại, vai trò đồng phạm giúp sức nhưng chưa thống nhất quan điểm. HĐXX đang nghị án.",
                "Lê Quốc Minh",
                100, // Số lượt xem
                50,  // Số lượt bình luận
                200, // Số lượt theo dõi
                thumbnailBitmap,
                authorImageBitmap,
                new Date() // Thời gian đăng bài
        ));
        articles.add(new MinimalArticleModel(
                "Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND TP.HCM xét xử sơ thẩm vụ án Trương Mỹ Lan - Vạn Thịnh Phát, nhiều nội dung tranh luận giữa Viện KSND TP.HCM (Viện kiểm sát) và luật sư về tội danh, thiệt hại, vai trò đồng phạm giúp sức nhưng chưa thống nhất quan điểm. HĐXX đang nghị án.",
                "Lê Quốc Minh",
                100, // Số lượt xem
                50,  // Số lượt bình luận
                200, // Số lượt theo dõi
                thumbnailBitmap,
                authorImageBitmap,
                new Date() // Thời gian đăng bài
        ));
        articles.add(new MinimalArticleModel(
                "Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND TP.HCM xét xử sơ thẩm vụ án Trương Mỹ Lan - Vạn Thịnh Phát, nhiều nội dung tranh luận giữa Viện KSND TP.HCM (Viện kiểm sát) và luật sư về tội danh, thiệt hại, vai trò đồng phạm giúp sức nhưng chưa thống nhất quan điểm. HĐXX đang nghị án.",
                "Lê Quốc Minh",
                100, // Số lượt xem
                50,  // Số lượt bình luận
                200, // Số lượt theo dõi
                thumbnailBitmap,
                authorImageBitmap,
                new Date() // Thời gian đăng bài
        ));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void notifyArticlesChanged() {
        articlesAdapter.notifyDataSetChanged();
    }
    private void setUpArticlesRecycleViewAdapter() {
        articles = new ArrayList<>();
        articlesAdapter = new ArticleRecycleViewAdapter(getActivity(), articles);
        article_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        article_recycle_view.setAdapter(articlesAdapter);
    }







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private void initiateAdapters() {
        setUpCategoriesRecycleViewAdapter();
        queryCategories();
        notifyCategoriesChanged();

        setUpFiltersSpinnerAdapter();
        queryFilters();
        notifyFiltersChanged();

        setUpArticlesRecycleViewAdapter();
        queryArticles();
        Log.e("TAG", articles.get(0).toString());

        notifyArticlesChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categories_recy = view.findViewById(R.id.categories_recy);
        toolbar = view.findViewById(R.id.tool_bar);
        nav_menu_button = view.findViewById(R.id.left_navigation_menu);
        filtersSpinner = view.findViewById(R.id.filters_spinner);
        article_recycle_view = view.findViewById(R.id.article_recycle_view);
        initiateAdapters();

        // Envent handler
        nav_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setOpenNavigationBar();
            }
        });
        return view;
    }
}