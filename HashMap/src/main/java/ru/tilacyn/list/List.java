package ru.tilacyn.list;


public class List<K,V> {

    private class Node<K,V> {
        public V s;
        public K key;
        public Node next, prev;
        Node(K key, V s){
            next = prev = null;
            this.s = s;
            this.key = key;
        }
    }


    public Node head;

    public List() {
        head = null;
    }


    public void add(K key, V s) {
        Node node = new Node(key, s);
        if(head == null) head = node;
        else {
            node.next = head;
            head = node;
        }
    }


    public boolean contains(K key) {
        Node cur = head;
        while(cur != null) {
            if(cur.key.equals(key)) return true;
            cur = cur.next;
        }
        return false;
    }


    public V remove(K key) {
        Node cur = head;
        while(cur != null) {
            if(cur.key.equals(key)) {
                if(cur.equals(head)) {
                    if(cur.next == null) {
                        head = null;
                    } else {
                        head = cur.next;
                        head.prev = null;
                    }
                } else {
                    if(cur.next == null) {
                        cur.prev.next = null;
                    } else {
                        cur.prev.next = cur.next;
                        cur.next.prev = cur.prev;
                    }
                }
                return (V) cur.s;
            }
            cur = cur.next;
        }
        return null;
    }

    public V get(K key) {
        Node cur = head;
        while(cur != null) {
            if(cur.key.equals(key)) return (V) cur.s;
            cur = cur.next;
        }
        return null;
    }

}

