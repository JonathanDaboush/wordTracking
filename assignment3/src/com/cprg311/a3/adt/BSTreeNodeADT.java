package com.cprg311.a3.adt;


/**
 * The Abstract Data Type for a node in a binary search tree
 */
public interface BSTreeNodeADT<E extends Comparable<? super E>> {

	public BSTreeNodeADT<E> getLeft();

	public BSTreeNodeADT<E> getRight();

	public E getData();

	public void setLeft(BSTreeNodeADT<E> left);

	public void setRight(BSTreeNodeADT<E> right);
}