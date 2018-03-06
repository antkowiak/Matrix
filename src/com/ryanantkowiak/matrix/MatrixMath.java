package com.ryanantkowiak.matrix;

public class MatrixMath
{
	public static double determinant(DoubleMatrix m) throws InvalidMatrixDimensionException
	{
		if (null == m)
			throw new NullPointerException();

		if (m.getRowDimension() != m.getColumnDimension())
			throw new InvalidMatrixDimensionException();

		return determinant(m, m.getRowDimension());
	}

	public static double determinantSparse(DoubleSparseMatrix m) throws InvalidMatrixDimensionException
	{
		if (null == m)
			throw new NullPointerException();

		if (m.getRowDimension() != m.getColumnDimension())
			throw new InvalidMatrixDimensionException();

		return determinantSparse(m, m.getRowDimension());
	}

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

	public static DoubleMatrix identity(int n) throws InvalidMatrixDimensionException
	{
		if (n < 1)
			throw new InvalidMatrixDimensionException();

		DoubleMatrix m = new DoubleMatrix(n, n);

		for (int i = 0 ; i < n ; ++i)
			m.set(i, i, 1.0);

		return m;
	}

	public static DoubleSparseMatrix identitySparse(int n) throws InvalidMatrixDimensionException
	{
		if (n < 1)
			throw new InvalidMatrixDimensionException();

		DoubleSparseMatrix m = new DoubleSparseMatrix(n, n);

		for (int i = 0 ; i < n ; ++i)
			m.set(i, i, 1.0);

		return m;
	}

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
