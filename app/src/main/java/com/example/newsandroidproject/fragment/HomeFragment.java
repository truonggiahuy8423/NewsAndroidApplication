package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.adapter.ArticleRecycleViewAdapter;
import com.example.newsandroidproject.adapter.CategoryRecycleViewAdapter;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.UserApi;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.common.FilterType;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.Category;
import com.example.newsandroidproject.model.User;
import com.example.newsandroidproject.model.dto.PostArticleResponse;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.adapter.FilterSpinnerAdapterArray;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.PostArticleRequestDTO;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Future;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ArticleApi apiService;

    // View
    private Toolbar toolbar;
    private ImageButton nav_menu_button;
    private MenuItem logoItem;
    private MenuItem searchItem;
    private RecyclerView categories_recy;

    // Data - Adapter - Query Data Func - Data Changed - Set Up Adapter
    //// Category
    ArrayList<Category> categories;
    CategoryRecycleViewAdapter category_recycle_view_adapter;
    RecyclerView article_recycle_view;

    ArticleRecycleViewAdapter.PostSectionViewHolder header;




    // Callback interface for category query completion
    public interface OnCategoriesLoadedListener {
        void onCategoriesLoaded();
    }

    private void queryUserData() {
        UserApi apiService = RetrofitService.getClient(getContext()).create(UserApi.class);
        apiService.getUserInfo().enqueue(new Callback<User>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        articlesAdapter.setAvaData(response.body().getAvatar());
//                        System.out.println(response.body().getAvatar());
                        articlesAdapter.notifyItemChanged(0);
                    }
                    // Notify that categories are loaded
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(getContext(), "errorResponse.getMessage()", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                // Notify that categories are loaded even on failure to avoid blocking other setups
            }
        });

    }
    @SuppressLint("NotifyDataSetChanged")
    private void queryCategories(OnCategoriesLoadedListener callback) {
        ArticleApi apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0) {
                        categories.addAll(response.body());
                        category_recycle_view_adapter.notifyDataSetChanged();
                    }
                    // Notify that categories are loaded
                    callback.onCategoriesLoaded();
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(getContext(), "errorResponse.getMessage()", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                // Notify that categories are loaded even on failure to avoid blocking other setups
                callback.onCategoriesLoaded();
            }
        });
    }

    int selectedCategoryIndex = 0;
    int selectedFilter = FilterType.NEWEST;
    private int page_index = 1;
    private Semaphore semaphore = new Semaphore(1);

    // Các biến và hàm khác...

    @SuppressLint("NotifyDataSetChanged")
    private void refreshData() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // Thử lấy semaphore, hủy bỏ các tác vụ khác nếu đang chạy
                    semaphore4.acquire();
                    threadCount++;
                    semaphore4.release();
                    semaphore.acquire();
                    System.out.println("refreshData " + selectedCategoryIndex);
                    page_index = 1;
                    int old_size = articles.size();
                    // Đặt lại trang và xóa dữ liệu cũ
                    articles.clear();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            articlesAdapter.notifyItemRangeRemoved(1, old_size);
                        }
                    });
