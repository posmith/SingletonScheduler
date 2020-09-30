package util;


public class SortedArrayList<T extends Comparable<T>> implements SortedList<T> {
	/** Array containing list of elements */
	private T[] array;
	/** Size of array */
	private int size = 0;
	/** Initial size of array */
	private static final int INITIAL_CAPACITY = 40;

	@SuppressWarnings("unchecked")
	public SortedArrayList() {
		Object[] temp = new Object[INITIAL_CAPACITY];
		array = (T[]) temp;
	}
	
	@Override
	public boolean add(T e) {
		if (e == null) {
			throw new NullPointerException("Cannot add null element");
		}
		if (this.contains(e)) {
			throw new IllegalArgumentException("Element already in list");
		}
		if (size == array.length) {
			growArray();
		}
		if (size == 0) {
			array[0] = e;
		} else {
			int count = 0;
			for (int i = 0; i < size; i++) {
				if (e.compareTo((T) array[i]) > 0) {
					count++;
				}
			}
			for (int i = size; i >= count; i--) {
				array[i] = array[i-1];
			}
			array[count] = e;
		}
		size++;
		return false;
	}

	@SuppressWarnings("unchecked")
	private void growArray() {
		int capacity = array.length * 2;
		Object[] buffer = new Object[capacity];
		for (int i = 0; i < size; i++) {
			buffer[i] = array[i];
		}
		array = (T[]) buffer;		
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(T e) {
		for (int i = 0; i < size(); i++)
			if (array[i].equals(e)) {
				return true;
			}
		return false;
	}

	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		T temp = array[index];
		for (int i = index; i < size(); i++) {
			array[i] = array[i + 1];
		}
		size--;
		return temp;
	}

	@Override
	public int indexOf(T e) {
		for (int i = 0; i < size(); i++) {
			if (array[i].equals(e)) {
				return i;
			}
		}
		return -1;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < size - 1; i++) {
			s += array[i].toString() + "\n";
		}
		return s + array[size].toString();
	}

}
