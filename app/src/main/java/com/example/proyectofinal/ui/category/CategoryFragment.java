package com.example.proyectofinal.ui.category;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.proyectofinal.R;
import com.example.proyectofinal.database.AppDataBase;
import com.example.proyectofinal.database.AppExecutors;
import com.example.proyectofinal.database.dao.CategoryDao;
import com.example.proyectofinal.database.models.Category;
import com.example.proyectofinal.database.models.User;
import com.example.proyectofinal.databinding.FragmentCategoryBinding;
import com.example.proyectofinal.listeners.OnItemTouchListener;
import com.example.proyectofinal.listeners.OptionsMenuListener;
import com.example.proyectofinal.networkSync.FirebaseNetwork;
import com.example.proyectofinal.networkSync.NetResponse;
import com.example.proyectofinal.ui.ViewModelFactory;
import com.example.proyectofinal.utils.CommonUtil;
import com.example.proyectofinal.utils.Constants;
import com.example.proyectofinal.utils.KProgressHUDUtils;
import com.example.proyectofinal.utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment";

    private FragmentCategoryBinding binding;
    private CategoryDao categoryDao;
    //to get user session data
    private UserSession session;
    private User user;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retrieve session values and display
        retrieveSession();

        categoryDao = AppDataBase.getInstance(getContext()).categoryDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fab.setOnClickListener(v -> {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.CATEGORY, null);

            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_nav_category_to_nav_category_man, bundle);
        });

        if (!this.user.getRol().equals(User.ROL.SELLER)) {
            binding.fab.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        final CategoryAdaptor adapter = new CategoryAdaptor(user);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOptionsMenuListener((OptionsMenuListener<Category>) (view1, element) -> {
            CommonUtil.popupMenu(getContext(), view1, () -> {
                final Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CATEGORY, element);

                NavHostFragment.findNavController(CategoryFragment.this)
                        .navigate(R.id.action_nav_category_to_nav_category_man, bundle);
            }, () -> {
                CommonUtil.alertDialog(getContext(), "Confirm dialog delete!",
                        "You are about to delete record. Do you really want to proceed?",
                        () -> delete(element));
            });
        });

        adapter.setOnItemTouchListener((OnItemTouchListener<Category>) element -> {
            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.CATEGORY, element);

            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_nav_category_to_nav_product, bundle);
        });

        CategoryViewModel categoryViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext()))
                .get(CategoryViewModel.class);

        categoryViewModel.getListLiveData().observe(this, elements -> {
            final Stream<Category> stream = user.getRol().equals(User.ROL.CUSTOMER)
                    ? elements.stream().filter(f -> (f.isActive()))
                    : elements.stream();

            adapter.setElements(stream.collect(Collectors.toList()));
        });
    }

    private void delete(Category element) {
        final KProgressHUD progressDialog = new KProgressHUDUtils(getActivity()).showConnecting();

        if (element.getPhoto() != null && !element.getPhoto().isEmpty()) {
            FirebaseNetwork.obtain().delete(element.getPhoto(), new NetResponse<String>() {
                @Override
                public void onResponse(String response) {
                    function.apply(progressDialog).apply(true).accept(element);
                }

                @Override
                public void onFailure(Throwable t) {
                    function.apply(progressDialog).apply(false).accept(element);
                    FancyToast.makeText(getContext(), t.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            });
        } else {
            function.apply(progressDialog).apply(true).accept(element);
        }

    }

    private final Function<KProgressHUD, Function<Boolean, Consumer<Category>>> function = progress -> success -> element -> {
        if (success) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                categoryDao.delete(element);
                getActivity().runOnUiThread(() -> FancyToast.makeText(getContext(), "Successfully deleted!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show());
            });
        }
        progress.dismiss();
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void retrieveSession() {
        //create new session object by passing application context
        session = new UserSession(getContext());
        //get User details if logged in
        user = session.getUserSession();
    }

}