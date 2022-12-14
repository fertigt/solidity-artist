package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.files.ContractElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConstructorElement implements SolidityElement
{
	private final NatSpecElement comment;
	private final Set<ParameterElement> parameters;
	private final Set<String> inheritanceModifiers;
	private final Keyword.Visibility visibility;
	private final Set<Keyword.Modifier> modifiers;
	private final Set<String> customModifiers;
	private final CodeElement code;

	private ConstructorElement( Builder builder )
	{
		this.comment = builder.comment;
		this.parameters = builder.parameters;
		this.inheritanceModifiers = builder.inheritanceModifiers;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifiers;
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

	public Set<ParameterElement> getParameters( )
	{
		return parameters;
	}

	public Set<String> getInheritanceModifiers( )
	{
		return inheritanceModifiers;
	}

	public Keyword.Visibility getVisibility( )
	{
		return visibility;
	}

	public Set<Keyword.Modifier> getModifiers( )
	{
		return modifiers;
	}

	public Set<String> getCustomModifiers( )
	{
		return customModifiers;
	}

	public CodeElement getCode( )
	{
		return code;
	}

	public static Builder publicBuilder( )
	{
		return new Builder( Keyword.Visibility.PUBLIC );
	}

	public static Builder internalBuilder( )
	{
		return new Builder( Keyword.Visibility.INTERNAL );
	}

	public static final class Builder
	{
		private NatSpecElement comment;
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );
		private final Set<String> inheritanceModifiers = new LinkedHashSet<>( );
		private final Keyword.Visibility visibility;
		private final Set<Keyword.Modifier> modifiers = new LinkedHashSet<>( );
		private final Set<String> customModifiers = new LinkedHashSet<>( );
		private CodeElement code;

		private Builder( Keyword.Visibility visibility )
		{
			this.visibility = visibility;
		}

		public Builder addNatSpec( NatSpecElement comment )
		{
			this.comment = comment;
			return this;
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

		public Builder addInheritanceModifier( String inheritanceModifier )
		{
			this.inheritanceModifiers.add( inheritanceModifier );
			return this;
		}

		public Builder isPayable( )
		{
			this.modifiers.add( Keyword.Modifier.PAYABLE );
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

		public Builder addCode( CodeElement code )
		{
			this.code = code;
			return this;
		}

		public ConstructorElement build( )
		{
			return new ConstructorElement( this );
		}
	}
}
