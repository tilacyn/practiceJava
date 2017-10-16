package ru.tilacyn.list;

/**
 * Класс Node: вспомогательный класс для List
 * Хранит пару: {key, s}
 * @author tilac
 *
 */



/**
 * Двусвязный список List (в реализации HashTable коллизии устраняются методом цепочек)
 *
 * @author tilac
 *
 */

public class List {
    public class Node {
        public String s, key;
        public Node next, prev;
        Node(String key, String s) {
            next = prev = null;
            this.s = s;
            this.key = key;
        }
    }

    /**
     * Голова списка, хвост не храним
     */

    public Node head;

    public List() {
        head = null;
    }


    /**
     * Добавляет Node с ключом и значением
     * @param key - ключ
     * @param s - значение
     */

    public void add(String key, String s) {
        Node node = new Node(key, s);
        if(head == null) {
            head = node;
        }
        else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    /**
     * Проверка на присутствие данного ключа в списке
     * Работает за линию, как поиск в обычном двусвязном списке
     * Этот же перебор применяется в последующий методах
     * @param key - ключ
     * @return true, если есть такой, и false иначе
     */

    public boolean contains(String key) {
        Node cur = head;
        while(cur != null) {
            if(cur.key.equals(key)) return true;
            cur = cur.next;
        }
        return false;
    }

    /**
     * Удаление ключа и значения: происходит, как в обычном двусвязном списке
     * @param key - ключ
     * @return значение по ключу или null, если такого ключа нет
     */

    public String remove(String key) {
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
                return cur.s;
            }
            cur = cur.next;
        }
        return null;
    }

    /**
     * Возвращает значение по ключу, не меняя список
     * @param key - ключ
     * @return значение, если ключ найден или null, если ключа нет
     */

    public String get(String key) {
        Node cur = head;
        while(cur != null) {
            if(cur.key.equals(key)) return cur.s;
            cur = cur.next;
        }
        return null;
    }

}
