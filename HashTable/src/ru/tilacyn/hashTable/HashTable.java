package ru.tilacyn.hashTable;

import ru.tilacyn.list.*;

/**
 * ���-������� �� ������� � �������� ����������
 * ��������������� �� �����������, ��� ��� �� ����������� � �������
 * @author tilac
 *
 */

public class HashTable {
	
	/**
	 * ������ �������
	 */
	
	private List[] arr;
	
	/**
	 * ���������� ������ � �������
	 */
	
	private int size;
	
	/**
	 * �����������, ������� ������� ������ ������� 1000
	 */
	
	public HashTable(){
		size = 0;
		arr = new List[1000];
		for(int i = 0; i < 1000; i++) {
			arr[i] = new List();
		}
	}
	
	/**
	 * ���������� ��������� ������
	 * @return size
	 */
	
	public int size() {
		return size;
	}
	
	/**
	 * ������� ���� ����, �������� �� �������
	 * @param key - ����
	 * @return �������� �� ����� ��� null, ���� ���� �� ������
	 */
	
	public String remove(String key) {
		int i = key.hashCode() % arr.length;
		size--;
		return arr[i].remove(key);
	}
	
	/**
	 * �������� �� ����������� ����� � �������
	 * @param key - ����
	 * @return true, ���� ���� �����, false - �����
	 */
	
	public boolean contains(String key) {
		int i = key.hashCode() % arr.length;
		return arr[i].contains(key);
	}
	
	/**
	 * �������� �� �����
	 * @param key - ����
	 * @return �������� ��� null, ���� ���� �� ������
	 */
	
	public String get(String key) {
		int i = key.hashCode() % arr.length;
		return arr[i].get(key);
	}
	
	/**
	 * ������ � ���-������� ����� ���� ����, ��������
	 * @param key - ����
	 * @param s - ��������
	 * @return ��������, ���� ���� ���� ������������� � ���-������� ��� null �����
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
	 * ������� ���-�������, ���������� ����� ������������
	 */
	
	public void clear() {
		size = 0;
		arr = new List[1000];
		for(int i = 0; i < 1000; i++) {
			arr[i] = new List();
		}
	}

	
	
	
}
