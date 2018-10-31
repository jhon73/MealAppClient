package com.mealappclient;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mealappclient.baseclass.BaseAppCompactActivity;
import com.mealappclient.container.BaseContainer;
import com.mealappclient.container.FavouriteContainer;
import com.mealappclient.container.HomeContainer;
import com.mealappclient.container.OrderHistoryContainer;
import com.mealappclient.databinding.ActivityMainBinding;
import com.mealappclient.fragment.CouponFragment;
import com.mealappclient.fragment.FavouriteFragment;
import com.mealappclient.fragment.FilterFragment;
import com.mealappclient.fragment.HomeFragment;
import com.mealappclient.fragment.OrderConfirmFragment;
import com.mealappclient.fragment.OrderHistoryFragment;
import com.mealappclient.fragment.ProfileFragment;
import com.mealappclient.fragment.RestaurantsFragment;
import com.mealappclient.fragment.ReviewMainFragment;
import com.mealappclient.fragment.SearchFragment;
import com.mealappclient.helper.APIService;
import com.mealappclient.helper.APIUtils;
import com.mealappclient.helper.ConstantData;
import com.mealappclient.helper.SharedPref;
import com.mealappclient.retrofit.model.UserDetail;
import com.mealappclient.utility.Utility;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseAppCompactActivity implements View.OnClickListener {

    private static String currentFragment;
    private ActivityMainBinding mBinding;
    private ActionBarDrawerToggle drawerToggle;
    private String screen = " ";
    private String[] category = null;
    private ImageView profileImg;
    private TextView txtUserName;
    private SharedPref session;

    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int id;


//    private FusedLocationProviderClient mFusedLocationClient;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);

        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        session = new SharedPref(MainActivity.this);
        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_);
//        mBinding.toolbar.setNavigationIcon(R.drawable.ic_nav);


        View view = mBinding.navView.getHeaderView(0);
        mBinding.navView.getMenu().getItem(0).setChecked(false);
        profileImg = (ImageView) view.findViewById(R.id.imageView_user);
        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);

        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
        drawerToggle = setupDrawerToggle();
//        circleImageView_nav.setOnClickListener(this);
        mBinding.drawerLayout.addDrawerListener(drawerToggle);
        mBinding.navView.setItemIconTintList(null);

        getUserData();

        profileImg.setOnClickListener(this);


        currentFragment = HomeContainer.class.getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ll_frame, new HomeContainer(), HomeContainer.class.getSimpleName());
        ft.commit();

    }

    private void getUserData() {
        apiService.getUserDetail(sharedPreferences.getString(ConstantData.TOKEN, ""))
                .enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    Utility.loadImage(response.body().getImageUrl(), profileImg, R.drawable.placeholder);
                                    txtUserName.setText(response.body().getFirstName());
                                    id = response.body().getId();
                                }
                            } else if (response.code() == 401) {
                                Utility.toast(MainActivity.this, "Unauthorized");
                            } else if (response.code() == 403) {
                                Utility.toast(MainActivity.this, "Forbidden");
                            } else if (response.code() == 404) {
                                Utility.toast(MainActivity.this, "Not Found");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            boolean isPop = false;
            if (currentFragment.equals(HomeContainer.class.getSimpleName())) {
                isPop = ((BaseContainer) getSupportFragmentManager().findFragmentByTag(HomeContainer.class.getSimpleName())).popFragment();
            } else if (currentFragment.equals(FavouriteContainer.class.getSimpleName())) {
                isPop = ((BaseContainer) getSupportFragmentManager().findFragmentByTag(FavouriteContainer.class.getSimpleName())).popFragment();
            } else if (currentFragment.equals(OrderHistoryContainer.class.getSimpleName())) {
                isPop = ((BaseContainer) getSupportFragmentManager().findFragmentByTag(OrderHistoryContainer.class.getSimpleName())).popFragment();
            }
            if (!isPop) {
                super.onBackPressed();
            }
        }
    }


    public void setNavigationSpinner() {
        category = getResources().getStringArray(R.array.category);
        Spinner navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                return v;
            }
        };

        navigationSpinner.setAdapter(aa);
        mBinding.toolbar.addView(navigationSpinner, 0);
    }

//    @OnClick(R.id.imageView_user)
//    public void onClickImageUSer(){
//        ProfileFragment nextFrag = new ProfileFragment();
//        this.getSupportFragmentManager().beginTransaction()
//                .replace(R.id.ll_frame, nextFrag, "findThisFragment")
//                .addToBackStack(null)
//                .commit();
//    }

    public void setRemoveAllView() {
//        mBinding.toolbar.removeAllViewsInLayout();
    }

    public void setIconNavi() {
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void setIconBack() {
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setActionBarTitle(String title) {
        mBinding.txtToolbarTitle.setText(title);
    }

    public void setActionBarSubTitle(String subTitle) {
        getSupportActionBar().setSubtitle(subTitle);
    }


    public void setMenuActionBar(String screen) {
        this.screen = screen;
        invalidateOptionsMenu();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        String title = null;

        if (id == R.id.home) {
//            title = "Menu";
//            currentFragment = HomeContainer.class.getSimpleName();
//            fragment = new HomeContainer();
            HomeFragment nextFrag = new HomeFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.favourite) {
//            title = "Favourite";
//            currentFragment = FavouriteContainer.class.getSimpleName();
//            fragment = new FavouriteContainer();

            FavouriteFragment nextFrag = new FavouriteFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

            Toast.makeText(this, "favourite", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.order_history) {
//            title = "Order History";
//            currentFragment = OrderHistoryContainer.class.getSimpleName();
//            fragment = new OrderHistoryContainer();

            OrderHistoryFragment nextFrag = new OrderHistoryFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "order history", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.restaurant){
            RestaurantsFragment nextFrag = new RestaurantsFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.payment) {
            CouponFragment nextFrag = new CouponFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "payment", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.setting) {

//            RestaurantsFragment nextFrag = new RestaurantsFragment();
//            this.getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
//                    .addToBackStack(null)
//                    .commit();

            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.support) {
            ReviewMainFragment nextFrag = new ReviewMainFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();


            Toast.makeText(this, "support", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.logout) {
            session.logoutUser();
            Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show();

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ll_frame, fragment, fragment.getClass().getSimpleName());
            ft.commit();
            setActionBarTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

//        MenuItem item = menu.findItem(R.id.action_filter_search);
//        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                OrderConfirmFragment nextFrag = new OrderConfirmFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_filter:
                FilterFragment filterFragment = new FilterFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ll_frame, filterFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_search:
                SearchFragment searchFragment = new SearchFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ll_frame, searchFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (screen.equals("HomeMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(true);
            menu.findItem(R.id.action_filter).setVisible(true);
            menu.findItem(R.id.action_cart).setVisible(true);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("RestaurantMenu")) {
//            setNavigationSpinner();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(true);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("FavouriteMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("VerifyCodeMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("CouponMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("OrderHistoryMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("RestaurantDetailMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(true);
            menu.findItem(R.id.action_search).setVisible(true);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("DeliveryAddressMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("FilterMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(true);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("SearchMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("ConfirmOrderMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("OrderHistoryMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("CheckoutMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("ReviewMenu")) {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        } else if (screen.equals("ProfileMenu")) {
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(true);
        } else {
            setRemoveAllView();
            menu.findItem(R.id.action_reset).setVisible(false);
            menu.findItem(R.id.action_like).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_filter).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if (v == profileImg) {
            ProfileFragment searchFragment = new ProfileFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, searchFragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
    }
}
