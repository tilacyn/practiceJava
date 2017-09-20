package ru.tilacyn.list;

/**
 * ���������� ������ List ��� HashTable(�������� ����������� ������� �������)
 * @author tilac
 *
 */

public class List {
	
	/**
	 * ������ ������, ����� �� ������
	 */
	
	public Node head;
	
	public List() {
		head = null;
	}
	
	
	/**
	 * ��������� Node � ������ � ���������
	 * @param key - ����
	 * @param s - �������� 
	 */
	
	public void add(String key, String s) {
		Node node = new Node(key, s);
		if(head == null) head = node;
		else {
			node.next = head;
			head = node;
		}
	}
	
	/**
	 * �������� �� ����������� ������� ����� � ������
	 * �������� �� �����, ��� ����� � ������� ���������� ������
	 * ���� �� ������� ����������� � ����������� �������
	 * @param key - ����
	 * @return true, ���� ���� �����, � false �����
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
	 * �������� ����� � ��������: ����������, ��� � ������� ���������� ������
	 * @param key - ����
	 * @return �������� �� ����� ��� null, ���� ������ ����� ���
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
	 * ���������� �������� �� �����, �� ����� ������
	 * @param key - ����
	 * @return ��������, ���� ���� ������ ��� null, ���� ����� ���
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
