package com.tobiasfertig.java.solidityartist.elements.files;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.utils.Keywords;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class PragmaElement implements SolidityElement
{
	private final Keywords pragmaType;
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

	public Keywords getPragmaType( )
	{
		return pragmaType;
	}

	public String getContent( )
	{
		return content;
	}

	public static ExperimentalBuilder experimentalBuilder( )
	{
		return new ExperimentalBuilder( Keywords.EXPERIMENTAL );
	}

	public static VersionBuilder versionBuilder( )
	{
		return new VersionBuilder( Keywords.SOLIDITY );
	}

	public static final class ExperimentalBuilder
	{
		private final Keywords pragmaType;
		private String content;

		private ExperimentalBuilder( Keywords pragmaType )
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
		private final Keywords pragmaType;
		private String content;

		private VersionBuilder( Keywords pragmaType )
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
