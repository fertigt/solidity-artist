package com.tobiasfertig.java.solidityartist.elements.datatypes;

import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionTypeElement extends DataTypeElement
{
	private final Set<ParameterElement> parameters;
	private final Keyword.Visibility visibility;
	private final Set<Keyword.Modifier> modifiers;
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

	public Keyword.Visibility getVisibility( )
	{
		return visibility;
	}

	public Set<Keyword.Modifier> getModifiers( )
	{
		return modifiers;
	}

	public Set<ParameterElement> getReturnParameters( )
	{
		return returnParameters;
	}

	public static Builder externalBuilder( )
	{
		return new Builder( Keyword.Visibility.EXTERNAL );
	}

	public static Builder internalBuilder( )
	{
		return new Builder( Keyword.Visibility.INTERNAL );
	}

	public static final class Builder
	{
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );
		private final Keyword.Visibility visibility;
		private final Set<Keyword.Modifier> modifiers = new LinkedHashSet<>( );
		private final Set<ParameterElement> returnParameters = new LinkedHashSet<>( );

		private Builder( Keyword.Visibility visibility )
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
			this.modifiers.add( Keyword.Modifier.PURE );
			return this;
		}

		public Builder isView( )
		{
			this.modifiers.add( Keyword.Modifier.VIEW );
			return this;
		}

		public Builder isPayable( )
		{
			this.modifiers.add( Keyword.Modifier.PAYABLE );
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
