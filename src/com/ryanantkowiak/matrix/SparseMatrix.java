package com.ryanantkowiak.matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Sparse Matrix class.
 * 
 * @author antko
 *
 * @param <E>
 */
public class SparseMatrix<E> extends Matrix<E>
{
	/**
	 * Underlying data representation of a SparseMatrix
	 */
	protected Map<Integer, E> m_sparseData;

	/**
	 * Constructor
	 * 
	 * @param rows
	 *            - the number of rows in this sparse matrix
	 * @param cols
	 *            - the number of columns in this sparse matrix
	 */
	public SparseMatrix(int rows, int cols)
	{
		super(rows, cols);
	}

	/**
	 * Returns the element at the specified row and column.
	 * 
	 * @param row
	 *            - the row at which to return the element
	 * @param col
	 *            - the column at which to return the element
	 * @return - the element at the given row and column
	 */
	public E get(int row, int col)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		return m_sparseData.get(convertRowAndColToIndex(row, col));
	}

	@Override
	/**
	 * Initialize the underlying data structures.
	 */
	protected void initializeDataStructures()
	{
		m_sparseData = new HashMap<Integer, E>();
	}

	/**
	 * Set the data at the given row and column.
	 * 
	 * @param row
	 *            - the row at which to set the element
	 * @param col
	 *            - the column at which to set the element
	 * @param element
	 *            - the element to set at the given row and column
	 */
	@Override
	public void set(int row, int col, E element)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		m_sparseData.put(convertRowAndColToIndex(row, col), element);
	}

	/**
	 * Return a sub-matrix with the given indices.
	 * 
	 * @param firstRowIndex
	 * @param lastRowIndex
	 * @param firstColIndex
	 * @param lastColIndex
	 * @return
	 */
	public SparseMatrix<E> subMatrix(int firstRowIndex, int lastRowIndex, int firstColIndex, int lastColIndex)
	{
		if (firstRowIndex < 0 || lastRowIndex > m_rows || firstRowIndex >= lastRowIndex)
			throw new IndexOutOfBoundsException();

		if (firstColIndex < 0 || lastColIndex > m_columns || firstColIndex >= lastColIndex)
			throw new IndexOutOfBoundsException();

		int newRows = lastRowIndex - firstRowIndex;
		int newCols = lastColIndex - firstColIndex;

		SparseMatrix<E> m = new SparseMatrix<E>(newRows, newCols);

		for (int row = firstRowIndex ; row < lastRowIndex ; ++row)
		{
			for (int col = firstColIndex ; col < lastColIndex ; ++col)
			{
				E obj = get(row, col);

				if (null != obj)
					m.set(row - firstRowIndex, col - firstColIndex, obj);
			}
		}

		return m;
	}

}
