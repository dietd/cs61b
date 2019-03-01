import org.junit.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestUnionFind {
    @Test
    public void testConnected() {
        UnionFind un = new UnionFind(10);
        un.union(1, 2);
        un.union(1, 3);
        un.union(2, 3);
        un.union(0, 3);
        un.union(1, 3);
        un.union(9, 8);
        assertEquals(false, un.connected(9, 3));
    }
}
