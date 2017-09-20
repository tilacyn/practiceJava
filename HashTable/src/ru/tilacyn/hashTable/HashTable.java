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
	
	private List[] arr;
	
	/**
	 * количество ключей в таблице
	 */
	
	private int size;
	
	/**
	 * Конструктор, который создает массив размера 1000
	 */
	
	public HashTable(){
		size = 0;
		arr = new List[1000];
		for(int i = 0; i < 1000; i++) {
			arr[i] = new List();
		}
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
		int i = key.hashCode() % arr.length;
		size--;
		return arr[i].remove(key);
	}
	
	/**
	 * Проверка на присутствие ключа в таблице
	 * @param key - ключ
	 * @return true, если есть такой, false - иначе
	 */
	
	public boolean contains(String key) {
		int i = key.hashCode() % arr.length;
		return arr[i].contains(key);
	}
	
	/**
	 * Значение по ключу
	 * @param key - ключ
	 * @return значение или null, если ключ не найден
	 */
	
	public String get(String key) {
		int i = key.hashCode() % arr.length;
		return arr[i].get(key);
	}
	
	/**
	 * Кладет в хеш-таблицу новую пару ключ, значение
	 * @param key - ключ
	 * @param s - значение
	 * @return значение, если этот ключ присутствовал в хеш-табоице или null иначе
	 */
	
	public String put(String key, String s) {
		int i = key.hashCode() % arr.length;
		String res = null;
		if(contains(key)) {
			res = remove(key);
		}
		arr[i].add(key, s);
		size++;
		return res;
	}
	
	/**
	 * Очищает хеш-таблицу, фактически копия конструктора
	 */
	
	public void clear() {
		size = 0;
		arr = new List[1000];
		for(int i = 0; i < 1000; i++) {
			arr[i] = new List();
		}
	}

	
	
	
}
