import com.sun.jdi.AbsentInformationException;

public class LinkedListDeque<T> {
    private class IntNode {
        public IntNode prev;
        public T item;
        public IntNode next;

        public IntNode(IntNode p, T i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }

        public IntNode() {
            item = null;
        }
    }

    private IntNode sentinel;
    private int size;

    public LinkedListDeque(T i) {
        sentinel = new IntNode();
        sentinel.next = new IntNode(sentinel, i, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public LinkedListDeque() {
        sentinel = new IntNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        sentinel.next = new IntNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev.next = new IntNode(sentinel.prev, item, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    private void printnaked(IntNode x) {
        if (x == sentinel) {
            return;
        }
        System.out.printf("%s ", x.item);
        printnaked(x.next);
    }

    public void printDeque() {
        IntNode x = sentinel.next;
        printnaked(x);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T x = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return x;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T x = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return x;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        IntNode p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursivenaked(IntNode x, int index) {
        if (index == 0) {
            return x.item;
        }
        return getRecursivenaked(x.next, index - 1);
    }

    public T getRecursive(int index) {
        if (isEmpty()) {
            return null;
        }
        IntNode x = sentinel.next;
        return getRecursivenaked(x, index);
    }
}