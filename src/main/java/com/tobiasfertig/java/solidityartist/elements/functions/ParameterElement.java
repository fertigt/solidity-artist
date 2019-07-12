package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class ParameterElement implements SolidityElement
{
	private final DataTypeElement dataType;
	private final String name;

	private ParameterElement( Builder builder )
	{
		this( builder.dataType, builder.name );
	}

	ParameterElement( MemoryParameterElement.Builder builder )
	{
		this( builder.getDataType( ), builder.getName( ) );
	}

	ParameterElement( StorageParameterElement.Builder builder )
	{
		this( builder.getDataType( ), builder.getName( ) );
	}

	private ParameterElement( DataTypeElement dataType, String name )
	{
		this.dataType = dataType;
		this.name = name;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public DataTypeElement getDataType( )
	{
		return dataType;
	}

	public String getName( )
	{
		return name;
	}

	public static Builder builder( DataTypeElement dataType )
	{
		return new Builder( dataType );
	}

	public static final class Builder
	{
		private final DataTypeElement dataType;
		private String name;

		private Builder( DataTypeElement dataType )
		{
			this.dataType = dataType;
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public MemoryParameterElement.Builder inMemory( )
		{
			return new MemoryParameterElement.Builder( this.dataType, this.name );
		}

		public StorageParameterElement.Builder inStorage( )
		{
			return new StorageParameterElement.Builder( this.dataType, this.name );
		}

		public ParameterElement build( )
		{
			return new ParameterElement( this );
		}
	}
}
