package com.tobiasfertig.java.solidityartist;

public class EventParameterAttributeName extends AttributeName
{
	public final static EventParameterAttributeName INDEXED = new EventParameterAttributeName( "indexed" );
	public final static EventParameterAttributeName ANONYMOUS = new EventParameterAttributeName( "anonymous" );

	public EventParameterAttributeName( String keyword )
	{
		super( keyword );
	}
}
