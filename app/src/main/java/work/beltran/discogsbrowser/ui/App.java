package work.beltran.discogsbrowser.ui;

import android.app.Application;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import work.beltran.discogsbrowser.BuildConfig;
import work.beltran.discogsbrowser.api.di.modules.ApiFrontendModule;
import work.beltran.discogsbrowser.api.di.modules.AveragePriceModule;
import work.beltran.discogsbrowser.api.di.modules.DiscogsModule;
import work.beltran.discogsbrowser.api.di.modules.LoginModule;
import work.beltran.discogsbrowser.ui.di.ApiComponent;
import work.beltran.discogsbrowser.ui.di.AppComponent;
import work.beltran.discogsbrowser.ui.di.DaggerApiComponent;
import work.beltran.discogsbrowser.ui.di.DaggerAppComponent;
import work.beltran.discogsbrowser.ui.di.DaggerLoginComponent;
import work.beltran.discogsbrowser.ui.di.LoginComponent;
import work.beltran.discogsbrowser.ui.di.modules.ContextModule;

/**
 * Created by Miquel Beltran on 22.04.16.
 */
@SuppressWarnings("ALL")
public class App extends Application {
    private static final String TAG = App.class.getCanonicalName();
    /* package */ ApiComponent apiComponent;
    /* package */ AppComponent appComponent;
    /* package */ LoginComponent loginComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        loginComponent = DaggerLoginComponent
                .builder()
                .loginModule(new LoginModule(BuildConfig.API_CONSUMER_KEY, BuildConfig.API_CONSUMER_SECRET))
                .contextModule(new ContextModule(this))
                .build();
    }

    public void setUserToken(String userToken) {
        apiComponent = DaggerApiComponent
                .builder()
                .contextModule(new ContextModule(this))
                .discogsModule(new DiscogsModule(BuildConfig.API_CONSUMER_KEY, BuildConfig.API_CONSUMER_SECRET, userToken))
                .apiFrontendModule(new ApiFrontendModule(Schedulers.io(), AndroidSchedulers.mainThread()))
                .averagePriceModule(new AveragePriceModule(Schedulers.io(), AndroidSchedulers.mainThread()))
                .build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public LoginComponent getLoginComponent() {
        return loginComponent;
    }
}

