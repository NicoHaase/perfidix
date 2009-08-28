package org.perfidix.perclipse.views;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.perfidix.perclipse.views.BenchTreeContentProvider;
import org.perfidix.perclipse.viewtreedata.TreeDataProvider;

/**
 * This class tests the java class
 * {@link org.perfidix.perclipse.views.BenchTreeContentProvider}.
 * 
 * @author Lewandowski Lukas, DiSy, University of Konstanz
 */
public class BenchTreeContentProviderTest {
    private BenchTreeContentProvider treeContentProvider;
    private TreeDataProvider dataProvider;

    /**
     * Simple setUp - method.
     * 
     * @throws java.lang.Exception
     *             The Exception occurred.
     */
    @Before
    public void setUp() throws Exception {
        dataProvider = new TreeDataProvider("package.Class.TheElement", 25, 17);
        treeContentProvider = new BenchTreeContentProvider();
    }

    /**
     * Simple tearDown - method.
     * 
     * @throws java.lang.Exception
     *             The Exception occurred.
     */
    @After
    public void tearDown() throws Exception {
        dataProvider = null;
        treeContentProvider = null;
    }

    /**
     * Tests the method
     * {@link org.perfidix.perclipse.views.BenchTreeContentProvider#getChildren(Object)}
     * .
     */
    @Test
    public void testGetChildren() {
        // Expected no children because currently our objects has no children in
        // the treeviewer
        assertNotNull(treeContentProvider.getChildren(null));
        assertArrayEquals(new Object[0], treeContentProvider.getChildren(null));
        assertArrayEquals(new Object[0], treeContentProvider
                .getChildren(dataProvider));

    }

    /**
     * Tests the method
     * {@link org.perfidix.perclipse.views.BenchTreeContentProvider#hasChildren(Object)}
     * .
     */
    @Test
    public void testHasChildren() {
        // Expected no children because currently our objects has no children in
        // the treeviewer
        assertFalse(treeContentProvider.hasChildren(null));
        assertFalse(treeContentProvider.hasChildren(dataProvider));
    }

    /**
     * Tests the method
     * {@link org.perfidix.perclipse.views.BenchTreeContentProvider#getParent(Object)}
     * .
     */
    @Test
    public void testGetParent() {
        assertNull(treeContentProvider.getParent(null));
        assertNotNull(treeContentProvider.getParent(dataProvider));
        assertEquals(dataProvider, treeContentProvider.getParent(dataProvider));
        TreeDataProvider newDataProvider = new TreeDataProvider("A", 22, 9);
        assertFalse(newDataProvider.equals(treeContentProvider
                .getParent(dataProvider)));
    }
}
