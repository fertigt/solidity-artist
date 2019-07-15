package com.tobiasfertig.java.solidityartist.elements.files;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashMap;
import java.util.Map;

public class ImportElement implements SolidityElement
{
	public static final String ASTERISK = "*";

	private final Map<String, String> symbolMap;
	private final String fileName;
	private final String fileNameAlias;

	private ImportElement( Builder builder )
	{
		this.symbolMap = builder.symbolMap;
		this.fileName = builder.fileName;
		this.fileNameAlias = builder.fileNameAlias;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public Map<String, String> getSymbolMap( )
	{
		return symbolMap;
	}

	public String getFileName( )
	{
		return fileName;
	}

	public String getFileNameAlias( )
	{
		return fileNameAlias;
	}

	public static Builder builder( String fileName )
	{
		return new Builder( fileName );
	}

	public final static class Builder
	{
		private final Map<String, String> symbolMap = new LinkedHashMap<>( );
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

		public ImportElement build( )
		{
			return new ImportElement( this );
		}
	}
}
