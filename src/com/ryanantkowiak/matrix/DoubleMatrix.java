package com.ryanantkowiak.matrix;

/**
 * Matrix of Doubles class.
 * 
 * @author antko
 *
 */
public class DoubleMatrix extends Matrix<Double>
{
	/**
	 * Construct an empty matrix of Doubles with the specified dimensions.
	 * 
	 * @param rows
	 *            - the number of rows in the matrix
	 * @param cols
	 *            - the number of columns in the matrix
	 */
	public DoubleMatrix(int rows, int cols)
	{
		super(rows, cols);
	}

	/**
	 * Return a matrix, constructed from a given string and delimiter
	 * 
	 * @param rows
	 *            - the number of rows in the matrix
	 * @param cols
	 *            - the number of columns in the matrix
	 * @param str
	 *            - the string containing the matrix data
	 * @param delim
	 *            - delimiters for the matrix data
	 * @return - a matrix containing the provided data
	 */
	public static DoubleMatrix fromString(int rows, int cols, String str, String delim)
	{
		DoubleMatrix m = new DoubleMatrix(rows, cols);

		String[] arr = str.split(delim);

		int i = 0;

		for (int r = 0 ; r < rows ; ++r)
		{
			for (int c = 0 ; c < cols ; ++c)
			{
				m.set(r, c, Double.parseDouble(arr[i]));
				++i;

				if (i >= arr.length)
					return m;
			}
		}

		return m;
	}

	/**
	 * Returns the element at the specified row and column.
	 * 
	 * @param row
	 *            - the row at which to get the element
	 * @param col
	 *            - the column at which to get the element
	 * @return - the element at the specified row and column
	 */
	@Override
	public Double get(int row, int col)
	{
		if (row < 0 || row >= m_rows || col < 0 || col >= m_columns)
			throw new IndexOutOfBoundsException();

		Double d = m_data.get(convertRowAndColToIndex(row, col));

		if (null == d)
			return 0.0;

		return d;
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
	public DoubleMatrix subMatrix(int firstRowIndex, int lastRowIndex, int firstColIndex, int lastColIndex)
	{
		if (firstRowIndex < 0 || lastRowIndex > m_rows || firstRowIndex >= lastRowIndex)
			throw new IndexOutOfBoundsException();

		if (firstColIndex < 0 || lastColIndex > m_columns || firstColIndex >= lastColIndex)
			throw new IndexOutOfBoundsException();

		int newRows = lastRowIndex - firstRowIndex;
		int newCols = lastColIndex - firstColIndex;

		DoubleMatrix m = new DoubleMatrix(newRows, newCols);

		for (int row = firstRowIndex ; row < lastRowIndex ; ++row)
		{
			for (int col = firstColIndex ; col < lastColIndex ; ++col)
			{
				m.set(row - firstRowIndex, col - firstColIndex, get(row, col));
			}
		}

		return m;
	}

}
