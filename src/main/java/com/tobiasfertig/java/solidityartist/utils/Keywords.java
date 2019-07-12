package com.tobiasfertig.java.solidityartist.utils;

import com.tobiasfertig.java.solidityartist.CodeWriter;
import com.tobiasfertig.java.solidityartist.Writable;

public enum Keywords implements Writable
{
	AS( "as" ),
	CONTRACT( "contract" ),
	CONSTRUCTOR( "constructor" ),
	ENUM( "enum" ),
	EVENT( "event" ),
	FOR( "for" ),
	FROM( "from" ),
	FUNCTION( "function" ),
	IMPORT( "import" ),
	INTERFACE( "interface" ),
	LIBRARY( "library" ),
	MAPPING( "mapping" ),
	MODIFIER( "modifier" ),
	RETURNS( "returns" ),
	STRUCT( "struct" ),
	USING( "using" );

	private final String keyword;

	Keywords( String keyword )
	{
		this.keyword = keyword;
	}

	@Override public String toString( )
	{
		return this.keyword;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( keyword );
	}
}
