import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('z', 'd'));
        assertFalse(offByOne.equalChars('b', 'b'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertFalse(offByOne.equalChars('a', 'B'));
    }
}