//                    int old_size = articles.size();
//                    articles.clear();
//                    articlesAdapter.notifyItemRangeRemoved(0, old_size);

                    // Sử dụng runOnUiThread để cập nhật giao diện người dùng
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            articlesAdapter.notifyItemRangeRemoved(0, old_size);
//                        }
//                    });
                    semaphore.release();

                    queryArticlesWhenAlterFilter();
                } catch (Exception e) {
                    // Xử lý bất kỳ ngoại lệ nào, bao gồm cả việc bị hủy
                    Log.d("Refresh Task", "Refresh task was interrupted or cancelled.");
                } finally {
                    // Giải phóng semaphore
                    semaphore.release();
                }
            }
        });
    }

    private void setUpCategoriesRecycleViewAdapter() {
        categories = new ArrayList<>();
        category_recycle_view_adapter = new CategoryRecycleViewAdapter(getActivity(), categories);
        category_recycle_view_adapter.setCategoryItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategoryIndex = category_recycle_view_adapter.getSelectedCategoryIndex();
                refreshData();
            }
        });
        categories_recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categories_recy.setAdapter(category_recycle_view_adapter);


    }

    private List<ArticleRecycleViewAdapter.ArticleViewHolder> viewHolders = new ArrayList<>();
    private final Semaphore semaphore8 = new Semaphore(1);

    public void postArticle(PostArticleRequestDTO article)  {
//        System.out.println("Hellooo" + article.toString());
        Date cre = null, mod = null;
        try {
            cre = DateParser.parseFromISO8601(article.getCreateTime());
            mod = DateParser.parseFromISO8601(article.getModifyTime());
         } catch (ParseException e) {
            Toast.makeText(getContext(), "Parse Error", Toast.LENGTH_SHORT).show();
        }
        ArticleInReadingPageDTO a = new ArticleInReadingPageDTO(
                new ArticleInNewsFeedModel(
                        null, // articleId sẽ được thiết lập sau
                        article.getTitle(),
                        article.getDescription(),
                        article.getThumbnail(),
                        article.getThumbnailName(),
                        cre, mod,
                        article.getViewCount(),
                        article.getCommentCount(),
                        article.getUserId(),
                        article.getUserName(),
                        article.getAvatar(),
                        article.getFollowCount(),
                        article.getSaveCount()), article.getBodyItemList(), article.getCategories(), 0, 0);
        a.isLoading = true;
        articles.add(0, a);
        articlesAdapter.notifyItemInserted(1);

        System.out.println(article);

        ArticleRecycleViewAdapter.ArticleViewHolder viewHolder;
        article_recycle_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore8.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                viewHolders.add((ArticleRecycleViewAdapter.ArticleViewHolder) article_recycle_view.findViewHolderForAdapterPosition(1));
                int position = viewHolders.size() - 1;
                semaphore8.release();
                queryUserData();
                ArticleApi apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
                apiService.postArticle(article).enqueue(new Callback<PostArticleResponse>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<PostArticleResponse> call, Response<PostArticleResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                try {
                                    semaphore8.acquire();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                ArticleRecycleViewAdapter.ArticleViewHolder vh = viewHolders.get(response.body().rvPosition);
                                vh.loadingView.setVisibility(View.GONE);
                                vh.loadingPb.setVisibility(View.GONE);
                                articles.get(response.body().rvPosition).setArticleId(response.body().articleId);
                                if (response.body().rvPosition + 1 == viewHolders.size()) {
                                    viewHolders.clear();
                                }
                                semaphore8.release();

                                Toast.makeText(getContext(), "Đăng thành công, Id bài viết: " + response.body().articleId, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            try {
                                ResponseException errorResponse = JsonParser.parseError(response);
                                System.out.println(errorResponse.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PostArticleResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                        // Notify that categories are loaded even on failure to avoid blocking other setups
                    }
                });

            }
        }, 100);
    }
    public final static int GET_ARTICLE_REQUEST_CODE = 10;

    ////Filter Items
    ArrayList<String> filters;
    FilterSpinnerAdapterArray filtersAdapter;
    private Spinner filtersSpinner;

    private void queryFilters() {
        filters.add("Mới nhất");
        filters.add("Khám phá");
        filters.add("Hôm nay");
        filters.add("Tuần qua");
        filters.add("Tháng qua");
        filtersAdapter.notifyDataSetChanged();
    }

    private void setUpFiltersSpinnerAdapter() {
        filters = new ArrayList<>();
        filtersAdapter = new FilterSpinnerAdapterArray(getActivity(), filters);
        filtersSpinner.setAdapter(filtersAdapter);
    }

    private void handleFilterSelection(String filter) {
        // Implement your logic for handling the selected filter here
        // For example, refreshing the list of articles based on the selected filter
        switch (filter) {
            case "Mới nhất":
                selectedFilter = FilterType.NEWEST;
                break;
            case "Khám phá":
                selectedFilter = FilterType.EXPLORE;
                break;
            case "Hôm nay":
                selectedFilter = FilterType.TODAY;
                break;
            case "Tuần qua":
                selectedFilter = FilterType.WEEK;
                break;
            case "Tháng qua":
                selectedFilter = FilterType.MONTH;
                break;
            default:
                break;
        }
        refreshData();
    }

    ////MinimalArticleModel
    ArrayList<ArticleInNewsFeedModel> articles;
    ArticleRecycleViewAdapter articlesAdapter;
    private Parcelable listState;
    private boolean isLoading = false;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Future<?> currentTask;

    // Các biến và hàm khác...
    private final Semaphore semaphore2 = new Semaphore(1);

    // Các biến và hàm khác...
    @SuppressLint("NotifyDataSetChanged")
    private void queryArticles() {
        // Thực hiện truy vấn trong luồng con
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Acquiring the semaphore
                    semaphore2.acquire();
                    System.out.println("queryArticle " + selectedCategoryIndex);



                    // Vùng critical
//                    System.out.println("queryArticles " + page_index);
                    listState = article_recycle_view.getLayoutManager().onSaveInstanceState();

                    ArticleApi apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
                    apiService.getArticlesInNewsFeed(page_index++, categories.get(selectedCategoryIndex).getCategoryId(), selectedFilter).enqueue(new Callback<List<ArticleInNewsFeedModel>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<ArticleInNewsFeedModel>> call, Response<List<ArticleInNewsFeedModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().size() > 0) {

//                                    System.out.println("queryArticlesSuccess " + response.body().size());
//                                    articlesAdapter.addArticles(response.body(), (Activity) getContext());
                                    int oldSize = articles.size();

//                                    System.out.println("start " + oldSize);
                                    articles.addAll(response.body());

                                    System.out.println("onResponse " + selectedCategoryIndex + "articles size " + articles.size());

//                                    System.out.println("end " + (articles.size() - oldSize));
                                    try {
                                        semaphore4.acquire();
                                        if (threadCount == 1) {
                                            articlesAdapter.notifyItemRangeInserted(oldSize + 1, articles.size() - oldSize);
                                        }
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    threadCount--;
                                    semaphore4.release();

                                }
                            } else {
                                try {
                                    ResponseException errorResponse = JsonParser.parseError(response);
                                    Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            article_recycle_view.getLayoutManager().onRestoreInstanceState(listState);
                            // Releasing the semaphore
                            System.out.println("Realease " + selectedCategoryIndex);
                            semaphore2.release();
                        }

                        @Override
                        public void onFailure(Call<List<ArticleInNewsFeedModel>> call, Throwable t) {
                            Log.d("Test API", "Failure: " + t.getMessage());
                            article_recycle_view.getLayoutManager().onRestoreInstanceState(listState);
                            // Releasing the semaphore
                            semaphore2.release();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private final Semaphore semaphore3 = new Semaphore(1);
    private final Semaphore semaphore4 = new Semaphore(1);

    private int threadCount = 0;

    @SuppressLint("NotifyDataSetChanged")
    private void queryArticlesWhenAlterFilter() {
        // Thực hiện truy vấn trong luồng con
        queryUserData();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    // Acquiring the semaphore
                    semaphore3.acquire();
                    System.out.println("queryArticle " + selectedCategoryIndex);



                    // Vùng critical
//                    System.out.println("queryArticles " + page_index);
                    listState = article_recycle_view.getLayoutManager().onSaveInstanceState();

                    ArticleApi apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
                    apiService.getArticlesInNewsFeed(page_index++, categories.get(selectedCategoryIndex).getCategoryId(), selectedFilter).enqueue(new Callback<List<ArticleInNewsFeedModel>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<ArticleInNewsFeedModel>> call, Response<List<ArticleInNewsFeedModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().size() > 0) {

//                                    System.out.println("queryArticlesSuccess " + response.body().size());
//                                    articlesAdapter.addArticles(response.body(), (Activity) getContext());
                                    articles.clear();

//                                    System.out.println("start " + oldSize);
                                    articles.addAll(response.body());


//                                    System.out.println("end " + (articles.size() - oldSize));
                                    try {
                                        semaphore4.acquire();
                                        System.out.println("onResponse " + selectedCategoryIndex + "articles size " + articles.size() +"thread " + threadCount);

                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    if (threadCount == 1) {
                                        System.out.println("articles size " + articles.size() + "update");
                                        articlesAdapter.notifyItemRangeInserted(1, articles.size());
                                    }
                                    threadCount--;
                                    semaphore4.release();

                                }
                            } else {
                                try {
                                    ResponseException errorResponse = JsonParser.parseError(response);
                                    Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            article_recycle_view.getLayoutManager().onRestoreInstanceState(listState);
                            // Releasing the semaphore
                            System.out.println("Realease " + selectedCategoryIndex);
                            semaphore3.release();
                        }

                        @Override
                        public void onFailure(Call<List<ArticleInNewsFeedModel>> call, Throwable t) {
                            Log.d("Test API", "Failure: " + t.getMessage());
                            article_recycle_view.getLayoutManager().onRestoreInstanceState(listState);
                            // Releasing the semaphore
                            semaphore3.release();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isInit = true;

    private void setUpArticlesRecycleViewAdapter() {
        articles = new UniqueList<>();
        articlesAdapter = new ArticleRecycleViewAdapter((MainActivity) getActivity(), articles);
        article_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        articlesAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                System.out.println("OKKKKKKKKKKKKKKKKKKKK");

                if (isInit) { isInit = false;
                    article_recycle_view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            header = (ArticleRecycleViewAdapter.PostSectionViewHolder) article_recycle_view.findViewHolderForAdapterPosition(0);
                            header.btnPost.setClickable(false);
                            System.out.println("OKKKKKKKKKKKKKKKKKKKK");
//                            header.btnPost.setOnClickListener
                        }
                    }, 100);}
            }
        });
        articlesAdapter.setAction((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).startSecondActivityForResult();
            }
        }));
        article_recycle_view.setAdapter(articlesAdapter);

        System.out.println("OKKKKKKKKKKKKKKKKKKKK");


