package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import manager.AbstractManager;
import java.util.HashMap;
import java.util.Map;

public class AbstractManagerTest {

    private AbstractManager<String> manager;

    @Before
    public void setUp() {
        manager = new AbstractManager<String>() {};
    }

    @Test
    public void testAdd() {
        assertTrue(manager.add("1", "Item1"));
        assertFalse(manager.add("1", "Item2"));
        assertEquals("Item1", manager.FindById("1"));
    }

    @Test
    public void testRemove() {
        manager.add("1", "Item1");
        assertTrue(manager.remove("1"));
        assertFalse(manager.remove("1"));
    }

    @Test
    public void testFindById() {
        manager.add("1", "Item1");
        assertEquals("Item1", manager.FindById("1"));
        assertNull(manager.FindById("2"));
    }

    @Test
    public void testIsExists() {
        manager.add("1", "Item1");
        assertTrue(manager.isExists("1"));
        assertFalse(manager.isExists("2"));
    }

    @Test
    public void testUpdate() {
        manager.add("1", "Item1");
        assertTrue(manager.update("1", "UpdatedItem1"));
        assertEquals("UpdatedItem1", manager.FindById("1"));
        assertFalse(manager.update("2", "UpdatedItem2"));
    }

    @Test
    public void testGet() {
        manager.add("1", "Item1");
        Map<String, String> expected = new HashMap<>();
        expected.put("1", "Item1");
        assertEquals(expected, manager.get());
    }
}
