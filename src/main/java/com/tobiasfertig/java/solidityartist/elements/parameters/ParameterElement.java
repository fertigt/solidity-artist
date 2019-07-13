package com.tobiasfertig.java.solidityartist.elements.parameters;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.utils.Keywords;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class ParameterElement implements SolidityElement
{
	private final DataTypeElement dataType;
	private final String name;

	private ParameterElement( Builder builder )
	{
		this( builder.dataType, builder.name );
	}

	ParameterElement( DataLocationParameterElement.Builder builder )
	{
		this( builder.getDataType( ), builder.getName( ) );
	}

	ParameterElement( EventParameterElement.Builder builder )
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

		public DataLocationParameterElement.Builder inMemory( )
		{
			return new DataLocationParameterElement.Builder( this.dataType, this.name, Keywords.MEMORY );
		}

		public DataLocationParameterElement.Builder inStorage( )
		{
			return new DataLocationParameterElement.Builder( this.dataType, this.name, Keywords.STORAGE );
		}

		public EventParameterElement.Builder isAnonymousEventParameter( )
		{
			return new EventParameterElement.Builder( this.dataType, this.name, Keywords.ANONYMOUS );
		}

		public EventParameterElement.Builder isIndexedEventParameter( )
		{
			return new EventParameterElement.Builder( this.dataType, this.name, Keywords.INDEXED );
		}

		public ParameterElement build( )
		{
			return new ParameterElement( this );
		}
	}
}
