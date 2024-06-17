package com.example.newsandroidproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.BodyItemAdapter;
import com.example.newsandroidproject.adapter.CategoryAdapter;
import com.example.newsandroidproject.adapter.CategoryInDialogAdapter;
import com.example.newsandroidproject.adapter.CategoryInPostArticleActivityAdapter;
import com.example.newsandroidproject.adapter.ChosenCategoryInDialogAdapter;
import com.example.newsandroidproject.adapter.ItemTouchHelperCallback;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.UserApi;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.Article;
import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.Category;
import com.example.newsandroidproject.model.User;
import com.example.newsandroidproject.model.dto.ArticleDTO;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.dto.UserDTO;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedRequestDTO;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.PostArticleRequestDTO;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import androidx.recyclerview.widget.ItemTouchHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostArticleActivity extends AppCompatActivity implements BodyItemAdapter.OnItemLongClickListener {

    BodyItemAdapter.HeaderViewHolder header;
    private Toolbar toolbar;
    private ImageButton btnCancel;
    private TextView btnPost;
    public NestedScrollView scrollView;

    private ImageButton btnAddItem;
    private ImageButton btnGoUp;
    private TextView txtUserName, txtFollower, tvThumbnail, textView3;
    private EditText edtTitle, edtDescription, edtThumbnailName;
    private TextInputLayout edtTitleLayout, edtDescriptionLayout, edtThumbnailNameLayout;
    private com.google.android.material.imageview.ShapeableImageView imgAvar, edtThumbnail;
    private LinearLayout linearLayout4;


//    private RecyclerView rvChosenCategoríes;
//    private UniqueList<Category> chosenCategories;

    private List<BodyItem> items;
    private RecyclerView.ViewHolder currentViewHolder;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_FOR_ITEM = 2;

    @Override
    public void onItemLongClick(int position) {
        // Hiển thị dialog xác nhận xóa phần tử
//        new AlertDialog.Builder(this)
//                .setTitle("Xóa phần tử")
//                .setMessage("Bạn có muốn xóa phần tử này?")
//                .setPositiveButton("Xóa", (dialog, which) -> {
//                    // Xóa phần tử khỏi danh sách
//                    items.remove(position);
//                    itemAdapter.notifyItemRemoved(position);
//                })
//                .setNegativeButton("Hủy", null)
//                .show();
    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            // Handle long press event
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Handle scroll event
            return true;
        }
    }
    private GestureDetector gestureDetector;

    private UserDTO user;
    private void queryUserData() {
        UserApi apiService = RetrofitService.getClient(this).create(UserApi.class);
        apiService.getUserInfo2().enqueue(new Callback<UserDTO>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        user = response.body();
                        header.txtUserName.setText(response.body().getName());
                        if (response.body().getAvatar() != null) {
                            byte[] bytes = Base64.decode(response.body().getAvatar(), Base64.DEFAULT);
                            header.imgAvar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        } else {
                            header.imgAvar.setImageResource(R.drawable.ic_blank_avatar);
                        }
                    }
                    // Notify that categories are loaded
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(PostArticleActivity.this, "errorResponse.getMessage()", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(PostArticleActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(PostArticleActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                // Notify that categories are loaded even on failure to avoid blocking other setups
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);

        // Find view by ID
        toolbar = findViewById(R.id.tool_bar);
        btnCancel = findViewById(R.id.btnCancel);
        btnPost = findViewById(R.id.btnPost);
        rvBodyItems = findViewById(R.id.rvBodyItems);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnGoUp = findViewById(R.id.btnGoUp);





        setUpBodyItemRecycleViewAdapter();
//        setUpCategoryRecycleView();

        itemAdapter.setOnItemLongClickListener(this);
        itemAdapter.setOnclickForAddCateBtn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAndShowDialogCate();
            }
        });
        gestureDetector = new GestureDetector(this, new MyGestureListener());

        btnGoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvBodyItems.smoothScrollToPosition(0);
            }
        });
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("Drag:" + event.getX() + " " + event.getY());
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
        // Setup any necessary listeners or initializations here
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAndShowDialog();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Post btn clicked!", Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(PostArticleActivity.this)
                .setTitle("Đăng bài viết")
                .setMessage("Bạn có muốn đăng bài viết này?")
                .setPositiveButton("Đăng", (dialog, which) -> {
                    boolean isValid = validateInputs();
                    System.out.println("Valid " + isValid);
                    if (isValid) {
                        // Code to post the article goes here
//                    getData();

                        // Thực hiện một số thao tác và chuẩn bị kết quả trả về
                        PostArticleRequestDTO myClassInstance = getData();

                        // Tạo Intent để chứa kết quả
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("article", myClassInstance);
                        resultIntent.putExtra("isDraft", false);


                        // Đặt kết quả trả về và kết thúc Activity
                        setResult(RESULT_OK, resultIntent);
                        finish();

                    }
                })
                .setNegativeButton("Hủy", null)
                .show();



            }
        });
    }

    private RecyclerView rvBodyItems;
    private BodyItemAdapter itemAdapter;


    private PostArticleRequestDTO getData() {
        String title = null;
        String description = null;
        String thumbnail = null;
        String thumbnailName = null;
        UniqueList<Category> categories = new UniqueList<>();
        List<BodyItem> bodyItems = itemAdapter.items;

        // Duyệt qua các item trong itemAdapter để lấy dữ liệu
        for (int i = 0; i < itemAdapter.getItemCount(); i++) {
            RecyclerView.ViewHolder viewHolder = rvBodyItems.findViewHolderForAdapterPosition(i);

            if (viewHolder instanceof BodyItemAdapter.HeaderViewHolder) {
                BodyItemAdapter.HeaderViewHolder headerViewHolder = (BodyItemAdapter.HeaderViewHolder) viewHolder;
                title = headerViewHolder.edtTitle.getText().toString();
                description = headerViewHolder.edtDescription.getText().toString();
                thumbnail = (String) headerViewHolder.edtThumbnail.getTag(); // Lấy dữ liệu hình ảnh từ tag
                thumbnailName = headerViewHolder.edtThumbnailName.getText().toString();
                categories = headerViewHolder.cates;
            } else if (viewHolder instanceof BodyItemAdapter.BodyTitleViewHolder) {
                BodyItemAdapter.BodyTitleViewHolder bodyTitleViewHolder = (BodyItemAdapter.BodyTitleViewHolder) viewHolder;
                BodyItem bodyItem = bodyItems.get(i);
                bodyItem.setOrdinalNumber(i);
                bodyItem.setArticleTitle(bodyTitleViewHolder.edtBodyTitle.getText().toString());
            } else if (viewHolder instanceof BodyItemAdapter.ParagraphViewHolder) {
                BodyItemAdapter.ParagraphViewHolder paragraphViewHolder = (BodyItemAdapter.ParagraphViewHolder) viewHolder;
                BodyItem bodyItem = bodyItems.get(i);
                bodyItem.setOrdinalNumber(i);
                bodyItem.setContent(paragraphViewHolder.edtParagraph.getText().toString());
            } else if (viewHolder instanceof BodyItemAdapter.ImageViewHolder) {
                BodyItemAdapter.ImageViewHolder imageViewHolder = (BodyItemAdapter.ImageViewHolder) viewHolder;
                BodyItem bodyItem = bodyItems.get(i);
                bodyItem.setOrdinalNumber(i);
                bodyItem.setDataImage((String) imageViewHolder.edtImage.getTag()); // Lấy dữ liệu hình ảnh từ tag
                bodyItem.setImageName(imageViewHolder.edtImageName.getText().toString());
            }
        }

        // Lấy thời gian hiện tại làm thời gian tạo và sửa đổi
        Date currentTime = Calendar.getInstance().getTime();

        // Giả sử các giá trị này được lấy từ hệ thống hoặc có sẵn
        Long viewCount = 0L; // hoặc giá trị thích hợp khác
        Long commentCount = 0L; // hoặc giá trị thích hợp khác
        Long userId = 0L; // hoặc giá trị thích hợp khác
        String userName = user.getName(); // hoặc giá trị thích hợp khác
        String avatar = user.getAvatar(); // hoặc giá trị thích hợp khác
        Long followCount = user.getFolloweCount(); // hoặc giá trị thích hợp khác
        Long saveCount = 0L; // hoặc giá trị thích hợp khác

        bodyItems.remove(0);

        // Tạo đối tượng ArticleInNewsFeedModel với các giá trị đã thu thập được
        ArticleInNewsFeedRequestDTO newsFeedArticleDTO = new ArticleInNewsFeedRequestDTO(
                null, // articleId sẽ được thiết lập sau
                title,
                description,
                thumbnail,
                thumbnailName,
                DateParser.formatToISO8601(currentTime),
                DateParser.formatToISO8601(currentTime),
                viewCount,
                commentCount,
                userId,
                userName,
                avatar,
                followCount,
                saveCount
        );

        // Trả về đối tượng ArticleInReadingPageDTO
        return new PostArticleRequestDTO(newsFeedArticleDTO,  bodyItems, categories);
    }



    private boolean validateInputs() {
        boolean isValid = true;

        // Convert 1dp to pixels
        float strokeWidthInPx = getResources().getDisplayMetrics().density;

        for (int i = 0; i <items.size(); i++) {
            RecyclerView.ViewHolder holder = rvBodyItems.findViewHolderForAdapterPosition(i);
            switch (items.get(i).getItemType()) {
                case  BodyItem.HEADER:
                    BodyItemAdapter.HeaderViewHolder headerViewHolder = (BodyItemAdapter.HeaderViewHolder)holder;

                    if (headerViewHolder.edtTitle.getText().toString().trim().isEmpty()) {
                        headerViewHolder.edtTitleLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        headerViewHolder.edtTitleLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        headerViewHolder.edtTitleLayout.setError(null); headerViewHolder.edtTitleLayout.setErrorEnabled(false);
                    }

                    if (headerViewHolder.edtDescription.getText().toString().trim().isEmpty()) {
                        headerViewHolder.edtDescriptionLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        headerViewHolder.edtDescriptionLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        headerViewHolder.edtDescriptionLayout.setError(null); headerViewHolder.edtDescriptionLayout.setErrorEnabled(false);
                    }

                    if (headerViewHolder.edtThumbnailName.getText().toString().trim().isEmpty()) {
                        headerViewHolder.edtThumbnailNameLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        headerViewHolder.edtThumbnailNameLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        headerViewHolder.edtThumbnailNameLayout.setError(null); headerViewHolder.edtThumbnailNameLayout.setErrorEnabled(false);
                    }

                    headerViewHolder.tvThumbnail.setTextColor(getResources().getColor(R.color.primaryTextColor));
                    if ((headerViewHolder.edtThumbnail.getDrawable() != null && headerViewHolder.edtThumbnail.getDrawable().getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.default_image).getConstantState()))) {
                        isValid = false;
                        headerViewHolder.tvThumbnail.setTextColor(getResources().getColor(R.color.red));
                    }
                    break;
                case BodyItem.BODY_TITLE_ITEM:
                    BodyItemAdapter.BodyTitleViewHolder viewHolder = (BodyItemAdapter.BodyTitleViewHolder)holder;
                    if (viewHolder.edtBodyTitle.getText().toString().trim().isEmpty()) {
                        viewHolder.edtBodyTitleLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        viewHolder.edtBodyTitleLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        viewHolder.edtBodyTitleLayout.setError(null); viewHolder.edtBodyTitleLayout.setErrorEnabled(false);
                    }
                    break;
                case BodyItem.PARAGRAPH_ITEM:
                    BodyItemAdapter.ParagraphViewHolder viewHolder2 = (BodyItemAdapter.ParagraphViewHolder)holder;
                    if (viewHolder2.edtParagraph.getText().toString().trim().isEmpty()) {
                        viewHolder2.edtParagraphLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        viewHolder2.edtParagraphLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        viewHolder2.edtParagraphLayout.setError(null); viewHolder2.edtParagraphLayout.setErrorEnabled(false);
                    }
                    break;
                case BodyItem.IMAGE_ITEM:
                    BodyItemAdapter.ImageViewHolder viewHolder3 = (BodyItemAdapter.ImageViewHolder)holder;
                    if (viewHolder3.edtImageName.getText().toString().trim().isEmpty()) {
                        viewHolder3.edtImageNameLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        viewHolder3.edtImageNameLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        viewHolder3.edtImageNameLayout.setError(null); viewHolder3.edtImageNameLayout.setErrorEnabled(false);
                    }
                    viewHolder3.tvImage.setTextColor(getResources().getColor(R.color.primaryTextColor));
                    if ((viewHolder3.edtImage.getDrawable() != null && viewHolder3.edtImage.getDrawable().getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.default_image).getConstantState()))) {
                        viewHolder3.tvImage.setTextColor(getResources().getColor(R.color.red));
                        isValid = false;
                    }

                    break;
            }
        }
        return isValid;
    }


    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openImagePickerForItem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOR_ITEM);
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                BodyItemAdapter.HeaderViewHolder headerViewHolder = (BodyItemAdapter.HeaderViewHolder) currentViewHolder;
                headerViewHolder.edtThumbnail.setImageBitmap(bitmap);
                String encodedImage = encodeImageToBase64(bitmap);
                headerViewHolder.edtThumbnail.setTag(encodedImage); // Gán chuỗi Base64 cho ImageView
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST_FOR_ITEM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                BodyItemAdapter.ImageViewHolder imageViewHolder = (BodyItemAdapter.ImageViewHolder) currentViewHolder;
                imageViewHolder.edtImage.setImageBitmap(bitmap);
                String encodedImage = encodeImageToBase64(bitmap);
                imageViewHolder.edtImage.setTag(encodedImage); // Gán chuỗi Base64 cho ImageView
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void setCurrentViewHolder(RecyclerView.ViewHolder viewHolder) {
        this.currentViewHolder = viewHolder;
    }

    public RecyclerView.ViewHolder getCurrentViewHolder() {
        return currentViewHolder;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUpBodyItemRecycleViewAdapter() {
        items = new ArrayList<>();
        itemAdapter = new BodyItemAdapter(this, items);
        itemAdapter.setImagePickerAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePickerForItem();
            }
        });
        itemAdapter.setImagePickerAction2(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
        rvBodyItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBodyItems.setAdapter(itemAdapter);

        // Đăng ký AdapterDataObserver trước khi thay đổi dữ liệu
        itemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                rvBodyItems.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        header = (BodyItemAdapter.HeaderViewHolder) rvBodyItems.findViewHolderForAdapterPosition(0);
                        System.out.println("VcSy" + (header == null));
                        queryUserData();
                    }
                }, 100);
            }
        });

        // Thêm dữ liệu và thông báo cho Adapter về sự thay đổi
        items.add(new BodyItem(0));
        itemAdapter.notifyDataSetChanged();

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(this, itemAdapter, rvBodyItems);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvBodyItems);
    }

