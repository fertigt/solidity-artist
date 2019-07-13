package com.tobiasfertig.java.solidityartist.visitors;

public abstract class VisitorImpl implements Visitor
{
	private static final String INDENTATION = "    ";

	private int level = 0;

	StringBuilder sb;

	VisitorImpl( )
	{
		sb = new StringBuilder( );
	}

	void closeCurlyBraces( )
	{
		this.level--;
		newline( );
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

	private void getIndentationLevel( )
	{
		for(int i = 0; i < level; i++)
		{
			sb.append( INDENTATION );
		}
	}
}
