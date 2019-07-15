package com.tobiasfertig.java.solidityartist.elements.parameters;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class DataLocationParameterElement extends ParameterElement
{
	private final String dataLocation;

	private DataLocationParameterElement( Builder builder )
	{
		super( builder );
		this.dataLocation = builder.dataLocation;
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
		private String dataLocation;

		Builder( DataTypeElement dataType, String name, Keyword.DataLocation dataLocation )
		{
			this.dataType = dataType;
			this.name = name;
			this.dataLocation = dataLocation.toString( );
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public DataLocationParameterElement build( )
		{
			return new DataLocationParameterElement( this );
		}

		DataTypeElement getDataType( )
		{
			return this.dataType;
		}

		String getName( )
		{
			return this.name;
		}
	}
}
