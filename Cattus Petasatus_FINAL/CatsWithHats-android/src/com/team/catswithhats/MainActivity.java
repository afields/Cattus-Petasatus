package com.team.catswithhats;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GPS andgps = new AndroidGPS(MainActivity.this);
        
    	AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        initialize(new CatsWithHats(andgps), cfg);
        
        
        /*mBackground1 = new Thread(new Runnable()
        {
                // Setup the run() method that is called when the background thread
                // is started.
                public void run()
                {
                	AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
                    cfg.useGL20 = false;
                    initialize(new CatsWithHats(new AndroidGPS()), cfg);
                }
        });

        mBackground1.start();*/
    }
}