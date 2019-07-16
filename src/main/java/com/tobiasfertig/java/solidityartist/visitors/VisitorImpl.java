package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;

import java.util.Collection;
import java.util.Iterator;

public abstract class VisitorImpl implements Visitor
{
	private static final String INDENTATION = "    ";

	private int level = 0;

	StringBuilder sb;

	VisitorImpl( )
	{
		sb = new StringBuilder( );
	}

	void closeBraces( )
	{
		sb.append( ")" );
	}

	void closeCurlyBraces( )
	{
		this.level--;
		indent( );
		sb.append( "}" );
	}

	void indent( )
	{
		getIndentationLevel( );
	}

	void newline( )
	{
		sb.append( "\n" );
	}

	void openBraces( )
	{
		sb.append( "(" );
	}

	void openCurlyBraces( )
	{
		sb.append( "{\n" );
		this.level++;
	}

	void semicolon( )
	{
		sb.append( ";" );
	}

	void space( )
	{
		sb.append( " " );
	}

	void appendCollectionIndented( Iterable<? extends Object> set, String delimiter )
	{
		Iterator<? extends Object> iterator = set.iterator( );
		if ( iterator.hasNext( ) )
		{
			Object nextValue = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				indent( );
				sb.append( nextValue.toString( ) );
				sb.append( delimiter );

				nextValue = iterator.next( );
			}

			indent( );
			sb.append( nextValue );
		}
	}

	void appendCollectionInline( Iterable<? extends Object> set, String delimiter )
	{
		Iterator<? extends Object> iterator = set.iterator( );
		if ( iterator.hasNext( ) )
		{
			Object nextValue = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				sb.append( nextValue.toString( ) );
				sb.append( delimiter );

				nextValue = iterator.next( );
			}

			sb.append( nextValue );
		}
	}

	void appendCollectionOfSolidityElements(
		Iterable<? extends SolidityElement> elements,
		String delimiter
	)
	{
		Iterator<? extends SolidityElement> iterator = elements.iterator( );
		if ( iterator.hasNext( ) )
		{
			SolidityElement nextElement = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				nextElement.accept( this );
				sb.append( delimiter );

				nextElement = iterator.next( );
			}

			nextElement.accept( this );
		}
	}

	void appendCollectionOfSolidityElementsIndented(
		Iterable<? extends SolidityElement> elements,
		String delimiter
	)
	{
		Iterator<? extends SolidityElement> iterator = elements.iterator( );
		if ( iterator.hasNext( ) )
		{
			SolidityElement nextElement = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				indent( );
				nextElement.accept( this );
				sb.append( delimiter );

				nextElement = iterator.next( );
			}

			indent( );
			nextElement.accept( this );
		}
	}

	boolean checkAndAppend(
		Collection<? extends SolidityElement> elements,
		boolean isNotFirstCollection,
		String delimiter
	)
	{
		boolean result = isNotFirstCollection;
		if ( !elements.isEmpty( ) )
		{
			if ( isNotFirstCollection )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( elements, delimiter );
			result = true;
		}

		return result;
	}

	boolean checkAndAppend( SolidityElement element, boolean isNotFirstElement )
	{
		boolean result = isNotFirstElement;
		if ( element != null )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			element.accept( this );
			result = true;
		}

		return result;
	}

	void checkAndAppendComment( NatSpecElement element )
	{
		if ( element != null )
		{
			element.accept( this );
			newline( );
		}
	}

	private void getIndentationLevel( )
	{
		for(int i = 0; i < level; i++)
		{
			sb.append( INDENTATION );
		}
	}
}
