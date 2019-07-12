package com.tobiasfertig.java.solidityartist.visitors;

public abstract class VisitorImpl implements Visitor
{
	protected StringBuilder sb;

	public VisitorImpl( )
	{
		sb = new StringBuilder( );
	}

	protected void space( )
	{
		sb.append( " " );
	}
}
