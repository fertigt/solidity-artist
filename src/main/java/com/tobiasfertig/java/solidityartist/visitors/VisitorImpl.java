package com.tobiasfertig.java.solidityartist.visitors;

public abstract class VisitorImpl implements Visitor
{
	private static final String INDENTATION = "    ";

	StringBuilder sb;

	VisitorImpl( )
	{
		sb = new StringBuilder( );
	}

	void indent( )
	{
		sb.append( INDENTATION );
	}

	void newline( )
	{
		sb.append( "\n" );
	}

	void semicolon( )
	{
		sb.append( ";" );
	}

	void space( )
	{
		sb.append( " " );
	}
}
