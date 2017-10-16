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
     * Кладет в хеш-таблицу новую пару ключ, значение
     * @param key - ключ
     * @param s - значение
     * @return значение, если этот ключ присутствовал в хеш-табоице или null иначе
     */

    public String put(String key, String s) {
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
}

