package com.tobiasfertig.java.solidityartist;

import java.util.Iterator;
import java.util.Set;

public class CodeWriter
{
	private static String INDENTATION = "    ";
	private int level = 0;


	public CodeWriter( )
	{
	}

	public CodeWriter write( String s )
	{
		System.out.print( s );
		return this;
	}

	public CodeWriter write( Writable writable )
	{
		writable.write( this );
		return this;
	}

	public CodeWriter writeAndIndent( String s )
	{
		System.out.print( getIndentationLevel( ) + s );
		return this;
	}

	public CodeWriter writeAndIndent( Writable writable )
	{
		this.write( getIndentationLevel( ) );
		writable.write( this );
		return this;
	}

	public CodeWriter writeInQuotes( String s )
	{
		System.out.print( "\"" + s + "\"");
		return this;
	}

	public CodeWriter writeStrings( Iterable<String> strings )
	{
		Iterator<String> iterator = strings.iterator( );

		if ( iterator.hasNext( ) )
		{
			String s = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				this.write( s )
					.comma( )
					.space( );
				s = iterator.next( );
			}

			this.write( s );
		}

		return this;
	}

	public CodeWriter write( Iterable<? extends Writable> writables )
	{
		Iterator<? extends Writable> iterator = writables.iterator( );
		Writable writable = iterator.next( );

		while ( iterator.hasNext( ) )
		{
			this.write( writable )
				.comma( )
				.space( );

			writable = iterator.next( );
		}

		this.write( writable );

		return this;
	}

	public CodeWriter writeParameters( Set<ParameterSpec> parameters )
	{
		Iterator<ParameterSpec> iterator = parameters.iterator( );

		if ( iterator.hasNext( ) )
		{
			ParameterSpec nextParameter = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				this.write( nextParameter )
					.comma( )
					.space( );
				nextParameter = iterator.next( );
			}

			this.write( nextParameter );
		}

		return this;
	}

	public CodeWriter openBraces( )
	{
		System.out.print( "(" );
		return this;
	}

	public CodeWriter closeBraces( )
	{
		System.out.print( ")" );
		return this;
	}

	public CodeWriter emptyBraces( )
	{
		System.out.print( "()" );
		return this;
	}

	public CodeWriter openCurlyBraces( )
	{
		System.out.println( "{" );
		this.level++;
		return this;
	}

	public CodeWriter closeCurlyBraces( )
	{
		System.out.println( );
		this.level--;
		System.out.print( getIndentationLevel( ) + "}" );
		return this;
	}

	public CodeWriter emptyCurlyBraces( )
	{
		System.out.println( "{");
		System.out.print( getIndentationLevel( ) + "}" );
		return this;
	}

	public CodeWriter comma( )
	{
		System.out.print( "," );
		return this;
	}

	public CodeWriter semicolon( )
	{
		System.out.print( ";" );
		return this;
	}

	public CodeWriter semicolonAndNewline( )
	{
		System.out.println( ";" );
		return this;
	}

	public CodeWriter space( )
	{
		System.out.print( " " );
		return this;
	}

	public CodeWriter newline( )
	{
		System.out.println( );
		return this;
	}

	public CodeWriter indent( )
	{
		System.out.print( getIndentationLevel( ) );
		return this;
	}

	private String getIndentationLevel( )
	{
		StringBuilder stringBuilder = new StringBuilder( );

		for(int i = 0; i < level; i++)
		{
			stringBuilder.append( INDENTATION );
		}

		return stringBuilder.toString( );
	}
}
