package com.tobiasfertig.java.solidityartist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ImportSpec implements Writable
{
	public final static String IMPORT_KEYWORD = "import";
	public final static String AS_KEYWORD = "as";
	public final static String FROM_KEYWORD = "from";
	public final static String ASTERISK = "*";

	private final Map<String, String> symbolMap;
	private final String fileName;
	private final String fileNameAlias;

	private ImportSpec( Builder builder )
	{
		this.symbolMap = builder.symbolMap;
		this.fileName = builder.fileName;
		this.fileNameAlias = builder.fileNameAlias;
	}

	private boolean hasSymbols( )
	{
		return !this.symbolMap.isEmpty( );
	}

	private boolean hasSymbolForAll( )
	{
		return this.symbolMap.containsKey( ASTERISK );
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( IMPORT_KEYWORD )
			  .space( );

		if ( hasSymbols( ) )
		{
			if ( hasSymbolForAll( ) )
			{
				writeAsteriskImport( writer );
			}
			else
			{
				writeSymbolMap( writer );
			}

			writer.write( FROM_KEYWORD )
				  .space( );
		}

		writer.writeInQuotes( this.fileName );

		if ( !this.fileNameAlias.isEmpty( ) )
		{
			writer.space( )
				  .write( AS_KEYWORD )
				  .space( )
				  .write( this.fileNameAlias );
		}

		writer.semicolonAndNewline( );
	}

	private void writeAsteriskImport( CodeWriter writer )
	{
		writer.write( ASTERISK )
			  .space( )
			  .write( AS_KEYWORD )
			  .space( )
			  .write( this.symbolMap.get( ASTERISK ) )
			  .space( );
	}

	private void writeSymbolMap( CodeWriter writer )
	{
		Set<Map.Entry<String,String>> symbolEntries = this.symbolMap.entrySet( );

		if ( symbolEntries.size( ) > 1 )
		{
			writer.write( "{" );
		}

		Iterator<Map.Entry<String,String>> iterator = symbolEntries.iterator( );
		Map.Entry<String, String> nextEntry = iterator.next( );
		while ( iterator.hasNext( ) )
		{
			if ( nextEntry.getValue( ).isEmpty( ) )
			{
				writer.write( nextEntry.getKey( ) )
					  .comma( )
					  .space( );
			}
			else
			{
				writer.write( nextEntry.getKey( ) )
					  .space( )
					  .write( AS_KEYWORD )
					  .space( )
					  .write( nextEntry.getValue( ) )
					  .comma( )
					  .space( );
			}

			nextEntry = iterator.next( );
		}

		if ( nextEntry.getValue( ).isEmpty( ) )
		{
			writer.write( nextEntry.getKey( ) );
		}
		else
		{
			writer.write( nextEntry.getKey( ) )
				  .space( )
				  .write( AS_KEYWORD )
				  .space( )
				  .write( nextEntry.getValue( ) );
		}

		if ( symbolEntries.size( ) > 1 )
		{
			writer.write( "}" );

		}

		writer.space( );
	}

	public static Builder builder( String fileName )
	{
		return new Builder( fileName );
	}

	public final static class Builder
	{
		private final Map<String, String> symbolMap = new HashMap<>( );
		private final String fileName;
		private String fileNameAlias;

		private Builder( String fileName )
		{
			this.fileName = fileName;
			this.fileNameAlias = "";
		}

		public Builder useSymbolForAll( String symbol )
		{
			symbolMap.put( ASTERISK, symbol );
			return this;
		}

		public Builder addSymbol( String symbol )
		{
			symbolMap.put( symbol, "" );
			return this;
		}

		public Builder addSymbolWithAlias( String symbol, String alias )
		{
			symbolMap.put( symbol, alias );
			return this;
		}

		public Builder addFileNameAlias( String fileNameAlias )
		{
			this.fileNameAlias = fileNameAlias;
			return this;
		}

		public ImportSpec build( )
		{
			return new ImportSpec( this );
		}
	}
}