//    private void setUpBodyItemRecycleViewAdapter() {
//        items = new ArrayList<>();
//        itemAdapter = new BodyItemAdapter(this, items);
//        itemAdapter.setImagePickerAction(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openImagePickerForItem();
//            }
//        });
//        rvBodyItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvBodyItems.setAdapter(itemAdapter);
//    }

    private void initAndShowDialogCate() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_cate_dialog);

        RecyclerView rvCate = dialog.findViewById(R.id.rvCategories);
        RecyclerView rvChosenCate = dialog.findViewById(R.id.rvChosenCate);
        ProgressBar pb = dialog.findViewById(R.id.pb);
        AppCompatButton save = dialog.findViewById(R.id.btnSave);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        rvCate.setLayoutManager(flexboxLayoutManager);

        FlexboxLayoutManager flexboxLayoutManager2 = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        rvChosenCate.setLayoutManager(flexboxLayoutManager2);

        UniqueList<Category> cates = new UniqueList<>();
        UniqueList<Category> chosenCate = new UniqueList<>();

        CategoryInDialogAdapter catesAdapter = new CategoryInDialogAdapter(this, cates, chosenCate);
        ChosenCategoryInDialogAdapter chosenCatesAdapter = new ChosenCategoryInDialogAdapter(this, chosenCate, cates);
        catesAdapter.setChosenAdapter(chosenCatesAdapter);
        chosenCatesAdapter.setCateAdapter(catesAdapter);



        rvCate.setAdapter(catesAdapter);
        rvChosenCate.setAdapter(chosenCatesAdapter);
        pb.setVisibility(View.VISIBLE);

        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                header.setCates(chosenCate);
                dialog.dismiss();
            }
        });
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.widthPixels;

        int minHeight = (int) (screenHeight * 4 / 5);




        dialog.getWindow().setLayout(minHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0) {
                        chosenCate.addAll(header.cates);
                        cates.addAll(response.body());
                        cates.removeAll(chosenCate);
                        System.out.println(cates.size());

                        chosenCatesAdapter.notifyDataSetChanged();
                        catesAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                    }
                    // Notify that categories are loaded
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(PostArticleActivity.this, "errorResponse.getMessage()", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(PostArticleActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(PostArticleActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                // Notify that categories are loaded even on failure to avoid blocking other setups
                pb.setVisibility(View.GONE);

            }
        });


    }

        private void initAndShowDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_body_item_dialog);

        LinearLayout btnImage = dialog.findViewById(R.id.btnImage);
        LinearLayout btnBodyTitle = dialog.findViewById(R.id.btnTitle);
        LinearLayout btnParagraph = dialog.findViewById(R.id.btnContent);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Image button clicked", Toast.LENGTH_SHORT).show();
                btnImage.setBackgroundColor(Color.parseColor("#FF888888"));
                items.add(new BodyItem(BodyItem.IMAGE_ITEM));
                itemAdapter.notifyItemInserted(items.size() - 1);
                rvBodyItems.smoothScrollToPosition(items.size() - 1);
                dialog.dismiss();
            }
        });

        btnBodyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Title button clicked", Toast.LENGTH_SHORT).show();
                btnBodyTitle.setBackgroundColor(Color.parseColor("#FF888888"));
                items.add(new BodyItem(BodyItem.BODY_TITLE_ITEM));
                itemAdapter.notifyItemInserted(items.size() - 1);
                rvBodyItems.smoothScrollToPosition(items.size() - 1);
                dialog.dismiss();
            }
        });

        btnParagraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Paragraph button clicked", Toast.LENGTH_SHORT).show();
                btnParagraph.setBackgroundColor(Color.parseColor("#FF888888"));
                items.add(new BodyItem(BodyItem.PARAGRAPH_ITEM));
                itemAdapter.notifyItemInserted(items.size() - 1);
                rvBodyItems.smoothScrollToPosition(items.size() - 1);
                dialog.dismiss();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > Math.abs(velocityX)) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });

        dialog.findViewById(R.id.dialogRootView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        dialog.show();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        int minHeight = (int) (screenHeight * 3 / 5);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, minHeight);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
