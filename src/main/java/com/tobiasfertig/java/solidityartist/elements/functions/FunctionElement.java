package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.utils.Keywords;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FunctionElement implements SolidityElement
{
	private final boolean isFallback;
	private final String name;
	private final Set<ParameterElement> parameters;
	private final Keywords visibility;
	private final Set<Keywords> modifiers;
	private final Set<String> customModifiers;
	private final List<ParameterElement> returnParameters;
	private final boolean isAbstract;
	private final CodeElement code;

	private FunctionElement( Builder builder )
	{
		this.isFallback = builder.isFallback;
		this.name = builder.name;
		this.parameters = builder.parameters;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifiers;
		this.returnParameters = builder.returnParameters;
		this.isAbstract = builder.isAbstract;
		this.code = builder.code;
	}

	@Override
	public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public boolean isFallback( )
	{
		return isFallback;
	}

	public String getName( )
	{
		return name;
	}

	public Set<ParameterElement> getParameters( )
	{
		return parameters;
	}

	public Keywords getVisibility( )
	{
		return visibility;
	}

	public Set<Keywords> getModifiers( )
	{
		return modifiers;
	}

	public Set<String> getCustomModifiers( )
	{
		return customModifiers;
	}

	public List<ParameterElement> getReturnParameters( )
	{
		return returnParameters;
	}

	public boolean isAbstract( )
	{
		return isAbstract;
	}

	public CodeElement getCode( )
	{
		return code;
	}

	public static Builder externalBuilder( String name )
	{
		return new Builder( Keywords.EXTERNAL, false, name );
	}

	public static Builder internalBuilder( String name )
	{
		return new Builder( Keywords.INTERNAL, false, name );
	}

	public static Builder privateBuilder( String name )
	{
		return new Builder( Keywords.PRIVATE, false, name );
	}

	public static Builder publicBuilder( String name )
	{
		return new Builder( Keywords.PUBLIC, false, name );
	}

	public static Builder fallbackBuilder( )
	{
		return new Builder( Keywords.EXTERNAL, true, "" );
	}

	public static final class Builder
	{
		private final boolean isFallback;
		private final String name;
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );
		private final Keywords visibility;
		private final Set<Keywords> modifiers = new LinkedHashSet<>( );
		private final Set<String> customModifiers = new LinkedHashSet<>( );
		private final List<ParameterElement> returnParameters = new ArrayList<>( );
		private boolean isAbstract;
		private CodeElement code;

		private Builder( Keywords visibility, boolean isFallback, String name )
		{
			this.visibility = visibility;
			this.isFallback = isFallback;
			this.name = name;
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
			this.modifiers.add( Keywords.PURE );
			return this;
		}

		public Builder isView( )
		{
			this.modifiers.add( Keywords.VIEW );
			return this;
		}

		public Builder isPayable( )
		{
			this.modifiers.add( Keywords.PAYABLE );
			return this;
		}

		public Builder isAbstract( )
		{
			this.isAbstract = true;
			return this;
		}

		public Builder addCustomModifierElements( Iterable<ModifierElement> customModifiers )
		{
			for ( ModifierElement customModifier : customModifiers )
			{
				this.customModifiers.add( customModifier.getName( ) );
			}

			return this;
		}

		public Builder addCustomModifiers( Iterable<String> customModifiers )
		{
			for ( String customModifier : customModifiers )
			{
				this.customModifiers.add( customModifier );
			}

			return this;
		}

		public Builder addCustomModifier( ModifierElement customModifier )
		{
			this.customModifiers.add( customModifier.getName( ) );
			return this;
		}

		public Builder addCustomModifierName( String customModifier )
		{
			this.customModifiers.add( customModifier );
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

		public Builder addCode( CodeElement code )
		{
			this.code = code;
			return this;
		}

		public FunctionElement build( )
		{
			return new FunctionElement( this );
		}
	}
}
