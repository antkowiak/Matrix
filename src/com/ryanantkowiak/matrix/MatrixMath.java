package com.ryanantkowiak.matrix;

/**
 * Math functions on matrices of doubles.
 * 
 * @author antko
 *
 */
public class MatrixMath
{
	/**
	 * Calculate the determinant of the given matrix.
	 * 
	 * @param m
	 *            - the matrix to calculate the determinant of
	 * @return - the determinant of the matrix
	 * @throws InvalidMatrixDimensionException
	 */
	public static double determinant(DoubleMatrix m) throws InvalidMatrixDimensionException
	{
		if (null == m)
			throw new NullPointerException();

		if (m.getRowDimension() != m.getColumnDimension())
			throw new InvalidMatrixDimensionException();

		return determinant(m, m.getRowDimension());
	}

	/**
	 * Calculate the determinant of the given sparse matrix.
	 * 
	 * @param m
	 *            - the sparse matrix to calculate the determinant of
	 * @return - the determinant of the matrix
	 * @throws InvalidMatrixDimensionException
	 */
	public static double determinantSparse(DoubleSparseMatrix m) throws InvalidMatrixDimensionException
	{
		if (null == m)
			throw new NullPointerException();

		if (m.getRowDimension() != m.getColumnDimension())
			throw new InvalidMatrixDimensionException();

		return determinantSparse(m, m.getRowDimension());
	}

	/**
	 * Helper function to help calculate a determinant of a matrix.
	 * 
	 * @param a
	 * @param n
	 * @return
	 */
	private static double determinant(DoubleMatrix a, int n)
	{
		double det = 0;
		int sign = 1;
		int p = 0;
		int q = 0;

		if (n == 1)
			det = a.get(0, 0);
		else
		{
			DoubleMatrix b = new DoubleMatrix(n - 1, n - 1);

			for (int x = 0 ; x < n ; x++)
			{
				p = 0;
				q = 0;

				for (int i = 1 ; i < n ; ++i)
				{
					for (int j = 0 ; j < n ; ++j)
					{
						if (j != x)
						{
							b.set(p, q++, a.get(i, j));

							if (q % (n - 1) == 0)
							{
								++p;
								q = 0;
							}
						}
					}
				}

				det = det + a.get(0, x) * determinant(b, n - 1) * sign;

				sign = -sign;
			}
		}

		return det;
	}

	/**
	 * Helper function to help calculate the determinant of a sparse matrix.
	 * 
	 * @param a
	 * @param n
	 * @return
	 */
	private static double determinantSparse(DoubleSparseMatrix a, int n)
	{
		double det = 0;
		int sign = 1;
		int p = 0;
		int q = 0;

		if (n == 1)
			det = a.get(0, 0);
		else
		{
			DoubleSparseMatrix b = new DoubleSparseMatrix(n - 1, n - 1);

			for (int x = 0 ; x < n ; x++)
			{
				p = 0;
				q = 0;

				for (int i = 1 ; i < n ; ++i)
				{
					for (int j = 0 ; j < n ; ++j)
					{
						if (j != x)
						{
							b.set(p, q++, a.get(i, j));

							if (q % (n - 1) == 0)
							{
								++p;
								q = 0;
							}
						}
					}
				}

				det = det + a.get(0, x) * determinant(b, n - 1) * sign;

				sign = -sign;
			}
		}

		return det;
	}

