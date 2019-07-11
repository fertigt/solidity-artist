package com.tobiasfertig.java.solidityartist;

public class AttributeName implements Writable
{
	private final String keyword;

	public AttributeName( String keyword )
	{
		this.keyword = keyword;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( keyword );
	}
}
