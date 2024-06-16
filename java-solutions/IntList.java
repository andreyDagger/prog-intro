import java.util.Arrays;

public class IntList {
    private int[] array;
    private int size;

    public IntList() {
        array = new int[0];
        size = 0;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index in IntList.get() must be in range [0, size)");
        }
        return array[index];
    }

    public int size() {
        return size;
    }

    private void normalize() {
        if (size == 0) {
            array = new int[1];
            return;
        }
        int[] temp = Arrays.copyOf(array, size);
        array = new int[2 * size];
        System.arraycopy(temp, 0, array, 0, size);
    }

    public void pushBack(int x) {
        if (size == array.length) {
            normalize();
        }
        array[size++] = x;
    }
}
