package HW6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestsTest {

    @Test
    void TestResizeArrayInt() {
        Tests tests=new Tests();
       int[] a= new int[]{4,2, 3};
       assertArrayEquals(new int[]{2, 3},tests.resizeArrayInt(a));
    }
}