package me.jeu.amolejourney;

import quentin.jeu.mole.MoleGame;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	private DroidShop shop;
	private DroidGoogleServices gservices;
//	private Droidbook fb;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		if(android.os.Build.VERSION.SDK_INT>18)config.useImmersiveMode=true;
		setSystemUiVisibilityChangeListener();
		shop=new DroidShop(this);
		gservices = new DroidGoogleServices(this);
		initialize(new MoleGame(shop, gservices), config);
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
    private void setSystemUiVisibilityChangeListener() {
        if (CompatibilityManager.isKitKat()) {
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView
                                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                            }
                        }
                    });
        }
    }
	
	@Override
	protected void onStart(){
		super.onStart();
		gservices.gameHelper.onStart(this);
	}

	@Override
	protected void onStop(){
		super.onStop();
		gservices.gameHelper.onStop();
		
	}
	
	///face book////////////
/*	@Override
	protected void onPause() {
	  super.onPause();
	  // Logs 'app deactivate' App Event.
	  AppEventsLogger.deactivateApp(this);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
		
	}*/
	////////////////////////
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 
		 ///FACEBOOK services
//		 fb. callbackManager.onActivityResult(requestCode, resultCode, data);
		 
		 ///GOOGLE services
		 gservices.gameHelper.onActivityResult(requestCode, resultCode, data);
		 
		 ///GOOGLE billing
        if (shop.mHelper == null) return;
        // Pass on the activity result to the helper for handling
        if (!shop.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            //Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
        
        
        
    }
}

