package util;

import java.util.AbstractList;

/**
 * This ArrayList class extends the AbstractList class and allows for generic
 * parameters of type E. The ArrayList class includes functionality from
 * AbstractList, including methods for adding elements to the list, removing
 * elements from the list, setting an element at a given index (replacing the
 * previous element at that index), getting (returning) an element at a given
 * index, and providing the size of the list as an integer representation of the
 * numbe of elements in the list. In addition to these functionalities,
 * ArrayList does not allow for the addition or setting of null or duplicate
 * elements in the list. The class also contains a private helper method used to
 * increase the size of the private array when the number of elements in the
 * array equals the size of the array. This allows for the array to extend
 * beyond its initial capacity.
 * 
 * @author Patrick Smith
 * @param <E> generic element used as a parameter in the class methods
 *
 */
public class ArrayList<E> extends AbstractList<E> {

	/** The constant value used for initializing the list size */
	private static final int INIT_SIZE = 10;
	/** An array of the generic type E */
	private E[] list;
	/** Value for the size of the list */
	private int size = 0;

	/**
	 * Constructor method for an ArrayList which creates an array of objects equal
	 * in length to the constant INIT_SIZE. The method casts the Object array into a
	 * generic element array to allow for generic objects to be used as parameters
	 * in the class.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		Object[] temp = new Object[INIT_SIZE];
		list = (E[]) temp;
	}

	/**
	 * Inserts the specified element at the specified position in this ArrayList.
	 * Shifts the element currently at that position(if any) and any subsequent
	 * elements to the right, adding one to their indexes.
	 * 
	 * @param index   index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws NullPointerException      if the specified element is null
	 * @throws IllegalArgumentException  if the specified element is a duplicate of
	 *                                   an existing element in this list
	 * @throws IndexOutOfBoundsException if the index is out of range ( less than 0
	 *                                   or greater than size)
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element");
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		for (int i = 0; i < size; i++) {
			if (element.equals(list[i])) {
				throw new IllegalArgumentException("Duplicate element");
			}
		}
		if (size == list.length) {
			growArray();
		}
		if (size == 0) {
			list[0] = element;
		} else {
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
			list[index] = element;
		}
		size++;
	}

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left, subtracting one from their indexes. Returns
	 * the element that was removed from the list.
	 * 
	 * @param index the index of the element to be removed
	 * @return E the element previously at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of range (less than 0
	 *                                   or greater than size)
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		E temp = list[index];
		for (int i = index; i < size; i++) {
			list[i] = list[i + 1];
		}
		size--;
		return temp;
	}

	/**
	 * Replaces the element at the specified position in this list with the given
	 * element.
	 * 
	 * @param index   index of the element to replace
	 * @param element element to be stored at the specified position
	 * @return E the element previously at the specified position
	 * @throws NullPointerException      if the specified element is null
	 * @throws IllegalArgumentException  if the specified element is a duplicate of
	 *                                   an existing element in this list
	 * @throws IndexOutOfBoundsException if the index is out of range (less than 0
	 *                                   or greater than size)
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Cannot set null element");
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		for (int i = 0; i < size; i++) {
			if (element.equals(list[i])) {
				throw new IllegalArgumentException("Duplicate element");
			}
		}
		E temp = list[index];
		list[index] = element;
		return temp;
	}

	/**
	 * Returns the element at the specified index in the list.
	 * 
	 * @param index index of element to return
	 * @return E element at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range (less than 0
	 *                                   or greater than size)
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		return list[index];
	}

	/**
	 * Returns the size of the list as represented by the number of elements in the
	 * list.
	 * 
	 * @return size the number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Helper method used to increase the capacity of the list two-fold if the
	 * number of elements in the list equals the capacity.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		int capacity = list.length * 2;
		Object[] buffer = new Object[capacity];
		for (int i = 0; i < size; i++) {
			buffer[i] = list[i];
		}
		list = (E[]) buffer;
	}

}
