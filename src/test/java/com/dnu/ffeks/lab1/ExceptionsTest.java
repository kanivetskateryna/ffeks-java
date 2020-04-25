package com.dnu.ffeks.lab1;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ExceptionsTest {

    private SmallestRowFounder founder;

    @Before
    public void setUp() {
        founder = new SmallestRowFounderImpl();
    }


    @Test(expected = EmptyListException.class)
    public void shouldThrowEmptyListException() {
        // GIVEN
        founder.getTheSmallestRowOfList(new ArrayList<>());

        // WHEN // THEN
    }
}
