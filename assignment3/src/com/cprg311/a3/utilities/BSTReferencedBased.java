package com.cprg311.a3.utilities;

import com.cprg311.a3.adt.BSTreeNodeADT;
import com.cprg311.a3.adt.BSTreeADT;
import com.cprg311.a3.adt.IteratorADT;
import com.cprg311.a3.exceptions.TreeException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Stack;

/**
 * A referenced based binary search tree
 * @param <E>
 */
public class BSTReferencedBased<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable {
	private BSTreeNodeADT<E> root;
	private int size = 0;

	public BSTReferencedBased() {
		this.root = null;
	}

	public BSTReferencedBased(E element) {
		this.root = new BSTreeNode<E>(element, null, null);
	}

	@Override
	public BSTreeNodeADT<E> getRoot() throws TreeException {
		if (this.root == null) throw new TreeException("Root is null.");
		return this.root;
	}

	@Override
	public int getHeight() {
		return treeHeight(this.root);
	}

	private int treeHeight(BSTreeNodeADT<E> node) {
		if (node == null) {
			return -1;
		}

		int leftHeight = treeHeight(node.getLeft());
		int rightHeight = treeHeight(node.getRight());

		return ((leftHeight > rightHeight) ? leftHeight + 1 : rightHeight + 1);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	@Override
	public void clear() {
		this.size = 0;
		this.root = null;
	}

	@Override
	public boolean contains(E entry) throws TreeException {
		return (search(entry) != null);
	}

	@Override
	public BSTreeNodeADT<E> search(E entry) throws TreeException {
		if (entry == null)
			throw new TreeException();
		return (searchNode(this.root, entry));
	}

	private BSTreeNodeADT<E> searchNode(BSTreeNodeADT<E> node, E entry) {
		if (node == null || node.getData().compareTo(entry) == 0) {
			return node;
		}
		if (node.getData().compareTo(entry) > 0) {
			return searchNode(node.getLeft(), entry);
		}
		return searchNode(node.getRight(), entry);
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		if (newEntry == null)
			throw new NullPointerException();
		this.root = addNode(this.root, newEntry);
		size++;
		return false;
	}

	private BSTreeNodeADT<E> addNode(BSTreeNodeADT<E> node, E newEntry) {
		if (node == null) {
			return new BSTreeNode(newEntry, null, null);
		}
		if (newEntry.compareTo(node.getData()) < 0) {
			node.setLeft(addNode(node.getLeft(), newEntry));
		} else if (newEntry.compareTo(node.getData()) > 0) {
			node.setRight(addNode(node.getRight(), newEntry));
		}
		return node;
	}


	@Override
	public IteratorADT<E> inorderIterator() {
		return new IteratorADT<E>() {
			Stack<BSTreeNodeADT<E>> stack = null;

			{
				BSTreeNodeADT<E> rootNode = root;
				stack = new Stack<>();
				while (rootNode != null) {
					stack.push(rootNode);
					rootNode = rootNode.getLeft();
				}
			}

			@Override
			public boolean hasNext() {
				return !stack.isEmpty();
			}

			@Override
			public E next() {
				BSTreeNodeADT<E> node = stack.pop();
				E result = node.getData();
				if (node.getRight() != null) {
					node = node.getRight();
					while (node != null) {
						stack.push(node);
						node = node.getLeft();
					}
				}
				return result;
			}
		};
	}

	@Override
	public IteratorADT<E> preorderIterator() {
		return new IteratorADT<E>() {
			LinkedList<E> result = null;
			Stack<BSTreeNodeADT<E>> stack = null;

			{
				BSTreeNodeADT<E> rootNode = root;
				result = new LinkedList<>();
				stack = new Stack<>();
				stack.push(rootNode);
				while (!stack.empty()) {
					BSTreeNodeADT<E> node = stack.pop();
					if (node != null) {
						result.add(node.getData());
					}
					if (node.getRight() != null) {
						stack.push(node.getRight());
					}
					if (node.getLeft() != null) {
						stack.push(node.getLeft());
					}
				}
			}

			@Override
			public boolean hasNext() {
				return (!result.isEmpty());
			}

			@Override
			public E next() {
				return (result.remove());
			}
		};

	}

	@Override
	public IteratorADT<E> postorderIterator() {
		return new IteratorADT<E>() {
			LinkedList<E> result = null;
			Stack<BSTreeNodeADT<E>> stack = null;

			{
				BSTreeNodeADT<E> rootNode = root;
				result = new LinkedList<>();
				stack = new Stack<>();
				stack.push(rootNode);
				BSTreeNodeADT<E> prev = null;
				while (!stack.empty()) {
					BSTreeNodeADT<E> node = stack.peek();
					if (prev == null || prev.getLeft() == node || prev.getRight() == node) {
						if (node.getLeft() != null) {
							stack.push(node.getLeft());
						} else if (node.getRight() != null) {
							stack.push(node.getRight());
						} else {
							stack.pop();
							result.add(node.getData());
						}
					} else if (node.getLeft() == prev) {
						if (node.getRight() != null) {
							stack.push(node.getRight());
						} else {
							stack.pop();
							result.add(node.getData());
						}
					} else if (node.getRight() == prev) {
						stack.pop();
						result.add(node.getData());
					}
					prev = node;
				}
			}

			@Override
			public boolean hasNext() {
				return (!result.isEmpty());
			}

			@Override
			public E next() {
				return (result.remove());
			}
		};
	}
}
