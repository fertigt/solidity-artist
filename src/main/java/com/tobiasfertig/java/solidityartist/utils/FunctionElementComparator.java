package com.tobiasfertig.java.solidityartist.utils;

import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;

import java.util.Comparator;

public class FunctionElementComparator implements Comparator<FunctionElement>
{
	@Override public int compare( FunctionElement o1, FunctionElement o2 )
	{
		int result;

		if ( o1.equals( o2 ) )
		{
			result = 0;
		}
		else if ( ( hasNoFunctionModifiiers( o1 ) || isFunctionPayable( o1 ) ) &&
			( isFunctionView( o2 ) || isFunctionPure( o2 ) ) )
		{
			result = -1;
		}
		else if ( isFunctionView( o1 ) && isFunctionPure( o2 ) )
		{
			result = -1;
		}
		else if ( isFunctionPure( o1 ) && isFunctionView( o2 ) )
		{
			result = 1;
		}
		else if ( ( isFunctionView( o1 ) || isFunctionPure( o1 ) ) &&
			( hasNoFunctionModifiiers( o2 ) || isFunctionPayable( o2 ) ) )
		{
			result = 1;
		}
		else
		{
			result = 1;
		}

		return result;
	}

	private boolean hasNoFunctionModifiiers( FunctionElement functionElement )
	{
		return functionElement.getModifiers( ).isEmpty( );
	}

	private boolean isFunctionPayable( FunctionElement functionElement )
	{
		boolean result = false;

		for ( Keyword.Modifier modifier : functionElement.getModifiers( ) )
		{
			if ( modifier.equals( Keyword.Modifier.PAYABLE ) )
			{
				result = true;
				break;
			}
		}

		return result;
	}

	private boolean isFunctionView( FunctionElement functionElement )
	{
		boolean result = false;

		for ( Keyword.Modifier modifier : functionElement.getModifiers( ) )
		{
			if ( modifier.equals( Keyword.Modifier.VIEW ) )
			{
				result = true;
				break;
			}
		}

		return result;
	}

	private boolean isFunctionPure( FunctionElement functionElement )
	{
		boolean result = false;

		for ( Keyword.Modifier modifier : functionElement.getModifiers( ) )
		{
			if ( modifier.equals( Keyword.Modifier.PURE ) )
			{
				result = true;
				break;
			}
		}

		return result;
	}
}
