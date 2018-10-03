package com.epam.pharmacy.util.listener;

import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.testng.Assert.*;

public class OnContextListenerTest {

    @Test
    public void testContextInitialized() {
        ResourceBundle bundle = ResourceBundle.getBundle("prop.pagecontent");
        assertNotNull(bundle);
    }
}