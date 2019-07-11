package com.tobiasfertig.java.solidityartist;

public class LocationName extends AttributeName
{
	public static final LocationName MEMORY = new LocationName( "memory" );
	public static final LocationName STORAGE = new LocationName( "storage" );

	public LocationName( String keyword )
	{
		super( keyword );
	}
}
