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

    public void testFragment() throws Throwable {
        //check to make sure fragments get instantiated correctly
        GameSetupFragment fragPre = GameSetupFragment.newInstance();
        Fragment fragPost = startFragment(fragPre);
        assertNotNull(fragPost);

        //check that passing string argument to fragment creates adapter as expected

        //4x4 should give 16 in adapter
        GameFragment gameFragPre = GameFragment.newInstance("4x4");
        final GameFragment gameFragPost = (GameFragment) startFragment(gameFragPre);
        assertNotNull(gameFragPost);

        int numCubes = gameFragPost.cubeGrid.getAdapter().getCount();
        assertEquals(16, numCubes);

        //6x8 should give 48 in adapter
        GameFragment gameFragPre1 = GameFragment.newInstance("6x8");
        GameFragment gameFragPost1 = (GameFragment) startFragment(gameFragPre1);
        assertNotNull(gameFragPost1);

        int numCubes1 = gameFragPost1.cubeGrid.getAdapter().getCount();
        assertEquals(48, numCubes1);

        //check that no more than two cubes are ever selected
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameFragPost.cubeGrid.performItemClick(null, 2, 0);

                int selectedCount = ((CubeView.Adapter)gameFragPost.cubeGrid.getAdapter()).getSelectedCount();
                assertEquals(1, selectedCount); //1 cube selected after 1 press

                gameFragPost.itemClick.onItemClick(null, null, 0, 0);
                gameFragPost.itemClick.onItemClick(null, null, 4, 0);
                gameFragPost.itemClick.onItemClick(null, null, 9, 0);
                selectedCount = ((CubeView.Adapter)gameFragPost.cubeGrid.getAdapter()).getSelectedCount();
                assertEquals(2, selectedCount); //2 cubes selected after several more presses
            }
        });


    }
}