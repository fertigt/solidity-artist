package com.tobiasfertig.java.solidityartist.elements.typedeclarations;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class EnumElement implements SolidityElement
{
	private final String name;
	private final Set<String> values;

	private EnumElement( Builder builder )
	{
		this.name = builder.name;
		this.values = builder.values;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getName( )
	{
		return name;
	}

	public Set<String> getValues( )
	{
		return values;
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private final String name;
		private final Set<String> values = new LinkedHashSet<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addValue( String value )
		{
			this.values.add( value );
			return this;
		}

		public EnumElement build( )
		{
			return new EnumElement( this );
		}
	}
}
