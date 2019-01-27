package com.example.student238033.got;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Przygotowano 7 testów funkcjonalnych.
 * Przypadki testowe:
 * • Ustawianie podgrupy górskiej, w której będzie planowana trasa.
 * • Pokazanie, że po wybraniu podgrupy górskiej nie można jej już zmienić.
 * • Wątek główny: wybranie podgrupy górskiej i oraz dwóch punktów na trasie, oraz zapisanie trasy.
 * • Po zaplanowaniu trasy, brak chęci do planowania kolejnej.
 * • Planowanie trasy z 4 punktami na trasie.
 * • Anulowanie planowania trasy.
 * • Wypróbowanie możliwości zmiany ostatniego punktu na trasie.
 */
@RunWith(AndroidJUnit4.class)
public class ZaplanujTraseTest {

    /**
     * The Activity test rule.
     */
    @Rule
    public ActivityTestRule<ZaplanujTrase> activityTestRule =
            new ActivityTestRule<>(ZaplanujTrase.class);


    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception{
        Intents.init();
    }


    /**
     * Test ustaw podgrupe gorska.
     */
    @Test
    public void testUstawPodgrupeGorska()
    {
        String text = new String("Kotlina Kłodzka");
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(text))).perform(click());
        onView(withId(R.id.listaPodgrupGorskich)).check(matches(withSpinnerText(containsString(text))));
    }

    /**
     * Test brak mozliwosci zmiany wybranej podgrupy gorskiej.
     */
    @Test
    public void testBrakMozliwosciZmianyWybranejPodgrupyGorskiej()
    {
        String text = new String("Kotlina Kłodzka");
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(text))).perform(click());

        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());

    }

    /**
     * Test watek głowny.
     */
    @Test
    public void testWatekGłowny()
    {
        String text = new String("Kotlina Kłodzka");
        String start = new String("Kłodzko");
        String meta = new String("Kłodzka Góra");
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(text))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onData(allOf(is(instanceOf(String.class)), is(start))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onData(allOf(is(instanceOf(String.class)), is(meta))).perform(click());
        onView(withId(R.id.zapisz)).perform(click());

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("TAK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

    }

    /**
     * Test brak checi do planowania kolejenej trasy.
     */
    @Test
    public void testBrakCheciDoPlanowaniaKolejenejTrasy()
    {
        String text = new String("Kotlina Kłodzka");
        String start = new String("Kłodzko");
        String meta = new String("Kłodzka Góra");
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(text))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onData(allOf(is(instanceOf(String.class)), is(start))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onData(allOf(is(instanceOf(String.class)), is(meta))).perform(click());
        onView(withId(R.id.zapisz)).perform(click());

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("NIE"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

    }

    /**
     * Test cztery punkty na trasie.
     */
    @Test
    public void testCzteryPunktyNaTrasie()
    {
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kotlina Kłodzka"))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onData(allOf(is(instanceOf(String.class)), is("Kłodzko"))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onData(allOf(is(instanceOf(String.class)), is("Ołdrzychowice Kłodzkie"))).perform(click());
        onView(withId(R.id.PrzyciskDodajPunkt)).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onData(allOf(is(instanceOf(String.class)), is("Rogówka"))).perform(click());
        onView(withId(R.id.PrzyciskDodajPunkt)).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onData(allOf(is(instanceOf(String.class)), is("Sarnica"))).perform(click());
        onView(withId(R.id.zapisz)).perform(click());

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("TAK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

    }

    /**
     * Test anulowanie.
     */
    @Test
    public void testAnulowanie()
    {
        String text = new String("Kotlina Kłodzka");
        String start = new String("Kłodzko");
        String meta = new String("Kłodzka Góra");
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(text))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onData(allOf(is(instanceOf(String.class)), is(start))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onData(allOf(is(instanceOf(String.class)), is(meta))).perform(click());
        onView(withId(R.id.PrzyciskDodajPunkt)).perform(click());

        onView(withId(R.id.anuluj)).perform(click());

    }

    /**
     * Test zmiana ostatniego punktu.
     */
    @Test
    public void testZmianaOstatniegoPunktu()
    {
        onView(withId(R.id.listaPodgrupGorskich)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Kotlina Kłodzka"))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onData(allOf(is(instanceOf(String.class)), is("Kłodzko"))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onData(allOf(is(instanceOf(String.class)), is("Ołdrzychowice Kłodzkie"))).perform(click());
        onView(withId(R.id.PrzyciskDodajPunkt)).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onData(allOf(is(instanceOf(String.class)), is("Rogówka"))).perform(click());
        onView(withId(R.id.punktyNaTrasie)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onData(allOf(is(instanceOf(String.class)), is("Sarnica"))).perform(click());
        onView(withId(R.id.zapisz)).perform(click());

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("TAK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception
    {
        Intents.release();
    }
}
