package com.tobiasfertig.java.solidityartist;

public class VisibilityName
{
	public static final VisibilityName EXTERNAL = new VisibilityName( "external" );
	public static final VisibilityName PUBLIC = new VisibilityName( "public" );
	public static final VisibilityName INTERNAL = new VisibilityName( "internal" );
	public static final VisibilityName PRIVATE = new VisibilityName( "private" );


	private final String keyword;

	public VisibilityName( String keyword )
	{
		this.keyword = keyword;
	}

	public CodeWriter write( CodeWriter writer )
	{
		writer.write( keyword );
		return writer;
	}
}
