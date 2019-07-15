package com.tobiasfertig.java.solidityartist.utils;

public enum Keyword
{
	AS( "as" ),
	CONTRACT( "contract" ),
	CONSTANT( "constant" ),
	CONSTRUCTOR( "constructor" ),
	ENUM( "enum" ),
	EVENT( "event" ),
	EXPERIMENTAL( "experimental" ),
	FOR( "for" ),
	FROM( "from" ),
	FUNCTION( "function" ),
	IMPORT( "import" ),
	INTERFACE( "interface" ),
	IS( "is" ),
	LIBRARY( "library" ),
	MAPPING( "mapping" ),
	MODIFIER( "modifier" ),
	PRAGMA( "pragma" ),
	RETURNS( "returns" ),
	SOLIDITY( "solidity" ),
	STRUCT( "struct" ),
	USING( "using" );

	public enum DataLocation
	{
		MEMORY( "memory" ),
		STORAGE( "storage" );

		private final String keyword;

		DataLocation( String keyword )
		{
			this.keyword = keyword;
		}

		@Override public String toString( )
		{
			return this.keyword;
		}
	}

	public enum EventParameterModifier
	{
		ANONYMOUS( "anonymous" ),
		INDEXED( "indexed" );

		private final String keyword;

		EventParameterModifier( String keyword )
		{
			this.keyword = keyword;
		}

		@Override public String toString( )
		{
			return this.keyword;
		}
	}

	public enum Modifier
	{
		PAYABLE( "payable" ),
		PURE( "pure" ),
		VIEW( "view" );

		private final String keyword;

		Modifier( String keyword )
		{
			this.keyword = keyword;
		}

		@Override public String toString( )
		{
			return this.keyword;
		}
	}

	public enum Visibility
	{
		EXTERNAL( "external" ),
		PUBLIC( "public" ),
		INTERNAL( "internal" ),
		PRIVATE("private");

		private final String keyword;

		Visibility( String keyword )
		{
			this.keyword = keyword;
		}

		@Override public String toString( )
		{
			return this.keyword;
		}
	}

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