	/**
	 * Compare the dimensions of two given matrices.
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static <E> boolean dimensionsEqual(Matrix<E> m1, Matrix<E> m2)
	{
		if (m1 == null || m2 == null)
			return false;

		if (m1.getRowDimension() != m2.getRowDimension())
			return false;

		if (m1.getColumnDimension() != m2.getColumnDimension())
			return false;

		return true;
	}

	/**
	 * Add two matrices and return the result in a new matrix.
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleMatrix add(DoubleMatrix m1, DoubleMatrix m2) throws InvalidMatrixDimensionException
	{
		if (m1 == null || m2 == null)
			throw new NullPointerException();

		if (!dimensionsEqual(m1, m2))
			throw new InvalidMatrixDimensionException();

		int rows = m1.getRowDimension();
		int cols = m1.getColumnDimension();

		DoubleMatrix m = new DoubleMatrix(rows, cols);

		for (int r = 0 ; r < rows ; ++r)
			for (int c = 0 ; c < cols ; ++c)
				m.set(r, c, m1.get(r, c) + m2.get(r, c));

		return m;
	}

	/**
	 * Add two matrices and return the result in a new sparse matrix.
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleSparseMatrix addSparse(DoubleMatrix m1, DoubleMatrix m2) throws InvalidMatrixDimensionException
	{
		if (m1 == null || m2 == null)
			throw new NullPointerException();

		if (!dimensionsEqual(m1, m2))
			throw new InvalidMatrixDimensionException();

		int rows = m1.getRowDimension();
		int cols = m1.getColumnDimension();

		DoubleSparseMatrix m = new DoubleSparseMatrix(rows, cols);

		for (int r = 0 ; r < rows ; ++r)
			for (int c = 0 ; c < cols ; ++c)
			{
				double val = m1.get(r, c) + m2.get(r, c);

				if (val != 0.0)
					m.set(r, c, val);
			}

		return m;
	}

	/**
	 * Construct and return an identity matrix with dimensions n by n.
	 * 
	 * @param n
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleMatrix identity(int n) throws InvalidMatrixDimensionException
	{
		if (n < 1)
			throw new InvalidMatrixDimensionException();

		DoubleMatrix m = new DoubleMatrix(n, n);

		for (int i = 0 ; i < n ; ++i)
			m.set(i, i, 1.0);

		return m;
	}

	/**
	 * Construct and return a sparse identity matrix with dimensions n by n.
	 * 
	 * @param n
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleSparseMatrix identitySparse(int n) throws InvalidMatrixDimensionException
	{
		if (n < 1)
			throw new InvalidMatrixDimensionException();

		DoubleSparseMatrix m = new DoubleSparseMatrix(n, n);

		for (int i = 0 ; i < n ; ++i)
			m.set(i, i, 1.0);

		return m;
	}

	/**
	 * Multiply two given matrices and return the result in a new matrix.
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleMatrix multiply(DoubleMatrix m1, DoubleMatrix m2) throws InvalidMatrixDimensionException
	{
		if (m1 == null || m2 == null)
			throw new NullPointerException();

		if (m1.getColumnDimension() != m2.getRowDimension())
			throw new InvalidMatrixDimensionException();

		DoubleMatrix m = new DoubleMatrix(m1.getRowDimension(), m2.getColumnDimension());

		for (int r = 0 ; r < m.getRowDimension() ; ++r)
		{
			for (int c = 0 ; c < m.getColumnDimension() ; ++c)
			{
				double val = 0.0;

				for (int z = 0 ; z < m1.getColumnDimension() ; ++z)
					val += m1.get(r, z) * m2.get(z, c);

				m.set(r, c, val);
			}
		}

		return m;
	}

	/**
	 * Multiply two given sparse matrices and return the result in a new sparse
	 * matrix.
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleSparseMatrix multiplySparse(DoubleMatrix m1, DoubleMatrix m2)
			throws InvalidMatrixDimensionException
	{
		if (m1 == null || m2 == null)
			throw new NullPointerException();

		if (m1.getColumnDimension() != m2.getRowDimension())
			throw new InvalidMatrixDimensionException();

		DoubleSparseMatrix m = new DoubleSparseMatrix(m1.getRowDimension(), m2.getColumnDimension());

		for (int r = 0 ; r < m.getRowDimension() ; ++r)
		{
			for (int c = 0 ; c < m.getColumnDimension() ; ++c)
			{
				double val = 0.0;

				for (int z = 0 ; z < m1.getColumnDimension() ; ++z)
					val += m1.get(r, z) * m2.get(z, c);

				if (val != 0.0)
					m.set(r, c, val);
			}
		}

		return m;
	}

	/**
	 * Multiply the given matrix by a scalar and return the result in a new matrix.
	 * 
	 * @param m
	 * @param scalar
	 * @return
	 */
	public static DoubleMatrix scalarMultiply(DoubleMatrix m, double scalar)
	{
		if (m == null)
			throw new NullPointerException();

		DoubleMatrix v = new DoubleMatrix(m.getRowDimension(), m.getColumnDimension());

		for (int r = 0 ; r < m.getRowDimension() ; ++r)
			for (int c = 0 ; c < m.getColumnDimension() ; ++c)
				v.set(r, c, scalar * m.get(r, c));

		return v;
	}

	/**
	 * Multiply the given sparse matrix by a scalar and return the result in a new
	 * matrix.
	 * 
	 * @param m
	 * @param scalar
	 * @return
	 */
	public static DoubleMatrix scalarMultiplySparse(DoubleMatrix m, double scalar)
	{
		if (m == null)
			throw new NullPointerException();

		DoubleSparseMatrix v = new DoubleSparseMatrix(m.getRowDimension(), m.getColumnDimension());

		for (int r = 0 ; r < m.getRowDimension() ; ++r)
			for (int c = 0 ; c < m.getColumnDimension() ; ++c)
			{
				double val = scalar * m.get(r, c);
				if (val != 0.0)
					v.set(r, c, val);
			}

		return v;
	}

	/**
	 * Subtract matrix m2 from matrix m1 and return the result in a new matrix.
	 * (e.g. returns m1-m2).
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleMatrix subtract(DoubleMatrix m1, DoubleMatrix m2) throws InvalidMatrixDimensionException
	{
		if (m1 == null || m2 == null)
			throw new NullPointerException();

		if (!dimensionsEqual(m1, m2))
			throw new InvalidMatrixDimensionException();

		int rows = m1.getRowDimension();
		int cols = m1.getColumnDimension();

		DoubleMatrix m = new DoubleMatrix(rows, cols);

		for (int r = 0 ; r < rows ; ++r)
			for (int c = 0 ; c < cols ; ++c)
				m.set(r, c, m1.get(r, c) - m2.get(r, c));

		return m;
	}

	/**
	 * Subtract sparse matrix m2 from sparse matrix m1 and return the result in a
	 * new sparse matrix. (e.g. returns m1-m2).
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws InvalidMatrixDimensionException
	 */
	public static DoubleSparseMatrix subtractSparse(DoubleMatrix m1, DoubleMatrix m2)
			throws InvalidMatrixDimensionException
	{
		if (m1 == null || m2 == null)
			throw new NullPointerException();

		if (!dimensionsEqual(m1, m2))
			throw new InvalidMatrixDimensionException();

		int rows = m1.getRowDimension();
		int cols = m1.getColumnDimension();

		DoubleSparseMatrix m = new DoubleSparseMatrix(rows, cols);

		for (int r = 0 ; r < rows ; ++r)
			for (int c = 0 ; c < cols ; ++c)
			{
				double val = m1.get(r, c) - m2.get(r, c);

				if (val != 0.0)
					m.set(r, c, val);
			}

		return m;
	}

}
