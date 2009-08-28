/**
 * 
 */
package org.perfidix.perclipse.buildpath;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.perfidix.perclipse.buildpath.BuildPathSupport;
import org.perfidix.perclipse.launcher.PerclipseActivator;

/**
 * This class tests the java class
 * {@link org.perfidix.perclipse.buildpath.BuildPathSupport}.
 * 
 * @author Lewandowski Lukas, DiSy, University of Konstanz
 */
public class BuildPathSupportTest {

    private PerclipseActivator activator;
    /**
     * Simple setUp - method.
     * 
     * @throws java.lang.Exception
     *             The Exception occurred.
     */
    @Before
    public void setUp() throws Exception {
        activator=PerclipseActivator.getDefault();
        
    }

    /**
     * Simple tearDown - method.
     * 
     * @throws java.lang.Exception
     *             The Exception occurred.
     */
    @After
    public void tearDown() throws Exception {
        activator=null;
    }

    /**
     * Test method for {@link org.perfidix.perclipse.buildpath.BuildPathSupport#getPerfidixClasspathEntry()}.
     */
    @Test
    public void testGetPerfidixClasspathEntry() {
        assertNotNull(BuildPathSupport.getPerfidixClasspathEntry());
    }
    
    /**
     * Test method for {@link org.perfidix.perclipse.buildpath.BuildPathSupport#getBundleLocation()}.
     */
    @Test
    public void testGetBundleLocation() {
        assertNotNull(BuildPathSupport.getBundleLocation());
    }
    
    /**
     * Test method for {@link org.perfidix.perclipse.buildpath.BuildPathSupport#getPerfidixLibraryEntry()}.
     */
    @Test
    public void testGetPerfidixLibraryEntry() {
        assertNotNull(BuildPathSupport.getPerfidixLibraryEntry());
    }


}
