package parser;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thuis on 12/17/2015.
 */
public class AirportFinderTest {

    @org.junit.Test
    public void testFind() throws Exception {
        boolean didFind = AirportFinder.find("Bolivia");
        assertEquals(true,didFind);
        System.out.println("@Test find Bolivia complete");
    }

    @org.junit.Test
    public void testNotFind() throws Exception {
        boolean didFind = AirportFinder.find("Boasdavia");
        assertEquals(false,didFind);
        System.out.println("@Test find Boasdavia complete");
    }
    @org.junit.Test
    public void testGoodCode() throws Exception {
        boolean didFind = AirportFinder.find("BO");
        assertEquals(true,didFind);
        System.out.println("@Test find Bolivia code complete");
    }

    @org.junit.Test
    public void testBadCode() throws Exception {
        boolean didFind = AirportFinder.find("B1");
        assertEquals(false,didFind);
        System.out.println("@Test find B1 complete");
    }

    @org.junit.Test
    public void testRepeatedFind() throws Exception {
        boolean didFind = AirportFinder.find("Bolivia");
        assertEquals(true,didFind);
        didFind = AirportFinder.find("Australia");
        assertEquals(true,didFind);
        System.out.println("@Test repeated find complete");
    }

    @org.junit.Test
    public void testRepeatedFindMixed() throws Exception {
        boolean didFind = AirportFinder.find("Bolivia");
        assertEquals(true,didFind);
        didFind = AirportFinder.find("A2");
        assertEquals(false,didFind);
        System.out.println("@Test repeated find mix complete");
    }
}