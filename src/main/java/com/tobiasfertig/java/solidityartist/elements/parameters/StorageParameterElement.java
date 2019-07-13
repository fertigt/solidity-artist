package com.tobiasfertig.java.solidityartist.elements.parameters;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.utils.Keywords;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class StorageParameterElement extends ParameterElement
{
	private String dataLocation;

	private StorageParameterElement( StorageParameterElement.Builder builder, String dataLocation )
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

		public StorageParameterElement build( )
		{
			return new StorageParameterElement( this, Keywords.STORAGE.toString( ) );
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
