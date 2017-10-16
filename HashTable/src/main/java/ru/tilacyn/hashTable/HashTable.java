package ru.tilacyn.hashTable;

import ru.tilacyn.list.*;

/**
 * Хеш-таблица на списках с закрытой адресацией
 * Перехеширование не реализовано, так как не требовалось в задании
 * @author tilac
 *
 */

public class HashTable {

    /**
     * массив списков
     */

    private List[] data;

    /**
     * количество ключей в таблице
     */

    private int size = 0;

    /**
     * Конструктор
     * @param n - размер массива
     */

    public HashTable(int n){
        data = new List[n];
        for(int i = 0; i < n; i++) {
            data[i] = new List();
        }
    }

    /**
     * Хеширует строку
     * @param s - строка
     * @return хеш-код типа int
     */

    public int getHash(String s){
        return s.hashCode() % data.length;
    }

    /**
     * Количество различных ключей
     * @return size
     */

    public int size() {
        return size;
    }

    /**
     * Удаляет пару ключ, значение из таблицы
     * @param key - ключ
     * @return значение по ключу или null, если ключ не найден
     */

    public String remove(String key) {
        int i = getHash(key);
        size--;
        return data[i].remove(key);
    }

    /**
     * Проверка на присутствие ключа в таблице
     * @param key - ключ
     * @return true, если есть такой, false - иначе
     */

    public boolean contains(String key) {
        int i = getHash(key);
        return data[i].contains(key);
    }

    /**
     * Значение по ключу
     * @param key - ключ
     * @return значение или null, если ключ не найден
     */

    public String get(String key) {
        int i = getHash(key);
        return data[i].get(key);
    }

    /**
     * кладет в хеш-таблицу новую пару ключ, значение
     * если размер более, чем в два раза превышает размер массива data, вызывается метод rebuild
     * @param key - ключ
     * @param s - значение
     * @return значение, если этот ключ присутствовал в хеш-табоице или null иначе
     */

    public String put(String key, String s) {
        if(size > 2 * data.length) {
            rebuild();
        }
        int i = getHash(key);
        String res = null;
        if(contains(key)) {
            res = remove(key);
        }
        data[i].add(key, s);
        size++;
        return res;
    }

    /**
     * Очищает хеш-таблицу, фактически копия конструктора
     */

    public void clear() {
        size = 0;
        int n = data.length;
        data = new List[n];
        for(int i = 0; i < n; i++) {
            data[i] = new List();
        }
    }

    /**
     * rebuilds our HashTable
     * it increases size of data array twice
     * and copies all data to a new List array
     * rehashing is being done since now our hash-function has changed
     */

    public void rebuild() {
        int n = data.length;
        List[] oldData = data.clone();
        data = new List[2 * n];
        for(int i = 0; i < 2 * n; i++) {
            data[i] = new List();
        }

        for(int i = 0; i < n; i++) {
            while(oldData[i].head != null) {
                put(oldData[i].head.key, oldData[i].head.s);
                oldData[i].remove(oldData[i].head.key);
            }
        }
    }
}

