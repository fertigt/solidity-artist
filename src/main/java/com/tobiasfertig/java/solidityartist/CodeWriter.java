package com.tobiasfertig.java.solidityartist;

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
		System.out.print( "}" );
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
