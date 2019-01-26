package com.example.student238033.got;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    DodajTrase dodajTrase;
    TrasyPunktowaneDB db;

    @Before
    public void przygotowanie(){
        dodajTrase = new DodajTrase();
        db = new TrasyPunktowaneDB();

    }
    @Test
    public void PunktyUjemne()  {
        assertEquals(false, dodajTrase.sprawdzPunkty(-50));
        assertEquals(false, dodajTrase.sprawdzPunkty(-2));
        assertEquals(false, dodajTrase.sprawdzPunkty(-1));
    }

    @Test
    public void PunktyZero()  {
        assertEquals(false, dodajTrase.sprawdzPunkty(0));
    }

    @Test
    public void PunktyOK()  {
        assertEquals(true, dodajTrase.sprawdzPunkty(1));
        assertEquals(true, dodajTrase.sprawdzPunkty(2));
        assertEquals(true, dodajTrase.sprawdzPunkty(3));
        assertEquals(true, dodajTrase.sprawdzPunkty(10));
        assertEquals(true, dodajTrase.sprawdzPunkty(18));
        assertEquals(true, dodajTrase.sprawdzPunkty(19));
        assertEquals(true, dodajTrase.sprawdzPunkty(20));
    }

    @Test
    public void PunktyZaDuze()  {
        assertEquals(false, dodajTrase.sprawdzPunkty(21));
        assertEquals(false, dodajTrase.sprawdzPunkty(22));
        assertEquals(false, dodajTrase.sprawdzPunkty(50));
    }
    @Test
    public void sprawdzWpisaneDaneOk()  {
        assertEquals(true, dodajTrase.sprawdzWpisaneDane("Kłodzko", "Kłodzka Góra", "Sudety", "Kotlina Kłodzka", "Początkujący", new Integer(4), new Integer(5)));
    }

    @Test(expected = NullPointerException.class)
    public void sprawdzWpisaneDaneNiewpisanyPunkt()  {
        dodajTrase.sprawdzWpisaneDane("Kłodzko", "Kłodzka Góra", "Sudety", "Kotlina Kłodzka", "Początkujący", null, new Integer(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void sprawdzWpisaneDaneWpisanyPunktZaDuzy()  {
        dodajTrase.sprawdzWpisaneDane("Kłodzko", "Kłodzka Góra", "Sudety", "Kotlina Kłodzka", "Początkujący", new Integer(50), new Integer(5));
    }

    @Test (expected = NullPointerException.class)
    public void sprawdzWpisaneDaneNieWpisanoStartu()  {
        dodajTrase.sprawdzWpisaneDane(" ", "Kłodzka Góra", "Sudety", "Kotlina Kłodzka", "Początkujący", new Integer(5), new Integer(5));
    }




    }






