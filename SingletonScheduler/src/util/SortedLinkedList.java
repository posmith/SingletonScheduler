package util;

/**
 * The SortedLinkedList class implements the SortedList interface, using nodes
 * from the inner Node class to form a linked list of elements of type E. The
 * class contains methods for adding, getting, removing, and returning the index
 * of elements contained within the list. The class also contains methods for
 * returning the size, checking whether or not the list is empty, and checking
 * whether or not the list contains a specific element.
 * 
 * @author Patrick Smith
 *
 * @param <E> List element type
 */
public class SortedLinkedList<E extends Comparable<E>> implements SortedList<E> {
	/**
	 * The first node in the list, which contains the first element and a link to
	 * the next element in the list.
	 */
	private Node<E> head;
	/** The number of elements in the list */
	private int size;

	/**
	 * Constructor for the SortedLinkedList class which creates a new empty list.
	 */
	public SortedLinkedList() {
		head = new Node<E>(null, null);
		size = 0;
	}

	/**
	 * Method that returns the number of elements in a list as an integer.
	 * 
	 * @return int The size of the list as measured by the number of elements in the
	 *         list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true if this collection contains no elements.
	 * 
	 * @return true if this collection contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this collection contains the given element.
	 * 
	 * @return true if this collection contains the given element
	 */
	@Override
	public boolean contains(E e) {
		SimpleListIterator<E> cursor = iterator();
		while (cursor.hasNext()) {
			if (cursor.next().equals(e)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to add elements to a collection. Returns true if the element can be
	 * added, false otherwise.
	 * 
	 * @return boolean true if the element can be added to the list and false if the
	 *         element cannot be added
	 */
	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (this.contains(e)) {
			throw new IllegalArgumentException("Element already in list");
		}
		Node<E> current = head;
		Node<E> previous = null;
		if (current.value == null) {
			head = new Node<E>(e, head);
			size++;
			return true;
		}
		while (current.value != null && current.value.compareTo(e) < 0) {
			previous = current;
			current = current.next;
		}
		if (previous == null) {
			head = new Node<E>(e, head);
			size++;
			return true;
		} else {
			previous.next = new Node<E>(e, current);
		}
		size++;
		return true;
	}

	/**
	 * Method which returns an element at the given index from the collection
	 * without removing it.
	 * 
	 * @return E element from the collection
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index == size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		Node<E> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return (E) current.value;
	}

	/**
	 * Method which removes and returns an element at the given index from the
	 * collection
	 * 
	 * @return E element removed from the collection
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index == size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		Node<E> current = head;
		Node<E> previous = null;
		E temp = this.get(index);
		while (current != null && index > 0) {
			previous = current;
			current = current.next;
			index--;
		}
		if (current != null) {
			if (current == head) {
				head = head.next;
			} else {
				previous.next = current.next;
			}
		}
		size--;
		return temp;
	}

	/**
	 * Method which returns the index of a given element as an integer.
	 * 
	 * @return the index of a given element as an integer
	 */
	@Override
	public int indexOf(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (!this.contains(e)) {
			return -1;
		}
		Node<E> current = head;
		int count = 0;
		while (current.value != null && !current.value.equals(e)) {
			current = current.next;
			count++;
		}
		return count;
	}

	/**
	 * Returns a SimpleListIterator used for traveling through a list one item at a
	 * time.
	 * 
	 * @return SimpleListIterator used for traversing a list
	 */
	public SimpleListIterator<E> iterator() {
		return new Cursor();
	}

	/**
	 * Method used for generating hashCode for a SortedLinkedList.
	 * 
	 * @return int representing the hashCode for the object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + size;
		return result;
	}

	/**
	 * Method used to determine whether or not this SortedLinkedList equals
	 * another SortedLinkedList. Returns true if the two SortedLinkedList
	 * objects contain the same head Node (which subsequently means they contain all
	 * other Nodes).
	 * 
	 * @param obj Object to compare with this SortedLinkedList to determine
	 *            equivalence
	 * @return true if objects are equal and false if different
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SortedLinkedList<E> other = (SortedLinkedList<E>) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	/**
	 * Returns a String representing objects in the list with each object on a new
	 * line.
	 * 
	 * @return String representing the objects in the list
	 */
	@Override
	public String toString() {
		String s = "";
		SimpleListIterator<E> cursor = iterator();
		if (size() == 0) {
			return "";
		} else {
			while (cursor.hasNext()) {
				s += "-" + cursor.next() + "\n";
			}
		}
		return s.trim();
	}

	/**
	 * Node class used to create data structure for a linked list. Each node
	 * contains data representing an element and a link to the next node in the
	 * list.
	 * 
	 * @author Patrick Smith
	 * 
	 * @param <E> List element type
	 *
	 */
	private static class Node<E> {
		/** The data contained within the element of the list */
		protected E value;
		/** A link to the next Node in the list */
		protected Node<E> next;

		/**
		 * Constructor for the Node<E> class which uses a given element of type E and
		 * the next node in the list to construct a Node.
		 * 
		 * @param element Element of type E which represents the data contained in a
		 *                Node
		 * @param next    A link to the next Node in a list
		 */
		public Node(E element, Node<E> next) {
			this.value = element;
			this.next = next;
		}

		/**
		 * Method used for generating hashCode for a Node<E>.
		 * 
		 * @return int representing the hashCode for the object
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		/**
		 * Method used to determine whether or not this Node<E> equals another Node<E>.
		 * Returns true if the two Node<E> objects contain the same value field.
		 * 
		 * @param obj Object to compare with this Node<E> to determine equivalence
		 * @return true if objects have the same value and false otherwise
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node<E> other = (Node<E>) obj;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
	}

	/**
	 * A private inner class that provides a cursor for iterating forward through a
	 * list without changing the list.
	 * 
	 * @author Patrick Smith
	 *
	 */
	private class Cursor implements SimpleListIterator<E> {
		/** A node that represents the current place of the cursor in the list */
		private Node<E> current;

		/**
		 * The constructor method which creates a new cursor with which to iterate the
		 * list.
		 */
		public Cursor() {
			current = head;
		}

		/**
		 * Are there elements in the collection that have not been visited?
		 *
		 * @return true if yes, false if all elements have been visited
		 */
		@Override
		public boolean hasNext() {
			return current.next != null;
		}

		/**
		 * Answers the question: "What is the next element in the collection to be
		 * visited?" This method also advances the iterator to the following element, or
		 * throws NoSuchListElementException if the list has already been traversed.
		 * Exception message: "No element available with call to next."
		 *
		 * @return the next element in the collection to be visited
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchListElementException("No element available with call to next.");
			} else {
				E data = current.value;
				current = current.next;
				return (E) data;
			}
		}

	}

}
