package com.cprg311.a3.utilities;

import com.cprg311.a3.adt.BSTreeNodeADT;

import java.io.Serializable;

/**
 * Represents a binary search tree node
 * @param <E>
 */
public class BSTreeNode<E extends Comparable<? super E>> implements BSTreeNodeADT<E>, Serializable {
	private E element;
	private BSTreeNodeADT<E> left, right;

	public BSTreeNode(E element, BSTreeNode<E> left, BSTreeNode<E> right) {
		this.element = element;
		this.left = left;
		this.right = right;
	}

	@Override
	public BSTreeNodeADT<E> getLeft() {
		return this.left;
	}

	@Override
	public BSTreeNodeADT<E> getRight() {
		return this.right;
	}

	@Override
	public E getData() {
		return this.element;
	}

	public void setLeft(BSTreeNodeADT<E> left) {
		this.left = left;
	}

	public void setRight(BSTreeNodeADT<E> right) {
		this.right = right;
	}
}
