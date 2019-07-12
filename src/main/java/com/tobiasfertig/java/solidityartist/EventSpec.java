package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.LinkedHashSet;
import java.util.Set;

public class EventSpec implements Writable
{
	private final String name;
	private final Set<ParameterSpec> parameters;

	private EventSpec( Builder builder )
	{
		this.name = builder.name;
		this.parameters = builder.parameters;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( Keywords.EVENT )
			  .space( )
			  .write( this.name )
			  .openBraces( )
			  .writeParameters( parameters )
			  .closeBraces( )
			  .semicolonAndNewline( );
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public final static class Builder
	{
		private final String name;
		private final Set<ParameterSpec> parameters = new LinkedHashSet<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addParameters( Iterable<ParameterSpec> parameterSpecs )
		{
			for ( ParameterSpec parameterSpec : parameterSpecs )
			{
				this.parameters.add( parameterSpec );
			}

			return this;
		}

		public Builder addParameter( ParameterSpec parameterSpec )
		{
			this.parameters.add( parameterSpec );
			return this;
		}

		public EventSpec build( )
		{
			return new EventSpec( this );
		}
	}
}
