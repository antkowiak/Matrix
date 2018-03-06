package com.ryanantkowiak.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Matrix class.
 * 
 * @author antko
 *
 * @param <E>
 */
public class Matrix<E>
{
	/**
	 * The number of rows in this matrix.
	 */
	protected int m_rows;

	/**
	 * The number of columns in this matrix.
	 */
	protected int m_columns;

	/**
	 * List of data elements in this matrix.
	 */
	protected List<E> m_data;

	/**
	 * Unused default constructor.
	 */
	@SuppressWarnings("unused")
	private Matrix()
	{
	}

	/**
	 * Construct an empty matrix with the specified dimensions.
	 * 
	 * @param rows
	 *            - row dimension
	 * @param cols
	 *            - column dimension
	 */
	public Matrix(int rows, int cols)
	{
		if (rows < 0 || cols < 0)
			throw new IndexOutOfBoundsException();

		m_rows = rows;
		m_columns = cols;

		initializeDataStructures();
	}

	/**
	 * Clear the values of the matrix.
	 */
	public void clear()
	{
		initializeDataStructures();
	}

	/**
	 * Convert a row and column offset into an array index.
	 * 
	 * @param row
	 *            - row value used in conversion
	 * @param col
	 *            - column value used in conversion
	 * @return - the converted index into the underlying data structure
	 */
	protected int convertRowAndColToIndex(int row, int col)
	{
		return ((row * m_columns) + col);
	}

	/**
	 * Equals comparison operator.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;

		if (o instanceof Matrix<?> == false)
			return false;

		if (o == this)
			return true;

		@SuppressWarnings("unchecked")
		Matrix<E> rhs = (Matrix<E>) o;

		if (m_rows != rhs.m_rows || m_columns != rhs.m_columns)
			return false;

		for (int row = 0 ; row < m_rows ; ++row)
		{
			for (int col = 0 ; col < m_columns ; ++col)
			{
				E rhsVal = rhs.get(row, col);
				E thisVal = get(row, col);

				if (thisVal == null && rhsVal == null)
					continue;

				if (thisVal == null || rhsVal == null)
					return false;

				if (!thisVal.equals(rhsVal))
					return false;
			}
		}

		return true;
	}

	/**
	 * Returns the element at the specified row and column.
	 * 
	 * @param row
	 *            - row value used to look up return value
	 * @param col
	 *            - column value used to look up return value
	 * @return - the data element at the given row and column
	 */
	public E get(int row, int col)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		return m_data.get(convertRowAndColToIndex(row, col));
	}

	/**
	 * Returns the column dimension.
	 * 
	 * @return - the number of columns
	 */
	public int getColumnDimension()
	{
		return m_columns;
	}

	/**
	 * Returns the row dimension.
	 * 
	 * @return - the number of rows
	 */
	public int getRowDimension()
	{
		return m_rows;
	}

	/**
	 * Initialize the underlying data structures.
	 */
	protected void initializeDataStructures()
	{
		int size = m_rows * m_columns;

		m_data = new ArrayList<E>(size);

		for (int i = 0 ; i < size ; ++i)
			m_data.add(null);
	}

	/**
	 * Set the data at the given row and column.
	 * 
	 * @param row
	 *            - the row at which to set the element value
	 * @param col
	 *            - the column at which to set the element value
	 * @param element
	 *            - the element value to set at the given row and column
	 */
	public void set(int row, int col, E element)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		m_data.set(convertRowAndColToIndex(row, col), element);
	}

	/**
	 * Returns the number of elements in this matrix.
	 * 
	 * @return - the number of elements in this matrix
	 */
	public int size()
	{
		return (m_rows * m_columns);
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
	public Matrix<E> subMatrix(int firstRowIndex, int lastRowIndex, int firstColIndex, int lastColIndex)
	{
		if (firstRowIndex < 0 || lastRowIndex > m_rows || firstRowIndex >= lastRowIndex)
			throw new IndexOutOfBoundsException();

		if (firstColIndex < 0 || lastColIndex > m_columns || firstColIndex >= lastColIndex)
			throw new IndexOutOfBoundsException();

		int newRows = lastRowIndex - firstRowIndex;
		int newCols = lastColIndex - firstColIndex;

		Matrix<E> m = new Matrix<E>(newRows, newCols);

		for (int row = firstRowIndex ; row < lastRowIndex ; ++row)
		{
			for (int col = firstColIndex ; col < lastColIndex ; ++col)
			{
				m.set(row - firstRowIndex, col - firstColIndex, get(row, col));
			}
		}

		return m;
	}

	/**
	 * Return string representation of this object.
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (int row = 0 ; row < m_rows ; ++row)
		{
			for (int col = 0 ; col < m_columns ; ++col)
			{
				sb.append(get(row, col));

				if (col != m_columns - 1)
					sb.append(" ");
			}

			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}
}
