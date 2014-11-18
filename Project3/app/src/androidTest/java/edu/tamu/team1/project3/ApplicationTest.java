package edu.tamu.team1.project3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;

    public ApplicationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    private Fragment startFragment(Fragment fragment) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment, "tag");
        transaction.commit();
        getInstrumentation().waitForIdleSync();
        Fragment frag = mActivity.getSupportFragmentManager().findFragmentByTag("tag");
        return frag;
    }

    public void testFragment() {
        Fragment frag = startFragment(GameSetupFragment.newInstance());
        assertNotNull(frag);
    }
}