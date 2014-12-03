package edu.tamu.team1.project3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

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

    private Fragment startFragment(Fragment fragment, String tag) throws Throwable {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment, tag);
        transaction.commit();
        getInstrumentation().waitForIdleSync();
        Fragment frag = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
        return frag;
    }

    public void testUI() throws Throwable {
        //check to make sure fragments get instantiated correctly
        GameSetupFragment fragPre = GameSetupFragment.newInstance();
        Fragment fragPost = startFragment(fragPre, "1");
        assertNotNull(fragPost);

        //check that passing string argument to fragment creates adapter as expected

        //4x4 should give 16 in adapter
        GameFragment gameFragPre = GameFragment.newInstance("4x4");
        final GameFragment gameFragPost = (GameFragment) startFragment(gameFragPre, "2");
        assertNotNull(gameFragPost);

        int numCubes = gameFragPost.cubeGrid.getAdapter().getCount();
        assertEquals(16, numCubes);

        //6x8 should give 48 in adapter
        GameFragment gameFragPre1 = GameFragment.newInstance("6x8");
        GameFragment gameFragPost1 = (GameFragment) startFragment(gameFragPre1, "3");
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

    public void testGameSetupCorrectly() throws Throwable {
        //check to make sure fragments get instantiated correctly
        GameSetupFragment gameSetupFragment = GameSetupFragment.newInstance();
        Fragment fragPost = startFragment(gameSetupFragment, "4");
        assertNotNull(fragPost);

        //4x4 should give 16 in adapter
        final GameFragment gameFragment = GameFragment.newInstance("4x4");
        startFragment(gameFragment, "5");
        assertNotNull(gameFragment);

        //check that two of each number have been placed on the board
        CubeView.Adapter adapter = (CubeView.Adapter) gameFragment.cubeGrid.getAdapter();

        int numCubes = adapter.getCount();
        assertEquals(16, numCubes);

        final int[] leftCounts = new int[8];
        final int[] topCounts = new int[8];
        final int[] rightCounts = new int[8];
        final int[] bottomCounts = new int[8];

        final ArrayList<CubeView> cubes = adapter.cubes;

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (CubeView cube : cubes) {
                    for(int j = 0; j < 4; j++) {
                        switch(j) {
                            case 0: leftCounts[cube.showLeft()]++;
                                break;
                            case 1: topCounts[cube.showTop()]++;
                                break;
                            case 2: rightCounts[cube.showRight()]++;
                                break;
                            case 3: bottomCounts[cube.showBottom()]++;
                                break;
                        }
                    }
                }
            }
        });

        for(int i = 0; i < 8; i++) {
            assertEquals(2, leftCounts[i]);
            assertEquals(2, topCounts[i]);
            assertEquals(2, rightCounts[i]);
            assertEquals(2, bottomCounts[i]);
        }

        //========================================
        // Test the settings.xml parsing functions
        //========================================

        Settings settings = Settings.deserialize();
        // Test theme parsing
        settings.setTheme("Red");
        assertEquals("Red",settings.getTheme());
        settings.setTheme("Green");
        assertEquals("Green",settings.getTheme());
        // Test topic parsing
        settings.setTopic("Reptiles");
        assertEquals("Reptiles", settings.getTopic());
        settings.setTopic("fish");
        assertEquals("fish", settings.getTopic());
        settings.setTopic("Mammals");
        assertEquals("Mammals", settings.getTopic());

        // Test fling parsing
        settings.setFling(false);
        assertEquals(false, settings.isFling());
        settings.setFling(true);
        assertEquals(true, settings.isFling());
        // Test swipe parsing
        settings.setSwipe(false);
        assertEquals(false, settings.isSwipe());
        settings.setSwipe(true);
        assertEquals(true, settings.isSwipe());
    }

    public void testImageLookup() throws Throwable {
        int[] fish = new int[] {
            R.drawable.fish1,
            R.drawable.fish2,
            R.drawable.fish3,
            R.drawable.fish4,
            R.drawable.fish5,
            R.drawable.fish6,
            R.drawable.fish7,
            R.drawable.fish8,
        };

        int[] reptiles = new int[] {
            R.drawable.reptile1,
            R.drawable.reptile2,
            R.drawable.reptile3,
            R.drawable.reptile4,
            R.drawable.reptile5,
            R.drawable.reptile6,
            R.drawable.reptile7,
            R.drawable.reptile8,
        };

        int[] mammals = new int[] {
            R.drawable.mammal1,
            R.drawable.mammal2,
            R.drawable.mammal3,
            R.drawable.mammal4,
            R.drawable.mammal5,
            R.drawable.mammal6,
            R.drawable.mammal7,
            R.drawable.mammal8,
        };

        CubeView cubeView = new CubeView(getActivity());
        for(int i = 0; i < 8; i++) {
            String name;

            name = "fish" + Integer.toString(i+1);
            assertEquals(fish[i], cubeView.getAndroidDrawable(name));

            name = "reptile" + Integer.toString(i+1);
            assertEquals(reptiles[i], cubeView.getAndroidDrawable(name));

            name = "mammal" + Integer.toString(i+1);
            assertEquals(mammals[i], cubeView.getAndroidDrawable(name));
        }
    }
}