package de.rub.nds.praktikum.crypto;

import de.rub.nds.praktikum.util.Util;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe3.class)
public class HkdFunctionTest {

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    public HkdFunctionTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     *
     */
    @Test
    public void testExtractHandshake() {
        byte[] expand = HkdFunction.extract(Util.hexStringToByteArray("6f2615a108c702c5678f54fc9dbab69716c076189c48250cebeac3576c3611ba"), Util.hexStringToByteArray("8151d1464c1b55533623b9c2246a6a0e6e7e185063e14afdaff0b6e1c61a8642"));
        assertArrayEquals(Util.hexStringToByteArray("5b4f965df03c682c46e6ee86c311636615a1d2bbb24345c25205953c879e8d06"), expand);
    }

    /**
     *
     */
    @Test
    public void testExtractEarly() {
        byte[] expand = HkdFunction.extract(Util.hexStringToByteArray(""), Util.hexStringToByteArray("0000000000000000000000000000000000000000000000000000000000000000"));
        assertArrayEquals(Util.hexStringToByteArray("33ad0a1c607ec03b09e6cd9893680ce210adf300aa1f2660e1b22e10f170f92a"), expand);
    }

    /**
     *
     */
    @Test
    public void testExtractMaster() {
        byte[] expand = HkdFunction.extract(Util.hexStringToByteArray("c8615719e2403747b610762c72b8f4da5c60995765d404a9d006b9b0727ba583"), Util.hexStringToByteArray("0000000000000000000000000000000000000000000000000000000000000000"));
        assertArrayEquals(Util.hexStringToByteArray("5c79d169424e262b563203627be4eb51033f588c43c9ce0373372dbcbc0185a7"), expand);
    }

    /**
     *
     */
    @Test
    public void testExpand() {
        byte[] expand = HkdFunction.expand(Util.hexStringToByteArray("3b7a839c239ef2bf0b7305a0e0c4e5a8c6c69330a753b308f5e3a83aa2ef6979"), Util.hexStringToByteArray("001009746c733133206b657900"), 16);
        assertArrayEquals(Util.hexStringToByteArray("c66cb1aec519df44c91e10995511ac8b"), expand);
    }

    /**
     *
     */
    @Test
    public void testDeriveSecret() {
        byte[] derivedSecret = HkdFunction.deriveSecret(Util.hexStringToByteArray("33ad0a1c607ec03b09e6cd9893680ce210adf300aa1f2660e1b22e10f170f92a"), "derived", Util.hexStringToByteArray(""));
        assertArrayEquals(Util.hexStringToByteArray("6f2615a108c702c5678f54fc9dbab69716c076189c48250cebeac3576c3611ba"), derivedSecret);
    }
}
