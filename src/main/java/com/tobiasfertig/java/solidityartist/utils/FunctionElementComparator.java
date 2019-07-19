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
		else if ( isExternal( o1 ) && isExternal( o2 ) )
		{
			result = compareByModifier( o1, o2 );
		}
		else if ( isPublic( o1 ) && isPublic( o2 ) )
		{
			result = compareByModifier( o1, o2 );
		}
		else if ( isInternal( o1 ) && isInternal( o2 ) )
		{
			result = compareByModifier( o1, o2 );
		}
		else if ( isPrivate( o1 ) && isPrivate( o2 ) )
		{
			result = compareByModifier( o1, o2 );
		}
		else if ( isExternal( o1 ) && ( isPublic( o2 ) || isInternal( o2 ) || isPrivate( o2 ) ) )
		{
			result = -1;
		}
		else if ( isPublic( o1 ) && ( isInternal( o2 ) || isPrivate( o2 ) ) )
		{
			result = -1;
		}
		else if ( isPublic( o1 ) && isExternal( o2 ) )
		{
			result = 1;
		}
		else if ( isInternal( o1 ) && isPrivate( o2 ) )
		{
			result = -1;
		}
		else if ( isInternal( o1 ) && ( isExternal( o2 ) || isPublic( o2 ) ) )
		{
			result = 1;
		}
		else
		{
			result = 1;
		}

		return result;
	}

	private boolean isExternal( FunctionElement functionElement )
	{
		return Keyword.Visibility.EXTERNAL.equals( functionElement.getVisibility( ) );
	}

	private boolean isPublic( FunctionElement functionElement )
	{
		return Keyword.Visibility.PUBLIC.equals( functionElement.getVisibility( ) );
	}

	private boolean isInternal( FunctionElement functionElement )
	{
		return Keyword.Visibility.INTERNAL.equals( functionElement.getVisibility( ) );
	}

	private boolean isPrivate( FunctionElement functionElement )
	{
		return Keyword.Visibility.PRIVATE.equals( functionElement.getVisibility( ) );
	}

	private int compareByModifier( FunctionElement o1, FunctionElement o2 )
	{
		int result;

		if ( ( hasNoModifiiers( o1 ) || isPayable( o1 ) ) &&
			( isView( o2 ) || isPure( o2 ) ) )
		{
			result = -1;
		}
		else if ( isView( o1 ) && isPure( o2 ) )
		{
			result = -1;
		}
		else if ( isPure( o1 ) && isView( o2 ) )
		{
			result = 1;
		}
		else if ( ( isView( o1 ) || isPure( o1 ) ) &&
			( hasNoModifiiers( o2 ) || isPayable( o2 ) ) )
		{
			result = 1;
		}
		else
		{
			result = 1;
		}

		return result;
	}

	private boolean hasNoModifiiers( FunctionElement functionElement )
	{
		return functionElement.getModifiers( ).isEmpty( );
	}

	private boolean isPayable( FunctionElement functionElement )
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

	private boolean isView( FunctionElement functionElement )
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

	private boolean isPure( FunctionElement functionElement )
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
