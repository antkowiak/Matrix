package com.ryanantkowiak.matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Sparse Matrix of Doubles.
 * 
 * @author antko
 *
 */
public class DoubleSparseMatrix extends DoubleMatrix
{
	/**
	 * Underlying data structure for a sparse matrix of doubles.
	 */
	protected Map<Integer, Double> m_sparseData;

	/**
	 * Constructor
	 * 
	 * @param rows
	 *            - the number of rows in this matrix
	 * @param cols
	 *            - the number of columns in this matrix
	 */
	public DoubleSparseMatrix(int rows, int cols)
	{
		super(rows, cols);
	}

	/**
	 * Retrieve the double at the given row and column
	 */
	@Override
	public Double get(int row, int col)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		Double d = m_sparseData.get(convertRowAndColToIndex(row, col));

		if (null == d)
			return 0.0;

		return d;
	}

	/**
	 * Initialize the underlying data structures.
	 */
	@Override
	protected void initializeDataStructures()
	{
		m_sparseData = new HashMap<Integer, Double>();
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
	public void set(int row, int col, Double element)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		if (element == null || element.doubleValue() == 0.0)
			m_sparseData.remove(convertRowAndColToIndex(row, col));
		else
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
	@Override
	public DoubleSparseMatrix subMatrix(int firstRowIndex, int lastRowIndex, int firstColIndex, int lastColIndex)
	{
		if (firstRowIndex < 0 || lastRowIndex > m_rows || firstRowIndex >= lastRowIndex)
			throw new IndexOutOfBoundsException();

		if (firstColIndex < 0 || lastColIndex > m_columns || firstColIndex >= lastColIndex)
			throw new IndexOutOfBoundsException();

		int newRows = lastRowIndex - firstRowIndex;
		int newCols = lastColIndex - firstColIndex;

		DoubleSparseMatrix m = new DoubleSparseMatrix(newRows, newCols);

		for (int row = firstRowIndex ; row < lastRowIndex ; ++row)
		{
			for (int col = firstColIndex ; col < lastColIndex ; ++col)
			{
				Double d = get(row, col);
				if (d != null && d.doubleValue() != 0.0)
					m.set(row - firstRowIndex, col - firstColIndex, d);
			}
		}

		return m;
	}
}
