package com.tobiasfertig.java.solidityartist;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionSpec implements Writable
{
	public final static String FUNCTION_KEYWORD = "function";

	private final String name;
	private final Set<ParameterSpec> parameters;
	private final VisibilityName visibility;
	private final Set<ModifierName> modifiers;
	private final Set<ModifierName> customModifiers;

	private FunctionSpec( Builder builder )
	{
		this.name = ( builder.name == null ) ? "" : builder.name;
		this.parameters = builder.parameterSpecs;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifiers;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( FUNCTION_KEYWORD )
			  .write( this.name )
			  .openBraces( );

		writeParameters( writer );

		writer.closeBraces( )
			  .space( )
			  .write( visibility )
			  .space( );

		for ( ModifierName modifier : modifiers )
		{
			writer.write( modifier )
				  .space( );
		}

		for ( ModifierName customModifier : customModifiers )
		{
			writer.write( customModifier )
				  .space( );
		}

		writer.emptyCurlyBraces( );
	}

	private void writeParameters( CodeWriter writer )
	{
		Iterator<ParameterSpec> iterator = parameters.iterator( );

		if ( iterator.hasNext( ) )
		{
			ParameterSpec nextParameter = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				writer.write( nextParameter )
					  .comma( )
					  .space( );
				nextParameter = iterator.next( );
			}

			writer.write( nextParameter );
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

		public FunctionSpec build( )
		{
			return new FunctionSpec( this );
		}
	}
}
