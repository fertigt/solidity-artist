package com.tobiasfertig.java.solidityartist.elements.files;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class PragmaElement implements SolidityElement
{
	private final Keyword pragmaType;
	private final String content;

	private PragmaElement( ExperimentalBuilder builder )
	{
		this.pragmaType = builder.pragmaType;
		this.content = builder.content;
	}

	private PragmaElement( VersionBuilder builder )
	{
		this.pragmaType = builder.pragmaType;
		this.content = builder.content;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public Keyword getPragmaType( )
	{
		return pragmaType;
	}

	public String getContent( )
	{
		return content;
	}

	public static ExperimentalBuilder experimentalBuilder( )
	{
		return new ExperimentalBuilder( Keyword.EXPERIMENTAL );
	}

	public static VersionBuilder versionBuilder( )
	{
		return new VersionBuilder( Keyword.SOLIDITY );
	}

	public static final class ExperimentalBuilder
	{
		private final Keyword pragmaType;
		private String content;

		private ExperimentalBuilder( Keyword pragmaType )
		{
			this.pragmaType = pragmaType;
		}

		public ExperimentalBuilder abiEncoderV2( )
		{
			this.content = "ABIEncoderV2";
			return this;
		}

		public ExperimentalBuilder smtChecker( )
		{
			this.content = "SMTChecker";
			return this;
		}

		public PragmaElement build( )
		{
			return new PragmaElement( this );
		}
	}

	public static final class VersionBuilder
	{
		private final Keyword pragmaType;
		private String content;

		private VersionBuilder( Keyword pragmaType )
		{
			this.pragmaType = pragmaType;
		}

		public VersionBuilder addVersion( String version )
		{
			this.content = version;
			return this;
		}

		public PragmaElement build( )
		{
			return new PragmaElement( this );
		}
	}
}
