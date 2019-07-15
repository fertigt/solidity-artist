package com.tobiasfertig.java.solidityartist.elements.parameters;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class EventParameterElement extends ParameterElement
{
	private final String attribute;

	private EventParameterElement( Builder builder )
	{
		super( builder );
		this.attribute = builder.attribute;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getAttribute( )
	{
		return attribute;
	}

	public static final class Builder
	{
		private final DataTypeElement dataType;
		private String name;
		private String attribute;

		Builder( DataTypeElement dataType, String name, Keyword.EventParameterModifier attribute )
		{
			this.dataType = dataType;
			this.name = name;
			this.attribute = attribute.toString( );
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public EventParameterElement build( )
		{
			return new EventParameterElement( this );
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