//        queryArticles();
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnDocBao;

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
        System.out.println("onCreate");
    }

    private void initiateAdapters() {
        setUpCategoriesRecycleViewAdapter();
        setUpFiltersSpinnerAdapter();
        queryFilters();
        queryUserData();
        queryCategories(new OnCategoriesLoadedListener() {
            @Override
            public void onCategoriesLoaded() {
                setUpArticlesRecycleViewAdapter();
            }
        });
    }
    private boolean isInitSpinner = true;
    private boolean isInitRvArticle = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categories_recy = view.findViewById(R.id.categories_recy);
        toolbar = view.findViewById(R.id.tool_bar);
        nav_menu_button = view.findViewById(R.id.left_navigation_menu);
        filtersSpinner = view.findViewById(R.id.filters_spinner);
        article_recycle_view = view.findViewById(R.id.article_recycle_view);
        System.out.println("onCreateView");
        initiateAdapters();

        // Envent handler
        nav_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setOpenNavigationBar();
            }
        });

        article_recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == articlesAdapter.getItemCount() - 1 && (articles.size() != 0 || isInitRvArticle)) {
                    isInitRvArticle = false;
                    // Người dùng đã kéo đến item cuối cùng
                    try {
                        semaphore4.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    threadCount++;
                    semaphore4.release();
                    queryArticles();
                    Log.d("Test", "Đã đến item cuối cùng");
                }
            }
        });

        filtersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInitSpinner) {
                    // Get the selected item
                    String selectedFilter = filters.get(position);
                    // Handle the selected item
                    Toast.makeText(getContext(), "Selected: " + selectedFilter, Toast.LENGTH_SHORT).show();
                    // Implement your logic for the selected filter here
                    handleFilterSelection(selectedFilter);
                } else {
                    isInitSpinner = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        return view;
    }
}
