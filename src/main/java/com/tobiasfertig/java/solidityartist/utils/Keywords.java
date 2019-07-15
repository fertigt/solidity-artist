package com.tobiasfertig.java.solidityartist.utils;

import com.tobiasfertig.java.solidityartist.CodeWriter;
import com.tobiasfertig.java.solidityartist.Writable;

public enum Keywords implements Writable
{
	ANONYMOUS( "anonymous" ),
	AS( "as" ),
	CONTRACT( "contract" ),
	CONSTANT( "constant" ),
	CONSTRUCTOR( "constructor" ),
	ENUM( "enum" ),
	EVENT( "event" ),
	EXPERIMENTAL( "experimental" ),
	EXTERNAL( "external" ),
	FOR( "for" ),
	FROM( "from" ),
	FUNCTION( "function" ),
	IMPORT( "import" ),
	INDEXED( "indexed" ),
	INTERFACE( "interface" ),
	INTERNAL( "internal" ),
	IS( "is" ),
	LIBRARY( "library" ),
	MAPPING( "mapping" ),
	MEMORY( "memory" ),
	MODIFIER( "modifier" ),
	PAYABLE( "payable" ),
	PRAGMA( "pragma" ),
	PRIVATE( "private" ),
	PUBLIC( "public" ),
	PURE( "pure" ),
	RETURNS( "returns" ),
	SOLIDITY( "solidity" ),
	STORAGE( "storage" ),
	STRUCT( "struct" ),
	USING( "using" ),
	VIEW( "view" );

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
