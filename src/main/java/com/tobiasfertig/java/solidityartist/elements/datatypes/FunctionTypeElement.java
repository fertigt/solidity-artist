package com.tobiasfertig.java.solidityartist.elements.datatypes;

import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionTypeElement extends DataTypeElement
{
	private final Set<ParameterElement> parameters;
	private final Keyword visibility;
	private final Set<Keyword> modifiers;
	private final Set<ParameterElement> returnParameters;

	private FunctionTypeElement( Builder builder )
	{
		super( Keyword.FUNCTION.toString( ) );
		this.parameters = builder.parameters;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.returnParameters = builder.returnParameters;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public Set<ParameterElement> getParameters( )
	{
		return parameters;
	}

	public Keyword getVisibility( )
	{
		return visibility;
	}

	public Set<Keyword> getModifiers( )
	{
		return modifiers;
	}

	public Set<ParameterElement> getReturnParameters( )
	{
		return returnParameters;
	}

	public static Builder internalBuilder( )
	{
		return new Builder( Keyword.INTERNAL );
	}

	public static Builder publicBuilder( )
	{
		return new Builder( Keyword.PUBLIC );
	}

	public static final class Builder
	{
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );
		private final Keyword visibility;
		private final Set<Keyword> modifiers = new LinkedHashSet<>( );
		private final Set<ParameterElement> returnParameters = new LinkedHashSet<>( );

		private Builder( Keyword visibility )
		{
			this.visibility = visibility;
		}

		public Builder addParameters( Iterable<ParameterElement> parameterSpecs )
		{
			for ( ParameterElement parameterSpec : parameterSpecs )
			{
				this.parameters.add( parameterSpec );
			}

			return this;
		}

		public Builder addParameter( ParameterElement parameterSpec )
		{
			this.parameters.add( parameterSpec );
			return this;
		}

		public Builder isPure( )
		{
			this.modifiers.add( Keyword.PURE );
			return this;
		}

		public Builder isView( )
		{
			this.modifiers.add( Keyword.VIEW );
			return this;
		}

		public Builder isPayable( )
		{
			this.modifiers.add( Keyword.PAYABLE );
			return this;
		}

		public Builder addReturnParameters( Iterable<ParameterElement> returnParameters )
		{
			for ( ParameterElement returnParameter : returnParameters )
			{
				this.returnParameters.add( returnParameter );
			}

			return this;
		}

		public Builder addReturnParameter( ParameterElement returnParameter )
		{
			this.returnParameters.add( returnParameter );
			return this;
		}

		public FunctionTypeElement build( )
		{
			return new FunctionTypeElement( this );
		}
	}
}
