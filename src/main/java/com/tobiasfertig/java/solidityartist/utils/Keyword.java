package com.tobiasfertig.java.solidityartist.utils;

public enum Keyword
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

	Keyword( String keyword )
	{
		this.keyword = keyword;
	}

	@Override public String toString( )
	{
		return this.keyword;
	}
}
