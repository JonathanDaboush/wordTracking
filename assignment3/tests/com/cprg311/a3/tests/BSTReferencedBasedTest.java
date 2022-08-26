package com.cprg311.a3.tests;

import com.cprg311.a3.adt.IteratorADT;
import com.cprg311.a3.exceptions.TreeException;
import com.cprg311.a3.utilities.BSTReferencedBased;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTReferencedBasedTest {
	private BSTReferencedBased tree;
	private final int TREE_HEIGHT = 3;

	@BeforeEach
	void setUp() {
		tree = new BSTReferencedBased();
		tree.add(8);
		tree.add(3);
		tree.add(1);
		tree.add(6);
		tree.add(4);
		tree.add(7);
		tree.add(10);
		tree.add(14);
		tree.add(13);
	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void getRoot_EmptyTree_ThrowsException() {
		this.tree.clear();
		assertThrows(TreeException.class, () -> this.tree.getRoot());
	}

	@Test
	void getRoot_NonEmptyTree_ReturnsRoot() {
		try {
			assertEquals(this.tree.getRoot().getData(), 8);
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void getHeight_EmptyTree_ReturnsZero() {
		this.tree.clear();
		assertEquals(this.tree.getHeight(), -1);
	}

	@Test
	void getHeight_NonEmptyTree_ReturnsHeight() {
		assertEquals(this.tree.getHeight(), TREE_HEIGHT);
	}

	@Test
	void size_EmptyTree_ReturnsZero() {
		this.tree.clear();
		assertEquals(this.tree.size(), 0);
	}

	@Test
	void size_NonEmptyTree_ReturnsSize() {
		assertEquals(this.tree.size(), 9);
	}

	@Test
	void isEmpty_EmptyTree_True() {
		this.tree.clear();
		assertTrue(this.tree.isEmpty());
	}

	@Test
	void isEmpty_NonEmptyTree_False() {
		assertFalse(this.tree.isEmpty());
	}

	@Test
	void clear_NonEmptyTree_IsClear() {
		this.tree.clear();
		assertTrue(this.tree.isEmpty());
	}

	@Test
	void contains_NullValue_ThrowsException() {
		assertThrows(TreeException.class, () -> this.tree.contains(null));
	}

	@Test
	void contains_EmptyTree_IsFalse() {
		this.tree.clear();
		try {
			assertFalse(this.tree.contains(11));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void contains_NonEmptyTreeInvalidValue_IsFalse() {
		try {
			assertFalse(this.tree.contains(11));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void contains_NonEmptyTreeValidValue_IsTrue() {
		try {
			assertTrue(this.tree.contains(14));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void search_NullValue_ThrowsException() {
		assertThrows(TreeException.class, () -> this.tree.search(null));
	}

	@Test
	void search_EmptyTree_IsNull() {
		this.tree.clear();
		try {
			assertNull(this.tree.search(11));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void search_NonEmptyTreeInvalidValue_IsNull() {
		try {
			assertNull(this.tree.search(11));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void search_NonEmptyTreeValidValue_IsTrue() {
		try {
			assertEquals(this.tree.search(14).getData(), 14);
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void add_EmptyTree_NewRootNode() {
		this.tree.clear();
		this.tree.add(99);
		try {
			assertTrue(this.tree.contains(99));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void add_NullEntry_ThrowsException() {
		assertThrows(NullPointerException.class, () -> this.tree.add(null));
	}

	@Test
	void add_NonEmptyTree_NewNodeAdded() {
		this.tree.add(99);
		try {
			assertTrue(this.tree.contains(99));
		} catch (TreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	void inorderIterator() {
		int[] expected = {1, 3, 4, 6, 7, 8, 10, 13, 14};
		int[] actual = new int[9];
		int index = 0;

		IteratorADT iterator = this.tree.inorderIterator();
		while(iterator.hasNext()) {
			actual[index++] = (int) iterator.next();
		}
		assertArrayEquals(actual, expected);
	}

	@Test
	void preorderIterator() {
		int[] expected = {8, 3, 1, 6, 4, 7, 10, 14, 13};
		int[] actual = new int[9];
		int index = 0;

		IteratorADT iterator = this.tree.preorderIterator();
		while(iterator.hasNext()) {
			actual[index++] = (int) iterator.next();
		}
		assertArrayEquals(actual, expected);
	}

	@Test
	void postorderIterator() {
		int[] expected = {1, 4, 7, 6, 3, 13, 14, 10, 8};
		int[] actual = new int[9];
		int index = 0;

		IteratorADT iterator = this.tree.postorderIterator();
		while(iterator.hasNext()) {
			actual[index++] = (int) iterator.next();
		}
		assertArrayEquals(actual, expected);
	}
}