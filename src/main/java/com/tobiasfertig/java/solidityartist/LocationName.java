package com.tobiasfertig.java.solidityartist;

public class LocationName implements Writable
{
	public static final LocationName MEMORY = new LocationName( "memory" );
	public static final LocationName STORAGE = new LocationName( "storage" );

	private final String keyword;

	public LocationName( String keyword )
	{
		this.keyword = keyword;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( keyword );
	}
}
