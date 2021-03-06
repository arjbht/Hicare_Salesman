package com.ab.hicaresalesman;

import android.app.Application;
import android.content.Context;


import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;

import com.ab.hicaresalesman.database.realm.RealmString;
import com.ab.hicaresalesman.database.realm.RealmStringListTypeAdapter;
import com.ab.hicaresalesman.network.HeaderInterceptor;
import com.ab.hicaresalesman.network.IRetrofit;
import com.ab.hicaresalesman.network.RequestHeader;
import com.ab.hicaresalesman.network.models.login.LoginData;
import com.ab.hicaresalesman.utils.notifications.OneSIgnalHelper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.onesignal.OneSignal;

import java.util.concurrent.TimeUnit;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BaseApplication extends Application {

    private static volatile IRetrofit IRETROFIT = null;
    private static volatile Realm REALM = null;
    private static final String ONESIGNAL_APP_ID = "2864d171-0498-4fb6-aed7-ff8be7fdb197";


    public static synchronized Realm getRealm() {
        if (REALM != null) {
            return REALM;
        } else {
            RealmConfiguration realmConfig =
                    new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfig);
            REALM = Realm.getDefaultInstance();
            return REALM;
        }
    }

    public static synchronized IRetrofit getRetrofitAPI(boolean autohrised) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        gsonBuilder.registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
        }.getType(), RealmStringListTypeAdapter.INSTANCE);

        Gson gson = gsonBuilder.create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        if (autohrised) httpClientBuilder.addInterceptor(new HeaderInterceptor(getHeader()));

        IRETROFIT = new Retrofit.Builder().baseUrl(IRetrofit.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build()
                .create(IRetrofit.class);


        return IRETROFIT;
    }

    private static RequestHeader getHeader() {
        RequestHeader header = null;
        RealmResults<LoginData> query =
                BaseApplication.getRealm().where(LoginData.class).findAll();
//        if (query != null && query.size() > 0) {
//            header = new RequestHeader();
//            header.setHeaderName("Authorization");
//            assert query.get(0) != null;
//            assert query.get(0) != null;
//            header.setHeaderValue(query.get(0).getTokenType() + " " + query.get(0).getAccessToken());
//        }
        return header;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        // initialise the realm database
        try {
            Realm.init(this);
            RealmConfiguration realmConfiguration =
                    new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // configuring the font for calligraphy
        try {
            ViewPump.init(ViewPump.builder()
                    .addInterceptor(new CalligraphyInterceptor(
                            new CalligraphyConfig.Builder()
                                    .setDefaultFontPath("fonts/font.ttf")
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
