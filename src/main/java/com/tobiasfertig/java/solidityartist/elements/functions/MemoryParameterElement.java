package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.utils.Keywords;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class MemoryParameterElement extends ParameterElement
{
	private String dataLocation;

	private MemoryParameterElement( Builder builder, String dataLocation )
	{
		super( builder );
		this.dataLocation = dataLocation;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getDataLocation( )
	{
		return dataLocation;
	}

	public static final class Builder
	{
		private final DataTypeElement dataType;
		private String name;

		Builder( DataTypeElement dataType, String name )
		{
			this.dataType = dataType;
			this.name = name;
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public MemoryParameterElement build( )
		{
			return new MemoryParameterElement( this, Keywords.MEMORY.toString( ) );
		}

		public DataTypeElement getDataType( )
		{
			return dataType;
		}

		public String getName( )
		{
			return name;
		}
	}
}
