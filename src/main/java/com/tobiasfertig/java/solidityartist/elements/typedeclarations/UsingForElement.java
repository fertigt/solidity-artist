package com.tobiasfertig.java.solidityartist.elements.typedeclarations;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class UsingForElement implements SolidityElement
{
	private final DataTypeElement extension;
	private final DataTypeElement source;

	private UsingForElement( Builder builder )
	{
		this.extension = builder.extension;
		this.source = builder.source;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public DataTypeElement getExtension( )
	{
		return extension;
	}

	public DataTypeElement getSource( )
	{
		return source;
	}

	public static Builder builder( DataTypeElement extension, DataTypeElement source )
	{
		return new Builder( extension, source );
	}

	public static Builder builder( String extension, String source )
	{
		return new Builder( new DataTypeElement( extension ), new DataTypeElement( source ) );
	}

	public static Builder builder( String extension, DataTypeElement source )
	{
		return new Builder( new DataTypeElement( extension ), source );
	}

	public static Builder builder( DataTypeElement extension, String source )
	{
		return new Builder( extension, new DataTypeElement( source ) );
	}

	public final static class Builder
	{
		private final DataTypeElement extension;
		private final DataTypeElement source;

		private Builder( DataTypeElement extension, DataTypeElement source )
		{
			this.extension = extension;
			this.source = source;
		}

		public UsingForElement build( )
		{
			return new UsingForElement( this );
		}
	}
}
