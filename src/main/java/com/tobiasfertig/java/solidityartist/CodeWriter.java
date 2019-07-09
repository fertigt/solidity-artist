package com.tobiasfertig.java.solidityartist;

public class CodeWriter
{
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
		System.out.print( "{" );
		return this;
	}

	public CodeWriter closeCurlyBraces( )
	{
		System.out.print( "}" );
		return this;
	}

	public CodeWriter emptyCurlyBraces( )
	{
		System.out.println( "{");
		System.out.print( "}" );
		return this;
	}

	public CodeWriter space( )
	{
		System.out.print( " " );
		return this;
	}

	public CodeWriter newline( )
	{
		System.out.println( "\n" );
		return this;
	}
}
