package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionSpec implements Writable
{
	private final String name;
	private final Set<ParameterSpec> parameters;
	private final VisibilityName visibility;
	private final Set<ModifierName> modifiers;
	private final Set<ModifierName> customModifiers;
	private final Set<ParameterSpec> returnParameters;
	private final CodeBlock code;

	private FunctionSpec( Builder builder )
	{
		this.name = ( builder.name == null ) ? "" : builder.name;
		this.parameters = builder.parameterSpecs;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifiers;
		this.returnParameters = builder.returnParameters;
		this.code = builder.code;
	}

	public Set<ParameterSpec> getParameters( )
	{
		return parameters;
	}

	public VisibilityName getVisibility( )
	{
		return visibility;
	}

	public Set<ModifierName> getModifiers( )
	{
		return modifiers;
	}

	public Set<ModifierName> getCustomModifiers( )
	{
		return customModifiers;
	}

	public Set<ParameterSpec> getReturnParameters( )
	{
		return returnParameters;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( Keywords.FUNCTION );
		if ( !this.name.isEmpty( ) )
		{
			writer.space( )
				  .write( this.name );
		}

		writer.openBraces( )
			  .writeParameters( this.parameters )
			  .closeBraces( )
			  .space( )
			  .write( visibility );

		for ( ModifierName modifier : modifiers )
		{
			writer.space( )
				  .write( modifier );
		}

		for ( ModifierName customModifier : customModifiers )
		{
			writer.space( )
				  .write( customModifier );
		}

		if(!returnParameters.isEmpty())
		{
			writer.space( )
				  .write( Keywords.RETURNS )
				  .openBraces( )
				  .writeParameters( this.returnParameters )
				  .closeBraces( );
		}

		if ( code == null )
		{
			writer.semicolon( );
		}
		else
		{
			if ( code.isEmpty( ) )
			{
				writer.space()
					  .emptyCurlyBraces( );
			}
			else
			{
				writer.space( )
					  .openCurlyBraces( )
					  .write( code )
					  .closeCurlyBraces( );
			}
		}


	}

	public static Builder builder( VisibilityName visibility )
	{
		return new Builder( visibility );
	}

	public static final class Builder
	{
		private String name;
		private final Set<ParameterSpec> parameterSpecs = new LinkedHashSet<>( );
		private final VisibilityName visibility;
		private final Set<ModifierName> modifiers = new LinkedHashSet<>( );
		private final Set<ModifierName> customModifiers = new LinkedHashSet<>( );
		private final Set<ParameterSpec> returnParameters = new LinkedHashSet<>( );
		private CodeBlock code = CodeBlock.emptyBlock( );

		private Builder( VisibilityName visibility )
		{
			this.visibility = visibility;
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public Builder addParameters( Iterable<ParameterSpec> parameterSpecs )
		{
			for ( ParameterSpec parameterSpec : parameterSpecs )
			{
				this.parameterSpecs.add( parameterSpec );
			}

			return this;
		}

		public Builder addParameter( ParameterSpec parameterSpec )
		{
			this.parameterSpecs.add( parameterSpec );
			return this;
		}

		public Builder addModifierNames( Iterable<ModifierName> modifierNames )
		{
			for ( ModifierName modifierName : modifierNames )
			{
				this.modifiers.add( modifierName );
			}

			return this;
		}

		public Builder addModifierName( ModifierName modifierName )
		{
			this.modifiers.add( modifierName );
			return this;
		}

		public Builder addCustomModifierSpecs( Iterable<ModifierSpec> customModifierSpecs )
		{
			for ( ModifierSpec modifierSpec : customModifierSpecs )
			{
				this.customModifiers.add( modifierSpec.getModifierName( ) );
			}

			return this;
		}

		public Builder addCustomModifierSpec( ModifierSpec customModifierSpec )
		{
			this.customModifiers.add( customModifierSpec.getModifierName( ) );
			return this;
		}

		public Builder addCustomModifierNames( Iterable<ModifierName> customModifierNames )
		{
			for ( ModifierName customModifierName : customModifierNames )
			{
				this.customModifiers.add( customModifierName );
			}

			return this;
		}

		public Builder addCustomModifierName( ModifierName customModifierName )
		{
			this.customModifiers.add( customModifierName );
			return this;
		}

		public Builder addReturnParameters( Iterable<ParameterSpec> returnParameters )
		{
			for ( ParameterSpec returnParameter : returnParameters )
			{
				this.returnParameters.add( returnParameter );
			}

			return this;
		}

		public Builder addReturnParameter( ParameterSpec returnParameter )
		{
			this.returnParameters.add( returnParameter );
			return this;
		}

		public Builder addCode( CodeBlock code )
		{
			this.code = code;
			return this;
		}

		public Builder isAbstract( )
		{
			this.code = null;
			return this;
		}

		public FunctionSpec build( )
		{
			return new FunctionSpec( this );
		}
	}
}
