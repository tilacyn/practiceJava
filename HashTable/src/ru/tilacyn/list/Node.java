package ru.tilacyn.list;

/**
 * ����� Node: ��������������� ����� ��� List
 * ������ ����: {key, s}
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
