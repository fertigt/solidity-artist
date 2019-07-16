package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class ModifierElement implements SolidityElement
{
	private final NatSpecElement comment;
	private final String name;
	private final Set<ParameterElement> parameters;
	private final CodeElement code;

	private ModifierElement( Builder builder )
	{
		this.comment = builder.comment;
		this.name = builder.name;
		this.parameters = builder.parameters;
		this.code = builder.code;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public NatSpecElement getComment( )
	{
		return comment;
	}

	public String getName( )
	{
		return name;
	}

	public Set<ParameterElement> getParameters( )
	{
		return parameters;
	}

	public CodeElement getCode( )
	{
		return code;
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private NatSpecElement comment;
		private final String name;
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );
		private CodeElement code;

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addNatSpec( NatSpecElement comment )
		{
			this.comment = comment;
			return this;
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

		public Builder addCode( CodeElement code )
		{
			this.code = code;
			return this;
		}

		public Builder addCodeWithoutUnderscoreStatement( CodeElement code )
		{
			this.code = CodeElement.builder( )
								   .addCodeElement( code )
								   .addStatement( "_" )
								   .build( );

			return this;
		}

		public ModifierElement build( )
		{
			if ( this.code == null )
			{
				this.code = CodeElement.builder( )
									   .addStatement( "_" )
									   .build( );
			}

			return new ModifierElement( this );
		}
	}
}
