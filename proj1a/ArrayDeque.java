public class ArrayDeque<T> {
    private T [] item;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        item = (T []) new Object[8];
        first = 0;
        last = 0;
        size = 0;
    }

    public void addFirst(T x) {
        if (isEmpty()) {
            item[first] = x;
            size += 1;
            return;
        } else if (isFull()) {
            resizing(item.length * 2);
        }
        if (first !=0) {
            first -= 1;
        } else {
            first = item.length - 1;
        }
        item[first] = x;
        size += 1;
    }

    public void addLast(T x) {
        if (isEmpty()) {
            item[last] = x;
            size += 1;
            return;
        } else if (isFull()) {
            resizing(item.length * 2);
        }
        if (last != item.length-1) {
            last += 1;
        } else {
            last = 0;
        }
        item[last] = x;
        size += 1;
    }

    public boolean isFull() {
        if (size == item.length) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        if (size == 0) {
            first = 0;
            last = 0;
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public double ratio() {
        return size / (double)item.length;
    }

    public void printDeque() {
        for(int i=0; i < size; i++) {
            System.out.print(get(i));
            System.out.print(" ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T p = item[first];
        item[first] = null;
        size -= 1;
        if (first != item.length - 1) {
            first += 1;
        } else {
            first = 0;
        }
        if (ratio() < 0.25) {
            resizing(item.length / 2);
        }
        return p;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T p = item[last];
        item[last] = null;
        size -= 1;
        if (last != 0) {
            last -= 1;
        } else {
            last = item.length - 1;
        }
        if (ratio() < 0.25) {
            resizing(item.length / 2);
        }
        return p;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        } else {
            if (first <= last) {
                return item[first + index];
            } else {
                if (first + index <= item.length - 1) {
                    return item[first + index];
                } else {
                    return item[first + index - item.length];
                }
            }
        }
    }

    public void resizing(int capacity) {
        T [] a = (T []) new Object[capacity];
        if (first <= last) {
            System.arraycopy(item, first, a, 0, size);
        } else {
            System.arraycopy(item, first, a, 0, item.length - first);
            System.arraycopy(item, 0, a, item.length - first, last + 1);
        }
        item = a;
        first = 0;
        last = size-1;
    }
}
