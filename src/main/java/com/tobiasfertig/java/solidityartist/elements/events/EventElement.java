package com.tobiasfertig.java.solidityartist.elements.events;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class EventElement implements SolidityElement
{
	private final String name;
	private final Set<ParameterElement> parameters;

	private EventElement( Builder builder )
	{
		this.name = builder.name;
		this.parameters = builder.parameters;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getName( )
	{
		return name;
	}

	public Set<ParameterElement> getParameters( )
	{
		return parameters;
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private final String name;
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addParameters( Iterable<ParameterElement> parameters )
		{
			for ( ParameterElement parameter : parameters )
			{
				this.parameters.add( parameter );
			}

			return this;
		}

		public Builder addParameter( ParameterElement parameter )
		{
			this.parameters.add( parameter );
			return this;
		}

		public EventElement build( )
		{
			return new EventElement( this );
		}
	}
}
