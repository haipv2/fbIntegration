package haipv.fbintegration;

import android.app.Application;

//import com.sromku.simple.fb.Permission;
//import com.sromku.simple.fb.SimpleFacebook;
//import com.sromku.simple.fb.SimpleFacebookConfiguration;

public class MainApp extends Application {


    @Override
    public void onCreate() {
//        Permission[] permissions = new Permission[] {
//                Permission.USER_PHOTOS,
//                Permission.EMAIL
//        };
        super.onCreate();
//        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
//                .setAppId(getString(R.string.facebook_app_id))
//                .setPermissions(permissions)
//                .build();
//        SimpleFacebook.setConfiguration(configuration);
    }
}
