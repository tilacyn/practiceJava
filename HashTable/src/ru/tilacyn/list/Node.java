package ru.tilacyn.list;

/**
 * Класс Node: вспомогательный класс для List
 * Хранит пару: {key, s}
 * @author tilac
 *
 */

public class Node {
	public String s, key;
	public Node next, prev;
	Node(String key, String s){
		next = prev = null;
		this.s = s;
		this.key = key;
	}
}
