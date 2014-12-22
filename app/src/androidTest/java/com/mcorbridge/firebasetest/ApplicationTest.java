package com.mcorbridge.firebasetest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.mcorbridge.firebasetest.model.ApplicationModel;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mainActivity;
    ApplicationModel applicationModel;

   public ApplicationTest(){
       super(MainActivity.class);
   }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        applicationModel = ApplicationModel.getInstance();
    }

    public void testImageViewNotNull(){
        ImageView imageView = (ImageView)mainActivity.findViewById(R.id.imageView);
        assertNotNull(imageView);
    }

    public void testFirebaseBruins(){
        applicationModel.setApplicationTeam("bruins");
        assertEquals("bruins",applicationModel.getApplicationTeam());
    }

    public void testFirebaseLeafs(){
        applicationModel.setApplicationTeam("leafs");
        assertEquals("leafs",applicationModel.getApplicationTeam());
    }

    public void testMenuOptions(){
        
    }
}