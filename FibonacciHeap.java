import java.util.*;

public class FibonacciHeap<T extends Comparable<T>> {

    private Node<T> min;
    private int size;
    public int COUNT_OF_OPERATIONS = 0;

    public FibonacciHeap() {
        min = null;
        size = 0;
    }

    public boolean isEmpty() {
        return min == null;
    }

    public int size() {
        return size;
    }

    public void insert(T value) {
        Node<T> node = new Node<>(value);
        COUNT_OF_OPERATIONS++;
        if (min == null) {
            min = node;
        } else {
            node.right = min.right;
            min.right = node;
            node.right.left = node;
            node.left = min;
            if (node.value.compareTo(min.value) < 0) {
                min = node;
            }
        }
        size++;
    }

    public T findMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return min.value;
    }

    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        Node<T> z = min;
        if (z.child != null) {
            Node<T> child = z.child;
            do {
                child.parent = null;
                child = child.right;
                COUNT_OF_OPERATIONS++;
            } while (child != z.child);
            Node<T> minRight = min.right;
            Node<T> childLeft = z.child.left;
            min.right = z.child;
            z.child.left = min;
            childLeft.right = minRight;
            minRight.left = childLeft;
        }
        z.left.right = z.right;
        z.right.left = z.left;
        if (z == z.right) {
            min = null;
        } else {
            min = z.right;
            consolidate();
        }
        size--;
        return z.value;
    }

    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(size) / Math.log(2));
        List<Node<T>> degreeTable = new ArrayList<>(maxDegree + 1);
        for (int i = 0; i <= maxDegree; i++) {
            degreeTable.add(null);
        }
        int numRoots = 0;
        Node<T> x = min;
        if (x != null) {
            numRoots++;
            x = x.right;
            while (x != min) {
                numRoots++;
                x = x.right;
            }
        }
        while (numRoots > 0) {
            int d = x.degree;
            Node<T> next = x.right;
            while (true) {
                Node<T> y = degreeTable.get(d);
                if (y == null) {
                    break;
                }
                if (x.value.compareTo(y.value) > 0) {
                    Node<T> temp = x;
                    x = y;
                    y = temp;
                }
                link(y, x);
                degreeTable.set(d, null);
                d++;
            }
            degreeTable.set(d, x);
            x = next;
            numRoots--;
        }
        min = null;
        for (int i = 0; i <= maxDegree; i++) {
            Node<T> y = degreeTable.get(i);
            if (y == null) {
                continue;
            }
            if (min == null) {
                min = y;
            } else {
                y.left.right = y.right;
                y.right.left = y.left;
                y.left = min;
                y.right = min.right;
                min.right = y;
                y.right.left = y;
                if (y.value.compareTo(min.value) < 0) {
                    min = y;
                }
            }
        }
    }

    private void link(Node<T> y, Node<T> x) {
        y.left.right = y.right;
        y.right.left = y.left;
        y.parent = x;
        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }
        x.degree++;
        y.marked = false;
    }

    private static class Node<T extends Comparable<T>> {

        private T value;
        private Node<T> parent;
        private Node<T> child;
        private Node<T> left;
        private Node<T> right;
        private int degree;
        private boolean marked;

        private Node(T value) {
            this.value = value;
            this.left = this;
            this.right = this;
            this.degree = 0;
            this.marked = false;
        }
    }
}


