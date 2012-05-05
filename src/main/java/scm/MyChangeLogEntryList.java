package scm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * シリアライズ出来るようにクラスにするためだけのクラス
 * @author nilfs
 */
public class MyChangeLogEntryList implements List<MyChangeLogEntry>
{
	public boolean add(MyChangeLogEntry e) {
		return logs.add(e);
	}

	public void add(int index, MyChangeLogEntry element) {
		logs.add(index, element);
	}

	public boolean addAll(Collection<? extends MyChangeLogEntry> c) {
		return logs.addAll(c);
	}

	public boolean addAll(int index,
			Collection<? extends MyChangeLogEntry> c) {
		return logs.addAll(index, c);
	}

	public void clear() {
		logs.clear();
	}

	public boolean contains(Object o) {
		return logs.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return logs.containsAll(c);
	}

	public MyChangeLogEntry get(int index) {
		return logs.get(index);
	}

	public int indexOf(Object o) {
		return logs.indexOf(o);
	}

	public boolean isEmpty() {
		return logs.isEmpty();
	}

	public Iterator<MyChangeLogEntry> iterator() {
		return logs.iterator();
	}

	public int lastIndexOf(Object o) {
		return logs.lastIndexOf(o);
	}

	public ListIterator<MyChangeLogEntry> listIterator() {
		return logs.listIterator();
	}

	public ListIterator<MyChangeLogEntry> listIterator(int index) {
		return logs.listIterator(index);
	}

	public boolean remove(Object o) {
		return logs.remove(o);
	}

	public MyChangeLogEntry remove(int index) {
		return logs.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		return logs.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return logs.retainAll(c);
	}

	public MyChangeLogEntry set(int index, MyChangeLogEntry element) {
		return logs.set(index, element);
	}

	public int size() {
		return logs.size();
	}

	public List<MyChangeLogEntry> subList(int fromIndex, int toIndex) {
		return logs.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return logs.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return logs.toArray(a);
	}
	
	private List<MyChangeLogEntry> logs = new ArrayList<MyChangeLogEntry>();
}
