package com.mcorbridge.firebasetest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mainActivity;


   public ApplicationTest(){
       super(MainActivity.class);
   }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
    }

    public void testImageViewNotNull(){
        ImageView imageView = (ImageView)mainActivity.findViewById(R.id.imageView);
        assertNotNull(imageView);
    }
}