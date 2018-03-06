package com.ryanantkowiak.matrix;

public class InvalidMatrixDimensionException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidMatrixDimensionException()
	{
	}

	public InvalidMatrixDimensionException(String arg0)
	{
		super(arg0);
	}

	public InvalidMatrixDimensionException(Throwable arg0)
	{
		super(arg0);
	}

	public InvalidMatrixDimensionException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public InvalidMatrixDimensionException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
	}

}
